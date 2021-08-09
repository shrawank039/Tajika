package com.matrixdeveloper.tajika.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.RedeemCoinModel;
import com.matrixdeveloper.tajika.R;

public class RedeemCoinAdapter extends RecyclerView.Adapter<RedeemCoinAdapter.viewHolder> {
    private Context ctx;
    RedeemCoinModel[] redeemCoinModels;

    public RedeemCoinAdapter(Context ctx, RedeemCoinModel[] redeemCoinModels) {
        this.ctx = ctx;
        this.redeemCoinModels = redeemCoinModels;
    }

    @NonNull
    @Override
    public RedeemCoinAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem_coin, parent, false);
        return new RedeemCoinAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedeemCoinAdapter.viewHolder holder, int position) {
        final RedeemCoinModel redeemCoinModel = redeemCoinModels[position];
        holder.redeemRating.setRating(redeemCoinModel.getRedeemRating());
        holder.reedemHeading.setText(redeemCoinModel.getRedeemHeader());
        holder.redeemReqCoin.setText("Required coin: " + redeemCoinModel.getRedeemReqCoin());
        holder.redeemValidity.setText(redeemCoinModel.getRedeemValidity());

        holder.redeemNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_redeem);

                ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return redeemCoinModels.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private RatingBar redeemRating;
        private TextView reedemHeading, redeemReqCoin, redeemValidity, redeemNow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            redeemRating = itemView.findViewById(R.id.rating_redeemRating);
            reedemHeading = itemView.findViewById(R.id.txt_redeemHeading);
            redeemReqCoin = itemView.findViewById(R.id.txt_redeemReqCoin);
            redeemValidity = itemView.findViewById(R.id.txt_redeemValidity);
            redeemNow = itemView.findViewById(R.id.txt_redeemNow);
        }
    }
}
