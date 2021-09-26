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
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpiMyServicesActivity extends AppCompatActivity {

    private ImageView backPress;
    private RecyclerView myServicesRecView;
    private SPIMyServicesAdapter servicesAdapter;
    private final String TAG = "SpiMyServicesAct";
    private PrefManager pref;
    private Button addNewGoodsOrServices;

    private List<SPIMyServicesModel> serviceArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_my_services);

        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        myServicesRecView = findViewById(R.id.rv_myServices);
        addNewGoodsOrServices = findViewById(R.id.button2);

        backPress.setOnClickListener(view -> SpiMyServicesActivity.super.onBackPressed());
        addNewGoodsOrServices.setOnClickListener(view -> startActivity(new Intent(SpiMyServicesActivity.this, SpiAddNewServiceActivity.class)));

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

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONArray myServices = response.getJSONArray("data");
                    SPIMyServicesModel servicesModel = MySingleton.getGson().fromJson(myServices.getJSONObject(i).toString(), SPIMyServicesModel.class);
                    serviceArray.add(servicesModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            servicesAdapter = new SPIMyServicesAdapter(this, serviceArray);
            myServicesRecView.setHasFixedSize(true);
            myServicesRecView.setLayoutManager(new LinearLayoutManager(this));
            myServicesRecView.setAdapter(servicesAdapter);
        });

    }
}