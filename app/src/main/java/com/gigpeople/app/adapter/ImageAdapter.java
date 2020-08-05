package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;


import java.util.ArrayList;


/**
 * Created by uniflyn on 6/4/18.
 */

public class ImageAdapter extends PagerAdapter {
    Context context;
    int[] img;
    LayoutInflater layoutInflater;
    ArrayList<String> imageurl;
//    List<CarListResponse.CarList.CarImage>carImages;

    public ImageAdapter(Context context, ArrayList<String> imageurl ) {
        this.context = context;
        this.imageurl=imageurl;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageurl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = layoutInflater.inflate(R.layout.item_image, container, false);

       /* TextView textView = (TextView) itemView.findViewById(R.id.text);
        textView.setText(img.get(position));*/

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
      // imageView.setImageResource(imageSliderModelList.get(position).getImage());
        Glide.with(context).load(imageurl.get(position)).into(imageView);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}
