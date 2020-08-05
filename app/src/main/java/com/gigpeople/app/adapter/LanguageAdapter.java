package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.model.ImageAddModel;

import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.DataObjectHolder> {

    Context context;
    List<ImageAddModel> mData;
    CallBack callBack;

    public interface CallBack {
        public void call();
    }

    public LanguageAdapter(Context context, List<ImageAddModel> mData) {
        this.context = context;
        this.mData = mData;
        this.callBack = callBack;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imageDelete;
        TextView status;

        public DataObjectHolder(View itemView) {
            super(itemView);
            status = (TextView) itemView.findViewById(R.id.img_one);
            imageDelete = (ImageView) itemView.findViewById(R.id.img_delete);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lang_add, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.status.setText(mData.get(position).getStatus());
        Log.e("STATUS", mData.get(position).getStatus());
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                notifyDataSetChanged();
                //callBack.call();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

