package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {

    private ImageView backPress, editProfile, profileImage;
    private EditText currentPassword, newPassword, confirmNewPassword;
    private Button submit;
    private TextView userName, userMobile, userMail, gender;
    private final String TAG = "MyProfileAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        pref = new PrefManager(this);

        intiViews();
        initListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUserDetails();
    }

    private void fetchUserDetails() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.GET_USER_PROFILE, data, response -> {
            Utils.log(TAG, response.toString());


            try {
                JSONObject jsonObject = response.getJSONObject("data");
                String profileUrl = jsonObject.optString("profileimage");
                Glide.with(this).load(jsonObject.optString("profileimage")).placeholder(R.drawable.app_logo).into(profileImage);

                userName.setText(jsonObject.optString("name"));
                userMobile.setText(jsonObject.optString("phone"));
                userMail.setText(jsonObject.optString("email"));
                gender.setText(jsonObject.optString("gender"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }

    private void intiViews() {
        backPress = findViewById(R.id.iv_backPress);
        profileImage = findViewById(R.id.iv_userImage);
        editProfile = findViewById(R.id.iv_editProfile);

        currentPassword = findViewById(R.id.edt_currentPassword);
        newPassword = findViewById(R.id.edt_newPassword);
        confirmNewPassword = findViewById(R.id.edt_confirmNewPassword);

        userName = findViewById(R.id.txt_userName);
        userMobile = findViewById(R.id.txt_userNumber);
        userMail = findViewById(R.id.txt_userMail);
        gender = findViewById(R.id.txt_userGender);

        submit = findViewById(R.id.btn_submit);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> MyProfileActivity.super.onBackPressed());
        editProfile.setOnClickListener(view -> startActivity(new Intent(this, EditProfileActivity.class)));
        submit.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {
        String currentPass = currentPassword.getText().toString().trim();
        String newPass = newPassword.getText().toString().trim();
        String confirmNewPass = confirmNewPassword.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("current_password", currentPass);
            data.put("password", newPass);
            data.put("password_confirmation", confirmNewPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.CHANGE_PASSWORD, data, response -> {

            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                Utils.toast(this, response.optString("message"));
                finish();
            } else {
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_LONG).show();
            }

        });

    }

}