package com.example.allinone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


import java.util.HashMap;

import Intents.Helper;

public class MoreServiceHndler extends AppCompatActivity implements PaymentResultListener {
    Button next;
    String amount_pay="";
    HashMap<String,String> hashMap_sms;
    FirebaseDatabase database;
    TextView HeadTitle;
    DatabaseReference myRef;
    EditText uname, email, Number, service_type, description;
    String user_name = "";
    String Header_name = "";
    String user_email = "";
    String service_Department = "";
    String user_number = "";
    String service = "";
    String Description = "";
    String user_Sms="";
    static boolean active = false;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_service_hndler);
        init();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = uname.getText().toString();
                user_email = email.getText().toString();
                user_number = Number.getText().toString();
                service = service_type.getText().toString();
                Description = description.getText().toString();
                if (user_name.isEmpty() && user_email.isEmpty() && user_number.isEmpty() && Description.isEmpty()) {
                    Toast.makeText(MoreServiceHndler.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                } else {
                    builder = new AlertDialog.Builder(MoreServiceHndler.this);
                    builder.setTitle("Payment Mode")
                            .setMessage("Choose Your Payment Mode")
                            .setCancelable(true)
                            .setPositiveButton("COD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Cod_Payment();
                                }
                            })
                            .setNegativeButton("PrePaY", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Checkout();
                                }
                            });
                    if (active)
                        builder.show();

                }
            }
        });
    }

    private void init() {
        Intent i = getIntent();
        service_Department = i.getStringExtra("serviceType");
        amount_pay = i.getStringExtra("serviceAmount");
        Header_name= i.getStringExtra("Headername");
        HeadTitle=findViewById(R.id.textView3);
        next = findViewById(R.id.next_button);
        uname = findViewById(R.id.userName_);
        service_type = findViewById(R.id.service__type);
        service_type.setText(service_Department);
        email = findViewById(R.id.service_email);
        Number = findViewById(R.id.service_number);
        description = findViewById(R.id.service_description);
        HeadTitle.setText(Header_name);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(service_Department);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Helper helper = new Helper(user_name, user_email, user_number, service, Description);
        user_Sms=user_name+"\n"+user_email+"\n"+user_number+"\n"+service+"\n"+Description;
        String mdid = myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(MoreServiceHndler.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MoreServiceHndler.this, Complaints.class));
        finish();
        sendMessage();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(MoreServiceHndler.this, "Payment Failed.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MoreServiceHndler.this, Complaints.class));
        finish();

    }

    public void Checkout() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_siFMSOnEVt01G4");
        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Yourocity");
            options.put("description", "Yourocity");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //   options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount_pay);//pass amount in currency subunits
            options.put("prefill.email", "mayank0218.cse19@chitkara.edu.in");
            options.put("prefill.contact", "8168196670");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    public void Cod_Payment() {
        Helper helper = new Helper(user_name, user_email, user_number, service, Description);
        String mdid = myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(MoreServiceHndler.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MoreServiceHndler.this, Complaints.class));
        finish();
    }
    public void sendMessage() {
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permission== PackageManager.PERMISSION_GRANTED)
        {
            MyMessage();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    MyMessage();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Your Message Cant be sent",Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void MyMessage() {
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage("+91 8168196670",null,user_Sms,null,null);
            Toast.makeText(getApplicationContext(),"Your Message sent",Toast.LENGTH_SHORT).show();

    }
}