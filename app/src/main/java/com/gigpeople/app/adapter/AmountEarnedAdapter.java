package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.ActiveOrdersActivity;
import com.gigpeople.app.activity.CompletedSalesDetailsActivity;
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.activity.UpcomingSalesDetailsActivity;
import com.gigpeople.app.apiModel.PaymentHistoryResponse;
import com.gigpeople.app.model.AmountSpentModel;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

public class AmountEarnedAdapter extends RecyclerView.Adapter<AmountEarnedAdapter.DataObjectHolder> {

    Context context;
    List<PaymentHistoryResponse.AmountEarning> amount;
    String status = "0";

    public interface CallBack {
        public void call(String name);
    }

    public AmountEarnedAdapter(Context context, List<PaymentHistoryResponse.AmountEarning> amount) {

        this.context = context;
        this.amount = amount;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView amount, date,txt_title,txt_desc;

        public DataObjectHolder(View itemView) {
            super(itemView);

            amount = (TextView) itemView.findViewById(R.id.txt_amount);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_desc = (TextView) itemView.findViewById(R.id.txt_desc);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amount_earned, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.amount.setText("$"+amount.get(position).getPrice());
        holder.date.setText(GlobalMethods.DateTime13(amount.get(position).getOrderDate()));
        holder.txt_title.setText(amount.get(position).getCategoryName());
        holder.txt_desc.setText(amount.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SalesDetailsActivity.class);
                intent.putExtra("from","amount_earned");
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

}
