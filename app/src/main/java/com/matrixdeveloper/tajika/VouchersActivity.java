package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.VoucherAdapter;
import com.matrixdeveloper.tajika.model.VoucherModel;

public class VouchersActivity extends AppCompatActivity {

    private RecyclerView voucherRecyclerview;
    private VoucherAdapter voucherAdapter;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers);

        backPress.setOnClickListener(view -> VouchersActivity.super.onBackPressed());

        VoucherModel[] voucherModels = new VoucherModel[]{
                new VoucherModel(1, 0, "20% flat on all order above 500", "Minimum order value of 1000", "Valid till 22 December 2020", "XYZ!Z#MF$NS"),
                new VoucherModel(2, 1, "50% flat on all order above 500", "Minimum order value of 1000", "Valid till 22 December 2020", "XYZ!Z#MF$NS"),
                new VoucherModel(3, 2, "30% flat on all order above 500", "Minimum order value of 1000", "Valid till 22 December 2020", "XYZ!Z#MF$NS"),
                new VoucherModel(4, 3, "60% flat on all order above 500", "Minimum order value of 1000", "Valid till 22 December 2020", "XYZ!Z#MF$NS"),

        };

        voucherRecyclerview = findViewById(R.id.recView_vouchers);
        voucherAdapter = new VoucherAdapter(this, voucherModels);
        voucherRecyclerview.setHasFixedSize(true);
        voucherRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        voucherRecyclerview.setAdapter(voucherAdapter);
    }
}