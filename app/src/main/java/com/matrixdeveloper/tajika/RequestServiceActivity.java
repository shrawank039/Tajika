package com.matrixdeveloper.tajika;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RequestServiceActivity extends AppCompatActivity implements
        View.OnClickListener {

    private String provider_id, service_name, service_id;
    private String TAG = "RequestServiceAct";
    private static PrefManager prf;
    private Button submitRequest;
    private EditText edtDate, edtTime, edtAmount, edtService, edtDescription;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        provider_id = getIntent().getStringExtra("provider_id");
        service_name = getIntent().getStringExtra("service_name");
        service_id = getIntent().getStringExtra("service_id");

        edtDate = findViewById(R.id.edt_date);
        edtTime = findViewById(R.id.edt_time);
        edtAmount = findViewById(R.id.edt_amount);
        edtService = findViewById(R.id.edt_service);
        edtDescription = findViewById(R.id.edt_description);
        submitRequest = findViewById(R.id.btn_submit);
        backPress = findViewById(R.id.iv_backPress);

        edtService.setText(service_name);
        prf = new PrefManager(this);

        edtDate.setOnClickListener(this);
        edtTime.setOnClickListener(this);
        submitRequest.setOnClickListener(this);

    }

    public void submitClick() {

        JSONObject data = new JSONObject();
        try {
            data.put("service_provider_id", provider_id);
            data.put("user_id", prf.getString("id"));
            data.put("service_date", edtDate.getText().toString());
            data.put("service_time", edtTime.getText().toString());
            data.put("service_type", service_id);
            data.put("willing_amount_pay", edtAmount.getText().toString());
            data.put("work_description", edtDescription.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiCall.postMethod(getApplicationContext(), ServiceNames.SUBMIT_SERVICE_REQUEST, data, response -> {

            Utils.log(TAG, response.toString());
            Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public void onClick(View v) {

        int mYear, mMonth, mDay, mHour, mMinute;

        if (v == edtDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            edtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == edtTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            edtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == submitRequest) {
            submitClick();
        }
        if (v == backPress) {
            super.onBackPressed();
        }
    }
}