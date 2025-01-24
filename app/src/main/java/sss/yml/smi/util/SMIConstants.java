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
        SD4neg, SD3neg, SD2neg, SD1neg, SD0, SD1, SD2, SD3, SD4, SD5
    }

    /**
     * Color for Obesity(>+2SD)or
     * over Normal height range
     */
    public static final int COLOR_PURPLE = Color.argb(128, 160, 32, 240);
    public static final int COLOR_PURPLE_OPAQUE = Color.argb(255, 160, 32, 240);
    /**
     * Color for Overweight(+1SD to +2SD)
     */
    public static final int COLOR_PINK = Color.argb(0, 229, 93, 190);
    public static final int COLOR_PINK_OPAQUE = Color.argb(255, 229, 93, 190);
    /**
     * Color for Normal Weight(+1SD to -2SD) and Normal height(>-2SD)
     */
    public static final int COLOR_GREEN = Color.argb(128, 57, 255, 20);
    /**
     * Color for Moderate Wasting(-2SD to -3SD) and stunting(-2SD to -3SD)
     */
    public static final int COLOR_ORANGE = Color.argb(128, 255, 165, 0);
    public static final int COLOR_ORANGE_OPAQUE = Color.argb(255, 255, 131, 0);
    /**
     * Color for Severe Wasting(<-3SD) and Server Stunting(<-3SD)
     */
    public static final int COLOR_RED = Color.argb(128, 255, 36, 0);


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

    public enum SEX {
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