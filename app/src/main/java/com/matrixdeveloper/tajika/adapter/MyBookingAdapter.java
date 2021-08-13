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

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.viewHolder> {
    private Context ctx;
    BookingModel[] bookingModels;

    public MyBookingAdapter(Context ctx, BookingModel[] bookingModels) {
        this.ctx = ctx;
        this.bookingModels = bookingModels;
    }

    @NonNull
    @Override
    public MyBookingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booked_or_requested, parent, false);
        return new MyBookingAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingAdapter.viewHolder holder, int position) {
        final BookingModel bookingModel = bookingModels[position];
        holder.serviceName.setText("Name: " + bookingModel.getServiceName());
        holder.serviceAddress.setText("Address: " + bookingModel.getServiceAddress());
        holder.serviceType.setText("Service type: " + bookingModel.getServiceType());
        holder.serviceStatus.setText("Status: " + bookingModel.getServiceStatus());

        Glide.with(ctx).load(bookingModel.getServiceImage()).into(holder.serviceImage);
    }

    @Override
    public int getItemCount() {
        return bookingModels.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private ImageView serviceImage;
        private TextView serviceName, serviceAddress, serviceType, serviceStatus;

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
