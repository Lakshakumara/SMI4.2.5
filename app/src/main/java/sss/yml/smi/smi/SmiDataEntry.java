package sss.yml.smi.smi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.chdr.ChartShow;
import sss.yml.smi.gt.AnalyseStudent;
import sss.yml.smi.room.Student;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;

public class SmiDataEntry extends Fragment {
    private Student student;
    private boolean isStudentUpdate;
    private TextInputEditText txtGrade, yy, mm, dd, txtHt, txtWt;
    private RadioButton male, female;
    private final DecimalFormat f = new DecimalFormat("##.000");
    private int heightLength = 5;
    private int weightLength = 5;
    private TextView txtStunting, txtWasted, txtOW, txtObesity, txtHtNor, txtWtNor, txtTotal;
    private final Calendar measureDate = Calendar.getInstance();
    private View view;
    private SmiDataEntryViewModel mViewModel;
    private int censusNo;
    private int year;
    private Spinner gradeList;
    private String gradeSelected = "%";
    public static SmiDataEntry newInstance() {
        return new SmiDataEntry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.smi_data_entry_summary_fragment, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(SmiDataEntryViewModel.class);

        txtGrade = view.findViewById(R.id.grade);
        yy = view.findViewById(R.id.yy);
        mm = view.findViewById(R.id.mm);
        dd = view.findViewById(R.id.dd);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view -> calAction());
        txtHt = view.findViewById(R.id.txtHt);
        txtWt = view.findViewById(R.id.txtWt);

        male = view.findViewById(R.id.rbM);
        female = view.findViewById(R.id.rbF);
        yy.setError(null);
        mm.setError(null);
        dd.setError(null);
        yy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4) {
                    try {
                        int years = Integer.parseInt(s.toString());
                        if (years < 1900 || years > 2099) {
                            Snackbar.make(view, getString(R.string.dob_year_wrong), Snackbar.LENGTH_SHORT).show();
                            yy.selectAll();
                            yy.requestFocus();
                            return;
                        }
                        if (years > measureDate.get(Calendar.YEAR)) {
                            Snackbar.make(view, getString(R.string.dob_year_wrong_early) + " " + getMeasureDateString(), Snackbar.LENGTH_SHORT).show();
                            yy.selectAll();
                            yy.requestFocus();
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        Snackbar.make(view, getString(R.string.error), Snackbar.LENGTH_SHORT).show();
                        yy.selectAll();
                        yy.requestFocus();
                        return;
                    }
                    mm.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    int month = Integer.parseInt(s.toString());
                    if (month < 1 || month > 12) {
                        mm.selectAll();
                        mm.requestFocus();
                        return;
                    }
                    dd.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 1){
                    int month = Integer.parseInt(s.toString());
                    if (month > 2) dd.requestFocus();
                }
            }
        });
        dd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    int days = Integer.parseInt(s.toString());
                    if (days < 1 || days > 31) {
                        dd.requestFocus();
                        return;
                    }
                    txtHt.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 1){
                    int dd = Integer.parseInt(s.toString());
                    if (dd > 3) txtHt.requestFocus();
                }
            }
        });
        txtHt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double ht;
                if (s.length() == 0) return;
                try {
                    ht = Double.parseDouble(s.toString());
                    if (s.length() >= 3) {
                        if (ht < 40 || ht > 240) {
                            txtHt.selectAll();
                            txtHt.requestFocus();
                            return;
                        }
                    }
                    if (s.length() == heightLength) {
                        txtWt.requestFocus();
                    }
                } catch (NumberFormatException ignored) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtHt.setOnEditorActionListener((v, actionId, event) -> {
            txtWt.requestFocus();
            return true;
        });
        txtWt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= weightLength) {
                    double wt = Double.parseDouble(s.toString());
                    if (wt < 12 || wt > 500) {
                        Toast.makeText(getContext(), getString(R.string.wtMaxError), Toast.LENGTH_SHORT).show();
                        //.make(view, getString(R.string.wtMaxError), Snackbar.LENGTH_SHORT).show();
                        txtWt.selectAll();
                        txtWt.requestFocus();
                        //txtWt.setError(getString(R.string.wtMaxError));
                        return;
                    }
                    calAction();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtWt.setOnEditorActionListener((v, actionId, event) -> calAction());

        view.findViewById(R.id.summary_table_layout);
        txtStunting = view.findViewById(R.id.nos_stunting);
        txtWasted = view.findViewById(R.id.nos_wasted);
        txtOW = view.findViewById(R.id.nos_ow);
        txtObesity = view.findViewById(R.id.nos_ob);
        txtHtNor = view.findViewById(R.id.nos_ht_normal);
        txtWtNor = view.findViewById(R.id.nos_wt_normal);
        txtTotal = view.findViewById(R.id.nos_total);
        txtTotal.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtTotal.setSingleLine(true);
        txtTotal.setSelected(true);
        txtTotal.setMarqueeRepeatLimit(-1);

        gradeList =  view.findViewById(R.id.gradeList);

        gradeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeSelected = gradeList.getSelectedItem().toString();
                int year = Calendar.getInstance().get(Calendar.YEAR)-1900;
                loadStudentSummary(year);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gradeSelected= "%";
            }
        });

        if (getArguments() != null){
            censusNo = SmiDataEntryArgs.fromBundle(getArguments()).getCensusNo();
            ((HomePage) getActivity()) .setActionBarTitle(SmiDataEntryArgs.fromBundle(getArguments()).getSchool());
            year = Calendar.getInstance().get(Calendar.YEAR)-1900;

            mViewModel.getGradeList(censusNo, year).observe(getViewLifecycleOwner(), grades ->
                    gradeList.setAdapter(new ArrayAdapter<>(requireContext(),
                    R.layout.list_item, grades)));
            Student s = SmiDataEntryArgs.fromBundle(getArguments()).getStudent();
            if(s !=null){
                isStudentUpdate = true;
                student = s;
                fillStudentData();
            }else isStudentUpdate = false;
        }
        return view;
    }

    private void fillStudentData() {
        txtGrade.setText(student.getGrade());
        if(student.getSex().equalsIgnoreCase(SMIConstants.MALE)) male.setChecked(true);
        else female.setChecked(true);
        Calendar c = Calendar.getInstance();
        c.setTime(student.getDob());
        yy.setText(String.valueOf(c.get(Calendar.YEAR)));
        mm.setText(String.valueOf(c.get(Calendar.MONTH)+1));
        dd.setText(String.valueOf(c.get(Calendar.DATE)));
        txtHt.setText(String.valueOf(student.getHeight()));
        txtWt.setText(String.valueOf(student.getWeight()));
        Date md = student.getMdate();
        measureDate.setTime(md);
    }

    private void loadStudentSummary(int year) {
        mViewModel.getStudentList(censusNo, year, gradeSelected, 0).observe(getViewLifecycleOwner(), this::fillSummary);
    }

    private void fillSummary(List<Student> students) {
        int sTot = students.size();
        if(sTot <= 0) sTot = 1;
        int nosStunting = 0;
        int nosHtNormal = 0;
        int nosHBothNormal = 0;

        int nosWasted = 0;
        int nosWtNormal = 0;
        int nosOverWeight = 0;
        int nosObese = 0;
        for (Student student:students ) {
            String wPos = student.getWtPos();
            boolean isNormal = false;
            if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD4neg.toString())) nosWasted++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD3neg.toString())) nosWasted++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD2neg.toString())) nosWasted++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD1neg.toString())) {nosWtNormal++;isNormal = true;}
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD0.toString())) {nosWtNormal++;isNormal = true;}
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD1.toString())) {nosWtNormal++;isNormal = true;}
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD2.toString())) nosOverWeight++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD3.toString())) nosObese++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD4.toString())) nosObese++;
            else if(wPos.equalsIgnoreCase(SMIConstants .ChartArea.SD5.toString())) nosObese++;

            String hPos = student.getHtPos();
            if(hPos.equalsIgnoreCase(SMIConstants .ChartArea.SD4neg.toString())) nosStunting++;
            else if(hPos.equalsIgnoreCase(SMIConstants .ChartArea.SD3neg.toString())) nosStunting++;
            else if(hPos.equalsIgnoreCase(SMIConstants .ChartArea.SD2neg.toString())) nosStunting++;
            else {
                nosHtNormal++;
                if(isNormal) nosHBothNormal++;
            }
        }
        txtStunting.setText(String.valueOf(nosStunting));
        txtHtNor.setText(String.valueOf(nosHtNormal));
        txtWasted.setText(String.valueOf(nosWasted));
        txtOW.setText(String.valueOf(nosOverWeight));
        txtObesity.setText(String.valueOf(nosObese));
        txtWtNor.setText(String.valueOf(nosWtNormal));
        String tot = "Total "+sTot+"; "+"Healthy "+(nosHBothNormal)*100/sTot+"%"+"==> "+"S-"+nosStunting*100/sTot+"%"+" "+"W-"+nosWasted*100/sTot+"%"+" "+"OW-"+nosOverWeight*100/sTot+"%"+" "+"OB-"+nosObese*100/sTot+"%";
        txtTotal.setText(tot);
        if(!TextUtils.isEmpty(txtGrade.getText())
                && !txtGrade.getText().toString().equalsIgnoreCase(gradeList.getSelectedItem().toString())){
            gradeSelected = txtGrade.getText().toString().toUpperCase();
            selectValue(gradeList, gradeSelected);
        }

    }
    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.smi_data_entry, menu);
        //PopupMenu pp = new PopupMenu(requireContext(), inflater.inflate(R.menu.smi_sub_menu), menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.auto:
                setAuto();
                break;
            case R.id.sch_measure_date:
                showChangeMeasureDate();
                break;
            case R.id.showData:
                if(gradeList.getSelectedItem() == null) break;
                String grade = gradeList.getSelectedItem().toString();
                //Navigation.findNavController(view).navigate(R.id.SMIData);
                NavDirections navDirections = SmiDataEntryDirections.actionSmiToSMIData(censusNo, grade, year);
                Navigation.findNavController(view).navigate(navDirections);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChangeMeasureDate() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.date_change, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(getString(R.string.change_smi_date));
        CalendarView cv = dialogView.findViewById(R.id.calendarView);
        cv.setDate(measureDate.getTimeInMillis());
        cv.setMaxDate(new java.util.Date().getTime());
        //if (childData != null) cv.setMinDate(childData.child.getDob().getTime());
        cv.setOnDateChangeListener((calendarView, i, i1, i2) -> measureDate.set(i, i1, i2));
        dialogBuilder.setPositiveButton(getString(R.string.change), (dialog, whichButton) -> isMeasureDateChange());
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private void isMeasureDateChange() {
        Calendar now = Calendar.getInstance();
        boolean sameDay = measureDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) &&
                measureDate.get(Calendar.YEAR) == now.get(Calendar.YEAR);
        if (!sameDay) {
            Snackbar.make(view, "SMI date changed to " + getMeasureDateString(), Snackbar.LENGTH_LONG).show();
        }
    }
    private String getMeasureDateString() {
        return SMIConstants.d.format(measureDate.getTime());
    }

    private void clear() {
        mm.setText(null);
        dd.setText(null);
        txtHt.setText(null);
        txtWt.setText(null);
    }

    private boolean calAction() {
        if (!calAge()) return false;
        if (!calBMI()) return false;
        AnalyseStudent sr = new AnalyseStudent(getContext(), student);
        student.setWtStatus(sr.getWtSummary());
        student.setWtColor(AnalyseStudent.getWtColor());
        student.setHtStatus(sr.getHtSummary());
        student.setHtColor(AnalyseStudent.getHtColor());
        //Logger.setMsg("sr.getWtPos()", sr.getWtPos());
        student.setWtPos(sr.getWtPos());
        //Logger.setMsg("sr.getHtPos()", sr.getHtPos());
        student.setHtPos(sr.getHtPos());
        DataTraveller.wtCode = 1;
        DataTraveller.htCode = 1;
        showResult();
        return true;
    }

    private boolean calBMI() {
        if (Objects.requireNonNull(txtHt.getText()).toString().isEmpty()) {
            txtHt.setError(getString(R.string.htRequired));
            txtHt.requestFocus();
            return false;
        }
        if (Objects.requireNonNull(txtWt.getText()).toString().isEmpty()) {
            txtWt.setError(getString(R.string.wtRequired));
            txtWt.requestFocus();
            return false;
        }
        final double ht = Double.parseDouble(txtHt.getText().toString());
        final double wt = Double.parseDouble(txtWt.getText().toString());
        if (ht == 0) {
            txtHt.setError(getString(R.string.htErrorZero));
            txtHt.requestFocus();
            return false;
        }
        if (ht < 45) {
            txtHt.setError(getString(R.string.htMinZero));
            txtHt.requestFocus();
            return false;
        }
        if (ht > 211) {
            txtHt.setError(getString(R.string.htMaxError));
            txtHt.requestFocus();
            return false;
        }

        if (wt == 0) {
            txtWt.setError(getString(R.string.wtErrorZero));
            txtWt.requestFocus();
            return false;
        }
        if (wt < 1.7) {
            txtWt.setError(getString(R.string.wtMinZero));
            txtWt.requestFocus();
            return false;
        }
        if (wt > 501) {
            txtWt.setError(getString(R.string.wtMaxError));
            txtWt.requestFocus();
            return false;
        }
        double bmiCal = Double.parseDouble(f.format(wt / (ht * ht / 10000)));

        if (bmiCal < 10) {
            txtWt.setError(getString(R.string.bmiMinError));
            txtWt.selectAll();
            txtWt.requestFocus();
            return false;
        }
        if (bmiCal > 50) {
            txtWt.setError(getString(R.string.bmiMaxError));
            txtWt.requestFocus();
            return false;
        }
        if (!male.isChecked() && !female.isChecked()) {
            male.setError(getString(R.string.enter_sex));
            male.requestFocus();
            return false;
        }
        student.setSex((male.isChecked()) ? SMIConstants.MALE : SMIConstants.FEMALE);
        student.setBmi(bmiCal);
        student.setHeight(ht);
        student.setWeight(wt);
        return true;
    }

    private String getAge() {
        return student.getAgeY() + getString(R.string.y) + student.getAgeM() + getString(R.string.m) + student.getAgeD() + getString(R.string.d);
    }

    private boolean calAge() {
        int dobY;
        int dobM;
        int dobD;
        try {
            dobY = Integer.parseInt(Objects.requireNonNull(yy.getText()).toString());
            dobM = Integer.parseInt(Objects.requireNonNull(mm.getText()).toString()) - 1;
            dobD = Integer.parseInt(Objects.requireNonNull(dd.getText()).toString());
        } catch (NumberFormatException ex) {
            return false;
        }
        if (dobY == 0 && dobM == 0 && dobD == 0)  return false;
        if (dobY < 1900 || dobY > 2099)  return false;
        Calendar birthDay = Calendar.getInstance();
        try {
            birthDay.set(dobY, dobM, dobD);
        } catch (Exception ex) {
            return false;
        }

        int years;
        int months;
        int days;
        years = measureDate.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

        int diff = (birthDay.get(Calendar.MONTH) == 0)?4:5;
        int gradeX = years - diff;
        String grade = (gradeX < 1 || gradeX > 13)?"": String.valueOf(gradeX);
        if(TextUtils.isEmpty(txtGrade.getText())) txtGrade.setText(grade);
        int currMonth = measureDate.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        long ageDays = measureDate.getTime().getTime() - birthDay.getTime().getTime();
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        } else if (months > 0 && measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            months--;
        }
        //Calculate the days
        if (measureDate.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = measureDate.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (measureDate.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            Calendar td = Calendar.getInstance();
            td.setTimeInMillis(measureDate.getTimeInMillis());
            int today = td.get(Calendar.DAY_OF_MONTH);
            td.add(Calendar.MONTH, -1);
            days = td.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay
                    .get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        if (years < 0 || years > 120) return false;
        //long dd = TimeUnit.DAYS.convert(ageDays, TimeUnit.MILLISECONDS);
        if (years < 5 || years >= 19) {
            yy.setError(getString(R.string.sch_age_group_error));
            yy.selectAll();
            showToast(getString(R.string.sch_age_group_error));
            return false;
        }
        student = (isStudentUpdate)?student:new Student();
        student.setCensusNo(censusNo);
        student.setDob(birthDay.getTime());
        student.setMdate(measureDate.getTime());
        student.setSex(male.isChecked() ? SMIConstants.MALE : SMIConstants.FEMALE);
        student.setAgeY(years);
        student.setAgeM(months);
        student.setAgeD(days);
        student.setGrade(txtGrade.getText().toString().trim().toUpperCase());
        return true;
    }

    private void showResult() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_short_popup, null);
        TextView txtAge = dialogView.findViewById(R.id.txtAgeNote);
        txtAge.setSelected(true);
        txtAge.setText(getAge());
        TextView txtBmi = dialogView.findViewById(R.id.txtBMINote);

        if (student.getAgeY() >= 2) txtBmi.setText(f.format(student.getBmi()));
        else txtBmi.setText(getString(R.string.bmiError));

        TextView wtPop = dialogView.findViewById(R.id.txtWtNote);
        wtPop.setSelected(true);
        wtPop.setText(student.getWtStatus());
        wtPop.setBackgroundColor(student.getWtColor());
        TextView htPop = dialogView.findViewById(R.id.txtHtNote);
        htPop.setSelected(true);
        htPop.setText(student.getHtStatus());
        htPop.setBackgroundColor(student.getHtColor());

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.summary);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        final TextInputEditText name = dialogView.findViewById(R.id.student_name);

        if (isStudentUpdate) {
            name.setText(student.getUserName());
            dialogBuilder.setPositiveButton(R.string.update, (dialog, whichButton) -> {
                updateDatabase(Objects.requireNonNull(name.getText()).toString());
                clear();
                isStudentUpdate = false;
                Navigation.findNavController(view).popBackStack();
            });
        }else
            dialogBuilder.setPositiveButton(R.string.add_pop, (dialog, whichButton) -> {
            addToDatabase(Objects.requireNonNull(name.getText()).toString());
            clear();
            mm.requestFocus();
        });

        dialogBuilder.setNegativeButton(R.string.try_again_pop, (dialog, whichButton) -> yy.requestFocus());
        dialogBuilder.setNeutralButton(R.string.chart, (dialog, whichButton) -> showGraphs());
        dialogBuilder.create();
        dialogBuilder.show();
    }

    private void addToDatabase(String name) {
        student.setUserName(name);
        try {
            if(mViewModel.insert(student) > 0)
                Snackbar.make(view, "Added to Database", Snackbar.LENGTH_SHORT).show();
            clear();
        }catch (ExecutionException | InterruptedException ignored){}
    }
    private void updateDatabase(String name) {
        student.setUserName(name);
        mViewModel.update(student);
        Snackbar.make(view, "Database Updated", Snackbar.LENGTH_SHORT).show();
        clear();
    }

    private void setAuto() {
        if(heightLength == 5 && weightLength == 5){
            heightLength = 3;
            weightLength = 2;
            Snackbar.make(view, "Auto Mode On", Snackbar.LENGTH_SHORT).show();
        }else{
            heightLength = 5;
            weightLength = 5;
            Snackbar.make(view, "Auto Mode Off", Snackbar.LENGTH_SHORT).show();
        }
    }
    private void showGraphs() {
        if (student == null) return;
        Intent i = new Intent(getContext(), ChartShow.class);
        i.putExtra("student", student);
        startActivity(i);
    }
}