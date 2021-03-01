package com.testing.nokia19.ui.notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.testing.nokia19.R;

public class NotificationView extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        TextView textView = findViewById(R.id.text_dashboard);

        String message = getIntent().getStringExtra("message");
        textView.setText(message);
    }
}