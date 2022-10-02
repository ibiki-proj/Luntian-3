package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity /*implements NavigationView.OnNavigationItemSelectedListener */{

    ImageView humidityBtn, soilmoistureBtn, plannerBtn, plantcyclopediaBtn, plantgrowthBtn, reminderBtn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Toast.makeText(MainActivity.this, "Fetching FCM registration token failed"+task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        // Initialized Firestore
                        FirebaseDatabase rtdb = FirebaseDatabase.getInstance();
                        // Database location reference of the humidity, temperature, fan status
                        DatabaseReference sampleRef = rtdb.getReference("Users").child(FirebaseAuth.getInstance().getUid());

                        Map<String, Object> data = new HashMap<>();

                        data.put("token",token);

                        sampleRef.updateChildren(data);
                        // Upload to firebase
                        //Toast.makeText(MainActivity.this, "Token "+token, Toast.LENGTH_SHORT).show();
                    }
                });
        Intent rIntent = getIntent();
        String uID = rIntent.getStringExtra("UserID");

        TextView userID = findViewById(R.id.userIDTxt);
        userID.setText(uID);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);

                        overridePendingTransition(0, 0);

                        return true;

                    case R.id.planttracking:
                        Intent intent2 = new Intent(getApplicationContext(), plant_growth_tracking.class);
                        intent2.putExtra(userID.getText().toString(), "UserID");
                        startActivity(intent2);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.plantcyclopedia:
                        Intent intent3 = new Intent(getApplicationContext(), plantcyclopedia.class);

                        startActivity(intent3);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.planner:
                        Intent intent4 = new Intent(getApplicationContext(), ReminderMainActivity.class);

                        startActivity(intent4);

                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        Intent intent5 = new Intent(getApplicationContext(), Settings.class);

                        startActivity(intent5);

                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

       /* drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);*/

        humidityBtn = findViewById(R.id.humidityBtn);
        soilmoistureBtn = findViewById(R.id.soilmoistureBtn);
        plannerBtn = findViewById(R.id.plannerBtn);
        plantcyclopediaBtn = findViewById(R.id.plancyclopediaBtn);
        plantgrowthBtn = findViewById(R.id.plantgrowthBtn);
        reminderBtn = findViewById(R.id.reminderBtn);


        humidityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, humidity_monitoring.class);
                startActivity(intent);

            }
        });

        soilmoistureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, soil_moisture.class);
                startActivity(intent);

            }
        });

        plantcyclopediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, plantcyclopedia.class);
                startActivity(intent);

            }
        });

        plantgrowthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, plant_growth_tracking.class);
                startActivity(intent);

            }
        });

        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);

            }
        });
        plannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReminderMainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
}
}


