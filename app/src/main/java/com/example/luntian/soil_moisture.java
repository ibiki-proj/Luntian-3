package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.luntian.model.Device;
import com.example.luntian.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class soil_moisture extends AppCompatActivity {
    FirebaseDatabase rtdb;

    SwitchCompat fanStatusValue1
            ,fanStatusValue2
            ,fanStatusValue3
            ,fanStatusValue4;
String devicePath = "";
    Button minusBtn4 ,
            minusBtn3,
            minusBtn2,
            minusBtn1,
            plusBtn4,
            plusBtn3,
            plusBtn2,
            plusBtn1;
    TextView minSoilMoist4,
            minSoilMoist3,
            minSoilMoist2,
            minSoilMoist1,
            currentSoilMoist4,
            currentSoilMoist3,
            currentSoilMoist2,
            currentSoilMoist1;
    TextView homeTitle;
    void checkedValue(String fieldName,boolean currentValue){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("devices").child(devicePath);
        Map<String, Object> data = new HashMap<>();

        data.put(fieldName,currentValue);

        try {

            sampleRef.updateChildren(data);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }


    void plusValue(String fieldName,int currentValue){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("devices").child(devicePath);
        Map<String, Object> data = new HashMap<>();
        currentValue+=1;
        data.put(fieldName,currentValue);

        try {

            sampleRef.updateChildren(data);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }
    void minusValue(String fieldName,int currentValue){
        // Initialized Firestore
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("devices").child(devicePath);
        Map<String, Object> data = new HashMap<>();
        currentValue-=1;
        data.put(fieldName,currentValue);

        try {

            sampleRef.updateChildren(data);
            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            sampleRef.setValue(data);
            Toast.makeText(this, "Error"+e, Toast.LENGTH_SHORT).show();
        }
    }
    void listenToData(){
        rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("devices").child(devicePath);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Device data = snapshot.getValue(Device.class);



                    //Current Moist
                    currentSoilMoist1.setText(Integer.toString(data.currentSoilMoist1));
                    currentSoilMoist2.setText(Integer.toString(data.currentSoilMoist2));
                    currentSoilMoist3.setText(Integer.toString(data.currentSoilMoist3));
                    currentSoilMoist4.setText(Integer.toString(data.currentSoilMoist4));

                    //Minimum Moist
                    minSoilMoist1.setText(Integer.toString(data.minSoilMoist1));
                    minSoilMoist2.setText(Integer.toString(data.minSoilMoist2));
                    minSoilMoist3.setText(Integer.toString(data.minSoilMoist3));
                    minSoilMoist4.setText(Integer.toString(data.minSoilMoist4));

                    //Fan Status
                    // 1
                    if(data.currentSoilMoist1 > data.minSoilMoist1 ){
                        fanStatusValue1.setChecked(false);

                    }else{
                        fanStatusValue1.setChecked(true);
                    }
                    // 2
                    if(data.currentSoilMoist2 > data.minSoilMoist2 ){
                        fanStatusValue2.setChecked(false);
                    }else{
                        fanStatusValue2.setChecked(true);
                    }
                    // 3
                    if(data.currentSoilMoist3 > data.minSoilMoist3 ){
                        fanStatusValue3.setChecked(false);
                    }else{
                        fanStatusValue3.setChecked(true);
                    }
                    // 4
                    if(data.currentSoilMoist4 > data.minSoilMoist4 ){
                        fanStatusValue4.setChecked(false);
                    }else{
                        fanStatusValue4.setChecked(true);
                    }








                }
                Toast.makeText(soil_moisture.this, "New data changes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(soil_moisture.this, "Cancelled", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(soil_moisture.this, "Path"+devicePath, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(soil_moisture.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
        sampleRef.addValueEventListener(postListener);
        System.out.println("Listening to user");


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);

        listenToUsers();

        minusBtn4 = findViewById(R.id.minusBtn4);
        minusBtn3 = findViewById(R.id.minusBtn3);
        minusBtn2 = findViewById(R.id.minusBtn2);
        minusBtn1 = findViewById(R.id.minusBtn1);
        plusBtn4 = findViewById(R.id.plusBtn4);
        plusBtn3 = findViewById(R.id.plusBtn3);
        plusBtn2 = findViewById(R.id.plusBtn2);
        plusBtn1 = findViewById(R.id.plusBtn1);

        minSoilMoist4= findViewById(R.id.minSoilMoist4);
        minSoilMoist3= findViewById(R.id.minSoilMoist3);
        minSoilMoist2= findViewById(R.id.minSoilMoist2);
        minSoilMoist1= findViewById(R.id.minSoilMoist1);

        currentSoilMoist4= findViewById(R.id.currentSoilMoist4);
        currentSoilMoist3= findViewById(R.id.currentSoilMoist3);
        currentSoilMoist2= findViewById(R.id.currentSoilMoist2);
        currentSoilMoist1= findViewById(R.id.currentSoilMoist1);

        fanStatusValue1 = findViewById(R.id.switchSprinkler1Status);
        fanStatusValue2 = findViewById(R.id.switchSprinkler2Status);
        fanStatusValue3 = findViewById(R.id.switchSprinkler3Status);
        fanStatusValue4 = findViewById(R.id.switchSprinkler4Status);

        homeTitle = findViewById(R.id.appTitle);
        homeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(soil_moisture.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        //Add Buttons
        plusBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist1.getText().toString());

                plusValue("minSoilMoist1",currentMinMoist);
            }
        });

        plusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist2.getText().toString());

                plusValue("minSoilMoist2",currentMinMoist);
            }
        });
        plusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist3.getText().toString());

                plusValue("minSoilMoist3",currentMinMoist);
            }
        });
        plusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist4.getText().toString());

                plusValue("minSoilMoist4",currentMinMoist);
            }
        });
//Add Buttons
        minusBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist1.getText().toString());

                minusValue("minSoilMoist1",currentMinMoist);
            }
        });

        minusBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist2.getText().toString());

                minusValue("minSoilMoist2",currentMinMoist);
            }
        });
        minusBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist3.getText().toString());

                minusValue("minSoilMoist3",currentMinMoist);
            }
        });
        minusBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentMinMoist = Integer.parseInt(minSoilMoist4.getText().toString());

                minusValue("minSoilMoist4",currentMinMoist);
            }
        });


    }
}