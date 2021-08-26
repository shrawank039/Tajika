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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

    //for accepted booked
    private TextView abServiceName, abServiceAddress, abServiceType, abServiceBookingId, abServiceStatus, abRequestedOn;
    private TextView abAcceptedOn, abRequestNumber, abServiceDate, abServiceTime, abServiceWorkDesc, abServiceUserName;
    private TextView abServiceUserContact, abServiceUserAddress, abUserInstruction, abAmountToPay, abAmountWillingToPay, applyCoupon, abFinalAmountToPay;
    private TextView bookThisService;

    //for pending declined
    private TextView pdServiceName, pdServiceAddress, pdServiceType, pdServiceStatus, pdRequestedOn;
    private TextView pdRequestNumber, pdServiceDate, pdServiceTime, pdServiceWorkDesc, pdAmountToBePay;

    //for pending declined
    private TextView upServiceName, upServiceAddress, upServiceType, upServiceBookingId, upServiceBookingDate, upServiceStatus;
    private TextView upServiceDate, upServiceTime, upServiceWorkDesc, upServiceUserName, upServiceUserContact, upServiceUserAddress, upUserInstruction, upAmountToBePaid;

    private String cancellationReason;
    private EditText cancellationComment;
    private ViewFlipper bookingViewFlipper;
    private CardView congratsContainer, cheersContainer;
    private LinearLayout abContainerServiceAddress, abContainerBottomBookCancel, abContainerRequestSummery, abCouponContainer;
    private ConstraintLayout abContainerContactHelp;

    private String status;

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
                bookingViewFlipper.setDisplayedChild(0);
                congratsContainer.setVisibility(View.VISIBLE);
                cheersContainer.setVisibility(View.GONE);
                abContainerRequestSummery.setVisibility(View.GONE);
                abContainerServiceAddress.setVisibility(View.VISIBLE);
                abCouponContainer.setVisibility(View.GONE);
                abContainerBottomBookCancel.setVisibility(View.GONE);
                abContainerContactHelp.setVisibility(View.VISIBLE);
                break;
            case "Accepted":
                bookingViewFlipper.setDisplayedChild(0);
                congratsContainer.setVisibility(View.GONE);
                cheersContainer.setVisibility(View.VISIBLE);
                abContainerRequestSummery.setVisibility(View.VISIBLE);
                abContainerServiceAddress.setVisibility(View.GONE);
                abCouponContainer.setVisibility(View.VISIBLE);
                abContainerBottomBookCancel.setVisibility(View.VISIBLE);
                abContainerContactHelp.setVisibility(View.GONE);
                break;
            case "Declined":
            case "Pending":
                bookingViewFlipper.setDisplayedChild(1);
                break;
            case "Upcoming":
                bookingViewFlipper.setDisplayedChild(2);
                break;

        }

        getBookingDetails(id);
    }

    private void initViews() {

        bookingViewFlipper = findViewById(R.id.vf_bookingViewFlipper);
        backPress = findViewById(R.id.iv_backPress);

        // for accepted booked service viewFlipper--> ab stands for accepted_booked
        abServiceName = findViewById(R.id.txt_abServiceName);
        abServiceAddress = findViewById(R.id.txt_abServiceAddress);
        abServiceType = findViewById(R.id.txt_abServiceType);
        abServiceBookingId = findViewById(R.id.txt_abServiceBookingId);
        abServiceStatus = findViewById(R.id.txt_abServiceStatus);
        abRequestedOn = findViewById(R.id.txt_abServiceRequestedOn);
        abAcceptedOn = findViewById(R.id.txt_abServiceAcceptedOn);
        abRequestNumber = findViewById(R.id.txt_abServiceRequestId);
        abServiceDate = findViewById(R.id.txt_abServiceDate);
        abServiceTime = findViewById(R.id.txt_abServiceTime);
        abServiceWorkDesc = findViewById(R.id.txt_abServiceWorkDescription);
        abServiceUserName = findViewById(R.id.txt_abServiceUserName);
        abServiceUserContact = findViewById(R.id.txt_abServiceUserContactNumber);
        abServiceUserAddress = findViewById(R.id.txt_abServiceUserAddress);
        abUserInstruction = findViewById(R.id.txt_abServiceUserInstruction);
        abAmountToPay = findViewById(R.id.txt_abServiceAmountToBePaid);
        abAmountWillingToPay = findViewById(R.id.txt_abServiceUserAmountWillingToPay);
        applyCoupon = findViewById(R.id.txt_applyCoupon);
        abFinalAmountToPay = findViewById(R.id.txt_abFinalAmountToPay);
        bookThisService = findViewById(R.id.txt_bookThisService);

        //Containers to show or Hide
        abContainerRequestSummery = findViewById(R.id.ll_abRequestSummeryContainer);
        abContainerServiceAddress = findViewById(R.id.ll_abServiceAddressContainer);
        abContainerBottomBookCancel = findViewById(R.id.ll_abBottomBookCancelContainer);
        congratsContainer = findViewById(R.id.cv_congratulationContainer);
        cheersContainer = findViewById(R.id.cv_cheersContainer);
        abCouponContainer = findViewById(R.id.ll_abCouponContainer);
        abContainerContactHelp = findViewById(R.id.cl_containerContactHelp);


        // for pending declined service viewFlipper --> pd stands for pending_declined
        pdServiceName = findViewById(R.id.txt_pdServiceName);
        pdServiceAddress = findViewById(R.id.txt_pdServiceAddress);
        pdServiceType = findViewById(R.id.txt_pdServiceType);
        pdServiceStatus = findViewById(R.id.txt_pdServiceStatus);
        pdRequestedOn = findViewById(R.id.txt_pdServiceRequestedOn);
        pdRequestNumber = findViewById(R.id.txt_pdServiceRequestId);
        pdServiceDate = findViewById(R.id.txt_pdServiceDate);
        pdServiceTime = findViewById(R.id.txt_pdServiceTime);
        pdServiceWorkDesc = findViewById(R.id.txt_pdServiceWorkDescription);
        pdAmountToBePay = findViewById(R.id.txt_pdServiceAmountToBePaid);


        // for upcoming service viewFlipper --> pd stands for pending_declined
        upServiceName = findViewById(R.id.txt_upServiceName);
        upServiceAddress = findViewById(R.id.txt_upServiceAddress);
        upServiceType = findViewById(R.id.txt_upServiceType);
        upServiceBookingId = findViewById(R.id.txt_upServiceBookingId);
        upServiceBookingDate = findViewById(R.id.txt_upServiceBookingDate);
        upServiceStatus = findViewById(R.id.txt_upServiceStatus);
        upServiceDate = findViewById(R.id.txt_upServiceDate);
        upServiceTime = findViewById(R.id.txt_upServiceTime);
        upServiceWorkDesc = findViewById(R.id.txt_upServiceWorkDescription);
        upServiceUserName = findViewById(R.id.txt_upServiceUserName);
        upServiceUserContact = findViewById(R.id.txt_upServiceUserContactNumber);
        upServiceUserAddress = findViewById(R.id.txt_upServiceUserAddress);
        upUserInstruction = findViewById(R.id.txt_upServiceUserInstruction);
        upAmountToBePaid = findViewById(R.id.txt_upServiceAmountToBePaid);

    }

    private void initListeners() {
        backPress.setOnClickListener(view -> BookingDetailsActivity.super.onBackPressed());
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

                //for accepted booked service request
                abServiceName.setText("Name: " + requestDetails.getServiceName());
                abServiceAddress.setText("Address: " + requestDetails.getAddress());
                abServiceType.setText("Service Type: " + requestDetails.getServiceType());
                abServiceBookingId.setText("Booking Id: " + requestDetails.getBookingId());
                abServiceStatus.setText("Status: " + requestDetails.getStatus());
                abRequestedOn.setText(requestDetails.getRequestDate() + " at " + requestDetails.getRequestTime());
                abAcceptedOn.setText(requestDetails.getRequestAcceptDate() + " at " + requestDetails.getRequestAcceptTime());
                abRequestNumber.setText(requestDetails.getRequestId());
                abServiceDate.setText(requestDetails.getServiceDate());
                abServiceTime.setText(requestDetails.getServiceTime());
                abServiceWorkDesc.setText(requestDetails.getWorkDescription());
                abServiceUserName.setText("Name: " + requestDetails.getContactPersonName());
                abServiceUserContact.setText("Contact No: " + requestDetails.getContactPersonPhone());
                abServiceUserAddress.setText("Address: " + requestDetails.getServiceaddressBuildingNo() + " " + requestDetails.getServiceaddressStreetaddress() + " " + requestDetails.getServiceaddressLandmark());
                abUserInstruction.setText("Instruction: " + requestDetails.getInstruction());
                //amountToPay.setText(requestDetails.);
                abAmountWillingToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getWillingAmountPay());
                //finalAmountToPay.setText(requestDetails.);


                //for pending declined service request
                pdServiceName.setText("Name: " + requestDetails.getServiceName());
                pdServiceAddress.setText("Address: " + requestDetails.getAddress());
                pdServiceType.setText("Service Type: " + requestDetails.getServiceType());
                pdServiceStatus.setText("Status: " + requestDetails.getStatus());
                pdRequestedOn.setText(requestDetails.getRequestDate() + " at " + requestDetails.getRequestTime());
                pdRequestNumber.setText(requestDetails.getRequestId());
                pdServiceDate.setText(requestDetails.getServiceDate());
                pdServiceTime.setText(requestDetails.getServiceTime());
                pdServiceWorkDesc.setText(requestDetails.getWorkDescription());
                //pdAmountToBePay.setText(requestDetails.);


                //for upcoming service request
                upServiceName.setText("Name: " + requestDetails.getServiceName());
                upServiceAddress.setText("Address: " + requestDetails.getAddress());
                upServiceType.setText("Service Type: " + requestDetails.getServiceType());
                upServiceBookingId.setText("Booking Id: " + requestDetails.getBookingId());
                upServiceBookingDate.setText("Booked on: " + requestDetails.getBookingDatetime());
                upServiceStatus.setText("Status: " + requestDetails.getStatus());
                upServiceDate.setText(requestDetails.getServiceDate());
                upServiceTime.setText(requestDetails.getServiceTime());
                upServiceWorkDesc.setText(requestDetails.getWorkDescription());
                upServiceUserName.setText("Name: " + requestDetails.getContactPersonName());
                upServiceUserContact.setText("Contact No: " + requestDetails.getContactPersonPhone());
                upServiceUserAddress.setText("Address: " + requestDetails.getServiceaddressBuildingNo() + " " + requestDetails.getServiceaddressStreetaddress() + " " + requestDetails.getServiceaddressLandmark());
                upUserInstruction.setText("Instruction: " + requestDetails.getInstruction());
                //upAmountToBePaid.setText(requestDetails.);

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

    public void onHelpClick(View view) {
        new Intent(BookingDetailsActivity.this, HelpActivity.class);
    }
}