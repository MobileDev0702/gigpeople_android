package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidpagecontrol.PageControl;
import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.CategoryDetailsActivity;
import com.gigpeople.app.activity.GigDetailsActivity;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.utils.GlobalApiMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class GigImageListAdapterAdapter extends RecyclerView.Adapter<GigImageListAdapterAdapter.DataObjectHolder> {


    Context context;
    List<GiGListResponse.PublishGigList> mData;
    String types;
    int selectposition = 0;
    AdapterImageSlideSupportAdapter adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    int fav_status = 0;

    public interface CallBack {
        public void call(String name);
    }

    public GigImageListAdapterAdapter(Context context, List<GiGListResponse.PublishGigList> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ViewPager viewPager;
        PageControl pageController;
        TextView txt_category_name;
        RatingBar rating_bar;

        public DataObjectHolder(View itemView) {
            super(itemView);

            viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
            pageController = (PageControl) itemView.findViewById(R.id.pageController);
            txt_category_name = (TextView) itemView.findViewById(R.id.txt_category_name);
            rating_bar = (RatingBar) itemView.findViewById(R.id.rating_bar);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_imagelist, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        final List<String> temp = new ArrayList<>();
        int size = mData.get(position).getImageList().size();

        holder.txt_category_name.setText(mData.get(position).getCategoryName());
        //This is gig rating name only changed
        holder.rating_bar.setRating(Float.parseFloat(mData.get(position).getRating()));

        //Log.e("size", size + "");
        for (int i = 0; i < size; i++) {
            temp.add(mData.get(position).getImageList().get(i).getThumnail());
        }

        adapterImageSlide = new AdapterImageSlideSupportAdapter(context, temp);
        holder.viewPager.setAdapter(adapterImageSlide);
        holder.pageController.setViewPager(holder.viewPager);

        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (currentPage == temp.size()) {
                    currentPage = 0;
                }
                String count = String.valueOf(currentPage + 1);

                try {
                    holder.viewPager.setCurrentItem(currentPage++, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "0");
                share.putExtra("gig_id", mData.get(position).getGigId());
                share.putExtra("seller_id", mData.get(position).getUserId());
                share.putExtra("position", position);
                share.putExtra("from", "search");
                context.startActivity(share);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

