package com.matrixdeveloper.tajika;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.helpers.ExpandableTextView;
import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class BookingDetailsActivity extends AppCompatActivity {

    private final String TAG = "BookingDetailsAct";
    private String id;
    private double discount = 0.0;
    private ImageView backPress, pdBackPress, upBackPress, ccBackPress;
    private PrefManager prf;

    //for accepted booked
    private ImageView abServiceImage;
    private TextView abServiceName, abServiceAddress, abServiceType, abServiceBookingId, abServiceStatus, abRequestedOn;
    private TextView abAcceptedOn, abRequestNumber, abServiceDate, abServiceTime, abServiceUserName;
    private ExpandableTextView abServiceWorkDesc, pdServiceWorkDesc, upServiceWorkDesc, ccServiceWorkDesc;
    private TextView abServiceUserContact, abServiceUserAddress, abUserInstruction, abAmountToPay, abAmountWillingToPay, applyCoupon, abFinalAmountToPay, abServiceTaxAmount;
    private TextView appbarTitle, bookThisService, abContactUs, abHelp;;

    //for pending declined
    private ImageView pdServiceImage;
    private TextView pdServiceName, pdServiceAddress, pdServiceType, pdServiceStatus, pdRequestedOn;
    private TextView pdRequestNumber, pdServiceDate, pdServiceTime, pdAmountToBePay;
    private TextView  pdContactUs, pdHelp;

    //for upComing
    private ImageView upServiceImage;
    private TextView upServiceName, upServiceAddress, upServiceType, upServiceBookingId, upServiceBookingDate, upServiceStatus;
    private TextView upServiceDate, upServiceTime, upServiceUserName, upServiceUserContact, upServiceUserAddress, upUserInstruction, upAmountToBePaid, upAmountPayableToProvider;
    private TextView  upContactUs, upHelp;

    //for Completed Cancelled
    private ImageView ccServiceImage;
    private TextView ccServiceName, ccServiceAddress, ccServiceType, ccServiceBookingId, ccServiceBookingDate, ccServiceStatus;
    private TextView ccServiceDate, ccServiceTime, ccServiceUserName, ccServiceUserContact, ccServiceUserAddress, ccUserInstruction;
    private TextView ccAmountToBePaid, ccAmountPaidToProvider, ccCancelledOn, ccCancellationReason, ccCancellationCharge, ccCancellationComment, ccContactUs, ccHelp;
    private RatingBar ccRateYourExperience;
    private Button submitRating;

    private String cancellationReason;
    private EditText cancellationComment;
    private ViewFlipper bookingViewFlipper;
    private CardView congratsContainer, cheersContainer;
    private LinearLayout abContainerServiceAddress, abContainerBottomBookCancel, abContainerRequestSummery, abCouponContainer, ccCancellationDetailsContainer, ccRateYourExperienceContainer;
    private ConstraintLayout abContainerContactHelp;
    RequestDetails requestDetails;
    private String status;
    double finalAmount;
    private LinearLayout ll_voiceCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        initViews();

        initListeners();

        id = getIntent().getStringExtra("booking_id");
        status = getIntent().getStringExtra("status");

        switch (status) {
            case "Booked":
                appbarTitle.setText("Booking Confirmation");
                bookingViewFlipper.setDisplayedChild(2);
//                abServiceBookingId.setVisibility(View.VISIBLE);
//                congratsContainer.setVisibility(View.VISIBLE);
//                cheersContainer.setVisibility(View.GONE);
//                abContainerRequestSummery.setVisibility(View.GONE);
//                abContainerServiceAddress.setVisibility(View.VISIBLE);
//                abCouponContainer.setVisibility(View.GONE);
//                abContainerBottomBookCancel.setVisibility(View.GONE);
//                abContainerContactHelp.setVisibility(View.VISIBLE);
                break;
            case "Accepted":
                bookingViewFlipper.setDisplayedChild(0);
                abServiceBookingId.setVisibility(View.GONE);
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
            case "Cancelled":
                bookingViewFlipper.setDisplayedChild(3);
                ccRateYourExperienceContainer.setVisibility(View.GONE);
                break;
            case "Completed":
                bookingViewFlipper.setDisplayedChild(3);
                ccCancellationDetailsContainer.setVisibility(View.GONE);
                break;
        }
        getBookingDetails(id);
    }

    private void initViews() {

        prf = new PrefManager(this);
        bookingViewFlipper = findViewById(R.id.vf_bookingViewFlipper);
        backPress = findViewById(R.id.iv_backPress);

        // for accepted booked service viewFlipper--> ab stands for accepted_booked
        ll_voiceCall = findViewById(R.id.ll_voiceCall);
        pdServiceImage = findViewById(R.id.iv_abServiceImage);
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
        abServiceTaxAmount = findViewById(R.id.txt_abServiceTaxAmount);
        bookThisService = findViewById(R.id.txt_bookThisService);
        appbarTitle = findViewById(R.id.txt_appbartitle);
        abHelp = findViewById(R.id.txt_abHelp);
        abContactUs = findViewById(R.id.txt_abContactUs);

        //Containers to show or Hide
        abContainerRequestSummery = findViewById(R.id.ll_abRequestSummeryContainer);
        abContainerServiceAddress = findViewById(R.id.ll_abServiceAddressContainer);
        abContainerBottomBookCancel = findViewById(R.id.ll_abBottomBookCancelContainer);
        congratsContainer = findViewById(R.id.cv_congratulationContainer);
        cheersContainer = findViewById(R.id.cv_cheersContainer);
        abCouponContainer = findViewById(R.id.ll_abCouponContainer);
        abContainerContactHelp = findViewById(R.id.cl_containerContactHelp);
        ccCancellationDetailsContainer = findViewById(R.id.ll_cancellationDetails);
        ccRateYourExperienceContainer = findViewById(R.id.ll_rateExperience);


        // for pending declined service viewFlipper --> pd stands for pending_declined
        pdServiceImage = findViewById(R.id.iv_pdServiceImage);
        pdBackPress = findViewById(R.id.iv_pdBackPress);
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
        pdHelp = findViewById(R.id.txt_pdHelp);
        pdContactUs = findViewById(R.id.txt_pdContactUs);


        // for upcoming service viewFlipper --> up stands for upcoming
        pdServiceImage = findViewById(R.id.iv_upServiceImage);
        upBackPress = findViewById(R.id.iv_upBackPress);
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
        upAmountPayableToProvider = findViewById(R.id.txt_upServiceAmountToBePaidToProvider);
        upHelp = findViewById(R.id.txt_upHelp);
        upContactUs = findViewById(R.id.txt_upContactUs);

        // for Completed Cancelled service viewFlipper --> cc stands for Completed Cancelled
        pdServiceImage = findViewById(R.id.iv_ccServiceImage);
        ccBackPress = findViewById(R.id.iv_ccBackPress);
        ccServiceName = findViewById(R.id.txt_ccServiceName);
        ccServiceAddress = findViewById(R.id.txt_ccServiceAddress);
        ccServiceType = findViewById(R.id.txt_ccServiceType);
        ccServiceBookingId = findViewById(R.id.txt_ccServiceBookingId);
        ccServiceBookingDate = findViewById(R.id.txt_ccServiceBookingDate);
        ccServiceStatus = findViewById(R.id.txt_ccServiceStatus);
        ccServiceDate = findViewById(R.id.txt_ccServiceDate);
        ccServiceTime = findViewById(R.id.txt_ccServiceTime);
        ccServiceWorkDesc = findViewById(R.id.txt_ccServiceWorkDescription);
        ccServiceUserName = findViewById(R.id.txt_ccServiceUserName);
        ccServiceUserContact = findViewById(R.id.txt_ccServiceUserContactNumber);
        ccServiceUserAddress = findViewById(R.id.txt_ccServiceUserAddress);
        ccUserInstruction = findViewById(R.id.txt_ccServiceUserInstruction);
        ccAmountToBePaid = findViewById(R.id.txt_ccServiceAmountToBePaid);
        ccAmountPaidToProvider = findViewById(R.id.txt_ccServiceAmountToBePaidToProvider);
        ccCancelledOn = findViewById(R.id.txt_ccCancelledOn);
        ccCancellationCharge = findViewById(R.id.txt_ccCancellationCharge);
        ccCancellationReason = findViewById(R.id.txt_ccCancellationReason);
        ccCancellationComment = findViewById(R.id.txt_ccCancellationComment);
        ccRateYourExperience = findViewById(R.id.rb_ccRateYourExperience);
        submitRating = findViewById(R.id.btn_ccSubmitRating);
        ccHelp = findViewById(R.id.txt_ccHelp);
        ccContactUs = findViewById(R.id.txt_ccContactUs);

    }

    private void initListeners() {

        ll_voiceCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + requestDetails.getContactPersonPhone();
            callIntent.setData(Uri.parse(temp));
            startActivity(callIntent);
        });

        backPress.setOnClickListener(v -> super.onBackPressed());
        pdBackPress.setOnClickListener(v -> super.onBackPressed());
        upBackPress.setOnClickListener(v -> super.onBackPressed());
        ccBackPress.setOnClickListener(v -> super.onBackPressed());
        bookThisService.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), BookServiceActivity.class)
                    .putExtra("booking_id", id).putExtra("amount", String.valueOf(finalAmount)).putExtra("discount", String.valueOf(discount))
            );
        });
        applyCoupon.setOnClickListener(v -> {
            dialogApplyCoupon();
        });
        submitRating.setOnClickListener(v -> addRating());
        ccHelp.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        pdHelp.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        abHelp.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        upHelp.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));

        ccContactUs.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        pdContactUs.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        abContactUs.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
        upContactUs.setOnClickListener(v -> startActivity(new Intent(this, HelpActivity.class)));
    }

    private void dialogApplyCoupon() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_apply_coupon);

        EditText enterCoupon = dialog.findViewById(R.id.edt_enterCoupon);
        TextView txtApply = dialog.findViewById(R.id.txt_apply);
        TextView txtCancel = dialog.findViewById(R.id.txt_cancel);
        ImageView closeDialog = dialog.findViewById(R.id.iv_dialogCancel);

        txtApply.setOnClickListener(v -> {
            JSONObject data = new JSONObject();
            try {
                data.put("user_id", prf.getString("id"));
                data.put("coupen_code", enterCoupon.getText().toString().trim());
                data.put("total_amount", requestDetails.getWillingAmountPay());
                data.put("request_id", requestDetails.getRequestId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiCall.postMethod(this, ServiceNames.VALIDATE_COUPON, data, response -> {

                JSONObject jsonObject = null;
                if (Objects.equals(response.opt("status"), 200)) {
                    try {
                        jsonObject = response.optJSONObject("data");
                        double marginAmount = jsonObject.optDouble("margin_amount");
                        double totalAmount = Double.parseDouble(requestDetails.getWillingAmountPay());
                        finalAmount = totalAmount - marginAmount;
                        abAmountWillingToPay.setText(requestDetails.getCurrency() + " " + marginAmount);
                        dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(this, "" + response.optString("message"), Toast.LENGTH_SHORT).show();
            });
        });
        txtCancel.setOnClickListener(v -> dialog.dismiss());
        closeDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void getBookingDetails(String bookingID) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", bookingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {
                requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);

                finalAmount = requestDetails.getAdminpayableamount();

                //for accepted booked service request
                //Glide.with(this).load(requestDetails.).placeholder(R.drawable.app_logo).into(abServiceImage);
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
                abAmountWillingToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getWillingAmountPay());
                abFinalAmountToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());
                abServiceTaxAmount.setText(requestDetails.getCurrency() + " " + requestDetails.getServiceTaxFee());
                abAmountToPay.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());
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
                pdAmountToBePay.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());

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
                upAmountToBePaid.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());
                upAmountPayableToProvider.setText(requestDetails.getCurrency() + " " + requestDetails.getWillingAmountPay());

                //for Cancelled Completed service request
                ccServiceName.setText("Name: " + requestDetails.getServiceName());
                ccServiceAddress.setText("Address: " + requestDetails.getAddress());
                ccServiceType.setText("Service Type: " + requestDetails.getServiceType());
                ccServiceBookingId.setText("Booking Id: " + requestDetails.getBookingId());
                ccServiceBookingDate.setText("Booked on: " + requestDetails.getBookingDatetime());
                ccServiceStatus.setText("Status: " + requestDetails.getStatus());
                ccServiceDate.setText(requestDetails.getServiceDate());
                ccServiceTime.setText(requestDetails.getServiceTime());
                ccServiceWorkDesc.setText(requestDetails.getWorkDescription());
                ccServiceUserName.setText("Name: " + requestDetails.getContactPersonName());
                ccServiceUserContact.setText("Contact No: " + requestDetails.getContactPersonPhone());
                ccServiceUserAddress.setText("Address: " + requestDetails.getServiceaddressBuildingNo() + " " + requestDetails.getServiceaddressStreetaddress() + " " + requestDetails.getServiceaddressLandmark());
                ccUserInstruction.setText("Instruction: " + requestDetails.getInstruction());
                ccAmountToBePaid.setText(requestDetails.getCurrency() + " " + requestDetails.getAdminpayableamount());
                ccAmountPaidToProvider.setText(requestDetails.getCurrency() + " " + requestDetails.getWillingAmountPay());
                ccCancelledOn.setText(getString(R.string.cancelled_on) + requestDetails.getCancelationDate() + " at " + requestDetails.getCancelationTime());
                //CancellationCharge and Rating missing
                ccCancellationCharge.setText(getString(R.string.cancellation_charge) + requestDetails.getCurrency() + " "+ requestDetails.getCancellationCharges());

                ccRateYourExperience.setRating(requestDetails.getUserRating());

                if (requestDetails.getUserRating() != 0){
                    submitRating.setVisibility(View.GONE);
                    ccRateYourExperience.setIsIndicator(true);
                    ccRateYourExperience.setFocusable(false);
                }


                ccCancellationComment.setText(getString(R.string.cancellation_comment) + requestDetails.getCancelationComment());
                ccCancellationReason.setText(getString(R.string.cancellation_reason) + requestDetails.getCancelationReason());
                Glide.with(this).load(requestDetails.getServiceProviderImage()).placeholder(R.drawable.app_logo).into(pdServiceImage);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void onCancelRequestClick(View view) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_booking_cancellation_reason);

        ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
        Button submitCancellationRequest = dialog.findViewById(R.id.btn_submitCancellationRequest);
        Spinner spinner = dialog.findViewById(R.id.spinner);
        cancellationComment = dialog.findViewById(R.id.edt_cancellationComment);

        TextView cc = dialog.findViewById(R.id.txt_cancellationCharge);
        cc.setText(getString(R.string.cancellation_charge) + requestDetails.getCurrency() + " "+ requestDetails.getCancellationCharges());

        String[] simpleArrayEducation = {"Select Reason", "I no longer need it", "Booked from somewhere else", "i don't like your service", "Response is late", "Fixed myself", "Others"};
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
            data.put("user_id", prf.getString("id"));
            data.put("id", id);
            data.put("cancelation_reason", cancellationReason);
            data.put("cancelation_comment", cancellationComment.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.CANCEL_SERVICE_BOOKING, data, response -> {

            Utils.log(TAG, response.toString());
            if (response.optString("status").equals("200")) {
                final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);

                dialog.setContentView(R.layout.dialog_booking_cancelled);

                ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);

                TextView cc = dialog.findViewById(R.id.txt_cancelledCharge);
                cc.setText(getString(R.string.cancellation_charge) + requestDetails.getCurrency() + " "+ requestDetails.getCancellationCharges());

                dialogButton.setOnClickListener(v -> finish());

                dialog.show();
            }
        });
    }

    private void addRating() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
            data.put("service_id", id);
            data.put("rate", ccRateYourExperience.getRating());

        } catch (Exception e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.ADD_RATING, data, response -> {

            Utils.log(TAG, response.toString());
            if (response.optString("status").equals("200")) {
                submitRating.setVisibility(View.GONE);
                Utils.toast(getApplicationContext(), "Thanks for Rating.");
            }
        });
    }

    public void onHelpClick(View view) {
        new Intent(BookingDetailsActivity.this, HelpActivity.class);
    }

}

