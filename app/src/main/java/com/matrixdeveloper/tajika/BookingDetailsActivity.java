package com.matrixdeveloper.tajika;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingDetailsActivity extends AppCompatActivity {

    private String TAG = "BookingDetailsAct";
    private String id;
    private ImageView backPress, serviceImage;
    private TextView help, serviceName, serviceAddress, serviceType, serviceBookingId, serviceStatus, requestedOn;
    private TextView acceptedOn, requestNumber, serviceDate, serviceTime, serviceWorkDesc, serviceUserName;
    private TextView serviceUserContact, serviceUserAddress, amountToPay, amountWillingToPay, applyCoupon, finalAmountToPay;
    private TextView bookThisService, userInstruction, bookingDate;
    private String cancellationReason;
    private EditText cancellationComment;
    private ViewFlipper bookingViewFlipper;
    String status;
    private ConstraintLayout upcoming, pending, booked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        initViews();

        initListeners();

        id = getIntent().getStringExtra("service_id");
        status = getIntent().getStringExtra("status");

        switch (status) {
            case "Booked":
            case "Upcoming":
                upcoming.setVisibility(View.VISIBLE);
                booked.setVisibility(View.GONE);
                pending.setVisibility(View.GONE);
                break;
            case "Accepted":
                upcoming.setVisibility(View.GONE);
                booked.setVisibility(View.VISIBLE);
                pending.setVisibility(View.GONE);
                break;
            case "Declined":
            case "Pending":
                upcoming.setVisibility(View.GONE);
                booked.setVisibility(View.GONE);
                pending.setVisibility(View.VISIBLE);
                break;

        }

        getBookingDetails(id);
    }

    private void initViews() {

        //bookingViewFlipper = findViewById(R.id.vf_bookingViewFlipper);
        backPress = findViewById(R.id.iv_backPress);
        help = findViewById(R.id.txt_help);

        serviceName = findViewById(R.id.txt_serviceName);
        serviceAddress = findViewById(R.id.txt_serviceAddress);
        serviceType = findViewById(R.id.txt_serviceType);
        serviceBookingId = findViewById(R.id.txt_serviceBookingId);
        serviceStatus = findViewById(R.id.txt_serviceStatus);

        requestedOn = findViewById(R.id.txt_serviceRequestedOn);
        acceptedOn = findViewById(R.id.txt_serviceAcceptedOn);
        requestNumber = findViewById(R.id.txt_serviceRequestId);
        serviceDate = findViewById(R.id.txt_serviceDate);
        serviceTime = findViewById(R.id.txt_serviceTime);

        serviceWorkDesc = findViewById(R.id.txt_serviceWorkDescription);
        serviceUserName = findViewById(R.id.txt_serviceUserName);
        serviceUserContact = findViewById(R.id.txt_serviceUserContactNumber);
        serviceUserAddress = findViewById(R.id.txt_serviceUserAddress);
        amountToPay = findViewById(R.id.txt_serviceAmountToBePaid);
        amountWillingToPay = findViewById(R.id.txt_serviceUserAmountWillingToPay);
        finalAmountToPay = findViewById(R.id.txt_finalAmountToPay);
        applyCoupon = findViewById(R.id.txt_applyCoupon);
        bookThisService = findViewById(R.id.txt_bookThisService);
        userInstruction = findViewById(R.id.txt_serviceUserInstruction);
        bookingDate = findViewById(R.id.txt_serviceBookingDate);


        upcoming = findViewById(R.id.cl_upcoming);
        pending = findViewById(R.id.cl_pendingDeclined);
        booked = findViewById(R.id.cl_acceptedBooked);


    }

    private void initListeners() {
        backPress.setOnClickListener(view -> BookingDetailsActivity.super.onBackPressed());
        help.setOnClickListener(view -> startActivity(new Intent(BookingDetailsActivity.this, HelpActivity.class)));
    }

    private void getBookingDetails(String bookingID) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", 2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {
                RequestDetails requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);
                serviceName.setText("Name: " + requestDetails.getServiceName());
                serviceAddress.setText("Address: " + requestDetails.getAddress());
                serviceType.setText("Service Type: " + requestDetails.getServiceType());
                serviceBookingId.setText("Booking Id: " + requestDetails.getBookingId());
                serviceStatus.setText("Status: " + requestDetails.getStatus());
                requestedOn.setText(requestDetails.getRequestDate() + " at " + requestDetails.getRequestTime());
                acceptedOn.setText(requestDetails.getRequestAcceptDate() + " at " + requestDetails.getRequestAcceptTime());
                requestNumber.setText(requestDetails.getRequestId());
                serviceDate.setText(requestDetails.getServiceDate());
                serviceTime.setText(requestDetails.getServiceTime());
                serviceWorkDesc.setText(requestDetails.getWorkDescription());
                serviceUserName.setText("Name: " + requestDetails.getContactPersonName());
                serviceUserContact.setText("Contact No: " + requestDetails.getContactPersonPhone());
                serviceUserAddress.setText("Address: " + requestDetails.getServiceaddressBuildingNo() + " " + requestDetails.getServiceaddressStreetaddress() + " " + requestDetails.getServiceaddressLandmark());
                userInstruction.setText("Instruction: " + requestDetails.getInstruction());
                //amountToPay.setText(requestDetails.);
                amountWillingToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getWillingAmountPay());
                //finalAmountToPay.setText(requestDetails.);
                bookingDate.setText(requestDetails.getBookingDatetime());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void onCancelRequestClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_booking_cancellation_reason);

        ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
        Button submitCancellationRequest = dialog.findViewById(R.id.btn_submitCancellationRequest);
        Spinner spinner = dialog.findViewById(R.id.spinner);
        cancellationComment = dialog.findViewById(R.id.edt_cancellationComment);

        String[] simpleArrayEducation = {"I no longer need it", "Booked from somewhere else", "i don\'t like your service", "Response is late", "Fixed myself", "others"};
        ArrayAdapter aa = new ArrayAdapter(BookingDetailsActivity.this, android.R.layout.simple_spinner_item, simpleArrayEducation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cancellationReason = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dialogButton.setOnClickListener(v -> dialog.dismiss());

        submitCancellationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cancellationComment.getText().toString().equals("")) {
                    initiateRequestCancellation();
                } else {
                    Toast.makeText(BookingDetailsActivity.this, "Please provide cancellation Comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void initiateRequestCancellation() {

        JSONObject data = new JSONObject();
        try {
            data.put("id", 3);
            data.put("cancelation_reason", cancellationReason);
            data.put("cancelation_comment", cancellationComment.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.CANCEL_SERVICE_BOOKING, data, response -> {

            Utils.log(TAG, response.toString());
            if (response.optString("status").equals("200")) {
                final Dialog dialog = new Dialog(this);
                dialog.setCancelable(false);

                dialog.setContentView(R.layout.dialog_booking_cancelled);

                ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);

                dialogButton.setOnClickListener(v -> finish());

                dialog.show();
            }
        });
    }

}