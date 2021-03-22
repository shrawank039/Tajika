package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MyProfileActivity extends AppCompatActivity {

    private ImageView backPress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        backPress=findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> MyProfileActivity.super.onBackPressed());
    }

    public void onEditClick(View view) {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    public void onCancelRequestClick(View view) {
    }
}