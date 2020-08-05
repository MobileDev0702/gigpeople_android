package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.gigpeople.app.R;

import java.util.ArrayList;


public class WalkthroughAdapter extends PagerAdapter {
    private ArrayList<String> text_array=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    int image_bg[] = {R.drawable.translogo, R.drawable.translogo, R.drawable.translogo};

    public WalkthroughAdapter(Context context, ArrayList<String> text_array) {
        this.context = context;
        this.text_array=text_array;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return text_array.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.item_walkthrough, view, false);

        view.addView(myImageLayout, 0);
        TextView text = (TextView) myImageLayout
                .findViewById(R.id.txt_desc);
        text.setText(text_array.get(position));

        ImageView image = (ImageView) myImageLayout
                .findViewById(R.id.linear_parent);
        //image.setImageResource(image_bg[position]);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}