package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.matrixdeveloper.tajika.ConversationListActivity;
import com.matrixdeveloper.tajika.HomeActivity;
import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.NewRequestAdapter;
import com.matrixdeveloper.tajika.adapter.ServiceAdapter;
import com.matrixdeveloper.tajika.adapter.UpcomingJobAdapter;
import com.matrixdeveloper.tajika.model.ServiceRequestList;
import com.matrixdeveloper.tajika.model.UpcomingJob;
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

public class SpiHomeActivity extends AppCompatActivity {

    ImageView moreSettings, notifications, indicator;
    private SwitchMaterial onlineOffline;
    private LinearLayout newServiceRequestNotFound, upcomingJobsNotFound;

    RecyclerView requestRecycler, upcomingJobRecycler;
    private NewRequestAdapter requestAdapter;
    private UpcomingJobAdapter upcomingJobAdapter;
    private List<ServiceRequestList> requestLists;
    private List<UpcomingJob> upcomingJobList;
    private PrefManager pref;
    private String TAG = "SPHomeAct";
    private ImageView allBookings;
    private CardView cvMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_home);

        pref = new PrefManager(this);
        requestLists = new ArrayList<>();
        upcomingJobList = new ArrayList<>();
        requestAdapter = new NewRequestAdapter(SpiHomeActivity.this, requestLists, 0);
        upcomingJobAdapter = new UpcomingJobAdapter(SpiHomeActivity.this, upcomingJobList, 0);

        requestRecycler = findViewById(R.id.rv_new_request);
        upcomingJobRecycler = findViewById(R.id.rv_upcoming_job);

        requestRecycler.setHasFixedSize(true);
        requestRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestRecycler.setItemAnimator(new DefaultItemAnimator());
        requestRecycler.setAdapter(requestAdapter);

        upcomingJobRecycler.setHasFixedSize(true);
        upcomingJobRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        upcomingJobRecycler.setItemAnimator(new DefaultItemAnimator());
        upcomingJobRecycler.setAdapter(upcomingJobAdapter);

        moreSettings = findViewById(R.id.iv_moreSettings);
        notifications = findViewById(R.id.iv_notifications);
        onlineOffline = findViewById(R.id.switch_onlineOffline);
        indicator = findViewById(R.id.iv_indicator);
        newServiceRequestNotFound = findViewById(R.id.ll_newServiceRequestNotFound);
        upcomingJobsNotFound = findViewById(R.id.ll_upComingJobsRequestNotFound);
        cvMessageButton = findViewById(R.id.cv_conversation);
        allBookings = findViewById(R.id.iv_allBookings);

        cvMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, ConversationListActivity.class));

            }
        });
        allBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, SpiAllBookingsActivity.class));

            }
        });

        moreSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, SpiMoreActivity.class));
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, NotificationActivity.class));
            }
        });

        onlineOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onlineOffline.isChecked()) {
                    onlineOffline.setText("You are Online");
                    indicator.setColorFilter(getResources().getColor(R.color.light_green));
                    changeStatus("1");

                } else {
                    onlineOffline.setText("You are Offline");
                    indicator.setColorFilter(getResources().getColor(R.color.grey_300));
                    changeStatus("0");
                }
            }
        });

        getHomeData();

    }

    private void changeStatus(String status) {

        JSONObject data = new JSONObject();
        try {
            data.put("provider_id", pref.getString("id"));
            data.put("type", "I");
            data.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.UPDATE_LIVE_STATUS, data, response -> {
            Utils.log(TAG, response.toString());

        });

    }


    private void getHomeData() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", "30");//prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.HOME_SCREEN_DATA_INDI, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray requestArray, upcominJobArray;

            try {

                JSONObject jsonObject = response.optJSONObject("data");

                requestArray = jsonObject.getJSONArray("tajikaServiceRequest");
                upcominJobArray = jsonObject.getJSONArray("upcomingjob");

                // request list
                if (requestArray.length()<1){
                    newServiceRequestNotFound.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < requestArray.length(); i++) {

                    try {

                        ServiceRequestList serviceList = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), ServiceRequestList.class);
                        requestLists.add(serviceList);
                        requestAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // upcoming job list
                if (requestArray.length()<1){
                    upcomingJobsNotFound.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < upcominJobArray.length(); i++) {

                    try {

                        UpcomingJob upcomingJob = MySingleton.getGson().fromJson(upcominJobArray.getJSONObject(i).toString(), UpcomingJob.class);
                        upcomingJobList.add(upcomingJob);
                        upcomingJobAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }
}