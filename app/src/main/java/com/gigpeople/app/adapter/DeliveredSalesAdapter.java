package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.VideoViewActivity;
import com.gigpeople.app.activity.ZoomImageActivity;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

public class DeliveredSalesAdapter extends RecyclerView.Adapter<DeliveredSalesAdapter.DataObjectHolder> {

    Context context;
    ItemClickListener itemClickListener;

    List<SellerSalesDetailResponse.OrderDelivery> mData;

    public interface ItemClickListener {
        void itemClick(String name, int position, String makeid);
    }

    public DeliveredSalesAdapter(Context context, List<SellerSalesDetailResponse.OrderDelivery> mData) {

        this.context = context;
        this.mData = mData;
        this.itemClickListener = itemClickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txt_description,txt_date;
        ImageView img_delivered,img_play;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            img_delivered = (ImageView) itemView.findViewById(R.id.img_delivered);
            img_play = (ImageView) itemView.findViewById(R.id.img_play);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_delivered_product_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_description.setText(mData.get(position).getDescription());
        holder.txt_date.setText(GlobalMethods.DateTime12(mData.get(position).getIsDate()));
        if (mData.get(position).getType().equals("1")){

            Glide.with(context).load(mData.get(position).getProjectUrl()).into(holder.img_delivered);
            holder.img_play.setVisibility(View.GONE);
        }else  if (mData.get(position).getType().equals("2")){

            Glide.with(context).load(mData.get(position).getThumbUrl()).into(holder.img_delivered);
            holder.img_play.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mData.get(position).getType().equals("1")){

                    Log.e("i am in","play button click");
                    Intent video=new Intent(context, ZoomImageActivity.class);
                    video.putExtra("image",mData.get(position).getProjectUrl());
                    context.startActivity(video);
                }else  if (mData.get(position).getType().equals("2")){

                    Log.e("i am in","play button click");
                    Intent video=new Intent(context, VideoViewActivity.class);
                    video.putExtra("video",mData.get(position).getProjectUrl());
                    context.startActivity(video);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

