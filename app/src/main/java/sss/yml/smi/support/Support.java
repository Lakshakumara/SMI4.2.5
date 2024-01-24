package sss.yml.smi.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
/**
 * https://www.paypal.com/donate?business=ZYM5KGGZ6PPM4&item_name=General+Public+free+App+development&currency_code=USD
 * Donate
 * <form action="https://www.paypal.com/donate" method="post" target="_top">
 * <input type="hidden" name="business" value="ZYM5KGGZ6PPM4" />
 * <input type="hidden" name="item_name" value="General Public free App development" />
 * <input type="hidden" name="currency_code" value="USD" />
 * <input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Donate with PayPal button" />
 * <img alt="" border="0" src="https://www.paypal.com/en_LK/i/scr/pixel.gif" width="1" height="1" />
 * </form>
 *
 */
public class Support extends Fragment {
    View view;
    private SupportViewModel mViewModel;

    public static Support newInstance() {
        return new Support();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.donation_fragment, container, false);
        view.findViewById(R.id.btnSupport).setOnClickListener(v -> {
            String url = PreferenceManager.getDefaultSharedPreferences(HomePage.application.getApplicationContext()).getString("urlsupport", "http://lakshakumara.dx.am/bmiSimulator/support.html");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        // TODO: Use the ViewModel
    }

}
