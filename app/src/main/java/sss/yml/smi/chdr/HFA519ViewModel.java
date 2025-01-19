package sss.yml.smi.chdr;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import sss.yml.smi.R;
import sss.yml.smi.gt.DataTable;
import sss.yml.smi.room.Student;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;

public class HFA519ViewModel extends ViewModel {
    private Student student;
    @SuppressLint("StaticFieldLeak")
    private LineChart mChart;
    DataTable dataTable;
    boolean LEGEND_SHOW;
    boolean DRAW_VALUE;
    private final int mFillColor = Color.argb(185, 133, 189, 229);

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean setChart(LineChart mChart) {
        this.mChart = mChart;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mChart.getContext());
        this.LEGEND_SHOW = sharedPreferences.getBoolean("legend", false);
        this.DRAW_VALUE = sharedPreferences.getBoolean("user_value", false);

        if (!getTables()) return false;
        initialise();
        return true;
    }

    public LineChart getChart() {
        return this.mChart;
    }

    public boolean getTables() {
        if (student == null || student.getSex() == null) return false;
        if (student.getSex().equalsIgnoreCase("Male")) dataTable = DataTraveller.getHFA_boys_z();
        else dataTable = DataTraveller.getHFA_girls_z();
        return true;
    }

    private void initialise() {
        mChart.setDescription(mChart.getContext().getResources().getString(R.string.sss));
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setVisibleXRangeMaximum(15);
        mChart.setDrawGridBackground(true);
        mChart.setData(addBasicData());

        XAxis x1 = mChart.getXAxis();
        x1.setEnabled(true);
        x1.setDrawLabels(true);
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setDrawGridLines(true);
        x1.setGridLineWidth(1f);
        x1.setGridColor(Color.BLACK);
        x1.setDrawAxisLine(true);
        x1.setLabelCount(15);
        x1.setAxisLineColor(Color.BLACK);
        x1.setAxisLineWidth(4f);
        x1.setAxisMinValue(60f);
        x1.setAxisMaxValue(228f);
        x1.setGranularity(1f);
        x1.setInteval(12);
        x1.setMajorGridSize(12);
        x1.setValueFormatter(getXaxisValueFormatter());
        x1.setTextSize(17f);
        float[] zoom = new float[]{1, 3, 6, 12};
        x1.setZoomSteps(zoom);

        YAxis y1 = mChart.getAxisLeft();
        y1.setAxisMaxValue(mChart.getAxisRight().getAxisMaximum());
        y1.setAxisMinValue(student.getHeight() < 91 ? 30f : 90f);
        y1.setGridLineWidth(1f);
        y1.setDrawGridLines(true);
        y1.setDrawAxisLine(true);
        y1.setGridColor(Color.GRAY);
        y1.setLabelCount(20);
        y1.setTextSize(15f);
        y1.setGranularity(0.001f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMaxValue(mChart.getAxisLeft().getAxisMaximum());
        rightAxis.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(20);
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
        if (LEGEND_SHOW) {
            myColor = new int[]{SMIConstants.COLOR_GREEN, SMIConstants.COLOR_ORANGE, SMIConstants.COLOR_RED};
            legend = mChart.getContext().getResources().getStringArray(R.array.ht_legend);
            l.setPosition(Legend.LegendPosition.LEFT_OF_CHART_INSIDE);
        } else {
            myColor = new int[]{SMIConstants.legendNoColor};
            legend = new String[]{mChart.getContext().getResources().getString(R.string.hfa_title)};
            l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        }
        l.setCustom(myColor, legend);
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }
/*
    private LineData addBasicData() {
        ArrayList<Entry> L1;
        L1 = dataTable.getPercentile("SD2neg");
        LineDataSet SD2neg = new LineDataSet(L1, "SD2neg");
        SD2neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        SD2neg.setColor(Color.rgb(242, 19, 19));
        SD2neg.setCircleColor(ColorTemplate.getHoloBlue());
        SD2neg.setDrawCircles(false);
        SD2neg.setLineWidth(3f);
        // SD2neg.setFillAlpha(65);
        SD2neg.setValueTextColor(Color.BLACK);
        SD2neg.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        SD2neg.setValueTextSize(20f);
        SD2neg.setDrawFilled(true);
        SD2neg.setDrawHighlightIndicators(false);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_red);
            SD2neg.setFillDrawable(drawable);
        } else {
            SD2neg.setFillColor(Color.RED);
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

        ArrayList<Entry> sdL1;
        sdL1 = dataTable.getPercentile("SD2");
        LineDataSet sd2 = new LineDataSet(sdL1, "SD2");
        sd2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2.setColor(Color.rgb(6, 115, 35));
        sd2.setCircleColor(ColorTemplate.getHoloBlue());
        sd2.setDrawCircles(false);
        sd2.setLineWidth(3f);
        sd2.setFillAlpha(65);
        sd2.setFillColor(Color.rgb(0, 255, 0));
        sd2.setDrawHighlightIndicators(false);
        sd2.setValueTextColor(Color.BLACK);
        sd2.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sd2.setValueTextSize(20f);
        sd2.setDrawFilled(true);
        sd2.setCurveFilling("SD2neg");
        sd2.setFillFormatter((dataSet, dataProvider) -> mChart.getAxisLeft().getAxisMaximum());
        //end of sd2

        ArrayList<Entry> sdLu;
        sdLu = dataTable.getPercentile("SD2");
        LineDataSet sdu = new LineDataSet(sdLu, "SDu");
        sdu.setAxisDependency(YAxis.AxisDependency.LEFT);
        //sdu.setColor(Color.rgb(6,115,35));
        sdu.setDrawCircles(false);
        sdu.setFillAlpha(65);
        sdu.setFillColor(mFillColor);
        sdu.setDrawHighlightIndicators(false);
        sdu.setDrawValuesAlways(SMIConstants.DRAW_VALUE);
        sdu.setDrawFilled(true);
        sdu.setFillFormatter((dataSet, dataProvider) -> {
            return mChart.getAxisLeft().getAxisMaximum();
            //return 11f;
        });

        //end of sd2

        ArrayList<Entry> userData = new ArrayList<>();
        userData.add(new Entry(student.getAgeMonths(), (float) student.getHeight()));
        LineDataSet setUd = new LineDataSet(userData, "userData");
        setUd.setDrawCircles(true);
        setUd.setDrawCross(true);
        setUd.setCircleColor(Color.BLACK);
        setUd.setDrawCircleHole(true);
        setUd.setCircleColorHole(Color.WHITE);
        setUd.setCircleHoleRadius(5f);
        setUd.setDrawHighlightIndicators(true);
        setUd.setHighlightLineWidth(3f);
        setUd.setDrawValues(true);
        setUd.setDrawValuesAlways(true);
        setUd.setValueTextSize(20f);
        setUd.setColor(Color.BLACK);
        setUd.setValueFormatter(valueFormatterForBMI());

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(sdu);
        dataSets.add(sd2);
        dataSets.add(median);
        dataSets.add(SD2neg);
        dataSets.add(setUd);
        return new LineData(dataSets);
    }
*/

    private LineData addBasicData() {
        ArrayList<Entry> L1;
        L1 = dataTable.getPercentile("SD3neg");
        LineDataSet SD3neg = new LineDataSet(L1, "SD3neg");
        SD3neg.setLineName("-3SD");
        SD3neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        SD3neg.setColor(SMIConstants.COLOR_RED);
        SD3neg.setCircleColor(ColorTemplate.getHoloBlue());
        SD3neg.setDrawCircles(false);
        SD3neg.setLineWidth(3f);
        SD3neg.setValueTextColor(Color.BLACK);
        SD3neg.setDrawValues(DRAW_VALUE);
        SD3neg.setValueTextSize(20f);
        SD3neg.setDrawFilled(true);
        SD3neg.setDrawHighlightIndicators(false);
        Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_red);
        SD3neg.setFillDrawable(drawable);

        LineDataSet SD2neg = new LineDataSet(dataTable.getPercentile("SD2neg"), "SD2neg");
        SD2neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        SD2neg.setLineName("-2SD");
        SD2neg.setColor(SMIConstants.COLOR_RED);
        SD2neg.setCircleColor(ColorTemplate.getHoloBlue());
        SD2neg.setDrawCircles(false);
        SD2neg.setLineWidth(3f);
        SD2neg.setValueTextColor(Color.BLACK);
        SD2neg.setDrawValues(DRAW_VALUE);
        SD2neg.setValueTextSize(20f);
        SD2neg.setDrawFilled(true);
        SD2neg.setDrawHighlightIndicators(false);
        SD2neg.setCurveFilling("SD3neg");
        SD2neg.setFillColor(SMIConstants.COLOR_ORANGE);//SMIConstants.COLOR_ORANGE
        SD2neg.setFillAlpha(65);

        LineDataSet median = new LineDataSet(dataTable.getPercentile("SD0"), "Median");
        median.setAxisDependency(YAxis.AxisDependency.LEFT);
        median.setLineName("Median");
        median.setColor(Color.rgb(6, 115, 35));
        median.setCircleColor(ColorTemplate.getHoloBlue());
        median.setDrawCircles(false);
        median.setLineWidth(2f);
        median.setDrawHighlightIndicators(false);
        median.setValueTextColor(Color.BLACK);
        median.setDrawValues(DRAW_VALUE);
        median.setValueTextSize(20f);
        median.setDrawFilled(false);

        ArrayList<Entry> sd2l = dataTable.getPercentile("SD2");
        LineDataSet sd2 = new LineDataSet(sd2l, "SD2");
        sd2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2.setLineName("+2SD");
        sd2.setColor(SMIConstants.COLOR_RED);
        sd2.setCircleColor(ColorTemplate.getHoloBlue());
        sd2.setDrawCircles(false);
        sd2.setLineWidth(3f);
        sd2.setFillAlpha(65);
        sd2.setFillColor(SMIConstants.COLOR_GREEN);
        sd2.setDrawHighlightIndicators(false);
        sd2.setValueTextColor(Color.BLACK);
        sd2.setDrawValues(DRAW_VALUE);
        sd2.setValueTextSize(20f);
        sd2.setDrawFilled(true);
        sd2.setCurveFilling("SD2neg");
        sd2.setFillFormatter((dataSet, dataProvider) -> mChart.getAxisLeft().getAxisMaximum());

        LineDataSet sdu = new LineDataSet(sd2l, "SDu");
        sdu.setAxisDependency(YAxis.AxisDependency.LEFT);
        sdu.setDrawCircles(false);
        sdu.setFillAlpha(65);
        sdu.setFillColor(SMIConstants.COLOR_PURPLE);
        sdu.setDrawHighlightIndicators(false);
        sdu.setDrawValues(DRAW_VALUE);
        sdu.setDrawFilled(true);
        sdu.setFillFormatter((dataSet, dataProvider) -> mChart.getAxisLeft().getAxisMaximum());

        ArrayList<Entry> userData = new ArrayList<>();
        userData.add(new Entry(student.getAgeMonths(), (float) student.getHeight()));
        LineDataSet setUd = new LineDataSet(userData, "userData");
        setUd.setDrawCircles(true);
        setUd.setDrawCross(true);
        setUd.setCircleColor(Color.BLACK);
        setUd.setDrawCircleHole(true);
        setUd.setCircleColorHole(Color.WHITE);
        setUd.setCircleHoleRadius(5f);
        setUd.setDrawHighlightIndicators(true);
        setUd.setHighlightLineWidth(3f);
        setUd.setDrawValues(true);
        setUd.setDrawValuesAlways(true);
        setUd.setValueTextSize(20f);
        setUd.setColor(Color.BLACK);
        setUd.setValueFormatter(valueFormatterForBMI());

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(sdu);
        dataSets.add(sd2);
        dataSets.add(median);
        dataSets.add(SD2neg);
        dataSets.add(SD3neg);
        dataSets.add(setUd);
        return new LineData(dataSets);
    }


    public void addPercentile(String percentile) {
        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();
        for (ILineDataSet i : dataSets) {
            if (i.getLabel().equalsIgnoreCase(percentile)) {
                removePercentile(percentile);
                return;
            }
        }

        ArrayList<Entry> L1 = dataTable.getPercentile(percentile);
        LineDataSet set = new LineDataSet(L1, percentile);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(SMIConstants.PERCENTILE_COLOR);
        set.setDrawCircles(false);
        //set.enableDashedLine(50f, 25f, 50f);
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

    public void removePercentile(String percentile) {
        for (ILineDataSet iLineDataSet : mChart.getLineData().getDataSets()) {
            if (iLineDataSet.getLabel().equalsIgnoreCase(percentile)) {
                mChart.getLineData().removeDataSet(iLineDataSet);
                if (200 < iLineDataSet.getYMax()) {
                    mChart.getAxisLeft().setAxisMaxValue(mChart.getYMax() + 10);
                    mChart.getAxisRight().setAxisMaxValue(mChart.getYMax() + 10);
                }
                break;
            }
        }
        mChart.notifyDataSetChanged();
        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }
    public boolean saveChart() {
        String fileName = SMIConstants.todayTimeStamp();
        mChart.saveToGallery("ab", 100);
        return mChart.saveChart(fileName);
    }
    private ValueFormatter valueFormatterForBMI() {
        return (value, entry, dataSetIndex, viewPortHandler) -> {
            float y = entry.getX();
            int yy = (int) (y / 12);
            float mm = y % 12;
            String xVal = yy + mChart.getContext().getResources().getString(R.string.y) +
                    (int)mm + mChart.getContext().getResources().getString(R.string.m);
            return value + ", " + xVal;
        };
    }

    public AxisValueFormatter getXaxisValueFormatter() {
        return new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis, Paint mAxisLabelPaint) {
                if (value % 12 == 0) {
                    if (mAxisLabelPaint != null)
                        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(16f));
                    return String.valueOf((int) value / 12);
                } else if (value % 6 == 0) {
                    if (mAxisLabelPaint != null)
                        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(13f));
                    return String.valueOf((int) value % 12);
                } else if (value % 3 == 0) {
                    if (mAxisLabelPaint != null)
                        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(13f));
                    return String.valueOf((int) value % 12);
                } else {
                    if (mAxisLabelPaint != null)
                        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(13f));
                    return String.valueOf((int) value % 12);
                }
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };
    }
}
