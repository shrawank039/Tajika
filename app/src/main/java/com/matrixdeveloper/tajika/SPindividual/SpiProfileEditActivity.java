package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiProfileEditActivity extends AppCompatActivity {

    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile_edit);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> SpiProfileEditActivity.super.onBackPressed());
    }
}