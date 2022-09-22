package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luntian.adapter.ListAdapter;
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

public class ReminderMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    String tomorrow;
    Calendar calendar = Calendar.getInstance();
    String currentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()).toString();
    com.example.luntian.adapter.ListAdapter ListAdapter;
    ArrayList<Reminder> list;
    TextView homeTitle;
    ImageView addEventBtn;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.planner);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);

                        overridePendingTransition(0,0);
                        finish();
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


        addEventBtn = findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderMainActivity.this, MainActivity3.class);
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        Date date;
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        Format format = new SimpleDateFormat("dd-MM-yyyy");
        tomorrow = format.format(date);

        recyclerView = findViewById(R.id.userList);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference(userID).child("Reminder");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Reminder>();
        ListAdapter = new ListAdapter(this,list);
        recyclerView.setAdapter(ListAdapter);

        database.orderByChild("dt").startAt(currentdate).endAt(tomorrow).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Reminder reminder = dataSnapshot.getValue(Reminder.class);
                        list.add(reminder);
                    Toast.makeText(ReminderMainActivity.this, ""+reminder.getcurrentDate(), Toast.LENGTH_SHORT).show();
                    }
                    ListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //upcoming button
        Button button = (Button) findViewById(R.id.upcoming);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ReminderMainActivity.this, upcoming.class);
                startActivity(intent);

            }
        });

    }

}