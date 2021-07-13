package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.util.SessionManager;
import com.example.admin.agriculturalseedsystem.util.Validations;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText txt_email, txt_pass;
    Button btnLogin;
    TextView txt_ForgotPassword,txt_RegisterYourself;
    DatabaseReference db;
    ImageView logo_img;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.etEmailAddress);
        txt_pass = findViewById(R.id.etPassword);
        txt_ForgotPassword = findViewById(R.id.txtForgotPassword);
        txt_RegisterYourself = findViewById(R.id.txtRegisterYourself);
        btnLogin = findViewById(R.id.btnLogin);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        logo_img = findViewById(R.id.logo_img2);
        logo_img.setAnimation(topAnim);

        txt_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        txt_RegisterYourself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = FirebaseDatabase.getInstance().getReference("Register_Backend");

                String lemail = txt_email.getText().toString();
                String lpass = txt_pass.getText().toString();
                String[] username = lemail.split("@|shettigar|is", 2);

                System.out.println(username[0]);
                for (String uname : username) {
                    Query checkUser = db.orderByChild("username").equalTo(uname+"_id");
                    System.out.println(uname);
                    System.out.println(checkUser);

                    if (lemail.isEmpty()) {
                        txt_email.setError("The Username Space Cannot Be empty");
                        return;
                    }
                    if (lpass.isEmpty()) {
                        txt_pass.setError("The Password Space Cannot Be empty");
                    }
                    if (Validations.validateEmail(lemail) == false) {
                        txt_email.setError("The Username Pattern should match");
                        return;
                    } else {
                        if (Validations.isValidPassword(lpass) == false) {
                            txt_pass.setError("The Password Pattern must match");
                            return;
                        } else {

                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    System.out.println(snapshot);

                                    if (snapshot.exists()) {
                                        String emailFromDB = snapshot.child(uname+"_id").child("email").getValue(String.class);
                                        System.out.println(emailFromDB);
                                        if (emailFromDB.equals(lemail)) {
                                            String nameFromDB = snapshot.child(uname+"_id").child("name").getValue(String.class);
                                            String phoneFromDB = snapshot.child(uname+"_id").child("phone").getValue(String.class);
                                            String usernameFromDB = snapshot.child(uname+"_id").child("username").getValue(String.class);
                                            String email2FromDB = snapshot.child(uname+"_id").child("email").getValue(String.class);
                                            String addressFromDB = snapshot.child(uname+"_id").child("address").getValue(String.class);
                                            String passFromDB = snapshot.child(uname+"_id").child("pass").getValue(String.class);

                                            //session
                                            SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                            boolean Lg = sessionManager.checkLogin();
                                            System.out.println(Lg);
                                            sessionManager.createLoginSession(nameFromDB,usernameFromDB,email2FromDB,phoneFromDB,passFromDB,addressFromDB);


                                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                            intent.putExtra("name",nameFromDB);
                                            intent.putExtra("email",emailFromDB);
                                            intent.putExtra("phone",phoneFromDB);
                                            intent.putExtra("address",addressFromDB);



                                            startActivity(intent);
                                        }else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setMessage("User has not been registered....");
                                            builder.setMessage("Would You like to register Yourself...");
                                            builder.setTitle("Alert!!.....");
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    builder.setCancelable(false);
                                                }
                                            });
                                            //Toast.makeText(LoginActivity.this,"user has not been register",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Toast.makeText(LoginActivity.this, "Error has occures!!!! " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    break;
                }
            }
        });
    }
}