package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.model.FaqModel;
import com.matrixdeveloper.tajika.R;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    private Context ctx;
    List<FaqModel> listdata;

    public FaqAdapter(Context ctx, List<FaqModel> listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final FaqModel myListData = listdata.get(position);
        holder.faqQuestion.setText(myListData.getQuestion());
        holder.faqAnswer.setText(myListData.getAnswer());
        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.faqAnswer.getVisibility()==View.GONE) {
                    holder.faqAnswer.setVisibility(View.VISIBLE);
                    holder.dropDown.setRotation(180);
                }else{
                    holder.faqAnswer.setVisibility(View.GONE);
                    holder.dropDown.setRotation(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView faqQuestion,faqAnswer;
        private LinearLayout expand;
        private ImageView dropDown;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            faqQuestion=itemView.findViewById(R.id.txt_faqQuestion);
            faqAnswer=itemView.findViewById(R.id.txt_faqAnswer);
            expand=itemView.findViewById(R.id.ll_expandMore);
            dropDown=itemView.findViewById(R.id.iv_dropDown);
        }
    }

}
