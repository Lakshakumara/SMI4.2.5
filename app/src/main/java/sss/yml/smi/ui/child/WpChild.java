package sss.yml.smi.ui.child;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import sss.yml.smi.Home.HomePage;
import sss.yml.smi.Home.HomeView;
import sss.yml.smi.R;
import sss.yml.smi.chdr.LHFA_0_5;
import sss.yml.smi.chdr.WFA_0_5;
import sss.yml.smi.chdr.WFH_0_5;
import sss.yml.smi.cloud.CloudAccess;
import sss.yml.smi.gt.AnalyseChild;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.util.AdLoader;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.Util;
import sss.yml.smi.util.ZoomOutPageTransformer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WpChild extends Fragment implements History.SingleClickListener {
    private static final int PERMISSION_REQUEST_STORAGE = 0;
    private View view;
    private ViewPager mViewPager;
    private boolean graphics = true;
    private AutoCompleteTextView childName;
    private ArrayAdapter<String> adapter;
    private TextInputLayout wtLay, htLay;
    private TextInputEditText txtWt, txtHt;
    private Chip chip;
    private ChildData childdata;
    private ChildWpData childWpData;
    private ChildWpData updateData;
    private final Calendar measureDate = Calendar.getInstance();
    private final DecimalFormat f = new DecimalFormat("##0.000");
    private PagerAdapter pagerAdapter;
    private WpChildViewModel mViewModel;
    private DataTable dataTableMonth;
    public static WpChild newInstance() {
        return new WpChild();
    }

    public static float roundLowestHalf(double number) {
        return (number - (int) number < 0.5) ? (int) number : (int) number + 0.5f;
    }
    private enum CHOICE{
        ADD,UPDATE,DELETE
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher =
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.child_data_entry_new, container, false);
        setHasOptionsMenu(true);
        ((HomePage) getActivity()) .setActionBarTitle(getString(R.string.wt_post));

        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Need Storage access permission to Save Charts. Please provide permission to access your Storage.")
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

        boolean adEnable = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("ad", true);
        AdView mAdView = view.findViewById(R.id.myAdView);
        if (adEnable) {
            MobileAds.initialize(requireContext(), initializationStatus -> {
                mAdView.loadAd(AdLoader.getRequest());
            });
        } else mAdView.setVisibility(View.GONE);

        childName = view.findViewById(R.id.wp_child_name);
        childName.setOnItemClickListener((parent, view, position, id) -> {
            InputMethodManager imm = (InputMethodManager) HomePage.application.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            isChild();
        });

        wtLay = view.findViewById(R.id.wp_wt_lay);
        htLay = view.findViewById(R.id.wp_ht_lay);
        htLay.setEndIconDrawable(R.drawable.ic_height);
        txtWt = view.findViewById(R.id.wp_txtWt);
        txtWt.setOnClickListener(v -> getChipEvent());
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
                } catch(NumberFormatException ignored){}
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
                } catch(NumberFormatException ignored){}
            }
        });
        htLay.setEndIconOnClickListener(v -> {
            if (childdata == null) return;
            else calAge();
            setHLHint();
        });
        mViewPager = view.findViewById(R.id.wp_child_pager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), null);
        mViewPager.setAdapter(pagerAdapter);
        mViewModel = new ViewModelProvider(this).get(WpChildViewModel.class);
        mViewModel.getStringList("childname").observe(getViewLifecycleOwner(), nameList -> {
            adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, nameList);
            childName.setAdapter(adapter);
        });
        chip = view.findViewById(R.id.wp_chip);
        chip.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) HomePage.application.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            analyse();
        });
        chip.setOnCloseIconClickListener(v -> clear());
        return view;
    }

    private void setHLHint() {
        if(childWpData != null) {
            if (htLay.getPrefixText().toString().startsWith("H")) {
                htLay.setPrefixText("L ");
                htLay.setHelperText("Length" + ((childWpData.getAgeYY() < 2) ? "" : " - 0.7"));
                htLay.setEndIconContentDescription("Length");
                htLay.setEndIconDrawable(R.drawable.ic_length);
            } else {
                htLay.setPrefixText("H ");
                htLay.setHelperText("Height" + ((childWpData.getAgeYY() < 2) ? " + 0.7" : ""));
                htLay.setEndIconContentDescription("Height");
                htLay.setEndIconDrawable(R.drawable.ic_height);
            }
        }
    }
    private int getChipEvent() {
        int event = R.string.add;
        if (updateData != null) {
            if (updateData.equals(childWpData)) event = R.string.delete;
            else event = R.string.update;
        }
        chip.setText(event);
        return event;
    }

   /* private double genZScore() {
        int ageMM = childWpData.getAgeYY() * 12 + childWpData.getAgeMM();
        float L = dataTableMonth.getZScore(ageMM, "L");
        float M = dataTableMonth.getZScore(ageMM, "M");
        float S = dataTableMonth.getZScore(ageMM, "S");
        //Log.e("xyz", "L " + L + " M " + M + " S " + S + " wt " + childWpData.getWt() + " ageMM " + ageMM + " Age Days " + childWpData.getAgeDays());
        double Zind = (Math.pow(childWpData.getWt() / M, L) - 1) / (S * L);
        if (Zind < -3) {
            double SD2neg = M * Math.pow(1 + L * S * -2, 1 / L);
            double SD3neg = M * Math.pow(1 + L * S * -3, 1 / L);
            double SD23neg = SD2neg - SD3neg;
            Zind = -3 + ((childWpData.getWt() - SD3neg) / SD23neg);
        } else if (Zind > 3) {
            //Log.e("xyz", "Zind >3");
            double SD3pos = M * Math.pow(1 + L * S * 3, 1 / L);
            double SD2pos = M * Math.pow(1 + L * S * 2, 1 / L);
            double SD23pos = SD3pos - SD2pos;
            Zind = 3 + ((childWpData.getWt() - SD3pos) / SD23pos);
        }
        return Zind;
    }*/

    private void analyse() {
        HashMap<String, Float> idealWeightMap;
        HashMap<String, Float> idealHeightMap;
        HashMap<String, Float> idealWFHMap;
        isChild();

        if (childName.getText().toString().isEmpty() || childdata == null) {
            Snackbar.make(view, R.string.select_child, Snackbar.LENGTH_SHORT).show();
            return;
        }
        Integer childId = childdata.child.getId();
        if (childId == null || childId == 0) {
            Snackbar.make(view, R.string.child_not_register, Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!calAge()) return;
        if (!isValidMea()) return;

        CHOICE event = CHOICE.ADD;
        if (updateData != null) {
            if (updateData.equals(childWpData)) {
                event = CHOICE.DELETE;
            } else {
                event = CHOICE.UPDATE;
            }
        } else {
            childWpData.setChildId(childId);
        }
        AnalyseChild ac = new AnalyseChild(childdata.childWpData);
        idealWeightMap = (childdata.child.getSex().equalsIgnoreCase("Male"))
                ? DataTraveller.getWFA0_5_boys_z().getIdealData((int) childWpData.getAgeDays())
                : DataTraveller.getWFA0_5_girls_z().getIdealData((int) childWpData.getAgeDays());
        ac.setIdealWtMap(idealWeightMap);
        ac.setWt((float) childWpData.getWt());

        dataTableMonth = (childdata.child.getSex().equalsIgnoreCase("Male"))
                ? DataTraveller.getWFA_boys_0_5_z_month()
                : DataTraveller.getWFA_girls_0_5_z_month();

        childWpData.setWfaZ(Util.genZScore(dataTableMonth, childWpData));
        if (childWpData.getHt() > 0) {
            idealHeightMap = (childdata.child.getSex().equalsIgnoreCase("Male"))
                    ? DataTraveller.getLHFA_boys_z().getIdealData((int) childWpData.getAgeDays())
                    : DataTraveller.getLHFA_girls_z().getIdealData((int) childWpData.getAgeDays());
            ac.setIdealHtMap(idealHeightMap);

            if (childWpData.getAgeYY() < 2) {
                idealWFHMap = (childdata.child.getSex().equalsIgnoreCase("Male"))
                        ? DataTraveller.getWFH_boys_0_2_z().getIdealData(roundLowestHalf(childWpData.getHt()))
                        : DataTraveller.getWFH_girls_0_2_z().getIdealData(roundLowestHalf(childWpData.getHt()));
            } else {
                idealWFHMap = (childdata.child.getSex().equalsIgnoreCase("Male"))
                        ? DataTraveller.getWFH_boys_2_5_z().getIdealData(roundLowestHalf(childWpData.getHt()))
                        : DataTraveller.getWFH_girls_2_5_z().getIdealData(roundLowestHalf(childWpData.getHt()));
            }
            ac.setIdealWFHMap(idealWFHMap);
            ac.setHt((float) childWpData.getHt());
        }
        ac.generate();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_child_new, null);

        @SuppressLint("SimpleDateFormat") String sqlDate = new SimpleDateFormat("yyyy-MM-dd").format(childWpData.getmDate());
        TableLayout tl = dialogView.findViewById(R.id.summary_table_layout);
        TextView txtWd = dialogView.findViewById(R.id.summary_wd);
        txtWd.setText(sqlDate);
        TextView txAge = dialogView.findViewById(R.id.summary_age);
        txAge.setSelected(true);
        txAge.setText(getAgeText(childWpData.getAgeYY(), childWpData.getAgeMM(), childWpData.getAgeDD()));

        TextView wfa_status = dialogView.findViewById(R.id.summary_wfa_status);
        TextView wfa_comment = dialogView.findViewById(R.id.summary_wfa_comment);
        TextView hfa_status = dialogView.findViewById(R.id.summary_hfa_status);
        TextView hfa_comment = dialogView.findViewById(R.id.summary_hfa_comment);
        TextView wfh_status = dialogView.findViewById(R.id.summary_wfh_status);
        TextView wfh_comment = dialogView.findViewById(R.id.summary_wfh_comment);
        TableRow row_hfa = dialogView.findViewById(R.id.row_hfa);
        TableRow row_wfh = dialogView.findViewById(R.id.row_wfh);
        float wtDeviate = ac.getWtDeviate();
        String wd = (wtDeviate == 0) ? "" : f.format(wtDeviate)+ " "+getResources().getString(R.string.kg);
        wfa_status.setText(ac.getWtTitle());
        wfa_comment.setText(wd);
        wfa_status.setBackgroundColor(ac.getWtColor());

        if (childWpData.getHt() > 0) {
            float htDeviate = ac.getHtDeviate();
            String hd = (htDeviate == 0) ? "" : f.format(htDeviate)+ " "+getResources().getString(R.string.cm);
            hfa_status.setText(ac.getHtTitle());
            hfa_comment.setText(hd);
            hfa_status.setBackgroundColor(ac.getHtColor());

            float wfhDeviate = ac.getWfhDeviate();
            String wfh = (wfhDeviate == 0) ? "" : f.format(wfhDeviate)+" "+getResources().getString(R.string.kg);
            wfh_status.setText(ac.getWfhTitle());
            wfh_comment.setText(wfh);
            wfh_status.setBackgroundColor(ac.getWfhColor());
        } else {
            tl.removeView(row_hfa);
            tl.removeView(row_wfh);
        }

        final TextInputEditText note = dialogView.findViewById(R.id.sc_note);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.summary_title, null));
        if (event == CHOICE.ADD) {
            dialogBuilder.setPositiveButton(R.string.add, (dialog, whichButton) -> {
                childWpData.setNote(note.getText().toString());
                mViewModel.insert(childWpData);
                clear();
                updateData = null;
            });
        } else if (event == CHOICE.UPDATE) {
            dialogBuilder.setPositiveButton(R.string.update, (dialog, whichButton) -> {
                childWpData.setNote(note.getText().toString());
                mViewModel.update(childWpData);
                updateData = null;
                clear();
            });
        } else {
            dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
                childWpData.setNote(note.getText().toString());
                mViewModel.delete(childWpData);
                updateData = null;
            });
        }

        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> updateData = null);
        dialogBuilder.create();
        dialogBuilder.show();
    }

    private String getAgeText(int ageYY, int ageMM, int ageDD) {
        if (ageYY == 0 && ageMM == 0 && ageDD == 0) return getString(R.string.birth_weight);
        else if (ageYY == 0 && ageMM == 0) return getString(R.string.agelable)+": " + ageDD + getString(R.string.d);
        else if (ageYY == 0) return getString(R.string.agelable)+": " + ageMM + getString(R.string.m) + ageDD + getString(R.string.d);
        else return getString(R.string.agelable)+": " + ageYY + getString(R.string.y) + ageMM + getString(R.string.m) + ageDD + getString(R.string.d);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.child_main_menu, menu);
        MenuItem item = menu.findItem(R.id.wp_chart);
        item.setIcon((graphics) ? R.drawable.ic_show_text : R.drawable.ic_show_chart);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        isChild();
        switch (id) {
            case R.id.wp_child_add:
                int childId = (childdata == null) ? 0 : (childdata.child.getName().equalsIgnoreCase(childName.getText().toString())) ? childdata.child.getId() : 0;
                NavDirections navDirections = WpChildDirections.actionWtpostToChildReg().setChildId(childId);
                NavHostFragment.findNavController(WpChild.this)
                        .navigate(navDirections);
                break;
            case R.id.wp_chart:
                graphics = !graphics;
                item.setIcon((graphics) ? R.drawable.ic_show_text : R.drawable.ic_show_chart);
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), null);
                mViewPager.setAdapter(pagerAdapter);
                break;
            case R.id.wp_child_measure_date:
                showChangeMeasureDate();
                break;
            case R.id.wp_child_share:
                shareIt();
                break;
            case R.id.wp_child_reset:
                childdata = null;
                clear();
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), null);
                mViewPager.setAdapter(pagerAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareIt() {
        if(childdata == null) {
            Snackbar.make(view, R.string.select_child, Snackbar.LENGTH_SHORT).show();
            return;
        }
        CloudAccess.uploadTo(childdata, CloudAccess.TO.PARENT);
    }

    private boolean isValidMea() {
        if (txtWt.getText().toString().isEmpty()) {
            txtWt.setError(getString(R.string.wtRequired));
            txtWt.requestFocus();
            return false;
        }
        double wt;
        try {
            wt = Double.parseDouble(txtWt.getText().toString());
        } catch (NumberFormatException e) {
            txtWt.setError(getString(R.string.error));
            txtWt.requestFocus();
            return false;
        }

        if (wt == 0) {
            txtWt.setError(getString(R.string.wtErrorZero));
            txtWt.requestFocus();
            return false;
        }
        if (wt < 0.5) {
            txtWt.setError(getString(R.string.wtMinZero));
            txtWt.requestFocus();
            return false;
        }
        if (wt > 501) {
            txtWt.setError(getString(R.string.wtMaxError));
            txtWt.requestFocus();
            return false;
        }
        double ht;
        try {
            ht = Double.parseDouble((txtHt.getText().toString().isEmpty()) ? "0.0" : txtHt.getText().toString());
        } catch (NumberFormatException e) {
            txtHt.setError(getString(R.string.error));
            txtHt.requestFocus();
            return false;
        }
        if (ht > 0 && ht < 45) {
            txtHt.setError(getString(R.string.htMinZero));
            txtHt.requestFocus();
            return false;
        }
        if (ht > 210) {
            txtHt.setError(getString(R.string.htMaxError));
            txtHt.requestFocus();
            return false;
        }
        childWpData.setWt(wt);
        childWpData.setHt(ht);
        return true;
    }

    private boolean calAge() {
        int years = 0;
        int months = 0;
        int days = 0;
        if (updateData != null) {
            updateData.setmDate(measureDate.getTime());
        }
        Calendar birthDay = Calendar.getInstance();
        if (childdata != null) birthDay.setTime(childdata.child.getDob());
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
        if (childName.getText() == null || childName.getText().toString().trim().isEmpty()) return;
        String name = childName.getText().toString();
        mViewModel.getChild(name).observe(getViewLifecycleOwner(), nameList -> {
            if (nameList.size() == 0) {
                childdata = null;
                updateData = null;
            } else {
                childdata = nameList.get(0);
                Collections.sort(childdata.childWpData, new Comparator<ChildWpData>() {
                    @Override
                    public int compare(ChildWpData o1, ChildWpData o2) {
                        return o1.getmDate().compareTo(o2.getmDate());
                    }
                });

                updateData = (updateData != null && updateData.getChildId() != childdata.child.getId()) ? null : updateData;
                getChipEvent();
                pagerAdapter = new WpChartPagerAdapter(getChildFragmentManager(), nameList.get(0));
                mViewPager.setAdapter(pagerAdapter);
            }
        });
    }

    private void clear() {
        txtHt.setText(null);
        txtHt.setError(null);
        txtWt.setText(null);
        txtWt.setError(null);
        childName.setText(null);

        updateData = null;
        childWpData = null;
       // measureDate.setTime(new java.util.Date());
        isMeasureDateChange();
        childName.requestFocus();
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
        if (childdata != null) {
            cv.setMinDate(childdata.child.getDob().getTime());
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

    private void hideKeyboard() {
        //View viewI = view.getCurrentFocus();
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
        chip.setText("Delete");
    }

    private class WpChartPagerAdapter extends FragmentStatePagerAdapter {
        ChildData child;
        int nosFragments = 3;

        public WpChartPagerAdapter(FragmentManager fm, ChildData child) {
            super(fm);
            this.child = child;
            this.nosFragments = (child == null) ? 1 : (child.childWpData.size() == 0) ? 1 : (graphics)
                    ? (child.childWpData.get(child.childWpData.size() - 1).getAgeYY() >= 2) ? 4 : 3
                    : 1;
        }

        @Override
        public Fragment getItem(int position) {
            if (child == null) return new HomeView(HomeView.MODE.GMP);
            if (!graphics) {
                History his = new History(child);
                his.setOnItemClickListener(WpChild.this);
                return his;
            }
            if (position == 0) {
                //wfa_0_5 = (wfa_0_5 == null)?new WFA_0_5(child):wfa_0_5;
                return new WFA_0_5(child);
            }
            if (position == 1) return new LHFA_0_5(child);
            if (position == 2) return new WFH_0_5(child, true);
            if (position == 3) return new WFH_0_5(child, false);
            return new HomeView(HomeView.MODE.GMP);
        }

        @Override
        public int getCount() {
            return nosFragments;
        }
    }
}
