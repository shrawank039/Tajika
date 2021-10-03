package com.matrixdeveloper.tajika.SPindividual;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiServiceAcceptActivity extends AppCompatActivity {

    private TextView completeJob, customerNumber;
    private LinearLayout voiceCall;
    private String id;
    private final String TAG = "SpiServiceAcceptAct";
    private PrefManager pref;
    RequestDetails requestDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_accept);

        pref = new PrefManager(this);
        requestDetails = (RequestDetails) getIntent().getSerializableExtra("requestDetails");
        completeJob = findViewById(R.id.txt_completeJob);
        customerNumber = findViewById(R.id.txt_custNumber);
        voiceCall = findViewById(R.id.ll_voiceCall);
        voiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + customerNumber.getText().toString();
                callIntent.setData(Uri.parse(temp));

                startActivity(callIntent);
            }
        });

        completeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SpiServiceAcceptActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_job_completion);

                ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
                TextView yes = dialog.findViewById(R.id.txt_yes);
                TextView no = dialog.findViewById(R.id.txt_no);
                dialogButton.setOnClickListener(v -> dialog.dismiss());
                yes.setOnClickListener(v -> {
                    changeServiceStatus(id, "Completed");
                    dialog.dismiss();
                });
                no.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
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

            startActivity(new Intent(SpiServiceAcceptActivity.this, SpiServiceCompletedStatusActivity.class));
            finish();

        });
    }
}