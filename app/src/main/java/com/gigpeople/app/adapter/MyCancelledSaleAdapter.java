package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.activity.UpcomingSalesDetailsActivity;
import com.gigpeople.app.apiModel.MySalesListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class MyCancelledSaleAdapter extends RecyclerView.Adapter<MyCancelledSaleAdapter.DataObjectHolder> {


    Context context;
    List<MySalesListResponse.CancelledOrderList> mData;
    String types;
    int selectposition = 0;
    String params;

    public interface CallBack{
        public void call(String name);
    }


    public MyCancelledSaleAdapter(Context context, List<MySalesListResponse.CancelledOrderList> mData) {

        this.context = context;
        this.mData = mData;
        this.params = params;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        ImageView imagFav;
        CircleImageView profile_img;
        TextView txtMenu,txt_time,txt_category,txt_name,txt_description;

        public DataObjectHolder(View itemView){
            super(itemView);

            imagFav = (ImageView)itemView.findViewById(R.id.img_fav);
            profile_img = (CircleImageView)itemView.findViewById(R.id.img);
            txt_time = (TextView)itemView.findViewById(R.id.txt_time);
            txt_name = (TextView)itemView.findViewById(R.id.txt_name);
            txt_category= (TextView)itemView.findViewById(R.id.txt_category);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mysale_cancelleditem, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_name.setText(mData.get(position).getBuyerDetails().getFirstName()+" "+mData.get(position).getBuyerDetails().getLastName());
        holder.txt_category.setText(mData.get(position).getCategoryName());
        holder.txt_description.setText(mData.get(position).getDescription());
        if (!mData.get(position).getBuyerDetails().getProfileImageUrl().equals("")){
            Glide.with(context).load(mData.get(position).getBuyerDetails().getProfileImageUrl()).into(holder.profile_img);

        }
        holder.txt_time.setText(GlobalMethods.DateConvert(mData.get(position).getDeliverytime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SalesDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("from","cancelled");
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

