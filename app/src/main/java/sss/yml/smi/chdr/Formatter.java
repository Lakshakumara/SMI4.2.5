package sss.yml.smi.chdr;

import android.graphics.Paint;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.HashMap;

public class Formatter {

    public static AxisValueFormatter getXaxisValueFormatter() {
        return new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis, Paint mAxisLabelPaint) {
                if (value % (360) == 0) {
                    if (mAxisLabelPaint != null) {
                        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(16f));
                    }
                    return "Y " + (int) value / (360);
                }else if ( value % (30) == 0) {
                    if (mAxisLabelPaint != null) mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(14f));
                    return "M " + (int) value / (30);
                }else if (value % 7 == 0) {
                    if (mAxisLabelPaint != null) mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(12f));
                    return "W " + (int) value / (7);
                }else{
                    if (mAxisLabelPaint != null) mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10f));
                    // return String.valueOf("Y"+(int)value/(7*4*12)+" M"+(int)value/(7*4)+" W"+(int)value/(7)+" D"+(int)value%(7));
                    return "D " + (int) value % (7);
                }
            }
            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };
    }



    public static ValueFormatter valueFormatterAges(final HashMap<Float, HashMap<String, Integer> > udXAxisVal) {
        return (value, entry, dataSetIndex, viewPortHandler) -> {
            String xVal;
            if(udXAxisVal == null) return String.valueOf(entry.getX());
            HashMap<String, Integer> val = udXAxisVal.get(entry.getX());
            if(val == null) return String.valueOf(entry.getX());
            if(val.get("y") == 0 && val.get("m")==0)
                xVal= val.get("d") +"Days";
            else if(val.get("y") == 0 )
                xVal= val.get("m") +"M"+ val.get("d") +"D";
            else xVal= val.get("y") +"Y "+ val.get("m") +"M "+ val.get("d") +"D";
            return value +", "+xVal;
        };
    }
}
