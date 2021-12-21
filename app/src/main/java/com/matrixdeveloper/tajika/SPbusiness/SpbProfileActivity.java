package com.matrixdeveloper.tajika.SPbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiProfileEditActivity;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpbProfileActivity extends AppCompatActivity {
    private ImageView backPress, providerImage;
    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;
    private final String TAG = "SpbProfileAct";
    private PrefManager pref;
    private TextView profileEdit, txtRating, providerName, providerNumber, providerEmail, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView providerEducation, passportNumber, passportCopyStatus, proQualification, proQualificationStatus;
    private TextView subscriptionPlan, purchasedOn, expiresOn;
    private RatingBar providerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_profile);

        pref = new PrefManager(this);
        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress = findViewById(R.id.iv_backPress);
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
        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);
        backPress.setOnClickListener(view -> SpbProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpbProfileActivity.this, SpbEditProfileActivity.class)));

        SPBbusinessPhotosVideosModel[] pvModel = new SPBbusinessPhotosVideosModel[]{
                new SPBbusinessPhotosVideosModel(1, R.drawable.provider_image_1x),
                new SPBbusinessPhotosVideosModel(2, R.drawable.giving_high_five_2x),
                new SPBbusinessPhotosVideosModel(3, R.drawable.plumbing),
                new SPBbusinessPhotosVideosModel(4, R.drawable.catering),
                new SPBbusinessPhotosVideosModel(5, R.drawable.badge_check_2x),
                new SPBbusinessPhotosVideosModel(5, R.drawable.business_leader_2x),
                new SPBbusinessPhotosVideosModel(5, R.drawable.businessman_2x),
        };

        mAdapter = new SPBbusinessPhotosVideoAdapter(this, pvModel);
        recViewPhotosVideos.setHasFixedSize(true);
        recViewPhotosVideos.setLayoutManager(new LinearLayoutManager(
                SpbProfileActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        recViewPhotosVideos.setAdapter(mAdapter);

        getProfile();
    }

    private void getProfile() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.GET_PROVIDER_PROFILE_BUSI, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    providerRating.setRating(jsonObject.optInt("rating"));
                    txtRating.setText(jsonObject.optInt("rating") + " star ratings");
                    providerName.setText(jsonObject.optString("name"));
                    providerNumber.setText(jsonObject.optString("phone"));
                    providerEmail.setText(jsonObject.optString("email"));
                    String profileUrl = jsonObject.optString("profileimage");
                    if (!profileUrl.equals("null") && !profileUrl.equals("")) {
                        Glide.with(this).load(profileUrl).into(providerImage);
                    }
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