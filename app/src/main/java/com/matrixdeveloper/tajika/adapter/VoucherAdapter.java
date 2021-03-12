package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.VoucherModel;
import com.matrixdeveloper.tajika.R;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.viewHolder> {

    private Context ctx;
    VoucherModel[] voucherModels;

    public VoucherAdapter(Context ctx, VoucherModel[] voucherModels) {
        this.ctx = ctx;
        this.voucherModels = voucherModels;
    }

    @NonNull
    @Override
    public VoucherAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vouchers, parent, false);
        return new VoucherAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.viewHolder holder, int position) {
        final VoucherModel voucherModel = voucherModels[position];
        holder.voucherHeader.setText(voucherModel.getVoucherHeader());
        holder.voucherValidity.setText(voucherModel.getVoucherValidity());
        holder.voucherMinOrder.setText(voucherModel.getVoucherMinOrder());
        holder.voucherCode.setText("Code: " + voucherModel.getVoucherCode());
        holder.voucherRating.setRating(voucherModel.getRating());

        holder.voucherCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, voucherModel.getVoucherCode() + " Copied !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return voucherModels.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private RatingBar voucherRating;
        private TextView voucherHeader, voucherMinOrder, voucherValidity, voucherCode, voucherCopyCode;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            voucherRating = itemView.findViewById(R.id.rating_voucherRating);
            voucherHeader = itemView.findViewById(R.id.txt_voucherHeading);
            voucherMinOrder = itemView.findViewById(R.id.txt_voucherMinOrder);
            voucherValidity = itemView.findViewById(R.id.txt_voucherValidity);
            voucherCode = itemView.findViewById(R.id.txt_voucherCode);
            voucherCopyCode = itemView.findViewById(R.id.txt_coupyCode);
        }
    }
}
