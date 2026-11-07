package sss.yml.smi.login;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import sss.yml.smi.R;

public class Ban extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.ban, new HomeView(HomeView.MODE.BAN))
                .disallowAddToBackStack()
                .commit();
        //finish();*/
        WebView web = findViewById(R.id.webViewer);
        if(isConnected())
            web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        else  web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.loadUrl(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("urlban", ""));
        web.setWebViewClient(new MyBrowser());
    }
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities caps = cm.getNetworkCapabilities(network);
            return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
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
            if(errorCode == -2)
                view.loadData("Connect to internet for more info","Text/html", "utf-8");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
