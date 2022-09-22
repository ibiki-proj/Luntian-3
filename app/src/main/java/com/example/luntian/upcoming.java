package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.adapter.ListAdapter;
import com.example.luntian.adapter.upAdapter;
import com.example.luntian.model.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class upcoming extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference database;
    com.example.luntian.adapter.upAdapter upAdapter;
    ArrayList<Reminder> list;
    Calendar calendar = Calendar.getInstance();
    String currentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()).toString();
    String tomorrow, week;
    TextView homeTitle;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.planner);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.planttracking:
                        Intent intent2 = new Intent(getApplicationContext(), plant_growth_tracking.class);

                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.plantcyclopedia:
                        Intent intent3 = new Intent(getApplicationContext(), plantcyclopedia.class);

                        startActivity(intent3);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.planner:
                        Intent intent4 = new Intent(getApplicationContext(), ReminderMainActivity.class);

                        startActivity(intent4);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        Intent intent5 = new Intent(getApplicationContext(), Settings.class);

                        startActivity(intent5);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        //tomorrow
        Date date;
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        Format format = new SimpleDateFormat("dd-MM-yyyy");
        tomorrow = format.format(date);

        //week
        Date datee;
        calendar.add(Calendar.DATE, 7);
        datee = calendar.getTime();
        Format formatt = new SimpleDateFormat("dd-MM-yyyy");
        week = format.format(datee);


        //retrieve data from database
        recyclerView = findViewById(R.id.userList);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference(userID).child("Reminder");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Reminder>();
        upAdapter = new upAdapter(this, list);
        recyclerView.setAdapter(upAdapter);
        Intent rInt = getIntent();
        String edPID = rInt.getStringExtra("childKey");

        database.orderByChild("dt").startAt(tomorrow).endAt(week).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Reminder reminder = dataSnapshot.getValue(Reminder.class);
                    list.add(reminder);
                }

                ListAdapter adapter = new ListAdapter(upcoming.this, list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnn = (Button) findViewById(R.id.calendar);
        btnn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(upcoming.this, calendar.class);
                startActivity(intent);
            }
        });
    }
}