package com.matrixdeveloper.tajika;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.Adapter.RedeemCoinAdapter;
import com.matrixdeveloper.tajika.Model.RedeemCoinModel;

public class RedeemCoinActivity extends AppCompatActivity {

    RecyclerView redeemCoinRecView;
    RedeemCoinAdapter redeemCoinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_coin);

        RedeemCoinModel[] redeemCoinModels = new RedeemCoinModel[]{
                new RedeemCoinModel(1, 0, "10% flat on all order above 500", "1000", "Valid till 22 December 2020"),
                new RedeemCoinModel(2, 1, "20% flat on all order above 1500", "500", "Valid till 22 December 2020"),
                new RedeemCoinModel(3, 2, "30% flat on all order above 2500", "1100", "Valid till 22 December 2020"),
                new RedeemCoinModel(4, 3, "40% flat on all order above 3500", "100", "Valid till 22 December 2020"),
        };

        redeemCoinRecView = findViewById(R.id.recView_redeemCoin);
        redeemCoinAdapter = new RedeemCoinAdapter(this, redeemCoinModels);
        redeemCoinRecView.setHasFixedSize(true);
        redeemCoinRecView.setLayoutManager(new LinearLayoutManager(this));
        redeemCoinRecView.setAdapter(redeemCoinAdapter);
    }
}