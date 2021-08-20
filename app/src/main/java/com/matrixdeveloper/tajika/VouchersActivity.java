package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.VoucherAdapter;
import com.matrixdeveloper.tajika.model.ServiceRequestList;
import com.matrixdeveloper.tajika.model.VoucherList;
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

public class VouchersActivity extends AppCompatActivity {

    RecyclerView voucherRecyclerview;
    List<VoucherList> voucherLists;
    VoucherAdapter voucherAdapter;
    ImageView backPress;
    String TAG = "VouchersAct";
    PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);
        backPress=findViewById(R.id.iv_backPress);

        pref = new PrefManager(this);

        backPress.setOnClickListener(view -> VouchersActivity.super.onBackPressed());

        voucherLists = new ArrayList<>();
        voucherRecyclerview = findViewById(R.id.recView_vouchers);
        voucherAdapter = new VoucherAdapter(this, voucherLists);
        voucherRecyclerview.setHasFixedSize(true);
        voucherRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        voucherRecyclerview.setAdapter(voucherAdapter);

        getVoucherList();
    }

    private void getVoucherList() {

        ApiCall.getMethod(this, ServiceNames.VOUCHER_LIST, response -> {

            Utils.log(TAG, response.toString());

            JSONArray voucherArray;

            try {

                voucherArray = response.getJSONArray("data");


                for (int i = 0; i < voucherArray.length(); i++) {

                    try {

                        VoucherList voucherList = MySingleton.getGson().fromJson(voucherArray.getJSONObject(i).toString(), VoucherList.class);
                        voucherLists.add(voucherList);

                        voucherAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }
}