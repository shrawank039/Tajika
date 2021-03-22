package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.SPIMyServicesModel;

public class SPIMyServicesAdapter extends RecyclerView.Adapter<SPIMyServicesAdapter.viewHolder> {

    private Context ctx;
    private SPIMyServicesModel[] listdata;

    public SPIMyServicesAdapter(Context ctx, SPIMyServicesModel[] listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spi_my_services, parent, false);
        return new SPIMyServicesAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SPIMyServicesModel myListData = listdata[position];
        holder.serviceNumber.setText(myListData.getServiceNumber());
        holder.serviceCategory.setText(myListData.getServiceCategory());
        holder.serviceExperience.setText(myListData.getServiceExperience());
        holder.serviceMinCharge.setText(myListData.getServiceMinCharge());

        holder.serviceEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Service Position"+position+" Edit Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        holder.serviceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Service Position" + position + " delete Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private TextView serviceNumber,serviceCategory,serviceExperience,serviceMinCharge;
        private ImageView serviceEdit,serviceDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNumber=itemView.findViewById(R.id.txt_serviceNumber);
            serviceCategory=itemView.findViewById(R.id.txt_serviceCategory);
            serviceExperience=itemView.findViewById(R.id.txt_serviceExperience);
            serviceMinCharge=itemView.findViewById(R.id.txt_serviceMinCharge);
            serviceEdit=itemView.findViewById(R.id.iv_serviceEdit);
            serviceDelete=itemView.findViewById(R.id.iv_serviceDelete);
        }
    }
}
