package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiProfileActivity extends AppCompatActivity {

    private TextView profileEdit;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile);
        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress=findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> SpiProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpiProfileActivity.this, SpiProfileEditActivity.class)));
    }
}