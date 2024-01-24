package sss.yml.smi.util;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by My Pc on 2016-08-11.
 */

public class SMIConstants {
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static boolean isMale(String sex) {
        return sex.equalsIgnoreCase("Male");
    }

    public enum ChartArea {
        SD4neg,	SD3neg,	SD2neg,	SD1neg,	SD0, SD1, SD2, SD3, SD4, SD5
    }
    public static final SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd-h-mm-s");
    public static final int ANIMATION_TIME = 2500;

    public static final int YEAR = Calendar.getInstance().get(Calendar.YEAR);

    public static String todayTimeStamp() {
        return dt.format(new java.util.Date());
    }
    public static Boolean DRAW_TREND = false;
    /**
     * Chart Lengdn On or Off
     *
     * @return
     */
    public static Boolean LEGEND_SHOW = false;

    /**
     * draw Coordinate values
     *
     * @return
     */
    public static Boolean DRAW_VALUE = false;
    /**
     * draw User Values
     *
     * @return
     */
    public static Boolean DRAW_USER_VALUE = false;
    public enum SEX{
        MALE, FEMALE
    }
    public static final int legendNoColor = Color.argb(0, 221, 160, 221);
    public static final int PERCENTILE_COLOR = Color.rgb(0, 255, 0);
    public static String getAdFile() {
        return "https://www.dropbox.com/s/mzmi7os7n13isf6/NewadFile.txt?dl=1";
    }

    public static String getVersionFile() {
        return "https://www.dropbox.com/s/7yftv7iyiq7jma3/versionInfo.txt?dl=1";
    }
}