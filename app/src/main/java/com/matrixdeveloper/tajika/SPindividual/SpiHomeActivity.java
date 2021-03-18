package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;

public class SpiHomeActivity extends AppCompatActivity {

    ImageView moreSettings,notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_home);
        moreSettings = findViewById(R.id.iv_moreSettings);
        notifications=findViewById(R.id.iv_notifications);

        moreSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, SpiMoreActivity.class));
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, NotificationActivity.class));
            }
        });
    }
}