package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView serviceAccept, serviceDeclined, requestId,jobDate,jobTime,jobType,
            address,description,amtWillingToPay,contactName,contactNumber;
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

        initViews();

        backPress.setOnClickListener(view -> SpiServiceRequestDetailsActivity.super.onBackPressed());

        serviceAccept.setOnClickListener(view -> {
                changeServiceStatus(id,"Accept");

                    });

        serviceDeclined.setOnClickListener(view -> {
            changeServiceStatus(id,"Declined");
        });

        getServiceDetails(id);
    }

    private void initViews() {
        backPress = findViewById(R.id.iv_backPress);
        serviceAccept = findViewById(R.id.txt_acceptService);
        serviceDeclined = findViewById(R.id.txt_declineService);
        requestId = findViewById(R.id.txt_jobId);
        jobDate = findViewById(R.id.txt_jobDate);
        jobTime = findViewById(R.id.txt_jobTime);
        jobType = findViewById(R.id.txt_jobType);
        address = findViewById(R.id.txt_serviceAddress);
        description = findViewById(R.id.txt_serviceDescription);
        amtWillingToPay = findViewById(R.id.txt_amtWillingToPay);
        contactName = findViewById(R.id.txt_custName);
        contactNumber = findViewById(R.id.txt_custNumber);
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
                data.put("id", 3);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST_DETAILS, data, response -> {
                Utils.log(TAG, response.toString());
                try {

                    requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);
                    JSONObject jsonObject=response.getJSONObject("data");

                    requestId.setText(jsonObject.optString("request_id"));
                    jobDate.setText(jsonObject.optString("service_date"));
                    jobTime.setText(jsonObject.optString("service_time"));
                    jobType.setText(jsonObject.optString("service_type"));
                    address.setText(jsonObject.optString("address"));
                    description.setText(jsonObject.optString("work_description"));
                    contactNumber.setText(jsonObject.optString("customerphone"));
                    contactName.setText(jsonObject.optString("customername"));
                    amtWillingToPay.setText(jsonObject.optString("currency")+" "+jsonObject.optString("willing_amount_pay"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

    }
}