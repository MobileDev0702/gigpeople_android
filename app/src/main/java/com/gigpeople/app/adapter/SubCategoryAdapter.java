package com.gigpeople.app.adapter;

import android.content.Context;
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

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.DataObjectHolder> {

    Context context;
    List<DashBoardResponse.MainCategoryList.SubCategory> sublist;
    String status = "0";

    public interface CallBack {
        public void call(String name);
    }

    public SubCategoryAdapter(Context context, List<DashBoardResponse.MainCategoryList.SubCategory> sublist) {

        this.context = context;
        this.sublist = sublist;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView subcategory;
        CheckBox check;

        public DataObjectHolder(View itemView) {
            super(itemView);
            subcategory = (TextView) itemView.findViewById(R.id.txt_subcat);
            check = (CheckBox) itemView.findViewById(R.id.checkbox_subcategory);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory_select, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.subcategory.setText(sublist.get(position).getSubCategoryName());
        holder.check.setOnCheckedChangeListener(null);

        if (sublist.get(position).getStatus().equals("0")) {
            holder.check.setChecked(false);
        } else {
            holder.check.setChecked(true);
        }

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sublist.get(position).setStatus("1");
                } else {
                    sublist.get(position).setStatus("0");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

}
