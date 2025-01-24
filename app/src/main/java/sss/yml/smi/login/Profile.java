package sss.yml.smi.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import sss.yml.smi.R;
import sss.yml.smi.util.CircularImageView;

public class Profile extends AppCompatActivity {
    public static final String EXTRA_REPLY = "Profile.REPLY";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextInputEditText txtEmail;
    private TextInputEditText txtName;
    private TextInputEditText txtTp;
    private TextInputEditText pin;
    private Spinner spinner1;
    private AutoCompleteTextView txtDistrict, txtMoh, txtRange;
    private TextInputEditText txtJobTitle;
    //private FloatingActionButton profilePhoto;
    private CircularImageView profilePhoto;
    private Map<String, Object> userData;
    private boolean isCreate = false;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_personal_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        Intent i = getIntent();
        Bundle b = i.getBundleExtra("data");
        if (b != null) isCreate = b.getBoolean("create");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        profilePhoto = findViewById(R.id.profilePhoto);
        txtEmail = findViewById(R.id.log_email);
        pin = findViewById(R.id.my_pin);
        txtName = findViewById(R.id.log_name);
        txtTp = findViewById(R.id.log_tp);
        txtDistrict = findViewById(R.id.log_district);
        txtMoh = findViewById(R.id.log_moh);
        txtRange = findViewById(R.id.log_range);
        txtJobTitle = findViewById(R.id.log_job_title);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item,
                Arrays.asList(getResources().getStringArray(R.array.district)));
        txtDistrict.setAdapter(adapter);

        spinner1 = findViewById(R.id.spinner_log_category);
        final ArrayAdapter<String> cats = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.logging_group))) {
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        cats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(cats);
        spinner1.setEnabled(isCreate);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        findViewById(R.id.lay_moh).setVisibility(View.GONE);
                        findViewById(R.id.lay_range).setVisibility(View.GONE);
                        findViewById(R.id.lay_job_title).setVisibility(View.GONE);
                        break;
                    case 1:
                    case 2:
                    case 3:
                        findViewById(R.id.lay_moh).setVisibility(View.VISIBLE);
                        ((TextInputLayout) findViewById(R.id.lay_range)).setHint(getString(R.string.working_division));
                        findViewById(R.id.lay_range).setVisibility(View.VISIBLE);
                        findViewById(R.id.lay_job_title).setVisibility(View.GONE);
                        break;
                    case 4:
                        findViewById(R.id.lay_moh).setVisibility(View.GONE);
                        ((TextInputLayout) findViewById(R.id.lay_range)).setHint(R.string.work_institute);
                        findViewById(R.id.lay_range).setVisibility(View.VISIBLE);
                        findViewById(R.id.lay_job_title).setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        findViewById(R.id.lay_moh).setVisibility(View.GONE);
                        findViewById(R.id.lay_range).setVisibility(View.GONE);
                        findViewById(R.id.lay_job_title).setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            if (spinner1.getSelectedItemPosition() == 0) {
                Snackbar.make(spinner1, "Select category", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (isEmailValid() && isTPValid() && iscat()) {
                currentUser = mAuth.getCurrentUser();
                userData = new HashMap<>();
                userData.put("name", currentUser.getDisplayName());
                userData.put("tp", currentUser.getPhoneNumber());
                userData.put("photo", (currentUser.getPhotoUrl() == null) ? null : currentUser.getPhotoUrl().toString());
                userData.put("email", currentUser.getEmail());
                userData.put("uid", currentUser.getUid());
                userData.put("pname", txtName.getText().toString());
                userData.put("ptp", (txtTp.getText() == null) ? "" : txtTp.getText().toString());
                userData.put("district", (txtDistrict.getText() == null) ? "" : txtDistrict.getText().toString());
                if (isCreate) {
                    userData.put("cat", spinner1.getSelectedItem().toString());
                    userData.put("isSMI", isSMImodule());
                    userData.put("isWP", isWPmodule());
                }
                userData.put("moh", (txtMoh.getText() == null) ? "" : txtMoh.getText().toString());
                userData.put("range", (txtRange.getText() == null) ? "" : txtRange.getText().toString());
                userData.put("jobTitle", (txtJobTitle.getText() == null) ? "" : txtJobTitle.getText().toString());

                userData.put("pemail", (txtEmail.getText() == null) ? "" : txtEmail.getText().toString());
                userData.put("phoneid", Settings.Secure
                        .getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                userData.put("ban", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ban", false));
                userData.put("ad", PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ad", true));
                userData.put("startDate", new java.util.Date());
                userData.put("endDate", new java.util.Date());

            } else return;
            final Intent replyIntent = new Intent();
            if (userData == null) {
                setResult(RESULT_CANCELED, replyIntent);
            }
            FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().getUid()).set(userData)
                    .addOnSuccessListener(aVoid -> {
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("my_pin", FirebaseAuth.getInstance().getUid()).apply();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("district", userData.get("district").toString()).apply();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("uid", FirebaseAuth.getInstance().getUid()).apply();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("profile", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("cat", userData.get("cat").toString()).apply();
                        if (isCreate) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("isSMI", (Boolean) userData.get("isSMI")).apply();
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("isWP", (Boolean) userData.get("isWP")).apply();
                        }
                        replyIntent.putExtra(EXTRA_REPLY, (Serializable) userData);
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    });

        });
        if (currentUser != null/* && currentUser.getPhotoUrl() != null*/) {
            txtEmail.setText(currentUser.getEmail());
            txtName.setText(currentUser.getDisplayName());
            txtTp.setText(currentUser.getPhoneNumber());
            //profilePhoto.setImageURI(Uri.parse(currentUser.getPhotoUrl().toString()));
            new getImage().execute(currentUser.getPhotoUrl().toString());
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) return;
                    if (snapshot != null && snapshot.exists()) {
                        Map<String, Object> data = snapshot.getData();
                        txtTp.setText((CharSequence) data.get("ptp"));
                        txtDistrict.setText((CharSequence) data.get("district"));
                        spinner1.setSelection(cats.getPosition((String) data.get("cat")));
                        txtMoh.setText((CharSequence) data.get("moh"));
                        txtRange.setText((CharSequence) data.get("range"));
                        txtJobTitle.setText((CharSequence) data.get("jobTitle"));
                        pin.setText(FirebaseAuth.getInstance().getUid());
                        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("isSMI", (Boolean) data.get("isSMI")).apply();
                        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("isWP", (Boolean) data.get("isWP")).apply();
                    }
                });
        if (!isCreate) {
            ConstraintLayout pnlRequest = findViewById(R.id.request);
            pnlRequest.setVisibility(View.VISIBLE);
            TextInputLayout pinCopy = findViewById(R.id.lay_my_pin);
            pinCopy.setEndIconOnClickListener(v -> {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Pin", pin.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Profile.this, "Pin Copied", Toast.LENGTH_SHORT).show();
            });

            final CheckBox rqsmi = findViewById(R.id.rqsmi);
            final boolean isSMI = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("isSMI", false);
            rqsmi.setEnabled(!isSMI);
            final boolean isWP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("isWP", false)
                    || PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("gwp", false);
            final CheckBox rqwp = findViewById(R.id.rqwp);
            rqwp.setEnabled(!isWP);
            final Button btnUpgrade = findViewById(R.id.btnUpgrade);
            btnUpgrade.setOnClickListener(v -> {
                Map<String, Object> request = new HashMap<>();
                if (isSMI) {
                    request.put("smi", rqsmi.isChecked());
                    request.put("gsmi", null);
                }
                if (isWP) {
                    request.put("wp", rqwp.isChecked());
                    request.put("gwp", null);
                }
                FirebaseFirestore.getInstance().collection("request")
                        .document(FirebaseAuth.getInstance().getUid()).set(request)
                        .addOnSuccessListener(aVoid -> btnUpgrade.setEnabled(false));
            });
        }
    }

    private boolean isEmailValid() {
        CharSequence email = txtEmail.getText();
        if (email == null || TextUtils.isEmpty((email))) {
            Snackbar.make(txtEmail, "Enter Email Address", Snackbar.LENGTH_SHORT).show();
            txtEmail.requestFocus();
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(txtEmail, "Email Address is not valid", Snackbar.LENGTH_SHORT).show();
                txtEmail.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean isTPValid() {
        CharSequence tp = txtTp.getText();
        if (tp == null) return true;
        if (TextUtils.isEmpty(tp)) return true;
        if (!Patterns.PHONE.matcher(tp).matches()) {
            Snackbar.make(txtTp, "Telephone number not in valid format", Snackbar.LENGTH_SHORT).show();
            txtTp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isSMImodule() {
        int x = spinner1.getSelectedItemPosition();
        return x == 1 || x == 2;
    }

    private boolean isWPmodule() {
        int x = spinner1.getSelectedItemPosition();
        return x == 1 || x == 2 || x == 3;
    }

    private boolean iscat() {
        boolean ok = false;
        switch (spinner1.getSelectedItemPosition()) {
            case 0:
                break;
            case 1:
            case 2:
            case 3:
                ok = !TextUtils.isEmpty(txtMoh.getText()) && !TextUtils.isEmpty(txtRange.getText());
                break;
            case 4:
                ok = !TextUtils.isEmpty(txtRange.getText()) && !TextUtils.isEmpty(txtJobTitle.getText());
                break;
            case 5:
                ok = true;
                break;
            default:
                ok = false;
        }
        if (!ok)
            Snackbar.make(spinner1, R.string.required_field_empty, Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(this,  R.string.required_field_empty, Toast.LENGTH_SHORT).show();//
        return ok;
    }

    private class getImage extends AsyncTask<String, Void, Boolean> {
        Bitmap bitmap;

        @Override
        protected Boolean doInBackground(String... strings) {
            URL imageurl;
            boolean saved = false;
            try {
                imageurl = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
                File folder = new File(getFilesDir().getAbsoluteFile(), "/profile/");
                folder.mkdir();
                File file = new File(folder.getAbsoluteFile(), "profile.jpg");
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                saved = true;
            } catch (IOException e) {
                //bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_person)).getBitmap();
                e.printStackTrace();
            }
            return saved;
        }

        @Override
        protected void onPostExecute(Boolean saved) {
            /*DrawableUtils.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                profilePhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_full_sad, context.getTheme()));
            } else {
                profilePhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_full_sad));
            }*/
            File f = new File(getFilesDir().getPath() + "/profile/profile.jpg");
            profilePhoto.setImageURI(Uri.parse(f.toString()));
        }
    }

}