package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginThroughOTPActivity extends AppCompatActivity {

    private String TAG = "LoginThroughOTPAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_through_otp);
    }

    public void onLoginThroughPasswordCLick(View view) {
        super.onBackPressed();
    }

    public void onLoginThroughOTPNEXTCLick(View view) {

        EditText edtEmail = findViewById(R.id.edt_email);
        String email = edtEmail.getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(LoginThroughOTPActivity.this, ServiceNames.SEND_OTP, data, response -> {

            Utils.log(TAG, response.toString());
            Utils.toast(LoginThroughOTPActivity.this, response.optString("message"));

            if (response.optInt("status") == 200){
                JSONObject dataObj = response.optJSONObject("data");
                assert dataObj != null;
                startActivity(new Intent(
                        this, OTPInputActivity.class
                ).putExtra("email", email).putExtra("otp", dataObj.optString("otp")));
            }

        });
    }
}