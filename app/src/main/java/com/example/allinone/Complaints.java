package com.example.allinone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import Intents.Comp_Expert;
import Intents.Mechanic;
import Intents.Plumber;

public class Complaints extends AppCompatActivity {
CardView profile;
    SharedPreferences sharedPreferences;
    String username="";
    TextView txuserName;
    final static String KEY_NAME = "mypref";
    final static String KEY_CHECKBOX = "rememberme";
    final static String KEY_UNAME = "Armando";
    final static String KEY_UMail = "android@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        init();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Complaints.this,Profile.class));
            }
        });

    }

    private void init() {
        profile=findViewById(R.id.profileCard);
        txuserName=findViewById(R.id.tvUsername);
        sharedPreferences = getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        username = sharedPreferences.getString(KEY_UNAME, "");
        txuserName.setText("Hello "+username);

    }





    public void Others(View view) {
    startActivity(new Intent(Complaints.this, MoreServices.class));
    }

    public void Mechanic(View view) {
        startActivity(new Intent(Complaints.this, Mechanic.class));
    }

    public void compExpert(View view) {
        startActivity(new Intent(Complaints.this, Comp_Expert.class));
    }

    public void plumber(View view) {
        startActivity(new Intent(Complaints.this, Plumber.class));
    }
}