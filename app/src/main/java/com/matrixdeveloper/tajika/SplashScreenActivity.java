package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.utils.PrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prf = new PrefManager(SplashScreenActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prf.getBoolean("firstLaunch")){
                    startActivity(new Intent(getApplicationContext(), OnboardingActivity.class));
                }else {
                    startActivity(new Intent(SplashScreenActivity.this, LandingPage.class));
                }
                finish();
            }
        }, 2000);
    }
}