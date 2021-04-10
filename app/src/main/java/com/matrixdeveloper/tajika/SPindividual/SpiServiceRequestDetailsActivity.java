package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.model.UserBookingDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiServiceRequestDetailsActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView serviceAccept, serviceDeclined;
    private String id;
    private String TAG = "SpiServiceRequestDetailsAct";
    private PrefManager pref;
    RequestDetails requestDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_request_details);

        pref = new PrefManager(this);
        id = getIntent().getStringExtra("id");
        backPress = findViewById(R.id.iv_backPress);
        serviceAccept = findViewById(R.id.txt_acceptService);
        serviceDeclined = findViewById(R.id.txt_declineService);

        backPress.setOnClickListener(view -> SpiServiceRequestDetailsActivity.super.onBackPressed());

        serviceAccept.setOnClickListener(view -> {
                changeServiceStatus(id,"Accept");

                    });

        serviceDeclined.setOnClickListener(view -> {
            changeServiceStatus(id,"Declined");
        });

        getServiceDetails(id);
    }

    private void changeServiceStatus(String id, String status) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
            data.put("status", status);
            data.put("service_provider_id", pref.getString("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.CHANGE_SERVICE_REQUEST_STATUS, data, response -> {
            Utils.log(TAG, response.toString());
            startActivity(new Intent(getApplicationContext(), SpiServiceAcceptActivity.class)
            .putExtra("requestDetails", requestDetails));
        });
    }

    private void getServiceDetails(String id) {

            JSONObject data = new JSONObject();
            try {
                data.put("id", id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ApiCall.postMethod(this, ServiceNames.UPDATE_LIVE_STATUS, data, response -> {
                Utils.log(TAG, response.toString());
                try {

                    requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

    }
}