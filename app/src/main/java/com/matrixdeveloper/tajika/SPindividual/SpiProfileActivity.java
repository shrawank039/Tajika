package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiProfileActivity extends AppCompatActivity {

    private TextView profileEdit, txtRating, providerName, providerNumber, providerEmail, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView providerEducation, passportNumber, passportCopyStatus, proQualification, proQualificationStatus;
    private TextView subscriptionPlan, purchasedOn, expiresOn;
    private ImageView backPress, providerImage;
    private RatingBar providerRating;
    private final String TAG = "SpiProfileAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile);

        initViews();

        backPress.setOnClickListener(view -> SpiProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpiProfileActivity.this, SpiProfileEditActivity.class)));

        getProfile();
    }

    private void initViews() {
        pref = new PrefManager(this);
        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress = findViewById(R.id.iv_backPress);
        providerImage = findViewById(R.id.iv_providerImage);
        providerRating = findViewById(R.id.rating_providerRating);
        txtRating = findViewById(R.id.txt_ratings);
        providerName = findViewById(R.id.edt_providerName);
        providerEmail = findViewById(R.id.txt_providerEmail);
        providerNumber = findViewById(R.id.edt_providerPhone);
        businessName = findViewById(R.id.edt_businessNameTop);
        businessLink = findViewById(R.id.txt_businessLink);
        businessCategory = findViewById(R.id.txt_businessCategory);
        providerExperience = findViewById(R.id.txt_providerExperience);
        businessDesc = findViewById(R.id.txt_businessDesc);

        providerEducation = findViewById(R.id.txt_provideEducation);
        passportNumber = findViewById(R.id.txt_passportNumber);
        passportCopyStatus = findViewById(R.id.txt_passportCopyStatus);
        proQualification = findViewById(R.id.txt_proQualification);
        proQualificationStatus = findViewById(R.id.txt_proQualificationStatus);
        subscriptionPlan = findViewById(R.id.txt_subPlan);
        purchasedOn = findViewById(R.id.txt_purchasedOn);
        expiresOn = findViewById(R.id.txt_expiresOn);

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
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    providerRating.setRating(jsonObject.optInt("rating"));
                    txtRating.setText(jsonObject.optInt("rating") + " star ratings");
                    providerName.setText(jsonObject.optString("name"));
                    providerNumber.setText(jsonObject.optString("phone"));
                    providerEmail.setText(jsonObject.optString("email"));
                    businessCategory.setText(jsonObject.optString("business_categories"));
                    providerExperience.setText(jsonObject.optString("year_of_experience") + " Yrs");
                    businessLink.setText(jsonObject.optString("bussiness_link"));
                    businessDesc.setText(jsonObject.optString("service_description"));
                    providerEducation.setText(jsonObject.optString("education_level"));
                    passportNumber.setText(jsonObject.optString("passportnumber"));
                    passportCopyStatus.setText(jsonObject.optString("upload_passportid"));
                    proQualification.setText(jsonObject.optString("professional_qualification"));
                    proQualificationStatus.setText(jsonObject.optString("qualification_certification"));
                    subscriptionPlan.setText(jsonObject.optString("plan_id"));
                    purchasedOn.setText(jsonObject.optString("start_date"));
                    expiresOn.setText(jsonObject.optString("end_date"));


                    //Not in response
                    businessName.setText(jsonObject.optString("business_categories"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }
}