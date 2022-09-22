package com.example.luntian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class editevent extends AppCompatActivity {

    private EditText eventTitle, eventDesc;
    private TextView eventTime, eventDate;
    private Button updateEvent;
    private ImageView backBtn, deleteEvent;

    int timerHour, timerMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editevent);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventTitle = findViewById(R.id.title);
        eventDesc = findViewById(R.id.desc);
        eventTime = findViewById(R.id.timer);
        eventDate = findViewById(R.id.date);
        updateEvent = findViewById(R.id.updateEvent);
        deleteEvent = findViewById(R.id.deleteEvent);

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        editevent.this,
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
                                    eventTime.setText(f12Hours.format(date));
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

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(editevent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        eventDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }
}