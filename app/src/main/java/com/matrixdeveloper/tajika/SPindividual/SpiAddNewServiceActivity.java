package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpiAddNewGoodsActivity;

public class SpiAddNewServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView addNewGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbadd_new_service);
        backPress = findViewById(R.id.iv_backPress);
        addNewGood = findViewById(R.id.txt_addGoodsInstead);

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpiAddNewServiceActivity.super.onBackPressed();
            }
        });
        addNewGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpiAddNewServiceActivity.this, SpiAddNewGoodsActivity.class));
            }
        });

    }
}