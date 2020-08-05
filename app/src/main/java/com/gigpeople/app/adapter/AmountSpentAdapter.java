package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gigpeople.app.R;
import com.gigpeople.app.activity.ActiveOrdersActivity;
import com.gigpeople.app.activity.DeliveredOrdersActivity;
import com.gigpeople.app.apiModel.PaymentHistoryResponse;
import com.gigpeople.app.model.AmountSpentModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AmountSpentAdapter extends RecyclerView.Adapter<AmountSpentAdapter.DataObjectHolder> {

    Context context;
    List<PaymentHistoryResponse.AmountSpending> amount;
    String status = "0";

    public interface CallBack {
        public void call(String name);
    }

    public AmountSpentAdapter(Context context, List<PaymentHistoryResponse.AmountSpending> amount) {

        this.context = context;
        this.amount = amount;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        TextView amount, date,txt_title,txt_description;

        public DataObjectHolder(View itemView) {
            super(itemView);

            amount = (TextView) itemView.findViewById(R.id.txt_amount);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amount_spent, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.amount.setText("$"+amount.get(position).getPrice());
       // holder.date.setText(amount.get(position).getDate());
        holder.txt_title.setText(amount.get(position).getTitle());
        holder.txt_description.setText(amount.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActiveOrdersActivity.class);
                intent.putExtra("page", "amount_spend");
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

}
