package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiServiceRequestDetailsActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView serviceAccept, serviceDeclined, requestId, jobDate, jobTime, jobType,
            address, description, amtWillingToPay, contactName, contactNumber;
    private final String TAG = "SpiServiceRequestDetailsAct";
    private PrefManager pref;
    RequestDetails requestDetails;
    private String serID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_request_details);

        pref = new PrefManager(this);

        serID = getIntent().getStringExtra("ser_id");

        initViews();

        backPress.setOnClickListener(view -> SpiServiceRequestDetailsActivity.super.onBackPressed());

        serviceAccept.setOnClickListener(view -> {
            changeServiceStatus(serID, "Accept");
        });

        serviceDeclined.setOnClickListener(view -> {
            changeServiceStatus(serID, "Declined");
        });

        getServiceDetails(serID);
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

    public void changeServiceStatus(String id, String status) {
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

            if (response.optInt("status")==400){
                Utils.toast(getApplicationContext(), response.optString("message"));
            }else {
            if (status.equals("Accept")) {
                startActivity(new Intent(getApplicationContext(), SpiServiceAcceptActivity.class)
                        .putExtra("requestDetails", requestDetails));
                finish();
            } else {
                Intent intent = new Intent(this, SpiHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "Service has been declined..", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    private void getServiceDetails(String id) {

        JSONObject data = new JSONObject();
        try {
            data.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST_DETAILS, data, response -> {
            Utils.log(TAG, response.toString());
            try {

                requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);

                if (!requestDetails.getStatus().equals("Pending")){
                    findViewById(R.id.linearLayout9).setVisibility(View.GONE);
                }
                requestId.setText(requestDetails.getRequestId());
                jobDate.setText(requestDetails.getServiceDate());
                jobTime.setText(requestDetails.getServiceTime());
                jobType.setText(requestDetails.getServiceType());
                address.setText(requestDetails.getAddress());
                description.setText(requestDetails.getWorkDescription());
                contactNumber.setText(requestDetails.getCustomerphone());
                contactName.setText(requestDetails.getCustomername());
                amtWillingToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());


            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }
}