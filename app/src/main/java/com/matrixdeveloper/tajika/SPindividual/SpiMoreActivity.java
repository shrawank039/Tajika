package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPMoreOptionsBaseAdapter;

public class SpiMoreActivity extends AppCompatActivity {

    private ListView moreOptions;
    String settingsList[] = {"My Profile", "Services you offer", "All requests", "All bookings",
            "Credit wallet", "Contact us", "About us", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_more);
        moreOptions = findViewById(R.id.lv_moreOptions);

        SPMoreOptionsBaseAdapter customAdapter = new SPMoreOptionsBaseAdapter(getApplicationContext(), settingsList);
        moreOptions.setAdapter(customAdapter);
    }
}