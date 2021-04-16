package com.matrixdeveloper.tajika.SPbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiHomeActivity;
import com.matrixdeveloper.tajika.model.Register;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Global;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SpbRegisterActivity extends AppCompatActivity {

    private ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, submit;
    private static final String TAG = "SpbRegisterAct";
    private String name, phone, email, pass, Cpass,service_area,business_categories,service_description,year_of_experience,
            bussiness_link,minimum_charge,education_level,passportnumber,upload_passportid,professional_qualification,
            qualification_certification,latitude,longitude;
    private EditText edtName, edtPhone, edtEmail, edtPass, edtCPass;
    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_register);

        prf = new PrefManager(this);
        regViewFlipper = findViewById(R.id.vf_regBViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);

        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        submit.setOnClickListener(view -> Toast.makeText(this, "Submit Clicked", Toast.LENGTH_SHORT).show());
    }

    private void registerSubmit() {
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        Cpass = edtCPass.getText().toString();

        if (pass.equals(Cpass)) {
            JSONObject data = new JSONObject();
            try {
                data.put("name", name);
                data.put("phone", phone);
                data.put("email", email);
                data.put("password", pass);
                data.put("password_confirmation", pass);
                data.put("role", "3");
                data.put("service_area", service_area);
                data.put("business_categories", business_categories);
                data.put("service_description", service_description);
                data.put("year_of_experience", year_of_experience);
                data.put("bussiness_link", bussiness_link);
                data.put("minimum_charge", minimum_charge);
                data.put("education_level", education_level);
                data.put("passportnumber", passportnumber);
                data.put("upload_passportid", upload_passportid);
                data.put("professional_qualification", professional_qualification);
                data.put("qualification_certification", qualification_certification);
                data.put("latitude", latitude);
                data.put("longitude", longitude);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiCall.postMethod(this, ServiceNames.REGISTER_SERVICE_PROVIDER_BUSI, data, response -> {

                Utils.log(TAG, response.toString());
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_SHORT).show();

                try {

                    Register register = MySingleton.getGson().fromJson(response.getJSONObject("data").toString(), Register.class);

                    prf.setString(Global.user_id, register.getId().toString());
                    prf.setString(Global.token, register.getToken());
                    prf.setString(Global.role, register.getRoles().toString());
                    prf.setString(Global.email, register.getEmail());

                    startActivity(new Intent(this, SpiHomeActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        } else {
            Toast.makeText(this, "Password did't match", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        int displayedChild = regViewFlipper.getDisplayedChild();
        if (displayedChild > 0) {
            regViewFlipper.setDisplayedChild(displayedChild - 1);
        } else {
            super.onBackPressed();
        }
    }
}