package sss.yml.smi.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

public class NotificationReceiver extends BroadcastReceiver {
    final String KEY_TEXT_REPLY = "key_text_reply";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(action)){
            startNotification(context);
        }
    }
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }
    private void startNotification(Context context){
        Intent i = new Intent(context, Notify.class);
        try {
            // API 26+: plain startService() throws IllegalStateException when the
            // app is in background (incl. boot receivers). startForegroundService()
            // is exempted for BOOT_COMPLETED; Notify.onCreate() calls
            // startForeground() immediately. ContextCompat falls back to
            // startService() below API 26.
            ContextCompat.startForegroundService(context, i);
        } catch (RuntimeException e) {
            // Never crash the boot path on OEM quirks — degrade silently.
            try {
                context.startService(i);
            } catch (RuntimeException ignored) {
            }
        }
    }

}
