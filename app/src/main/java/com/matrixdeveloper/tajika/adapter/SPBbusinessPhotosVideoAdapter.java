package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPbusiness.SpbEditProfileActivity;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;

import java.util.List;

public class SPBbusinessPhotosVideoAdapter extends RecyclerView.Adapter<SPBbusinessPhotosVideoAdapter.viewHolder> {

    private final Context ctx;
    private List<SPBbusinessPhotosVideosModel> imageList;
    private String type;

    public SPBbusinessPhotosVideoAdapter(Context ctx, List<SPBbusinessPhotosVideosModel> imageList, String type) {
        this.ctx = ctx;
        this.imageList = imageList;
        this.type = type;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spb_business_photos_videos, parent, false);
        return new SPBbusinessPhotosVideoAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SPBbusinessPhotosVideosModel list = imageList.get(position);
        Glide.with(ctx).load(list.getImageUrl()).into(holder.businessPhotos);

        if (position == 0 && type.equals("editProfile")) {
            holder.deletePhoto.setVisibility(View.GONE);
            holder.businessPhotos.setOnClickListener(v -> ((SpbEditProfileActivity) ctx).openPhotoChooser(1));
        }

        holder.deletePhoto.setOnClickListener(v -> Toast.makeText(ctx, "Deleted!!", Toast.LENGTH_SHORT).show());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView businessPhotos, deletePhoto;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            businessPhotos = itemView.findViewById(R.id.iv_businessPhotosVideos);
            deletePhoto = itemView.findViewById(R.id.iv_deletePhotosVideos);
        }
    }
}
