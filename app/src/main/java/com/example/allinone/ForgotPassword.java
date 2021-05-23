package com.example.allinone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button forgotPass;
    private FirebaseAuth mAuth;
    EditText forgotEmail1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        forgotEmail1=findViewById(R.id.userRegisterEmail);
        Intent i=getIntent();
        String Mail=i.getStringExtra("forgotEmail");
        forgotEmail1.setText(Mail);
        forgotPass=findViewById(R.id.button2);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= forgotEmail1.getText().toString();
                if(email.isEmpty())
                {
                    Toast.makeText(ForgotPassword.this, "Fill The Details.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(email);
                    Toast.makeText(ForgotPassword.this, "Please Check Your Email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}