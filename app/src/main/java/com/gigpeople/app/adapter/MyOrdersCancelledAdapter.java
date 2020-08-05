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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.ActiveOrdersActivity;
import com.gigpeople.app.activity.CancelledOrdersActivity;
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
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

public class MyOrdersCancelledAdapter extends RecyclerView.Adapter<MyOrdersCancelledAdapter.DataObjectHolder> {

    Context context;
    List<MyOrdersListResponse.CancelledOrderList> mData;
    String types;
    int selectposition = 0;

    public interface CallBack {
        public void call(String name);
    }

    public MyOrdersCancelledAdapter(Context context, List<MyOrdersListResponse.CancelledOrderList> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav, imgshare, imagestatusicon;
        View color;
        TextView txtMenu, status, txtGigname, txtGigprice, txtdate, txtlastseen;
        LinearLayout secondlayout;
        RoundedImageView gigimages;

        public DataObjectHolder(View itemView) {
            super(itemView);

            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            imgshare = (ImageView) itemView.findViewById(R.id.img_share);
            status = (TextView) itemView.findViewById(R.id.txt_status);
            color = (View) itemView.findViewById(R.id.view);
            secondlayout = (LinearLayout) itemView.findViewById(R.id.secondlayout);
            txtGigname = (TextView) itemView.findViewById(R.id.txt_gigname);
            txtGigprice = (TextView) itemView.findViewById(R.id.txt_gigprice);
            txtdate = (TextView) itemView.findViewById(R.id.txtcreateddate);
            txtlastseen = (TextView) itemView.findViewById(R.id.txt_messagetime);
            imagestatusicon = (ImageView) itemView.findViewById(R.id.imagesatusicon);
            gigimages = (RoundedImageView) itemView.findViewById(R.id.imageView);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_myorderscancelled_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txtGigname.setText(mData.get(position).getTitle());
        Glide.with(context).load(mData.get(position).getImageList().get(0).getImageUrl()).into(holder.gigimages);
        holder.txtGigprice.setText("$ " + mData.get(position).getPrice());
        holder.txtdate.setText(GlobalMethods.DateTime1(mData.get(position).getOrderDate()));
        String TIME = setTimeString(mData.get(position).getSellerDetails().getLastVisited());
        Log.e("LastVisited",mData.get(position).getSellerDetails().getLastVisited());

        if (TIME.equals("0 minutes ago")) {
            holder.txtlastseen.setText("Last Seen: "+"Just Now");
        } else {
            holder.txtlastseen.setText("Last seen: "+TIME);
        }
        if (mData.get(position).getSellerDetails().getLiveStatus().equals("1")) {
            holder.status.setText("Online");
            holder.color.setBackgroundResource(R.drawable.background_green_one);
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
           // holder.imagestatusicon.setBackgroundResource(R.drawable.circlebackground_green);
        } else {
            holder.status.setText("Offline");
            holder.color.setBackgroundResource(R.drawable.background_red_one);
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
           // holder.imagestatusicon.setBackgroundResource(R.drawable.circlebackground_red);
        }

       /* holder.secondlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(context, SalesDetailsActivity.class);
                share.putExtra("page", "0");
                context.startActivity(share);
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActiveOrdersActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("page", "cancelled");
                context.startActivity(intent);
            }
        });
        holder.imgshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String image="";
                for (int i = 0; i < mData.get(position).getImageList().size(); i++) {
                    if (image.equals("")) {
                        image = mData.get(position).getImageList().get(i).getImageUrl();
                    } else {
                        image +="\n"+mData.get(position).getImageList().get(i).getImageUrl();
                    }
                }

                String shareBody = "\nGigPeople"+"\nTitle: "+mData.get(position).getTitle()+"\nCategory: "+mData.get(position).getCategoryName()+
                        "\nSubcategory: "+mData.get(position).getSubCategoryName()+"\nPrice: "+"$"+mData.get(position).getPrice()+
                        "\nDeliveryTime: "+mData.get(position).getDeliverytime()+" days"/*+"\nImage: "+image*/;

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        String TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        Log.e("TIME", TIME + " nan ");
        return TIME;
    }

}

