package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpiAddNewGoodsActivity;
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

public class SpiAddNewServiceActivity extends AppCompatActivity {

    private ImageView backPress;
    private TextView addNewGood;
    private final String TAG = "SpiAddNewServiceAct";
    private PrefManager pref;
    private EditText edtExp, edtMinCharge;
    private Spinner sprCategory, sprSubCategory;
    private Button saveService;

    int serviceCatID, serviceSubCatID;


    private List<String> categoryName = new ArrayList<>();
    private List<Integer> categoryID = new ArrayList<>();

    private List<String> subCategoryName = new ArrayList<>();
    private List<Integer> subCategoryID = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbadd_new_service);

        initViews();
        initListeners();

        getCatSubcat();

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

        backPress.setOnClickListener(v -> SpiAddNewServiceActivity.super.onBackPressed());

        addNewGood.setOnClickListener(v -> startActivity(new Intent(SpiAddNewServiceActivity.this, SpiAddNewGoodsActivity.class)));

        saveService.setOnClickListener(v -> {
            if(serviceCatID!=0){
                if(serviceSubCatID!=0){
                    addNewService();
                }else {
                    Utils.toast(getApplicationContext(),"Sub-Category filed required!");
                }
            }else {
                Utils.toast(getApplicationContext(),"Category filed required!");
            }
        });

    }

    private void initViews() {
        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        addNewGood = findViewById(R.id.txt_addGoodsInstead);
        edtExp = findViewById(R.id.edt_experience);
        edtMinCharge = findViewById(R.id.edt_minCharge);
        sprCategory = findViewById(R.id.spinner_category);
        sprSubCategory = findViewById(R.id.spinner_subCategory);
        saveService = findViewById(R.id.btn_saveService);
    }

    private void getCatSubcat() {

        JSONObject data = new JSONObject();
        try {
            data.put("service_type", "service");
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

    private void addNewService() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("name", String.valueOf(serviceCatID));
            data.put("sub_cat_id", String.valueOf(serviceSubCatID));
            data.put("mincharge", edtMinCharge.getText().toString());
            data.put("experience", edtExp.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ADD_NEW_SERVICE, data, response -> {
            Utils.log(TAG, response.toString());
            Utils.toast(this, response.optString("message"));
            if (response.optString("status").equals("200")){
                finish();
            }
        });

    }

}