package sss.yml.smi.chdr;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;

public class LHFA05ViewModel extends ViewModel {
    private ChildData childData;
    @SuppressLint("StaticFieldLeak")
    private LineChart mChart;

    private final int mFillColor = Color.argb(185, 133, 189, 229);

    private final int c2up = Color.rgb(238, 130, 238);
    private final int c1neg2 = Color.rgb(0, 255, 0);
    private final int c2neg1 = Color.rgb(200, 230, 201);
    private final int c3neg2 = Color.rgb(255, 127, 0);//0
    private final int c3negDown = Color.rgb(255, 0, 0);
    private final int ymax = 130;

    private final int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
    private DataTable dataTable;

    public void setChart(LineChart mChart) {
        this.mChart = mChart;
        getTables();
        initialise();
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    private void getTables() {
        if (isMale()) {
            if (DataTraveller.lhfa_boys_z == null)
                DataTraveller.lhfa_boys_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.lhfa_boys_z));
            dataTable = DataTraveller.lhfa_boys_z;
        } else {
            if (DataTraveller.lhfa_girls_z == null)
                DataTraveller.lhfa_girls_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.lhfa_girls_z));
            dataTable = DataTraveller.lhfa_girls_z;
        }
    }

    private void initialise() {
        mChart.setDescription(mChart.getContext().getResources().getString(R.string.sss));
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(true);
        //mChart.setGridBackgroundColor(mFillColor);
        //mChart.set(mFillColor);
        mChart.setMaxVisibleValueCount(5);
        mChart.setData(addBasicData());

        XAxis x1 = mChart.getXAxis();
        x1.setEnabled(true);
        x1.setDrawLabels(true);
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setDrawGridLines(true);
        x1.setGridLineWidth(1f);
        x1.setGridColor(Color.BLACK);
        x1.setDrawAxisLine(true);
        x1.setAxisLineColor(Color.BLACK);
        x1.setAxisLineWidth(4f);
        x1.setAxisMinValue(0);
        x1.setAxisMaxValue(1856);
        x1.setGranularityEnabled(true);
        x1.setGranularity(1f);
        float[] zoom = new float[]{1, 7, 30, 90, 180, 360};
        x1.setZoomSteps(zoom);
        //x1.setValueFormatter(getXaxisValueFormatter());
        x1.setValueFormatter(Formatter.getXaxisValueFormatter());
        x1.setTextSize(17f);

        YAxis y1 = mChart.getAxisLeft();
        y1.setAxisMaxValue(mChart.getAxisRight().getAxisMaximum());
        y1.setAxisMinValue(30);
        y1.setGridLineWidth(1f);
        y1.setDrawGridLines(true);
        y1.setDrawAxisLine(true);
        y1.setGridColor(Color.BLACK);
        y1.setLabelCount(20);
        y1.setDrawLabels(true);
        y1.setGranularity(0.1f);
        y1.setTextSize(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMaxValue(mChart.getAxisLeft().getAxisMaximum());
        rightAxis.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(mChart.getAxisLeft().getLabelCount());
        rightAxis.setGranularity(0.1f);

        Legend l = mChart.getLegend();
        l.setEnabled(true);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(15f);
        l.setDrawInside(true);
        l.setYEntrySpace(15f);
        l.setXOffset(40f);
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);
        l.setTextSize(14f);
        l.setWordWrapEnabled(true);

        if (SMIConstants.LEGEND_SHOW) {
            int[] myColor = {c3negDown, c3neg2, c2neg1, c1neg2};
            String[] legen = mChart.getContext().getResources().getStringArray(R.array.Chartlhfa_0_5_ledgen);
            l.setCustom(myColor, legen);
        } else {
            int[] myColor = {SMIConstants.legendNoColor};
            String[] legend = {mChart.getContext().getResources().getString(R.string.hfa_title)};
            l.setTextSize(22f);
            l.setCustom(myColor, legend);
        }

        ArrayList<Entry> LMAx = new ArrayList<>();
        for (int i = 0; i <= 1856; i++)
            LMAx.add(new Entry(i, mChart.getAxisLeft().getAxisMaximum()));
        LineDataSet sdB = new LineDataSet(LMAx, "SDMax");
        sdB.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdB.setColor(c2up);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawCircles(false);
        sdB.setLineWidth(0.1f);
        sdB.setFillAlpha(65);
        sdB.setFillColor(c2up);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawFilled(true);
        sdB.setCurveFilling("SD2");

        mChart.getLineData().addDataSet(sdB);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();

    }

    private boolean isMale() {
        return (childData == null) || childData.child.getSex().equalsIgnoreCase("Male");
    }

    private LineData addBasicData() {
        ArrayList<Entry> L1 = dataTable.getPercentile("SD3neg");
        LineDataSet sdLower = new LineDataSet(L1, "SD3neg");
        sdLower.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdLower.setLineName("-3SD");
        sdLower.setColor(c3negDown);
        sdLower.setDrawCircles(false);
        sdLower.setLineWidth(1f);
        sdLower.setFillAlpha(65);
        sdLower.setValueTextColor(Color.BLACK);
        sdLower.setDrawFilled(true);
        sdLower.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdLower.setValueTextSize(14f);
        sdLower.setDrawHighlightIndicators(false);
        Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_red);
        sdLower.setFillDrawable(drawable);

        LineDataSet sd3neg2 = new LineDataSet(dataTable.getPercentile("SD2neg"), "SD2neg");
        sd3neg2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd3neg2.setColor(Color.BLACK);
        sd3neg2.setDrawCircles(false);
        sd3neg2.setLineWidth(1f);
        sd3neg2.setFillAlpha(65);
        sd3neg2.setValueTextColor(Color.BLACK);
        sd3neg2.setDrawFilled(true);
        sd3neg2.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd3neg2.setValueTextSize(20f);
        sd3neg2.setDrawHighlightIndicators(false);
        sd3neg2.enableDashedLine(500f, 250f, 0f);
        sd3neg2.setFillColor(c3neg2);
        sd3neg2.setCurveFilling("SD3neg");


        ArrayList<Entry> mean;
        mean = dataTable.getPercentile("SD0");
        LineDataSet median = new LineDataSet(mean, "Median");
        median.setAxisDependency(YAxis.AxisDependency.LEFT);
        median.setColor(Color.rgb(6, 115, 35));
        //median.setCircleColor(ColorTemplate.getHoloBlue());
        median.setDrawCircles(false);
        median.setLineWidth(2f);
        median.setFillAlpha(200);
        median.setDrawHighlightIndicators(false);
        median.setValueTextColor(Color.BLACK);
        median.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        median.setValueTextSize(20f);
        median.setDrawFilled(false);

        /**
         * sd2
         */
        ArrayList<Entry> sdL1;
        sdL1 = dataTable.getPercentile("SD1");
        LineDataSet sd1 = new LineDataSet(sdL1, "SD1");
        //sd2.setCubicIntensity(0.2f);
        sd1.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd1.setColor(Color.rgb(6, 115, 35));
        sd1.setCircleColor(ColorTemplate.getHoloBlue());
        sd1.setDrawCircles(false);
        sd1.setLineWidth(1f);
        sd1.setFillAlpha(200);
        sd1.setDrawHighlightIndicators(false);
        sd1.setValueTextColor(Color.BLACK);
        sd1.setDrawFilled(false);
        sd1.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd1.setValueTextSize(20f);
//end of sd2

        LineDataSet sd1neg2neg = new LineDataSet(dataTable.getPercentile("SD1neg"), "SD1neg");
        //sd1.setCubicIntensity(0.2f);
        sd1neg2neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd1neg2neg.setColor(c2neg1);
        //sd1neg2neg.setCircleColor(ColorTemplate.getHoloBlue());
        sd1neg2neg.setDrawHighlightIndicators(false);
        sd1neg2neg.setDrawCircles(false);
        sd1neg2neg.setLineWidth(1f);
        sd1neg2neg.setFillAlpha(65);
        sd1neg2neg.setFillColor(c2neg1);
        sd1neg2neg.setDrawHighlightIndicators(false);
        sd1neg2neg.setValueTextColor(Color.BLACK);
        sd1neg2neg.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd1neg2neg.setValueTextSize(20f);
        sd1neg2neg.setDrawFilled(true);
        sd1neg2neg.setCurveFilling("SD2neg");
/**
 * sd3
 */
        ArrayList<Entry> sdLupper;
        sdLupper = dataTable.getPercentile("SD2");
        LineDataSet sdUpper = new LineDataSet(sdLupper, "SD2");
        //sd1.setCubicIntensity(0.2f);
        sdUpper.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdUpper.setColor(c2neg1);
        //sd1neg2neg.setCircleColor(ColorTemplate.getHoloBlue());
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setDrawCircles(false);
        sdUpper.setLineWidth(1f);
        sdUpper.setFillAlpha(65);
        sdUpper.setFillColor(c1neg2);
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setValueTextColor(Color.BLACK);
        sdUpper.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdUpper.setValueTextSize(20f);
        sdUpper.setDrawFilled(true);
        sdUpper.setCurveFilling("SD1neg");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        //dataSets.add(sdB);
        dataSets.add(sdUpper);
        dataSets.add(sd1);
        dataSets.add(median);
        dataSets.add(sd3neg2);
        dataSets.add(sd1neg2neg);
        dataSets.add(sdLower);
        return new LineData(dataSets);
    }

    public void updateUserData() {
        ArrayList<Entry> userData = new ArrayList<>();
        if (childData == null || childData.childWpData == null) return;
        HashMap<Float, HashMap<String, Integer>> udXAxisVal = new HashMap<>();
        for (ChildWpData data : childData.childWpData) {
            if (data.getHt() > 0) {
                userData.add(new Entry(data.getAgeDays(), (float) data.getHt()));
                HashMap<String, Integer> age = new HashMap<>();
                age.put("y", data.getAgeYY());
                age.put("m", data.getAgeMM());
                age.put("d", data.getAgeDD());
                udXAxisVal.put(data.getAgeDays(), age);
            }
        }
        boolean singleData = childData.childWpData.size() == 1;
        LineDataSet setUd = new LineDataSet(userData, "User Value");
        setUd.setColor(Color.BLACK);
        setUd.setDrawCircles(true);
        setUd.setDrawCross(singleData);
        setUd.setCircleColor(Color.BLACK);
        setUd.setDrawCircleHole(true);
        setUd.setCircleColorHole(Color.WHITE);
        setUd.setCircleRadius(2f);
        setUd.setCircleHoleRadius(2f);
        setUd.setDrawHighlightIndicators(true);
        setUd.setHighlightLineWidth(3f);
        setUd.setDrawValues((SMIConstants.DRAW_USER_VALUE) || singleData);
        setUd.setDrawValuesAlways(true);
        setUd.setValueTextSize(15f);
        setUd.setValueFormatter(Formatter.valueFormatterAges(udXAxisVal));
        mChart.getLineData().addDataSet(setUd);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    public void addDataSet(String percentile) {
        int count = (mChart.getData().getDataSetCount() + 1);
        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();
        for (ILineDataSet i : dataSets) {
            if (i.getLabel().equalsIgnoreCase(percentile)) {
                removeDataSet(percentile);
                //Logger.setMsg("removed  and Match " + i.getLabel());
                return;
            }
        }

        ArrayList<Entry> L1;
        L1 = dataTable.getPercentile(percentile);
        LineDataSet set = new LineDataSet(L1, percentile);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        int color = mColors[count % mColors.length];
        set.setColor(color);
        set.setDrawCircles(false);
        set.enableDashedLine(100f, 100f, 0f);
        set.setLineWidth(3f);
        set.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        set.setDrawHighlightIndicators(false);
        if (mChart.getYMax() < set.getYMax()) {
            mChart.getAxisLeft().setAxisMaxValue(set.getYMax());
            mChart.getAxisRight().setAxisMaxValue(set.getYMax());
        }
        mChart.getLineData().addDataSet(set);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    private void removeDataSet(String percentile) {
        for (ILineDataSet iLineDataSet : mChart.getLineData().getDataSets()) {
            if (iLineDataSet.getLabel().equalsIgnoreCase(percentile)) {
                mChart.getLineData().removeDataSet(iLineDataSet);
                if (mChart.getYMax() < iLineDataSet.getYMax()) {
                    mChart.getAxisLeft().setAxisMaxValue(mChart.getYMax() + 1);
                    mChart.getAxisRight().setAxisMaxValue(mChart.getYMax() + 1);
                }
                break;
            }
        }
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    public boolean saveChart() {
        String fileName = (childData != null || childData.child != null) ? childData.child.getName() + "_LHFA"
                : "LHFA_" + SMIConstants.todayTimeStamp();
        return mChart.saveChart(fileName);
    }
}
