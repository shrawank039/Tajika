package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.SearchAdapter;
import com.matrixdeveloper.tajika.model.Category;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllServiceActivity extends AppCompatActivity {

    private String TAG = "AllServiceAct";
    private EditText inputSearch;
    private List<Category> catService, catGoods;
    private SearchAdapter serviceAdapter;
    private RecyclerView rvService;
    private ImageView backPress;
    String type = "service";
    private TextView titleView;
    private LinearLayout llnotFount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_service);

        inputSearch = findViewById(R.id.inputSearch);
        backPress = findViewById(R.id.iv_backPress);
        rvService = findViewById(R.id.rv_viewAllService);
        titleView = findViewById(R.id.txt_header);
        llnotFount = findViewById(R.id.ll_noItemFound);
        type = getIntent().getStringExtra("type");

        if (type.equals("goods"))
            titleView.setText("Browse Goods");

        catService = new ArrayList<>();
        catGoods = new ArrayList<>();
        serviceAdapter = new SearchAdapter(AllServiceActivity.this, catService);

        rvService.setHasFixedSize(true);

        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setItemAnimator(new DefaultItemAnimator());
        rvService.setAdapter(serviceAdapter);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                catService.clear();
                catGoods.clear();
                if (String.valueOf(cs).equals("")) {
                    getSearchResult("");
                } else {
                    getSearchResult(String.valueOf(cs));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllServiceActivity.super.onBackPressed();

            }
        });
        catService.clear();
        catGoods.clear();
        getSearchResult("");
    }


    private void getSearchResult(String key) {

        Toast.makeText(this, "" + key, Toast.LENGTH_SHORT).show();
        JSONObject data = new JSONObject();
        try {
            data.put("servicename", key);
            data.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethodWithoutProgress(this, ServiceNames.FILTER_SERVICE_LIST, data, response -> {

            JSONObject jsonObject = null;
            JSONArray serviceArray = null;
            if (Objects.equals(response.opt("status"), 200)) {
                try {
                    jsonObject = response.optJSONObject("data");
                    serviceArray = jsonObject.getJSONArray(type);
                    Utils.toast(this,serviceArray.toString());

                    if (serviceArray.length() > 0) {
                        for (int i = 0; i < serviceArray.length(); i++) {
                            try {
                                Category category = MySingleton.getGson().fromJson(serviceArray.getJSONObject(i).toString(), Category.class);
                                catService.add(category);
                                serviceAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        llnotFount.setVisibility(View.VISIBLE);
                        rvService.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void backPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}