package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpiAddNewGoodsActivity;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiAddNewServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView addNewGood;
    private final String TAG = "SpiAddNewServiceAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbadd_new_service);


        pref = new PrefManager(this);
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

    private void addNewService() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", "");
            data.put("sub_cat_id", "");
            data.put("mincharge", "");
            data.put("experience", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ADD_NEW_SERVICE, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }

    private void updateService() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", "");
            data.put("sub_cat_id", "");
            data.put("mincharge", "");
            data.put("experience", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.UPDATE_SERVICE, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }

    private void deleteService() {

        JSONObject data = new JSONObject();
        try {
            data.put("id", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.DELETE_SERVICE, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }
}