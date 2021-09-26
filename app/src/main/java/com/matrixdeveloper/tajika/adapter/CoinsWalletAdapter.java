package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.CoinsWalletModel;
import com.matrixdeveloper.tajika.R;

import java.util.ArrayList;

public class CoinsWalletAdapter extends RecyclerView.Adapter<CoinsWalletAdapter.viewHolder> {

    private final Context ctx;
    ArrayList<CoinsWalletModel> coinsWalletModels;

    public CoinsWalletAdapter(Context ctx, ArrayList<CoinsWalletModel> coinsWalletModels) {
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
        final CoinsWalletModel coinsWalletModel = coinsWalletModels.get(position);
        holder.header.setText(coinsWalletModel.getUserdFor());
        holder.coins.setText("Coins: " + coinsWalletModel.getPoint());
        holder.date.setText("Date: " + coinsWalletModel.getDate());

        if (coinsWalletModel.getType().equals("debited")) {
            holder.debited.setVisibility(View.VISIBLE);
            holder.credited.setVisibility(View.GONE);
        } else {
            holder.debited.setVisibility(View.GONE);
            holder.credited.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return coinsWalletModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private final TextView header;
        private final TextView coins;
        private final TextView date;
        private final ImageView debited;
        private final ImageView credited;

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
