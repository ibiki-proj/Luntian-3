package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.luntian.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    EditText username, email, password, repass;
    DatabaseReference mDBRef;

    Button registerBtn;
    TextView cancelBtn;
    ProgressBar pd;

    FirebaseAuth mAuth;

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
                            Users user = new Users(regUName, regEmail, regPassword);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){


                                                Toast.makeText(register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
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
