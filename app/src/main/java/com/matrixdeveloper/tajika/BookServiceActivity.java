package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BookServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private final String TAG = "BookServiceAct";
    String bookingID, amount, discount;
    double payableAmt;
    private EditText name, phoneNUmber, flatNumber, streetAddress, landmark, anyInstruction;
    private LinearLayout llMakePayment;
    private final int PAYMENT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        bookingID = getIntent().getStringExtra("booking_id");
        amount = getIntent().getStringExtra("amount");
        discount = getIntent().getStringExtra("discount");

        payableAmt = Double.parseDouble(amount) - Double.parseDouble(discount);

        initViews();

        backPress.setOnClickListener(view -> BookServiceActivity.super.onBackPressed());
        llMakePayment.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PaymentWebViewActivity.class);
            intent.putExtra("amount",String.valueOf(payableAmt));
            intent.putExtra("order_desc","service booking");
            startActivityForResult(intent, PAYMENT_REQUEST);
                });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT_REQUEST) {
            if (resultCode == 1) {
                bookService();
            } else {
                bookService();
                Utils.toast(getApplicationContext(), "Payment Failed!!!");
            }
        }

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
            data.put("discount", discount);
            data.put("adminpayableamount", String.valueOf(payableAmt));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.UPDATE_SERVICE_BOOKING_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
            startActivity(new Intent(getApplicationContext(), BookingActivity.class));
            finish();

        });
    }
}