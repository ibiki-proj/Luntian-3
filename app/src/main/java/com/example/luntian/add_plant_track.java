package com.example.luntian;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_plant_track extends AppCompatActivity {

    private Spinner spnWF1, spnWF2, spnFF1, spnFF2, spnwaterInterval;
    private EditText datePlanted, trackName;
    private ImageView trackplantImg;
    private Button trackPlantBtn;
    private TextView dateTimeDisplay, trackStatus;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private TextView waterTime, fertilizerTime;
    private int timerHour,timerMinute;

    private static final int img_code=1;
    private Uri imageUri = null;
    private Uri imgUri = null;
    ProgressDialog pd;

    String pName;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    FirebaseStorage storage;
    StorageReference storageReference;

    Intent rIn;

    public add_plant_track() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_track);

        /* back button declaration */
        ImageView backBtn = findViewById(R.id.plantbackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        /* spinner declaration */

        spnWF2 = findViewById(R.id.spnWF2);

        String[] spn2 = new String[]{"day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_single, spn2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWF2.setAdapter(adapter2);



        trackName = findViewById(R.id.trackName);
        trackplantImg = findViewById(R.id.planttrackImg);
        trackStatus = findViewById(R.id.plantStatus);

        waterTime = findViewById(R.id.waterTime);
        waterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(add_plant_track.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                                    waterTime.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false);
                //set transparent background
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Display previous time
                timePickerDialog.updateTime(timerHour, timerMinute);
                //show dialog
                timePickerDialog.show();
            }
        });



        /* get current date for date planted */
        datePlanted = findViewById(R.id.trackDatePlanted);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        datePlanted.setText(date);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
                datePlanted.setText(myFormat.format(calendar.getTime()));
            }
        };
        datePlanted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(add_plant_track.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(userID).child("TrackPlant");
        storage = FirebaseStorage.getInstance();
        String refId = mReference.push().getKey();

        pd = new ProgressDialog(this);


        rIn = getIntent();
        if (rIn != null){
            String newtrackName = rIn.getStringExtra("AddNewTrack");
            //String newTrackImg = rIn.getStringExtra("AddNewImg");
            trackName.setText(newtrackName);
            //Picasso.get().load(newTrackImg).into(trackplantImg);
            pName = trackName.getText().toString().trim();



        }
        trackplantImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, img_code);

            }
        });
        trackPlantBtn = findViewById(R.id.trackPlantBtn);
        trackPlantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pName = trackName.getText().toString().trim();
                String pPlanted = datePlanted.getText().toString().trim();
                String spinnerWF2 = spnWF2.getSelectedItem().toString().trim();
                String waterTimeTxt = waterTime.getText().toString().trim();
                String plantStatusTxt = trackStatus.getText().toString().trim();


                if (trackplantImg.getDrawable() == null){
                    Toast.makeText(add_plant_track.this, "Please choose an image for your plant.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    pd.setTitle("Adding plant...");
                    pd.show();
                    storageReference = storage.getReference(userID).child("TrackImage").child(imageUri.getLastPathSegment());
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();

                                    DatabaseReference newPost = mReference.push();
                                    newPost.child("ID").setValue(refId);
                                    newPost.child("Image").setValue(task.getResult().toString());
                                    newPost.child("PlantName").setValue(pName);
                                    newPost.child("DatePlanted").setValue(pPlanted);
                                    newPost.child("WaterFrequency").setValue(spinnerWF2);
                                    newPost.child("WaterTime").setValue(waterTimeTxt);


                                    newPost.child("PlantStatus").setValue(plantStatusTxt);

                                    pd.dismiss();
                                    Toast.makeText(add_plant_track.this, "New Plant to Track Added!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(add_plant_track.this, plant_growth_tracking.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == img_code && resultCode == RESULT_OK){
            imageUri = data.getData();
            trackplantImg.setImageURI(imageUri);
        }
    }
}