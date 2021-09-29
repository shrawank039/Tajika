package com.matrixdeveloper.tajika.SPbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiAddNewServiceActivity;
import com.matrixdeveloper.tajika.model.Category;
import com.matrixdeveloper.tajika.model.SubCategory;
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
import java.util.Objects;

public class SpiAddNewGoodsActivity extends AppCompatActivity {

    private final String TAG = "SpbMyServicesAct";
    private PrefManager pref;

    private EditText edtPrice;
    private Spinner sprCategory, sprSubCategory;
    private Button saveGoods;
    private ImageView backPress;

    int serviceCatID, serviceSubCatID;


    private List<String> categoryName = new ArrayList<>();
    private List<Integer> categoryID = new ArrayList<>();

    private List<String> subCategoryName = new ArrayList<>();
    private List<Integer> subCategoryID = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_add_new_goods);

        initViews();
        initListeners();
        getCatSubCat();

        categoryName.add("Choose category");
        categoryID.add(0);

        ArrayAdapter aaa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subCategoryName);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                serviceCatID = categoryID.get(sprCategory.getSelectedItemPosition());

                // for sub category
                subCategoryID.clear();
                subCategoryName.clear();
                subCategoryName.add("Choose sub-category");
                subCategoryID.add(0);

                if (position != 0) {
                    for (int i = 0; i < categoryList.get(position - 1).getSubCategory().size(); i++) {
                        SubCategory subCategory = categoryList.get(position - 1).getSubCategory().get(i);
                        subCategoryID.add(subCategory.getId());
                        subCategoryName.add(subCategory.getServiceName());
                    }
                }

                sprSubCategory.setAdapter(aaa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sprSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                serviceSubCatID = subCategoryID.get(sprSubCategory.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initListeners() {
        backPress.setOnClickListener(v -> SpiAddNewGoodsActivity.super.onBackPressed());
        saveGoods.setOnClickListener(v -> addNewGoods());

    }

    private void initViews() {
        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        edtPrice = findViewById(R.id.edt_enterPrice);
        sprCategory = findViewById(R.id.spinner_category);
        sprSubCategory = findViewById(R.id.spinner_subCategory);
        saveGoods = findViewById(R.id.btn_saveGoods);
    }

    private void addNewGoods() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", String.valueOf(serviceCatID));
            data.put("sub_cat_id", String.valueOf(serviceSubCatID));
            data.put("price", edtPrice.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ADD_NEW_GOODS, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
            if (response.optString("status").equals("200")){
                finish();
            }
        });

    }

    private void getCatSubCat() {

        JSONObject data = new JSONObject();
        try {
            data.put("service_type", "goods");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ALL_CAT_SUBCAT, data, response -> {

            Utils.log(TAG, response.toString());

            JSONArray catArray = null;
            JSONArray subCatArray = null;
            if (Objects.equals(response.opt("status"), 200)) {
                try {
                    catArray = response.getJSONArray("data");

                    // for category
                    for (int i = 0; i < catArray.length(); i++) {
                        try {
                            Category category = MySingleton.getGson().fromJson(catArray.getJSONObject(i).toString(), Category.class);
                            categoryList.add(category);
                            categoryID.add(category.getId());
                            categoryName.add(category.getName());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryName);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sprCategory.setAdapter(aa);

        });
    }
}