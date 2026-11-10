package sss.yml.smi.person;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.Home.HomeView;
import sss.yml.smi.R;
import sss.yml.smi.chdr.Adult;
import sss.yml.smi.chdr.BFA_5_19;
import sss.yml.smi.chdr.HFA_5_19;
import sss.yml.smi.chdr.LHFA_0_5;
import sss.yml.smi.chdr.WFA_0_5;
import sss.yml.smi.chdr.WFH_0_5;
import sss.yml.smi.cloud.CloudAccess;
import sss.yml.smi.gt.AnalyseChild;
import sss.yml.smi.gt.AnalyseStudent;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.Child;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.room.Elder;
import sss.yml.smi.room.Student;
import sss.yml.smi.ui.child.History;
import sss.yml.smi.util.CalAge;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.Logger;
import sss.yml.smi.util.SMIConstants;
import sss.yml.smi.util.Util;
import sss.yml.smi.util.ZoomOutPageTransformer;

public class General extends Fragment implements History.SingleClickListener {
    private double calculatedWt = 0;
    private double calculatedHt = 0;
    private boolean mVisible = false;
    private View view;
    private ViewPager mViewPager;
    private boolean graphics = true;
    private AutoCompleteTextView childName;
    private ArrayAdapter<String> adapter;
    private TextInputLayout wtLay, htLay, calCmLay;
    private TextInputEditText txtWt, txtHt, yy, mm, dd;
    private Spinner sex;
    private Chip chip;
    private ChildData childData;
    private ChildWpData childWpData;
    private ChildWpData updateData;
    private final Student student = new Student();
    private Elder elder;
    private final Calendar measureDate = Calendar.getInstance();
    private final DecimalFormat f = new DecimalFormat("##0.000");
    private PagerAdapter pagerAdapter;
    private GeneralViewModel mViewModel;
    private final boolean weighingPost = false;
    private ConstraintLayout entryLay;
    private ViewStub vStub;
    private View vStubView;

    private enum CHOICE {
        NONE, ADD, UPDATE, DELETE
    }

    private enum AgeCat {
        UNDEFINE, BABY, STUDENT, ELDER
    }

    private AgeCat ageCat = AgeCat.UNDEFINE;

    public static General newInstance() {
        return new General();
    }

    public static float roundLowestHalf(double number) {
        return (number - (int) number < 0.5) ? (int) number : (int) number + 0.5f;
    }

    @Override
    public void onDestroyView() {
        if (mVisible) hide();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (weighingPost) ? inflater.inflate(R.layout.child_data_entry_new, container, false)
                : inflater.inflate(R.layout.general_fragment, container, false);
        setHasOptionsMenu(true);

        childName = view.findViewById(R.id.wp_child_name);

        childName.setOnItemClickListener((parent, view, position, id) -> {
            childName.requestFocus(View.FOCUS_DOWN);
            isChild();
        });

        entryLay = view.findViewById(R.id.entry_layout);
        wtLay = view.findViewById(R.id.wp_wt_lay);
        htLay = view.findViewById(R.id.wp_ht_lay);
        htLay.setEndIconDrawable(R.drawable.ic_height);
        txtWt = view.findViewById(R.id.wp_txtWt);
        txtWt.setOnClickListener(v -> {
            getChipEvent();
            txtHt.requestFocus();
        });
        txtWt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (updateData == null) return;
                try {
                    if (updateData.getWt() != Double.parseDouble(s.toString()))
                        chip.setText(R.string.update);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        txtHt = view.findViewById(R.id.wp_txtHt);
        txtHt.setOnClickListener(v -> getChipEvent());
        txtHt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (updateData == null) return;
                try {
                    if (updateData.getHt() != Double.parseDouble(s.toString()))
                        chip.setText(R.string.update);
                } catch (NumberFormatException ignored) {
                }
            }
        });
        htLay.setEndIconOnClickListener(v -> {
            //if (childdata == null) return;
            //else calAge();
            setHLHint();
        });
        vStub = view.findViewById(R.id.viewStub);

        mViewModel = new ViewModelProvider(this).get(GeneralViewModel.class);
        mViewModel.getStringList("childname").observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> nameList) {
                adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, nameList);
                childName.setAdapter(adapter);
            }
        });
        chip = view.findViewById(R.id.wp_chip);
        chip.setOnClickListener(v -> {
            if(HomePage.progress != null) HomePage.progress.show();
            isChild();
            checkStatus();
            if(HomePage.progress.isShowing()) HomePage.progress.dismiss();
        });
        chip.setOnCloseIconClickListener(v -> clear());

        if (!weighingPost) {
            yy = view.findViewById(R.id.yy);
            mm = view.findViewById(R.id.mm);
            dd = view.findViewById(R.id.dd);
            sex = view.findViewById(R.id.sex);
            childName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int length = s.length();
                    if (length == 0 || !Character.isDigit(s.charAt(0))) return;
                    switch (length) {
                        case 2:
                            if (s.charAt(0) != '1' || s.charAt(0) != '2') {
                                yy.setText("19" + s);
                                mm.setText(null);
                                dd.setText(null);
                                sex.setSelection(0);
                            }

                            break;
                        case 3:
                            if (s.charAt(0) == '1' || s.charAt(0) == '2') yy.setText(s);
                            else {
                                int a = Character.getNumericValue(s.charAt(2));
                                if (a <= 3) {
                                    sex.setSelection(1);
                                } else if (a >= 5 && a <= 8) {
                                    sex.setSelection(2);
                                } else {
                                    sex.setSelection(0);
                                }
                                yy.setText("19" + s);
                            }
                            mm.setText(null);
                            dd.setText(null);
                            break;
                        case 4:
                            if (s.charAt(0) == '1' || s.charAt(0) == '2') {
                                yy.setText(s);
                            } else yy.setText("19" + s);
                            mm.setText(null);
                            dd.setText(null);
                            break;
                        case 5:
                            if (s.charAt(0) != '1' || s.charAt(0) != '2') {
                                int year = Integer.parseInt(Objects.requireNonNull(yy.getText()).toString());
                                int dayOfYear = Character.getNumericValue(s.charAt(2)) * 100
                                        + Character.getNumericValue(s.charAt(3)) * 10
                                        + Character.getNumericValue(s.charAt(4));

                                GregorianCalendar cal = new GregorianCalendar();
                                dayOfYear =
                                        (dayOfYear >= 500) ? dayOfYear - 500 : dayOfYear; // remove Female part
                                if (dayOfYear >= 60) dayOfYear =
                                        (cal.isLeapYear(year)) ? dayOfYear : dayOfYear - 1; // remove Female part

                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.DAY_OF_YEAR, dayOfYear);
                                int months = c.get(Calendar.MONTH) + 1;
                                int date = c.get(Calendar.DATE);
                                mm.setText(String.valueOf(months));
                                dd.setText(String.valueOf(date));
                            }
                            break;
                        case 7:
                            int year = Integer.parseInt(Objects.requireNonNull(yy.getText()).toString());
                            int dayOfYear;
                            if (s.charAt(0) == '1' || s.charAt(0) == '2') {
                                dayOfYear = Character.getNumericValue(s.charAt(4)) * 100
                                        + Character.getNumericValue(s.charAt(5)) * 10
                                        + Character.getNumericValue(s.charAt(6));
                            } else {
                                return;
                            }
                            GregorianCalendar cal = new GregorianCalendar();
                            dayOfYear = (dayOfYear >= 500) ? dayOfYear - 500 : dayOfYear; // remove Female part
                            if (dayOfYear >= 60)
                                dayOfYear = (cal.isLeapYear(year)) ? dayOfYear : dayOfYear - 1; // remove Female part

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.YEAR, year);
                            c.set(Calendar.DAY_OF_YEAR, dayOfYear);
                            int months = c.get(Calendar.MONTH) + 1;
                            int date = c.get(Calendar.DATE);
                            mm.setText(String.valueOf(months));
                            dd.setText(String.valueOf(date));
                            break;
                    }
                }
            });
            /*yy.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 4) {
                        try {
                            int years = Integer.parseInt(s.toString());
                            if (years < 1900 || years > 2099) {
                                Snackbar.make(view, getString(R.string.dob_year_wrong_early), Snackbar.LENGTH_SHORT).show();
                                yy.selectAll();
                                yy.requestFocus();
                                return;
                            }
                            if (years > measureDate.get(Calendar.YEAR)) {
                                yy.setError(getString(R.string.dob_year_wrong));
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
                            dd.selectAll();
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
            });*/
        }
        return view;
    }

    private void setHLHint() {
       /* if (htLay.getPrefixText().toString().startsWith("H")) {
            htLay.setPrefixText("L ");
            htLay.setHelperText("Length" + ((childWpData.getAgeYY() < 2) ? "" : " - 0.7"));
            htLay.setEndIconContentDescription("Length");
            htLay.setEndIconDrawable(R.drawable.ic_length);
        } else {
            htLay.setPrefixText("H ");
            htLay.setHelperText("Height" + ((childWpData.getAgeYY() < 2) ? " + 0.7" : ""));
            htLay.setEndIconContentDescription("Height");
            htLay.setEndIconDrawable(R.drawable.ic_height);
        }*/

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.feet_inch, null);
        dialogBuilder.setView(dialogView);
        calCmLay = dialogView.findViewById(R.id.popCmLay);
        final TextInputEditText feet = dialogView.findViewById(R.id.popfeet);
        final TextInputEditText inches = dialogView.findViewById(R.id.popinches);
        final TextInputEditText calCm = dialogView.findViewById(R.id.popcm);
        feet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String txt = getCm(Objects.requireNonNull(feet.getText()), inches.getText());
                if (txt == null) feet.requestFocus();
                else calCm.setText(txt);
            }
        });
        inches.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calCm.setText(getCm(feet.getText(), inches.getText()));
            }
        });
        dialogBuilder.setTitle(getResources().getString(R.string.height_input_method));
        dialogBuilder.setPositiveButton(R.string.add, (dialog, whichButton) -> txtHt.setText(calCm.getText()));
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
            //pass
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private String getCm(Editable feet, Editable inches) {
        calCmLay.setErrorEnabled(true);
        double ft = 0;
        try {
            ft = Double.parseDouble(feet.toString());
        } catch (NumberFormatException e) {
            calCmLay.setError("Feet input Error");
            return null;
        }
        double in = 0;
        if(!TextUtils.isEmpty(inches)) {
            try {
                in = Double.parseDouble(inches.toString());
            } catch (NumberFormatException e) {
                calCmLay.setError("Inch input Error");
                return null;
            }
            if (in < 0 || in > 11) {
                calCmLay.setError("0 =< Inch >= 11");
                return null;
            }
        }
        calCmLay.setErrorEnabled(false);
        return f.format((1 / 0.39370) * (12 * ft + in));
    }

    private int getChipEvent() {
        int event = R.string.add;
        if (childData == null) {
            event = R.string.calculate;
        } else if (updateData != null) {
            if (updateData.equals(childWpData)) event = R.string.delete;
            else event = R.string.update;
        }
        chip.setText(event);
        return event;
    }

    private void checkStatus() {
        if (!isValidMea()) return;
        Calendar dob = getDOB();
        if (dob == null) {
            double bmi = Double.parseDouble(f.format(calculatedWt / (calculatedHt * calculatedHt / 10000)));
            elder = new Elder((float) bmi, false);
            analyseElder();
            return;
        }
        if (dob.after(measureDate)) {
            Snackbar.make(view, R.string.dob_future, Snackbar.LENGTH_SHORT).show();
            return;
        }
        CalAge age = new CalAge(dob, measureDate);
        if (age.getYears() > 19 && age.getYears() < 120) {
            ageCat = AgeCat.ELDER;
            elder = new Elder((float) getBMI(), age.getYears(), age.getMonths(), age.getDays());
            analyseElder();
            return;
        }
        String gender = getSex();
        if (gender == null){
            Snackbar.make(chip, R.string.sex_not_selected, Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (age.getYears() >= 5 && age.getYears() <= 19) {
            ageCat = AgeCat.STUDENT;
            analyseSchool(age);
        }else if (age.getYears() >= 0 && age.getYears() < 5) {
            ageCat = AgeCat.BABY;
             analyseUnder5yrs(age, gender);
        }
    }

    private void analyseElder() {
        /*final ChildWpData wpData;
        CHOICE event;
        Integer childId;
        if (childData == null) {
            event = CHOICE.NONE;
            wpData = new ChildWpData();
        } else {
            childId = childData.child.getId();
            if (childId == 0) {
                event = CHOICE.NONE;
                wpData = new ChildWpData();
            } else wpData = childData.childWpData.get(0);
            wpData.setWt(calculatedWt);
            wpData.setHt(calculatedHt);
            if (updateData != null) {
                if (updateData.equals(wpData)) event = CHOICE.DELETE;
                else event = CHOICE.UPDATE;
            } else {
                event = CHOICE.ADD;
                wpData.setChildId(childId);
            }
        }

        wpData.setmDate(measureDate.getTime());
        //wpData.setAgeDays(elder.getAgeDays());
        wpData.setAgeYY(elder.getAgeYY());
        wpData.setAgeMM(elder.getAgeMM());
        wpData.setAgeDD(elder.getAgeDD());
        wpData.setWt(calculatedWt);
        wpData.setHt(calculatedHt);
*/
        double bmi = getBMI();

        double wtDeviate = 0;
        String wtTitleShort = null;

        if (18.5f > bmi) {
            wtDeviate = 18.5 * (calculatedHt * calculatedHt) / 10000 - calculatedWt;
            wtTitleShort = requireContext().getString(R.string.Wasted_short) + " +"+wtDeviate;
        } else if (18.5f <= bmi && bmi < 25) {
            wtTitleShort = requireContext().getString(R.string.normal_wt);
        } else if (25f <= bmi && bmi < 30) {
            wtDeviate = 25 * (calculatedHt * calculatedHt) / 10000 - calculatedWt;
            wtTitleShort = requireContext().getString(R.string.overweight_short)+ " -"+wtDeviate;
        } else if (bmi >= 30) {
            wtDeviate = 25 * (calculatedHt * calculatedHt) / 10000 - calculatedWt;
            wtTitleShort = requireContext().getString(R.string.obesity_short)+ " -"+wtDeviate;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.elder_popup, null);
        TextView txtAge = dialogView.findViewById(R.id.txtAgeNote);
        if (elder.isDOB())
            txtAge.setText(getAgeText(elder.getAgeYY(), elder.getAgeMM(), elder.getAgeDD()));
        TextView txtBmi = dialogView.findViewById(R.id.txtBMINote);
        txtBmi.setText(f.format(getBMI()));

        TextView wtPop = dialogView.findViewById(R.id.txtWtNote);
        wtPop.setText(wtTitleShort);
        //wtPop.setBackgroundColor(student.getWtColor());

        TextInputEditText note = dialogView.findViewById(R.id.elder_note);
        note.setVisibility(View.GONE);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.summary);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        dialogBuilder.setPositiveButton(R.string.chart, (dialog, whichButton) -> show());
        dialogBuilder.setNegativeButton(R.string.clear, (dialog, whichButton) -> clear());
        dialogBuilder.create();
        dialogBuilder.show();

        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_child_new, null);

        String sqlDate = SMIConstants.d.format(wpData.getmDate());
        TableLayout tl = dialogView.findViewById(R.id.summary_table_layout);
        TextView txtWd = dialogView.findViewById(R.id.summary_wd);
        txtWd.setText(sqlDate);
        TextView txAge = dialogView.findViewById(R.id.summary_age);
        txAge.setText(getAgeText(wpData.getAgeYY(), wpData.getAgeMM(), wpData.getAgeDD()));

        TextView wfa_status = dialogView.findViewById(R.id.summary_wfa_status);
        TextView wfa_comment = dialogView.findViewById(R.id.summary_wfa_comment);
        TextView hfa_status = dialogView.findViewById(R.id.summary_hfa_status);
        TextView hfa_comment = dialogView.findViewById(R.id.summary_hfa_comment);
        TextView wfh_status = dialogView.findViewById(R.id.summary_wfh_status);
        TextView wfh_comment = dialogView.findViewById(R.id.summary_wfh_comment);
        TableRow row_hfa = dialogView.findViewById(R.id.row_hfa);
        TableRow row_wfh = dialogView.findViewById(R.id.row_wfh);
        float wtDeviate = ac.getWtDeviate();
        String wd = (wtDeviate == 0) ? "" : f.format(wtDeviate) + " " + getResources().getString(R.string.kg);
        wfa_status.setText(ac.getWtTitle());
        wfa_comment.setText(wd);
        wfa_status.setBackgroundColor(ac.getWtColor());


        final TextInputEditText note = dialogView.findViewById(R.id.sc_note);
        if (event == CHOICE.NONE) note.setVisibility(View.GONE);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        if (event == CHOICE.NONE) {
            dialogBuilder.setPositiveButton(R.string.calculate, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    updateData = null;
                    childdata = new ChildData();
                    childdata.child = new Child(sex, age.getBirthDay());
                    childdata.childWpData = Arrays.asList(wpData);
                    show();
                }
            });
        } else if (event == CHOICE.ADD) {
            dialogBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    wpData.setNote(note.getText().toString());
                    mViewModel.insert(wpData);
                    //clear();
                    updateData = null;
                }
            });
        } else if (event == CHOICE.UPDATE) {
            dialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    wpData.setNote(note.getText().toString());
                    mViewModel.update(wpData);
                    updateData = null;
                    clear();
                }
            });

        } else if (event == CHOICE.DELETE) {
            dialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    wpData.setNote(note.getText().toString());
                    mViewModel.delete(wpData);
                    updateData = null;
                }
            });
        }

        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateData = null;
            }
        });
        dialogBuilder.create();
        dialogBuilder.show();*/
    }

    private double getBMI() {
        return Double.parseDouble(f.format(calculatedWt / (calculatedHt * calculatedHt / 10000)));
    }

    private Calendar getDOB() {
        Calendar birthDay = null;
        if (childData != null) {
            birthDay = Calendar.getInstance();
            birthDay.setTime(childData.child.getDob());
            return birthDay;
        }
        int dobY;
        int dobM;
        int dobD;
        try {
            dobY = Integer.parseInt(Objects.requireNonNull(yy.getText()).toString());
            dobM = Integer.parseInt(Objects.requireNonNull(mm.getText()).toString()) - 1;
            dobD = Integer.parseInt(Objects.requireNonNull(dd.getText()).toString());
            birthDay = Calendar.getInstance();
            if (dobY < birthDay.get(Calendar.YEAR) - 120) {
                Snackbar.make(view, R.string.dob_year_error, Snackbar.LENGTH_SHORT).show();
                yy.requestFocus();
                return null;
            }
            if (dobM < 0 || dobM > 11) {
                Snackbar.make(view, R.string.dob_month_error, Snackbar.LENGTH_SHORT).show();
                mm.requestFocus();
                return null;
            }
            birthDay.set(Calendar.YEAR, dobY);
            birthDay.set(Calendar.MONTH, dobM);
            int maxDD = birthDay.getActualMaximum(Calendar.DATE);
            if (maxDD < dobD) {
                Snackbar.make(view, "Max Date is " + maxDD, Snackbar.LENGTH_SHORT).show();
                dd.requestFocus();
                return null;
            }
            birthDay.set(Calendar.DATE, dobD);
        } catch (NumberFormatException ex) {
            return birthDay;
        }
        return birthDay;
    }

    private void analyseSchool(CalAge age) {
        if (getSex() == null) {
            Toast.makeText(getContext(), R.string.sex_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        student.setDob(getDOB().getTime());
        student.setMdate(measureDate.getTime());
        student.setSex(getSex());
        student.setAgeY(age.getYears());
        student.setAgeM(age.getMonths());
        student.setAgeD(age.getDays());
        student.setWeight(calculatedWt);
        student.setHeight(calculatedHt);
        student.setBmi(getBMI());
        AnalyseStudent sr = new AnalyseStudent(getContext(), student);
        student.setWtStatus(sr.getWtSummary());
        student.setWtColor(AnalyseStudent.getWtColor());
        student.setHtStatus(sr.getHtSummary());
        student.setHtColor(AnalyseStudent.getHtColor());
        student.setWtPos(sr.getWtPos());
        student.setHtPos(sr.getHtPos());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_short_popup, null);
        TextView txtAge = dialogView.findViewById(R.id.txtAgeNote);
        txtAge.setText(getAgeText(age.getYears(), age.getMonths(), age.getDays()));
        TextView txtBmi = dialogView.findViewById(R.id.txtBMINote);

        txtBmi.setText(f.format(getBMI()));

        TextView wtPop = dialogView.findViewById(R.id.txtWtNote);
        wtPop.setText(student.getWtStatus());
        wtPop.setBackgroundColor(student.getWtColor());
        TextView htPop = dialogView.findViewById(R.id.txtHtNote);
        htPop.setText(student.getHtStatus());
        htPop.setBackgroundColor(student.getHtColor());
        TextInputEditText name = dialogView.findViewById(R.id.student_name);
        name.setVisibility(View.GONE);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.summary);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        dialogBuilder.setPositiveButton(R.string.chart, (dialog, whichButton) -> show());
        dialogBuilder.setNegativeButton(R.string.clear, (dialog, whichButton) -> clear());
        dialogBuilder.create();
        dialogBuilder.show();
    }

    private void analyseUnder5yrs(final CalAge age,String sex) {
        final ChildWpData wpData;
        CHOICE event;
        Integer childId;
        if (childData == null) {
            event = CHOICE.NONE;
            wpData = new ChildWpData();
        } else {
            childId = childData.child.getId();
            if (childId == 0) {
                event = CHOICE.NONE;
                wpData = new ChildWpData();
            } else
                wpData = (childData.childWpData.size() <= 0)?new ChildWpData():childData.childWpData.get(0);
            wpData.setWt(calculatedWt);
            wpData.setHt(calculatedHt);
            if (updateData != null) {
                if (updateData.equals(wpData)) event = CHOICE.DELETE;
                else event = CHOICE.UPDATE;
            } else {
                event = CHOICE.ADD;
                wpData.setChildId(childId);
            }
        }

        wpData.setmDate(measureDate.getTime());
        wpData.setAgeDays(age.getAgeDays());
        wpData.setAgeYY(age.getYears());
        wpData.setAgeMM(age.getMonths());
        wpData.setAgeDD(age.getDays());
        wpData.setWt(calculatedWt);
        wpData.setHt(calculatedHt);

        HashMap<String, Float> idealWeightMap;
        HashMap<String, Float> idealHeightMap;
        HashMap<String, Float> idealWFHMap;

        AnalyseChild ac = new AnalyseChild(Arrays.asList(wpData));
        idealWeightMap = SMIConstants.isMale(sex)
                ? DataTraveller.getWFA0_5_boys_z().getIdealData((int) wpData.getAgeDays())
                : DataTraveller.getWFA0_5_girls_z().getIdealData((int) wpData.getAgeDays());
        ac.setIdealWtMap(idealWeightMap);
        ac.setWt((float) wpData.getWt());

        DataTable dataTableMonth = SMIConstants.isMale(sex)
                ? DataTraveller.getWFA_boys_0_5_z_month()
                : DataTraveller.getWFA_girls_0_5_z_month();

        wpData.setWfaZ(Util.genZScore(dataTableMonth, wpData));
        if (wpData.getHt() > 0) {
            idealHeightMap = SMIConstants.isMale(sex)
                    ? DataTraveller.getLHFA_boys_z().getIdealData((int) wpData.getAgeDays())
                    : DataTraveller.getLHFA_girls_z().getIdealData((int) wpData.getAgeDays());
            ac.setIdealHtMap(idealHeightMap);

            if (wpData.getAgeYY() < 2) {
                idealWFHMap = SMIConstants.isMale(sex)
                        ? DataTraveller.getWFH_boys_0_2_z().getIdealData(roundLowestHalf(wpData.getHt()))
                        : DataTraveller.getWFH_girls_0_2_z().getIdealData(roundLowestHalf(wpData.getHt()));
            } else {
                idealWFHMap = SMIConstants.isMale(sex)
                        ? DataTraveller.getWFH_boys_2_5_z().getIdealData(roundLowestHalf(wpData.getHt()))
                        : DataTraveller.getWFH_girls_2_5_z().getIdealData(roundLowestHalf(wpData.getHt()));
            }
            ac.setIdealWFHMap(idealWFHMap);
            ac.setHt((float) wpData.getHt());
        }
        ac.generate();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_child_new, null);

        String sqlDate = SMIConstants.d.format(wpData.getmDate());
        TableLayout tl = dialogView.findViewById(R.id.summary_table_layout);
        TextView txtWd = dialogView.findViewById(R.id.summary_wd);
        txtWd.setText(sqlDate);
        TextView txAge = dialogView.findViewById(R.id.summary_age);
        txAge.setText(getAgeText(wpData.getAgeYY(), wpData.getAgeMM(), wpData.getAgeDD()));

        TextView wfa_status = dialogView.findViewById(R.id.summary_wfa_status);
        TextView wfa_comment = dialogView.findViewById(R.id.summary_wfa_comment);
        TextView hfa_status = dialogView.findViewById(R.id.summary_hfa_status);
        TextView hfa_comment = dialogView.findViewById(R.id.summary_hfa_comment);
        TextView wfh_status = dialogView.findViewById(R.id.summary_wfh_status);
        TextView wfh_comment = dialogView.findViewById(R.id.summary_wfh_comment);
        TableRow row_hfa = dialogView.findViewById(R.id.row_hfa);
        TableRow row_wfh = dialogView.findViewById(R.id.row_wfh);
        float wtDeviate = ac.getWtDeviate();
        String wd = (wtDeviate == 0) ? "" : f.format(wtDeviate) + " " + getResources().getString(R.string.kg);
        wfa_status.setText(ac.getWtTitle());
        wfa_comment.setText(wd);
        wfa_status.setBackgroundColor(ac.getWtColor());

        if (wpData.getHt() > 0) {
            float htDeviate = ac.getHtDeviate();
            String hd = (htDeviate == 0) ? "" : f.format(htDeviate) + " " + getResources().getString(R.string.cm);
            hfa_status.setText(ac.getHtTitle());
            hfa_comment.setText(hd);
            hfa_status.setBackgroundColor(ac.getHtColor());

            float wfhDeviate = ac.getWfhDeviate();
            String wfh = (wfhDeviate == 0) ? "" : f.format(wfhDeviate) + " " + getResources().getString(R.string.kg);
            wfh_status.setText(ac.getWfhTitle());
            wfh_comment.setText(wfh);
            wfh_status.setBackgroundColor(ac.getWfhColor());
        } else {
            tl.removeView(row_hfa);
            tl.removeView(row_wfh);
        }

        final TextInputEditText note = dialogView.findViewById(R.id.sc_note);
        if (event == CHOICE.NONE) note.setVisibility(View.GONE);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        if (event == CHOICE.NONE) {
            dialogBuilder.setPositiveButton(R.string.chart, (dialog, whichButton) -> {
                updateData = null;
                childData = new ChildData();
                childData.child = new Child(sex, age.getBirthDay());
                childData.childWpData = Arrays.asList(wpData);
                show();
            });
        } else if (event == CHOICE.ADD) {
            dialogBuilder.setPositiveButton(R.string.add, (dialog, whichButton) -> {
                wpData.setNote(note.getText().toString());
                mViewModel.insert(wpData);
                //clear();
                updateData = null;
            });
        } else if (event == CHOICE.UPDATE) {
            dialogBuilder.setPositiveButton(R.string.update, (dialog, whichButton) -> {
                wpData.setNote(note.getText().toString());
                mViewModel.update(wpData);
                updateData = null;
                clear();
            });

        } else if (event == CHOICE.DELETE) {
            dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
                wpData.setNote(note.getText().toString());
                mViewModel.delete(wpData);
                updateData = null;
            });
        }

        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> updateData = null);
        dialogBuilder.create();
        dialogBuilder.show();
    }

    private String getAgeText(int ageYY, int ageMM, int ageDD) {
        if (ageYY == 0 && ageMM == 0 && ageDD == 0) return getString(R.string.birth_weight);
        else if (ageYY == 0 && ageMM == 0) return ageDD + getString(R.string.d);
        else if (ageYY == 0) return ageMM + getString(R.string.m) + ageDD + getString(R.string.d);
        else return ageYY + getString(R.string.y) + ageMM + getString(R.string.m) + ageDD + getString(R.string.d);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.child_main_menu, menu);
        MenuItem item = menu.findItem(R.id.wp_chart);
        item.setIcon((graphics) ? R.drawable.ic_show_chart : R.drawable.ic_show_text);
        MenuItem itemS = menu.findItem(R.id.wp_child_share);
        itemS.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // isChild();
        switch (id) {
            case R.id.wp_child_add:
                int childId = (childData == null) ? 0 : (childData.child.getName().equalsIgnoreCase(childName.getText().toString())) ?
                        childData.child.getId() : 0;
                NavDirections navDirections = GeneralDirections.actionNavGeneralToChildReg().setChildId(childId);
                NavHostFragment.findNavController(General.this)
                        .navigate(navDirections);
                break;
            case R.id.wp_chart:
                graphics = !graphics;
                item.setIcon((graphics) ? R.drawable.ic_show_chart : R.drawable.ic_show_text);
                toggle();
                break;
            case R.id.wp_child_measure_date:
                showChangeMeasureDate();
                break;
            case R.id.wp_child_share:
                shareIt();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareIt() {
        if (childData == null) {
            Snackbar.make(view, R.string.select_child, Snackbar.LENGTH_SHORT).show();
            return;
        }
        CloudAccess.uploadTo(childData, CloudAccess.TO.PARENT);
    }

    private boolean isValidMea() {
        double wt;
        try {
            wt = Double.parseDouble(Objects.requireNonNull(txtWt.getText()).toString());
        } catch (NumberFormatException e) {
            msg(R.string.wtRequired);
            //Snackbar.make(view, R.string.wtRequired, Snackbar.LENGTH_SHORT).show();
            txtWt.requestFocus();
            return false;
        }

        if (wt == 0) {
            Snackbar.make(view, R.string.wtErrorZero, Snackbar.LENGTH_SHORT).show();
            txtWt.requestFocus();
            return false;
        }
        if (wt < 0.5) {
            Snackbar.make(view, R.string.wtMinZero, Snackbar.LENGTH_SHORT).show();
            txtWt.requestFocus();
            return false;
        }
        if (wt > 501) {
            Snackbar.make(view, R.string.wtMaxError, Snackbar.LENGTH_SHORT).show();
            txtWt.requestFocus();
            return false;
        }
        double ht;
        try {
            ht = Double.parseDouble(txtHt.getText().toString());
        } catch (NumberFormatException e) {
            Snackbar.make(view, R.string.error, Snackbar.LENGTH_SHORT).show();
            txtHt.requestFocus();
            return false;
        }
        if (ht > 0 && ht < 45) {
            Snackbar.make(view, R.string.htMinZero, Snackbar.LENGTH_SHORT).show();
            txtHt.requestFocus();
            return false;
        }
        if (ht > 210) {
            Snackbar.make(view, R.string.htMaxError, Snackbar.LENGTH_SHORT).show();
            txtHt.requestFocus();
            return false;
        }
        calculatedWt = wt;
        calculatedHt = ht;
        return true;
    }

    private String getSex() {
        return (sex.getSelectedItemPosition() == 1) ? "Male" :
                (sex.getSelectedItemPosition() == 2) ? "Female" : null;
    }

    private boolean calAge() {
        int years = 0;
        int months = 0;
        int days = 0;
        if (updateData != null) {
            updateData.setmDate(measureDate.getTime());
        }
        Calendar birthDay = Calendar.getInstance();
        if (childData != null) birthDay.setTime(childData.child.getDob());
        years = measureDate.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
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
        long dd = TimeUnit.DAYS.convert(ageDays, TimeUnit.MILLISECONDS);

        /*if (years < 5 || years >= 19) {
            yy.setError(getString(R.string.sch_age_group_error));
            yy.selectAll();
            showToast(getString(R.string.sch_age_group_error));
            return false;
        }*/

        childWpData = new ChildWpData();
        if (updateData != null) {
            childWpData.setId(updateData.getId());
            childWpData.setChildId(updateData.getChildId());
        }
        childWpData.setmDate(measureDate.getTime());
        childWpData.setAgeDays(Float.valueOf(dd));
        childWpData.setAgeYY(years);
        childWpData.setAgeMM(months);
        childWpData.setAgeDD(days);
        return true;
    }

    private void isChild() {
        String name = "";
        if (!weighingPost) {
            if (childName.getText() == null || childName.getText().toString().trim().isEmpty())
                return;
            name = childName.getText().toString();
        }
        mViewModel.getChild(name).observe(getViewLifecycleOwner(), nameList -> {
            if (nameList.size() == 0) {
                childData = null;
                updateData = null;
            } else {
                childData = nameList.get(0);
                Collections.sort(childData.childWpData, (o1, o2) -> o1.getmDate().compareTo(o2.getmDate()));

                updateData = (updateData != null && updateData.getChildId() != childData.child.getId()) ? null : updateData;
                getChipEvent();
                Calendar c = Calendar.getInstance();
                c.setTime(childData.child.getDob());
                yy.setText("" + c.get(Calendar.YEAR));
                mm.setText("" + (c.get(Calendar.MONTH) + 1));
                dd.setText("" + c.get(Calendar.DATE));
                sex.setSelection(childData.child.getSex().equalsIgnoreCase("Male") ? 1 : 2);
                //pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), nameList.get(0));
                //mViewPager.setAdapter(pagerAdapter);
                txtWt.requestFocus();
            }
        });
    }

    private void clear() {
        childName.setText(null);
        txtHt.setText(null);
        txtWt.setText(null);
        if (!weighingPost) {
            yy.setText(null);
            mm.setText(null);
            dd.setText(null);
        }
        sex.setSelection(0);
        childData = null;
        updateData = null;
        childWpData = null;
        measureDate.setTime(new java.util.Date());
        isMeasureDateChange();
        childName.requestFocus();
        childData = null;
        childWpData = null;
        updateData= null;
        elder = null;
        student.clear();
    }

    private void showChangeMeasureDate() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.date_change, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(getString(R.string.change_clinic_date));
        CalendarView cv = dialogView.findViewById(R.id.calendarView);
        cv.setDate(measureDate.getTimeInMillis());
        cv.setMaxDate(new java.util.Date().getTime());
        if (childData != null) {
            cv.setMinDate(childData.child.getDob().getTime());
        }
        cv.setOnDateChangeListener((calendarView, i, i1, i2) -> measureDate.set(i, i1, i2));
        dialogBuilder.setPositiveButton(R.string.change, (dialog, whichButton) -> isMeasureDateChange());
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
            //pass
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void isMeasureDateChange() {
        Calendar now = Calendar.getInstance();
        boolean sameDay = measureDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) &&
                measureDate.get(Calendar.YEAR) == now.get(Calendar.YEAR);
        if (sameDay) {
            TypedValue typedValue = new TypedValue();
            if (this.getActivity().getTheme().resolveAttribute(android.R.attr.windowBackground,
                    typedValue, true)){
                int colorWindowBackground = typedValue.data;// **just add this line to your code!!**
                wtLay.setBoxBackgroundColor(colorWindowBackground);
                htLay.setBoxBackgroundColor(colorWindowBackground);
            }
        } else {
            calAge();
            setHLHint();
            wtLay.setBoxBackgroundColor(Color.RED);
            htLay.setBoxBackgroundColor(Color.RED);
        }
    }

    private String getMeasureDateString() {
        return SMIConstants.d.format(measureDate.getTime());
    }

    private void hideKeyboard() {
        if (view != null) {
            ((InputMethodManager) HomePage.application.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClickListener(final ChildWpData childData) {
        updateData = childData;
        childWpData = childData;
        txtWt.setText("" + childData.getWt());
        txtHt.setText("" + childData.getHt());
        measureDate.setTime(updateData.getmDate());
        chip.setText(R.string.delete);
        txtWt.requestFocus();
    }

    private void toggle() {
        if (mVisible) hide();
        else show();
    }

    private void hide() {
        vStub.setVisibility(View.GONE);
        entryLay.setVisibility(View.VISIBLE);
        //ActionBar actionBar = ((HomePage) getActivity()).getSupportActionBar();
        //if (actionBar != null) actionBar.show();
        mVisible = false;
    }

    private void show() {
        //ActionBar actionBar = ((HomePage) getActivity()).getSupportActionBar();
        //if (actionBar != null) actionBar.hide();
        if (vStubView == null) {
            vStubView = vStub.inflate();
            mViewPager = vStubView.findViewById(R.id.stub_pager);
            mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            Logger.setMsg("vStubView == null");
        }
        vStub.setVisibility(View.VISIBLE);
        entryLay.setVisibility(View.GONE);
        switch (ageCat) {
            case BABY:
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), childData);
                break;
            case STUDENT:
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), student);
                break;
            case ELDER:
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), elder);
                break;
        }
        mViewPager.setAdapter(pagerAdapter);
        mVisible = true;
    }

    private void msg(@StringRes int resId){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.msg_text);
        MaterialTextView msg = bottomSheetDialog.findViewById(R.id.msg);
        msg.setText(resId);
        bottomSheetDialog.show();
    }
    private class WpChartPagerAdapter extends FragmentStatePagerAdapter {
        private ChildData child;
        private Student student;
        private Elder elder;
        private final int nosFragments;

        public WpChartPagerAdapter(FragmentManager fm, ChildData child) {
            super(fm);
            Logger.setMsg(" WpChartPagerAdapter(FragmentManager fm, ChildData child) ");
            this.child = child;
            this.nosFragments = (child == null) ? 1 : (child.childWpData.size() == 0) ? 1 : (graphics)
                    ? (child.childWpData.get(child.childWpData.size() - 1).getAgeYY() >= 2) ? 4 : 3
                    : 1;
        }

        public WpChartPagerAdapter(FragmentManager fm, Student student) {
            super(fm);
            this.student = student;
            this.nosFragments = 2;
        }

        public WpChartPagerAdapter(FragmentManager fm, Elder elder) {
            super(fm);
            this.elder = elder;
            this.nosFragments = 1;
        }


        @Override
        public Fragment getItem(int position) {
            if (ageCat == AgeCat.UNDEFINE) return new HomeView(HomeView.MODE.GMP);
            if (ageCat == AgeCat.ELDER) return new Adult(elder);
            if (!graphics) {
                History his = new History(child);
                his.setOnItemClickListener(General.this);
                return his;
            }
            if (ageCat == AgeCat.BABY) {
                Logger.setMsg("in AgeCat.BABY  "+position);
                if (position == 0) return new WFA_0_5(child);
                if (position == 1) return new LHFA_0_5(child);
                if (position == 2) return new WFH_0_5(child, true);
                if (position == 3) return new WFH_0_5(child, false);
            }
            if (ageCat == AgeCat.STUDENT) {
                if (position == 0) return new HFA_5_19(student);
                if (position == 1) return new BFA_5_19(student);
            }
            return new HomeView(HomeView.MODE.GMP);
        }

        @Override
        public int getCount() {
            return nosFragments;
        }
    }
}
