package sss.yml.smi.util;

import java.util.HashMap;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.gt.DataTable;

/**
 * Created by YML Kumara
 */
public class DataTraveller {
    public static String adText = "MCR";
    public static String className = "XX";
    public static double height = 0;
    public static double weight = 0;
    public static String sex;
    public static double bmi;
    public static double idealBmi;
    public static int ageYY = -1;
    public static int ageMM;
    public static int ageDD;
    public static float ageDays;
    public static boolean ageValid = false;
    public static boolean calculated = false;

    public static boolean initial = false;

    public static String wtMsg = "";
    public static int wtColor;
    public static String htMsg = "";
    public static int htColor;
    /**
     * -1 Under Weight
     * 0 Normal Weight
     * 1 Over Normal
     */
    public static int wtCode;
    /**
     * -1 under normal Height
     * 0 Normal Height
     * 1 above Normal Height
     */
    public static int htCode;
    public static HashMap<String, Float> idealWeight = new HashMap<>();
    public static HashMap<String, Float> idealWeight_wfh = new HashMap<>();
    public static HashMap<String, Float> idealHeight = new HashMap<>();
    @Deprecated
    public static DataTable wfa0_5_boys_z;
    @Deprecated
    public static DataTable wfa0_5_girls_z;
    @Deprecated
    public static DataTable lhfa_boys_z;
    @Deprecated
    public static DataTable lhfa_girls_z;
    @Deprecated
    public static DataTable wfh_boys_0_2_z;
    @Deprecated
    public static DataTable wfh_girls_0_2_z;
    @Deprecated
    public static DataTable wfh_boys_2_5_z;
    @Deprecated
    public static DataTable wfh_girls_2_5_z;
    @Deprecated
    public static DataTable bmi_boys_z;
    @Deprecated
    public static DataTable bmi_girls_z;
    @Deprecated
    public static DataTable hfa_boys_z;
    @Deprecated
    public static DataTable hfa_girls_z;
    private static DataTable wfa_boys_0_5_z_month;
    private static DataTable wfa_girls_0_5_z_month;

    public static DataTable getWFA0_5_boys_z() {
        if (wfa0_5_boys_z != null) return wfa0_5_boys_z;
        else
            wfa0_5_boys_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfa0_5_boys_z));
        return wfa0_5_boys_z;
    }

    public static DataTable getWFA_boys_0_5_z_month() {
        if (wfa_boys_0_5_z_month != null) return wfa_boys_0_5_z_month;
        else
            wfa_boys_0_5_z_month = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfa_boys_0_5_z_month));
        return wfa_boys_0_5_z_month;
    }

    public static DataTable getWFA_girls_0_5_z_month() {
        if (wfa_girls_0_5_z_month != null) return wfa_girls_0_5_z_month;
        else
            wfa_girls_0_5_z_month = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfa_girls_0_5_z_month));
        return wfa_girls_0_5_z_month;
    }

    public static DataTable getWFA0_5_girls_z() {
        if (wfa0_5_girls_z != null) return wfa0_5_girls_z;
        else
            wfa0_5_girls_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfa0_5_girls_z));
        return wfa0_5_girls_z;
    }

    public static DataTable getLHFA_boys_z() {
        if (lhfa_boys_z != null) return lhfa_boys_z;
        else
            lhfa_boys_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.lhfa_boys_z));
        return lhfa_boys_z;
    }

    public static DataTable getLHFA_girls_z() {
        if (lhfa_girls_z != null) return lhfa_girls_z;
        else
            lhfa_girls_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.lhfa_girls_z));
        return lhfa_girls_z;
    }

    public static DataTable getWFH_boys_0_2_z() {
        if (wfh_boys_0_2_z != null) return wfh_boys_0_2_z;
        else
            wfh_boys_0_2_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_boys_0_2_z), true);
        return wfh_boys_0_2_z;
    }

    public static DataTable getWFH_girls_0_2_z() {
        if (wfh_girls_0_2_z != null) return wfh_girls_0_2_z;
        else
            wfh_girls_0_2_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_girls_0_2_z), true);
        return wfh_girls_0_2_z;
    }

    public static DataTable getWFH_boys_2_5_z() {
        if (wfh_boys_2_5_z != null) return wfh_boys_2_5_z;
        else
            wfh_boys_2_5_z = new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_boys_2_5_z), true);
        return wfh_boys_2_5_z;
    }

    public static DataTable getWFH_girls_2_5_z() {
        return (wfh_girls_2_5_z != null) ? wfh_girls_2_5_z:
                new DataTable(HomePage.application.getResources().openRawResource(R.raw.wfh_girls_2_5_z), true);
    }

    public static DataTable getBMI_boys_z() {
        return (bmi_boys_z != null)?bmi_boys_z:
                new DataTable(HomePage.application.getResources().openRawResource(R.raw.bmi_boys_z));
    }

    public static DataTable getBMI_girls_z() {
        return (bmi_girls_z != null)?bmi_girls_z:
                new DataTable(HomePage.application.getResources().openRawResource(R.raw.bmi_girls_z));
    }

    public static DataTable getHFA_boys_z() {
        return (hfa_boys_z != null)?hfa_boys_z:new DataTable(HomePage.application.getResources().openRawResource(R.raw.hfa_boys_z));
    }

    public static DataTable getHFA_girls_z() {
        return (hfa_girls_z != null)?hfa_girls_z:new DataTable(HomePage.application.getResources().openRawResource(R.raw.hfa_girls_z));
    }

    public static boolean isNull() {
        return sex == null || weight == 0;
    }

    @Deprecated
    public static double getHeight() {
        return height;
    }

    public static float getHeightFloat() {
        return (float) height;//Float.valueOf(height);
    }

    @Deprecated
    public static double getWeight() {
        return weight;
    }

    public static float getWeightFloat() {
        return (float) weight;//Float.valueOf(weight);
    }

    @Deprecated
    public static String getAge() {
        return String.valueOf(ageYY + "." + String.valueOf(ageMM));
    }

    public static String getSex() {
        return sex;
    }

    public static boolean isMale() {
        return sex.equalsIgnoreCase("Male");
    }

    public static float getAgeMonths() {
        int months = getAgeYY() * 12 + getAgeMM();
        return (float) months;//Float.valueOf(months);
    }

    public static float getAgeDays() {
        return ageDays;
    }

    public static double getBmi() {
        return bmi;
    }

    public static float getBmiFloat() {
        return (float) bmi;//Float.valueOf(bmi);
    }

    @Deprecated
    public static double getIdealBmi() {
        return idealBmi;
    }

    public static boolean isAgeValid() {
        return ageValid;
    }

    public static int getAgeYY() {
        return ageYY;
    }

    public static int getAgeMM() {
        return ageMM;
    }

    public static int getAgeDD() {
        return ageDD;
    }

    public static void calculated(boolean cal) {
        calculated = cal;
    }

    public static boolean isCal() {
        return calculated;
    }

    public static void reset() {
        calculated(false);
        height = 0;
        weight = 0;
        sex = null;
        bmi = 0;
        idealBmi = 0;
        ageYY = -1;
        ageMM = -1;
        ageDD = -1;
        ageDays = -1;
        ageValid = false;
        idealWeight = new HashMap<>();
        idealHeight = new HashMap<>();
    }
}
