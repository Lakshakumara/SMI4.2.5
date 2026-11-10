package sss.yml.smi.Home;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;

import sss.yml.smi.R;
import sss.yml.smi.util.CloudRestore;
import sss.yml.smi.util.ProgressBanner;

public class HomePage extends AppCompatActivity{
    public static Application application;
    public static ProgressBanner progress;
    //private AdView mAdView;
    private AppBarConfiguration mAppBarConfiguration;
   // CollapsingToolbarLayout toolBarLayout;
    Toolbar toolbar;
    public void setActionBarTitle(String title) {
        if(toolbar == null) return;
        toolbar.setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = App.getInstance();
        App.getInstance().initAppLanguage(this);
        progress = new ProgressBanner(this);
        setContentView(R.layout.activity_home_page);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            TextView tbt = (TextView) f.get(toolbar);
            assert tbt != null;
            tbt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tbt.setSingleLine(true);
            tbt.setSelected(true);
            tbt.setMarqueeRepeatLimit(-1);
        }catch(NoSuchFieldException ignored){} catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /*
        boolean adEnable = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("ad", true);
        if (adEnable) {
            mAdView = findViewById(R.id.adView1);
            if (BuildConfig.DEBUG) {
                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        mAdView.loadAd(new AdRequest.Builder().build());
                    }
                });
                /*AdRequest adRequest = new AdRequest.Builder()
                        //.addTestDevice("71228269EE062EAE468CEE26EEEC9AEB")
                        .build();
                mAdView.loadAd(adRequest);*/
            /*}else {
                MobileAds.initialize(this, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        mAdView.loadAd(new AdRequest.Builder().build());
                    }
                });
            }

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    mAdView.setVisibility(View.GONE);
                    Logger.setMsg(adError.getDomain() + "\t" + adError.getMessage());
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });
        } else mAdView.setVisibility(View.GONE);
*/
        progress.setMessage("Loading...");
        progress.show();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView author = navigationView.getHeaderView(0).findViewById(R.id.author);
        String r = getResources().getString(R.string.phirange);
        String range = "range";
        if (r.equalsIgnoreCase("1")) range = "rangesi";
        if (r.equalsIgnoreCase("2")) range = "rangeta";
        range = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(range, "");
        author.setText(range);
        //@SuppressLint("HardwareIds")
        String pid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        boolean isSMI = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("isSMI", false);
        if(!isSMI) isSMI = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("gsmi", false);
        boolean isWP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("isWP", false);
        if(!isWP) isWP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("gwp", false);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_admin).setVisible(pid.equalsIgnoreCase("e1b0b7bb251ef9ae"));
        nav_Menu.findItem(R.id.nav_smi).setVisible(isSMI);
        nav_Menu.findItem(R.id.wtpost).setVisible(isWP);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.homeView, R.id.nav_smi, R.id.wtpost, R.id.nav_general, R.id.setting,
                R.id.nav_userguide, R.id.nav_profile, R.id.nav_about, R.id.nav_rate, R.id.nav_support, R.id.nav_admin)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().findItem(R.id.nav_web).setOnMenuItemClickListener(item -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://bmisimulator.web.app")));
            } catch (Exception ignored) {
            }
            return true;
        });

        CloudRestore.maybeAutoPrompt(this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(progress.isShowing()) progress.dismiss();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}