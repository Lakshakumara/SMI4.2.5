package sss.yml.smi.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import sss.yml.smi.R;
import sss.yml.smi.login.Ban;
import sss.yml.smi.login.Profile;
import sss.yml.smi.util.ConsentManager;

import androidx.preference.PreferenceManager;

import java.util.*;

public class Start extends AppCompatActivity {
    private final int SIGN_IN_REQUEST_CODE = 1;
    public static final int PROFILE_REQUEST_CODE = 2;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    Animation topAnimation, bottomAnimation;
    ImageView logo;
    TextView company, appName, phi;
    boolean showWelComeScreen = false;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );
    private final ActivityResultLauncher<Intent> profileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    launchApp();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.profile_not_complete,
                            Toast.LENGTH_LONG).show();
                }
            }
    );
    private final ActivityResultLauncher<String> notificationPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
            }
    );
    @Override
    public void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        //SplashScreen splashScreen = SplashScreen(this);

        super.onCreate(savedInstanceState);
        App.getInstance().initAppLanguage(this);
        ConsentManager.ensure(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        setContentView(R.layout.splash);
        logo = findViewById(R.id.logo);
        logo.setAnimation(topAnimation);
        company = findViewById(R.id.company);
        appName = findViewById(R.id.app_name);
        phi = findViewById(R.id.phi);
        company.setAnimation(bottomAnimation);
        appName.setAnimation(bottomAnimation);

        String r = getResources().getString(R.string.phirange);
        String range = "range";
        if (r.equalsIgnoreCase("1")) range = "rangesi";
        if (r.equalsIgnoreCase("2")) range = "rangeta";
        range = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(range, "");
        phi.setText(range);
        phi.setAnimation(bottomAnimation);
        FirebaseFirestore.getInstance().collection("BMIS").document("url")
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) return;
                    if (snapshot != null && snapshot.exists()) {
                        Map<String, Object> data = snapshot.getData();
                        //Logger.setMsg(data.toString());
                        if(data != null) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlban", (String) data.get("ban")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlgmp", (String) data.get("gmp")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlhomeview", (String) data.get("homeview")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlprofile", (String) data.get("profile")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlsmi", (String) data.get("smi")).apply();

                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlfacebook", (String) data.get("facebook")).apply();

                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlsupport", (String) data.get("support")).apply();

                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("urlschool", (String) data.get("schools")).apply();
                            //Logger.setMsg((String) data.get("schools"));
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("range", (String) data.get("range")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("rangesi", (String) data.get("rangesi")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                                    putString("rangeta", (String) data.get("rangeta")).apply();
                        }
                    }
                });
        if(FirebaseAuth.getInstance().getUid() != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .get(Source.SERVER)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data = document.getData();
                                //Logger.setMsg(data.toString());
                                if (data != null) {
                                    if (data.get("isSMI") != null)
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                            .putBoolean("isSMI", (Boolean) data.get("isSMI")).apply();
                                    if (data.get("isWP") != null)
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                            .putBoolean("isWP", (Boolean) data.get("isWP")).apply();
                                }
                            }
                        }
                    });
            FirebaseFirestore.getInstance().collection("request")
                    .document(FirebaseAuth.getInstance().getUid())
                    .get(Source.SERVER)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data = document.getData();
                                if(data != null) {
                                    if (data.get("gsmi") != null)
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                                .putBoolean("gsmi", (Boolean) data.get("gsmi")).apply();
                                    if (data.get("gwp") != null)
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                                                .putBoolean("gwp", (Boolean) data.get("gwp")).apply();
                                }
                            }
                        }
                    });
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // if(BuildConfig.DEBUG) launchApp();
            if (currentUser != null) launchApp();
            else createSignInIntent();
        }, 2000);
    }

    public void createSignInIntent() {
        if (!isGmsAvailable()) {
            showGmsDialog();
            return;
        }
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        //fb.build()

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void showGmsDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.gms_missing_title)
                .setMessage(R.string.gms_missing_message)
                .setCancelable(false)
                .setPositiveButton(R.string.skip, (dialog, which) -> createEmailSignInIntent())
                .setNeutralButton(R.string.forgot_password, (dialog, which) -> showForgotPasswordDialog())
                .setNegativeButton(R.string.exit, (dialog, which) -> finish())
                .show();
    }

    private void showForgotPasswordDialog() {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint(R.string.email);
        new AlertDialog.Builder(this)
                .setTitle(R.string.forgot_password_title)
                .setMessage(R.string.forgot_password_message)
                .setView(input)
                .setCancelable(false)
                .setPositiveButton(R.string.send, (dialog, which) ->
                        sendResetEmail(input.getText().toString().trim()))
                .setNegativeButton(R.string.cancel, (dialog, which) -> showGmsDialog())
                .show();
    }

    private void sendResetEmail(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, R.string.invalid_email, Toast.LENGTH_LONG).show();
            showForgotPasswordDialog();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(t -> Toast.makeText(this,
                        R.string.reset_email_sent, Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(this, R.string.reset_email_failed, Toast.LENGTH_LONG).show();
                    showForgotPasswordDialog();
                });
    }

    private void createEmailSignInIntent() {
        // Email/password sign-in works without Google Play Services;
        // Credential Manager is disabled because it requires GMS.
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setCredentialManagerEnabled(false)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private boolean isGmsAvailable() {
        try {
            return GoogleApiAvailability.getInstance()
                    .isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
        } catch (Throwable t) {
            return false;
        }
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUserMetadata metadata = FirebaseAuth.getInstance().getCurrentUser().getMetadata();
            if(metadata != null) {
                showWelComeScreen = metadata.getCreationTimestamp() == metadata.getLastSignInTimestamp();
                launchProfile();
            }
        } else {
            if (response != null && response.getError() != null) {
                Toast.makeText(getApplicationContext(), R.string.sign_in_failed,
                        Toast.LENGTH_LONG).show();
            }
            finish();
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    public void updateApp() {
        String url = PreferenceManager.getDefaultSharedPreferences(HomePage.application.getApplicationContext()).getString("urlfacebook", "http://facebook.com/BMISimulator");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
        /*try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }*/
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, this.getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private void launchApp() {
        boolean profile = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("profile", false);
        if (profile) {
            db.collection("users").document(FirebaseAuth.getInstance().getUid())
                    .addSnapshotListener((snapshot, e) -> {
                        if (e != null) return;
                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                                ? "Local" : "Server";
                        if (snapshot != null && snapshot.exists()) {
                            Map<String, Object> data = snapshot.getData();
                            if(data != null) {
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("ban", (boolean) data.get("ban")).apply();
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("ad", (boolean) data.get("ad")).apply();
                            }
                        }
                    });
            boolean ban = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ban", false);
            if (ban) {
                startActivity(new Intent(Start.this, Ban.class));
            } else {
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("welcome", showWelComeScreen);
                i.putExtra("myself", bundle);
                startActivity(i, bundle);
            }
            finish();
        } else launchProfile();
    }

    private void launchProfile() {
        Intent intent = new Intent(Start.this, Profile.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("create", true);
        intent.putExtra("data", bundle);
        profileLauncher.launch(intent);
    }
}