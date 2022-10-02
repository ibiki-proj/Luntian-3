package com.example.luntian;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luntian.model.Reminder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity  {

    TextView timer, date, textViewDate;
    int timerHour,timerMinute;
    private TextView selectDate;
    EditText title, desc;
    Button confirm, calendarBtn;
    Calendar calendar = Calendar.getInstance();
    String timeTonotify;
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
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity3.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeTonotify = i + ":" + i1;
                        timer.setText(FormatTime(i, i1));
                    }
                }, hour, minute, false);
                timePickerDialog.show();
//                TimePickerDialog timePickerDialog = new TimePickerDialog(
//                        MainActivity3.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                                timerHour = hour;
//                                timerMinute = minute;
//                                //store
//                                String time = timerHour + ":" + timerMinute;
//                                SimpleDateFormat f24Hours = new SimpleDateFormat(
//                                        "HH:mm"
//                                );
//                                try {
//                                    Date date = f24Hours.parse(time);
//                                    // 12 hour time format
//                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
//                                            "hh:mm aa"
//                                    );
//                                    // set selected time
//                                    timer.setText(f12Hours.format(date));
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, 12, 0, false
//                );
//                //set transparent background
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                //Display previous time
//                timePickerDialog.updateTime(timerHour, timerMinute);
//                //show dialog
//                timePickerDialog.show();
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
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity3.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectDate.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

//                DatePickerDialog dialog = new DatePickerDialog(MainActivity3.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        month = month + 1;
//                        String date = dayOfMonth + "-" + month + "-" + year;
//                        selectDate.setText(date);
//                    }
//                }, year, month, day);
//                dialog.show();
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
    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }
    private void insertReminderData() {

        String t = title.getText().toString().trim();
        String d = desc.getText().toString().trim();
        String tm = timer.getText().toString().trim();
        String dt = date.getText().toString().trim();
        String currentDate = textViewDate.getText().toString();

//        Toast.makeText(this, tm.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, dt.toString(), Toast.LENGTH_SHORT).show();

        setAlarm(t, d, dt, tm);
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
            Reminder reminder = new Reminder(t, d, tm, dt, currentDate);
            reminderDBRef.push().setValue(reminder);
            //Toast.makeText(MainActivity3.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity3.this, ReminderMainActivity.class);
            intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                title.setText(text.get(0));
            }
        }

    }
    private void setAlarm(String text, String description, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);

        intent.putExtra("event", text);
        intent.putExtra("desc", description);
        intent.putExtra("time", date);
        intent.putExtra("date", time);
        PendingIntent pendingIntent ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("yyyy-M-d hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(this, "Alarm Setup Succesfully", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }


}
