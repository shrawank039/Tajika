package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.LocationSelectorActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.SubscriptionModel;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.viewHolder> {
    private final Context ctx;
    private final List<SubscriptionModel> subscriptionModels;

    public SubscriptionAdapter(Context ctx, List<SubscriptionModel> subscriptionModels) {
        this.ctx = ctx;
        this.subscriptionModels = subscriptionModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_premium_packages, parent, false);
        return new SubscriptionAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SubscriptionModel subscriptionModel = subscriptionModels.get(position);
        holder.subType.setText(subscriptionModel.getName());
        holder.subAmount.setText("Rs. " + subscriptionModel.getAmount());
        holder.subDays.setText("Validity: " + subscriptionModel.getNoOfDays() + " days");
        holder.cvSelectPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctx instanceof LocationSelectorActivity) {
                    ((LocationSelectorActivity)ctx).showSubscriptionAlert();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subscriptionModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView subType;
        private final TextView subAmount;
        private final TextView subDays;
        private final CardView cvSelectPackage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            subType = itemView.findViewById(R.id.txt_subType);
            subAmount = itemView.findViewById(R.id.txt_subAmount);
            subDays = itemView.findViewById(R.id.txt_subDays);
            cvSelectPackage = itemView.findViewById(R.id.cv_selectPackage);
        }
    }
}
