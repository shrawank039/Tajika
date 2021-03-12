package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.ConversationListModel;
import com.matrixdeveloper.tajika.R;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.viewModel> {

    private Context ctx;
    private ConversationListModel[] conversationListModels;

    public ConversationListAdapter(Context ctx, ConversationListModel[] conversationListModels) {
        this.ctx = ctx;
        this.conversationListModels = conversationListModels;
    }

    @NonNull
    @Override
    public viewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_list, parent, false);
        return new ConversationListAdapter.viewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewModel holder, int position) {
        final ConversationListModel listModel = conversationListModels[position];
        holder.serviceText.setText(listModel.getHeader());
        holder.serviceDate.setText(listModel.getTime());
    }

    @Override
    public int getItemCount() {
        return conversationListModels.length;
    }

    public static class viewModel extends RecyclerView.ViewHolder {
        private ImageView serviceImage;
        private TextView serviceText, serviceDate;

        public viewModel(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.iv_serviceImage);
            serviceText = itemView.findViewById(R.id.txt_serviceType);
            serviceDate = itemView.findViewById(R.id.txt_serviceDate);
        }
    }
}
