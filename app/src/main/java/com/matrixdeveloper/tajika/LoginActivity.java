package com.matrixdeveloper.tajika;

import static com.matrixdeveloper.tajika.network.ServiceNames.PRODUCTION_API;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.model.Login;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    private final String TAG = "LoginAct";
    private PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prf = new PrefManager(this);

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
        ).putExtra("user_type", "user"));
    }

    public void onLoginCLick(View view) {

        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
            data.put("password", pass);
            data.put("role", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.LOGIN, data, response -> {

            Utils.log(TAG, response.toString());
            Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

            try {

                Login login = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), Login.class);

                prf.setString(Global.user_id, login.getId().toString());
                prf.setString(Global.token, login.getToken());
                prf.setString(Global.role, login.getRoles().toString());
                prf.setString(Global.email, login.getEmail());
                prf.setString(Global.phone, login.getPhoneNumber());
                prf.setString(Global.name, login.getName());
                prf.setString(Global.rating, login.getRating());
                prf.setString(Global.profileImage, PRODUCTION_API+login.getImage());

                startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public void onForgotPasswordClick(View view) {
        startActivity(new Intent(
                this, ResetPasswordActivity.class
        ).putExtra("user_type", "user"));
    }

    public void onHideClick(View view) {
        if (view.getId() == R.id.eye) {
            if (edtPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Passsword
                edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}