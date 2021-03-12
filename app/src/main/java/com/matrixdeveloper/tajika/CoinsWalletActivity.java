package com.matrixdeveloper.tajika;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.CoinsWalletAdapter;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;

public class CoinsWalletActivity extends AppCompatActivity {

    RecyclerView coinWalletRecView;
    CoinsWalletAdapter coinsWalletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins_wallet);
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