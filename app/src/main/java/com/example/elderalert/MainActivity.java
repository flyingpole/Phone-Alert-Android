package com.example.elderalert;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestNeededPermissions();

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(36, 48, 36, 36);
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("Elder Alert v1 Test");
        title.setTextSize(28);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        status = new TextView(this);
        status.setText("Service ready. Bluetooth/ESP32 will be added next.");
        status.setTextSize(18);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, 24, 0, 24);
        root.addView(status, new LinearLayout.LayoutParams(-1, -2));

        Button start = makeButton("Start Service");
        start.setOnClickListener(v -> send(AlertService.ACTION_START));
        root.addView(start);

        Button call = makeButton("Test CALL Alert");
        call.setOnClickListener(v -> send(AlertService.ACTION_TEST_CALL));
        root.addView(call);

        Button text = makeButton("Test TEXT Alert");
        text.setOnClickListener(v -> send(AlertService.ACTION_TEST_TEXT));
        root.addView(text);

        Button stop = makeButton("STOP Alert");
        stop.setOnClickListener(v -> send(AlertService.ACTION_STOP));
        root.addView(stop);

        TextView note = new TextView(this);
        note.setText("Incoming call/text receivers are included. Allow Phone and SMS permissions when prompted.");
        note.setTextSize(15);
        note.setPadding(0, 28, 0, 0);
        root.addView(note, new LinearLayout.LayoutParams(-1, -2));

        setContentView(root);
    }

    private Button makeButton(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(0, 12, 0, 12);
        b.setLayoutParams(lp);
        return b;
    }

    private void send(String action) {
        Intent i = new Intent(this, AlertService.class);
        i.setAction(action);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(i);
        else startService(i);
        status.setText("Sent: " + action);
    }

    private void requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_PHONE_STATE
            }, 100);
        }
    }
}
