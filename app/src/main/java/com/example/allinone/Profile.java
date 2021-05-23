package com.example.allinone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Profile extends AppCompatActivity {
    Button logout_Button,forgot_pass;
    TextView uname, umail;
    Bitmap profile_Pic;
    ImageView profile_Image;
   static Uri selectedImage, save_Info;
    private FirebaseAuth mAuth;
    final static String KEY_NAME = "mypref";
    final static String KEY_CHECKBOX = "rememberme";
    final static String KEY_UNAME = "Armando";
    final static String KEY_UMail = "android@gmail.com";
    String User_Name = "", User_Mail = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(Profile.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finishAffinity();
            }
        });
        profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryOpens();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

        }
            forgot_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Profile.this, ForgotPassword.class);
                    intent.putExtra("forgotEmail",User_Mail);
                    startActivity(intent);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                selectedImage = data.getData();
                save_Info = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                profile_Image.setImageBitmap(bitmap);
                profile_Pic = bitmap;
                profile_Image.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galleryOpens();
            }
        }

    }

    public void galleryOpens() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    private void init() {
        logout_Button = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(KEY_NAME, MODE_PRIVATE);
        User_Name = sharedPreferences.getString(KEY_UNAME, "");
        User_Mail = sharedPreferences.getString(KEY_UMail, "");
        uname = findViewById(R.id.Username);
        umail = findViewById(R.id.usermail);
        forgot_pass=findViewById(R.id.ForgotPass);
        uname.setText(User_Name);
        umail.setText(User_Mail);
        profile_Image = findViewById(R.id.profileImage);
    }

    public void ContactUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+91-8168196670"));
        startActivity(intent);
    }
}