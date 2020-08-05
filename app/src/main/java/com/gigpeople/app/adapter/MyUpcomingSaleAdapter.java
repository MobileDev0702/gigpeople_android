package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.activity.UpcomingSalesDetailsActivity;
import com.gigpeople.app.apiModel.MySalesListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class MyUpcomingSaleAdapter extends RecyclerView.Adapter<MyUpcomingSaleAdapter.DataObjectHolder> {


    Context context;
    List<MySalesListResponse.OrderRequestList> mData;
    String types;
    int selectposition = 0;
    String TIME;

    public interface CallBack{
        public void call(String name);
    }


    public MyUpcomingSaleAdapter(Context context, List<MySalesListResponse.OrderRequestList> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        ImageView imagFav;
        CircleImageView profile_img;
        TextView txtMenu,txt_time,txt_name,txt_description,txt_category;


        public DataObjectHolder(View itemView){
            super(itemView);

            imagFav = (ImageView)itemView.findViewById(R.id.img_fav);
            profile_img = (CircleImageView)itemView.findViewById(R.id.img);
            txt_time = (TextView)itemView.findViewById(R.id.txt_time);
            txt_name = (TextView)itemView.findViewById(R.id.txt_name);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);
            txt_category = (TextView)itemView.findViewById(R.id.txt_category);





        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mysale_upcomingitem, parent, false);

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
                Intent intent = new Intent(context,UpcomingSalesDetailsActivity.class);
                intent.putExtra("page","2");
                intent.putExtra("position",position);
                intent.putExtra("from_list","order");
                context.startActivity(intent);
            }
        });



    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
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

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

