package sss.yml.smi.Home;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import sss.yml.smi.util.EdgeToEdge;
import sss.yml.smi.util.LocaleUtils;

public class App extends android.app.Application implements Application.ActivityLifecycleCallbacks {

    private static App appInstance;

    public static synchronized App getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        String theme = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("theme", "3");

        AppCompatDelegate.setDefaultNightMode(Integer.parseInt(theme));
        appInstance = this;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        EdgeToEdge.apply(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public void initAppLanguage(Context context) {
        LocaleUtils.initialize(context);
    }
    public void initAppLanguage(Context context, String language) {
        LocaleUtils.setLocale(context, language);
    }

}