package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.VideoViewActivity;
import com.gigpeople.app.model.ImageVideoModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by uniflyn on 6/4/18.
 */

public class ImageVideoAdapter extends PagerAdapter {
    Context context;
    int[] img;
    LayoutInflater layoutInflater;
    List<ImageVideoModel> imageurl;
//    List<CarListResponse.CarList.CarImage>carImages;

    public ImageVideoAdapter(Context context,List<ImageVideoModel> imageurl ) {
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
        ImageView imageViewPlay = (ImageView) itemView.findViewById(R.id.image_play);
      // imageView.setImageResource(imageSliderModelList.get(position).getImage());
        Glide.with(context).load(imageurl.get(position).getThumb_url()).into(imageView);

        if (imageurl.get(position).getType().equals("1")){
            imageViewPlay.setVisibility(View.GONE);
        }else {
            imageViewPlay.setVisibility(View.VISIBLE);
        }

        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("i am in","play button click");
                Intent video=new Intent(context, VideoViewActivity.class);
                video.putExtra("video",imageurl.get(position).getFile_url());
                context.startActivity(video);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}
