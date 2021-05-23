package Intents;

import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allinone.Complaints;
import com.example.allinone.R;
import com.example.allinone.Mechanic_PrePay;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Mechanic extends AppCompatActivity {
    Button next;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Mechanic");
    EditText uname, email, Number, service_type, description;
    String user_name = "";
    String user_email = "";
    String user_number = "";
    String service = "";
    String Description = "";
    CheckBox bike, car;
    static boolean active = false;
    int x = 0;
    AlertDialog.Builder builder;
    String Vehicle = "";
    boolean bikeservice = false, carservice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
        init();
        onStart();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = uname.getText().toString();
                user_email = email.getText().toString();
                user_number = Number.getText().toString();
                service = service_type.getText().toString();
                Description = description.getText().toString();
                checkBoxData();
                if (user_name.isEmpty() && user_email.isEmpty() && user_number.isEmpty() && Description.isEmpty()) {
                    Toast.makeText(Mechanic.this, "Please fill the details", Toast.LENGTH_SHORT).show();

                } else {
                    builder = new AlertDialog.Builder(Mechanic.this);
                    builder.setTitle("Payment Mode")
                            .setMessage("Choose Your Payment Mode")
                            .setCancelable(true)
                            .setPositiveButton("COD", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (bike.isChecked()) {
                                        Cod_Payment(Vehicle);

                                    } else if (car.isChecked()) {
                                        Cod_Payment(Vehicle);
                                    } else if (car.isChecked() && bike.isChecked()) {
                                        Cod_Payment(Vehicle);
                                    } else {
                                        Toast.makeText(Mechanic.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("PrePaY", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    successActivityData();
                                }
                            });
                    if (active)
                        builder.show();
                }

            }
        });

    }

    private void checkBoxData() {
        Vehicle = bike.getText().toString() +car.getText().toString();
    }

    private void successActivityData() {
        Intent i = new Intent(Mechanic.this, Mechanic_PrePay.class);
        if (bike.isChecked()) {
            bikeservice = true;
            x = 100;
            Vehicle = "Bike";
            i.putExtra("uname", user_name);
            i.putExtra("umail", user_email);
            i.putExtra("unumber", user_number);
            i.putExtra("serviceType", service);
            i.putExtra("Description", Description);
            i.putExtra("Bike", bikeservice);
            i.putExtra("Car", carservice);
            startActivity(i);
        } else if (car.isChecked()) {
            carservice = true;
            x = 200;
            Vehicle = "Car";
            i.putExtra("uname", user_name);
            i.putExtra("umail", user_email);
            i.putExtra("unumber", user_number);
            i.putExtra("serviceType", service);
            i.putExtra("Description", Description);
            i.putExtra("Bike", bikeservice);
            i.putExtra("Car", carservice);
            startActivity(i);
        } else if (bike.isChecked() && car.isChecked()) {
            bikeservice = true;
            carservice = true;
            x = 300;
            Vehicle = "Bike and Car";
            i.putExtra("uname", user_name);
            i.putExtra("umail", user_email);
            i.putExtra("unumber", user_number);
            i.putExtra("serviceType", service);
            i.putExtra("Description", Description);
            i.putExtra("Bike", bikeservice);
            i.putExtra("Car", carservice);
            startActivity(i);
        }


    }

    private void init() {
        next = findViewById(R.id.next_button);
        uname = findViewById(R.id.userName_);
        email = findViewById(R.id.service_email);
        Number = findViewById(R.id.service_number);
        service_type = findViewById(R.id.service__type);
        description = findViewById(R.id.service_description);
        bike = findViewById(R.id.BikeBox);
        car = findViewById(R.id.CarBox);
    }

    public void Cod_Payment(String veh) {
        MechanicHelper helper = new MechanicHelper(user_name, user_email, user_number, service, Description, veh);
        String mdid = myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(Mechanic.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Mechanic.this, Complaints.class));
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }
}