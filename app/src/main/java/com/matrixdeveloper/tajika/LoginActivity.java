package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matrixdeveloper.tajika.model.Login;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.network.VolleyCallback;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    private String TAG = "LoginAct";
    private static PrefManager prf;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prf = new PrefManager(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
    }

    public void onSignUPClick(View view) {
        startActivity(new Intent(
                this, RegisterActivity.class
        ));
    }

    public void onLoginThroughOTPCLick(View view) {
        startActivity(new Intent(
                this, LoginThroughOTPActivity.class
        ));
    }

    public void onLoginCLick(View view) {

        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("email",email);
            data.put("password",pass);
            data.put("role","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getApplicationContext(), ServiceNames.LOGIN, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                Login login= gson.fromJson(response.getJSONObject("data").toString(), Login.class);

                prf.setString(Global.user_id, login.getId().toString());
                prf.setString(Global.token, login.getToken());
                prf.setString(Global.role, login.getRoles().toString());
                prf.setString(Global.email, login.getEmail());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    public void onForgotPasswordClick(View view) {
        startActivity(new Intent(
                this, ResetPasswordActivity.class
        ));
    }
}