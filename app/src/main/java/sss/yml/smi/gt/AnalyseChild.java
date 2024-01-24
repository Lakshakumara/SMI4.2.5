package sss.yml.smi.gt;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.ChildWpData;

public class AnalyseChild {
    private final Context ctx = HomePage.application;
    private HashMap<String, Float> idealWtMap;
    private HashMap<String, Float> idealHtMap;
    private HashMap<String, Float> idealWFHMap;
    private float wt;
    private float ht;
    private final List<ChildWpData> childWpData;
    private float wtDeviate = 0;
    private float htDeviate = 0;
    private float wfhDeviate = 0;

    private int wtColor;
    private int htColor;
    private int wfhColor;

    private int wfaCode;
    private int hfaCode;
    private int wfhCode;

    private final int ob = Color.argb(65, 221, 160, 221);
    private final int ow = Color.argb(65, 238, 130, 238);
    private final int nw = Color.argb(65, 0, 255, 0);
    private final int lwr = Color.argb(65, 200, 230, 201);
    private final int lw = Color.rgb(255, 127, 0);//0
    private final int slw = Color.rgb(255, 0, 0);
    private String wtTitle = "";
    private String htTitle = "";
    private String wfhTitle = "";

    public AnalyseChild(List<ChildWpData> childWpData) {
        this.childWpData = childWpData;
    }

    public void setIdealWtMap(HashMap<String, Float> idealWtMap) {
        this.idealWtMap = idealWtMap;
    }

    public void setIdealHtMap(HashMap<String, Float> idealHtMap) {
        this.idealHtMap = idealHtMap;
    }

    public void setIdealWFHMap(HashMap<String, Float> idealWFHMap) {
        this.idealWFHMap = idealWFHMap;
    }

    public void setWt(float wt) {
        this.wt = wt;
    }

    public void setHt(float ht) {
        this.ht = ht;
    }

    public void generate() {
        setUp();
        getWfaLay();
        if (ht > 0) {
            getLHeightLay();
            getWFHLay();
        }
    }

    private void setUp() {
        if (childWpData == null) return;
        int size = childWpData.size();
        if (size == 0) return;
        double birthWt = childWpData.get(0).getWt();
        ChildWpData lastWpData = childWpData.get(size - 1);
    }

    public float getWtDeviate() {
        return wtDeviate;
    }

    public int getWtColor() {
        return wtColor;
    }

    public String getWtTitle() {
        return wtTitle;
    }

    private void getWfaLay() {
        float idealWeight = (wt < idealWtMap.get("SD1neg")) ? idealWtMap.get("SD1neg") : (wt > idealWtMap.get("SD2")) ? idealWtMap.get("SD2") : 0;
        if (wt < idealWtMap.get("SD3neg")) {
            wtDeviate = wt - idealWeight;
            wtColor = slw;
            wtTitle = ctx.getString(R.string.severely_wasted);
        } else if (idealWtMap.get("SD3neg") <= wt && wt < idealWtMap.get("SD2neg")) {
            wtDeviate = wt - idealWeight;
            wtColor = lw;
            wtTitle = ctx.getString(R.string.low_weight);
        } else if (idealWtMap.get("SD2neg") <= wt && wt < idealWtMap.get("SD1neg")) {
            wtDeviate = wt - idealWeight;
            wtColor = lwr;
            wtTitle = ctx.getString(R.string.risk_low_weight);
        } else if (idealWtMap.get("SD1neg") <= wt && wt < idealWtMap.get("SD0")) {
            wtDeviate = 0;
            wtColor = nw;
            wtTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWtMap.get("SD0") <= wt && wt < idealWtMap.get("SD1")) {
            wtDeviate = 0;
            wtColor = nw;
            wtTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWtMap.get("SD1") <= wt && wt <= idealWtMap.get("SD2")) {
            wtDeviate = 0;
            wtColor = nw;
            wtTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWtMap.get("SD2") < wt && wt <= idealWtMap.get("SD3")) {
            wtDeviate = wt - idealWeight;
            wtColor = ow;
            wtTitle = ctx.getString(R.string.overweight);
        } else if (wt > idealWtMap.get("SD3")) {
            wtDeviate = wt - idealWeight;
            wtColor = ob;
            wtTitle = ctx.getString(R.string.obesity);
        }
    }

    public float getHtDeviate() {
        return htDeviate;
    }

    public int getHtColor() {
        return htColor;
    }

    public String getHtTitle() {
        return htTitle;
    }

    private void getLHeightLay() {
        float idealHeight = (ht < idealHtMap.get("SD1neg")) ? idealHtMap.get("SD1neg") : (ht > idealHtMap.get("SD2")) ? idealHtMap.get("SD2") : 0;

        if (ht < idealHtMap.get("SD3neg")) {
            htTitle = ctx.getString(R.string.severely_stunted);
            htColor = slw;
            htDeviate = ht - idealHeight;
        } else if (idealHtMap.get("SD3neg") <= ht && ht < idealHtMap.get("SD2neg")) {
            htTitle = ctx.getString(R.string.moderate_stunted);
            htColor = lw;
            htDeviate = ht - idealHeight;
        } else if (idealHtMap.get("SD2neg") <= ht && ht < idealHtMap.get("SD1neg")) {
            htTitle = ctx.getString(R.string.risk_stunt);
            htColor = lwr;
            htDeviate = ht - idealHeight;
        } else if (idealHtMap.get("SD1neg") <= ht && ht < idealHtMap.get("SD0")) {
            htTitle = ctx.getString(R.string.normal_height);
            htDeviate = 0;
            htColor = nw;
        } else if (idealHtMap.get("SD0") <= ht && ht < idealHtMap.get("SD1")) {
            htTitle = ctx.getString(R.string.normal_height);
            htDeviate = 0;
            htColor = nw;
        } else if (idealHtMap.get("SD1") <= ht && ht <= idealHtMap.get("SD2")) {
            htTitle = ctx.getString(R.string.normal_height);
            htDeviate = 0;
            htColor = nw;
        } else if (ht > idealHtMap.get("SD2")) {
            htTitle = ctx.getString(R.string.over_height);
            htDeviate = idealHeight - ht;
            htColor = ob;
        }
    }

    public float getWfhDeviate() {
        return wfhDeviate;
    }

    public int getWfhColor() {
        return wfhColor;
    }

    public String getWfhTitle() {
        return wfhTitle;
    }

    private void getWFHLay() {
        if(idealWFHMap == null){
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");
            Log.e("xyz", "EEEEEEEEEEEEEEEEEEEError Find this");


            return;
        }
        float idealWFH = (wt < idealWFHMap.get("SD1neg")) ? idealWFHMap.get("SD1neg") : (wt > idealWFHMap.get("SD2")) ? idealWFHMap.get("SD2") : 0;
        if (wt < idealWFHMap.get("SD3neg")) {
            wfhDeviate = wt - idealWFH;
            wfhColor = slw;
            wfhTitle = ctx.getString(R.string.severely_wasted);
        } else if (idealWFHMap.get("SD3neg") <= wt && wt < idealWFHMap.get("SD2neg")) {
            wfhDeviate = wt - idealWFH;
            wfhColor = lw;
            wfhTitle = ctx.getString(R.string.low_weight);
        } else if (idealWFHMap.get("SD2neg") <= wt && wt < idealWFHMap.get("SD1neg")) {
            wfhDeviate = wt - idealWFH;
            wfhColor = lwr;
            wfhTitle = ctx.getString(R.string.risk_low_weight);
        } else if (idealWFHMap.get("SD1neg") <= wt && wt < idealWFHMap.get("SD0")) {
            wfhDeviate = 0;
            wfhColor = nw;
            wfhTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWFHMap.get("SD0") <= wt && wt < idealWFHMap.get("SD1")) {
            wfhDeviate = 0;
            wfhColor = nw;
            wfhTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWFHMap.get("SD1") <= wt && wt <= idealWFHMap.get("SD2")) {
            wfhDeviate = 0;
            wfhColor = nw;
            wfhTitle = ctx.getString(R.string.normal_wt);
        } else if (idealWFHMap.get("SD2") < wt && wt <= idealWFHMap.get("SD3")) {
            wfhDeviate = wt - idealWFH;
            wfhColor = ow;
            wfhTitle = ctx.getString(R.string.overweight);
        } else if (wt > idealWFHMap.get("SD3")) {
            wfhDeviate = wt - idealWFH;
            wfhColor = ob;
            wfhTitle = ctx.getString(R.string.obesity);
        }
    }
}
