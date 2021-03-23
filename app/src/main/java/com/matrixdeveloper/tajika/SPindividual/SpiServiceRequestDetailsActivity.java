package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiServiceRequestDetailsActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView serviceAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_request_details);

        backPress = findViewById(R.id.iv_backPress);
        serviceAccept = findViewById(R.id.txt_acceptService);

        backPress.setOnClickListener(view -> SpiServiceRequestDetailsActivity.super.onBackPressed());
        serviceAccept.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SpiServiceAcceptActivity.class)));
    }
}