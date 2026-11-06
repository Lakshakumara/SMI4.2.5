package sss.yml.smi.ui.setting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import sss.yml.smi.Home.App;
import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.DbRepository;

public class Settings extends PreferenceFragmentCompat {
    private final ActivityResultLauncher<String> createBackupLauncher =
            registerForActivityResult(new ActivityResultContracts.CreateDocument("application/octet-stream"), uri -> {
                if (uri != null) new DatabaseBackup().execute(uri);
            });

    private final ActivityResultLauncher<String[]> openBackupLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) new DatabaseRestore().execute(uri);
            });
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        String pin = PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getString("my_pin", "Not Set");
        Preference my_pin = findPreference("my_pin");
        my_pin.setSummary(pin);
        my_pin.setOnPreferenceClickListener(arg0 -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireContext().getSystemService(requireContext().CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Pin", pin);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(requireContext(), "Pin Copied", Toast.LENGTH_SHORT).show();
            return true;
        });

        ListPreference lanPre = findPreference("language");
        assert lanPre != null;
        lanPre.setOnPreferenceChangeListener((preference, newValue) -> {
            App.getInstance().initAppLanguage(requireContext(), newValue.toString());
            Intent i = requireActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(requireActivity().getBaseContext().getPackageName());
            startActivity(i);
            return true;
        });
        ListPreference theme = findPreference("theme");
        theme.setOnPreferenceChangeListener((preference, newValue) -> {
            AppCompatDelegate.setDefaultNightMode(Integer.parseInt(newValue.toString()));
            return true;
        });

        Preference btnBackup = findPreference("backup");
        if (btnBackup != null) {
            btnBackup.setOnPreferenceClickListener(arg0 -> {
                createBackupLauncher.launch(System.currentTimeMillis() + ".sss");
                return true;
            });
        }
        Preference btnRestore = findPreference("restore");
        if (btnRestore != null) {
            btnRestore.setOnPreferenceClickListener(arg0 -> {
                openBackupLauncher.launch(new String[]{"*/*"});
                return true;
            });
        }
    }

    private class DatabaseBackup extends AsyncTask<Uri, Void, Boolean> {
        DbRepository repo;
        Uri backupUri;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            HomePage.progress = ProgressDialog.show(requireContext(), "Please wait..",
                    "Dumping", false, false);
            repo = new DbRepository();
            repo.close();
        }

        @Override
        protected Boolean doInBackground(Uri... uris) {
            backupUri = uris[0];
            boolean ok = false;
            byte[] buffer = new byte[1024];
            ZipOutputStream zos = null;
            try {
                java.io.OutputStream fos = requireContext().getContentResolver().openOutputStream(backupUri);
                if (fos == null) return false;
                zos = new ZipOutputStream(fos);
                zos.setComment("Property of Soft Solution Software");
                FileInputStream in = null;
                String file = repo.getDbPath();
                    ZipEntry ze = new ZipEntry(file);
                    zos.putNextEntry(ze);
                    try {
                        in = new FileInputStream(file);
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                    } finally {
                        if (in != null) in.close();
                    }
                zos.closeEntry();
                ok = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (zos != null) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ok;
        }
        @Override
        protected void onPostExecute(Boolean done) {
            super.onPostExecute(done);
            repo.setNull();
            HomePage.progress.dismiss();
            if (!done) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Database backup Error...");
                builder.setNegativeButton("Try again Later", null).create().show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Database Backup Successfully");
            builder.setPositiveButton("OK", null).create().show();
        }
    }

    private class DatabaseRestore extends AsyncTask<Uri, Void, Boolean> {
        DbRepository repo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            HomePage.progress = ProgressDialog.show(requireContext(), "Please wait..",
                    "Restoring", false, false);
            repo = new DbRepository();
            repo.close();
        }

        private void extractFile(ZipInputStream zipIn, String filePath) {
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] bytesIn = new byte[4096];
                int read = 0;
                while ((read = zipIn.read(bytesIn)) != -1) {
                    bos.write(bytesIn, 0, read);
                }
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected Boolean doInBackground(Uri... uris) {
            ZipInputStream zipIn = null;
            try {
                zipIn = new ZipInputStream(requireContext().getContentResolver().openInputStream(uris[0]));
                ZipEntry entry = zipIn.getNextEntry();
                // iterates over entries in the zip file
                while (entry != null) {
                    extractFile(zipIn, entry.getName());
                    zipIn.closeEntry();
                    entry = zipIn.getNextEntry();
                }
                zipIn.close();
            } catch (IOException ignored) {
                return false;
            } finally {
                try {
                    if (zipIn != null) zipIn.close();
                } catch (IOException ignored) {
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean done) {
            super.onPostExecute(done);
            repo.setNull();
            HomePage.progress.dismiss();
            String msg = "Database restored Successfully..";
            String button = "OK";
            if (!done) {
                msg = "Restoring  fail. System can not locate backup file...";
                button = "Try again Later";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(msg);
            builder.setNegativeButton(button, null).create().show();
        }
    }

}
