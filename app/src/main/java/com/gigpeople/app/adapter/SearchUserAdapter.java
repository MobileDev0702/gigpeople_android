package com.gigpeople.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.GigOwnerDetailsActivity;
import com.gigpeople.app.activity.OfferSendRequestDetailsActivity;
import com.gigpeople.app.apiModel.SearchSellerListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.DataObjectHolder> {


    Context context;
    List<SearchSellerListResponse.SellerList> mData;
    String types;
    int selectposition = 0;
    String params;

    public interface CallBack{
        public void call(String name);
    }


    public SearchUserAdapter(Context context,List<SearchSellerListResponse.SellerList> mData) {

        this.context = context;
        this.mData=mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        ImageView imagFav,mobileverified,emailverified;
        TextView txtverified,txt_name,txt_category,txt_date;
        CircleImageView img;


        public DataObjectHolder(View itemView){
            super(itemView);

            txtverified = (TextView)itemView.findViewById(R.id.txt_verified);
            mobileverified = (ImageView)itemView.findViewById(R.id.img_mobile);
            emailverified = (ImageView)itemView.findViewById(R.id.img_email);

            txt_name = (TextView)itemView.findViewById(R.id.txt_name);
            txt_category = (TextView)itemView.findViewById(R.id.txt_category);
            txt_date = (TextView)itemView.findViewById(R.id.txt_date);
            img = (CircleImageView) itemView.findViewById(R.id.img);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_searchuser, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_name.setText(mData.get(position).getFirstName()+" "+mData.get(position).getLastName());
        holder.txt_category.setText(mData.get(position).getSkills());
        holder.txt_date.setText(GlobalMethods.DateTime1(mData.get(position).getJoinDate()));

        if (!mData.get(position).getProfileImageUrl().equals("")){

            Glide.with(context).load(mData.get(position).getProfileImageUrl()).into(holder.img);
        }

        if (mData.get(position).getIsMobileVerified().trim().equals("1")){
           // Log.e("MobileVerified","if");
            holder.mobileverified.setImageDrawable(context.getResources().getDrawable(R.drawable.phone));
        }else {
           // Log.e("MobileVerified","else");
            holder.mobileverified.setImageDrawable(context.getResources().getDrawable(R.drawable.phone_gray));
        }

        if (mData.get(position).getIsEmailVerified().trim().equals("1")){
            holder.emailverified.setImageDrawable(context.getResources().getDrawable(R.drawable.mail));

        }else {
            holder.emailverified.setImageDrawable(context.getResources().getDrawable(R.drawable.mail_gray));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.e("MobileVerified",mData.get(position).getIsMobileVerified()+" Email: "+mData.get(position).getIsEmailVerified());
                Intent intent = new Intent(context,GigOwnerDetailsActivity.class);
                intent.putExtra("from_detail","search");
                intent.putExtra("seller_id",mData.get(position).getSellerId());
                intent.putExtra("page",position);
                intent.putExtra("is_favourite",mData.get(position).getIsFavourite());
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

