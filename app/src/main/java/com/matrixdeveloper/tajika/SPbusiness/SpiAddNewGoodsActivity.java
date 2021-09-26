package com.matrixdeveloper.tajika.SPbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiAddNewGoodsActivity extends AppCompatActivity {

    private final String TAG = "SpbMyServicesAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_add_new_goods);

        pref = new PrefManager(this);
    }

    private void addNewGoods() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", "");
            data.put("sub_cat_id", "");
            data.put("price", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ADD_NEW_GOODS, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }

    private void updateGoods() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", "");
            data.put("sub_cat_id", "");
            data.put("price", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.UPDATE_GOODS, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }

    private void deleteGoods() {

        JSONObject data = new JSONObject();
        try {
            data.put("id", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.DELETE_GOODS, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
        });

    }
}