package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;

public class SpiHomeActivity extends AppCompatActivity {

    ImageView moreSettings, notifications, indicator;
    private SwitchMaterial onlineOffline;
    private LinearLayout newServiceRequest, newServiceRequestNotFound, upcomingJobs, upcomingJobsNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_home);
        moreSettings = findViewById(R.id.iv_moreSettings);
        notifications = findViewById(R.id.iv_notifications);
        onlineOffline = findViewById(R.id.switch_onlineOffline);
        indicator = findViewById(R.id.iv_indicator);
        newServiceRequest = findViewById(R.id.ll_newServiceRequest);
        newServiceRequestNotFound = findViewById(R.id.ll_newServiceRequestNotFound);
        upcomingJobs = findViewById(R.id.ll_upComingJobs);
        upcomingJobsNotFound = findViewById(R.id.ll_upComingJobsRequestNotFound);

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
    }
}