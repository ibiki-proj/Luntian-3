package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luntian.model.Device;
import com.example.luntian.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private Button logoutBtn;
    String devicePath = "";
    String token = "";
    private Spinner spinner;
    private static final String[] paths = {"Device 1", "Device 2"};
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        // Initialized Firestore
        FirebaseDatabase rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("Users").child(FirebaseAuth.getInstance().getUid());
        DatabaseReference deviceRef = rtdb.getReference("sampletest1").child(paths[position]).child("usersInvolved");

        Map<String, Object> data = new HashMap<>();

        data.put("device",paths[position]);
        sampleRef.updateChildren(data);

//        List users = new ArrayList<String>();

//        users.add(users);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Settings.this, "Fetching FCM registration token failed"+task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                         token = task.getResult();

                        Toast.makeText(Settings.this, "Token "+token, Toast.LENGTH_SHORT).show();
                    }
                });
        deviceRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    ArrayList users = new ArrayList();

                    for (DataSnapshot dss:task.getResult().getChildren()){
                        Toast.makeText(Settings.this, ""+dss.getValue(), Toast.LENGTH_SHORT).show();

                        users.add(dss.getValue());
                    }
//                    ArrayList users = task.getResult().getValue(ArrayList.class);

                    boolean alreadyExist = users.contains(token);

                    Toast.makeText(Settings.this, ""+alreadyExist, Toast.LENGTH_SHORT).show();
                    if (alreadyExist) {
                        Toast.makeText(Settings.this, "You are already involve to this device", Toast.LENGTH_SHORT).show();
                    }else{
                        users.add(token);
                        deviceRef.setValue(users);
                    }


                }else{ArrayList users = new ArrayList();
                    users.add(token);
                        deviceRef.setValue(users);
                }
            }
        });
//        Toast.makeText(this, ""+deviceRef.get().getResult().getChildren(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+deviceRef.get().getResult().getValue(), Toast.LENGTH_SHORT).show();
        List<String> users = new ArrayList<>();

//        deviceRef.child("usersInvolved").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for (DataSnapshot dss:snapshot.getChildren()){
//
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//        });








    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

        int position =  Arrays.asList(paths).indexOf(devicePath);

        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
    }

    void listenToUsers(){
        FirebaseDatabase rtdb = FirebaseDatabase.getInstance();
        // Database location reference of the humidity, temperature, fan status
        DatabaseReference sampleRef = rtdb.getReference("Users").child(FirebaseAuth.getInstance().getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists()){

                    UserData data = snapshot.getValue(UserData.class);

                    devicePath = data.device;



                    int position =  Arrays.asList(paths).indexOf(devicePath);
                    spinner.setSelection(position);
                    Toast.makeText(Settings.this, "Path"+Arrays.asList(paths).indexOf(devicePath), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(Settings.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        };
        sampleRef.addValueEventListener(postListener);
        System.out.println("Listening to user");


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listenToUsers();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Settings.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);

                        overridePendingTransition(0,0);

                        return true;

                    case R.id.planttracking:
                        Intent intent2 = new Intent(getApplicationContext(), plant_growth_tracking.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);

                        overridePendingTransition(0,0);
                        return true;

                    case R.id.plantcyclopedia:
                        Intent intent3 = new Intent(getApplicationContext(), plantcyclopedia.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);

                        overridePendingTransition(0,0 );
                        return true;

                    case R.id.planner:
                        Intent intent4 = new Intent(getApplicationContext(), ReminderMainActivity.class);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent4);

                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        Intent intent5 = new Intent(getApplicationContext(), Settings.class);
                        intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent5);
                  ;
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        int position =  Arrays.asList(paths).indexOf(devicePath);
        spinner.setSelection(position);
        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Settings.this, "You have logged out.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.this, login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}