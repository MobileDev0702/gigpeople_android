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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidpagecontrol.PageControl;
import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.CategoryDetailsActivity;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.model.ImageVideoModel;
import com.gigpeople.app.utils.GlobalApiMethod;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class HomeSubCategoryTwoAdapter extends RecyclerView.Adapter<HomeSubCategoryTwoAdapter.DataObjectHolder> {

    Context context;
    List<GigCategoryResultResponse.CategorygigList> mData;
    String types;
    int selectposition = 0;
    AdapterImageSlideSupportAdapter adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    int fav_status = 0;
    int size=0;
    List<ImageVideoModel> imageListNew;
    ImageVideoAdapter imageAdapter;

    public interface CallBack {
        public void call(String name);
    }

    public HomeSubCategoryTwoAdapter(Context context, List<GigCategoryResultResponse.CategorygigList> mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav;
        TextView txtMenu, txtname, txtcategoryname, txtprice, txtStatus;
        ViewPager viewPager;
        LinearLayout namelayout;
        RelativeLayout relativeLayout;
        Button image;
        CircleImageView profileimage;
        RatingBar rating_bar;
        PageControl pageController;

        public DataObjectHolder(View itemView) {
            super(itemView);

            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
            namelayout = (LinearLayout) itemView.findViewById(R.id.namelayout);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.image_layout);
            image = (Button) itemView.findViewById(R.id.image);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtcategoryname = (TextView) itemView.findViewById(R.id.txtcategoryname);
            txtprice = (TextView) itemView.findViewById(R.id.txtprice);
            profileimage = (CircleImageView) itemView.findViewById(R.id.profileimage);
            rating_bar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            pageController = (PageControl) itemView.findViewById(R.id.pageController);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subcategory_two, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txtname.setText(mData.get(position).getSellerDetails().getFirstName()+" "+mData.get(position).getSellerDetails().getLastName());
        holder.txtcategoryname.setText(mData.get(position).getCategoryName());
        holder.txtprice.setText("$"+mData.get(position).getPrice());
        holder.rating_bar.setRating(Float.parseFloat(mData.get(position).getGigRating()));
        if (!mData.get(position).getSellerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(mData.get(position).getSellerDetails().getProfileImageUrl()).into(holder.profileimage);
        }

        if (mData.get(position).getIsFavourite().equals("0")){
            holder.imagFav.setImageResource(R.drawable.fav_line);
        }else {
            holder.imagFav.setImageResource(R.drawable.fav_icon);
        }
        holder.imagFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalApiMethod.toClickAddfavourite(GlobalMethods.GetUserID(context), "2", mData.get(position).getGigId(), context);
                if (mData.get(position).getIsFavourite().equals("0")) {
                    //holder.imagFav.setImageResource(R.drawable.fav_icon);
                    //fav_status = 1;
                    mData.get(position).setIsFavourite("1");
                    notifyDataSetChanged();
                } else {
                   // holder.imagFav.setImageResource(R.drawable.fav_line);
                    //fav_status = 0;
                    mData.get(position).setIsFavourite("0");
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryDetailsActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("from","category");
                context.startActivity(intent);
        }
        });

        List<String> temp = new ArrayList<>();
        size = mData.get(position).getImageList().size();

        for (int i = 0; i < size; i++) {
            temp.add(mData.get(position).getImageList().get(i).getThumnail());
        }
        /*imageListNew=new ArrayList<>();
        for (int i = 0; i < size; i++) {
            imageListNew.add(new ImageVideoModel(mData.get(position).getImageList().get(i).getFile(),mData.get(position).getImageList().get(i).getThumnail(),mData.get(position).getImageList().get(i).getType()));
        }*/
        //imageAdapter = new ImageVideoAdapter(context, imageListNew);
        adapterImageSlide = new AdapterImageSlideSupportAdapter(context, temp);
        holder.viewPager.setAdapter(adapterImageSlide);
        holder.pageController.setViewPager(holder.viewPager);
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

                    if (currentPage == size) {
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

        holder.viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

