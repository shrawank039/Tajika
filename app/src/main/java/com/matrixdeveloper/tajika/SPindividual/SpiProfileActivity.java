package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiProfileActivity extends AppCompatActivity {

    private TextView profileEdit;
    private ImageView backPress, editProfile, profileImage;
    private EditText currentPassword, newPassword, confirmNewPassword;
    private Button submit;
    private TextView userName, userMobile, userMail, gender;
    private final String TAG = "SpiProfileAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile);

        pref = new PrefManager(this);
        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress=findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> SpiProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpiProfileActivity.this, SpiProfileEditActivity.class)));

        getProfile();
    }

    private void getProfile() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.GET_PROVIDER_PROFILE_IND, data, response -> {
            Utils.log(TAG, response.toString());


        });

    }
}