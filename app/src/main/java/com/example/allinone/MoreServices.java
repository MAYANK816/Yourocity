package com.example.allinone;

import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MoreServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_serices);
    }

    public void TaxiBooking(View view) {
        Intent intent=new Intent(MoreServices.this, MoreServiceHndler.class);
        intent.putExtra("serviceType","TaxiBooking");
        intent.putExtra("serviceAmount","7000");
        intent.putExtra("Headername","TaxiBooking");
        startActivity(intent);

    }

    public void Emergency(View view) {
        Intent intent=new Intent(MoreServices.this, MoreServiceHndler.class);
        intent.putExtra("serviceType","EmergencyMedicine");
        intent.putExtra("serviceAmount","3000");
        intent.putExtra("Headername","Emergency");
        startActivity(intent);

    }

    public void Electrician(View view) {
        Intent intent=new Intent(MoreServices.this, MoreServiceHndler.class);
        intent.putExtra("serviceType","Electrician");
        intent.putExtra("serviceAmount","10000");
        intent.putExtra("Headername","Electrician");
        startActivity(intent);

    }

    public void AcServices(View view) {
        Intent intent=new Intent(MoreServices.this, MoreServiceHndler.class);
        intent.putExtra("serviceType","AcServices");
        intent.putExtra("serviceAmount","2000");
        intent.putExtra("Headername","AcServices");
        startActivity(intent);

    }

    public void Carpenter(View view) {
        Intent intent=new Intent(MoreServices.this, MoreServiceHndler.class);
        intent.putExtra("serviceType","Carpenter");
        intent.putExtra("serviceAmount","15000");
        intent.putExtra("Headername","Carpenter");
        startActivity(intent);
    }
}