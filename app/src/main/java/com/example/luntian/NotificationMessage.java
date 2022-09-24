package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationMessage extends AppCompatActivity {
    TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        textView = findViewById(R.id.tv_message);
        textView2 = findViewById(R.id.tv_desc);

        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));
        textView2.setText(bundle.getString("desc"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(NotificationMessage.this, MainActivity.class));
                finish();
            }
        }, 2000);


    }
}
