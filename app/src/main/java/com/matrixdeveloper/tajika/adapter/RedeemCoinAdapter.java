package com.matrixdeveloper.tajika.adapter;

import static com.matrixdeveloper.tajika.RedeemCoinActivity.prf;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.RedeemCoinActivity;
import com.matrixdeveloper.tajika.model.CoinsWalletModel;
import com.matrixdeveloper.tajika.model.RedeemCoinModel;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RedeemCoinAdapter extends RecyclerView.Adapter<RedeemCoinAdapter.viewHolder> {
    private Context ctx;
    ArrayList<RedeemCoinModel> redeemCoinModels;

    public RedeemCoinAdapter(Context ctx, ArrayList<RedeemCoinModel> redeemCoinModels) {
        this.ctx = ctx;
        this.redeemCoinModels = redeemCoinModels;
    }

    @NonNull
    @Override
    public RedeemCoinAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem_coin, parent, false);
        return new RedeemCoinAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedeemCoinAdapter.viewHolder holder, int position) {
        final RedeemCoinModel redeemCoinModel = redeemCoinModels.get(position);
        holder.redeemRating.setRating(2);
        holder.reedemHeading.setText(redeemCoinModel.getTitle());
        holder.redeemReqCoin.setText("Required coin: " + redeemCoinModel.getCoin());
        holder.redeemValidity.setText(redeemCoinModel.getValidDate());

        holder.redeemNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                try {
                    data.put("user_id",  prf.getString("id"));
                    data.put("reedom_id",redeemCoinModel.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ApiCall.postMethod(ctx, ServiceNames.REDEEM_COIN_TO_USER, data, response -> {

                    if(response.optString("status").equals("200")){

                        final Dialog dialog = new Dialog(ctx);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog_redeem);

                        ImageView dialogButton = dialog.findViewById(R.id.iv_dialogCancel);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                ((RedeemCoinActivity)ctx).finish();
                            }
                        });
                        dialog.show();
                    }

                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return redeemCoinModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private RatingBar redeemRating;
        private TextView reedemHeading, redeemReqCoin, redeemValidity, redeemNow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            redeemRating = itemView.findViewById(R.id.rating_redeemRating);
            reedemHeading = itemView.findViewById(R.id.txt_redeemHeading);
            redeemReqCoin = itemView.findViewById(R.id.txt_redeemReqCoin);
            redeemValidity = itemView.findViewById(R.id.txt_redeemValidity);
            redeemNow = itemView.findViewById(R.id.txt_redeemNow);
        }
    }
}
