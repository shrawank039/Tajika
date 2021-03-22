package com.matrixdeveloper.tajika;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.model.BookingDetails;
import com.matrixdeveloper.tajika.model.ServiceProviderDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookingDetailsActivity extends AppCompatActivity {

    private String TAG = "BookingDetailsAct";
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        id = getIntent().getStringExtra("id");

        getBookingDetails(id);
    }

    private void getBookingDetails(String bookingID) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", bookingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getApplicationContext(), ServiceNames.SERVICE_PROVIDER_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                BookingDetails bookingDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), BookingDetails.class);

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