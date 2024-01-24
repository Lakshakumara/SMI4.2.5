package sss.yml.smi.ui.child;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.Child;
import sss.yml.smi.util.DecimalDigitsInputFilter;
import sss.yml.smi.util.SMIConstants;

public class ChildReg extends Fragment {
    private Child child;
    private View view;
    private ChildRegViewModel mViewModel;
    private TextInputEditText regNo, yy, mm, dd, guard, tp;
    private TextInputEditText name, note, txtFHt, txtMHt, txtMaturity;
    private RadioGroup sexgp;
    private RadioButton male, female;
    private int curY, bY;
    private int bM;
    private int bD;
    private int SpannedLength = 0, chipLength = 4;
    private String subtitle = null;

    public ChildReg() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_reg_pop, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(ChildRegViewModel.class);
        Calendar c = Calendar.getInstance();
        curY = c.get(Calendar.YEAR);
        int curM = c.get(Calendar.MONTH) + 1;
        int curD = c.get(Calendar.DATE);

        regNo = view.findViewById(R.id.child_regno);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            regNo.setAccessibilityTraversalAfter(R.id.child_name);
        }
        regNo.setOnClickListener(v -> isBI());
        name = view.findViewById(R.id.child_name);
        txtMaturity = view.findViewById(R.id.child_maturity);
        txtMaturity.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 1)});
        guard = view.findViewById(R.id.child_guard_name);
        tp = view.findViewById(R.id.child_tp);

        yy = view.findViewById(R.id.yy);
        mm = view.findViewById(R.id.mm);
        dd = view.findViewById(R.id.dd);
        sexgp = view.findViewById(R.id.radioGroup);
        male = view.findViewById(R.id.child_rbM);
        female = view.findViewById(R.id.child_rbF);
        note = view.findViewById(R.id.child_note);
        txtFHt = view.findViewById(R.id.child_fht);
        txtMHt = view.findViewById(R.id.child_mht);
        //txtFHt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 1)});
        //txtMHt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 1)});

        note.setOnClickListener(v -> {
            /*ChipDrawable chip = ChipDrawable.createFromResource(getContext(), R.xml.standalone_chip);
            chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(chip);
            Editable text = note.getText();
            text.setSpan(span, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        });
        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if (charSequence.length() == SpannedLength - chipLength) {
                    SpannedLength = charSequence.length();
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {
/*
                if (editable.length() - SpannedLength == chipLength) {
                    ChipDrawable chip = ChipDrawable.createFromResource(getContext(), R.xml.standalone_chip);
                    chip.setText(editable.subSequence(SpannedLength, editable.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }*/
            }
        });
        //yy.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 1)});
        yy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    if (isYear(s)) mm.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //mm.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2, 1)});
        mm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    if (isMonth(s)) dd.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    if (isDate(s)) name.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (getArguments() != null) {
            int childId = ChildRegArgs.fromBundle(getArguments()).getChildId();
            //LiveData<Child> child = mViewModel.getChild(childId);
            //displayChild(this.child);
            mViewModel.getChild(childId).observe(getViewLifecycleOwner(), selectedChild -> {
                child = selectedChild;
                displayChild(child);
            });
        }

        // NavDirections navDirections = ChildRegDirections.actionChildRegToWtpost().setChildId(child.getId());
        // Navigation.findNavController(view).navigate(navDirections);
        return view;
    }

    private boolean isYear(CharSequence s) {
        try {
            bY = Integer.parseInt(s.toString());
            if (bY > curY) {
                yy.setError(getString(R.string.dob_future));
                yy.selectAll();
                yy.requestFocus();
                return false;
            }
            if (bY < curY - 5) {
                yy.setError(getString(R.string.age_over_5_years));
                yy.selectAll();
                yy.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            yy.setError(getString(R.string.error));
            yy.selectAll();
            yy.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isMonth(CharSequence s) {
        try {
            bM = Integer.parseInt(s.toString());
            if (bM < 1 || bM > 12) {
                mm.setError("X");
                mm.selectAll();
                mm.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            mm.setError(getString(R.string.error));
            mm.selectAll();
            mm.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isDate(CharSequence s) {
        try {
            bD = Integer.parseInt(s.toString());
            if (bD < 1 || bD > 31) {
                dd.setError("X");
                dd.selectAll();
                dd.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            dd.setError(getString(R.string.error));
            dd.selectAll();
            dd.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isBI() {
        TextInputLayout reglayout = view.findViewById(R.id.reglayout);
        if (TextUtils.isEmpty(regNo.getText()) || TextUtils.isEmpty(regNo.getText().toString().trim())) {
            reglayout.setErrorEnabled(true);
            reglayout.setError("BI registration no is empty");
            reglayout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            regNo.requestFocus();
            return false;
        } else {
            reglayout.setHelperTextEnabled(false);
            reglayout.setErrorEnabled(false);
            reglayout.setError(null);
            reglayout.setEndIconDrawable(R.drawable.ic_done);
            name.requestFocus();
            return true;
        }
    }

    private void saveChild() {
        InputMethodManager imm = (InputMethodManager) HomePage.application.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        if (!isBI()) return;

        String cn = name.getText().toString();
        if (TextUtils.isEmpty(cn) || TextUtils.isEmpty(cn.trim())) {
            name.setError(getString(R.string.please_enter_name));
            name.requestFocus();
            return;
        }
        Calendar dob = Calendar.getInstance();
        if (!isYear(yy.getText()) || !isMonth(mm.getText()) || !isDate(dd.getText())) {
            return;
        } else {
            dob.set(bY, bM - 1, bD);
            if (dob.getTime().after(new Date())) {
                Snackbar.make(view, R.string.dob_future, Snackbar.LENGTH_LONG).show();
                yy.setError("X");
                yy.requestFocus();
                return;
            }
        }
        if (!male.isChecked() && !female.isChecked()) {
            Snackbar.make(view, R.string.sex_not_selected, Snackbar.LENGTH_LONG).show();
            return;
        }
        double fht = 0;
        double mht = 0;
        int maturity = 37;
        try {
            fht = Double.parseDouble(Objects.requireNonNull(txtFHt.getText()).toString());
            mht = Double.parseDouble(Objects.requireNonNull(txtMHt.getText()).toString());
            maturity = Integer.parseInt(Objects.requireNonNull(txtMaturity.getText()).toString());
            if (maturity > 40) {
                Snackbar.make(view, R.string.check_maturity_weeks, Snackbar.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException ignored) {
        }

        Child savedChild = mViewModel.getChild(regNo.getText().toString().trim());
        Child nameChild = mViewModel.getChild(null, name.getText().toString().trim());
        //Integer childId = mViewModel.getChildId(name.getText().toString().trim());
        if(savedChild == null){
            if(nameChild == null){
                child = new Child(regNo.getText().toString(), name.getText().toString(), (male.isChecked()) ? "Male" : "Female",
                        dob.getTime(), maturity, guard.getText().toString(), tp.getText().toString(), note.getText().toString(), fht, mht);
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.add_new_child)
                        .setPositiveButton(R.string.add, (dialog, whichButton) -> {
                            int newId = 0;
                            try {
                                newId = mViewModel.insert(child);
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            clear();
                        })
                        .setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
                        }).create().show();
            }else{
                //Error Duplicate name
                name.setError(getString(R.string.name_already_exists));
                name.requestFocus();
                return;
            }
        }else{
            if(nameChild == null){
                //update
                updateChild();
            }else{
                if(savedChild.getId() == nameChild.getId()){
                    //update
                    updateChild();
                }else{
                    //Error Duplicate name
                    name.setError(getString(R.string.name_already_exists));
                    name.requestFocus();
                    return;

                }
            }
        }

        /*Integer childId = (savedChild == null) ? null : savedChild.getId();
        if (childId != null) {
            if (childId != child.getId()) {
                name.setError(getString(R.string.name_already_exists));
                name.requestFocus();
                return;
            }
            Calendar c = Calendar.getInstance();
            c.set(bY, bM - 1, bD);
            subtitle = null;
            if (!SMIConstants.d.format(c.getTime().getTime()).equalsIgnoreCase(SMIConstants.d.format(child.getDob()))) {
                child.setDob(c.getTime());
                subtitle = getString(R.string.data_will_lost_dueto_dob_change);
            }
            if (!child.getSex().equalsIgnoreCase((male.isChecked()) ? "Male" : "Female")) {
                child.setSex((male.isChecked()) ? "Male" : "Female");
                subtitle = getString(R.string.data_will_lost_dueto_sex_change);
            }

            new AlertDialog.Builder(view.getContext())
                    .setTitle(getString(R.string.update_child) + " " + name.getText().toString())
                    .setMessage(subtitle)
                    .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            editChild();
                            if (subtitle != null) {
                                mViewModel.deleteData(child.getId());
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    }).create().show();

        } else { //add
            child = new Child(regNo.getText().toString(), name.getText().toString(), (male.isChecked()) ? "Male" : "Female",
                    dob.getTime(), maturity, guard.getText().toString(), tp.getText().toString(), note.getText().toString(), fht, mht);
            new AlertDialog.Builder(view.getContext())
                    .setTitle(R.string.add_new_child)
                    .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Logger.setMsg("before Inserted " + child);
                            int newId = 0;
                            try {
                                newId = mViewModel.insert(child);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Logger.setMsg("after Inserted " + child);
                            clear();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    }).create().show();
        }*/
    }

    private void updateChild() {
        Calendar c = Calendar.getInstance();
        c.set(bY, bM - 1, bD);
        subtitle = null;
        if (!SMIConstants.d.format(c.getTime().getTime()).equalsIgnoreCase(SMIConstants.d.format(child.getDob()))) {
            child.setDob(c.getTime());
            subtitle = getString(R.string.data_will_lost_dueto_dob_change);
        }
        if (!child.getSex().equalsIgnoreCase((male.isChecked()) ? "Male" : "Female")) {
            child.setSex((male.isChecked()) ? "Male" : "Female");
            subtitle = getString(R.string.data_will_lost_dueto_sex_change);
        }

        new AlertDialog.Builder(view.getContext())
                .setTitle(getString(R.string.update_child) + " " + name.getText().toString())
                .setMessage(subtitle)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        child.setRegNo(regNo.getText().toString());
                        child.setName(name.getText().toString());
                        child.setGuardian(guard.getText().toString());
                        child.setTp(tp.getText().toString());
                        child.setNote(note.getText().toString());
                        try {
                            child.setMaturity(Integer.parseInt(txtMaturity.getText().toString()));
                            child.setFht(Double.parseDouble(txtFHt.getText().toString()));
                            child.setMht(Double.parseDouble(txtMHt.getText().toString()));
                        } catch (NumberFormatException ignored) {
                        }
                        mViewModel.update(child);
                        clear();
                        Snackbar.make(view, R.string.success_data_change, Snackbar.LENGTH_LONG).show();
                        if (subtitle != null) {
                            mViewModel.deleteData(child.getId());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        return;
                    }
                }).create().show();


    }
    /*private void editChild() {
        child.setRegNo(regNo.getText().toString());
        child.setName(name.getText().toString());
        child.setGuardian(guard.getText().toString());
        child.setTp(tp.getText().toString());
        child.setNote(note.getText().toString());
        try {
            child.setMaturity(Integer.parseInt(txtMaturity.getText().toString()));
            child.setFht(Double.parseDouble(txtFHt.getText().toString()));
            child.setMht(Double.parseDouble(txtMHt.getText().toString()));
        } catch (NumberFormatException ignored) {
        }
        mViewModel.update(child);
        clear();
        Snackbar.make(view, R.string.success_data_change, Snackbar.LENGTH_LONG).show();
    }*/

    private void deleteChild() {
        if (child == null) {
            Snackbar.make(view, R.string.select_child, Snackbar.LENGTH_LONG).show();
            return;
        }
        Calendar c = Calendar.getInstance();
        c.set(bY, bM - 1, bD);
        child.setRegNo(regNo.getText().toString());
        child.setName(name.getText().toString());
        child.setSex((male.isChecked()) ? "Male" : "Female");
        child.setDob(c.getTime());
        //, null,
        child.setGuardian(guard.getText().toString());
        child.setTp(tp.getText().toString());
        child.setNote(note.getText().toString());
        mViewModel.delete(child);
        clear();
        Snackbar.make(view, R.string.child_deleted, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.child_reg_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        InputMethodManager imm = (InputMethodManager) HomePage.application.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        switch (id) {
            case R.id.save:
                saveChild();
                break;
            case R.id.delete:
                deleteChild();
                break;
            case R.id.clear:
                clear();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayChild(Child child) {
        if (child == null) return;
        regNo.setText(child.getRegNo());
        if (child.getSex().equalsIgnoreCase("Male")) male.setChecked(true);
        else female.setChecked(true);

        Date dob = child.getDob();
        Calendar c = Calendar.getInstance();
        c.setTime(dob);
        yy.setText(String.valueOf(c.get(Calendar.YEAR)));
        mm.setText(String.valueOf(1 + c.get(Calendar.MONTH)));
        dd.setText(String.valueOf(c.get(Calendar.DATE)));
        name.setText(child.getName());
        txtMaturity.setText(String.valueOf((child.getMaturity() == 0) ? "" : child.getMaturity()));
        guard.setText(child.getGuardian());
        tp.setText(child.getTp());
        note.setText(child.getNote());
        txtFHt.setText(String.valueOf((child.getFht() == 0) ? "" : child.getFht()));
        txtMHt.setText(String.valueOf((child.getMht() == 0) ? "" : child.getMht()));
    }

    private void clear() {
        regNo.setText(null);
        name.setText(null);
        guard.setText(null);
        tp.setText(null);
        yy.setText(null);
        mm.setText(null);
        dd.setText(null);
        txtMaturity.setText(null);
        sexgp.clearCheck();
        note.setText(null);
        txtFHt.setText(null);
        txtMHt.setText(null);
        regNo.requestFocus();
    }
}