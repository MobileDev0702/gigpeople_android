package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class SalesReviewAdapterSales extends RecyclerView.Adapter<SalesReviewAdapterSales.DataObjectHolder> {

    Context context;
    SellerSalesDetailResponse.UserReviews mData;
    String types;

    public interface CallBack {
        public void call(String name);
    }

    public SalesReviewAdapterSales(Context context, SellerSalesDetailResponse.UserReviews mData) {

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
        holder.txt_desc.setText(mData.getReview());
        holder.txt_date.setText(GlobalMethods.DateTime1(mData.getIsDate()));
        holder.ratingbar.setRating(Float.parseFloat(mData.getRating()));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}

