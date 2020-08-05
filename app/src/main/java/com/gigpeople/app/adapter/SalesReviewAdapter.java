package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class SalesReviewAdapter extends RecyclerView.Adapter<SalesReviewAdapter.DataObjectHolder> {

    Context context;
    MyOrdersDetailResponse.UserReviews mData;
    String types;

    public interface CallBack {
        public void call(String name);
    }

    public SalesReviewAdapter(Context context,MyOrdersDetailResponse.UserReviews mData) {

        this.context = context;
        this.mData=mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        RatingBar ratingbar;
        TextView txt_date,txt_name,txt_desc;

        public DataObjectHolder(View itemView) {
            super(itemView);

            ratingbar=(RatingBar)itemView.findViewById(R.id.ratingbar);
            txt_date=(TextView)itemView.findViewById(R.id.txt_date);
            txt_name=(TextView)itemView.findViewById(R.id.txt_name);
            txt_desc=(TextView)itemView.findViewById(R.id.txt_desc);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sales_review_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_name.setText(mData.getFirstName()+" "+mData.getLastName());
        //Log.e("Description",mData.getReview()+" nnnnn");
        holder.txt_desc.setText(mData.getReview()+"");
        holder.txt_date.setText(GlobalMethods.DateTime1(mData.getIsDate()));
        if (!mData.getRating().equals("")) {
            holder.ratingbar.setRating(Float.parseFloat(mData.getRating()));
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}

