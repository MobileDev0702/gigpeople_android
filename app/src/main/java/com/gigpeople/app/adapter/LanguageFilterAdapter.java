package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.InnerLanguageTagModel;

import java.util.List;


public class LanguageFilterAdapter extends RecyclerView.Adapter<LanguageFilterAdapter.DataObjectHolder> {

    Context context;
    List<InnerLanguageTagModel> mData;
    CallBack callBack;

    public interface CallBack {
        public void call();
    }

    public LanguageFilterAdapter(Context context, List<InnerLanguageTagModel> mData) {
        this.context = context;
        this.mData = mData;
        this.callBack = callBack;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        TextView txtLangName;
        ImageView imgTick;
        LinearLayout linearLayouy;


        public DataObjectHolder(View itemView) {
            super(itemView);
            txtLangName = (TextView) itemView.findViewById(R.id.txt_langname);
            imgTick = (ImageView)itemView.findViewById(R.id.lang_tick);
            linearLayouy = (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_language_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
//btn_bg_lightgray_fill

        holder.txtLangName.setText(mData.get(position).getLangName());

        if(mData.get(position).getLangStatus().equals("1")){

            holder.linearLayouy.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_orange_fill));
            holder.imgTick.setVisibility(View.VISIBLE);
            holder.txtLangName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.linearLayouy.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_lightgray_fill));
            holder.imgTick.setVisibility(View.GONE);
            holder.txtLangName.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mData.get(position).getLangStatus().equals("1")){

                    mData.get(position).setLangStatus("0");

                }else {
                    mData.get(position).setLangStatus("1");
                }
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

