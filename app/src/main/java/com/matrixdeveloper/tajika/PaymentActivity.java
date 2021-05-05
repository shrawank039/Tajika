package com.matrixdeveloper.tajika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.finserve.pgw_sdk_android.forpublic.BillQueryResp;
import com.finserve.pgw_sdk_android.forpublic.ErrorResponse;
import com.finserve.pgw_sdk_android.forpublic.PGWBillQueryResponse;
import com.finserve.pgw_sdk_android.forpublic.PGWPaymentQueryResponse;
import com.finserve.pgw_sdk_android.forpublic.PaymentQueryResp;
import com.finserve.pgw_sdk_android.forpublic.PgwSdk;
import com.finserve.pgw_sdk_android.forpublic.SetUpResp;
import com.finserve.pgw_sdk_android.forpublic.auth.AuthData;

import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {

    String merchantCode= "4157993027"; // This is a five digit number that is specified in the welcome email
    // You will generate outlet code, api key, customer key and customer secret from  merchant portal. Go to the Manage API Key menu to generate a new key
    String outletCode = "0000000000";
    String password= "NoVjXD73cBGgCqLJODU2cdLwiV9j6cgG";
    String apiKey = "Basic b21MaWIxQXl1a0VMUkxBTWdUQXowY1B2N1V1Z1RtN1I6MEJKWVZ6WEtrR0ZWU2puNQ==";
    String orderRef = "";
    PgwSdk sdk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //Initializing the SDK

        AuthData authData = new AuthData(merchantCode, password, merchantCode,outletCode,apiKey); //they have to be passed in this order.
        PgwSdk.getSDK(authData, new SetUpResp() {
            @Override
            public void onConnected(PgwSdk pgwSdk) {
                sdk = pgwSdk; //am assuming you already declared this variable somewhere.
                Toast.makeText(PaymentActivity.this, "sdk: connected", Toast.LENGTH_SHORT).show();
                //TODO: SDK is ready for you to consume.
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                //TODO: an error occurred. You have access to the message code and a message through ErrorResponse
                Toast.makeText(PaymentActivity.this, "sdk: error", Toast.LENGTH_SHORT).show();
            }


        }, this);


    }

    private void createOrder() {
        //use sdk initialized in step 3.
        if(sdk.isConnected()){
            String customerWebSite = "https://www.finserve.africa"; //merchantWebsite
            String orderRef = "REF0001BN"; //Random minimum 9 characters
            float orderAmount = 10.03f; // order reference
            String currency = "KES";
            String orderDescription = "This is an order. A test order";
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 2);
            String customerName = "testCustomer"; //Company   Name
            String outletCode = "0000000000"; // Outlet Code
            Date orderExpiryDate = calendar.getTime();
            String customerFirstName = "John"; //your customer first name
            String customerlastName = "Doe"; //your customer lastname
            String customerEmail = "johndoe@abc.com"; //your customer email
            String customerPostcodeZip = "00100"; //your customer posta;
            String customerCity = "Nairobi"; //your customer city
            String customerAddress = "Test Address"; //your customer address
            String customerCountry = "Kenya";//your country

            Intent intent = sdk.createOrder(orderAmount, orderRef, customerWebSite, customerName, outletCode, orderExpiryDate, orderDescription, currency, customerFirstName,customerlastName,customerEmail,customerPostcodeZip, customerCity,customerAddress,customerCountry);

            startActivityForResult(intent, PgwSdk.CREATE_ORDER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PgwSdk.CREATE_ORDER){
            if(resultCode == RESULT_OK){
                //TODO: receive the response from the data
                Log.d("data","createbill transactionRef :" + data.getStringExtra("transactionRef"));
                Log.d("data"," Customer OrderRef :" + data.getStringExtra("billReference"));
            } else if (resultCode == RESULT_CANCELED){
                //TODO: request canceled by user. Handle it
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void queryPayment() {

        sdk.queryPayment(orderRef,  new PGWPaymentQueryResponse() {
            @Override
            public void onPaymentQuery(PaymentQueryResp paymentQueryResp) {
                //TODO: payment response with status, order reference and amount

                Log.d("retro","payment Status :" + paymentQueryResp.getStatus());
                Log.d("retro","payment transactionId :" + paymentQueryResp.getTransactionId());
            }

            @Override
            public void onError(ErrorResponse errorResponse) {
                //TODO: receive error incase there was a problem with the request.
            }
        });

    }

    private void queryBill() {

        sdk.queryBill(orderRef, new PGWBillQueryResponse() {
            @Override
            public void onError(ErrorResponse errorResponse) {
                //TODO: time to panic. You got an error.

            }

            @Override
            public void onBillQuery(BillQueryResp queryResp) {
                //TODO: see, I said you will get useful data.

                Log.d("retro","Bill Status :" + queryResp.getBillStatus());
                Log.d("retro","Bill amount :" + queryResp.getAmount());
            }
        });
    }

    public void intiatePay(View view) {
        createOrder();
    }
}