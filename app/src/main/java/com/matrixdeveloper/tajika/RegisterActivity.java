package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.model.Register;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtEmail, edtPass, edtCPass;
    String name, phone, email, pass, Cpass;
    private String TAG = "RegisterAct";
    private static PrefManager prf;
    private CheckBox termsAndPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prf = new PrefManager(this);

        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        edtCPass = findViewById(R.id.edt_cpass);
        termsAndPolicy = findViewById(R.id.checkboxTermsandPolicy);

    }

    public void onSubmitClick(View view) {
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        Cpass = edtCPass.getText().toString();
        if (pass.equals(Cpass)) {
            if (termsAndPolicy.isChecked()) {
                JSONObject data = new JSONObject();
                try {
                    data.put("name", name);
                    data.put("phone", phone);
                    data.put("email", email);
                    data.put("password", pass);
                    data.put("password_confirmation", pass);
                    data.put("role", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ApiCall.postMethod(this, ServiceNames.USER_REGISTRATION, data, response -> {

                    Utils.log(TAG, response.toString());
                    Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

                    try {

                        Register register = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), Register.class);

                        prf.setString(Global.user_id, register.getId().toString());
                        prf.setString(Global.token, register.getToken());
                        prf.setString(Global.role, register.getRoles().toString());
                        prf.setString(Global.email, register.getEmail());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                });
            } else {
                Toast.makeText(this, "Please Accept the Terms and Policy of Tajika", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Password did't match", Toast.LENGTH_SHORT).show();
        }

    }

    public void onPasswordClick(View view) {
        if (view.getId() == R.id.showPass) {

            if (edtPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Password
                edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        } else if (view.getId() == R.id.showCPass) {

            if (edtCPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                edtCPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Password
                edtCPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}