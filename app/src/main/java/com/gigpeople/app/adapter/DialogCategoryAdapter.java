package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.model.MainCategory;

import java.util.List;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class DialogCategoryAdapter extends RecyclerView.Adapter<DialogCategoryAdapter.DataObjectHolder> {


    Context context;
    List<MainCategory> mData;
    String types;
    int selectposition = 0;
    CallBack callBack;

    public interface CallBack{
        public void call();
    }


    public DialogCategoryAdapter(Context context, List<MainCategory> mData, CallBack callBack) {

        this.context = context;
        this.mData = mData;
        this.callBack = callBack;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView txtMenu;


        public DataObjectHolder(View itemView){
            super(itemView);


            txtMenu = (TextView)itemView.findViewById(R.id.txt_name);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dialog_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


        holder.txtMenu.setText(mData.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.call();
            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}

