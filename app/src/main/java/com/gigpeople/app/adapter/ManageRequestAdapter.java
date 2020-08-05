package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.BuyOfferActivity;
import com.gigpeople.app.activity.ManageRequestDetails_2;
import com.gigpeople.app.activity.ManageRequestOfferDetailsActivity;
import com.gigpeople.app.apiModel.RequestListResponse;
import com.gigpeople.app.model.MainCategory;

import java.util.List;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class ManageRequestAdapter extends RecyclerView.Adapter<ManageRequestAdapter.DataObjectHolder> {

    Context context;
    List<RequestListResponse.RequestList> mData;

    public interface CallBack {
        public void call(String name);
    }

    public ManageRequestAdapter(Context context, List<RequestListResponse.RequestList> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txt_category,txt_price,txt_description;

        public DataObjectHolder(View itemView) {
            super(itemView);

            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_manage_request_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_category.setText(mData.get(position).getCategoryName());
        holder.txt_price.setText("$"+mData.get(position).getPrice());
        holder.txt_description.setText(mData.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageRequestDetails_2.class);
                intent.putExtra("request_id",mData.get(position).getRequestId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

