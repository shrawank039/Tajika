package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIAllBookingsAdapter;
import com.matrixdeveloper.tajika.model.SPIAllBookingsModel;

public class SpiAllBookingsActivity extends AppCompatActivity {

    private RecyclerView allBookingsRecyclerView;
    private SPIAllBookingsAdapter allBookingsAdapter;
    TextView upcoming, completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_all_bookings);
        allBookingsRecyclerView = findViewById(R.id.recView_allBookings);
        upcoming = findViewById(R.id.txt_upcoming);
        completed = findViewById(R.id.txt_completed);

        SPIAllBookingsModel[] myListData = new SPIAllBookingsModel[]{
                new SPIAllBookingsModel(1, "TJD5611", "Peter Lawrence", "04 Feb 2021",
                        "Catering", "1", "05 Feb 2021"),
                new SPIAllBookingsModel(2, "TJD5611", "Peter Lawrence", "04 Feb 2021",
                        "Catering", "2", "05 Feb 2021"),
                new SPIAllBookingsModel(2, "TJD5611", "Peter Lawrence", "04 Feb 2021",
                        "Catering", "0", "05 Feb 2021"),

        };

        allBookingsAdapter = new SPIAllBookingsAdapter(this, myListData);
        allBookingsRecyclerView.setHasFixedSize(true);
        allBookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allBookingsRecyclerView.setAdapter(allBookingsAdapter);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upcoming.setBackground(getResources().getDrawable(R.drawable.job_switch_background));
                upcoming.setTextColor(getResources().getColor(R.color.white));

                completed.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                completed.setTextColor(getResources().getColor(R.color.dark_blue));
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completed.setBackground(getResources().getDrawable(R.drawable.job_switch_background));
                completed.setTextColor(getResources().getColor(R.color.white));

                upcoming.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                upcoming.setTextColor(getResources().getColor(R.color.dark_blue));

            }
        });
    }
}