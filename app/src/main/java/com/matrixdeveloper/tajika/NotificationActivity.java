package com.matrixdeveloper.tajika;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.Adapter.NotificationAdapter;
import com.matrixdeveloper.tajika.Model.NotificationModel;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView notificationRecyclerview;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        NotificationModel[] notificationDetails = new NotificationModel[]{
                new NotificationModel(1, R.drawable.app_logo,"Service request accepted", "Your Service Request for super Catering has been accepted.", "Go to book the service"),
                new NotificationModel(2, R.drawable.app_logo,"Service request accepted", "Your Service Request for super Catering has been declined", "Request new service"),
                new NotificationModel(3, R.drawable.app_logo,"Service request accepted", "Your Service Request for super Catering has been completed.", "Check booking details"),
        };

        notificationRecyclerview = findViewById(R.id.recView_notifications);
        notificationAdapter = new NotificationAdapter(this, notificationDetails);
        notificationRecyclerview.setHasFixedSize(true);
        notificationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        notificationRecyclerview.setAdapter(notificationAdapter);
    }
}