package com.gigpeople.app.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevisionAdapterSales extends RecyclerView.Adapter<RevisionAdapterSales.DataObjectHolder> {


    Context context;
    List<SellerSalesDetailResponse.RevisionOrder> chat_list;
    String status = "0",user_id;
    ApiService apiService;
    ProgressDialog progressDialog;
    CallBack callBack;

    public interface CallBack {
        public void call(String status,String order_id,String msg_id);
    }

    public RevisionAdapterSales(Context context, List<SellerSalesDetailResponse.RevisionOrder> chat_list,CallBack callBack) {

        this.context = context;
        this.chat_list = chat_list;
        this.callBack=callBack;
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");
        apiService= RetrofitSingleton.createService(ApiService.class);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView name,txt_date,txt_description,txt_status,txt_revision_desc;
        RoundedImageView user_image;
        LinearLayout linear_accept_reject;
        Button btn_accept,btn_decline;

        public DataObjectHolder(View itemView) {
            super(itemView);

            user_image = (RoundedImageView) itemView.findViewById(R.id.user_image);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_revision_desc = (TextView) itemView.findViewById(R.id.txt_revision_desc);
            linear_accept_reject = (LinearLayout) itemView.findViewById(R.id.linear_accept_reject);
            btn_accept = (Button) itemView.findViewById(R.id.btn_accept);
            btn_decline = (Button) itemView.findViewById(R.id.btn_decline);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mysale_orders_sales, parent, false);

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
            holder.txt_status.setText("Revision Received");
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.yellow));
            holder.linear_accept_reject.setVisibility(View.VISIBLE);
        }else if (chat_list.get(position).getStatus().equals("1")){
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.txt_status.setText("Revision Accepted");
            holder.linear_accept_reject.setVisibility(View.GONE);
        }else if (chat_list.get(position).getStatus().equals("2")){
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.colorRed));
            holder.txt_status.setText("Revision Rejected");
            holder.linear_accept_reject.setVisibility(View.GONE);
        }

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.isNetworkAvailable(context)){
                    callBack.call("1",chat_list.get(position).getOrderId(),chat_list.get(position).getMsgId());
                }else {
                    GlobalMethods.Toast(context,context.getString(R.string.internet));
                }

            }
        });

        holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMethods.isNetworkAvailable(context)){
                    callBack.call("2",chat_list.get(position).getOrderId(),chat_list.get(position).getMsgId());
                }else {
                    GlobalMethods.Toast(context,context.getString(R.string.internet));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return chat_list.size();
    }

}
