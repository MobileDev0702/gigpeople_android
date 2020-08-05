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
import com.gigpeople.app.activity.CategoryDetailsActivity;
import com.gigpeople.app.apiModel.SearchGiGListResponse;
import com.gigpeople.app.model.MainCategory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gigpeople.app.subfragment.SearchGigFragment.searchgigLists;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class SearchgigAdapter extends RecyclerView.Adapter<SearchgigAdapter.DataObjectHolder> {

    Context context;
    List<SearchGiGListResponse.SearchgigList> mData;
    String types;
    int selectposition = 0;
    String TIME,image="";

    public interface CallBack{
        public void call(String name);
    }

    public SearchgigAdapter(Context context,List<SearchGiGListResponse.SearchgigList>mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        ImageView imagFav;
        View color;
        TextView txtMenu,status,txt_name,txt_time,txt_description;
        ImageView sahre;
        RoundedImageView img;

        public DataObjectHolder(View itemView){
            super(itemView);

            //imagFav = (ImageView)itemView.findViewById(R.id.img_fav);

            status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            color = (View) itemView.findViewById(R.id.view);
            sahre = (ImageView)itemView.findViewById(R.id.img_share);
            img = (RoundedImageView)itemView.findViewById(R.id.img);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_search_gig, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.status.setText("Active");
        holder.color.setBackgroundResource(R.drawable.background_green_one);
        holder.txt_name.setText(mData.get(position).getTitle());
        holder.txt_description.setText(mData.get(position).getDescription());
        if (mData.get(position).getImageList().size()>0){
            Glide.with(context).load(mData.get(position).getImageList().get(0).getThumnail()).into(holder.img);
        }

        if (mData.get(position).getDeliveryTime().equals("1")) {
            holder.txt_time.setText(mData.get(position).getDeliveryTime() + " day");
        } else {
            holder.txt_time.setText(mData.get(position).getDeliveryTime() + " days");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent share = new Intent(context, CategoryDetailsActivity.class);
                    share.putExtra("page",position);
                    share.putExtra("from","search");
                    context.startActivity(share);
                }
            });


        holder.sahre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < mData.get(position).getImageList().size(); i++) {

                    if (image.equals("")) {
                        image = mData.get(position).getImageList().get(i).getFile();
                    } else {
                        image +="\n"+mData.get(position).getImageList().get(i).getFile();
                    }
                }
                String delivery_time="";
                if (mData.get(position).getDeliveryTime().equals("1")) {
                  delivery_time=mData.get(position).getDeliveryTime() + " day";
                } else {
                   delivery_time=mData.get(position).getDeliveryTime() + " days";
                }
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "\nGigPeople"+"\nTitle: "+mData.get(position).getTitle()+"\nCategory: "+mData.get(position).getCategoryName()+
                        "\nSubcategory: "+mData.get(position).getSubCategoryName()+ "\nRevision: "+mData.get(position).getRevisions()+"\nPrice: "+"$"+mData.get(position).getPrice()+
                        "\nDeliveryTime: "+delivery_time/*+"\nImage: "+image*/;
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
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

