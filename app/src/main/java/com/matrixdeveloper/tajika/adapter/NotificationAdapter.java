package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.NotificationModel;
import com.matrixdeveloper.tajika.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    private Context ctx;
    NotificationModel[] notificationDetails;

    public NotificationAdapter(Context ctx, NotificationModel[] notificationDetails) {
        this.ctx = ctx;
        this.notificationDetails = notificationDetails;
    }

    @NonNull
    @Override
    public NotificationAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {
        final NotificationModel notificationModel = notificationDetails[position];
        holder.notificationHeader.setText(notificationModel.getNotificationHeader());
        holder.notificationContent.setText(notificationModel.getNotificationContent());
        holder.notificationFooter.setText(notificationModel.getNotificationFooter());

        holder.clearNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Deleted !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationDetails.length;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        private ImageView notificationImage,clearNotification;
        private TextView notificationHeader,notificationContent,notificationFooter;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            notificationImage=itemView.findViewById(R.id.iv_notiImage);
            clearNotification=itemView.findViewById(R.id.iv_clearNotification);
            notificationHeader=itemView.findViewById(R.id.txt_notiHeader);
            notificationContent=itemView.findViewById(R.id.txt_notiContent);
            notificationFooter=itemView.findViewById(R.id.txt_notiFooter);

        }
    }
}
