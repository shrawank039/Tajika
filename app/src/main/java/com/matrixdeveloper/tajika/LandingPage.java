package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.utils.PrefManager;

public class LandingPage extends AppCompatActivity {

    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        prf = new PrefManager(LandingPage.this);

        if (prf.getString("id").equals(""))
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        else
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    public void onIndividualClick(View view) {

    }

    public void onUserClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}