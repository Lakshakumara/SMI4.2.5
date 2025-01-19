package sss.yml.smi.ui.admin;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;
import sss.yml.smi.R;
import sss.yml.smi.util.CircularImageView;
import sss.yml.smi.util.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


public class UsersFragment extends Fragment {
    FirebaseFirestore db;
    ProgressBar mProgressDialog;
    private TextInputEditText txtEmail,txtName,txtTp,pin, txtJobTitle;
    private Spinner spinner1;
    private AutoCompleteTextView txtDistrict, txtMoh, txtRange;
    private CircularImageView profilePhoto;
    ArrayAdapter<String> cats;
    private Map<String, Object> userData;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.login_personal_data, container, false);
        setMenuProvider();
        mProgressDialog = view.findViewById(R.id.progressBar);
        profilePhoto = view.findViewById(R.id.profilePhoto);
        txtEmail = view.findViewById(R.id.log_email);
        pin = view.findViewById(R.id.my_pin);
        txtName = view.findViewById(R.id.log_name);
        txtTp = view.findViewById(R.id.log_tp);
        txtDistrict = view.findViewById(R.id.log_district);
        txtMoh = view.findViewById(R.id.log_moh);
        txtRange = view.findViewById(R.id.log_range);
        txtJobTitle = view.findViewById(R.id.log_job_title);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, Arrays.asList(getResources().getStringArray(R.array.district)));
        txtDistrict.setAdapter(adapter);

        spinner1 = view.findViewById(R.id.spinner_log_category);
        cats = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.logging_group))) {
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        cats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(cats);
        spinner1.setEnabled(true);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnContinue).setOnClickListener(view1 -> {
            userData.put("email", Objects.requireNonNull(txtEmail.getText()).toString());
            userData.put("name", Objects.requireNonNull(txtName.getText()).toString());
            userData.put("tp", Objects.requireNonNull(txtTp.getText()).toString());
            userData.put("district", txtDistrict.getText().toString());
            userData.put("moh", txtMoh.getText().toString());
            userData.put("range", txtRange.getText().toString());
            userData.put("jobTitle", Objects.requireNonNull(txtJobTitle.getText()).toString());
            userData.put("cat", spinner1.getSelectedItem().toString());

            /*userData.put("ban", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ban", false));
            userData.put("ad", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ad", true));
            userData.put("startDate", new java.util.Date());
            userData.put("endDate", new java.util.Date());*/


            Logger.setMsg("Selected userData "+ userData);

            db.collection("users")
                    .document(Objects.requireNonNull(pin.getText()).toString()).set(userData)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show());

        });
    }
    private void setMenuProvider(){
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                MenuProvider.super.onPrepareMenu(menu);
            }

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.search, menu);
                SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
                searchView.setIconifiedByDefault(true);
                searchView.setQueryHint(getString(R.string.search));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        showProgressDialog("Fetching "+query);
                        db.collection("users")
                                .document(query)
                                .addSnapshotListener((snapshot, e) -> {
                                    if (e != null) return;
                                    if (snapshot != null && snapshot.exists()) {
                                        userData = snapshot.getData();
                                        assert userData != null;
                                        txtEmail.setText((CharSequence) userData.get("email"));
                                        txtName.setText((CharSequence) userData.get("name"));
                                        txtTp.setText((CharSequence) userData.get("ptp"));
                                        txtDistrict.setText((CharSequence) userData.get("district"));
                                        spinner1.setSelection(cats.getPosition((String)userData.get("cat")));
                                        txtMoh.setText((CharSequence) userData.get("moh"));
                                        txtRange.setText((CharSequence) userData.get("range"));
                                        txtJobTitle.setText((CharSequence) userData.get("jobTitle"));
                                        pin.setText((CharSequence) userData.get("uid"));

                                        }
                                    hideProgressDialog();
                                });

/*                        db.collection("users")
                                .document(query)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot result = task.getResult();
                                        Logger.setMsg(result.getId() + " => " + result.getData());
                                    } else {
                                        Logger.setMsg( "Error getting documents: ", task.getException());
                                    }
                                    hideProgressDialog();
                                });
                        db.collection("users")
                                .whereEqualTo("name", query)
                                .orderBy("name", Query.Direction.ASCENDING).limit(3)
                                .startAt(query)
                                .endAt(query+"\uf8ff")
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Logger.setMsg(document.getId() + " => " + document.getData());
                                        }
                                    } else {
                                        Logger.setMsg( "Error getting documents: ", task.getException());
                                    }
                                    hideProgressDialog();
                                });
*/
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Logger.setMsg(newText);
                        return false;
                    }
                });

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.filterName:
                    case R.id.filterAll:
                        menuItem.setChecked(!menuItem.isChecked());
                        return true;
                    default:
                        //return super.onMen(item);
                }
                return false;
            }

            @Override
            public void onMenuClosed(@NonNull Menu menu) {
                MenuProvider.super.onMenuClosed(menu);
            }
        });
    }

    private void showProgressDialog(String message) {
        //mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setVisibility(View.VISIBLE);
    }

    private void hideProgressDialog() {
        mProgressDialog.setIndeterminate(false);
        if (mProgressDialog != null && mProgressDialog.getVisibility() == View.VISIBLE) {
            mProgressDialog.setVisibility(View.GONE);
        }
    }
}
