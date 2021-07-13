package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.util.SessionManager;

public class MainScreen extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView logo_img;
    TextView logo_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo_img = findViewById(R.id.logo_img);
        logo_title = findViewById(R.id.logo_title);
        logo_img.setAnimation(topAnim);
        logo_title.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionManager sessionManager = new SessionManager(MainScreen.this);
                if (sessionManager.checkLogin()){
                    startActivity(new Intent(MainScreen.this, DashboardActivity.class));
                    finish();
                }else {
                    Intent intent = new Intent(MainScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);


    }
}