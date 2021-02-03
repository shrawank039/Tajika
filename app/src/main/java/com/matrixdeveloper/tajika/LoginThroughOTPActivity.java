package com.matrixdeveloper.tajika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginThroughOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_through_otp);
    }

    public void onLoginThroughPasswordCLick(View view) {
        super.onBackPressed();
    }

    public void onLoginThroughOTPNEXTCLick(View view) {
        startActivity(new Intent(
                this, OTPInputActivity.class
        ));
    }
}