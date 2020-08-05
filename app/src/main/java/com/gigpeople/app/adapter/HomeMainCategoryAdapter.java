package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.HomeSubCategoryActivity;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.model.MainCategory;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class HomeMainCategoryAdapter extends RecyclerView.Adapter<HomeMainCategoryAdapter.DataObjectHolder> {

    Context context;
    public List<DashBoardResponse.MainCategoryList> mData;
    String types;
    int selectposition = 0;

    public interface CallBack {
        public void call(String name);
    }

    public HomeMainCategoryAdapter(Context context, List<DashBoardResponse.MainCategoryList> mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtMenu;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_menu);
            txtMenu = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        Glide.with(context).load(mData.get(position).getMainCategoryIcon()).into(holder.imageView);

        holder.txtMenu.setText(mData.get(position).getMainCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeSubCategoryActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("maincatgory_id", mData.get(position).getMainCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

