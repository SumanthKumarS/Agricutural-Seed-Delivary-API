package com.example.admin.agriculturalseedsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admin.agriculturalseedsystem.R;
import com.example.admin.agriculturalseedsystem.forgotpassword.VerifyOTP;
import com.example.admin.agriculturalseedsystem.util.Validations;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.admin.agriculturalseedsystem.R.layout.activity_forgot_password;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView logo_img;
    Animation topAnim;

    TextInputEditText forgot_email;
    Button forgot_next_btn;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot_email = findViewById(R.id.forget_email);
        forgot_next_btn = findViewById(R.id.forgot_next);

        forgot_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgetPasswordVlidation();
            }
        });
    }

    private void ForgetPasswordVlidation() {
        String lemail = forgot_email.getText().toString().trim();
        db = FirebaseDatabase.getInstance().getReference("Register_Backend");
        if(Validations.validateEmail(lemail)){

            String[] username = lemail.split("@|shettigar|.is", 2);

                Query checkUser = db.orderByChild("username").equalTo(username[0]+"_id");
                System.out.println(username[0]);
                System.out.println(checkUser);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println(snapshot);
                        if (snapshot.exists()) {
                            String lphone = snapshot.child(username[0]+"_id").child("phone").getValue(String.class);
                            System.out.println("lphone "+lphone);
                            Intent intent = new Intent(ForgotPasswordActivity.this, VerifyOTP.class);
                            intent.putExtra("phone", lphone);
                            intent.putExtra("user",username[0]+"_id");
                            intent.putExtra("whatToDo", "updateData");
                            startActivity(intent);
                            finish();
                        } else {
                            forgot_email.setError("No such user Exisist");
                            forgot_email.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        }

    public void backActivity(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}