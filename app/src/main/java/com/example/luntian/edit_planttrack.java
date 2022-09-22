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
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class edit_planttrack extends AppCompatActivity {

    ImageView backBtn, ePlantImg, edDeleteBtn;
    View delete;
    EditText ePlantName, ePlantDate;
    TextView ePlantID, eWaterTime;
    Spinner ePStatus, eWFreq;
    Button updateBtn, newImgBtn, yesBtn, noBtn;
    Calendar calendar;
    private int timerHour,timerMinute;

    private static final int img_code=1;
    private Uri imageUri = null;

    FirebaseStorage mStorage;
    StorageReference mSRef;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_planttrack);

        backBtn = findViewById(R.id.plantbackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ePlantName = findViewById(R.id.eTrackPlantName);
        ePlantDate = findViewById(R.id.trackDatePlanted);
        ePlantID = findViewById(R.id.ePlantID);
        ePlantImg = findViewById(R.id.ePlantImg);


        Intent rInt = getIntent();
        String edPID = rInt.getStringExtra("childKey");

        ePlantID.setText(edPID);

        delete = findViewById(R.id.deleteLayout);
        edDeleteBtn = findViewById(R.id.deleteBtn);
        edDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.setVisibility(View.VISIBLE);
            }
        });
        yesBtn = findViewById(R.id.delYesBtn);
        noBtn = findViewById(R.id.delNoBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase mDB = FirebaseDatabase.getInstance();
                DatabaseReference mRef = mDB.getReference(userID).child("TrackPlant");

                Query delData = mRef.child(edPID);
                delData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            mRef.child(edPID).removeValue();
                            Toast.makeText(edit_planttrack.this, "Plant deleted!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(edit_planttrack.this, plant_growth_tracking.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.setVisibility(View.GONE);
            }
        });

        ePStatus = findViewById(R.id.spnStatus);
        String[] spnStr = new String[]{"Harvested","Planted"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_single, spnStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ePStatus.setAdapter(adapter);

        eWFreq = findViewById(R.id.spnFreq);
        String[] freqStr = new String[]{"day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_single, freqStr);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eWFreq.setAdapter(adapter2);

        eWaterTime = findViewById(R.id.edWaterTime);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase mDB = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDB.getReference(userID).child("TrackPlant");

        Query getData = mRef.child(edPID);
        getData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String imgUri = snapshot.child("Image").getValue().toString();
                    String datePlanted = snapshot.child("DatePlanted").getValue().toString();
                    String plantName = snapshot.child("PlantName").getValue().toString();
                    String plantStatus = snapshot.child("PlantStatus").getValue().toString();
                    String waterFreq = snapshot.child("WaterFrequency").getValue().toString();
                    String waterTime = snapshot.child("WaterTime").getValue().toString();

                    Picasso.get().load(imgUri).into(ePlantImg);
                    ePlantName.setText(plantName);
                    ePlantDate.setText(datePlanted);
                    ePStatus.setSelection(((ArrayAdapter<String>)ePStatus.getAdapter()).getPosition(plantStatus));
                    eWFreq.setSelection(((ArrayAdapter<String>)eWFreq.getAdapter()).getPosition(waterFreq));
                    eWaterTime.setText(waterTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatePickerDialog.OnDateSetListener edDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
                ePlantDate.setText(myFormat.format(calendar.getTime()));
            }
        };

        ePlantDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(edit_planttrack.this,edDate,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eWaterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(edit_planttrack.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        timerHour = hour;
                        timerMinute = minute;
                        String time = timerHour + " : " + timerMinute;
                        SimpleDateFormat f24hrs = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24hrs.parse(time);
                            // 12 hour time format
                            SimpleDateFormat f12Hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            // set selected time
                            eWaterTime.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, 12, 0, false);
                tpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tpd.updateTime(timerHour, timerMinute);
                tpd.show();
            }
        });
        newImgBtn = findViewById(R.id.changeImgBtn);
        newImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1, img_code);
            }
        });




        pd = new ProgressDialog(this);
        pd.setTitle("Updating...");

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pd.show();
                Query updateData = mRef.child(edPID);
                updateData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String imgUri = snapshot.child("Image").getValue().toString();
                            String plantName = ePlantName.getText().toString();
                            String plantDate = ePlantDate.getText().toString();
                            String status = ePStatus.getSelectedItem().toString();
                            String frequency = eWFreq.getSelectedItem().toString();
                            String wTime = eWaterTime.getText().toString();


                            mRef.child(edPID).child("PlantName").setValue(plantName);
                            mRef.child(edPID).child("DatePlanted").setValue(plantDate);
                            mRef.child(edPID).child("PlantStatus").setValue(status);
                            mRef.child(edPID).child("WaterFrequency").setValue(frequency);
                            mRef.child(edPID).child("WaterTime").setValue(wTime);

                            /*
                            mStorage = FirebaseStorage.getInstance();
                            mSRef = mStorage.getReference(userID).child(userID).child("TrackImage").child(imageUri.getLastPathSegment());
                            mSRef.child(imgUri).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                                    String newImgUri = downloadUrl.toString();
                                    mRef.child(edPID).child("Image").setValue(newImgUri);
                                }
                            });
                            */
                        }
                        pd.dismiss();
                        Toast.makeText(edit_planttrack.this, "Data successfully updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img_code && resultCode == RESULT_OK){
            imageUri = data.getData();
            ePlantImg.setImageURI(imageUri);
        }
    } */
}