package com.example.luntian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {

    EditText email;
    Button reset;
    ProgressBar pd;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.forget_emailTxt);
        reset = findViewById(R.id.resetBtn);
        pd = findViewById(R.id.resetProgressBar);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetEmail = email.getText().toString().trim();

                if(resetEmail.isEmpty()){
                    email.setError("Please enter email address!");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(resetEmail).matches()){
                    email.setError("Email address invalid!");
                    email.requestFocus();
                    return;
                }
                pd.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forget_password.this, "Please check your email to reset your password!", Toast.LENGTH_LONG).show();
                            pd.setVisibility(View.GONE);
                            Intent intent = new Intent(forget_password.this, login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(forget_password.this, "Password reset failed!", Toast.LENGTH_LONG).show();
                            pd.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}