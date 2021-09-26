package com.matrixdeveloper.tajika.SPbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiAddNewServiceActivity;
import com.matrixdeveloper.tajika.adapter.SPIMyServicesAdapter;
import com.matrixdeveloper.tajika.model.SPIMyServicesModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpbMyServicesActivity extends AppCompatActivity {

    private ImageView backPress;
    private RecyclerView myServicesRecView;
    private SPIMyServicesAdapter servicesAdapter;
    private final String TAG = "SpbMyServicesAct";
    private PrefManager pref;
    private Button addNewGoodOrService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_my_services);

        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        myServicesRecView = findViewById(R.id.rv_myServices);
        addNewGoodOrService = findViewById(R.id.button2);

        SPIMyServicesModel[] myListData = new SPIMyServicesModel[]{
                new SPIMyServicesModel(1, "Service #1", "Catering", "6.5 Years", "500 Ksh"),
                new SPIMyServicesModel(2, "Service #2", "Plumbing", "5.5 Years", "550 Ksh")
        };

        servicesAdapter = new SPIMyServicesAdapter(this, myListData);
        myServicesRecView.setHasFixedSize(true);
        myServicesRecView.setLayoutManager(new LinearLayoutManager(this));
        myServicesRecView.setAdapter(servicesAdapter);

        backPress.setOnClickListener(view -> SpbMyServicesActivity.super.onBackPressed());
        addNewGoodOrService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpbMyServicesActivity.this, SpiAddNewServiceActivity.class));
            }
        });

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

}