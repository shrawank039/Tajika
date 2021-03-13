package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.ServiceList;
import com.matrixdeveloper.tajika.utils.PrefManager;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<ServiceList> serviceLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView title, message;
        ImageView imageView;


        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txt_title);
            message = view.findViewById(R.id.txt_message);
            imageView = view.findViewById(R.id.image);
        }
    }


    public ServiceAdapter(Context context, List<ServiceList> serviceLists) {
        ctx = context;
        this.serviceLists = serviceLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ServiceList serviceList = serviceLists.get(position);

        holder.title.setText(serviceList.getServiceName());
        holder.message.setText(serviceList.getServiceDescription());
        Glide.with(ctx)
                .load(serviceList.getServiceImage())
                .placeholder(R.drawable.plumbing)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return serviceLists.size();
    }
}
