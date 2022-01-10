package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiHomeActivity;
import com.matrixdeveloper.tajika.SPindividual.SpiServiceRequestDetailsActivity;
import com.matrixdeveloper.tajika.model.ServiceRequestList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewRequestAdapter extends RecyclerView.Adapter<NewRequestAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<ServiceRequestList> serviceLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView requestId, jobDate, jobType, accept, viewInfo;

        MyViewHolder(View view) {
            super(view);

            requestId = view.findViewById(R.id.txt_request_id);
            jobDate = view.findViewById(R.id.txt_job_date);
            jobType = view.findViewById(R.id.txt_job_type);
            accept = view.findViewById(R.id.txt_accept);
            viewInfo = view.findViewById(R.id.txt_view_info);
        }
    }


    public NewRequestAdapter(Context context, List<ServiceRequestList> serviceLists, int type) {
        ctx = context;
        this.serviceLists = serviceLists;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_services_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ServiceRequestList serviceList = serviceLists.get(position);

        holder.requestId.setText(serviceList.getRequestId());
        holder.jobDate.setText(serviceList.getServiceDate());
        holder.jobType.setText(serviceList.getServiceType());


        holder.accept.setOnClickListener(v -> {
            ((SpiHomeActivity) ctx).getServiceDetails(serviceList.getId().toString(), "Accept");
        });

        holder.viewInfo.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, SpiServiceRequestDetailsActivity.class);
            intent.putExtra("ser_id", serviceList.getId().toString());
            ctx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return serviceLists.size();
    }
}
