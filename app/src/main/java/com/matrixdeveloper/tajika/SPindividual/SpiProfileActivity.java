package com.matrixdeveloper.tajika.SPindividual;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.PaymentWebViewActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SubscriptionAdapter;
import com.matrixdeveloper.tajika.model.SubscriptionModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpiProfileActivity extends AppCompatActivity {

    private TextView profileEdit, txtRating, providerName, providerNumber, providerEmail, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView providerEducation, passportNumber, passportCopyStatus, proQualification, proQualificationStatus;
    private TextView subscriptionPlan, purchasedOn, expiresOn;
    private ImageView backPress, providerImage;
    private RatingBar providerRating;
    private Button btnRenew;
    private final String TAG = "SpiProfileAct";
    private PrefManager pref;
    private final int PAYMENT_REQUEST = 1;
    String planID;
    private RecyclerView recSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_profile);

        initViews();

        backPress.setOnClickListener(view -> SpiProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpiProfileActivity.this, SpiProfileEditActivity.class)));

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        businessName = findViewById(R.id.txt_businessName);
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
        btnRenew = findViewById(R.id.btn_renew);
        recSubscription = findViewById(R.id.rv_subscription);

        btnRenew.setOnClickListener(v -> {
            getSubscriptionDetails();
        });

        checkSubscription("expired");

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
                    businessName.setText(jsonObject.optString("business_name"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public void showSubscriptionAlert(String id, String amount, String title) {
        planID = id;
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_notice);

        Button makePayment = dialog.findViewById(R.id.btn_makePayment);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        makePayment.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PaymentWebViewActivity.class);
            intent.putExtra("amount",amount);
            intent.putExtra("order_desc",title);
            startActivityForResult(intent, PAYMENT_REQUEST);
            dialog.dismiss();
        });
        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    public void addSubscription() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("plan_id", planID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.ADD_SUBSCRIPTION, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    showSubscriptionSuccessAlert();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    private void getSubscriptionDetails() {
        ApiCall.getMethod(this, ServiceNames.SUBSCRIPTION_PLAN, response -> {
            Utils.log(TAG, response.toString());
            JSONArray jsonarray = null;
            try {
                jsonarray = response.getJSONArray("data");
                List<SubscriptionModel> subModel = new ArrayList<>();

                for (int i = 0; i < jsonarray.length(); i++) {
                    SubscriptionModel subscriptionModel = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), SubscriptionModel.class);
                    subModel.add(subscriptionModel);

                }
                SubscriptionAdapter subAdapter = new SubscriptionAdapter(this, subModel);
                recSubscription.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
                gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recSubscription.setLayoutManager(gridLayoutManager);
                recSubscription.setItemAnimator(new DefaultItemAnimator());
                recSubscription.setAdapter(subAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void checkSubscription(String subscriptionStatus) {
        if (subscriptionStatus.equals("subscribed")) {
            recSubscription.setVisibility(View.GONE);
        } else if (subscriptionStatus.equals("expired")) {
            getSubscriptionDetails();
        }
    }

    private void showSubscriptionSuccessAlert() {
        recSubscription.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_successful);

        Button close = dialog.findViewById(R.id.btn_close);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT_REQUEST) {
            if (resultCode == 1) {
                addSubscription();
            } else {
                Utils.toast(getApplicationContext(), "Payment Failed!!!");
            }
        }

    }
}