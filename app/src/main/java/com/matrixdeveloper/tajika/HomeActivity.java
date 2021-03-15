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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matrixdeveloper.tajika.adapter.ServiceAdapter;
import com.matrixdeveloper.tajika.model.ServiceList;
import com.matrixdeveloper.tajika.network.ApiCall;
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
    private static PrefManager prf;
    private String TAG = "HomeAct";
    SliderLayout homeSlider;
    private List<ServiceList> serviceLists;
    private Gson gson;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        homeSlider = findViewById(R.id.slider);
        prf = new PrefManager(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        serviceLists = new ArrayList<>();
        mAdapter = new ServiceAdapter(HomeActivity.this, serviceLists);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ServiceList serviceList = serviceLists.get(position);

                startActivity(new Intent(getApplicationContext(), LocationSelectorActivity.class));
                Toast.makeText(HomeActivity.this, serviceList.getServiceName(), Toast.LENGTH_SHORT).show();

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


        getServiceList();
        getBannerImage();
    }

    private void getBannerImage() {

        ApiCall.getMethod(getApplicationContext(), ServiceNames.SERVICE_LIST, response -> {

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
                        Log.d("TAG", "image : " + c.optString("image"));
                        TextSliderView sliderView = new TextSliderView(this);
                        sliderView
                                .image(c.optString("image"))
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

        ApiCall.getMethod(getApplicationContext(), ServiceNames.SERVICE_LIST, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonarray = null;
            try {

                jsonarray = response.getJSONArray("data");

                for (int i = 0; i < jsonarray.length(); i++) {

                    try {

                        ServiceList serviceList = gson.fromJson(jsonarray.getJSONObject(i).toString(), ServiceList.class);

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
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                return true;
            case R.id.nav_my_bookings:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), BookServiceActivity.class));
                return true;
            case R.id.nav_notification:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                return true;
            case R.id.nav_offers:
                drawer.closeDrawer(Gravity.LEFT);
                Toast.makeText(this, "MyOffer", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nav_help:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                return true;
            case R.id.nav_privacy_policy:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
                return true;
            case R.id.nav_about_us:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                return true;
            case R.id.nav_refer_friends:
                drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
                return true;
            case R.id.nav_rate_app:
                drawer.closeDrawer(Gravity.LEFT);
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return true;
            case R.id.nav_logout:
                drawer.closeDrawer(Gravity.LEFT);
                prf.setString("id","");
                Intent intent = new Intent(this, LoginActivity.class);
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
}