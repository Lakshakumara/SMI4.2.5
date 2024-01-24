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

import sss.yml.smi.R;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;

public class WFA05ViewModel extends ViewModel {
    ChildData childData;
    @SuppressLint("StaticFieldLeak")
    LineChart mChart;
    int ob = Color.rgb(221, 160, 221);
    int ow = Color.rgb(238, 130, 238);
    int nw = Color.rgb(0, 255, 0);
    int lwr = Color.rgb(200, 230, 201);
    int lw = Color.rgb(255, 127, 0);
    int slw = Color.rgb(255, 0, 0);

    float yMax = 30f;
    DataTable dataTable;
    DataTable dataTableMonth;
    HashMap<Float, HashMap<String, Integer>> udXAxisVal;

    public void setChart(LineChart mChart) {
        this.mChart = mChart;
        getTables();
        initialise();
    }

    public void setChildData(ChildData childData) {
        this.childData = childData;
    }

    private void initialise() {
        mChart.setDescription(mChart.getContext().getResources().getString(R.string.sss));
        mChart.setContentDescription("Chart for WFA");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
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
        x1.setValueFormatter(Formatter.getXaxisValueFormatter());
        x1.setTextSize(17f);

        YAxis y1 = mChart.getAxisLeft();
        yMax = mChart.getAxisRight().getAxisMaximum();
        y1.setAxisMaxValue(yMax);
        y1.setAxisMinValue(0);
        y1.setGridLineWidth(1f);
        y1.setDrawGridLines(true);
        y1.setDrawAxisLine(true);
        y1.setGridColor(Color.BLACK);
        y1.setLabelCount(15);
        y1.setGranularity(0.001f);
        y1.setDrawLabels(true);
        y1.setTextSize(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMaxValue(mChart.getAxisLeft().getAxisMaximum());
        rightAxis.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(mChart.getAxisLeft().getLabelCount());
        rightAxis.setGranularity(0.001f);

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
        int[] myColor;
        String[] legend;
        if(SMIConstants.LEGEND_SHOW) {
            myColor = new int[]{slw, lw, lwr, nw, ow, ob};
            legend = mChart.getContext().getResources().getStringArray(R.array.wt_ledgen);
        }else{
            myColor = new int[]{SMIConstants.legendNoColor};
            legend = new String[]{mChart.getContext().getResources().getString(R.string.wfa_title)};
            l.setTextSize(22f);
        }
        l.setCustom(myColor, legend);
        ArrayList<Entry> LMAx = new ArrayList<>();
        for (int i = 0; i <= 1856; i++)
            LMAx.add(new Entry(i, mChart.getAxisLeft().getAxisMaximum()));
        LineDataSet sdB = new LineDataSet(LMAx, "SDMax");
        sdB.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdB.setColor(ob);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawCircles(false);
        sdB.setLineWidth(0.1f);
        sdB.setFillAlpha(65);
        sdB.setFillColor(ob);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawFilled(true);
        sdB.setCurveFilling("SD3");
        mChart.getLineData().addDataSet(sdB);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    public void getTables() {
        if (isMale()) {
            dataTable = DataTraveller.getWFA0_5_boys_z();
            dataTableMonth = DataTraveller.getWFA_boys_0_5_z_month();
        } else {
            dataTable = DataTraveller.getWFA0_5_girls_z();
            dataTableMonth = DataTraveller.getWFA_girls_0_5_z_month();
        }
    }

    private boolean isMale() {
        return (childData == null) || childData.child.getSex().equalsIgnoreCase("Male");
    }

    private LineData addBasicData() {
        ArrayList<Entry> L1 = dataTable.getPercentile("SD3neg");
        LineDataSet sdLower = new LineDataSet(L1, "SD3neg");
        sdLower.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdLower.setColor(Color.rgb(242, 19, 19));
        sdLower.setDrawCircles(false);
        sdLower.setLineWidth(1f);
        sdLower.setFillAlpha(65);
        sdLower.setValueTextColor(Color.BLACK);
        sdLower.setDrawFilled(true);
        sdLower.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdLower.setValueTextSize(20f);
        sdLower.setDrawHighlightIndicators(false);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_red);
            sdLower.setFillDrawable(drawable);
        } else {
            sdLower.setFillColor(slw);
        }
        ArrayList<Entry> mean;
        mean = dataTable.getPercentile("SD0");
        LineDataSet median = new LineDataSet(mean, "Median");
        median.setAxisDependency(YAxis.AxisDependency.LEFT);
        median.setColor(Color.rgb(6, 115, 35));
        median.setCircleColor(ColorTemplate.getHoloBlue());
        median.setDrawCircles(false);
        median.setLineWidth(2f);
        median.setDrawHighlightIndicators(false);
        median.setValueTextColor(Color.BLACK);
        median.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        median.setValueTextSize(20f);
        median.setDrawFilled(false);

        ArrayList<Entry> lsd1;
        lsd1 = dataTable.getPercentile("SD1");
        LineDataSet sd1 = new LineDataSet(lsd1, "SD1");
        sd1.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd1.setColor(Color.rgb(6, 115, 35));
        sd1.setCircleColor(ColorTemplate.getHoloBlue());
        sd1.setDrawCircles(false);
        sd1.setLineWidth(1f);
        sd1.setDrawHighlightIndicators(false);
        sd1.setValueTextColor(Color.BLACK);
        sd1.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd1.setValueTextSize(20f);
        sd1.setDrawFilled(false);

        /*
          sd2
         */
        ArrayList<Entry> sdL2;
        sdL2 = dataTable.getPercentile("SD2");
        LineDataSet sd2 = new LineDataSet(sdL2, "SD2");
        //sd2.setCubicIntensity(0.2f);
        sd2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2.setColor(nw);
        sd2.setCircleColor(ColorTemplate.getHoloBlue());
        sd2.setDrawCircles(false);
        sd2.setLineWidth(1f);
        sd2.setFillAlpha(65);
        sd2.setDrawHighlightIndicators(false);
        sd2.setValueTextColor(Color.BLACK);
        sd2.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd2.setValueTextSize(20f);
        sd2.setDrawFilled(true);
        sd2.setFillColor(nw);
        sd2.setCurveFilling("SD1neg");

        ArrayList<Entry> sdL1;
        sdL1 = dataTable.getPercentile("SD3");
        LineDataSet sdUpper = new LineDataSet(sdL1, "SD3");
        sdUpper.setCubicIntensity(0.2f);
        sdUpper.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdUpper.setColor(ow);
        sdUpper.setCircleColor(ColorTemplate.getHoloBlue());
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setDrawCircles(false);
        sdUpper.setLineWidth(1f);
        sdUpper.setFillAlpha(65);
        sdUpper.setFillColor(ow);
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setValueTextColor(Color.BLACK);
        sdUpper.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdUpper.setValueTextSize(20f);
        sdUpper.setDrawFilled(true);
        sdUpper.setCurveFilling("SD2");

        LineDataSet l12ned = new LineDataSet(dataTable.getPercentile("SD1neg"), "SD1neg");
        l12ned.setAxisDependency(YAxis.AxisDependency.LEFT);
        l12ned.setColor(Color.BLACK);
        l12ned.setDrawCircles(false);
        l12ned.setLineWidth(1f);
        l12ned.setFillAlpha(65);
        l12ned.setValueTextColor(Color.BLACK);
        l12ned.setDrawFilled(true);
        l12ned.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        l12ned.setValueTextSize(20f);
        l12ned.setDrawHighlightIndicators(false);
        l12ned.setFillColor(lwr);
        l12ned.setCurveFilling("SD2neg");


        LineDataSet l23neg = new LineDataSet(dataTable.getPercentile("SD2neg"), "SD2neg");
        l23neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        l23neg.setColor(Color.BLACK);
        l23neg.setDrawCircles(false);
        l23neg.setLineWidth(1f);
        l23neg.setFillAlpha(65);
        l23neg.setValueTextColor(Color.BLACK);
        l23neg.setDrawFilled(true);
        l23neg.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        l23neg.setValueTextSize(20f);
        l23neg.setDrawHighlightIndicators(false);
        //l23neg.enableDashedLine(100f, 200f, 0);
        l23neg.setFillColor(lw);
        //l23neg.setCurveFilling("SD3neg");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        // dataSets.add(sdB);
        dataSets.add(sdUpper);
        dataSets.add(sd2);
        dataSets.add(sd1);
        dataSets.add(median);
        dataSets.add(l12ned);
        dataSets.add(l23neg);
        dataSets.add(sdLower);
        return new LineData(dataSets);
    }

    public void updateUserData() {
        ArrayList<Entry> userData = new ArrayList<>();
        if (childData == null || childData.childWpData == null) return;
        udXAxisVal = new HashMap<>();
        for (ChildWpData data : childData.childWpData) {
            userData.add(new Entry(data.getAgeDays(), (float) data.getWt()));
            HashMap<String, Integer> age = new HashMap<>();
            age.put("y", data.getAgeYY());
            age.put("m", data.getAgeMM());
            age.put("d", data.getAgeDD());
            udXAxisVal.put(data.getAgeDays(), age);
        }
        boolean singleData = childData.childWpData.size() == 1;
        LineDataSet setUd = new LineDataSet(userData, "User Data");
        setUd.setColor(Color.BLACK);
        setUd.setDrawCircles(true);
        setUd.setDrawCross(singleData);
        setUd.setCircleColor(Color.BLACK);
        setUd.setDrawCircleHole(true);
        setUd.setCircleColorHole(Color.BLACK);//WHITE
        setUd.setCircleRadius(2f);
        setUd.setCircleHoleRadius(2f);
        setUd.setDrawHighlightIndicators(true);
        setUd.setLineWidth(2.5f);
        setUd.setHighlightLineWidth(2f);
        setUd.setDrawValues((SMIConstants.DRAW_USER_VALUE) || singleData);
        setUd.setDrawValuesAlways(singleData);
        setUd.setValueTextSize(10f);
        setUd.setValueFormatter(Formatter.valueFormatterAges(udXAxisVal));
        mChart.getLineData().addDataSet(setUd);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
        if(SMIConstants.DRAW_TREND) drawTrend();
    }

    private void drawTrend() {
        ArrayList<Entry> userData = new ArrayList<>();
        if (childData.childWpData == null) return;
        if (childData.childWpData.size() == 0) return;
        double sumZ = 0;
        int j = 1;
        for (ChildWpData data : childData.childWpData) {
            //Logger.setMsg(j+" calculated "+data.getWfaZ());
            sumZ = sumZ + data.getWfaZ();
            j=j+1;
        }
        double Z = sumZ/(j-1);
        //Logger.setMsg(" sumZ "+sumZ);
        //Logger.setMsg(j+" mean z "+Z);
        userData.add(new Entry(0, (float)childData.childWpData.get(0).getWt()));
        for (int i = 1; i <= 60; i++) {
            userData.add(new Entry(i * 30, getTrendLine(i-1, Z)));
        }
        LineDataSet setUd = new LineDataSet(userData, "Trend Line");
        setUd.setColor(Color.BLUE);
        setUd.setDrawCircles(false);
        setUd.setCircleColor(Color.BLACK);
        setUd.setDrawCircleHole(true);
        setUd.setDrawHighlightIndicators(false);
        setUd.setLineWidth(2.5f);
        setUd.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        mChart.getLineData().addDataSet(setUd);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }
   private float getTrendLine(int ageMM, double Zind) {
        float L = dataTableMonth.getZScore(ageMM, "L");
        float M = dataTableMonth.getZScore(ageMM, "M");
        float S = dataTableMonth.getZScore(ageMM, "S");
        //Logger.setMsg("L " + L + " M " + M + " S " + S + " ageMM " + ageMM + " Zind " + Zind);
       // Logger.setMsg("cal Float " + (float) (M * Math.pow(Zind * (S * L) + 1, 1 / L)));
        return (float) (M * Math.pow(Zind * (S * L) + 1, 1 / L));
    }

    public void addPercentile(String percentile) {
        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();
        for (ILineDataSet i : dataSets) {
            //Logger.setMsg("List "+i.getLabel());
            if (i.getLabel().equalsIgnoreCase(percentile)) {
                removePercentile(percentile);
                //Logger.setMsg(percentile+" Removed");
                return;
            }
        }
        LineDataSet set = new LineDataSet(dataTable.getPercentile(percentile), percentile);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(SMIConstants.PERCENTILE_COLOR);
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
        //Logger.setMsg(percentile+" Added");
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    private void removePercentile(String percentile) {
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
        String fileName = (childData != null || childData.child != null)?childData.child.getName()+"_wfa"
                :"wfa_" + SMIConstants.todayTimeStamp();
        return mChart.saveChart(fileName);
    }
}
