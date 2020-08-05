package com.gigpeople.app.adapter;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.model.UserDetailsOther;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by uniflyn on 8/11/17.
 */

public class AdapterChatDetail extends RecyclerView.Adapter<AdapterChatDetail.ViewHolder> {

    Context context;
    String my_id = "3", other_user_image, user_image;
    private int SELF = 100, OTHRES = 200;
    private int SELFTWO;
    List<UserDetailsOther> userDetailsOthers = new ArrayList<>();

    public AdapterChatDetail(Context context, List<UserDetailsOther> userDetailsOthers1, String other_user_image) {
        this.context = context;
        this.other_user_image = other_user_image;
        my_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        user_image = PrefConnect.readString(context, PrefConnect.IMAGE_URL, "");
        this.userDetailsOthers = userDetailsOthers1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_to, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_from, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        UserDetailsOther userDetailsOther = userDetailsOthers.get(position);
        if (userDetailsOther.getUserid().equals(my_id)) {
            SELFTWO = 300;
            return SELF;
        } else {
            SELFTWO = 400;
        }
        return OTHRES;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (SELFTWO == 300) {

            Log.e("SELFTWO", SELFTWO + "");
            if (userDetailsOthers.get(position).getType().equals("1")) {
                Log.e("300type1", userDetailsOthers.get(position).getType());
                holder.txtMsg.setVisibility(View.VISIBLE);
                holder.chat_image.setVisibility(View.GONE);
                holder.txtMsg.setText(userDetailsOthers.get(position).getUserMessage());
                holder.txt_time.setText(userDetailsOthers.get(position).getTime());
                if (!TextUtils.isEmpty(user_image)) {
                    Glide.with(context).load(user_image).into(holder.userImag);
                }
            } else {
                Log.e("300type2", userDetailsOthers.get(position).getType());
                holder.txtMsg.setVisibility(View.GONE);
                holder.chat_image.setVisibility(View.VISIBLE);
                holder.txt_time.setText(userDetailsOthers.get(position).getTime());
                if (!TextUtils.isEmpty(user_image)) {
                    Glide.with(context).load(user_image).into(holder.userImag);
                }
                Glide.with(context).load(userDetailsOthers.get(position).getUserMessage()).into(holder.chat_image);
            }
        } else if (SELFTWO == 400) {

            Log.e("SELFTWO", SELFTWO + "");
            if (userDetailsOthers.get(position).getType().equals("1")) {
                holder.txtMsg.setVisibility(View.VISIBLE);
                holder.chat_image.setVisibility(View.GONE);
                holder.txtMsg.setText(userDetailsOthers.get(position).getUserMessage());
                holder.txt_time.setText(userDetailsOthers.get(position).getTime());
                if (!TextUtils.isEmpty(other_user_image)) {
                Glide.with(context).load(other_user_image).into(holder.userImag);
                }
            } else {
                holder.txtMsg.setVisibility(View.GONE);
                holder.chat_image.setVisibility(View.VISIBLE);
                holder.txt_time.setText(userDetailsOthers.get(position).getTime());
                Glide.with(context).load(userDetailsOthers.get(position).getUserMessage()).into(holder.chat_image);
                if (!TextUtils.isEmpty(other_user_image)) {
                    Glide.with(context).load(other_user_image).into(holder.userImag);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return userDetailsOthers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMsg, txt_time;
        CircleImageView userImag;
        ImageView chat_image;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMsg = (TextView) itemView.findViewById(R.id.txt_msg);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            chat_image = (ImageView) itemView.findViewById(R.id.chat_image);
            userImag = (CircleImageView) itemView.findViewById(R.id.userImag);
        }
    }

}
