package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.CoinsWalletAdapter;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;

public class CoinsWalletActivity extends AppCompatActivity {

    RecyclerView coinWalletRecView;
    CoinsWalletAdapter coinsWalletAdapter;
    private LinearLayout redeemCoin;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins_wallet);
        backPress = findViewById(R.id.iv_backPress);
        redeemCoin = findViewById(R.id.ll_redeemCoin);
        backPress.setOnClickListener(view -> CoinsWalletActivity.super.onBackPressed());
        redeemCoin.setOnClickListener(view -> startActivity(new Intent(CoinsWalletActivity.this, RedeemCoinActivity.class)));

        CoinsWalletModel[] coinsWalletModels = new CoinsWalletModel[]{
                new CoinsWalletModel(1, 0, "Danil Lawrenece", "100 Credited", "22 December 2020"),
                new CoinsWalletModel(1, 0, "Smart Cleaner", "50 Credited", "21 December 2020"),
                new CoinsWalletModel(1, 1, "Super voucher of KSH 500", "200 Debited", "20 December 2020"),
                new CoinsWalletModel(1, 1, "Super voucher of KSH 100", "10 Debited", "12 December 2020"),
        };

        coinWalletRecView = findViewById(R.id.recView_coinsWallet);
        coinsWalletAdapter = new CoinsWalletAdapter(this, coinsWalletModels);
        coinWalletRecView.setHasFixedSize(true);
        coinWalletRecView.setLayoutManager(new LinearLayoutManager(this));
        coinWalletRecView.setAdapter(coinsWalletAdapter);
    }
}