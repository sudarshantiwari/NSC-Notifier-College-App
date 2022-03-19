package com.ursaccharine.nscnotifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

public class AdminLogin extends AppCompatActivity {

    EditText admin_id,admin_password;
    MaterialButton admin_click;
    LottieAnimationView unlock_lottie,error_lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_id = findViewById(R.id.admin_id);
        admin_password = findViewById(R.id.admin_password);
        admin_click = findViewById(R.id.admin_click);
        unlock_lottie = findViewById(R.id.unlock_lottie);
        error_lottie = findViewById(R.id.error_lottie);

        admin_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (admin_id.getText().toString().equals("new_summit") && admin_password.getText().toString().equals("summi_unit@313") )
                {
                    error_lottie.setVisibility(View.INVISIBLE);
                    unlock_lottie.setVisibility(View.VISIBLE);
                    unlock_lottie.playAnimation();
                    Toast.makeText(AdminLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(AdminLogin.this,For_Notice.class);
                    //startActivity(intent);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(AdminLogin.this,For_Notice.class);
                            startActivity(intent);
                        }
                    },4000);
                }
                else{
                        unlock_lottie.setVisibility(View.INVISIBLE);
                        error_lottie.setVisibility(View.VISIBLE);
                        error_lottie.playAnimation();
                        Toast.makeText(AdminLogin.this, "Login Unsuccessfull", Toast.LENGTH_SHORT).show();


                }

            }
        });



    }
}