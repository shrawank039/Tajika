package com.matrixdeveloper.tajika.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.Model.CoinsWalletModel;
import com.matrixdeveloper.tajika.R;

public class CoinsWalletAdapter extends RecyclerView.Adapter<CoinsWalletAdapter.viewHolder> {

    private Context ctx;
    CoinsWalletModel[] coinsWalletModels;

    public CoinsWalletAdapter(Context ctx, CoinsWalletModel[] coinsWalletModels) {
        this.ctx = ctx;
        this.coinsWalletModels = coinsWalletModels;
    }

    @NonNull
    @Override
    public CoinsWalletAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coins_wallet, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsWalletAdapter.viewHolder holder, int position) {
        final CoinsWalletModel coinsWalletModel = coinsWalletModels[position];
        holder.header.setText(coinsWalletModel.getHeader());
        holder.coins.setText("Coins: " + coinsWalletModel.getCoins());
        holder.date.setText("Date: " + coinsWalletModel.getDate());

        if (coinsWalletModel.getDcStatus() == 1) {
            holder.debited.setVisibility(View.VISIBLE);
            holder.credited.setVisibility(View.GONE);
        } else {
            holder.debited.setVisibility(View.GONE);
            holder.credited.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return coinsWalletModels.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private TextView header, coins, date;
        private ImageView debited, credited;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.txt_header);
            coins = itemView.findViewById(R.id.txt_coins);
            date = itemView.findViewById(R.id.txt_date);
            debited = itemView.findViewById(R.id.iv_debited);
            credited = itemView.findViewById(R.id.iv_credited);
        }
    }
}
