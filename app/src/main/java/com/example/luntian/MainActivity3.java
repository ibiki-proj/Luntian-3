package com.example.luntian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luntian.model.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity  {

    TextView timer, date, textViewDate;
    int timerHour,timerMinute;
    private TextView selectDate;
    EditText title, desc;
    Button confirm, calendarBtn;
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    DatabaseReference reminderDBRef;
    TextView homeTitle;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

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

        calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, calendar.class);
                intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        //current date
        textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        //time
        timer = findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity3.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                timerHour = hour;
                                timerMinute = minute;
                                //store
                                String time = timerHour + ":" + timerMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    // 12 hour time format
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    // set selected time
                                    timer.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                //set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Display previous time
                timePickerDialog.updateTime(timerHour, timerMinute);
                //show dialog
                timePickerDialog.show();
            }
        });
        //calendar
        selectDate = findViewById(R.id.date);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(MainActivity3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        selectDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });



        //database
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        timer = findViewById(R.id.timer);
        date = findViewById(R.id.date);
        textViewDate = findViewById(R.id.text_view_date);
        confirm = findViewById(R.id.confirm);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reminderDBRef = FirebaseDatabase.getInstance().getReference(userID).child("Reminder");

        String refId = reminderDBRef.push().getKey();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertReminderData();

                //overridePendingTransition(0, 0);
            }
        });
    }

    private void insertReminderData() {

        String t = title.getText().toString();
        String d = desc.getText().toString();
        String tm = timer.getText().toString();
        String dt = date.getText().toString();
        String currentDate = textViewDate.getText().toString();
        if (t.isEmpty()){
            title.setError("Please put event title.");
            title.requestFocus();
        } else if(d.isEmpty()){
            desc.setError("Please enter event description.");
            desc.requestFocus();
        } else if (tm.isEmpty()){
            timer.setError("Please add time.");
            timer.requestFocus();
        } else if (dt.isEmpty()){
            date.setError("Please add event date.");
            date.requestFocus();
        } else {
            Reminder reminder = new Reminder(t,d,tm,dt, currentDate);
            reminderDBRef.push().setValue(reminder);
            Toast.makeText(MainActivity3.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity3.this, ReminderMainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
            startActivity(intent);
        }

    }

}
