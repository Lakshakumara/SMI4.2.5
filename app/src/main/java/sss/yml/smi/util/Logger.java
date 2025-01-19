package sss.yml.smi.util;

import android.util.Log;

public class Logger {
    static String msg;

    public Logger(String msg) {
        this.msg = msg;
        //Log.e("xyz", " "+msg+" ");
    }
    public static void setMsg(Object... msg) {
        for (Object ss:msg ) {
            Log.e("xyz", " " + ss + " ");
        }
    }
    public static void setMsg(String... msg) {
        for (String ss:msg ) {
            Log.e("xyz", " "+ss+" ");
        }
    }
}
