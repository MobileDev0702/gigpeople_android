package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DashBoardResponse;

import java.util.List;

public class AdapterImageSlide extends PagerAdapter {

    Context context;
    public List<DashBoardResponse.BannerList> banner;

    public AdapterImageSlide(Context context, List<DashBoardResponse.BannerList> banner) {
        this.context = context;
        this.banner = banner;
    }

    @Override
    public int getCount() {
        return banner.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_image_slider, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_banner);
        Glide.with(context).load(banner.get(position).getBannerImage()).into(imageView);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}