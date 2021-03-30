package com.matrixdeveloper.tajika.SPbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.matrixdeveloper.tajika.R;

public class SpbRegisterActivity extends AppCompatActivity {

    private ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_register);

        regViewFlipper = findViewById(R.id.vf_regBViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);

        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        submit.setOnClickListener(view -> Toast.makeText(this, "Submit Clicked", Toast.LENGTH_SHORT).show());
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