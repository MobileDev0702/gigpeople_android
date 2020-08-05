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
import com.gigpeople.app.activity.CategoryDetailsActivity;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.FavouriteGigListResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalApiMethod;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class FavgigAdapter extends RecyclerView.Adapter<FavgigAdapter.DataObjectHolder> {

    ApiService apiService;
    Context context;
    List<FavouriteGigListResponse.FavouritegigList> mData;
    String types,user_id;
    int selectposition = 0;
    int fav_status = 0;

    public interface CallBack {
        public void call(String name);
    }

    public FavgigAdapter(Context context, List<FavouriteGigListResponse.FavouritegigList> mData) {

        this.context = context;
        this.mData = mData;
        user_id= PrefConnect.readString(this.context,PrefConnect.USER_ID,"");
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav;
        View color;
        TextView txtMenu, status, txtgigname, txtgigdescription, txtcreatedat;
        RoundedImageView imageview;

        public DataObjectHolder(View itemView) {
            super(itemView);

            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            imageview = (RoundedImageView) itemView.findViewById(R.id.imageView);
            txtgigname = (TextView) itemView.findViewById(R.id.txtgigname);
            txtgigdescription = (TextView) itemView.findViewById(R.id.txtgigdescription);
            txtcreatedat = (TextView) itemView.findViewById(R.id.txtcreatedat);
            status = (TextView) itemView.findViewById(R.id.txt_status);
            color = (View) itemView.findViewById(R.id.view);
            //sahre = (ImageView) itemView.findViewById(R.id.img_fav);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_favgig_item, parent, false);
        apiService = RetrofitSingleton.createService(ApiService.class);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        // holder.status.setText(mData.get(position).getName());
        //holder.color.setBackgroundResource(mData.get(position).getImage());

        holder.txtgigname.setText(mData.get(position).getGigDetails().get(0).getTitle());
        holder.txtgigdescription.setText(mData.get(position).getGigDetails().get(0).getDescription());


        if (mData.get(position).getGigDetails().get(0).getDeliveryTime().equals("1")) {
            holder.txtcreatedat.setText(mData.get(position).getGigDetails().get(0).getDeliveryTime() + " day");
        } else {
            holder.txtcreatedat.setText(mData.get(position).getGigDetails().get(0).getDeliveryTime() + " days");
        }


        //holder.txtcreatedat.setText(mData.get(position).getGigDetails().get(0).getDeliveryTime());
        Glide.with(context).load(mData.get(position).getGigDetails().get(0).getImageList().get(0).getThumnail()).into(holder.imageview);

        if (mData.get(position).getLiveStatus().equals("1")) {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
            holder.status.setText("Online now");
            holder.color.setBackgroundResource(R.color.colorGreen);
        } else {
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            holder.status.setText("Offline");
            holder.color.setBackgroundResource(R.color.colorRed);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("page",position);
                intent.putExtra("from","Favourite");

                context.startActivity(intent);
                // context.startActivity(share);
                  /* Intent share = new Intent(context, GigDetailsActivity.class);
                   share.putExtra("page","1");
                   */
            }
        });

        holder.imagFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GlobalApiMethod.toClickAddfavourite(user_id, "2", mData.get(position).getGigDetails().get(0).getGigId(), context);

                if (fav_status == 0) {
                    holder.imagFav.setImageResource(R.drawable.fav_line);
                    fav_status = 1;
                } else {
                    holder.imagFav.setImageResource(R.drawable.fav_icon);
                    fav_status = 0;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

