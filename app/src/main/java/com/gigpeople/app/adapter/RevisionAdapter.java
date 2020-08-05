package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class RevisionAdapter extends RecyclerView.Adapter<RevisionAdapter.DataObjectHolder> {


    Context context;
    List<MyOrdersDetailResponse.RevisionOrder> chat_list;
    String status = "0",user_id;

    public interface CallBack {
        public void call(String name);
    }

    public RevisionAdapter(Context context, List<MyOrdersDetailResponse.RevisionOrder> chat_list) {

        this.context = context;
        this.chat_list = chat_list;
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView name,txt_date,txt_description,txt_status;
        RoundedImageView user_image;

        public DataObjectHolder(View itemView) {
            super(itemView);

            user_image = (RoundedImageView) itemView.findViewById(R.id.user_image);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mysale_orders, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(chat_list.get(position).getFirstName() + " " + chat_list.get(position).getLastName());

        if (!chat_list.get(position).getProfileImageUrl().equals("")){
            Glide.with(context).load(chat_list.get(position).getProfileImageUrl()).into(holder.user_image);
        }

        holder.txt_date.setText(GlobalMethods.DateTime1( chat_list.get(position).getIsDate()));
        holder.txt_description.setText(chat_list.get(position).getMessage());
        if (chat_list.get(position).getStatus().equals("0")){
            holder.txt_status.setText("Revision Send");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.yellow));
        }else if (chat_list.get(position).getStatus().equals("1")){
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.txt_status.setText("Revision Accepted");
        }else if (chat_list.get(position).getStatus().equals("2")){
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.txt_status.setText("Revision Rejected");
        }
    }

    @Override
    public int getItemCount() {
        return chat_list.size();
    }

}
