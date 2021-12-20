package com.matrixdeveloper.tajika;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.SPindividual.SpiHomeActivity;
import com.matrixdeveloper.tajika.SPindividual.SpiLoginActivity;
import com.matrixdeveloper.tajika.model.Login;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SetPasswordActivity extends AppCompatActivity {

    private ImageView backPress;
    private EditText newPassword, confirmNewPassword;
    private Button submit;
    private final String TAG = "MyProfileAct";
    private PrefManager pref;
    private String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        pref = new PrefManager(this);

        intiViews();
        initListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void intiViews() {

        user_type = getIntent().getStringExtra("user_type");
        backPress = findViewById(R.id.iv_backPress);

        newPassword = findViewById(R.id.edt_newPassword);
        confirmNewPassword = findViewById(R.id.edt_confirmNewPassword);

        submit = findViewById(R.id.btn_submit);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> super.onBackPressed());
        submit.setOnClickListener(view -> setPassword());
    }

    private void setPassword() {
        String newPass = newPassword.getText().toString().trim();
        String confirmNewPass = confirmNewPassword.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("password", newPass);
            data.put("password_confirmation", confirmNewPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SET_PASSWORD, data, response -> {

            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                Utils.toast(this, response.optString("message"));

                        if (user_type.equals("business") || user_type.equals("individual")){
                            startActivity(new Intent(getApplicationContext(), SpiLoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }else {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }

                    finish();

            } else {
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_LONG).show();
            }

        });
    }
}