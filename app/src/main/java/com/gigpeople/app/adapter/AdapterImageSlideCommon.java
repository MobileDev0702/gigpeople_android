package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DashBoardResponse;

import java.util.List;

public class AdapterImageSlideCommon extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public List<String> banner;

    public AdapterImageSlideCommon(Context context, List<String> banner) {
        this.context = context;
        this.banner = banner;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        View itemView = layoutInflater.inflate(R.layout.item_image_slider, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_banner);
        Glide.with(context).load(banner.get(position)).into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}