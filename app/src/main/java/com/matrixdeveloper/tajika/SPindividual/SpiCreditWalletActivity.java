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
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpiCreditWalletActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recentTransactionRecyclerView;
    private SPIRecentTransactionAdapter recentTransactionAdapter;
    private ImageView backPress;
    private TextView allCredit, allTransaction, walletBalance;
    private PrefManager prf;
    private final String TAG = "WalletAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_credit_wallet);


        backPress = findViewById(R.id.iv_backPress);
        recentTransactionRecyclerView = findViewById(R.id.recView_recentTransations);
        allCredit = findViewById(R.id.txt_allCredit);
        allTransaction = findViewById(R.id.txt_allTransaction);
        walletBalance = findViewById(R.id.txt_walletBalance);


        prf = new PrefManager(this);

        allCredit.setOnClickListener(this);
        backPress.setOnClickListener(this);
        allTransaction.setOnClickListener(this);

        getWalletDetails();

    }

    private void getWalletDetails() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.WALLET_DETAILS, data, response -> {

            Utils.log(TAG, response.toString());

            List<SPIRecentTransactionModel> myListData = new ArrayList<>();
            JSONObject jsonObject = response.optJSONObject("data");

            try {
                JSONArray jsonArray = jsonObject.getJSONArray("transdetails");
                walletBalance.setText(jsonObject.optString("walletamount"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    myListData.add(new SPIRecentTransactionModel(
                            object.optString("id"),
                            object.optString("transcation_id"),
                            object.optString("submit_date"),
                            object.optString("amount")
                    ));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            recentTransactionAdapter = new SPIRecentTransactionAdapter(this, myListData);
            recentTransactionRecyclerView.setHasFixedSize(true);
            recentTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);

        });
    }

    @Override
    public void onClick(View view) {
        if (view == backPress) {
            super.onBackPressed();
        }
        if (view == allCredit) {
            startActivity(new Intent(SpiCreditWalletActivity.this, SpiAddCreditActivity.class));
        }
        if (view == allTransaction) {
            startActivity(new Intent(SpiCreditWalletActivity.this, SpiAllTransactionActivity.class));
        }
    }
}