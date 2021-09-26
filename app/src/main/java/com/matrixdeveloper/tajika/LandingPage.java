package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.SPbusiness.SpbLoginActivity;
import com.matrixdeveloper.tajika.SPindividual.SpiHomeActivity;
import com.matrixdeveloper.tajika.SPindividual.SpiLoginActivity;
import com.matrixdeveloper.tajika.utils.PrefManager;

public class LandingPage extends AppCompatActivity {

    private static PrefManager prf;
    private Button serviceProIndividual, user, serviceProBusiness, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        initView();
        initListeners();

        prf = new PrefManager(LandingPage.this);

        if (!prf.getString("id").equals("")) {

            if (prf.getString("role").equals("3"))
                startActivity(new Intent(getApplicationContext(), SpiHomeActivity.class));
            else if (prf.getString("role").equals("4"))
                startActivity(new Intent(getApplicationContext(), SpiHomeActivity.class));
            else
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            finish();
        }
    }

    private void initView() {
        serviceProIndividual = findViewById(R.id.btn_serviceProviderIndividual);
        user = findViewById(R.id.btn_user);
        serviceProBusiness = findViewById(R.id.btn_serviceProviderBusiness);
        help = findViewById(R.id.btn_help);
    }

    private void initListeners() {
        user.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, LoginActivity.class)));
        serviceProIndividual.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, SpiLoginActivity.class)));
        serviceProBusiness.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, SpbLoginActivity.class)));
        help.setOnClickListener(view -> startActivity(new Intent(LandingPage.this, HelpActivity.class)));
    }

}