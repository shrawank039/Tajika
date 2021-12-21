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
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpbProfileActivity extends AppCompatActivity {
    private ImageView backPress, providerImage;
    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;
    private final String TAG = "SpbProfileAct";
    private PrefManager pref;
    private TextView profileEdit, txtRating, providerName, providerNumber, providerEmail, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView subscriptionPlan, purchasedOn, expiresOn;
    private RatingBar providerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_profile);

        pref = new PrefManager(this);
        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress = findViewById(R.id.iv_backPress);
        providerImage = findViewById(R.id.iv_providerImage);
        providerRating = findViewById(R.id.rating_providerRating);
        txtRating = findViewById(R.id.txt_ratings);
        providerName = findViewById(R.id.edt_providerName);
        providerEmail = findViewById(R.id.txt_providerEmail);
        providerNumber = findViewById(R.id.edt_providerPhone);
        businessName = findViewById(R.id.txt_businessNameTop);
        businessLink = findViewById(R.id.txt_businessLink);
        businessCategory = findViewById(R.id.txt_businessCategory);
        providerExperience = findViewById(R.id.txt_providerExperience);
        businessDesc = findViewById(R.id.txt_businessDesc);

        subscriptionPlan = findViewById(R.id.txt_subPlan);
        purchasedOn = findViewById(R.id.txt_purchasedOn);
        expiresOn = findViewById(R.id.txt_expiresOn);
        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);

        backPress.setOnClickListener(view -> SpbProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpbProfileActivity.this, SpbEditProfileActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {

        List<SPBbusinessPhotosVideosModel> imageList=new ArrayList<>();
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
                    businessCategory.setText(jsonObject.optString("business_categories"));
                    providerExperience.setText(jsonObject.optString("year_of_experience"));
                    businessLink.setText(jsonObject.optString("bussiness_link"));
                    businessDesc.setText(jsonObject.optString("service_description"));
                    subscriptionPlan.setText(jsonObject.optString("plan_id"));
                    purchasedOn.setText(jsonObject.optString("start_date"));
                    expiresOn.setText(jsonObject.optString("end_date"));

                    Glide.with(this).load(jsonObject.optString("profileimage")).placeholder(R.drawable.app_logo).into(providerImage);

                    //Not in response
                    businessName.setText(jsonObject.optString("business_categories"));

                    JSONArray jsonArray=jsonObject.optJSONArray("service_offerd_image");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        imageList.add(new SPBbusinessPhotosVideosModel(jsonObject1.optString("id"),ServiceNames.PRODUCTION_API+jsonObject1.optString("link")));
                    }

                    mAdapter = new SPBbusinessPhotosVideoAdapter(this, imageList,"profile");
                    recViewPhotosVideos.setHasFixedSize(true);
                    recViewPhotosVideos.setLayoutManager(new LinearLayoutManager(
                            SpbProfileActivity.this,
                            LinearLayoutManager.HORIZONTAL,
                            false));
                    recViewPhotosVideos.setAdapter(mAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }
}