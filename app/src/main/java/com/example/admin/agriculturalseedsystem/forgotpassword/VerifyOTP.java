package com.example.admin.agriculturalseedsystem.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.activity.ForgotPasswordActivity;
import com.example.admin.agriculturalseedsystem.activity.LoginActivity;
import com.example.admin.agriculturalseedsystem.activity.ProfileActivity;
import com.example.admin.agriculturalseedsystem.activity.VerifyPhoneActivity;
import com.example.admin.agriculturalseedsystem.backend.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pin_from_user;
    FirebaseAuth mauth;
    FirebaseDatabase rootNode;
    String CodeBySystem;
    String name,email,phoneNo,password,address,username,whatToDo;
    TextView send_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        pin_from_user = findViewById(R.id.otp_pinView);
        send_text = findViewById(R.id.sent_otp_text);

        mauth = FirebaseAuth.getInstance();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phoneNo = getIntent().getStringExtra("phone");
        address = getIntent().getStringExtra("address");
        password = getIntent().getStringExtra("pass");
        username = getIntent().getStringExtra("user");
        whatToDo = getIntent().getStringExtra("whatToDo");

        send_text.setText("Enter one time password sent To ("+phoneNo+")");

        sendVerificationCode(phoneNo);
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mauth).setPhoneNumber("+91"+phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    CodeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null){
                        pin_from_user.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(VerifyOTP.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeBySystem,code);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyOTP.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (whatToDo.equals("updateData"))
                    {
                        upadateOldUserData();
                    }else {
                        storeNewUserData();
                    }
                    Toast.makeText(VerifyOTP.this,"Verification code Confirmed!...",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(VerifyOTP.this,"Verification code not found"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upadateOldUserData() {
        Intent intent = new Intent(getApplicationContext(),SetPasswordActivity.class);
        intent.putExtra("phone",phoneNo);
        intent.putExtra("user",username);
        startActivity(intent);
        finish();
    }

    private void storeNewUserData() {
        rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Register_Backend");
        UserHelperClass helperClass = new UserHelperClass(name,email,phoneNo,address,password,username);
        Toast.makeText(VerifyOTP.this,"data successfully inserted",Toast.LENGTH_LONG).show();
        reference.child(username).setValue(helperClass);

    }

    public void CallNextFromOTPScreen(View view){
        String code = pin_from_user.getText().toString();
        if(code.isEmpty()){
            verifyCode(code);
        }
        else {
            Intent intent = new Intent(VerifyOTP.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    public void backActivity(View view) {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        finish();
    }
}