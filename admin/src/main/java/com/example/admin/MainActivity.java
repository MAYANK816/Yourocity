package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void RegisterServiceMan(View view) {
        startActivity(new Intent(MainActivity.this,RegisterUser.class));

    }

    public void orders(View view) {
        startActivity(new Intent(MainActivity.this,UserOptions.class));
    }
}