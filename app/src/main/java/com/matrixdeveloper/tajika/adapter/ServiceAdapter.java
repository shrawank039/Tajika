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
import com.matrixdeveloper.tajika.model.SubCategory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.matrixdeveloper.tajika.network.ServiceNames.PRODUCTION_API;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<SubCategory> subCategories;
    private int type;

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


    public ServiceAdapter(Context context, List<SubCategory> subCategories, int type) {
        ctx = context;
        this.type = type;
        this.subCategories = subCategories;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (type == 0 || type == 2) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recomended_services, parent, false);
        } else if (type == 1) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.all_services, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SubCategory subCategory = subCategories.get(position);

        holder.title.setText(subCategory.getServiceName());
        holder.message.setText(subCategory.getServiceDescription());
        Glide.with(ctx)
                .load(PRODUCTION_API + subCategory.getServiceImage())
                .placeholder(R.drawable.plumbing)
                .into(holder.imageView);

        if (type == 2) {
            holder.message.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0 || type == 2) {
            if (subCategories.size() > 3) {
                return 3;
            } else {
                return subCategories.size();
            }
        } else {
            return subCategories.size();
        }
    }
}
