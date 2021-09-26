package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.BookingModel;
import com.matrixdeveloper.tajika.model.ServiceRequestList;

import java.util.List;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.viewHolder> {
    private final Context ctx;
    List<ServiceRequestList> serviceRequestLists;

    public MyBookingAdapter(Context ctx, List<ServiceRequestList> serviceRequestLists) {
        this.ctx = ctx;
        this.serviceRequestLists = serviceRequestLists;
    }

    @NonNull
    @Override
    public MyBookingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_or_requested, parent, false);
        return new MyBookingAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingAdapter.viewHolder holder, int position) {
        ServiceRequestList serviceRequestList = serviceRequestLists.get(position);
        holder.serviceName.setText("Name: " + serviceRequestList.getServiceName());
        holder.serviceAddress.setText("Address: " + serviceRequestList.getAddress());
        holder.serviceType.setText("Service type: " + serviceRequestList.getServiceType());
        holder.serviceStatus.setText("Status: " + serviceRequestList.getStatus());

        Glide.with(ctx).load(serviceRequestList.getImageUrl()).into(holder.serviceImage);
    }

    @Override
    public int getItemCount() {
        return serviceRequestLists.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView serviceImage;
        private final TextView serviceName;
        private final TextView serviceAddress;
        private final TextView serviceType;
        private final TextView serviceStatus;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.iv_serviceImage);
            serviceName = itemView.findViewById(R.id.txt_serviceName);
            serviceAddress = itemView.findViewById(R.id.txt_serviceAddress);
            serviceType = itemView.findViewById(R.id.txt_serviceType);
            serviceStatus = itemView.findViewById(R.id.txt_bookingStatus);
        }
    }
}
