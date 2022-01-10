package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIAllBookingsAdapter;
import com.matrixdeveloper.tajika.model.SPIAllBookingsModel;
import com.matrixdeveloper.tajika.model.ServiceRequestList;
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

public class SpiAllBookingsActivity extends AppCompatActivity {

    private RecyclerView allBookingsRecyclerView;
    private SPIAllBookingsAdapter allBookingsAdapter;
    TextView upcoming, completed;
    private final String TAG = "SpiAllBookingsAct";
    private PrefManager pref;
    private ImageView backPress;
    private List<SPIAllBookingsModel> spiAllBookingsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_all_bookings);
        allBookingsRecyclerView = findViewById(R.id.recView_allBookings);
        upcoming = findViewById(R.id.txt_upcoming);
        completed = findViewById(R.id.txt_completed);
        backPress = findViewById(R.id.iv_backPress);
        pref = new PrefManager(this);

        allBookingsAdapter = new SPIAllBookingsAdapter(this, spiAllBookingsModels);
        allBookingsRecyclerView.setHasFixedSize(true);
        allBookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allBookingsRecyclerView.setAdapter(allBookingsAdapter);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upcoming.setBackground(getResources().getDrawable(R.drawable.job_switch_background));
                upcoming.setTextColor(getResources().getColor(R.color.white));
                upcoming.setBackgroundResource(R.color.dark_blue);

                //completed.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                completed.setBackgroundResource(R.color.white);
                completed.setTextColor(getResources().getColor(R.color.dark_blue));

                getAllBooking("Pending");
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //completed.setBackground(getResources().getDrawable(R.drawable.job_switch_background));
                completed.setTextColor(getResources().getColor(R.color.white));
                completed.setBackgroundResource(R.color.dark_blue);

                //upcoming.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                upcoming.setTextColor(getResources().getColor(R.color.dark_blue));
                upcoming.setBackgroundResource(R.color.white);

                getAllBooking("Completed");
            }
        });
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpiAllBookingsActivity.super.onBackPressed();
            }
        });

        getAllBooking("Pending");
    }

    private void getAllBooking(String status) {

        spiAllBookingsModels.clear();

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.PROVIDER_ALL_BOOKING, data, response -> {
            Utils.log(TAG, response.toString());

            JSONArray requestArray;

            JSONObject jsonObject = response.optJSONObject("data");
            try {
                requestArray = jsonObject.getJSONArray("tajikaServiceRequest");
                for (int i = 0; i < requestArray.length(); i++) {
                    try {
                        SPIAllBookingsModel spiAllBookingsModel = MySingleton.getGson().fromJson(requestArray.getJSONObject(i).toString(), SPIAllBookingsModel.class);
                        spiAllBookingsModels.add(spiAllBookingsModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                allBookingsAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}