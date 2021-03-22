package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIMyServicesAdapter;
import com.matrixdeveloper.tajika.model.SPIMyServicesModel;

public class SpiMyServicesActivity extends AppCompatActivity {

    private ImageView backPress;
    private RecyclerView myServicesRecView;
    private SPIMyServicesAdapter servicesAdapter;
    private SPIMyServicesModel servicesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_my_services);
        backPress = findViewById(R.id.iv_backPress);
        myServicesRecView = findViewById(R.id.rv_myServices);

        SPIMyServicesModel[] myListData = new SPIMyServicesModel[]{
                new SPIMyServicesModel(1, "Service #1", "Catering", "6.5 Years", "500 Ksh"),
                new SPIMyServicesModel(2, "Service #2", "Plumbing", "5.5 Years", "550 Ksh")
        };

        servicesAdapter = new SPIMyServicesAdapter(this, myListData);
        myServicesRecView.setHasFixedSize(true);
        myServicesRecView.setLayoutManager(new LinearLayoutManager(this));
        myServicesRecView.setAdapter(servicesAdapter);

        backPress.setOnClickListener(view -> SpiMyServicesActivity.super.onBackPressed());
    }
}