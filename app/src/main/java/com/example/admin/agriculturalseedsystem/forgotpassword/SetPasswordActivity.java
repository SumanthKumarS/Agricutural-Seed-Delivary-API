package com.example.admin.agriculturalseedsystem.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.activity.LoginActivity;
import com.example.admin.agriculturalseedsystem.util.Validations;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetPasswordActivity extends AppCompatActivity {
    TextInputEditText newPassword,newConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        newPassword = findViewById(R.id.forget_newpassword);
        newConfirmPassword = findViewById(R.id.forget_confirmnewpassword);
    }

    public void setNewPasswordBtn(View view){
        String newpass = newPassword.getText().toString();
        String newcpass = newConfirmPassword.getText().toString();
        String phoneNumber = getIntent().getStringExtra("phone");
        String userName = getIntent().getStringExtra("user");
        System.out.println("Username set password"+userName);
        /*if(Validations.isValidPassword(newpass) | Validations.isValidPassword(newcpass)){
            return;
        }*/
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Register_Backend");
        reference.child(userName).child("pass").setValue(newpass);
        startActivity(new Intent(getApplicationContext(),ForgetPasswordSuccessActivity.class));
        finish();

    }
    public void backActivity(View view) {
        startActivity(new Intent(getApplicationContext(), VerifyOTP.class));
        finish();
    }
}