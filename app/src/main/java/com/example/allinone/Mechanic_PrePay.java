package com.example.allinone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import Intents.MechanicHelper;

public class Mechanic_PrePay extends AppCompatActivity implements PaymentResultListener {
    String user_name="";
    String user_email="";
    String user_number="";
    String service="";
    String Description="";
    int x=0;
    String Vehicle="";
    boolean bikeservice = false, carservice = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Mechanic");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Intent i=getIntent();
        user_name=i.getStringExtra("uname");
        user_email=i.getStringExtra("umail");
        user_number=i.getStringExtra("unumber");
        service=i.getStringExtra("serviceType");
        Description=i.getStringExtra("Description");
        bikeservice=i.getBooleanExtra("Bike",false);
        carservice=i.getBooleanExtra("Car",false);
        if(bikeservice ){
            x=10000;
            Vehicle="Bike";
        }
        else if(carservice){
            x=20000;
            Vehicle="Car";
        }
        else
        {
            x=30000;
            Vehicle="Both Car and Bike";
        }
        Checkout();
    }

    @Override
    public void onPaymentSuccess(String s) {
        MechanicHelper helper=new MechanicHelper(user_name,user_email,user_number,service,Description,Vehicle);
        String mdid=myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(Mechanic_PrePay.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Mechanic_PrePay.this, Complaints.class));
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Mechanic_PrePay.this, "Payment Failed.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Mechanic_PrePay.this, Complaints.class));
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
            options.put("amount",x);//pass amount in currency subunits
            options.put("prefill.email", "mayank0218.cse19@chitkara.edu.in");
            options.put("prefill.contact", "8168196670");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }
}