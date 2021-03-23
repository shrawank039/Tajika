package com.matrixdeveloper.tajika;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private TextView privacyPolicy;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        privacyPolicy = findViewById(R.id.txt_privacy_policy);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> PrivacyPolicyActivity.super.onBackPressed());

        ApiCall.getMethod(this, ServiceNames.PRIVACY_POLICY, response -> {

            Utils.log("aboutus: ", response.toString());

            privacyPolicy.setText(response.optString("data"));

        });
    }
}