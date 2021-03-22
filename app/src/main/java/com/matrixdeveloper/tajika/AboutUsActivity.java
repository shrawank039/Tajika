package com.matrixdeveloper.tajika;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

public class AboutUsActivity extends AppCompatActivity {

    private TextView aboutUSContent, appVersion;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutUSContent = findViewById(R.id.txt_aboutUS_content);
        appVersion = findViewById(R.id.txt_app_version);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> AboutUsActivity.super.onBackPressed());

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText("App Version: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ApiCall.getMethod(getApplicationContext(), ServiceNames.ABOUT_US, response -> {

            Utils.log("aboutus: ", response.toString());

            aboutUSContent.setText(response.optString("data"));

        });
    }
}