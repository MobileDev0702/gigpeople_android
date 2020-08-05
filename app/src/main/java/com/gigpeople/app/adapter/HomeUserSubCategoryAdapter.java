package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.CategoryDetailsActivity;
import com.gigpeople.app.model.MainCategory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class HomeUserSubCategoryAdapter extends RecyclerView.Adapter<HomeUserSubCategoryAdapter.DataObjectHolder> {


    Context context;
    List<MainCategory> mData;
    String types;
    int selectposition = 0;
    int[] banner = {R.drawable.banner_one, R.drawable.banner_two};
    AdapterImageSlide adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.

    public interface CallBack{
        public void call(String name);
    }


    public HomeUserSubCategoryAdapter(Context context, List<MainCategory> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        ImageView imagFav;
        TextView txtMenu;
        ViewPager viewPager;
        Button imagebutton;
        LinearLayout linear;


        public DataObjectHolder(View itemView){
            super(itemView);

            //imagFav = (ImageView)itemView.findViewById(R.id.img_fav);
            viewPager = (ViewPager)itemView.findViewById(R.id.viewpager);
            imagebutton=(Button)itemView.findViewById(R.id.imagebutton);
            linear=(LinearLayout)itemView.findViewById(R.id.linear);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user_subcategory, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

      /*  if(mData.get(position).getName().equalsIgnoreCase("1")){
            holder.imagFav.setImageResource(R.drawable.fav_icon);
        }else {
            holder.imagFav.setImageResource(R.drawable.un_fav_icon);
        }

        holder.imagFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mData.get(position).getName().equalsIgnoreCase("1")){

                    mData.get(position).setName("0");

                }else {
                    mData.get(position).setName("1");
                }


                notifyItemChanged(position);

            }
        });*/
       // adapterImageSlide = new AdapterImageSlide(context, banner);
        holder.viewPager.setAdapter(adapterImageSlide);
        try {


            holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;

                    //Log.e("position",currentPage+"");
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

                    if (currentPage == banner.length) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                context.startActivity(intent);
            }
        });

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

