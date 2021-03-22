package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.SPindividual.SpiRegisterActivity;
import com.matrixdeveloper.tajika.utils.PrefManager;

public class LandingPage extends AppCompatActivity {

    private static PrefManager prf;
    private Button serviceProIndividual, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        initView();
        initListeners();

        prf = new PrefManager(LandingPage.this);

        if (prf.getString("id").equals(""))
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        else {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    private void initView() {
        serviceProIndividual = findViewById(R.id.btn_serviceProviderIndividual);
        user = findViewById(R.id.btn_user);
    }

    private void initListeners() {
        user.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, LoginActivity.class)));
        serviceProIndividual.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, SpiRegisterActivity.class)));
    }

}