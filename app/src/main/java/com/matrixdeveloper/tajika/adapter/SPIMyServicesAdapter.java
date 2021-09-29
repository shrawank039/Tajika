package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpbMyServicesActivity;
import com.matrixdeveloper.tajika.SPindividual.SpiMyServicesActivity;
import com.matrixdeveloper.tajika.model.SPIMyServicesModel;

import java.util.List;

public class SPIMyServicesAdapter extends RecyclerView.Adapter<SPIMyServicesAdapter.viewHolder> {

    private final Context ctx;
    List<SPIMyServicesModel> listdata;
    private final int serviceType;

    public SPIMyServicesAdapter(Context ctx, List<SPIMyServicesModel> listdata, int serviceType) {
        this.ctx = ctx;
        this.listdata = listdata;
        this.serviceType = serviceType;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (serviceType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spb_my_goods, parent, false);
        } else if (serviceType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spi_my_services, parent, false);
        }
        return new SPIMyServicesAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final SPIMyServicesModel myListData = listdata.get(position);

        if (serviceType == 1) {
            holder.serviceNumber.setText("Service #" + myListData.getId());
            holder.serviceCategory.setText(myListData.getCategoryName());
            holder.serviceExperience.setText(myListData.getExperience());
            holder.serviceMinCharge.setText(myListData.getMincharge());

            holder.serviceDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SpiMyServicesActivity) ctx).deleteService(myListData.getId());
                }
            });
        } else if (serviceType == 0) {
            holder.goodsNumber.setText("Service #" + myListData.getId());
            holder.goodsCategory.setText(myListData.getCategoryName());
            holder.goodsSubCategory.setText(myListData.getSubcategoryName());
            holder.goodsPrice.setText(myListData.getPrice());
            holder.goodsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SpbMyServicesActivity) ctx).deleteGoods(myListData.getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView serviceNumber, goodsNumber, serviceCategory, serviceExperience, serviceMinCharge, goodsCategory, goodsSubCategory, goodsPrice;
        private ImageView serviceEdit, serviceDelete, goodsDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            if (serviceType == 1) {
                serviceNumber = itemView.findViewById(R.id.txt_serviceNumber);
                serviceCategory = itemView.findViewById(R.id.txt_serviceCategory);
                serviceExperience = itemView.findViewById(R.id.txt_serviceExperience);
                serviceMinCharge = itemView.findViewById(R.id.txt_serviceMinCharge);
                serviceEdit = itemView.findViewById(R.id.iv_serviceEdit);
                serviceDelete = itemView.findViewById(R.id.iv_serviceDelete);
            } else if (serviceType == 0) {
                goodsNumber = itemView.findViewById(R.id.txt_goodsNumber);
                goodsCategory = itemView.findViewById(R.id.txt_category);
                goodsSubCategory = itemView.findViewById(R.id.txt_subCategory);
                goodsPrice = itemView.findViewById(R.id.txt_goodsPrice);
                goodsDelete = itemView.findViewById(R.id.iv_goodsDelete);
            }
        }
    }
}
