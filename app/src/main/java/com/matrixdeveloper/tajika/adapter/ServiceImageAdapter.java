package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.ServiceImageModel;

import java.util.List;

public class ServiceImageAdapter extends RecyclerView.Adapter<ServiceImageAdapter.viewHolder> {
    private Context ctx;
    private List<ServiceImageModel> serviceImageModelList;

    public ServiceImageAdapter(Context ctx, List<ServiceImageModel> serviceImageModelList) {
        this.ctx = ctx;
        this.serviceImageModelList = serviceImageModelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_provider_images, parent, false);
        return new ServiceImageAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final ServiceImageModel serviceImageModel = serviceImageModelList.get(position);
        Glide.with(ctx).load(serviceImageModel.getServiceImageUrl()).placeholder(R.drawable.app_logo).into(holder.serviceProviderImage);
    }

    @Override
    public int getItemCount() {
        return serviceImageModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView serviceProviderImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            serviceProviderImage = itemView.findViewById(R.id.iv_serviceImage);
        }
    }
}
