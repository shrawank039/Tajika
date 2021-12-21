package com.matrixdeveloper.tajika.SPindividual;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpbEditProfileActivity;
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

public class SpiProfileEditActivity extends AppCompatActivity {

    private ImageView backPress, providerImage;
    private EditText minCharge, providerName, providerNumber, providerEmail, businessNameTop, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView providerEducation, passportNumber, passportCopyStatus, proQualification, proQualificationStatus;
    private Button updateProfile;
    private PrefManager pref;
    private final String TAG = "SpiProfileEditAct";
    private RelativeLayout profileContainer;
    private ImageView profileImage;
    private String base64String="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile_edit);

        initViews();
        initListeners();
        setProviderData();

    }

    private void initListeners() {
        backPress.setOnClickListener(view -> SpiProfileEditActivity.super.onBackPressed());
        updateProfile.setOnClickListener(view -> initiateProfileUpdate());

        profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SpiProfileEditActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

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

            profileImage.setImageURI(fileUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void setProviderData() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_PROVIDER_PROFILE_IND, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    providerName.setText(jsonObject.optString("name"));
                    providerNumber.setText(jsonObject.optString("phone"));
                    providerEmail.setText(jsonObject.optString("email"));
                    businessCategory.setText(jsonObject.optString("business_categories"));
                    providerExperience.setText(jsonObject.optString("year_of_experience"));
                    minCharge.setText(jsonObject.optString("minimum_charge"));
                    businessLink.setText(jsonObject.optString("bussiness_link"));
                    businessDesc.setText(jsonObject.optString("service_description"));

                    providerEducation.setText(jsonObject.optString("education_level"));
                    passportNumber.setText(jsonObject.optString("passportnumber"));
                    passportCopyStatus.setText(jsonObject.optString("upload_passportid"));
                    proQualification.setText(jsonObject.optString("professional_qualification"));
                    proQualificationStatus.setText(jsonObject.optString("qualification_certification"));

                    providerEducation.setText(jsonObject.optString("education_level"));
                    passportNumber.setText(jsonObject.optString("passportnumber"));
                    passportCopyStatus.setText(jsonObject.optString("upload_passportid"));
                    proQualification.setText(jsonObject.optString("professional_qualification"));
                    proQualificationStatus.setText(jsonObject.optString("qualification_certification"));

                    //Not in response
                    businessName.setText(jsonObject.optString("service_area"));
                    businessNameTop.setText(jsonObject.optString("business_categories"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    private void initViews() {
        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        updateProfile = findViewById(R.id.btn_update);

        providerImage = findViewById(R.id.iv_providerImage);
        providerName = findViewById(R.id.edt_providerName);
        providerEmail = findViewById(R.id.edt_providerEmail);
        providerNumber = findViewById(R.id.edt_providerPhone);
        businessNameTop = findViewById(R.id.edt_businessNameTop);
        businessName = findViewById(R.id.edt_businessName);
        businessLink = findViewById(R.id.edt_businessLink);
        businessCategory = findViewById(R.id.edt_businessCategory);
        providerExperience = findViewById(R.id.edt_providerExperience);
        minCharge = findViewById(R.id.edt_serviceMinCharge);
        businessDesc = findViewById(R.id.edt_businessDesc);

        providerEducation = findViewById(R.id.txt_provideEducation);
        passportNumber = findViewById(R.id.txt_passportNumber);
        passportCopyStatus = findViewById(R.id.txt_passportCopyStatus);
        proQualification = findViewById(R.id.txt_proQualification);
        proQualificationStatus = findViewById(R.id.txt_proQualificationStatus);

        profileContainer = findViewById(R.id.rl_profileContainer);
        profileImage = findViewById(R.id.iv_profileImage);
    }

    private void initiateProfileUpdate() {
        String name = providerName.getText().toString().trim();
        String phoneNumber = providerNumber.getText().toString().trim();
        String email = providerEmail.getText().toString().trim();
        String business_categories = businessCategory.getText().toString().trim();
        String business_name = businessCategory.getText().toString().trim();
        String year_of_experience = providerExperience.getText().toString().trim();
        String bussiness_link = businessLink.getText().toString().trim();
        String service_description = businessDesc.getText().toString().trim();
        String minimum_charge = minCharge.getText().toString().trim();
        String education_level = providerEducation.getText().toString().trim();
        String passportnumber = passportNumber.getText().toString().trim();
        String upload_passportid = "0";
        String professional_qualification = proQualification.getText().toString().trim();
        String qualification_certification = "0";

        JSONObject data = new JSONObject();
        try {
            data.put("id", pref.getString("id"));
            data.put("name", name);
            data.put("phone", phoneNumber);
            data.put("email", email);
            data.put("service_area", business_name);
            data.put("business_categories", business_categories);
            data.put("service_description", service_description);
            data.put("year_of_experience", year_of_experience);
            data.put("bussiness_link", bussiness_link);
            data.put("minimum_charge", minimum_charge);
            data.put("education_level", education_level);
            data.put("passportnumber", passportnumber);
            data.put("upload_passportid", upload_passportid);
            data.put("professional_qualification", professional_qualification);
            data.put("qualification_certification", qualification_certification);
            data.put("profileimage", base64String);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.UPDATE_PROVIDER_PROFILE_IND, data, response -> {
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