package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.ServiceAdapter;
import com.matrixdeveloper.tajika.model.SubCategory;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.RecyclerTouchListener;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AllServiceActivity extends AppCompatActivity {

    private String TAG = "AllServiceAct";
    private List<SubCategory> subCategories;
    private ServiceAdapter mAdapter;
    private RecyclerView recyclerView;
    private ImageView backPress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> AllServiceActivity.super.onBackPressed());



        recyclerView = findViewById(R.id.rv_viewAllService);

        subCategories = new ArrayList<>();
        mAdapter = new ServiceAdapter(AllServiceActivity.this, subCategories,1);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubCategory subCategory = subCategories.get(position);

                startActivity(new Intent(getApplicationContext(), LocationSelectorActivity.class)
                        .putExtra("service_name", subCategory.getServiceName())
                        .putExtra("service_id", String.valueOf(subCategory.getId())));

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getAllService();

    }

    private void getAllService() {

        ApiCall.getMethod(this, ServiceNames.SERVICE_LIST, response -> {

            Utils.log(TAG, response.toString());

            JSONArray jsonarray = null;
            try {

                jsonarray = response.getJSONArray("data");

                for (int i = 0; i < jsonarray.length(); i++) {

                    try {

                        SubCategory subCategory = MySingleton.getGson().fromJson(jsonarray.getJSONObject(i).toString(), SubCategory.class);

                        subCategories.add(subCategory);

                        mAdapter.notifyDataSetChanged();


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