package Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;
import com.example.allinone.Complaints;
import com.example.allinone.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Others extends AppCompatActivity implements PaymentResultListener {
    Button next;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Others");
    EditText uname, email, Number, service_type, description;
    String user_name = "";
    String user_email = "";
    String user_number = "";
    String service = "";
    String Description = "";
    static boolean active = false;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
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
                    Toast.makeText(Others.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                } else {
                    builder = new AlertDialog.Builder(Others.this);
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
        next = findViewById(R.id.next_button);
        uname = findViewById(R.id.userName_);
        email = findViewById(R.id.service_email);
        Number = findViewById(R.id.service_number);
        service_type = findViewById(R.id.service__type);
        description = findViewById(R.id.service_description);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Helper helper = new Helper(user_name, user_email, user_number, service, Description);
        String mdid = myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(Others.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Others.this, Complaints.class));
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Others.this, "Payment Failed.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Others.this, Complaints.class));
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
            options.put("amount", "10000");//pass amount in currency subunit
            options.put("prefill.email", "mayank0218.cse19@chitkara.edu.in");
            options.put("prefill.contact", "8168196670");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    public void Cod_Payment() {
        Helper helper = new Helper(user_name, user_email, user_number, service, Description);
        String mdid = myRef.push().getKey();
        myRef.child(mdid).setValue(helper);
        Toast.makeText(Others.this, "Details Filled.",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Others.this, Complaints.class));
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }
}