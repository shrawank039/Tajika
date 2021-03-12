package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matrixdeveloper.tajika.model.Register;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.network.VolleyCallback;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtEmail, edtPass, edtCPass;
    String name, phone, email, pass;
    private String TAG = "RegisterAct";
    private static PrefManager prf;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prf = new PrefManager(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_pass);
        edtCPass = findViewById(R.id.edt_cpass);

    }

    public void onSubmitClick(View view) {
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("name",name);
            data.put("phone",phone);
            data.put("email",email);
            data.put("password",pass);
            data.put("password_confirmation",pass);
            data.put("role","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getApplicationContext(), ServiceNames.USER_REGISTRATION, data, response -> {

            Utils.log(TAG, response.toString());

            try {

                Register register= gson.fromJson(response.getJSONObject("data").toString(), Register.class);

                prf.setString(Global.user_id, register.getId().toString());
                prf.setString(Global.token, register.getToken());
                prf.setString(Global.role, register.getRoles().toString());
                prf.setString(Global.email, register.getEmail());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }
}