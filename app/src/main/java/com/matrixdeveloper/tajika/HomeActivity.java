package com.matrixdeveloper.tajika;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.matrixdeveloper.tajika.adapter.ServiceAdapter;
import com.matrixdeveloper.tajika.model.ServiceList;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.RecyclerTouchListener;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;

    private RecyclerView recyclerView;
    private ServiceAdapter mAdapter;
    private PrefManager prf;
    private String TAG = "HomeAct";
    SliderLayout homeSlider;
    private List<ServiceList> serviceLists;
    private NavigationView navigationView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayout coinsWallet, notificationList, referFriends, llSearch;
    private TextView viewAllService, greeting;
    private CardView chatting;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        handleClickListener();

        greeting.setText("Good Morning " + prf.getString("name") + ", What can we help you with?");

        serviceLists = new ArrayList<>();
        mAdapter = new ServiceAdapter(HomeActivity.this, serviceLists, 0);

        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ServiceList serviceList = serviceLists.get(position);

                startActivity(new Intent(getApplicationContext(), LocationSelectorActivity.class)
                        .putExtra("service_name", serviceList.getServiceName())
                        .putExtra("service_id", String.valueOf(serviceList.getId())));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // for responsive screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = getResources().getDisplayMetrics().density;
        //  float height =  displayMetrics.heightPixels/density;
        float width = displayMetrics.widthPixels / density;
        int a = (int) (width * density / 3.189);  // 1920*602

        prf.setInt("banner_height", a + 20);

        // for responsive banner size
        LinearLayout llbanner = findViewById(R.id.ll_banner);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, prf.getInt("banner_height"));
        llbanner.setLayoutParams(parms);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        updateToken(token);

                        Log.d(TAG, "fcm token : " + token);

                    }
                });

        getServiceList();
        getBannerImage();

    }

    private void initViews() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        homeSlider = findViewById(R.id.slider);
        prf = new PrefManager(this);
        notificationList = findViewById(R.id.ll_notificationList);
        coinsWallet = findViewById(R.id.ll_coinsWallet);
        referFriends = findViewById(R.id.ll_referFriends);
        viewAllService = findViewById(R.id.txt_viewAllService);
        recyclerView = findViewById(R.id.recyclerView);
        llSearch = findViewById(R.id.ll_search);
        chatting = findViewById(R.id.cv_conversation);
        greeting = findViewById(R.id.txt_homeGreeting);
    }

    private void updateToken(String token) {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
            data.put("fcm_token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.SAVE_FCM_TOKEN, data, response -> {

            Utils.log(TAG, "SAVE TOKEN RESPONSE : " + response.toString());


        });
    }

    private void handleClickListener() {

        notificationList.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        });
        coinsWallet.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CoinsWalletActivity.class)));
        referFriends.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ReferralActivity.class)));
        viewAllService.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AllServiceActivity.class)));
        chatting.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ConversationListActivity.class)));

        llSearch.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        });


    }

    private void getBannerImage() {

        ApiCall.getMethod(this, ServiceNames.BANNER, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonArray = null;
            try {

                jsonArray = response.getJSONArray("data");
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.centerCrop();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject c = null;
                    try {
                        c = jsonArray.getJSONObject(i);
                        Log.d("TAG", "image : " + c.optString("url"));
                        TextSliderView sliderView = new TextSliderView(this);
                        sliderView
                                .image(c.optString("url"))
                                .setRequestOption(requestOptions)
                                .setProgressBarVisible(true)
                                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {

                                    }
                                });

                        //add your extra information
                        sliderView.bundle(new Bundle());
                        sliderView.getBundle().putString("extra", c.optString("link"));
                        homeSlider.addSlider(sliderView);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }

    private void getServiceList() {

        ApiCall.getMethod(this, ServiceNames.SERVICE_LIST, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonarray = null;
            try {

                jsonarray = response.getJSONArray("data");

                for (int i = 0; i < jsonarray.length(); i++) {

                    try {

                        ServiceList serviceList = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), ServiceList.class);

                        serviceLists.add(serviceList);

                        mAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawer(Gravity.LEFT);
                return true;
            case R.id.nav_my_profile:
                startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                return true;
            case R.id.nav_offers:
                startActivity(new Intent(getApplicationContext(), VouchersActivity.class));
                return true;
            case R.id.nav_my_bookings:
                startActivity(new Intent(getApplicationContext(), BookServiceActivity.class));
                return true;
            case R.id.nav_notification:
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                return true;
            case R.id.nav_help:
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                return true;
            case R.id.nav_privacy_policy:
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
                return true;
            case R.id.nav_about_us:
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                return true;
            case R.id.nav_refer_friends:
                startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
                return true;
            case R.id.nav_rate_app:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return true;
            case R.id.nav_logout:
                prf.setString("id", "");
                Intent intent = new Intent(this, LandingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onNavDrawerClick(View view) {
        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isOpen()) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}