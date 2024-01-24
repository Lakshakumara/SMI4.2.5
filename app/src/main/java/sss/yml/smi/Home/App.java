package sss.yml.smi.Home;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import sss.yml.smi.util.LocaleUtils;

public class App extends android.app.Application {

    private static App appInstance;

    public static synchronized App getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("theme", "3");

        AppCompatDelegate.setDefaultNightMode(Integer.parseInt(theme));
        appInstance = this;
    }

    public void initAppLanguage(Context context) {
        LocaleUtils.initialize(context);
    }
    public void initAppLanguage(Context context, String language) {
        LocaleUtils.setLocale(context, language);
    }

}