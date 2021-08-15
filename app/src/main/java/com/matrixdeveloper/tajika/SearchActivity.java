package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private EditText inputSearch;
    private String TAG = "SearchServiceAct";
    private List<Category> catService, catGoods;
    private SearchAdapter serviceAdapter, goodsAdapter;
    private RecyclerView rvService, rvGoods;
    private ImageView backPress;
    private LinearLayout noItemFound;
    private LinearLayout serviceGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputSearch = findViewById(R.id.inputSearch);
        backPress = findViewById(R.id.iv_backPress);
        rvService = findViewById(R.id.rv_viewAllService);
        rvGoods = findViewById(R.id.rv_viewAllGoods);
        noItemFound = findViewById(R.id.ll_noItemFound);
        serviceGoods = findViewById(R.id.ll_serviceGoods);

        catService = new ArrayList<>();
        catGoods = new ArrayList<>();
        serviceAdapter = new SearchAdapter(SearchActivity.this, catService);
        goodsAdapter = new SearchAdapter(SearchActivity.this, catGoods);

        rvService.setHasFixedSize(true);
        rvGoods.setHasFixedSize(true);

        rvService.setLayoutManager(new LinearLayoutManager(this));
        rvService.setItemAnimator(new DefaultItemAnimator());
        rvService.setAdapter(serviceAdapter);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.setItemAnimator(new DefaultItemAnimator());
        rvGoods.setAdapter(goodsAdapter);


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
                SearchActivity.super.onBackPressed();

            }
        });
        catService.clear();
        catGoods.clear();
        getSearchResult("");
    }


    private void getSearchResult(String key) {

        JSONObject data = new JSONObject();
        try {
            data.put("servicename", key);
            data.put("type", "all");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethodWithoutProgress(this, ServiceNames.FILTER_SERVICE_LIST, data, response -> {

            JSONObject jsonObject = null;
            JSONArray serviceArray = null;
            JSONArray goodsArray = null;
            JSONArray subCategory = null;
            if (Objects.equals(response.opt("status"), 200)) {
                try {

                    jsonObject = response.optJSONObject("data");
                    serviceArray = jsonObject.getJSONArray("service");
                    goodsArray = jsonObject.getJSONArray("goods");

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

                        for (int i = 0; i < goodsArray.length(); i++) {
                            try {
                                Category category = MySingleton.getGson().fromJson(goodsArray.getJSONObject(i).toString(), Category.class);
                                catGoods.add(category);
                                goodsAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        noItemFound.setVisibility(View.VISIBLE);
                        serviceGoods.setVisibility(View.GONE);

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