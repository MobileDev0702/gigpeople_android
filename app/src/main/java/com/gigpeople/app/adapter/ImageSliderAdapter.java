package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigpeople.app.R;


/**
 * Created by praveensamuel on 26/01/19.
 */

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.DataObjectHolder> {


    Context context;

    String type;
    int[] image;


    public interface CallBack{
        public void call(String name);
    }


    public ImageSliderAdapter(Context context, int[] image ) {

        this.context = context;
        this.image = image;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        ImageView img;


        public DataObjectHolder(View itemView){
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.img);


        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_image_slider, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.img.setImageResource(image[position]);



    }


    @Override
    public int getItemCount() {
        return image.length;
    }


}

