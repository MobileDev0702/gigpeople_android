package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.NotificationResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.sql.Types.TIME;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    List<NotificationResponse.NotificationList> notificationList;
    String page="0";

    public NotificationAdapter(Context context, List<NotificationResponse.NotificationList> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.details.setText(notificationList.get(position).getBody());
        holder.txt_title.setText(notificationList.get(position).getTitle());
        String dateandtime = notificationList.get(position).getCreatedAt();

        if (dateandtime.contains(" ")) {
            String temp[] = dateandtime.split(" ");
            holder.date.setText(temp[0]);
            String timeconvert = GlobalMethods.TimeConversion24to12(temp[1]);
            holder.txt_mins.setText(timeconvert);
        }

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorBgGray));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ids;
                String status=notificationList.get(position).getStatus();

                if (status.contains("-")){
                    String TEMP[]=status.split("-");
                    status=TEMP[0];
                    ids=TEMP[1];
                    if (status.equals("2")) {
                        PrefConnect.writeString(context, PrefConnect.FCM_GIG_ID, ids);
                        PrefConnect.writeString(context, PrefConnect.FCM_GIG_STATUS, TEMP[2]);

                    }else if (status.equals("4")) {
                        PrefConnect.writeString(context, PrefConnect.FCM_ORDER_ID, ids);
                        PrefConnect.writeString(context, PrefConnect.FCM_ORDER_STATUS, TEMP[2]);

                    }else if (status.equals("7")) {
                        PrefConnect.writeString(context, PrefConnect.FCM_SALES_ID, ids);
                        PrefConnect.writeString(context, PrefConnect.FCM_SALES_STATUS, TEMP[2]);

                    }
                }else {
                    PrefConnect.writeString(context,PrefConnect.FCM_GIG_ID,"");
                    PrefConnect.writeString(context,PrefConnect.FCM_GIG_STATUS,"");
                    PrefConnect.writeString(context,PrefConnect.FCM_ORDER_ID,"");
                    PrefConnect.writeString(context,PrefConnect.FCM_ORDER_STATUS,"");
                    PrefConnect.writeString(context,PrefConnect.FCM_SALES_ID,"");
                    PrefConnect.writeString(context,PrefConnect.FCM_SALES_ID,"");
                }

                switch (status){
                    case "0"://Gig Request
                        page="0";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("page", page);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                        break;
                    case "1"://Gig Request
                        page="1";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent1 = new Intent(context, MainActivity.class);
                        intent1.putExtra("page", page);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);

                        break;
                    case "2"://My Gig Fragment
                        page="2";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent2 = new Intent(context, MainActivity.class);
                        intent2.putExtra("page", page);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent2);

                        break;
                    case "3"://Manage Request
                        page="4";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent3 = new Intent(context, MainActivity.class);
                        intent3.putExtra("page", page);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent3);

                        break;
                    case "4"://My Orders
                        page="5";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent4 = new Intent(context, MainActivity.class);
                        intent4.putExtra("page", page);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent4);

                        break;
                    case "5"://Favourite
                        page="6";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent5 = new Intent(context, MainActivity.class);
                        intent5.putExtra("page", page);
                        intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent5);

                        break;
                    case "6"://Chat
                        page="11";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent6 = new Intent(context, MainActivity.class);
                        intent6.putExtra("page", page);
                        intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent6);

                        break;
                    case "7"://My sale
                        page="3";
                        Log.e("adapter","notification: "+":page: "+page+" status: "+notificationList.get(position).getStatus());
                        Intent intent7 = new Intent(context, MainActivity.class);
                        intent7.putExtra("page", page);
                        intent7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent7);
                        break;
                }



            }
        });

    }

    @Override
    public int getItemCount() {

        return notificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView details, date, txt_mins,txt_title;

        public MyViewHolder(View itemView) {
            super(itemView);

            details = (TextView) itemView.findViewById(R.id.txt_notification);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_mins = (TextView) itemView.findViewById(R.id.txt_time);
            txt_title=(TextView)itemView.findViewById(R.id.txt_title);

        }
    }
}