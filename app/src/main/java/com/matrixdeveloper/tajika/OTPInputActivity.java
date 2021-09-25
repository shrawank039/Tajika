package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.helpers.GenericTextWatcher;
import com.matrixdeveloper.tajika.model.Login;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPInputActivity extends AppCompatActivity {

    private EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six;
    private TextView resendOtp, back;
    private PrefManager prf;
    private String TAG = "OtpInputAct";
    private String email, otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input);

        prf = new PrefManager(this);

        email = getIntent().getStringExtra("email");
        otp = getIntent().getStringExtra("otp");
        resendOtp = findViewById(R.id.txt_resendOtp);
        back = findViewById(R.id.txt_back);
        otp_textbox_one = findViewById(R.id.otp_edit_box1);
        otp_textbox_two = findViewById(R.id.otp_edit_box2);
        otp_textbox_three = findViewById(R.id.otp_edit_box3);
        otp_textbox_four = findViewById(R.id.otp_edit_box4);
        otp_textbox_five = findViewById(R.id.otp_edit_box5);
        otp_textbox_six = findViewById(R.id.otp_edit_box6);

        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six};

        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));
        otp_textbox_five.addTextChangedListener(new GenericTextWatcher(otp_textbox_five, edit));
        otp_textbox_six.addTextChangedListener(new GenericTextWatcher(otp_textbox_six, edit));

        back.setOnClickListener(view -> OTPInputActivity.super.onBackPressed());
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                try {
                    data.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ApiCall.postMethod(OTPInputActivity.this, ServiceNames.SEND_OTP, data, response -> {

                    Utils.log(TAG, response.toString());
                    Utils.toast(OTPInputActivity.this, response.optString("message"));

                    if (response.optInt("status") == 200){
                        JSONObject dataObj = response.optJSONObject("data");
                        assert dataObj != null;
                        otp = dataObj.optString("otp");
                    }

                });
            }
        });

    }

    public void onLoginCLick(View view) {
        String inputOne = otp_textbox_one.getText().toString(),
                inputTwo = otp_textbox_two.getText().toString(),
                inputThree = otp_textbox_three.getText().toString(),
                inputFour = otp_textbox_four.getText().toString(),
                inputFive = otp_textbox_five.getText().toString(),
                inputSix = otp_textbox_six.getText().toString();

        String mergePassword = inputOne + inputTwo + inputThree + inputFour + inputFive + inputSix;

        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
            data.put("otp", mergePassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.VALIDATE_OTP, data, response -> {

            Utils.log(TAG, "input data: " + data);
            Utils.toast(OTPInputActivity.this, response.optString("message"));

            try {

                Login login = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), Login.class);

                prf.setString(Global.user_id, login.getId().toString());
                prf.setString(Global.token, login.getToken());
                prf.setString(Global.role, login.getRoles().toString());
                prf.setString(Global.email, login.getEmail());
                prf.setString(Global.name, login.getName());

                startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}