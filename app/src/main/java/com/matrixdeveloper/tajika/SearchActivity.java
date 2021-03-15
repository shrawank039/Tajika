package com.matrixdeveloper.tajika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.matrixdeveloper.tajika.model.ServiceList;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputSearch = findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                getServiceList(String.valueOf(cs));
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

    }

    private void getServiceList(String key) {

        JSONObject data = new JSONObject();
        try {
            data.put("key", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getApplicationContext(), ServiceNames.FILTER_SERVICE_LIST, data, response -> {


            JSONArray jsonarray = null;
            try {

                jsonarray = response.getJSONArray("data");

                for (int i = 0; i < jsonarray.length(); i++) {

//                    try {
//
//                        ServiceList serviceList = gson.fromJson(jsonarray.getJSONObject(i).toString(), ServiceList.class);
//
//                        serviceLists.add(serviceList);
//
//                        mAdapter.notifyDataSetChanged();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
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