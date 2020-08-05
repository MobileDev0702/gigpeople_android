package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.ChatDetailsActivity;
import com.gigpeople.app.activity.RefundDisputeActivity;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.model.DisputeModel;

import java.util.List;

public class DisputeAdapter extends RecyclerView.Adapter<DisputeAdapter.DataObjectHolder> {


    Context context;
    List<DisputeModel> disput_list;
    String status="0";

    public interface CallBack{
        public void call(String name);
    }


    public DisputeAdapter(Context context, List<DisputeModel> disput_list, String status) {

        this.context = context;
        this.disput_list = disput_list;
        this.status=status;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView amount,category;
        Button open;
        public DataObjectHolder(View itemView){
            super(itemView);

            amount = (TextView)itemView.findViewById(R.id.txt_amount);
            category = (TextView)itemView.findViewById(R.id.txt_cat);
            open = (Button) itemView.findViewById(R.id.btn_open);


        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dispute, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.category.setText(disput_list.get(position).getName());
        holder.amount.setText(disput_list.get(position).getAmount());

holder.open.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,RefundDisputeActivity.class);
        context.startActivity(intent);
    }
});






    }


    @Override
    public int getItemCount() {
        return disput_list.size();
    }


}
