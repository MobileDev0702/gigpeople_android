package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.MultipleImageModel;
import com.gigpeople.app.model.ImageAddModel;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;


public class NewImageAddAdapter extends RecyclerView.Adapter<NewImageAddAdapter.DataObjectHolder> {

    Context context;
    List<MultipleImageModel> mData;
    CallBack callBack;
    int status;

    public interface CallBack {
        public void call();
    }

    public NewImageAddAdapter(Context context, List<MultipleImageModel> mData,int status) {
        this.context = context;
        this.mData = mData;
        this.callBack = callBack;
        this.status=status;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView image, imageDelete;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img_one);
            imageDelete = (ImageView) itemView.findViewById(R.id.img_delete);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_newimage_add, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        if (status==1) {
            Glide.with(context).load(GlobalMethods.GIG_IMAGE_URL + mData.get(position).getUrl().trim()).into(holder.image);
        }else if (status==2){
            Glide.with(context).load(GlobalMethods.REQUEST_IMAGE_URL + mData.get(position).getUrl().trim()).into(holder.image);
        }

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

