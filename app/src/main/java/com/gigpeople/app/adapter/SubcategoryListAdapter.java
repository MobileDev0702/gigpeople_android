package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DashBoardResponse;

import java.util.List;

public class SubcategoryListAdapter extends RecyclerView.Adapter<SubcategoryListAdapter.DataObjectHolder> {


    Context context;
    List<DashBoardResponse.MainCategoryList.SubCategory> mData;
    ItemClickListener itemClickListenerSub;



    public interface ItemClickListener
    {
        void itemClick(String name, String modelid);
    }

    public SubcategoryListAdapter(Context context, List<DashBoardResponse.MainCategoryList.SubCategory> mData, ItemClickListener itemClickListenerSub) {

        this.context = context;
        this.mData = mData;
        this.itemClickListenerSub=itemClickListenerSub;
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
                .inflate(R.layout.item_sub_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(mData.get(position).getSubCategoryName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                itemClickListenerSub.itemClick(mData.get(position).getSubCategoryName(),mData.get(position).getSubCategoryId());
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

