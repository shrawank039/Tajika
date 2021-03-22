package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CompareListActivity extends AppCompatActivity {

    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_list);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> CompareListActivity.super.onBackPressed());
    }
}