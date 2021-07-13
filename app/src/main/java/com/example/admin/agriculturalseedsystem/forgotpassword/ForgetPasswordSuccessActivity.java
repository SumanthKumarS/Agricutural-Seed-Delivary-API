package com.example.admin.agriculturalseedsystem.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.activity.LoginActivity;

public class ForgetPasswordSuccessActivity extends AppCompatActivity {

    Button Password_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_success);
        Password_btn = findViewById(R.id.password_update_btn);

        Password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordSuccessActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    public void backActivity(View view) {
        startActivity(new Intent(getApplicationContext(),SetPasswordActivity.class));
        finish();
    }
}