package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIRecentTransactionAdapter;
import com.matrixdeveloper.tajika.model.SPIRecentTransactionModel;

public class SpiAllTransactionActivity extends AppCompatActivity {

    private RecyclerView recentTransactionRecyclerView;
    private SPIRecentTransactionAdapter recentTransactionAdapter;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_all_transaction);
        backPress = findViewById(R.id.iv_backPress);

        backPress.setOnClickListener(view -> SpiAllTransactionActivity.super.onBackPressed());

        recentTransactionRecyclerView = findViewById(R.id.recView_recentTransations);
        SPIRecentTransactionModel[] myListData = new SPIRecentTransactionModel[]{
                new SPIRecentTransactionModel(1, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(2, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(3, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(1, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(2, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(3, "TJD5611", "Peter Lawrence", "04 Feb 2021"),

        };

        recentTransactionAdapter = new SPIRecentTransactionAdapter(this, myListData);
        recentTransactionRecyclerView.setHasFixedSize(true);
        recentTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);
    }
}