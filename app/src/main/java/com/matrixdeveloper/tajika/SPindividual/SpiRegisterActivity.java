package com.matrixdeveloper.tajika.SPindividual;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiRegisterActivity extends AppCompatActivity {

    ViewFlipper regViewFlipper;
    Button nextToBusinessDetails, nextToDocumentUpload, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_register);
        regViewFlipper = findViewById(R.id.vf_regViewFlipper);
        nextToBusinessDetails = regViewFlipper.findViewById(R.id.btn_nextToBusinessDetails);
        nextToDocumentUpload = regViewFlipper.findViewById(R.id.btn_nexttoDocumentDetails);
        submit = regViewFlipper.findViewById(R.id.btn_submit);

        initListeners();
    }

    private void initListeners() {
        nextToBusinessDetails.setOnClickListener(view -> regViewFlipper.showNext());
        nextToDocumentUpload.setOnClickListener(view -> regViewFlipper.showNext());
        submit.setOnClickListener(view -> Toast.makeText(SpiRegisterActivity.this, "Submit Clicked !!", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        int displayedChild = regViewFlipper.getDisplayedChild();
        if (displayedChild>0) {
            regViewFlipper.setDisplayedChild(displayedChild-1);
        }
        else{
            super.onBackPressed();
        }
    }
}