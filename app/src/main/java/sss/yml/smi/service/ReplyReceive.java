package sss.yml.smi.service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

public class ReplyReceive extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this,getMessageText(getIntent()), Toast.LENGTH_SHORT).show();
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(Notify.KEY_TEXT_REPLY);
        }
        Toast.makeText(this,"Remote input is null",Toast.LENGTH_SHORT).show();

        return null;
    }
}