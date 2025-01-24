package sss.yml.smi.util;

import com.google.android.gms.ads.AdRequest;

public class AdLoader {
    public static AdRequest getRequest() {
        // my 71228269EE062EAE468CEE26EEEC9AEB
        //List<String> testDeviceIds = Arrays.asList("71228269EE062EAE468CEE26EEEC9AEB");
        //RequestConfiguration configuration =
        //        new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        //MobileAds.setRequestConfiguration(configuration);
        AdRequest request = new AdRequest.Builder().build();
        return request;
    }
}
