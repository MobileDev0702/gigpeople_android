package com.gigpeople.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.ActiveOrdersActivity;
import com.gigpeople.app.activity.ChatDetailsActivity;
import com.gigpeople.app.activity.RequestDetailsActivity;
import com.gigpeople.app.activity.SalesDeliveryActivity;
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SalefourAdapter extends RecyclerView.Adapter<SalefourAdapter.DataObjectHolder> {

    Context context;
    List<SellerSalesDetailResponse.MessageList> chat_list;
    String status = "0",user_id;

    public interface CallBack {
        public void call(String name);
    }

    public SalefourAdapter(Context context, List<SellerSalesDetailResponse.MessageList> chat_list) {

        this.context = context;
        this.chat_list = chat_list;
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView name,txt_date,txt_description;
        RoundedImageView user_image;

        public DataObjectHolder(View itemView) {
            super(itemView);

            user_image = (RoundedImageView) itemView.findViewById(R.id.user_image);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mysale_orders, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        if (user_id.equals(chat_list.get(position).getUserId())){
            holder.name.setText("Me");
        }else {
            holder.name.setText(chat_list.get(position).getFirstName() + " " + chat_list.get(position).getLastName());
        }

        if (!chat_list.get(position).getProfileImageUrl().equals("")){
            Glide.with(context).load(chat_list.get(position).getProfileImageUrl()).into(holder.user_image);
        }

        holder.txt_date.setText(GlobalMethods.DateTime1( chat_list.get(position).getIsDate()));
        holder.txt_description.setText(chat_list.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return chat_list.size();
    }

}
