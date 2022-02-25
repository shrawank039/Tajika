package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.SPIAllBookingsModel;
import com.matrixdeveloper.tajika.model.SPIRecentTransactionModel;

import java.util.List;

public class SPIRecentTransactionAdapter extends RecyclerView.Adapter<SPIRecentTransactionAdapter.viewHolder> {
    private final Context ctx;
    private final List<SPIRecentTransactionModel> listdata;

    public SPIRecentTransactionAdapter(Context ctx, List<SPIRecentTransactionModel> listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_transaction, parent, false);
        return new SPIRecentTransactionAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SPIRecentTransactionModel myListData = listdata.get(position);
        holder.transactionID.setText(myListData.getTranscationId());
        holder.transactionDate.setText(myListData.getSubmitDate());
        holder.debitAmount.setText("Ksh "+myListData.getAmount());

        if (myListData.getType().equals("credit"))
            holder.transactionType.setText("Credit amount:");

        if (myListData.getBalanceType().equals("EARNINGBALANCE")){
            holder.txtDate.setText("Order date:");
            holder.txtId.setText("Order id:");
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView transactionID;
        private final TextView transactionDate;
        private final TextView debitAmount, transactionType, txtDate, txtId;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            transactionID=itemView.findViewById(R.id.txt_txnID);
            transactionDate=itemView.findViewById(R.id.txt_txnDate);
            debitAmount=itemView.findViewById(R.id.txt_debitAmount);
            transactionType=itemView.findViewById(R.id.txt_transactionType);
            txtDate = itemView.findViewById(R.id.txt_order_date);
            txtId = itemView.findViewById(R.id.txt_order_id);
        }
    }
}
