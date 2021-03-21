package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIRecentTransactionAdapter;
import com.matrixdeveloper.tajika.model.SPIRecentTransactionModel;

public class SpiCreditWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recentTransactionRecyclerView;
    private SPIRecentTransactionAdapter recentTransactionAdapter;
    private ImageView backPress;
    private TextView allCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_credit_wallet);
        backPress = findViewById(R.id.iv_backPress);
        recentTransactionRecyclerView = findViewById(R.id.recView_recentTransations);
        allCredit = findViewById(R.id.txt_allCredit);

        allCredit.setOnClickListener(this);
        backPress.setOnClickListener(this);


        SPIRecentTransactionModel[] myListData = new SPIRecentTransactionModel[]{
                new SPIRecentTransactionModel(1, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(2, "TJD5611", "Peter Lawrence", "04 Feb 2021"),
                new SPIRecentTransactionModel(3, "TJD5611", "Peter Lawrence", "04 Feb 2021"),

        };

        recentTransactionAdapter = new SPIRecentTransactionAdapter(this, myListData);
        recentTransactionRecyclerView.setHasFixedSize(true);
        recentTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == backPress) {
            super.onBackPressed();
        }
        if (view == allCredit) {
            startActivity(new Intent(SpiCreditWalletActivity.this, SpiAddCreditActivity.class));
        }
    }
}