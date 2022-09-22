package com.example.luntian;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.luntian.model.Device;
import com.example.luntian.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.HashMap;
import java.util.Map;

public class humidity_monitoring extends AppCompatActivity {

    TextView homeTitle;
    TextView humidityValue;
    TextView temperatureValue;
    TextView minTempTxtValue;
    SwitchCompat fanStatusValue;
    Button plusBtn;
    Button minusBtn;
    // Declare Firestore
    FirebaseDatabase rtdb;

    String devicePath = "";


    // Upload data
    void upload( String fieldName,Object value){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("sampletest1").child(devicePath);

        Map<String, Object> data = new HashMap<>();

        data.put(fieldName,value);

        sampleRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot document = task.getResult();
                    if (document.exists()) {
                        sampleRef.updateChildren(data);
                    } else {
                        sampleRef.setValue(data);
                    }
                } else {
                    sampleRef.setValue(data);
                }
            }
        });

    }


    void minusTemp(){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("sampletest1").child(devicePath);
        Map<String, Object> data = new HashMap<>();
        int currentMinTemp = Integer.parseInt(minTempTxtValue.getText().toString());
        currentMinTemp -= 1;

        data.put("minTemp",currentMinTemp);
        try {

            sampleRef.updateChildren(data);


            System.out.println(currentMinTemp--);
            Toast.makeText(this, "Minus", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }
    void plusTemp(){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("sampletest1").child(devicePath);
        Map<String, Object> data = new HashMap<>();
        int currentMinTemp = Integer.parseInt(minTempTxtValue.getText().toString());
        currentMinTemp += 1;

        data.put("minTemp",currentMinTemp);
        try {

            sampleRef.updateChildren(data);


            System.out.println(currentMinTemp++);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }
    void checkedValue(String fieldName,boolean currentValue){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("sampletest1").child(devicePath);
        Map<String, Object> data = new HashMap<>();

        data.put(fieldName,currentValue);

        try {

            sampleRef.updateChildren(data);
            Toast.makeText(this, "Update Data", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }

    void listenToData(){
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("sampletest1").child(devicePath);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Device data = snapshot.getValue(Device.class);


//                    Toast.makeText(humidity_monitoring.this, "Listening data"+data.minTemp, Toast.LENGTH_SHORT).show();
                    int minTemp = Integer.parseInt(Integer.toString(data.minTemp)) ;
                    int temp = Integer.parseInt(Integer.toString(data.temperature)) ;
                    int humidity = Integer.parseInt(Integer.toString(data.humidity)) ;
                    humidityValue.setText(Integer.toString(data.humidity)+" %");
                    temperatureValue.setText(Integer.toString(data.temperature)+" °C");
                    minTempTxtValue.setText(Integer.toString(data.minTemp));


//                    System.out.println(snapshot.getData().get("humidity").toString());
//                    System.out.println(snapshot.getData().get("minTemp").toString());
//                    System.out.println(snapshot.getData().get("temperature").toString());
                    ;
                    if(data.temperature > data.minTemp){
                        fanStatusValue.setChecked(true);
                    }else{
                        fanStatusValue.setChecked(false);
                    }

                    checkedValue("fan_status",fanStatusValue.isChecked());

                }
                Toast.makeText(humidity_monitoring.this, "New data changes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(humidity_monitoring.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
        sampleRef.addValueEventListener(postListener);
        System.out.println("Listening to data");


    }

    void listenToUsers(){
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("Users").child(FirebaseAuth.getInstance().getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){

                    UserData data = snapshot.getValue(UserData.class);

                    devicePath = data.device;
                    listenToData();

                    Toast.makeText(humidity_monitoring.this, "Path"+devicePath, Toast.LENGTH_SHORT).show();

            }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(humidity_monitoring.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
        sampleRef.addValueEventListener(postListener);
        System.out.println("Listening to user");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_monitoring);
        // Listen to data
        listenToUsers();



        humidityValue = findViewById(R.id.humidityTxtValue);
        temperatureValue = findViewById(R.id.temperatureTxtValue);
        fanStatusValue = findViewById(R.id.switchFanStatusValue);
        minTempTxtValue = findViewById(R.id.minTempTxtValue);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);

        homeTitle = findViewById(R.id.appTitle);
        homeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View currentHumidity = findViewById(R.id.currentHumidity);
        View setHumidity = findViewById(R.id.setHumidity);


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusTemp();

            }

        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusTemp();

            }

        });
        fanStatusValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fanStatusValue.isChecked()){
                    System.out.println("Turn ON");
                    upload("fan_status",true);
                }else{
                    System.out.println("Turn Off");
                    upload("fan_status",false);
                }

            }

        });
        currentHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(humidity_monitoring.this, "Set Humidity to Firebase", Toast.LENGTH_SHORT).show();
                upload("humidity",60);
                upload("temperature",200);

//                upload(sampleRef);
//                setHumidity.setVisibility(View.VISIBLE);
//                setHumidityBtn.setVisibility(View.VISIBLE);
            }
        });




    }
}