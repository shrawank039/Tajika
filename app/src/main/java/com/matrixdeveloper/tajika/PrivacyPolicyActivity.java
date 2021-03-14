package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private TextView privacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        privacyPolicy = findViewById(R.id.txt_privacy_policy);

        ApiCall.getMethod(getApplicationContext(), ServiceNames.PRIVACY_POLICY, response -> {

            Utils.log("aboutus: ", response.toString());

            privacyPolicy.setText(response.optString("data"));

        });
    }
}