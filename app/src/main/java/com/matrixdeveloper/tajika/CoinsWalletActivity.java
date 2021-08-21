package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.CoinsWalletAdapter;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoinsWalletActivity extends AppCompatActivity {

    RecyclerView coinWalletRecView;
    CoinsWalletAdapter coinsWalletAdapter;
    private LinearLayout redeemCoin;
    private ImageView backPress;
    private TextView pointsEarned, pointsAvailable, pointsUsed;
    private String TAG = "CoinsWalletActivity";

    private PrefManager prf;
    private ArrayList<CoinsWalletModel> myListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins_wallet);

        initViews();
        initListeners();

        fetchWalletDetails();

    }

    private void fetchWalletDetails() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.USER_WALLET_DETAILS, data, response -> {
            Utils.log(TAG, response.toString());
            JSONObject jsonObject = null;
            try {
                jsonObject = response.getJSONObject("data");
                pointsEarned.setText(jsonObject.optString("total_point_earned"));
                pointsAvailable.setText(jsonObject.optString("point_available"));
                pointsUsed.setText(jsonObject.optString("point_used"));

                JSONArray jsonArray = jsonObject.getJSONArray("coin_history");

                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CoinsWalletModel walletModel = MySingleton.getGson().fromJson(jsonArray.getJSONObject(i).toString(), CoinsWalletModel.class);
                        myListData.add(walletModel);

                    }
                    coinWalletRecView = findViewById(R.id.recView_coinsWallet);
                    coinsWalletAdapter = new CoinsWalletAdapter(this, myListData);
                    coinWalletRecView.setHasFixedSize(true);
                    coinWalletRecView.setLayoutManager(new LinearLayoutManager(this));
                    coinWalletRecView.setAdapter(coinsWalletAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> CoinsWalletActivity.super.onBackPressed());
        redeemCoin.setOnClickListener(view -> startActivity(new Intent(CoinsWalletActivity.this, RedeemCoinActivity.class)));
    }

    private void initViews() {
        prf = new PrefManager(this);

        backPress = findViewById(R.id.iv_backPress);
        redeemCoin = findViewById(R.id.ll_redeemCoin);
        pointsUsed = findViewById(R.id.txt_pointsUsed);
        pointsAvailable = findViewById(R.id.txt_pointsAvailable);
        pointsEarned = findViewById(R.id.txt_pointsEarned);

    }
}