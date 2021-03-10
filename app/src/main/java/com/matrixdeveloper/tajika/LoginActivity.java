package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onSignUPClick(View view) {
        startActivity(new Intent(
                this, RegisterActivity.class
        ));
    }

    public void onLoginThroughOTPCLick(View view) {
        startActivity(new Intent(
                this, LoginThroughOTPActivity.class
        ));
    }

    public void onLoginCLick(View view) {
        startActivity(new Intent(
                this, HomeActivity.class
        ));
    }

    public void onForgotPasswordClick(View view) {
        startActivity(new Intent(
                this, ResetPasswordActivity.class
        ));
    }
}