package com.matrixdeveloper.tajika.SPindividual;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.R;

public class SpiServiceAcceptActivity extends AppCompatActivity {

    private TextView completeJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_service_accept);
        completeJob = findViewById(R.id.txt_completeJob);
        completeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SpiServiceAcceptActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.job_completion_dialog);

                ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
                TextView yes = dialog.findViewById(R.id.txt_yes);
                TextView no = dialog.findViewById(R.id.txt_no);
                dialogButton.setOnClickListener(v -> dialog.dismiss());
                yes.setOnClickListener(v -> startActivity(new Intent(SpiServiceAcceptActivity.this, SpiServiceCompletedStatusActivity.class)));
                no.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            }
        });
    }
}