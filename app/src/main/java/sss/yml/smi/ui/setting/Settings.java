package sss.yml.smi.ui.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import sss.yml.smi.Home.App;
import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.DbRepository;

public class Settings extends PreferenceFragmentCompat {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
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
        assert theme != null;
        theme.setOnPreferenceChangeListener((preference, newValue) -> {
            AppCompatDelegate.setDefaultNightMode(Integer.parseInt(newValue.toString()));
            return true;
        });

        Preference btnBackup = findPreference("backup");
        if (btnBackup != null) {
            btnBackup.setOnPreferenceClickListener(arg0 -> {
                if (ContextCompat.checkSelfPermission(
                        requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new AlertDialog.Builder(requireActivity())
                                .setMessage("Need Storage access permission to Backup Database Please provide permission to access your Storage.")
                                .setPositiveButton("Ok", (dialog, whichButton) ->
                                        ActivityCompat.requestPermissions(requireActivity(),
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                201))
                                .setNegativeButton(getString(R.string.cancel), (dialog, whichButton) -> {})
                                .create()
                                .show();
                    } else {
                        // You can directly ask for the permission.
                        // The registered ActivityResultCallback gets the result of this request.
                        registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                            if (isGranted) {
                                new DatabaseBackup().execute();
                            } else {
                                btnBackup.setSummary("Require Storage Access permission");
                                // Explain to the user that the feature is unavailable because the
                                // features requires a permission that the user has denied. At the
                                // same time, respect the user's decision. Don't link to system
                                // settings in an effort to convince the user to change their
                                // decision.
                            }
                        }).launch(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                } else new DatabaseBackup().execute();
                return true;
            });
        }
        Preference btnRestore = findPreference("restore");
        if (btnRestore != null) {
            btnRestore.setOnPreferenceClickListener(arg0 -> {
                permission("Database Restore");
                String source = Environment.getExternalStorageDirectory().getPath() + "/BMIS/";
                File ff = new File(source);
                final List<Long> fList = new ArrayList<>();
                final List<String> fDates = new ArrayList<>();
                final List<String> selectedFile = new ArrayList<>();
                File[] files = ff.listFiles();
                if(files == null) return false;
                Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

                for (File ffl : files)
                    try {
                        String f = ffl.getName();
                        String nameTime = f.substring(0, f.length() - 4);
                        if (nameTime.length() != 13 ) continue;
                        long l = Long.parseLong(nameTime);
                        fList.add(l);
                        fDates.add(sdf.format(new java.util.Date(l)));
                    } catch (NumberFormatException ignored) {
                    }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Database");
                builder.setSingleChoiceItems(fDates.toArray(new String[0]), -1, (dialog, which) ->
                        selectedFile.add(String.valueOf(fList.get(which))));
                builder.setPositiveButton("Restore", (dialog, id) ->
                        new DatabaseRestore().execute(selectedFile.toArray(new String[0])));
                    builder.setNegativeButton("Cancel", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                return true;
            });
        }
    }
    private void permission(String tag){
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Need Storage access permission to "+tag+". Please provide permission to access your Storage.")
                        .setPositiveButton("Ok", (dialog, whichButton) ->
                                ActivityCompat.requestPermissions(requireActivity(),
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        201))
                        .setNegativeButton(getString(R.string.cancel), (dialog, whichButton) -> {})
                        .create()
                        .show();
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

    }
    private class DatabaseBackup extends AsyncTask<Void, Void, Boolean> {
        DbRepository repo;
        String newBackupFile;
        String extDir;
        String extDirRoot;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            HomePage.progress = ProgressDialog.show(requireContext(), "Please wait..",
                    "Dumping", false, false);
            repo = new DbRepository();
            repo.close();
            long dd = new java.util.Date().getTime();
            newBackupFile = String.valueOf(dd);
            extDirRoot = Environment.getExternalStorageDirectory().getPath() + "/BMIS/";
            File outFolder = new File(extDirRoot);
            if (!outFolder.exists()) outFolder.mkdirs();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean ok = false;
            byte[] buffer = new byte[1024];
            FileOutputStream fos;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(extDirRoot + newBackupFile + ".sss");
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
            builder.setTitle("Database Backup Successfully to BMISimulator folder");
            builder.setNegativeButton("Keep Previous", null);
            builder.setPositiveButton("Delete Previous", (dialog, which) -> {
                extDir = Environment.getExternalStorageDirectory().getPath() + "/BMIS/";
                File ff = new File(extDir);
                String[] list = ff.list();
                for (String f : list)
                    try {
                        String nameTime = f.substring(0, f.length() - 4);
                        Long l = new Long(nameTime);
                        if (nameTime.length() != 13 || nameTime.equalsIgnoreCase(newBackupFile)) continue;
                        new File(extDir + f).delete();
                    } catch (NumberFormatException ignored) {}
            }).create().show();
        }
    }

    private  class DatabaseRestore extends AsyncTask<String, Void, Boolean> {
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
        protected Boolean doInBackground(String... backupFile) {
            String source = Environment.getExternalStorageDirectory().getPath() + "/BMIS/" + backupFile[0] + ".sss";
            ZipInputStream zipIn = null;
            try {
                zipIn = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry = zipIn.getNextEntry();
                // iterates over entries in the zip file
                while (entry != null) {
                    extractFile(zipIn, entry.getName());
                    zipIn.closeEntry();
                    entry = zipIn.getNextEntry();
                }
                zipIn.close();
            } catch (IOException ignored) {
            } finally {
                try {
                    zipIn.close();
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
