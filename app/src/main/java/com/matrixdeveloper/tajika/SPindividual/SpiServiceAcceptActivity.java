package com.matrixdeveloper.tajika.SPindividual;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class SpiServiceAcceptActivity extends AppCompatActivity {

    private TextView requestID, jobDate, jobTime, jobType,customerName, customerNumber,serviceArea,serviceDescription,amountWillingToPay, completeJob;
    private LinearLayout voiceCall;
    private final String TAG = "SpiServiceAcceptAct";
    private PrefManager pref;
    RequestDetails requestDetails;
    private String serID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_accept);

        pref = new PrefManager(this);
        serID = getIntent().getStringExtra("ser_id");
        initViews();
        initListeners();

        getServiceDetails(serID);
    }

    private void setViews() {
        requestID.setText(requestDetails.getRequestId());
        jobDate.setText(requestDetails.getServiceDate());
        jobTime.setText(requestDetails.getServiceTime());
        jobType.setText(requestDetails.getServiceType());
        customerName.setText(requestDetails.getCustomername());
        customerNumber.setText(requestDetails.getCustomerphone());
        serviceArea.setText(requestDetails.getAddress());
        serviceDescription.setText(requestDetails.getWorkDescription());
        amountWillingToPay.setText(requestDetails.getCurrency()+" "+requestDetails.getWillingAmountPay());

        if (!requestDetails.getStatus().equals("Booked")){
            completeJob.setVisibility(View.GONE);
        }
    }

    private void initListeners() {

        voiceCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + customerNumber.getText().toString();
            callIntent.setData(Uri.parse(temp));
            startActivity(callIntent);
        });

        completeJob.setOnClickListener(view -> {

            final Dialog dialog = new Dialog(SpiServiceAcceptActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_job_completion);

            ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
            TextView yes = dialog.findViewById(R.id.txt_yes);
            TextView no = dialog.findViewById(R.id.txt_no);
            dialogButton.setOnClickListener(v -> dialog.dismiss());
            yes.setOnClickListener(v -> {
                changeServiceStatus(serID, "Completed");
                dialog.dismiss();
            });
            no.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        });
    }

    private void initViews() {
        pref = new PrefManager(this);
        requestID = findViewById(R.id.txt_jobId);
        jobDate = findViewById(R.id.txt_jobDate);
        jobTime = findViewById(R.id.txt_jobTime);
        jobType = findViewById(R.id.txt_jobType);
        customerName = findViewById(R.id.txt_custName);
        customerNumber = findViewById(R.id.txt_custNumber);
        serviceArea = findViewById(R.id.txt_serviceAddress);
        serviceDescription = findViewById(R.id.txt_serviceDescription);
        amountWillingToPay = findViewById(R.id.txt_willingAmountToPAy);
        completeJob = findViewById(R.id.txt_completeJob);
        voiceCall = findViewById(R.id.ll_voiceCall);
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

                setViews();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

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

            if (response.optInt("status") == 400) {
                Utils.toast(getApplicationContext(), response.optString("message"));
            } else {
                startActivity(new Intent(SpiServiceAcceptActivity.this, SpiServiceCompletedStatusActivity.class).putExtra("requestDetails", requestDetails));
                finish();
            }

        });
    }
}