package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIMyServicesAdapter;
import com.matrixdeveloper.tajika.model.SPIMyServicesModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiMyServicesActivity extends AppCompatActivity {

    private ImageView backPress;
    private RecyclerView myServicesRecView;
    private SPIMyServicesAdapter servicesAdapter;
    private final String TAG = "SpiMyServicesAct";
    private PrefManager pref;
    private Button addNewGoodsOrServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_my_services);

        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        myServicesRecView = findViewById(R.id.rv_myServices);
        addNewGoodsOrServices = findViewById(R.id.button2);

        SPIMyServicesModel[] myListData = new SPIMyServicesModel[]{
                new SPIMyServicesModel(1, "Service #1", "Catering", "4.1 Years", "500 Ksh"),
                new SPIMyServicesModel(2, "Service #2", "Plumbing", "3.5 Years", "250 Ksh")
        };

        servicesAdapter = new SPIMyServicesAdapter(this, myListData);
        myServicesRecView.setHasFixedSize(true);
        myServicesRecView.setLayoutManager(new LinearLayoutManager(this));
        myServicesRecView.setAdapter(servicesAdapter);

        backPress.setOnClickListener(view -> SpiMyServicesActivity.super.onBackPressed());
        addNewGoodsOrServices.setOnClickListener(view -> startActivity(new Intent(SpiMyServicesActivity.this,SpiAddNewServiceActivity.class)));

        myServiceList();
    }

    private void myServiceList() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.PROVIDER_SERVICE_LIST, data, response -> {
            Utils.log(TAG, response.toString());
        });

    }

    private void addNewService() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.ADD_NEW_SERVICE, data, response -> {
            Utils.log(TAG, response.toString());


        });

    }
}