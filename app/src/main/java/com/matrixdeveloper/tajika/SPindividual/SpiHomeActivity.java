package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.matrixdeveloper.tajika.ConversationListActivity;
import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;
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
    private LinearLayout newServiceRequest, newServiceRequestNotFound, upcomingJobs, upcomingJobsNotFound;
    private TextView newServiceRequestInfo;

    RecyclerView requestRecycler, upcomingJobRecycler;
    private List<ServiceRequestList> serviceRequestLists;
    private List<UpcomingJob> upcomingJobList;
    private PrefManager prf;
    private String TAG = "SPHomeAct";
    private ImageView allBookings;
    private CardView cvMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_home);

        prf = new PrefManager(this);
        serviceRequestLists = new ArrayList<>();
        upcomingJobList = new ArrayList<>();

        moreSettings = findViewById(R.id.iv_moreSettings);
        notifications = findViewById(R.id.iv_notifications);
        onlineOffline = findViewById(R.id.switch_onlineOffline);
        indicator = findViewById(R.id.iv_indicator);
        newServiceRequest = findViewById(R.id.ll_newServiceRequest);
        newServiceRequestNotFound = findViewById(R.id.ll_newServiceRequestNotFound);
        upcomingJobs = findViewById(R.id.ll_upComingJobs);
        upcomingJobsNotFound = findViewById(R.id.ll_upComingJobsRequestNotFound);
        newServiceRequestInfo = findViewById(R.id.txt_newServiceRequestInfo);
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

        newServiceRequestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpiHomeActivity.this, SpiServiceRequestDetailsActivity.class));
            }
        });

        onlineOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onlineOffline.isChecked()) {
                    newServiceRequest.setVisibility(View.VISIBLE);
                    upcomingJobs.setVisibility(View.VISIBLE);
                    newServiceRequestNotFound.setVisibility(View.GONE);
                    upcomingJobsNotFound.setVisibility(View.GONE);
                    onlineOffline.setText("You are Online");
                    indicator.setColorFilter(getResources().getColor(R.color.light_green));


                } else {
                    newServiceRequest.setVisibility(View.GONE);
                    upcomingJobs.setVisibility(View.GONE);
                    newServiceRequestNotFound.setVisibility(View.VISIBLE);
                    upcomingJobsNotFound.setVisibility(View.VISIBLE);
                    onlineOffline.setText("You are Offline");
                    indicator.setColorFilter(getResources().getColor(R.color.grey_300));
                }
            }
        });

        getHomeData();

    }


    private void getHomeData() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", "30");//prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.HOME_SCREEN_DATA, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray requestArray, upcominJobArray;

            try {

                JSONObject jsonObject = response.optJSONObject("data");

                requestArray = jsonObject.getJSONArray("tajikaServiceRequest");
                upcominJobArray = jsonObject.getJSONArray("upcomingjob");

                // request list
                for (int i = 0; i < requestArray.length(); i++) {

                    try {

                        ServiceRequestList serviceList = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), ServiceRequestList.class);
                        serviceRequestLists.add(serviceList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // upcoming job list
                for (int i = 0; i < upcominJobArray.length(); i++) {

                    try {

                        UpcomingJob upcomingJob = MySingleton.getGson().fromJson(upcominJobArray.getJSONObject(i).toString(), UpcomingJob.class);
                        upcomingJobList.add(upcomingJob);

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