package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.model.CategoryModel;
import com.gigpeople.app.model.SubCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataObjectHolder> {


    Context context;
    //List<CategoryModel> chat_list;
    List<DashBoardResponse.MainCategoryList.SubCategory> sublist;
    SubCategoryAdapter subcategoryAdapter;

    public List<DashBoardResponse.MainCategoryList> mData;

    String status = "0";
    String check_status = "0";
    public interface CallBack {
        public void call(String name);
    }

    public CategoryAdapter(Context context, List<DashBoardResponse.MainCategoryList> mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView category;
        RecyclerView recyclerView;
        CheckBox check;

        public DataObjectHolder(View itemView) {
            super(itemView);

            category = (TextView) itemView.findViewById(R.id.categoryname);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
            check = (CheckBox) itemView.findViewById(R.id.checkbox_category);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_select, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.category.setText(mData.get(position).getMainCategoryName());
        holder.check.setOnCheckedChangeListener(null);

        if (mData.get(position).getSubCategory().size() > 0) {
            sublist = new ArrayList<>();
            sublist = mData.get(position).getSubCategory();
            subcategoryAdapter = new SubCategoryAdapter(context, sublist);
            holder.recyclerView.setAdapter(subcategoryAdapter);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        if (mData.get(position).getStatus().equals("0")) {
            holder.check.setChecked(false);
            holder.recyclerView.setVisibility(View.GONE);
        } else {
            holder.check.setChecked(true);
            holder.recyclerView.setVisibility(View.VISIBLE);
        }

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Log.e("TAG", "INSIDE ADAPTER");
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    mData.get(position).setStatus("1");
                } else {
                    mData.get(position).setStatus("0");
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClick","out side condition"+" position::"+position);
                if (mData.get(position).getStatus().equals("0")) {

                    Log.e("TAG", "INSIDE ADAPTER");

                    Log.d("onClick","in side condition"+" position::"+position);
                    holder.recyclerView.setVisibility(View.VISIBLE);

                     mData.get(position).setStatus("1");
                    holder.check.setChecked(true);

                } else {

                    holder.recyclerView.setVisibility(View.GONE);
                    mData.get(position).setStatus("0");
                    holder.check.setChecked(false);
                }
                notifyDataSetChanged();
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
