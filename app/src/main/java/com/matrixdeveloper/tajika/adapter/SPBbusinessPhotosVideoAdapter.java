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
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;

public class SPBbusinessPhotosVideoAdapter extends RecyclerView.Adapter<SPBbusinessPhotosVideoAdapter.viewHolder> {

    private final Context ctx;
    SPBbusinessPhotosVideosModel[] photosVideosModels;

    public SPBbusinessPhotosVideoAdapter(Context ctx, SPBbusinessPhotosVideosModel[] photosVideosModels) {
        this.ctx = ctx;
        this.photosVideosModels = photosVideosModels;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spb_business_photos_videos, parent, false);
        return new SPBbusinessPhotosVideoAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final SPBbusinessPhotosVideosModel models = photosVideosModels[position];
        Glide.with(ctx).load(models.getImageUrl()).into(holder.businessPhotos);

    }

    @Override
    public int getItemCount() {
        return photosVideosModels.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView businessPhotos;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            businessPhotos = itemView.findViewById(R.id.txt_businessPhotosVideos);
        }
    }
}
