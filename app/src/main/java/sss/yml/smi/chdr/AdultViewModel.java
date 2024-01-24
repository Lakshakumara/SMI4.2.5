package sss.yml.smi.chdr;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

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
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import sss.yml.smi.R;
import sss.yml.smi.room.Elder;
import sss.yml.smi.util.SMIConstants;

public class AdultViewModel extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private LineChart mChart;
    private Elder elder;

    public boolean setChart(LineChart mChart, Elder elder) {
        this.mChart = mChart;
        this.elder = elder;
        initialise();
        return true;
    }

    private void initialise() {
        //Logger.setMsg("elder ", elder.toString());
        mChart.setDescription(mChart.getContext().getResources().getString(R.string.sss));
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        XAxis x1 = mChart.getXAxis();
        x1.setEnabled(elder.isDOB());
        x1.setDrawLabels(true);
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
        x1.setDrawGridLines(true);
        x1.setGridLineWidth(1f);
       // x1.setGridColor(Color.BLACK);
        x1.setDrawAxisLine(true);
        //x1.setLabelCount(10);
        //x1.setAxisLineColor(Color.BLACK);
       // x1.setAxisLineColor(mChart.getContext().getResources().getColor(R.color.black));
        //x1.setTextColor(mChart.getContext().getResources().getColor(android.R.color.white, getActivity().getTheme()));
        x1.setAxisLineWidth(4f);
        x1.setGranularity(1f);
        x1.setValueFormatter(getXaxisValueFormatter());
        x1.setTextSize(17f);
        x1.setZoomSteps(new float[]{1, 3, 6, 12});
        if(elder.isDOB()){
            x1.setAxisMinValue((elder.getAgeYY() - 2)*12);
            x1.setAxisMaxValue((elder.getAgeYY() + 2)*12);
        }else{
            x1.setAxisMinValue(0);
            x1.setAxisMaxValue(5*12);
        }

        YAxis y1 = mChart.getAxisLeft();
        y1.setAxisMaxValue((elder.getBmi() > 35) ? elder.getBmi() + 10 : 40);
        y1.setAxisMinValue((elder.getBmi() > 15) ? 10 : 0);
        y1.setGridLineWidth(1f);
        y1.setDrawGridLines(true);
        y1.setDrawAxisLine(true);
        y1.setGridColor(Color.GRAY);
        y1.setTextSize(15f);
        y1.setGranularity(0.01f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setAxisMaxValue(mChart.getAxisLeft().getAxisMaximum());//hfa.getyAxisMaxValue()
        rightAxis.setAxisMinValue(mChart.getAxisLeft().getAxisMinimum());//hfa.getyAxisMinValue()
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(mChart.getAxisLeft().getLabelCount());
        rightAxis.setGranularity(0.01f);
        mChart.setData(addMainEntry());


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
        int[] myColor = {Color.RED, Color.rgb(0, 200, 0), Color.argb(229, 252, 193, 232), Color.argb(185, 133, 189, 229)};
        String[] legen = mChart.getContext().getResources().getStringArray(R.array.bmi_ledgen);
        l.setCustom(myColor, legen);

        mChart.animateX(SMIConstants.ANIMATION_TIME);
        mChart.invalidate();
    }

    private LineData addMainEntry() {
        int xMin;
        int xMax;
        if (!elder.isDOB()) {
            xMin = 0;
            xMax = 5 * 12;
        } else {
            xMin = (elder.getAgeYY() - 2) * 12;
            xMax = (elder.getAgeYY() + 2) * 12;
        }
        ArrayList<Entry> L1 = new ArrayList<>();
        ArrayList<Entry> L2 = new ArrayList<>();
        ArrayList<Entry> L3 = new ArrayList<>();
        ArrayList<Entry> L4 = new ArrayList<>();
        for (int i = xMin; i <= xMax; i++) {
            L1.add(new Entry(i, 18.5f));
            L2.add(new Entry(i, 25f));
            L3.add(new Entry(i, 30f));
            L4.add(new Entry(i, 30f));
        }

        LineDataSet SD2neg = new LineDataSet(L1, "wasted");
        SD2neg.setAxisDependency(YAxis.AxisDependency.LEFT);
        SD2neg.setColor(Color.RED);
        SD2neg.setDrawValues(false);
        SD2neg.setDrawCircles(false);
        SD2neg.setLineWidth(3f);
        SD2neg.setFillAlpha(65);
        SD2neg.setDrawFilled(true);
        SD2neg.setDrawHighlightIndicators(false);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(mChart.getContext(), R.drawable.fade_red);
            SD2neg.setFillDrawable(drawable);
        } else {
            SD2neg.setFillColor(Color.RED);
        }

        LineDataSet sd1 = new LineDataSet(L2, "Normal");
        sd1.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd1.setColor(Color.rgb(0, 200, 0));
        sd1.setDrawValues(false);
        sd1.setDrawCircles(false);
        sd1.setLineWidth(3f);
        sd1.setFillAlpha(65);
        sd1.setFillColor(Color.rgb(0, 200, 0));
        sd1.setDrawHighlightIndicators(false);
        sd1.setDrawFilled(true);
        sd1.setFillFormatter((dataSet, dataProvider) -> 18.5f);

        LineDataSet sd2 = new LineDataSet(L3, "Over Weight");
        sd2.setAxisDependency(YAxis.AxisDependency.LEFT);
        sd2.setColor(Color.argb(229, 252, 193, 232));
        sd2.setDrawCircles(false);
        sd2.setLineWidth(3f);
        sd2.setFillAlpha(65);
        sd2.setFillColor(Color.argb(229, 252, 193, 232));
        sd2.setDrawHighlightIndicators(false);
        sd2.setDrawValues(false);
        sd2.setDrawFilled(true);
        sd2.setFillFormatter((dataSet, dataProvider) -> 25f);
        LineDataSet obe = new LineDataSet(L4, "Obesity");
        obe.setAxisDependency(YAxis.AxisDependency.LEFT);
        obe.setColor(Color.argb(185, 133, 189, 229));
        obe.setDrawCircles(false);
        obe.setLineWidth(1f);
        obe.setFillAlpha(65);
        obe.setFillColor(Color.argb(185, 133, 189, 229));
        obe.setDrawHighlightIndicators(false);
        obe.setDrawValues(false);
        obe.setDrawFilled(true);
        obe.setFillFormatter((dataSet, dataProvider) -> mChart.getAxisLeft().getAxisMaximum());

        ArrayList<Entry> userData = new ArrayList<>();
        if (elder.isDOB())
            userData.add(new Entry(elder.getAgeYY() * 12 + elder.getAgeMM(), elder.getBmi()));
        else userData.add(new Entry(1 * 12, elder.getBmi()));
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
        setUd.setDrawValuesAlways(elder.isDOB());
        setUd.setValueTextSize(20f);
        setUd.setColor(Color.BLACK);
        setUd.setValueFormatter(valueFormatterForBMI());

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(obe);
        dataSets.add(sd2);
        dataSets.add(sd1);
        dataSets.add(SD2neg);
        dataSets.add(setUd);
        return new LineData(dataSets);
    }

    private AxisValueFormatter getXaxisValueFormatter() {
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

    private ValueFormatter valueFormatterForBMI() {
        return (value, entry, dataSetIndex, viewPortHandler) -> {
            float y = entry.getX();
            int yy = (int) (y / 12);
            float mm = y % 12;
            String xVal = yy + "y " + (int) mm + "m";
            return value + ", " + xVal;
        };
    }
}
