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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;


public class WFH05ViewModel extends ViewModel {
    private boolean bothChart;
    private ChildData childData;
    @SuppressLint("StaticFieldLeak")
    private LineChart mChart;
    private float xAxixmin;
    private float xAxixmax;
    private float xAxixGranularity;
    float ymax = 30f;
    private float yAxixGranularity;
    private String area1;
    private String area3;

    private final int c3up = Color.rgb(221, 160, 221);
    private final int c23 = Color.rgb(238, 130, 238);
    private final int c1neg2 = Color.rgb(0, 255, 0);
    private final int c2neg1 = Color.rgb(200, 230, 201);
    private final int c3neg2 = Color.rgb(255, 127, 0);//0
    private final int c3negDown = Color.rgb(255, 0, 0);

    private final int[] mColors = ColorTemplate.VORDIPLOM_COLORS;
    private DataTable dataTable;

    public void setBothChart(boolean bothChart) {
        this.bothChart = bothChart;
    }

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
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(true);
        //mChart.setGridBackgroundColor(c3up);
        // mChart.setMaxVisibleValueCount(5);
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
        x1.setAxisMinValue(xAxixmin);
        x1.setAxisMaxValue(xAxixmax);
        x1.setGranularityEnabled(true);
        x1.setGranularity(xAxixGranularity);
        // float[] zoom = new float[]{1,7,30,90,180,360};
        // x1.setZoomSteps(zoom);
        // x1.setValueFormatter(getXaxisValueFormatter());
        x1.setTextSize(17f);

        YAxis y1 = mChart.getAxisLeft();
        y1.setAxisMaxValue(mChart.getAxisRight().getAxisMaximum());
        y1.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());
        y1.setGridLineWidth(1f);
        y1.setDrawGridLines(true);
        y1.setDrawAxisLine(true);
        y1.setGridColor(Color.BLACK);
        //y1.setLabelCount(25);
        y1.setGranularity(yAxixGranularity);
        y1.setDrawLabels(true);
        y1.setTextSize(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMaxValue(mChart.getAxisLeft().getAxisMaximum());
        rightAxis.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(mChart.getAxisLeft().getLabelCount());
        rightAxis.setGranularity(yAxixGranularity);

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

        if(SMIConstants.LEGEND_SHOW) {
            int[] legendColor = new int[]{c3negDown, c3neg2, c2neg1, c1neg2, c23, c3up};
            String[] legendText = mChart.getContext().getResources().getStringArray(R.array.wt_ledgen);
            l.setCustom(legendColor, legendText);
        }else{
            int[] myColor = {SMIConstants.legendNoColor};
            String[] legend = {mChart.getContext().getResources().getString(R.string.wfh_title)};
            l.setTextSize(22f);
            l.setCustom(myColor, legend);
        }

        ArrayList<Entry> LMAx = new ArrayList<>();
        for (float i = xAxixmin; i <= xAxixmax; i += 0.5) {
            LMAx.add(new Entry(i, mChart.getAxisLeft().getAxisMaximum()));
        }
        LineDataSet sdB = new LineDataSet(LMAx, "SDMax");
        sdB.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdB.setColor(c3up);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawCircles(false);
        sdB.setLineWidth(0.1f);
        sdB.setFillAlpha(65);
        sdB.setFillColor(c3up);
        sdB.setDrawHighlightIndicators(false);
        sdB.setDrawFilled(true);
        sdB.setCurveFilling("SD3");
        mChart.getLineData().addDataSet(sdB);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    private void getTables() {
        area1 = "SD3neg";
        String area2 = "SD2neg";
        area3 = "SD1neg";
        if (getAgeYY() < 2 || bothChart) {
            //Log.e("xyz", "getAgeYY() < 2 || bothChart "+bothChart );
            xAxixmin = 45;
            xAxixmax = 110;
            xAxixGranularity = 0.1f;
            yAxixGranularity = 0.001f;
            if (isMale()) {//
                if (DataTraveller.wfh_boys_0_2_z == null)
                    DataTraveller.wfh_boys_0_2_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_boys_0_2_z), true);
                dataTable = DataTraveller.wfh_boys_0_2_z;
            } else {
                if (DataTraveller.wfh_girls_0_2_z == null)
                    DataTraveller.wfh_girls_0_2_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_girls_0_2_z), true);
                dataTable = DataTraveller.wfh_girls_0_2_z;
            }
        } else if (getAgeYY() >= 2 && getAgeYY() < 5) {
            //Log.e("xyz", "getAgeYY() >= 2 && getAgeYY() < 5 " );
            xAxixmin = 65;
            xAxixmax = 120;
            xAxixGranularity = 0.1f;
            if (isMale()) {
                if (DataTraveller.wfh_boys_2_5_z == null)
                    DataTraveller.wfh_boys_2_5_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_boys_2_5_z), true);
                dataTable = DataTraveller.wfh_boys_2_5_z;
            } else {
                //Logger.setMsg("Female called");
                if (DataTraveller.wfh_girls_2_5_z == null)
                    DataTraveller.wfh_girls_2_5_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_girls_2_5_z), true);
                dataTable = DataTraveller.wfh_girls_2_5_z;
            }
        }
        //Log.e("xyz" , "isMale WFH02ViewModel "+isMale()+" bothChart "+bothChart);
    }

    private int getAgeYY() {
        if (childData == null) return 1;
       return childData.childWpData.get(childData.childWpData.size() - 1).getAgeYY();
    }

    private boolean isMale() {
        return (childData == null) || childData.child.getSex().equalsIgnoreCase("Male");
    }

    public boolean saveChart() {
        String fileName = (childData != null || childData.child != null)?childData.child.getName()+"_wfh"
                :"wfh_" + SMIConstants.todayTimeStamp();
        return mChart.saveChart(fileName);
    }

    private LineData addBasicData() {
        ArrayList<Entry> L1;
        L1 = dataTable.getPercentileFloat(area1);
        LineDataSet sdLower = new LineDataSet(L1, area1);
        sdLower.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdLower.setColor(c3negDown);
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
        } else sdLower.setFillColor(c3negDown);

        ArrayList<Entry> mean;
        mean = dataTable.getPercentileFloat("SD0");
        LineDataSet median = new LineDataSet(mean, "Median");
        median.setAxisDependency(YAxis.AxisDependency.LEFT);
        median.setColor(Color.rgb(6, 115, 35));
        //median.setColor(Color.BLACK);
        median.setDrawCircles(false);
        median.setLineWidth(2f);
        median.setDrawHighlightIndicators(false);
        median.setValueTextColor(Color.BLACK);
        median.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        median.setValueTextSize(20f);
        median.setDrawFilled(false);

        ArrayList<Entry> lsd1;
        lsd1 = dataTable.getPercentileFloat("SD1");
        LineDataSet sd1 = new LineDataSet(lsd1, "SD1");
        sd1.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd1.setColor(c1neg2);
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
        sdL2 = dataTable.getPercentileFloat("SD2");
        LineDataSet sd2 = new LineDataSet(sdL2, "SD2");
        //sd2.setCubicIntensity(0.2f);
        sd2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2.setColor(c1neg2);
        sd2.setDrawCircles(false);
        sd2.setLineWidth(1f);
        sd2.setFillAlpha(200);
        sd2.setDrawHighlightIndicators(false);
        sd2.setValueTextColor(Color.BLACK);
        sd2.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd2.setValueTextSize(20f);
        sd2.setDrawFilled(true);
        sd2.setFillColor(c1neg2);
        sd2.setCurveFilling(area3);
//end of sd2


/*
  sd3
 */
        ArrayList<Entry> sdL1 = dataTable.getPercentileFloat("SD3");
        LineDataSet sdUpper = new LineDataSet(sdL1, "SD3");
        sdUpper.setCubicIntensity(0.2f);
        sdUpper.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdUpper.setColor(c23);
        sdUpper.setCircleColor(ColorTemplate.getHoloBlue());
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setDrawCircles(false);
        sdUpper.setLineWidth(1f);
        sdUpper.setFillAlpha(65);
        sdUpper.setFillColor(c23);
        sdUpper.setDrawHighlightIndicators(false);
        sdUpper.setValueTextColor(Color.BLACK);
        sdUpper.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdUpper.setValueTextSize(20f);
        sdUpper.setDrawFilled(true);
        sdUpper.setCurveFilling("SD2");
        //end of sd1

        LineDataSet sd2negTo1neg = new LineDataSet(dataTable.getPercentileFloat("SD1neg"), "SD1neg");
        sd2negTo1neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2negTo1neg.setColor(c2neg1);
        sd2negTo1neg.setDrawCircles(false);
        sd2negTo1neg.setLineWidth(1f);
        sd2negTo1neg.setFillAlpha(65);
        sd2negTo1neg.setValueTextColor(Color.BLACK);
        sd2negTo1neg.setDrawFilled(true);
        sd2negTo1neg.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd2negTo1neg.setValueTextSize(20f);
        sd2negTo1neg.setDrawHighlightIndicators(false);
        /*if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), colorCode);
            ld.setFillDrawable(drawable);
        } else */
        sd2negTo1neg.setFillColor(c2neg1);
        sd2negTo1neg.setCurveFilling("SD2neg");


        LineDataSet sd2neg3neg = new LineDataSet(dataTable.getPercentileFloat("SD2neg"), "SD2neg");
        sd2neg3neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2neg3neg.setColor(Color.BLACK);
        sd2neg3neg.setDrawCircles(false);
        sd2neg3neg.setLineWidth(1f);
        sd2neg3neg.setFillAlpha(200);
        sd2neg3neg.setDrawHighlightIndicators(false);
        sd2neg3neg.setValueTextColor(Color.BLACK);
        sd2neg3neg.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd2neg3neg.setValueTextSize(20f);
        sd2neg3neg.setDrawFilled(true);
        sd2neg3neg.enableDashedLine(500f, 250f, 0f);
        /*if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_white);
            sd2neg3neg.setFillDrawable(drawable);
        }else */
        sd2neg3neg.setFillColor(c3neg2);
        sd2neg3neg.setCurveFilling("SD3neg");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(sdUpper);
        dataSets.add(sd2);
        dataSets.add(sd1);
        dataSets.add(median);
        dataSets.add(sd2negTo1neg);
        dataSets.add(sd2neg3neg);
        dataSets.add(sdLower);
        return new LineData(dataSets);
    }

    public void updateUserData() {
        ArrayList<Entry> userData = new ArrayList<>();
        if (childData == null || childData.childWpData == null) return;
        for (ChildWpData data : childData.childWpData) {
            if (data.getHt() > 0)
                userData.add(new Entry((float) data.getHt(), (float) data.getWt()));
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
        setUd.setValueFormatter(valueFormatterForBMI());
        mChart.getLineData().addDataSet(setUd);
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    public void addPercentile(String percentile) {
        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();
        for (ILineDataSet i : dataSets) {
            if (i.getLabel().equalsIgnoreCase(percentile)) {
                removeDataSet(percentile);
                return;
            }
        }
        LineDataSet set = new LineDataSet(dataTable.getPercentileFloat(percentile), percentile);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(SMIConstants.PERCENTILE_COLOR);
        set.setDrawCircles(false);
        //set.enableDashedLine(100f, 100f, 0f);
        set.setLineWidth(1f);
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

    private ValueFormatter valueFormatterForBMI() {
        return (value, entry, dataSetIndex, viewPortHandler) -> {
            String xVal = String.valueOf(entry.getX());
            // if(DataTraveller.getAgeYY()==0 && DataTraveller.getAgeMM()==0)
            //      xVal= String.valueOf(DataTraveller.getAgeDD())+"Days";
            //  else if(DataTraveller.getAgeYY()==0 )
            //      xVal= String.valueOf(DataTraveller.getAgeMM())+"M"+String.valueOf(DataTraveller.getAgeDD())+"D";
            //   else xVal= String.valueOf(DataTraveller.getAgeYY())+"Y "+String.valueOf(DataTraveller.getAgeMM())+"M "+String.valueOf(DataTraveller.getAgeDD())+"D";
            return String.valueOf(value) + ", " + xVal;
        };
    }
}
