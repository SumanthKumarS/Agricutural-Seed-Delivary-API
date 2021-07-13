package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.backend.UserHelperClass;
import com.example.admin.agriculturalseedsystem.forgotpassword.VerifyOTP;
import com.example.admin.agriculturalseedsystem.util.Validations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText txt_name,txt_phone,txt_address,txt_email, txt_pass,txt_cpass;
    Button btn_register;
    FirebaseDatabase rootNode;
    DatabaseReference db_ref;
    ImageView logo_img;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //final RelativeLayout Relativeregister = (RelativeLayout) findViewById(R.id.RealtiveRegister);
        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle("Register Yourself");
        setTitleColor(R.color.white);
        txt_name = findViewById(R.id.etName);
        txt_email = findViewById(R.id.etEmail);
        txt_phone = findViewById(R.id.etPhoneNumber);
        txt_address = findViewById(R.id.etAddress);
        txt_pass = findViewById(R.id.etPassword);
        txt_cpass = findViewById(R.id.etConfirmPassword);
        btn_register = findViewById(R.id.btnRegister);
        logo_img = findViewById(R.id.logo_img3);

        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo_img.setAnimation(bottomAnim);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                db_ref = rootNode.getReference("Register_Backend");


                String rname = txt_name.getText().toString().trim();
                String remail = txt_email.getText().toString();
                String rphone = txt_phone.getText().toString();
                String raddress = txt_address.getText().toString();
                String rpass = txt_pass.getText().toString();
                String rcpass = txt_cpass.getText().toString();
                String username = rname+"_id";
                System.out.println(username);

                if(rname.isEmpty()){
                    txt_name.setError("The Name Space Cannot Be empty");
                    return;
                }
                if(remail.isEmpty()){
                    txt_email.setError("The Email Space Cannot Be empty");
                    return;
                }
                if(rphone.isEmpty()){
                    txt_phone.setError("The Phone Space Cannot Be empty");
                    return;
                }
                if(raddress.isEmpty()){
                    txt_phone.setError("The Phone Space Cannot Be empty");
                    return;
                }
                if(rpass.isEmpty()){
                    txt_pass.setError("The Password Space Cannot Be empty");
                    return;
                }
                if(rcpass.isEmpty()){
                    txt_cpass.setError("The Confirm password Space Cannot Be empty");
                    return;
                }

                if(Validations.validateEmail(remail)==true) {
                    if (Validations.isValidPhone(rphone)==true) {
                        if (rpass.equals(rcpass)) {
                            if (Validations.isValidPassword(rpass)==true) {
                                Intent intent = new Intent(RegisterActivity.this, VerifyOTP.class);
                                intent.putExtra("name",rname);
                                intent.putExtra("email",remail);
                                intent.putExtra("phone",rphone);
                                intent.putExtra("address",raddress);
                                intent.putExtra("pass",rpass);
                                intent.putExtra("user",username);
                                intent.putExtra("whatToDo", "storeData");
                                startActivity(intent);
                                /*UserHelperClass helperClass = new UserHelperClass(rname,remail,rphone,raddress,rpass,username);
                                Toast.makeText(RegisterActivity.this,"data successfully inserted",Toast.LENGTH_LONG).show();
                                db_ref.child(username).setValue(helperClass);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                               */
                            }else {
                                txt_pass.setError("The Password Dose'nt match with the pattern abc@123");
                            }
                        }else{
                            txt_pass.setError("The Password Dose'nt match with the Confirm Password");
                        }
                    }
                    else {
                        txt_phone.setError("The Phone number Dose'nt match with the pattern 9999999999");
                    }
                }else{
                    txt_email.setError("The Email Dose'nt match with the pattern abc@123.com");
                }

            }

        });

    }

}