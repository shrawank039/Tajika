package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer = findViewById(R.id.drawer_layout);

    }

    public void onNavDrawerClick(View view) {
        if (!drawer.isDrawerOpen(Gravity.START)) drawer.openDrawer(Gravity.START);
        else drawer.closeDrawer(Gravity.END);
    }
}