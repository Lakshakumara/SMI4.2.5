package sss.yml.smi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.StringDef;
import androidx.preference.PreferenceManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import sss.yml.smi.Home.App;

public class LocaleUtils {
    public static final String ENGLISH = "en";
    public static final String SINHALA = "si";
    public static final String TAMIL = "ta";

    public static void initialize(Context context) {
        setLocale(context, getSelectedLanguageId());
    }

    public static void setLocale(Context context, @LocaleDef String language) {
        //setSelectedLanguageId(language);
        updateResources(context, language);
    }

    /*private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }*/
    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        context.createConfigurationContext(configuration);
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENGLISH, SINHALA, TAMIL})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {ENGLISH, SINHALA, TAMIL};
    }

    private static SharedPreferences getDefaultSharedPreference(Context context) {
        if (PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext()) != null)
            return PreferenceManager.getDefaultSharedPreferences(App.getInstance().getApplicationContext());
        else return null;
    }

    public static String getSelectedLanguageId(){
        return getDefaultSharedPreference(App.getInstance().getApplicationContext())
                .getString("language", "en");
    }
}