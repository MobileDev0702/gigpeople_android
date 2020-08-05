package com.gigpeople.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.GigDetailsActivity;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class MyGigDeniedAdapter extends RecyclerView.Adapter<MyGigDeniedAdapter.DataObjectHolder> {

    Context context;
    List<GiGListResponse.DeniedGigList> mData;
    String types;
    int selectposition = 0;
    String TIME,image;

    public interface CallBack {
        public void call(String name);
    }

    public MyGigDeniedAdapter(Context context, List<GiGListResponse.DeniedGigList> mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav;
        RoundedImageView image;
        View color;
        TextView txtMenu, status, txt_name, txt_des, txt_time;
        ImageView sahre;

        public DataObjectHolder(View itemView) {
            super(itemView);

            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            status = (TextView) itemView.findViewById(R.id.txt_status);
            color = (View) itemView.findViewById(R.id.view);
            sahre = (ImageView) itemView.findViewById(R.id.img_share);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_des = (TextView) itemView.findViewById(R.id.txt_des);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            image = (RoundedImageView) itemView.findViewById(R.id.image);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mygig_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.status.setText("Denied");
        holder.color.setBackgroundResource(R.drawable.background_red_one);
        holder.txt_name.setText(mData.get(position).getTitle());
        holder.txt_des.setText(mData.get(position).getDescription());
        holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        Glide.with(context).load(GlobalMethods.GIG_IMAGE_URL+mData.get(position).getImageList().get(0).getThumnail()).into(holder.image);

        String TIME = setTimeString(mData.get(position).getCreatedAt());
        if (TIME.equals("0 minutes ago")) {
            holder.txt_time.setText("Created on: "+"Just Now");
        } else {
            holder.txt_time.setText("Created on: "+TIME);
        }
        if (position == 1) {
            //holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        if (position == 2) {
            // holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));

        }
        if (position == 0) {
            //holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "1");
                share.putExtra("gig_id", mData.get(position).getGigId());
                share.putExtra("position", position);
                share.putExtra("from", "gig_active");
                context.startActivity(share);
            }
        });

        holder.sahre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image="";
                for (int i = 0; i < mData.get(position).getImageList().size(); i++) {
                    if (image.equals("")) {
                        image = GlobalMethods.GIG_IMAGE_URL+mData.get(position).getImageList().get(i).getFile();
                    } else {
                        image +="\n"+GlobalMethods.GIG_IMAGE_URL+mData.get(position).getImageList().get(i).getFile();
                    }
                }
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "\nGigPeople"+"\nTitle: "+mData.get(position).getTitle()+"\nCategory: "+mData.get(position).getCategoryName()+
                        "\nSubcategory: "+mData.get(position).getSubCategoryName()+ "\nRevision: "+mData.get(position).getRevisions()+"\nPrice: "+"$"+mData.get(position).getPrice()+
                        "\nDeliveryTime: "+mData.get(position).getDeliveryTime()/*+"\nImage: "+image*/;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            long_time = mDate.getTime();
            Log.e("Date in milli", time + " ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        Log.e("TIME", TIME + " nan ");
        return TIME;
    }
}

