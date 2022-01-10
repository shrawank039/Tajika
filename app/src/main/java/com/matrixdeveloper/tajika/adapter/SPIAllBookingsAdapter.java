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

import java.util.List;

public class SPIAllBookingsAdapter extends RecyclerView.Adapter<SPIAllBookingsAdapter.viewHolder> {

    private final Context ctx;
    private final List<SPIAllBookingsModel> listdata;

    public SPIAllBookingsAdapter(Context ctx, List<SPIAllBookingsModel> listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_completed_or_pending, parent, false);
        return new SPIAllBookingsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SPIAllBookingsModel myListData = listdata.get(position);
        holder.bookingID.setText("Booking id: " + myListData.getBookingId());
        holder.customerName.setText(myListData.getCustomerName());
        holder.serviceDate.setText(myListData.getServiceDate());
        holder.serviceType.setText(myListData.getServiceType());
        holder.completedOn.setText(myListData.getServiceTime());
        holder.bookingStatus.setText(myListData.getStatus());

        if (myListData.getStatus().equals("Pending")) {
            holder.bookingStatus.setTextColor(ctx.getResources().getColor(R.color.light_red));
        } else if (myListData.getStatus().equals("Ongoing")) {
            holder.bookingStatus.setTextColor(ctx.getResources().getColor(R.color.golden));
        } else if (myListData.getStatus().equals("Completed")) {
            holder.bookingStatus.setTextColor(ctx.getResources().getColor(R.color.light_green));
        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView bookingID, customerName, serviceDate, serviceType, bookingStatus, completedOn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            bookingID = itemView.findViewById(R.id.txt_bookingID);
            customerName = itemView.findViewById(R.id.txt_customerName);
            serviceDate = itemView.findViewById(R.id.txt_serviceDate);
            serviceType = itemView.findViewById(R.id.txt_serviceType);
            bookingStatus = itemView.findViewById(R.id.txt_bookingStatus);
            completedOn = itemView.findViewById(R.id.txt_completedOn);
        }
    }
}
