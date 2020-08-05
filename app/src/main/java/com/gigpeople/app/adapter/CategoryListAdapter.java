package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.DataObjectHolder> {


    Context context;
    ItemClickListener itemClickListener;

     List<DashBoardResponse.MainCategoryList> mData;


    public interface ItemClickListener
    {
        void itemClick(String name, int position, String makeid);
    }

    public CategoryListAdapter(Context context,List<DashBoardResponse.MainCategoryList> mData, ItemClickListener itemClickListener) {

        this.context = context;
        this.mData = mData;
        this.itemClickListener=itemClickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{



        TextView name;

        public DataObjectHolder(View itemView){
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txt_category);




        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(mData.get(position).getMainCategoryName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


itemClickListener.itemClick(mData.get(position).getMainCategoryName(),position,mData.get(position).getMainCategoryId());
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

