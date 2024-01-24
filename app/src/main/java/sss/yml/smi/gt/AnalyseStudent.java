package sss.yml.smi.gt;

import android.content.Context;
import android.graphics.Color;
import sss.yml.smi.R;
import sss.yml.smi.room.Student;
import sss.yml.smi.util.DataTraveller;
import sss.yml.smi.util.SMIConstants;

import java.util.HashMap;

public class AnalyseStudent {
    static int wtColor;
    static int htColor;
    Context ctx;
    Student student;
    String wtTitle = "";
    boolean isNormalWt = false;
    boolean isNormalHt = false;
    String wtTitleShort = "";
    String htTitleShort = "";
    float idealWeight = 0;
    float wtDeviate = 0;
    float htDeviate = 0;
    String htTitle = "";
    float idealHeight = 0;
    String wtPos;
    String htPos;
    int ob = Color.argb(65, 221, 160, 221);
    int ow = Color.argb(65, 238, 130, 238);
    int nw = Color.argb(65, 0, 255, 0);
    int lwr = Color.argb(65, 200, 230, 201);
    int lw = Color.rgb(255, 127, 0);//0
    int slw = Color.rgb(255, 0, 0);

    public AnalyseStudent(Context context) {
        ctx = context;
    }

    public AnalyseStudent(Context context, Student student) {
        this.ctx = context;
        this.student = student;
    }

    public static int getWtColor() {
        return wtColor;
    }

    public static int getHtColor() {
        return htColor;
    }

    public String getWtPos() {
        return wtPos;
    }

    public void setWtPos(String wtPos) {
        this.wtPos = wtPos;
    }

    public String getHtPos() {
        return htPos;
    }

    public void setHtPos(String htPos) {
        this.htPos = htPos;
    }

    public String getWtSummary() {
        if (student.getAgeY() >= 5 && student.getAgeY() < 19) getBmiLay();
        if (isNormalWt) return wtTitleShort;
        else return wtTitleShort.concat(" " + String.format("%.3f", wtDeviate));
    }

    public String getHtSummary() {
        if (student.getAgeY() >= 5 && student.getAgeY() < 19) getHeightLay();
        else return "";
        if (isNormalHt) return htTitleShort;
        else return htTitleShort.concat(" " + String.format("%.3f", htDeviate));
    }

    private String getBmiLay() {
        String title = "";
        String subTitle = "";
        float idealBmi = 0;
        String massage = "<table id=\"t01\"><tr>";
        float wt = toFloat(student.getWeight());
        float ht = toFloat(student.getHeight());
        float bmi = toFloat(student.getBmi());

        HashMap<String, Float> dd = getIdealWeight();
        if (dd.get("SD4neg") > bmi) {
            idealBmi = dd.get("SD2neg");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = slw;
            wtPos = SMIConstants.ChartArea.SD4neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.severely_wasted) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.severely_wasted_short);
            subTitle = "<h2>" + ctx.getString(R.string.bmi) + " (" + bmi + ") < -4SD</h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -4SD</td><td><b>" + String.format("%.3f", dd.get("SD4neg") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD4neg") <= bmi && bmi < dd.get("SD3neg")) {
            idealBmi = dd.get("SD2neg");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = slw;
            wtPos = SMIConstants.ChartArea.SD3neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.severely_wasted) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.severely_wasted_short);
            subTitle = "<h2> -4SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < -3SD</h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr><td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD3neg") <= bmi && bmi < dd.get("SD2neg")) {
            idealBmi = dd.get("SD2neg");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = slw;
            wtPos = SMIConstants.ChartArea.SD2neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.Wasted) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.Wasted_short);
            subTitle = "<h2> -3SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < -2SD</h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD2neg") <= bmi && bmi < dd.get("SD1neg")) {
            wtDeviate = 0;
            wtColor = nw;
            wtPos = SMIConstants.ChartArea.SD1neg.toString();
            isNormalWt = true;
            title = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            subTitle = "<h2> -2SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < -1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + " </td><td></td><tr> <td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD1neg") <= bmi && bmi < dd.get("SD0")) {
            wtDeviate = 0;
            wtColor = nw;
            wtPos = SMIConstants.ChartArea.SD0.toString();
            isNormalWt = true;
            title = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            subTitle = "<h2> -1SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < Medium</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD0") <= bmi && bmi < dd.get("SD1")) {
            wtDeviate = 0;
            wtColor = nw;
            wtPos = SMIConstants.ChartArea.SD1.toString();
            isNormalWt = true;
            title = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            subTitle = "<h2> Medium < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < +1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for Over Weight</td><td><b>" + String.format("%.3f", dd.get("SD1") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD1") <= bmi && bmi < dd.get("SD2")) {
            idealBmi = dd.get("SD1");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = ob;
            wtPos = SMIConstants.ChartArea.SD2.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.overweight) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.overweight_short);
            subTitle = "<h2> +1SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < +2SD </h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for Obesity</td><td><b>" + String.format("%.3f", dd.get("SD2") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD2") <= bmi && bmi < dd.get("SD3")) {
            idealBmi = dd.get("SD1");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = ow;
            wtPos = SMIConstants.ChartArea.SD3.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.obesity) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.obesity_short);
            subTitle = "<h2> +2SD < " + ctx.getString(R.string.bmi) + "(" + bmi + ") < +3SD </h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD3") <= bmi && bmi < dd.get("SD4")) {
            idealBmi = dd.get("SD1");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = ow;
            wtPos = SMIConstants.ChartArea.SD4.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.obesity) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.obesity_short);
            subTitle = "<h2> +3SD < " + ctx.getString(R.string.bmi) + " (" + bmi + ") < +4SD </h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        } else if (bmi >= dd.get("SD4")) {
            idealBmi = dd.get("SD1");
            wtDeviate = idealBmi * (ht * ht) / 10000 - wt;
            wtColor = ow;
            wtPos = SMIConstants.ChartArea.SD5.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.obesity) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.obesity_short);
            subTitle = "<h2> " + ctx.getString(R.string.bmi) + " (" + bmi + ") > +4SD </h2>";
            massage = massage.concat("<p>" + ctx.getString(R.string.bmi) + " " + ctx.getString(R.string.bmi) + ": <b>" + idealBmi + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") * (ht * ht) / 10000 - wt) + "Kg</b></td></tr></table>");
        }
        return title + subTitle + massage;
    }

    private HashMap<String, Float> getIdealWeight() {
        if (student.getSex().equalsIgnoreCase("Male"))
            return DataTraveller.getBMI_boys_z().getIdealData((int) student.getAgeMonths());
        else
            return DataTraveller.getBMI_girls_z().getIdealData((int) student.getAgeMonths());
    }

    private HashMap<String, Float> getIdealHeight() {
        if (student.getSex().equalsIgnoreCase("Male"))
            return DataTraveller.getHFA_boys_z().getIdealData((int) student.getAgeMonths());
        else
            return DataTraveller.getHFA_girls_z().getIdealData((int) student.getAgeMonths());
    }

    private float toFloat(double value) {
        return (float) value;
    }

    private String getHeightLay() {
        String title = "";
        String subTitle = "";
        float idealHeight = 0;
        String massage = "<table id=\"t01\"><tr>";
        float ht = toFloat(student.getHeight());
        HashMap<String, Float> dd = getIdealHeight();
        idealHeight = dd.get("SD2neg");
        htDeviate = idealHeight - ht;
        if (dd.get("SD4neg") > ht) {
            isNormalHt = false;
            htColor = slw;
            htPos = SMIConstants.ChartArea.SD4neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.severely_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.severely_stunted);
            subTitle = "<h2>" + ctx.getString(R.string.ht) + " (" + ht + ") < -4SD</h2>";
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -4SD</td><td><b>" + String.format("%.3f", dd.get("SD4neg") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD4neg") <= ht && ht < dd.get("SD3neg")) {
            isNormalHt = false;
            htColor = slw;
            htPos = SMIConstants.ChartArea.SD3neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.severely_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.severely_stunted);
            subTitle = "<h2> -4SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -3SD</h2>";
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr><td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD3neg") <= ht && ht < dd.get("SD2neg")) {
            isNormalHt = false;
            htColor = slw;
            htPos = SMIConstants.ChartArea.SD2neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.moderate_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.moderate_stunted_short);
            subTitle = "<h2> -3SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -2SD</h2>";
            idealHeight = dd.get("SD2neg");
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>Height Loss: </td><td></td><tr> <td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD2neg") <= ht && ht < dd.get("SD1neg")) {
            isNormalHt = true;
            htDeviate = 0;
            htColor = nw;
            htPos = SMIConstants.ChartArea.SD1neg.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            subTitle = "<h2> -2SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>Height Loss: </td><td></td><tr> <td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD1neg") <= ht && ht < dd.get("SD0")) {
            isNormalHt = true;
            htDeviate = 0;
            htColor = nw;
            htPos = SMIConstants.ChartArea.SD0.toString();
            htTitleShort = ctx.getString(R.string.normal_height_short);
            title = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            subTitle = "<h2> -1SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < Medium</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD0") <= ht && ht < dd.get("SD1")) {
            isNormalHt = true;
            htDeviate = 0;
            htColor = nw;
            htPos = SMIConstants.ChartArea.SD1.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            subTitle = "<h2> Medium < " + ctx.getString(R.string.ht) + " (" + ht + ") < +1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD1") <= ht && ht < dd.get("SD2")) {
            isNormalHt = true;
            idealHeight = dd.get("SD2");
            htDeviate = 0;
            htColor = nw;
            htPos = SMIConstants.ChartArea.SD2.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            subTitle = "<h2> +1SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +2SD </h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD2") <= ht && ht < dd.get("SD3")) {
            isNormalHt = false;
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            htPos = SMIConstants.ChartArea.SD3.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.over_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            subTitle = "<h2> +2SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +3SD </h2>";
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.ht) + " Loss:</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD3") <= ht && ht < dd.get("SD4")) {
            isNormalHt = false;
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            htPos = SMIConstants.ChartArea.SD4.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.over_height) + " Ht</center></h1></b>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            subTitle = "<h2> +3SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +4SD </h2>";
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>Height Loss:</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");
        } else if (ht >= dd.get("SD4")) {
            isNormalHt = false;
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            htPos = SMIConstants.ChartArea.SD5.toString();
            title = "<b><h1><center>" + ctx.getString(R.string.over_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            subTitle = "<h2> " + ctx.getString(R.string.ht) + " (" + ht + ") > +4SD </h2>";
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>Height Loss:</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");
        }
        return title + subTitle + massage;
    }

    private String get5_19Summary() {
        //return "<html><head></head><body>" + getBmiLay() + getHeightLay() + "</body></html>";
        return "<html><body style=\"background-color:powderblue;\">"+getBmiLay()+getHeightLay()+"</body></html>";
    }

    /*public String getBMISummary(){
            if(DataTraveller.isAgeValid() && DataTraveller.getAgeYY() < 19) return "";
            if(wtTitleShort.equalsIgnoreCase(ctx.getString(R.string.normal_wt))) return wtTitleShort;
            else return wtTitleShort.concat(" "+String.format("%.3f",wtDeviate));
        }*/
    private String getWfaLay() {
        String subTitle = "";
        String massage = "<table id=\"t01\"><tr>";
        float wt = DataTraveller.getWeightFloat();
        HashMap<String, Float> dd = DataTraveller.idealWeight;
        if (dd.get("SD4neg") > wt) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.severely_wasted) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.severely_wasted_short);
            subTitle = "<h2>" + ctx.getString(R.string.wt) + " (" + wt + ") < -4SD</h2>";

            idealWeight = dd.get("SD1neg");
            wtDeviate = idealWeight - wt;
            wtColor = slw;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -4SD</td><td><b>" + String.format("%.3f", dd.get("SD4neg") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - wt) + "Kg</b></td></tr></table>");
        } else if (dd.get("SD4neg") <= wt && wt < dd.get("SD3neg")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.severely_wasted) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.severely_wasted_short);
            subTitle = "<h2> -4SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < -3SD</h2>";
            idealWeight = dd.get("SD1neg");
            wtDeviate = idealWeight - wt;
            wtColor = slw;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr><td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD3neg") <= wt && wt < dd.get("SD2neg")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.low_weight) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.low_weight_short);
            subTitle = "<h2> -3SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < -2SD</h2>";
            idealWeight = dd.get("SD1neg");
            wtDeviate = idealWeight - wt;
            wtColor = lw;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + " </td><td></td><tr> <td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD2neg") <= wt && wt < dd.get("SD1neg")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.risk_low_weight) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.risk_low_weight_short);
            subTitle = "<h2> -2SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < -1SD</h2>";
            idealWeight = dd.get("SD1neg");
            wtDeviate = idealWeight - wt;
            wtColor = lwr;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + " </td><td></td><tr> <td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD1neg") <= wt && wt < dd.get("SD0")) {
            isNormalWt = true;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            wtDeviate = 0;
            wtColor = nw;
            subTitle = "<h2> -1SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < Medium</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + " </td><td></td><tr> <td>to -1SD</td><td><b>" + String.format("%.3f", dd.get("SD1neg") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD0") <= wt && wt < dd.get("SD1")) {
            isNormalWt = true;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            wtDeviate = 0;
            wtColor = nw;
            subTitle = "<h2> Medium < " + ctx.getString(R.string.wt) + " (" + wt + ") < +1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD1") <= wt && wt < dd.get("SD2")) {
            isNormalWt = true;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.normal_wt) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.normal_wt_short);
            wtDeviate = 0;
            wtColor = nw;
            subTitle = "<h2> +1SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < +2SD </h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_wt) + "</td><td></td><tr> <td>for +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD2") <= wt && wt < dd.get("SD3")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.overweight) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.overweight_short);
            subTitle = "<h2> +2SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < +3SD </h2>";
            idealWeight = dd.get("SD2");
            wtDeviate = idealWeight - wt;
            wtColor = ow;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr></table>");

        } else if (dd.get("SD3") <= wt && wt < dd.get("SD4")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.obesity) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.obesity_short);
            subTitle = "<h2> +3SD < " + ctx.getString(R.string.wt) + " (" + wt + ") < +4SD </h2>";
            idealWeight = dd.get("SD2");
            wtDeviate = idealWeight - wt;
            wtColor = ob;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr></table>");
        } else if (wt >= dd.get("SD4")) {
            isNormalWt = false;
            wtTitle = "<b><h1><center>" + ctx.getString(R.string.obesity) + "</center></h1></b>";
            wtTitleShort = ctx.getString(R.string.obesity_short);
            subTitle = "<h2> " + ctx.getString(R.string.wt) + " (" + wt + ") > +4SD </h2>";
            idealWeight = dd.get("SD2");
            wtDeviate = idealWeight - wt;
            wtColor = ob;
            massage = massage.concat("<p>" + ctx.getString(R.string.ideal_weight) + "<b>" + idealWeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.weght_loss) + "</td><td></td><tr> <td>to +4SD</td><td><b>" + String.format("%.3f", dd.get("SD4") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>" + ctx.getString(R.string.to_normal) + "</td><td><b>" + String.format("%.3f", wtDeviate) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - wt) + "Kg</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - wt) + "Kg</b></td></tr></table>");
        }
        return wtTitle + subTitle + massage;
    }

    private String getLHeightLay() {
        String subTitle = "";
        String massage = "<table id=\"t01\"><tr>";
        float ht = DataTraveller.getHeightFloat();
        HashMap<String, Float> dd = DataTraveller.idealHeight;
        idealHeight = dd.get("SD1neg");
        htDeviate = idealHeight - ht;
        if (dd.get("SD4neg") > ht) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.severely_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.severely_stunted);
            subTitle = "<h2>" + ctx.getString(R.string.ht) + " (" + ht + ") < -4SD</h2>";

            htColor = slw;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -4SD</td><td><b>" + String.format("%.3f", dd.get("SD4neg") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - ht) + "cm</b></td></tr></table>");
        } else if (dd.get("SD4neg") <= ht && ht < dd.get("SD3neg")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.severely_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.severely_stunted);
            subTitle = "<h2> -4SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -3SD</h2>";

            htColor = slw;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -3SD</td><td><b>" + String.format("%.3f", dd.get("SD3neg") - ht) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to -2SD</td><td><b>" + String.format("%.3f", dd.get("SD2neg") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD3neg") <= ht && ht < dd.get("SD2neg")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.moderate_stunted) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.moderate_stunted_short);
            subTitle = "<h2> -3SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -2SD</h2>";

            htColor = lw;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD2neg") <= ht && ht < dd.get("SD1neg")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.risk_stunt) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.risk_stunt);
            subTitle = "<h2> -2SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < -1SD</h2>";

            htColor = lwr;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>" + ctx.getString(R.string.for_normal) + "</td><td><b>" + String.format("%.3f", htDeviate) + "cm</b></td></tr>");
            massage = massage.concat("<tr><td>to Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD1neg") <= ht && ht < dd.get("SD0")) {
            isNormalHt = true;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            htDeviate = 0;
            htColor = nw;
            subTitle = "<h2> -1SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < Medium</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for Medium</td><td><b>" + String.format("%.3f", dd.get("SD0") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD0") <= ht && ht < dd.get("SD1")) {
            isNormalHt = true;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            htDeviate = 0;
            htColor = nw;
            subTitle = "<h2> Medium < " + ctx.getString(R.string.ht) + " (" + ht + ") < +1SD</h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +1SD</td><td><b>" + String.format("%.3f", dd.get("SD1") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD1") <= ht && ht < dd.get("SD2")) {
            isNormalHt = true;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.normal_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.normal_height_short);
            htDeviate = 0;
            htColor = nw;
            subTitle = "<h2> +1SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +2SD </h2>";
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +2SD</td><td><b>" + String.format("%.3f", dd.get("SD2") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD2") <= ht && ht < dd.get("SD3")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.over_height) + "</center></h1></b>";
            subTitle = "<h2> +2SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +3SD </h2>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +3SD</td><td><b>" + String.format("%.3f", dd.get("SD3") - ht) + "cm</b></td></tr></table>");

        } else if (dd.get("SD3") <= ht && ht < dd.get("SD4")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.over_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            subTitle = "<h2> +3SD < " + ctx.getString(R.string.ht) + " (" + ht + ") < +4SD </h2>";
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            massage = massage.concat("<table id=\"t01\"><tr><td>" + ctx.getString(R.string.required_ht) + "</td><td></td><tr> <td>for +4SD</td><td><b>" + String.format("%.3f", dd.get("SD4") - ht) + "cm</b></td></tr></table>");

        } else if (ht >= dd.get("SD4")) {
            isNormalHt = false;
            htTitle = "<b><h1><center>" + ctx.getString(R.string.over_height) + "</center></h1></b>";
            htTitleShort = ctx.getString(R.string.over_height_short);
            subTitle = "<h2> " + ctx.getString(R.string.ht) + " (" + ht + ") > +4SD </h2>";
            idealHeight = dd.get("SD2");
            htDeviate = idealHeight - ht;
            htColor = ob;
            massage = massage.concat(ctx.getString(R.string.ideal_height) + idealHeight + "</b></p>");
            // massage=massage.concat("<table id=\"t01\"><tr><td>Height Loss:</td><td></td><tr> <td>to +4SD</td><td><b>"+String.format("%.3f",dd.get("SD4")-ht)+"cm</b></td></tr>");
            // massage=massage.concat("<tr><td>to +3SD</td><td><b>"+String.format("%.3f",dd.get("SD3")-ht)+"cm</b></td></tr>");
            // massage=massage.concat("<tr><td>"+ctx.getString(R.string.to_normal)+"</td><td><b>"+String.format("%.3f",dd.get("SD2")-ht)+"cm</b></td></tr></table>");
        }
        return htTitle + subTitle + massage;
    }
}