package com.example.elderalert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class AlertService extends Service {
    public static final String ACTION_START = "com.example.elderalert.START";
    public static final String ACTION_TEST_CALL = "com.example.elderalert.TEST_CALL";
    public static final String ACTION_TEST_TEXT = "com.example.elderalert.TEST_TEXT";
    public static final String ACTION_STOP = "com.example.elderalert.STOP";
    public static final String ACTION_INCOMING_CALL = "com.example.elderalert.INCOMING_CALL";
    public static final String ACTION_INCOMING_TEXT = "com.example.elderalert.INCOMING_TEXT";
    private static final String CHANNEL_ID = "elder_alert_service";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();
        startForeground(1, buildNotification("Running"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null && intent.getAction() != null ? intent.getAction() : ACTION_START;
        switch (action) {
            case ACTION_TEST_CALL:
            case ACTION_INCOMING_CALL:
                Log.i("ElderAlert", "CALL alert would be sent to ESP32 here");
                updateNotification("CALL alert triggered");
                break;
            case ACTION_TEST_TEXT:
            case ACTION_INCOMING_TEXT:
                Log.i("ElderAlert", "TEXT alert would be sent to ESP32 here");
                updateNotification("TEXT alert triggered");
                break;
            case ACTION_STOP:
                Log.i("ElderAlert", "STOP alert would be sent to ESP32 here");
                updateNotification("Stopped");
                break;
            default:
                updateNotification("Running");
                break;
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Elder Alert Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
    }

    private Notification buildNotification(String text) {
        Intent openIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(
                this, 0, openIntent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );
        Notification.Builder b = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? new Notification.Builder(this, CHANNEL_ID)
                : new Notification.Builder(this);
        return b.setContentTitle("Elder Alert")
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pi)
                .setOngoing(true)
                .build();
    }

    private void updateNotification(String text) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, buildNotification(text));
    }
}
