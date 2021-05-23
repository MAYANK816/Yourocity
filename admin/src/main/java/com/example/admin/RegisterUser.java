package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    Button next;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    EditText uname, email, Number, service_type, description;
    String user_name = "";
    String user_email = "";
    String user_number = "";
    String service = "";
    String Description = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        init();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = uname.getText().toString();
                user_email = email.getText().toString();
                user_number = Number.getText().toString();
                service = service_type.getText().toString();
                Description = description.getText().toString();
                myRef = database.getReference("Registered ServiceMan").child(service.toLowerCase());
                if (user_name.isEmpty() && user_email.isEmpty() && user_number.isEmpty() && Description.isEmpty()&& service.isEmpty()) {

                    Toast.makeText(RegisterUser.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                } else {
                    AdminHelper helper = new AdminHelper(user_name, user_email, user_number, service.toLowerCase(), Description);
                    String mdid = myRef.push().getKey();
                    myRef.child(mdid).setValue(helper);
                    Toast.makeText(RegisterUser.this, "Details Filled.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterUser.this, RegisteredSuccessfully.class));
                    finish();
                }
            }
        });
    }
    private void init() {
        next = findViewById(R.id.next_button);
        uname = findViewById(R.id.userName_);
        email = findViewById(R.id.service_email);
        Number = findViewById(R.id.service_number);
        service_type = findViewById(R.id.service__type);
        description = findViewById(R.id.service_description);
        database = FirebaseDatabase.getInstance();

    }

}