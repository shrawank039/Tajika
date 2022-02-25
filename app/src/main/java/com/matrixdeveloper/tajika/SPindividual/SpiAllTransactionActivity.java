package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIRecentTransactionAdapter;
import com.matrixdeveloper.tajika.model.Category;
import com.matrixdeveloper.tajika.model.SPIRecentTransactionModel;
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

public class SpiAllTransactionActivity extends AppCompatActivity {

    private RecyclerView recentTransactionRecyclerView;
    private SPIRecentTransactionAdapter recentTransactionAdapter;
    private ImageView backPress;
    private PrefManager prf;
    private final String TAG = "AllTransactionsAct";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_all_transaction);

        type = getIntent().getStringExtra("type");
        backPress = findViewById(R.id.iv_backPress);
        prf=new PrefManager(this);

        backPress.setOnClickListener(view -> SpiAllTransactionActivity.super.onBackPressed());

        recentTransactionRecyclerView = findViewById(R.id.recView_recentTransations);

        getAppTransactionsDetails();

    }

    private void getAppTransactionsDetails() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", prf.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ALL_TRANSACTIONS, data, response -> {

            Utils.log(TAG, response.toString());

            List<SPIRecentTransactionModel> myListData = new ArrayList<>();
            JSONArray jsonArray = response.optJSONArray("data");

            try {

                for (int i = 0; i < jsonArray.length(); i++) {
                    SPIRecentTransactionModel spiRecentTransactionModel = MySingleton.getGson().fromJson(jsonArray.getJSONObject(i).toString(), SPIRecentTransactionModel.class);
                    myListData.add(spiRecentTransactionModel);

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
}