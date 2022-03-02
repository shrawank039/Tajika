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
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;

import org.json.JSONException;
import org.json.JSONObject;

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

        if (type.equals("profile")) {
            holder.deletePhoto.setVisibility(View.GONE);
        }

        holder.deletePhoto.setOnClickListener(v -> {
            imageList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, imageList.size());

           // Toast.makeText(ctx, ""+list.getId(), Toast.LENGTH_SHORT).show();
            if (!list.getId().equals("0")) {
                deleteImg(list.getId());
            }
        });
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

    public void deleteImg(String id) {
        JSONObject data = new JSONObject();
        try {
            data.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(ctx, ServiceNames.DELETE_SERVICE_IMAGE, data, response -> {
            if (response.optString("status").equals("200")) {
                Toast.makeText(ctx, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
