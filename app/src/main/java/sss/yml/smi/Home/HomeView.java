package sss.yml.smi.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import sss.yml.smi.R;

public class HomeView extends Fragment {
    WebView web;
    MODE mode = MODE.HOME_VIEW;
    public enum MODE{
        BAN, HOME_VIEW, PROFILE, SMI, GMP
    }

    public HomeView() { }

    public HomeView(MODE mode) {
        this.mode = mode;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homeview, container, false);
        web = view.findViewById(R.id.webViewer);
        if(isConnected())
            web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        else web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String theme = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getString("theme", "3");
        if(theme.equalsIgnoreCase("2") && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
            WebSettingsCompat.setForceDark(web.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        String url;
        switch (mode){
            case BAN:
                url = PreferenceManager.getDefaultSharedPreferences(HomePage.application.getApplicationContext()).getString("urlban", "");
                break;
            case GMP:
                url = PreferenceManager.getDefaultSharedPreferences(HomePage.application.getApplicationContext()).getString("urlgmp", "");
                break;
            default:
                url = PreferenceManager.getDefaultSharedPreferences(HomePage.application.getApplicationContext()).getString("urlhomeview", "");
        }
        web.loadUrl(url);

        web.setWebViewClient(new MyBrowser());
        return view;
    }
    public static boolean isConnected() {
        if(HomePage.application == null) return false;
        ConnectivityManager cm = (ConnectivityManager) HomePage.application
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        }
        return false;
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if(errorCode == -2 && HomePage.progress.isShowing()) HomePage.progress.dismiss();
            web.loadData("Connect to internet for more info","Text/html", "utf-8");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(HomePage.progress.isShowing()) HomePage.progress.dismiss();
        }
    }
}