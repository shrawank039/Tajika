package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiAddCreditActivity extends AppCompatActivity {

    ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_add_credit);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpiAddCreditActivity.super.onBackPressed();
            }
        });
    }
}