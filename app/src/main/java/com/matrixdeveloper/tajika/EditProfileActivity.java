package com.matrixdeveloper.tajika;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView backPress, choosePhoto, profilePicture;
    private TextView txtUserName, selectGender;
    private EditText edtUserName, userMobileNumber, userEmail;
    private Button submit;
    private PrefManager pref;
    private final String TAG = "EditProfileAct";

    private String base64String = "";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pref = new PrefManager(this);

        initViews();
        setUserDetails();
        initListeners();

    }

    private void initViews() {
        backPress = findViewById(R.id.iv_backPress);
        choosePhoto = findViewById(R.id.iv_choosePhoto);
        profilePicture = findViewById(R.id.iv_profileImage);

        txtUserName = findViewById(R.id.txt_userName);
        selectGender = findViewById(R.id.txt_selectGender);

        edtUserName = findViewById(R.id.edt_userName);
        userMobileNumber = findViewById(R.id.edt_userMobile);
        userEmail = findViewById(R.id.edt_userMail);

        submit = findViewById(R.id.btn_submit);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {
        backPress.setOnClickListener(view -> EditProfileActivity.super.onBackPressed());
        submit.setOnClickListener(view -> processProfileUpdate());
        selectGender.setOnClickListener(view -> initiatePopupMenu());
        choosePhoto.setOnClickListener(view ->
                ImagePicker.Companion.with(EditProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start());
        profilePicture.setOnClickListener(view ->
                ImagePicker.Companion.with(EditProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();
            File file = new File(fileUri.getPath());

            byte[] fileContent = new byte[0];
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fileContent = Files.readAllBytes(file.toPath());
                    base64String = Base64.getEncoder().encodeToString(fileContent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            profilePicture.setImageURI(fileUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiatePopupMenu() {
        PopupMenu popup = new PopupMenu(EditProfileActivity.this, selectGender);
        popup.getMenuInflater().inflate(R.menu.gender_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            selectGender.setText(item.getTitle());
            return true;
        });
        popup.show();
    }

    private void processProfileUpdate() {
        String name = edtUserName.getText().toString().trim();
        String phoneNumber = userMobileNumber.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String gender = selectGender.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("id", pref.getString("id"));
            data.put("name", name);
            data.put("phone", phoneNumber);
            data.put("email", email);
            data.put("gender", gender);
            data.put("profileimage", base64String);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.UPDATE_PROFILE, data, response -> {
            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                Utils.toast(this, response.optString("message"));
                finish();
            } else {
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserDetails() {
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

                Glide.with(this).load(jsonObject.optString("profileimage")).placeholder(R.drawable.app_logo).into(profilePicture);

                txtUserName.setText(jsonObject.optString("name"));
                edtUserName.setText(jsonObject.optString("name"));
                userMobileNumber.setText(jsonObject.optString("phone"));
                userEmail.setText(jsonObject.optString("email"));
                selectGender.setText(jsonObject.optString("gender"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}