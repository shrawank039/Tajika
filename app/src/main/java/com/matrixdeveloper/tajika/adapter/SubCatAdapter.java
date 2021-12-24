package com.matrixdeveloper.tajika.adapter;

import static com.matrixdeveloper.tajika.network.ServiceNames.PRODUCTION_API;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.LocationSelectorActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.model.Category;
import com.matrixdeveloper.tajika.model.SubCategory;
import com.matrixdeveloper.tajika.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<SubCategory> subCategories;
    private String TAG = "SearchAda";

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView title, message;
        ImageView imageView;
        LinearLayout llItem;


        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txt_title);
            message = view.findViewById(R.id.txt_message);
            imageView = view.findViewById(R.id.image);
            llItem = view.findViewById(R.id.ll_item);
        }
    }


    public SubCatAdapter(Context context, List<SubCategory> subCategories) {
        ctx = context;
        this.subCategories = subCategories;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_subcategory, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SubCategory subCategory = subCategories.get(position);

        holder.title.setText(subCategory.getServiceName());
        Glide.with(ctx)
                .load(PRODUCTION_API + subCategory.getServiceImage())
                .placeholder(R.drawable.plumbing)
                .into(holder.imageView);

        holder.llItem.setOnClickListener(v -> {
                ctx.startActivity(new Intent(ctx, LocationSelectorActivity.class)
                        .putExtra("service_name", subCategory.getServiceName())
                        .putExtra("service_type", subCategory.getServiceType())
                        .putExtra("service_id", String.valueOf(subCategory.getId())));

            Utils.log(TAG, position+" "+subCategory.getServiceType()+" "+ subCategory.getId());
        });

    }

    @Override
    public int getItemCount() {
            return subCategories.size();
    }
}
