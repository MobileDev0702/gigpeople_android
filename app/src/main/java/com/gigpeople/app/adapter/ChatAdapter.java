package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.ChatDetailsActivity;
import com.gigpeople.app.activity.MessengerActivity;
import com.gigpeople.app.apiModel.ChatListResponse;
import com.gigpeople.app.model.ChatModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.DataObjectHolder> {

    Context context;
    List<ChatListResponse.ChatList> chat_list;
    String TIME;

    public interface CallBack {
        public void call(String name);
    }

    public ChatAdapter(Context context, List<ChatListResponse.ChatList> chat_list) {

        this.context = context;
        this.chat_list = chat_list;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView name, message, txt_count, txt_time;
        LinearLayout layout;
        CircleImageView profile_img;

        public DataObjectHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_count = (TextView) itemView.findViewById(R.id.count);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            profile_img = (CircleImageView) itemView.findViewById(R.id.profile_img);
            message = (TextView) itemView.findViewById(R.id.txt_chat);
            layout = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(chat_list.get(position).getToUserDetails().getFirstName() + " " + chat_list.get(position).getToUserDetails().getLastName());

        if (chat_list.get(position).getMessage().contains("http")) {
            holder.message.setText("Image");

        } else {
            holder.message.setText(chat_list.get(position).getMessage());
        }
        String TIME = setTimeString(chat_list.get(position).getCreatedAt());

        if (TIME.equals("0 minutes ago")) {
            holder.txt_time.setText("Just Now");
        } else {
            holder.txt_time.setText(TIME);
        }
        if (!chat_list.get(position).getMessageCount().equals("0")) {

            holder.txt_count.setText(chat_list.get(position).getMessageCount());

        } else {

            holder.txt_count.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(chat_list.get(position).getToUserDetails().getProfileImageUrl())) {
            Glide.with(context).load(chat_list.get(position).getToUserDetails().getProfileImageUrl()).into(holder.profile_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = chat_list.get(position).getToUserDetails().getFirstName() + " " + chat_list.get(position).getToUserDetails().getLastName();

                Intent intent = new Intent(context, ChatDetailsActivity.class);
                intent.putExtra("other_user_id", chat_list.get(position).getToUserDetails().getUserId());
                intent.putExtra("other_user_name", name);
                intent.putExtra("other_user_image", chat_list.get(position).getToUserDetails().getProfileImageUrl());
                context.startActivity(intent);

            }
        });

        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorBgGray));
        } else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date mDate = sdf.parse(givenDateString);
            long_time = mDate.getTime();
            Log.e("Date in milli", time + " ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        Log.e("TIME", TIME + " nan ");
        return TIME;
    }

    @Override
    public int getItemCount() {
        return chat_list.size();
    }


}
