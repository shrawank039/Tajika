package com.matrixdeveloper.tajika;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.model.UserBookingDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingDetailsActivity extends AppCompatActivity {

    private String TAG = "BookingDetailsAct";
    private String id;
    private ImageView backPress;
    private TextView help;
    private LinearLayout bookingMessage, bookingCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        backPress = findViewById(R.id.iv_backPress);
        help = findViewById(R.id.txt_help);
        bookingMessage=findViewById(R.id.ll_bookingMessage);
        bookingCall=findViewById(R.id.ll_bookingCall);

        initListeners();

        id = getIntent().getStringExtra("id");

        getBookingDetails(id);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> BookingDetailsActivity.super.onBackPressed());
        help.setOnClickListener(view -> startActivity(new Intent(BookingDetailsActivity.this, HelpActivity.class)));
        bookingMessage.setOnClickListener(view ->initiateBookingCall());
        bookingMessage.setOnClickListener(view ->initiateBookingMessage());

    }

    private void initiateBookingCall() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        String temp = "tel:1234567890";
        callIntent.setData(Uri.parse(temp));
        startActivity(callIntent);
    }

    private void initiateBookingMessage() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    private void getBookingDetails(String bookingID) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", bookingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SERVICE_PROVIDER_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                UserBookingDetails userBookingDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), UserBookingDetails.class);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    public void onCancelRequestClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_booking_cancellation_reason);

        ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}