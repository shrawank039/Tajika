package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpiAddCreditActivity extends AppCompatActivity {

    private ImageView backPress;
    private Button submit;
    private EditText addCredit;
    private String TAG = "AddCreditAct";
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_add_credit);

        pref = new PrefManager(this);
        backPress = findViewById(R.id.iv_backPress);
        submit = findViewById(R.id.btn_submit);
        addCredit = findViewById(R.id.edt_creditAmount);

        backPress.setOnClickListener(view -> SpiAddCreditActivity.super.onBackPressed());
        submit.setOnClickListener(view -> initiateAddingCredit());
    }

    private void initiateAddingCredit() {
        String creditAmount = addCredit.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("amount", creditAmount);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(this, ServiceNames.ADD_CREDIT, data, response -> {

            Utils.log(TAG, response.toString());
            if (response.optString("status").equals("200")) {
                Utils.toast(SpiAddCreditActivity.this, response.optString("message"));
                finish();
            } else {
                Utils.toast(SpiAddCreditActivity.this, response.optString("message"));
            }
        });

    }
}