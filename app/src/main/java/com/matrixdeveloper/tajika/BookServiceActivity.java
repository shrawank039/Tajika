package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.model.RequestDetails;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private String TAG = "BookServiceAct";
    private String bookingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> BookServiceActivity.super.onBackPressed());
    }

    private void bookService() {

        JSONObject data = new JSONObject();
        try {
            data.put("id", bookingID);
            data.put("contact_person_name", bookingID);
            data.put("contact_person_phone", bookingID);
            data.put("serviceaddress_building_no", bookingID);
            data.put("serviceaddress_streetaddress", bookingID);
            data.put("serviceaddress_landmark", bookingID);
            data.put("instruction", bookingID);
            data.put("adminpayableamount", bookingID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiCall.postMethod(this, ServiceNames.UPDATE_SERVICE_BOOKING_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

        });
    }
}