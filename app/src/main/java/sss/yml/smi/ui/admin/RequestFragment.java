package sss.yml.smi.ui.admin;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import sss.yml.smi.R;
import sss.yml.smi.util.CircularImageView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static sss.yml.smi.util.SMIConstants.dt;

public class RequestFragment extends Fragment {
    View view;
    Switch fsmi;
    Switch fwp;
    Button fupdate;
    String id;
    TextView fUser;
    private CircularImageView profilePhoto;
    FirebaseFirestore db;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_request, container, false);
        mProgressDialog = new ProgressDialog(requireContext());
        setHasOptionsMenu(true);
        fsmi = view.findViewById(R.id.fSmi);
        fwp = view.findViewById(R.id.fwp);
        fUser = view.findViewById(R.id.fuser);
        profilePhoto = view.findViewById(R.id.profilePhoto);
        db = FirebaseFirestore.getInstance();

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fupdate).setOnClickListener(view1 -> {
            if (id == null) return;
            showProgressDialog("Saving data");
            Map<String, Object> news = new HashMap<>();
            news.put("gsmi", fsmi.isChecked());
            news.put("gwp", fwp.isChecked());
            db.collection("request")
                    .document(id).update("gsmi", fsmi.isChecked(), "gwp", fwp.isChecked())
                    .addOnSuccessListener(aVoid -> {
                        db.collection("users")
                                .document(id).update("isSMI", fsmi.isChecked(), "isWP", fwp.isChecked())
                                .addOnSuccessListener(Void -> {
                                    hideProgressDialog();
                                })
                                .addOnFailureListener(e -> {
                                });

                        hideProgressDialog();
                    })
                    .addOnFailureListener(e -> {
                        hideProgressDialog();
                    });
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                id = query;
                showProgressDialog("Fetching " + id);
                if (id.equalsIgnoreCase("all")) {

                } else
                    db.collection("request").document(id)
                            .addSnapshotListener((snapshot, e) -> {
                                if (snapshot != null && snapshot.exists()) {
                                    Map<String, Object> data = snapshot.getData();
                                    fsmi.setChecked(data.get("smi") != null && (boolean) data.get("smi"));
                                    fwp.setChecked(data.get("wp") != null && (boolean) data.get("wp"));
                                    db.collection("users").document(id)
                                            .addSnapshotListener((user, e1) -> {
                                                if (user != null && user.exists()) {
                                                    Map<String, Object> uData = user.getData();
                                                    assert uData != null;
                                                    fUser.append(uData.get("district") + " -> " + uData.get("moh") + " -> " + uData.get("range") + "\n");
                                                    Timestamp ts = ((Timestamp) uData.get("startDate"));
                                                    fUser.append(uData.get("cat") + " -> " + dt.format(ts.toDate()) + "\n");
                                                    fUser.append(uData.get("pname") + "\n");
                                                    fUser.append(uData.get("pemail") + "\n");
                                                    fUser.append(uData.get("ptp") + "\n");

                                                    new getImage().execute(uData.get("photo").toString());
                                                    fUser.append("\n\n\n");

                                                    for (Map.Entry<String, Object> entry : uData.entrySet()) {
                                                        if (entry.getKey().equalsIgnoreCase("photo") ||
                                                                entry.getKey().equalsIgnoreCase("startDate") ||
                                                                entry.getKey().equalsIgnoreCase("endDate"))
                                                            continue;
                                                        fUser.append(entry.getKey() + ":\t" + entry.getValue() + "\n");
                                                    }

                                                    hideProgressDialog();
                                                }
                                            });
                                }
                            });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private class getImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL imageurl = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            profilePhoto.setImageBitmap(result);
        }
    }

    private void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}