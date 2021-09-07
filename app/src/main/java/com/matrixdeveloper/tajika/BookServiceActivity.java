package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private String TAG = "BookServiceAct";
    String bookingID;
    private EditText name, phoneNUmber, flatNumber, streetAddress, landmark, anyInstruction;
    private LinearLayout llMakePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        bookingID = getIntent().getStringExtra("booking_id");

        initViews();

        backPress.setOnClickListener(view -> BookServiceActivity.super.onBackPressed());
        llMakePayment.setOnClickListener(view -> bookService());

    }

    private void initViews() {
        backPress = findViewById(R.id.iv_backPress);

        name = findViewById(R.id.edt_name);
        phoneNUmber = findViewById(R.id.edt_phoneNUmber);
        flatNumber = findViewById(R.id.edt_flatNumber);
        streetAddress = findViewById(R.id.edt_streetAddress);
        landmark = findViewById(R.id.edt_landmark);
        anyInstruction = findViewById(R.id.edt_anyInstruction);
        llMakePayment = findViewById(R.id.ll_makePayment);

    }

    private void bookService() {

        JSONObject data = new JSONObject();
        try {
            data.put("id", bookingID);
            data.put("contact_person_name", name.getText().toString());
            data.put("contact_person_phone", phoneNUmber.getText().toString());
            data.put("serviceaddress_building_no", flatNumber.getText().toString());
            data.put("serviceaddress_streetaddress", streetAddress.getText().toString());
            data.put("serviceaddress_landmark", landmark.getText().toString());
            data.put("instruction", anyInstruction.getText().toString());
            data.put("adminpayableamount", "5050");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.UPDATE_SERVICE_BOOKING_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));

        });
    }
}