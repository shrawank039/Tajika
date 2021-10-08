package com.matrixdeveloper.tajika.adapter;

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

import com.matrixdeveloper.tajika.BookingDetailsActivity;
import com.matrixdeveloper.tajika.NotificationActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiServiceRequestDetailsActivity;
import com.matrixdeveloper.tajika.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    private final Context ctx;
    List<NotificationModel> notificationModelList;

    public NotificationAdapter(Context ctx, List<NotificationModel> notificationModelList) {
        this.ctx = ctx;
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public NotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {

        final NotificationModel notificationModel = notificationModelList.get(position);
        holder.notificationHeader.setText(notificationModel.getTitle());
        holder.notificationContent.setText(notificationModel.getMessage());
        holder.notificationFooter.setText(notificationModel.getActiontext());

        holder.clearNotification.setOnClickListener(view -> ((NotificationActivity) ctx).deleteNotification(String.valueOf(notificationModel.getId())));
        holder.notificationBody.setOnClickListener(view -> ctx.startActivity(new Intent(ctx, SpiServiceRequestDetailsActivity.class)
                .putExtra("id", notificationModel.getId().toString())));
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private final ImageView clearNotification;
        private final TextView notificationHeader;
        private final TextView notificationContent;
        private final TextView notificationFooter;
        private final LinearLayout notificationBody;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            clearNotification = itemView.findViewById(R.id.iv_clearNotification);
            notificationHeader = itemView.findViewById(R.id.txt_notiHeader);
            notificationContent = itemView.findViewById(R.id.txt_notiContent);
            notificationFooter = itemView.findViewById(R.id.txt_notiFooter);
            notificationBody = itemView.findViewById(R.id.ll_notification);

        }
    }
}
