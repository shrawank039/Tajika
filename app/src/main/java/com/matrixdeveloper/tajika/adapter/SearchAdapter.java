package com.matrixdeveloper.tajika.adapter;

import static com.matrixdeveloper.tajika.network.ServiceNames.PRODUCTION_API;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.LocationSelectorActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SearchActivity;
import com.matrixdeveloper.tajika.model.Category;
import com.matrixdeveloper.tajika.model.SubCategory;
import com.matrixdeveloper.tajika.utils.RecyclerTouchListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<Category> categories;
    private RecyclerView rvSubcategory;
    private SubCatAdapter subCatAdapter;
    private List<SubCategory> subCategories = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView title;


        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txt_itmCategory);
            rvSubcategory = view.findViewById(R.id.rv_subcat);
        }
    }


    public SearchAdapter(Context context, List<Category> categories) {
        ctx = context;
        this.categories = categories;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Category category = categories.get(position);

        subCategories = category.getSubCategory();
        subCatAdapter = new SubCatAdapter(ctx.getApplicationContext(), subCategories);

        holder.title.setText(category.getName());

        rvSubcategory.setHasFixedSize(true);

        rvSubcategory.setLayoutManager(new LinearLayoutManager(ctx));
        rvSubcategory.setItemAnimator(new DefaultItemAnimator());
        rvSubcategory.setAdapter(subCatAdapter);

        rvSubcategory.addOnItemTouchListener(new RecyclerTouchListener(ctx, rvSubcategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SubCategory subCategory = subCategories.get(position);

                ctx.startActivity(new Intent(ctx, LocationSelectorActivity.class)
                        .putExtra("service_name", subCategory.getServiceName())
                        .putExtra("service_id", String.valueOf(subCategory.getId())));

             //   Toast.makeText(ctx, subCategory.getServiceName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public int getItemCount() {
            return categories.size();
    }
}
