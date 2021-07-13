package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.util.SessionManager;

public class DashboardActivity extends AppCompatActivity {
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Logout = findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(DashboardActivity.this);
                sessionManager.logoutUserFromSession();

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });
    }
}