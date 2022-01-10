package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.CoinsWalletAdapter;
import com.matrixdeveloper.tajika.adapter.RedeemCoinAdapter;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;
import com.matrixdeveloper.tajika.model.RedeemCoinModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RedeemCoinActivity extends AppCompatActivity {

    private RecyclerView redeemCoinRecView;
    private RedeemCoinAdapter redeemCoinAdapter;
    private ImageView backPress;
    private final String TAG="RedeemCoinAct";
    private final ArrayList<RedeemCoinModel> myListData = new ArrayList<>();
    public static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_coin);

        prf = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> RedeemCoinActivity.super.onBackPressed());

        getRedeemCoinsList();

    }

    private void getRedeemCoinsList() {

        JSONObject object = new JSONObject();
        try {
            object.put("user_id", prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.REDEEM_COIN_LIST, object, response -> {
            Utils.log(TAG, response.toString());
            JSONArray jsonArray = null;
            try {
                jsonArray = response.getJSONArray("data");

                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RedeemCoinModel coinModel = MySingleton.getGson().fromJson(jsonArray.getJSONObject(i).toString(), RedeemCoinModel.class);
                        myListData.add(coinModel);

                    }
                    redeemCoinRecView = findViewById(R.id.recView_redeemCoin);
                    redeemCoinAdapter = new RedeemCoinAdapter(this, myListData);
                    redeemCoinRecView.setHasFixedSize(true);
                    redeemCoinRecView.setLayoutManager(new LinearLayoutManager(this));
                    redeemCoinRecView.setAdapter(redeemCoinAdapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}