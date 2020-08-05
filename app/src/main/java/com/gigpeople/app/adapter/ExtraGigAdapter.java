package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.model.ExtraGigModel;

import java.util.List;

import javax.security.auth.callback.Callback;


public class ExtraGigAdapter extends RecyclerView.Adapter<ExtraGigAdapter.DataObjectHolder> {


    Context context;
    List<ExtraGigModel> mData;
    ItemClickListener itemClickListener;
    Callback callback;

    public interface ItemClickListener{
        public void itemClick();
    }

    public ExtraGigAdapter(Context context, List<ExtraGigModel> mData, ItemClickListener itemClickListener) {
        this.mData = mData;
        this.context = context;
        this.itemClickListener=itemClickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        TextView txt_text,txt_cost,desc,days;
        ImageView img_delete;

        public DataObjectHolder(View itemView){
            super(itemView);

            txt_text = (TextView)itemView.findViewById(R.id.txt_gigtitle);
            txt_cost = (TextView)itemView.findViewById(R.id.txt_cost);
            desc = (TextView)itemView.findViewById(R.id.txt_desc);
            days = (TextView)itemView.findViewById(R.id.txt_nodays);

            img_delete = (ImageView)itemView.findViewById(R.id.img_delete);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra_gig, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.txt_text.setText(mData.get(position).getTitle());
        holder.txt_cost.setText(mData.get(position).getCost());
        holder.desc.setText(mData.get(position).getDesc());
        holder.days.setText(mData.get(position).getDays());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
                itemClickListener.itemClick();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

