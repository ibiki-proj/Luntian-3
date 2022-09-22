package com.example.luntian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class main_navbar extends AppCompatActivity {

    Button sidehumidityBtn, sidesoilmoistureBtn, sideplannerBtn, sideplantcyclopediaBtn, sideplantgrowthBtn, sidereminderBtn;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navbar);

        sidehumidityBtn = findViewById(R.id.sideHumidityBtn);
        sidesoilmoistureBtn = findViewById(R.id.sideSoilMoistureBtn);
        sideplannerBtn = findViewById(R.id.sidePlannerBtn);
        sideplantcyclopediaBtn = findViewById(R.id.sidePlantcyclopediaBtn);
        sideplantgrowthBtn = findViewById(R.id.sideTrackPlantBtn);
        sidereminderBtn = findViewById(R.id.sideReminderBtn);

        sidehumidityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, humidity_monitoring.class);
                startActivity(intent);

            }
        });
    }
}