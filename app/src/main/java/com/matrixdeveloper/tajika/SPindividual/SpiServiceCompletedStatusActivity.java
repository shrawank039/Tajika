package com.matrixdeveloper.tajika.SPindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.utils.PrefManager;

public class SpiServiceCompletedStatusActivity extends AppCompatActivity {

    private TextView requestID, jobDate, jobTime, jobType,serviceStatus,dateOfCompletion,customerRating,customerName, customerNumber,serviceArea,serviceDescription,amountWillingToPay;
    RequestDetails requestDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_completed_status);
        initViews();
        requestDetails = (RequestDetails) getIntent().getSerializableExtra("requestDetails");
        setViews();
    }

    private void setViews() {
        requestID.setText(requestDetails.getRequestId());
        jobDate.setText(requestDetails.getServiceDate());
        jobTime.setText(requestDetails.getServiceTime());
        jobType.setText(requestDetails.getServiceType());
        serviceStatus.setText(requestDetails.getStatus());
        dateOfCompletion.setText(""+requestDetails.getCompetedOn());
        //customerRating.setText(requestDetails.rat);
        customerName.setText(requestDetails.getCustomername());
        customerNumber.setText(requestDetails.getCustomerphone());
        serviceArea.setText(requestDetails.getAddress());
        serviceDescription.setText(requestDetails.getWorkDescription());
        amountWillingToPay.setText(requestDetails.getWillingAmountPay());
    }

    private void initViews() {
        requestID = findViewById(R.id.txt_jobId);
        jobDate = findViewById(R.id.txt_jobDate);
        jobTime = findViewById(R.id.txt_jobTime);
        jobType = findViewById(R.id.txt_jobType);
        serviceStatus = findViewById(R.id.txt_serviceStatus);
        dateOfCompletion = findViewById(R.id.txt_completionDate);
        customerRating = findViewById(R.id.txt_customerRating);
        customerName = findViewById(R.id.txt_custName);
        customerNumber = findViewById(R.id.txt_custNumber);
        serviceArea = findViewById(R.id.txt_serviceAddress);
        serviceDescription = findViewById(R.id.txt_serviceDescription);
        amountWillingToPay = findViewById(R.id.txt_willingAmountToPAy);
    }
}