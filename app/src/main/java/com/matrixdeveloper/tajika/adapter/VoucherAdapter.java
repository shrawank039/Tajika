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

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.VoucherList;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.viewHolder> {

    private final Context ctx;
    List<VoucherList> voucherLists;

    public VoucherAdapter(Context ctx, List<VoucherList> voucherLists) {
        this.ctx = ctx;
        this.voucherLists = voucherLists;
    }

    @NonNull
    @Override
    public VoucherAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vouchers, parent, false);
        return new VoucherAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.viewHolder holder, int position) {
        final VoucherList voucherModel = voucherLists.get(position);
        holder.voucherHeader.setText(voucherModel.getTitle());
        holder.voucherValidity.setText("Valid till: " + voucherModel.getValidDate());
        holder.voucherMinOrder.setText("Minimum Order: " + voucherModel.getMinOrder());
        holder.voucherCode.setText("Code: " + voucherModel.getCode());
        holder.voucherRating.setRating(3);

        holder.voucherCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, voucherModel.getCode() + " Copied !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return voucherLists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private final RatingBar voucherRating;
        private final TextView voucherHeader;
        private final TextView voucherMinOrder;
        private final TextView voucherValidity;
        private final TextView voucherCode;
        private final TextView voucherCopyCode;

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
