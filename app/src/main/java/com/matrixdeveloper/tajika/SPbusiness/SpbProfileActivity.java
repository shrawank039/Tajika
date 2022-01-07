package com.matrixdeveloper.tajika.SPbusiness;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.PaymentWebViewActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.adapter.SubscriptionAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.model.SubscriptionModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;
import com.matrixdeveloper.tajika.widget.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpbProfileActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {
    private ImageView backPress, providerImage;
    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;
    private final String TAG = "SpbProfileAct";
    private PrefManager pref;
    private TextView profileEdit, txtRating, providerName, providerNumber, providerEmail, businessName, businessCategory, providerExperience, businessLink, businessDesc;
    private TextView subscriptionPlan, purchasedOn, expiresOn;
    private RatingBar providerRating;
    private Button btnRenew;
    private RecyclerView recSubscription;
    private final int PAYMENT_REQUEST = 1;
    String planID;

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
        btnRenew = findViewById(R.id.btn_renew);
        recSubscription = findViewById(R.id.rv_subscription);

        btnRenew.setOnClickListener(v -> {
            getSubscriptionDetails();
        });
        backPress.setOnClickListener(view -> SpbProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpbProfileActivity.this, SpbEditProfileActivity.class)));

        checkSubscription("expired");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
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

    public void showSubscriptionAlert(String id, String amount, String title) {
        planID = id;
        final Dialog dialog = new Dialog(this);
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

    private void checkSubscription(String subscriptionStatus) {
        if (subscriptionStatus.equals("subscribed")) {
            recSubscription.setVisibility(View.GONE);
        } else if (subscriptionStatus.equals("expired")) {
            getSubscriptionDetails();
        }
    }

    private void showSubscriptionSuccessAlert() {
        recSubscription.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_payment_successful);

        Button close = dialog.findViewById(R.id.btn_close);
        ImageView dialogCancel = dialog.findViewById(R.id.iv_dialogCancel);

        dialogCancel.setOnClickListener(v -> dialog.dismiss());
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }

    private void getProfile() {

        List<SPBbusinessPhotosVideosModel> imageList = new ArrayList<>();
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
                    subscriptionPlan.setText(jsonObject.optString("plan_name"));
                    purchasedOn.setText(jsonObject.optString("start_date"));
                    expiresOn.setText(jsonObject.optString("end_date"));

                    Glide.with(this).load(jsonObject.optString("profileimage")).placeholder(R.drawable.app_logo).into(providerImage);

                    //Not in response
                    businessName.setText(jsonObject.optString("business_name"));

                    JSONArray jsonArray = jsonObject.optJSONArray("service_offerd_image");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        imageList.add(new SPBbusinessPhotosVideosModel(jsonObject1.optString("id"), ServiceNames.PRODUCTION_API + jsonObject1.optString("link")));
                    }

                    mAdapter = new SPBbusinessPhotosVideoAdapter(this, imageList, "profile");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT_REQUEST) {
            if (resultCode == 1) {
                addSubscription();
            } else {
                addSubscription();
                Utils.toast(getApplicationContext(), "Payment Failed!!!");
            }
        }

    }

    @Override
    public void onButtonClicked(String text) {

    }
}