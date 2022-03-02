package com.matrixdeveloper.tajika.SPindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.NewRequestAdapter;
import com.matrixdeveloper.tajika.adapter.NotificationAdapter;
import com.matrixdeveloper.tajika.model.NotificationModel;
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

public class SpiAllRequestActivity extends AppCompatActivity {

    RecyclerView notificationRecyclerview;
    private NewRequestAdapter requestAdapter;
    List<ServiceRequestList> requestLists =new ArrayList<>();
    private PrefManager prf;
    private final String TAG = "AllServiceAct";
    private ImageView backPress, clearAllNotification;
    RequestDetails requestDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_all_request);

        prf = new PrefManager(this);

        notificationRecyclerview = findViewById(R.id.recView_notifications);
        clearAllNotification = findViewById(R.id.iv_refreshNotification);

        requestAdapter = new NewRequestAdapter(SpiAllRequestActivity.this, requestLists, 1);
        notificationRecyclerview.setHasFixedSize(true);
        notificationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerview.setAdapter(requestAdapter);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> SpiAllRequestActivity.super.onBackPressed());
        clearAllNotification.setOnClickListener(view -> refreshPopup());

        //getRequestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRequestData();
    }

    private void refreshPopup() {
        PopupMenu popup = new PopupMenu(SpiAllRequestActivity.this, clearAllNotification);
        popup.getMenuInflater().inflate(R.menu.clear_all_notification_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.clearAll) {
                deleteNotification("all");
            }
            return true;
        });
        popup.show();
    }

    private void getRequestData() {
        requestLists.clear();
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
            data.put("type", "Pending");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.GET_SERVICE_REQUEST, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray requestArray;

            try {

                requestArray = response.getJSONArray("data");

                for (int i = 0; i < requestArray.length(); i++) {

                    try {

                        ServiceRequestList serviceList = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), ServiceRequestList.class);
                        requestLists.add(serviceList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                requestAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public void getServiceDetails(String id, String status) {

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
                changeServiceStatus(requestDetails.getId().toString(), status);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }

    public void changeServiceStatus(String id, String status) {

        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
            data.put("status", status);
            data.put("service_provider_id", prf.getString("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.CHANGE_SERVICE_REQUEST_STATUS, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                requestDetails = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), RequestDetails.class);
                if (response.optInt("status")==400){
                    Utils.toast(getApplicationContext(), response.optString("message"));
                }else {
                    startActivity(new Intent(getApplicationContext(), SpiServiceAcceptActivity.class)
                            .putExtra("ser_id", requestDetails.getId().toString()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteNotification(String notificationId) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", notificationId);
            data.put("user_id", prf.getString("id"));
            data.put("type", "individual");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.DELETE_NOTIFICATION, data, response -> {

            Utils.log(TAG, response.toString());
            Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

        });
    }
}