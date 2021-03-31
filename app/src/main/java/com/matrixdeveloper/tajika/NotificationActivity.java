package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.NotificationAdapter;
import com.matrixdeveloper.tajika.model.Login;
import com.matrixdeveloper.tajika.model.NotificationModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView notificationRecyclerview;
    NotificationAdapter notificationAdapter;
    private List<NotificationModel> notificationModelList;
    private PrefManager prf;
    private String TAG = "AllServiceAct";
    private ImageView backPress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        prf = new PrefManager(this);
        notificationModelList = new ArrayList<>();

        notificationRecyclerview = findViewById(R.id.recView_notifications);
        notificationAdapter = new NotificationAdapter(this, notificationModelList);
        notificationRecyclerview.setHasFixedSize(true);
        notificationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerview.setAdapter(notificationAdapter);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> NotificationActivity.super.onBackPressed());

        getNotificationList();
    }

    private void getNotificationList() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.NOTIFICATION_LIST, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonarray = null;
            try {

                JSONObject jsonObject = response.optJSONObject("data");

                jsonarray = jsonObject.getJSONArray("notificationlist");

                for (int i = 0; i < jsonarray.length(); i++) {

                    try {

                        NotificationModel serviceList = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), NotificationModel.class);

                        notificationModelList.add(serviceList);

                        notificationAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    public void deleteNotification(int notificationId) {
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