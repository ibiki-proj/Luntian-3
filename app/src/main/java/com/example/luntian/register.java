package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luntian.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText username, email, password, repass;
    FirebaseDatabase rtdb = FirebaseDatabase.getInstance();
    Button registerBtn;
    TextView cancelBtn;
    ProgressBar pd;

    FirebaseAuth mAuth;

    private Button logoutBtn;
    String devicePath = "";
    String token = "";
    ArrayList users = new ArrayList();

    private Spinner spinner;
    private static final String[] paths = {"Device 1", "Device 2"};
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        // Initialized Firestore

        // Database location reference of the humidity, temperature, fan status
        DatabaseReference deviceRef = rtdb.getReference("devices").child(paths[position]).child("usersInvolved");
        devicePath = paths[position];


//        List users = new ArrayList<String>();

//        users.add(users);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(register.this, "Fetching FCM registration token failed"+task.getException(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        Toast.makeText(register.this, "Token "+token, Toast.LENGTH_SHORT).show();
                    }
                });
        deviceRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){

                    for (DataSnapshot dss:task.getResult().getChildren()){
                        Toast.makeText(register.this, ""+dss.getValue(), Toast.LENGTH_SHORT).show();

                        users.add(dss.getValue());
                    }
//                    ArrayList users = task.getResult().getValue(ArrayList.class);

                    boolean alreadyExist = users.contains(token);

                    Toast.makeText(register.this, ""+alreadyExist, Toast.LENGTH_SHORT).show();
                    if (alreadyExist) {
                        Toast.makeText(register.this, "Exist", Toast.LENGTH_SHORT).show();
                    }else{
                        users.add(token);
//                        deviceRef.setValue(users);
                        Toast.makeText(register.this, "Not Exist"+token, Toast.LENGTH_SHORT).show();
                    }


                }else{
                    users.add(token);
//                    deviceRef.setValue(users);
                    Toast.makeText(register.this, "No Parent Exist"+token, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cancelBtn = findViewById(R.id.cancelRegBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.regBtn);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(register.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        username = findViewById(R.id.register_usernameTxt);
        email = findViewById(R.id.register_emailTxt);
        password = findViewById(R.id.register_passwordTxt);
        repass = findViewById(R.id.register_repasswordTxt);

        pd = findViewById(R.id.regProgressBar);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regUName = username.getText().toString().trim();
                String regEmail = email.getText().toString().trim();
                String regPassword = password.getText().toString().trim();
                String regRepass = repass.getText().toString().trim();
//                Toast.makeText(register.this, "User has been registered successfully!"+devicePath, Toast.LENGTH_LONG).show();
//                Toast.makeText(register.this, "User has been registered successfully!"+users, Toast.LENGTH_LONG).show();
                if(regUName.isEmpty()){
                    username.setError("Please insert username!");
                    username.requestFocus();
                    return;
                }
                if(regEmail.isEmpty()){
                    email.setError("Please insert email address!");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(regEmail).matches()){
                    email.setError("Email format invalid!");
                    email.requestFocus();
                    return;
                }
                if(regPassword.isEmpty()){
                    password.setError("Password required!");
                    password.requestFocus();
                    return;
                }
                if(regRepass.isEmpty()){
                    repass.setError("Please confirm password!");
                    repass.requestFocus();
                    return;
                }
                if(!regPassword.equals(regRepass)){
                    repass.setError("Password do not match!");
                    repass.requestFocus();
                    return;
                }
                if(regPassword.length() < 6){
                    password.setError("Password should be at least 6 characters long!");
                    password.requestFocus();
                    return;
                }

                pd.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user = new Users(regUName, regEmail, regPassword,token);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                DatabaseReference deviceRef = rtdb.getReference("devices").child(devicePath).child("usersInvolved");
                                                // Create a new LinkedHashSet
                                                Set<String> set = new LinkedHashSet<String>();

                                                // Add the elements to set
                                                set.addAll(users);

                                                // Clear the list
                                                users.clear();

                                                // add the elements of set
                                                // with no duplicates to the list
                                                users.addAll(set);

                                                deviceRef.setValue(users);



                                                pd.setVisibility(View.GONE);
                                                Intent intent = new Intent(register.this, login.class);
                                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(register.this, "User registration failed!", Toast.LENGTH_LONG).show();
                                                pd.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(register.this, "User registration failed!", Toast.LENGTH_LONG).show();
                            pd.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}
