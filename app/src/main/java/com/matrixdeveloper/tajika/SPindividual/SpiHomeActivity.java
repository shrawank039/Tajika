package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.matrixdeveloper.tajika.ConversationListActivity;
import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.NewRequestAdapter;
import com.matrixdeveloper.tajika.adapter.UpcomingJobAdapter;
import com.matrixdeveloper.tajika.model.RequestDetails;
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

    private ImageView moreSettings, notifications, indicator;
    private SwitchMaterial onlineOffline;
    private LinearLayout newServiceRequestNotFound, upcomingJobsNotFound;
    private RecyclerView requestRecycler, upcomingJobRecycler;
    private NewRequestAdapter requestAdapter;
    private UpcomingJobAdapter upcomingJobAdapter;
    private List<ServiceRequestList> requestLists;
    private List<UpcomingJob> upcomingJobList;
    private PrefManager pref;
    private final String TAG = "SPHomeAct";
    private ImageView allBookings;
    private CardView cvMessageButton;
    private Button checkNewOffers;
    private ViewFlipper viewFlipper;
    private TextView creditBalance, todaySales, weekSales, totalSales, consumerGoods, clientConnected;
    private TextView totalServiceProvided, totalGoodsSold, totalClientConnected, totalFailedService;
    ServiceRequestList serviceList;
    RequestDetails requestDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_home);

        initViews();

        requestLists = new ArrayList<>();
        upcomingJobList = new ArrayList<>();
        requestAdapter = new NewRequestAdapter(SpiHomeActivity.this, requestLists, 0);
        upcomingJobAdapter = new UpcomingJobAdapter(SpiHomeActivity.this, upcomingJobList, 0);

        requestRecycler.setHasFixedSize(true);
        requestRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestRecycler.setItemAnimator(new DefaultItemAnimator());
        requestRecycler.setAdapter(requestAdapter);

        upcomingJobRecycler.setHasFixedSize(true);
        upcomingJobRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        upcomingJobRecycler.setItemAnimator(new DefaultItemAnimator());
        upcomingJobRecycler.setAdapter(upcomingJobAdapter);

        initListeners();

        getHomeData();

    }


    private void initViews() {
        pref = new PrefManager(this);
        requestRecycler = findViewById(R.id.rv_new_request);
        upcomingJobRecycler = findViewById(R.id.rv_upcoming_job);
        checkNewOffers = findViewById(R.id.btn_checkNewOrders);
        viewFlipper = findViewById(R.id.viewflipper);
        moreSettings = findViewById(R.id.iv_moreSettings);
        notifications = findViewById(R.id.iv_notifications);
        onlineOffline = findViewById(R.id.switch_onlineOffline);
        indicator = findViewById(R.id.iv_indicator);
        newServiceRequestNotFound = findViewById(R.id.ll_newServiceRequestNotFound);
        upcomingJobsNotFound = findViewById(R.id.ll_upComingJobsRequestNotFound);
        cvMessageButton = findViewById(R.id.cv_conversation);
        allBookings = findViewById(R.id.iv_allBookings);
        creditBalance = findViewById(R.id.txt_creditBalance);
        todaySales = findViewById(R.id.txt_todaySales);
        weekSales = findViewById(R.id.txt_weekSales);
        totalSales = findViewById(R.id.txt_totalSales);
        consumerGoods = findViewById(R.id.txt_consumerGoods);
        clientConnected = findViewById(R.id.txt_clientsConnected);
        totalServiceProvided = findViewById(R.id.txt_totalServiceProvided);
        totalGoodsSold = findViewById(R.id.txt_totalGoodsSold);
        totalClientConnected = findViewById(R.id.txt_totalClientConnected);
        totalFailedService = findViewById(R.id.txt_totalFailedService);
    }

    private void initListeners() {

        checkNewOffers.setOnClickListener(v -> viewFlipper.setDisplayedChild(1));

        cvMessageButton.setOnClickListener(view -> startActivity(new Intent(SpiHomeActivity.this, ConversationListActivity.class)));

        allBookings.setOnClickListener(view -> startActivity(new Intent(SpiHomeActivity.this, SpiAllBookingsActivity.class)));

        moreSettings.setOnClickListener(view -> startActivity(new Intent(SpiHomeActivity.this, SpiMoreActivity.class)));

        notifications.setOnClickListener(view -> startActivity(new Intent(SpiHomeActivity.this, NotificationActivity.class)));

        onlineOffline.setOnClickListener(view -> {
            if (onlineOffline.isChecked()) {
                changeStatus("1");

            } else {
                changeStatus("0");
            }
        });
    }

    private void changeStatus(String status) {

        if (status.equals("1")) {
            onlineOffline.setText("You are Online");
            indicator.setColorFilter(getResources().getColor(R.color.md_green_700));
        } else if (status.equals("0")) {
            onlineOffline.setText("You are Offline");
            indicator.setColorFilter(getResources().getColor(R.color.md_red_A700));
        }

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

    public void getServiceDetails(String id) {

        JSONObject data = new JSONObject();
        try {
            data.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST_DETAILS, data, response -> {
            Utils.log(TAG, response.toString());
            try {

                requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);
                changeServiceStatus(requestDetails.getId().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    private void getHomeData() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
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

                JSONObject jsonObject1 = jsonObject.getJSONObject("sales");

                // request list
                if (requestArray.length() < 1) {
                    newServiceRequestNotFound.setVisibility(View.VISIBLE);
                }

                creditBalance.setText("Credit Bal: Ksh " + jsonObject.optString("creditbalance"));
                todaySales.setText(jsonObject1.optString("today_sale") + " Ksh");
                weekSales.setText(jsonObject1.optString("week_sale") + " Ksh");
                totalSales.setText(jsonObject1.optString("total_sale") + " Ksh");
                consumerGoods.setText(jsonObject1.optString("customer_goods"));
                clientConnected.setText(jsonObject1.optString("clinet_connected"));
                totalServiceProvided.setText(jsonObject1.optString("service_provided"));
                totalGoodsSold.setText(jsonObject1.optString("goods_sold"));
                totalClientConnected.setText(jsonObject1.optString("client_contacted"));
                totalFailedService.setText(jsonObject1.optString("failed_order"));


                for (int i = 0; i < requestArray.length(); i++) {

                    try {

                        serviceList = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), ServiceRequestList.class);
                        requestLists.add(serviceList);
                        requestAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // upcoming job list
                if (requestArray.length() < 1) {
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

    @Override
    public void onBackPressed() {
        int displayedChild = viewFlipper.getDisplayedChild();
        if (displayedChild > 0) {
            viewFlipper.setDisplayedChild(displayedChild - 1);
        } else {
            super.onBackPressed();
        }
    }

    public void changeServiceStatus(String id) {

        Toast.makeText(this, ""+requestDetails.getId(), Toast.LENGTH_SHORT).show();
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
            data.put("status", "Accept");
            data.put("service_provider_id", pref.getString("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.CHANGE_SERVICE_REQUEST_STATUS, data, response -> {
            Utils.log(TAG, response.toString());

            startActivity(new Intent(getApplicationContext(), SpiServiceAcceptActivity.class)
                    .putExtra("requestDetails", requestDetails));
        });
    }
}