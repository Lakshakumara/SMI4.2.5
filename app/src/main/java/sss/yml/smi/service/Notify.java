package sss.yml.smi.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

import sss.yml.smi.R;
import sss.yml.smi.util.NortificationActivity;

public class Notify extends Service {
    private final String CHANNEL_ID = "m";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String KEY_TEXT_REPLY = "key_text_reply";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        if(db == null) return;
        createNotificationChannel();
        db.collection("BMIS")
                .document("news")
                .addSnapshotListener((snapshot, e) -> {
                    if (snapshot != null && snapshot.exists()) {
                        Map<String, Object> data = snapshot.getData();
                        if (data != null) {
                            //Logger.setMsg("xyz", "News Received " + data);
                            long nosl = (long) data.get("nos");
                            int nos = (int) nosl;
                            String newsId = (String) data.get("newsId");
                            String newMessage = data.get("title") == null ? "" : (String) data.get("title");
                            String newSubTitle = data.get("subTitle") == null ? "" : (String) data.get("subTitle");
                            String newDescription = data.get("description") == null ? "" : (String) data.get("description");
                            int nosShown = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt(newsId, 0);
                            if (nosShown < nos) {
                               // Logger.setMsg("xyz", "shown " + nosShown + " " + newsId);
                                simpleNotification(newsId, nosShown + 1, newMessage, newSubTitle, newDescription);
                            } else {
                               // Logger.setMsg("xyz", "all shown");
                            }
                        }
                    }
                });

        final DocumentReference docRef = db.collection("share").document(Objects.requireNonNull(androidx.preference.PreferenceManager.getDefaultSharedPreferences(this).getString("uid", null)));
        docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                //Logger.setMsg("xyz", "Listen failed.", e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                //Logger.setMsg("xyz", "Current data: " + snapshot.getData());
            } else {
               //Logger.setMsg("xyz", "Current data: null");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "manjula";
            String description = "author";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void simpleNotification(String newsId, int nosShown, String title, String subTitle, String description){
        Intent intentp = new Intent(this, ReplyReceive.class);
        final PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentp, PendingIntent.FLAG_MUTABLE);

        String replyLabel = "Your Response";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_send, "Reply", pIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Intent intent = new Intent(this, NortificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .addAction(action)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(newsId, nosShown).apply();
    }
    private void replyNotification(){
        Intent intent = new Intent(this, NortificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        String replyLabel = "Reply";//getResources().getString(R.string.reply_label)";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();
        // Build a PendingIntent for the reply action to trigger.
        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                1,
                getMessageReplyIntent(1),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_send,
                "Reply", replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("My title")
                .setContentText("my context")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line...Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private Intent getMessageReplyIntent(int i) {
        return new Intent(this, NortificationActivity.class);
    }

    /*protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//Unregister the BroadcastReceiver when the notification is tapped//
            unregisterReceiver(stopReceiver);
//Stop the Service//
            stopSelf();
        }
    };*/

    /*private void simpleNotification(String newsId, int nosShown, String title, String subTitle, String description){
        //Intent intent = new Intent(this, NortificationActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //.setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt(newsId, nosShown).apply();
        Log.e("xyz", "saved "+nosShown+" "+newsId);
    }*/
    private void buildNotification(String title, String msg) {
        String stop = "stop";
        //registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

// Create the persistent notification//
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
//Make this notification ongoing so it can’t be dismissed by the user//
                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(android.R.drawable.ic_menu_send);
        startForeground(1, builder.build());
    }
}
