package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> EditProfileActivity.super.onBackPressed());
    }
}