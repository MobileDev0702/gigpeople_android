package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.DeliveryModel;
import com.gigpeople.app.apiModel.RevisonsModel;

import java.util.List;

public class DeliveryTimeAdapter extends RecyclerView.Adapter<DeliveryTimeAdapter.DataObjectHolder> {


    Context context;
    List<DeliveryModel> mData;
    ItemClickListener itemClickListener;



    public interface ItemClickListener
    {
        void itemClick(String name);
    }

    public DeliveryTimeAdapter(Context context, List<DeliveryModel> mData, ItemClickListener itemClickListener) {

        this.context = context;
        this.mData = mData;
        this.itemClickListener=itemClickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{



        TextView name;

        public DataObjectHolder(View itemView){
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.txt_revisions);




        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_revisions, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(mData.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


itemClickListener.itemClick(mData.get(position).getName());
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

