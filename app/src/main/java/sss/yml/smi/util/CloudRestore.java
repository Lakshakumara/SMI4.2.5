package sss.yml.smi.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.DbRepository;
import sss.yml.smi.room.Student;

/**
 * One-time / on-demand restore of student records from the Firestore mirror
 * (data/{measureYear}/{censusNo}/{docRef}, stamped with uid + docRef on upload).
 *
 * Census/year combinations are queried as direct collection paths
 * (data/{year}/{census}) because plain collection queries work with Firestore's
 * default indexes, while collectionGroup() queries would need a per-census
 * collection-group index created in the console (FAILED_PRECONDITION).
 * Census numbers come from the local school table plus a guess range, with a
 * manual CensusNo entry fallback. Dedupe by docRef, falling back to a content
 * key for rows uploaded before docRef existed. Tasks.await is a plain library,
 * so this runs without Google Play Services.
 */
public class CloudRestore {
    private static final Executor EXEC = Executors.newSingleThreadExecutor();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private static final int FIRST_YEAR = 2015;
    private static final int LAST_YEAR = 2030;
    // Firestore runs these over one multiplexed stream; batching keeps the
    // request queue sane while still finishing in a few seconds.
    private static final int CHUNK = 48;

    private static class Result {
        int restored, skipped, failed, unmatched, queriesOk, queriesFail;
        String error;
    }

    /** Settings button and the auto-prompt both funnel into this. */
    public static void run(Context context) {
        start(context, null);
    }

    /** Re-run with the CensusNo values the user typed. */
    public static void runWithIds(Context context, Set<Integer> censusIds) {
        start(context, censusIds);
    }

    private static void start(Context context, Set<Integer> explicitIds) {
        if (HomePage.progress == null || !HomePage.progress.isShowing()) {
            HomePage.progress = new ProgressBanner(context);
        }
        ProgressBanner banner = HomePage.progress;
        banner.setTitle("Please wait..");
        banner.setMessage(context.getString(R.string.cloud_restore_progress));
        banner.show();
        final String progressPrefix = context.getString(R.string.cloud_restore_progress);
        EXEC.execute(() -> {
            Result res;
            try {
                DbRepository repo = new DbRepository();
                Set<Integer> schoolIds = explicitIds != null
                        ? explicitIds : new LinkedHashSet<>(repo.getCensusIds());
                Set<Integer> ids = new LinkedHashSet<>(schoolIds);
                if (explicitIds == null) {
                    for (int i = 0; i <= 50; i++) ids.add(i);
                    for (int y = FIRST_YEAR; y <= LAST_YEAR; y++) ids.add(y);
                }
                res = doRestore(repo, ids, schoolIds, banner, progressPrefix);
            } catch (Exception e) {
                res = new Result();
                res.error = describe(e);
            }
            final Result r = res;
            MAIN.post(() -> {
                if (HomePage.progress != null) HomePage.progress.dismiss();
                showResult(context, r);
            });
        });
    }

    private static Result doRestore(DbRepository repo, Set<Integer> censusIds,
                                   Set<Integer> schoolCids, ProgressBanner banner,
                                   String progressPrefix) {
        Result res = new Result();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            res.error = "not_signed_in";
            return res;
        }
        String uid = user.getUid();
        List<Student> local;
        try {
            local = repo.getAllStudentsSync();
        } catch (Exception e) {
            res.error = describe(e);
            return res;
        }
        Set<String> docRefs = new HashSet<>();
        Set<String> keys = new HashSet<>();
        for (Student s : local) {
            if (s.getDocRef() != null && !s.getDocRef().isEmpty()) docRefs.add(s.getDocRef());
            keys.add(key(s));
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Direct-path cross product: data/{year}/{census} where uid matches.
        List<int[]> combos = new ArrayList<>();
        for (int cid : censusIds) {
            for (int y = FIRST_YEAR; y <= LAST_YEAR; y++) combos.add(new int[]{cid, y});
        }
        String lastErr = null;
        for (int i = 0; i < combos.size(); i += CHUNK) {
            final int end = Math.min(i + CHUNK, combos.size());
            List<int[]> batch = combos.subList(i, end);
            List<Task<QuerySnapshot>> tasks = new ArrayList<>(batch.size());
            for (int[] cy : batch) {
                tasks.add(db.collection("data")
                        .document(String.valueOf(cy[1]))
                        .collection(String.valueOf(cy[0]))
                        .whereEqualTo("uid", uid)
                        .get());
            }
            MAIN.post(() -> banner.setMessage(progressPrefix + " " + end + "/" + combos.size()));
            for (Task<QuerySnapshot> task : tasks) {
                try {
                    QuerySnapshot match = Tasks.await(task);
                    res.queriesOk++;
                    for (DocumentSnapshot doc : match.getDocuments()) {
                        // Only the student mirror may be imported, never similarly
                        // named subcollections elsewhere.
                        if (!doc.getReference().getPath().startsWith("data/")) continue;
                        try {
                            Student s = doc.toObject(Student.class);
                            if (s == null) {
                                res.failed++;
                                continue;
                            }
                            String dr = s.getDocRef() == null ? "" : s.getDocRef();
                            if ((!dr.isEmpty() && docRefs.contains(dr)) || keys.contains(key(s))) {
                                res.skipped++;
                                continue;
                            }
                            s.setId(0);
                            repo.insert(s);
                            if (!dr.isEmpty()) docRefs.add(dr);
                            keys.add(key(s));
                            res.restored++;
                        } catch (Exception e) {
                            res.failed++;
                        }
                    }
                } catch (Exception e) {
                    res.queriesFail++;
                    lastErr = describe(e);
                }
            }
        }

        // Best-effort "records exist but aren't tagged with your uid" signal,
        // bounded to the school table's census numbers (their real paths).
        if (res.restored == 0 && res.skipped == 0) {
            for (int cid : schoolCids) {
                boolean found = false;
                for (int y = FIRST_YEAR; y <= LAST_YEAR && !found; y++) {
                    try {
                        QuerySnapshot all = Tasks.await(db.collection("data")
                                .document(String.valueOf(y))
                                .collection(String.valueOf(cid))
                                .limit(5)
                                .get());
                        if (!all.isEmpty()) {
                            res.unmatched += all.size();
                            found = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        if (res.queriesOk == 0 && res.queriesFail > 0) {
            res.error = lastErr;
        }
        return res;
    }

    private static String key(Student s) {
        return s.getCensusNo() + "|" + s.getGrade() + "|"
                + (s.getDob() == null ? 0 : s.getDob().getTime()) + "|"
                + s.getUserName() + "|" + s.getSex() + "|"
                + (s.getMdate() == null ? 0 : s.getMdate().getTime()) + "|" + s.getRefId();
    }

    private static String describe(Throwable e) {
        Throwable c = e;
        while (c.getCause() != null) c = c.getCause();
        if (c instanceof FirebaseFirestoreException) {
            FirebaseFirestoreException fe = (FirebaseFirestoreException) c;
            if (fe.getCode() == FirebaseFirestoreException.Code.PERMISSION_DENIED) return "permission_denied";
            return fe.getCode().name();
        }
        return c.getMessage() == null
                ? c.getClass().getSimpleName()
                : c.getClass().getSimpleName() + ": " + c.getMessage();
    }

    private static void showResult(Context context, Result res) {
        if (context instanceof Activity
                && (((Activity) context).isFinishing() || ((Activity) context).isDestroyed())) return;
        if (res.error != null && res.restored == 0) {
            String msg;
            if ("permission_denied".equals(res.error)) {
                msg = context.getString(R.string.cloud_restore_permission);
            } else if ("not_signed_in".equals(res.error)) {
                msg = context.getString(R.string.sign_in_failed);
            } else {
                msg = context.getString(R.string.cloud_restore_error, res.error);
            }
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.cloud_restore_failed)
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }
        if (res.restored == 0 && res.skipped == 0) {
            String msg = res.unmatched > 0
                    ? context.getString(R.string.cloud_restore_none_unmatched, res.unmatched)
                    : context.getString(R.string.cloud_restore_none);
            new MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.cloud_auto_title)
                    .setMessage(msg)
                    .setPositiveButton(R.string.cloud_restore_census_search,
                            (dialog, which) -> showCensusDialog(context))
                    .setNegativeButton(android.R.string.ok, null)
                    .show();
            return;
        }
        String msg = res.failed > 0
                ? context.getString(R.string.cloud_restore_result_partial, res.restored, res.skipped, res.failed)
                : context.getString(R.string.cloud_restore_result, res.restored, res.skipped);
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.cloud_restore_done)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private static void showCensusDialog(Context context) {
        if (context instanceof Activity
                && (((Activity) context).isFinishing() || ((Activity) context).isDestroyed())) return;
        EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.cloud_restore_census_hint);
        int margin = (int) (16 * context.getResources().getDisplayMetrics().density);
        FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = margin;
        lp.rightMargin = margin;
        lp.topMargin = margin / 2;
        input.setLayoutParams(lp);
        container.addView(input);
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.cloud_restore_census_title)
                .setMessage(R.string.cloud_restore_census_msg)
                .setView(container)
                .setPositiveButton(R.string.restore, (dialog, which) -> {
                    Set<Integer> ids = new LinkedHashSet<>();
                    for (String part : input.getText().toString().split("[^0-9]+")) {
                        if (part.isEmpty()) continue;
                        try {
                            ids.add(Integer.parseInt(part));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    if (ids.isEmpty()) {
                        Toast.makeText(context, R.string.cloud_restore_census_invalid, Toast.LENGTH_LONG).show();
                    } else {
                        runWithIds(context, ids);
                    }
                })
                .setNegativeButton(R.string.skip, null)
                .show();
    }

    /** Option B: one prompt per install, only when the local student table is empty. */
    public static void maybeAutoPrompt(Activity activity) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        if (prefs.getBoolean("cloud_restore_prompted", false)) return;
        EXEC.execute(() -> {
            int count = new DbRepository().countStudents();
            if (count != 0) return;
            MAIN.post(() -> showPromptWhenIdle(activity, prefs, 0));
        });
    }

    // Wait for HomePage's own "Loading..." banner to clear so dialogs never stack.
    private static void showPromptWhenIdle(Activity activity, SharedPreferences prefs, int attempt) {
        if (activity.isFinishing() || activity.isDestroyed()) return;
        if (HomePage.progress != null && HomePage.progress.isShowing()) {
            if (attempt >= 20) return; // still busy: skip, prompt again next launch
            MAIN.postDelayed(() -> showPromptWhenIdle(activity, prefs, attempt + 1), 1000);
            return;
        }
        prefs.edit().putBoolean("cloud_restore_prompted", true).apply();
        new MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.cloud_auto_title)
                .setMessage(R.string.cloud_auto_msg)
                .setPositiveButton(R.string.restore, (dialog, which) -> run(activity))
                .setNegativeButton(R.string.skip, null)
                .show();
    }
}
