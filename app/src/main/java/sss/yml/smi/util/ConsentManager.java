package sss.yml.smi.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import sss.yml.smi.R;

public final class ConsentManager {
    private static final String TAG = "ConsentManager";
    private static boolean requested;

    private ConsentManager() {
    }

    public static void ensure(Activity activity) {
        if (requested) return;
        requested = true;
        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation.requestConsentInfoUpdate(activity, params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity,
                        formError -> {
                            if (formError != null) {
                                Log.w(TAG, "consent form failed: " + formError.getMessage());
                            }
                        }),
                requestError -> Log.w(TAG, "consent info failed: " + requestError.getMessage()));
    }

    public static void showPrivacyOptions(Activity activity) {
        try {
            UserMessagingPlatform.showPrivacyOptionsForm(activity, formError -> {
                if (formError != null) {
                    Toast.makeText(activity, R.string.privacy_options_unavailable,
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (IllegalStateException e) {
            Log.w(TAG, "privacy options unavailable: " + e.getMessage());
            Toast.makeText(activity, R.string.privacy_options_unavailable,
                    Toast.LENGTH_LONG).show();
        }
    }
}
