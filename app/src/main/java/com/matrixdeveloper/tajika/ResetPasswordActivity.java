package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {
    private RadioGroup rGroup;
    private LinearLayout onMobile, onEmail;
    private TextView backToLogin;
    private EditText regMobile, regEmail;
    private Button next;
    private final String TAG = "ResetPasswordAct";
    private PrefManager pref;
    private String user_type, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        pref = new PrefManager(this);
        user_type = getIntent().getStringExtra("user_type");
        rGroup = findViewById(R.id.rg_resetPassword);
        onEmail = findViewById(R.id.ll_onEmail);
        onMobile = findViewById(R.id.ll_onNumber);
        backToLogin = findViewById(R.id.tv_backToLogin);
        regEmail = findViewById(R.id.edt_regEmail);
        regMobile = findViewById(R.id.edt_regNumber);
        next = findViewById(R.id.btn_next);

        rGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton1 = group.findViewById(checkedId);
            if (checkedRadioButton1.getText().equals("On Mobile")) {
                onMobile.setVisibility(View.VISIBLE);
                onEmail.setVisibility(View.GONE);
            } else {
                onMobile.setVisibility(View.GONE);
                onEmail.setVisibility(View.VISIBLE);
            }
        });

        backToLogin.setOnClickListener(view -> ResetPasswordActivity.super.onBackPressed());

        next.setOnClickListener(view -> {
            String regMail = regEmail.getText().toString().trim();
            String regNumber = regMobile.getText().toString().trim();

            JSONObject data = new JSONObject();
            try {
                if (regNumber.equals("") && !regMail.equals("")) {
                    mail = regMail;
                } else if (regMail.equals("") && regNumber.length() == 10) {
                    mail = regNumber;
                }
                data.put("email", mail);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiCall.postMethod(this, ServiceNames.SEND_OTP, data, response -> {

                Utils.log(TAG, response.toString());
                Utils.toast(ResetPasswordActivity.this, response.optString("message"));

                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    //pref.setString("otp", jsonObject.optString("otp"));
                    startActivity(new Intent(ResetPasswordActivity.this, OTPInputActivity.class)
                            .putExtra("user_type", user_type)
                            .putExtra("email", mail)
                            .putExtra("type", "reset"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        });

    }
}