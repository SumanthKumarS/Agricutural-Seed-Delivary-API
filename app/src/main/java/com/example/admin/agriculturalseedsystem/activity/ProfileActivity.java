package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.admin.agriculturalseedsystem.R;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {
    TextInputLayout fullName,email,phone,address;
    TextView fullNameLabel,phoneNumberLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        fullName = findViewById(R.id.fullNameProfile);
        email = findViewById(R.id.EmailProfile);
        phone = findViewById(R.id.PhoneNumberProfile);
        address = findViewById(R.id.AddressProfile);
        fullNameLabel = findViewById(R.id.id_profile_name);
        phoneNumberLabel = findViewById(R.id.phoneNumberdec);

        showUserData();
    }

    private void showUserData() {
        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        String user_email = intent.getStringExtra("email");
        String user_phone = intent.getStringExtra("phone");
        String user_address = intent.getStringExtra("address");

        fullNameLabel.setText(user_name);
        phoneNumberLabel.setText(user_phone);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phone.getEditText().setText(user_phone);
        address.getEditText().setText(user_address);


    }
}