package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.SubCategorytwoActivity;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.HelpandSupportResponse;
import com.gigpeople.app.model.MainCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class HomeSubCategoryAdapter extends RecyclerView.Adapter<HomeSubCategoryAdapter.DataObjectHolder> implements Filterable {


    Context context;
    public List<DashBoardResponse.MainCategoryList.SubCategory> mData;
    public List<DashBoardResponse.MainCategoryList.SubCategory> mDatafilter;
    String types,maincatgory_id;
    int selectposition = 0;

    HomeSubCategoryAdapterListener listener;

    public interface CallBack {
        public void call(String name);
    }

    public HomeSubCategoryAdapter(Context context, List<DashBoardResponse.MainCategoryList.SubCategory> mData,String maincatgory_id) {

        this.context = context;
        this.mData = mData;
        this.mDatafilter = mData;
        this.maincatgory_id=maincatgory_id;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_subcategory, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        Glide.with(context).load(mDatafilter.get(position).getSubCategoryIcon()).into(holder.imageView);
        holder.txtMenu.setText(mDatafilter.get(position).getSubCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("SubcategoryId",mDatafilter.get(position).getSubCategoryId());
                Intent intent = new Intent(context, SubCategorytwoActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("maincatgory_id", maincatgory_id);
                intent.putExtra("subcategoryid", mDatafilter.get(position).getSubCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatafilter.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDatafilter = mData;
                } else {
                    List<DashBoardResponse.MainCategoryList.SubCategory> filteredList = new ArrayList<>();
                    for (DashBoardResponse.MainCategoryList.SubCategory row : mData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSubCategoryName().toLowerCase().contains(charString.toLowerCase())) {

                            Log.e("Serched element", row.getSubCategoryName());
                            filteredList.add(row);
                        }

                    }

                    mDatafilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatafilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDatafilter = (List<DashBoardResponse.MainCategoryList.SubCategory>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface HomeSubCategoryAdapterListener {
        void onHelpSelected(DashBoardResponse.MainCategoryList.SubCategory filt);
    }


}

