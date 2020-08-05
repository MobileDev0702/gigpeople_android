package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.OfferSendRequestDetailsActivity;
import com.gigpeople.app.activity.RequestDetailsActivity;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class BuyerRequestSendOfferAdapter extends RecyclerView.Adapter<BuyerRequestSendOfferAdapter.DataObjectHolder> {

    Context context;

    String types;
    int selectposition = 0;
    public List<GigRequestOffersentResponse.RequestDetail> mData;

    public interface CallBack {
        public void call(String name);
    }

    public BuyerRequestSendOfferAdapter(Context context, List<GigRequestOffersentResponse.RequestDetail> mData) {

        this.context = context;
        this.mData = mData;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        ImageView imagFav, mobileverified, emailverified;
        TextView txtverified, txtname, txtcategoryname, txtdate;
        CircleImageView imageview;

        public DataObjectHolder(View itemView) {
            super(itemView);

            imagFav = (ImageView) itemView.findViewById(R.id.img_fav);
            txtverified = (TextView) itemView.findViewById(R.id.txt_verified);
            mobileverified = (ImageView) itemView.findViewById(R.id.img_mobile);
            emailverified = (ImageView) itemView.findViewById(R.id.img_email);
            imageview = (CircleImageView) itemView.findViewById(R.id.imageview);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtcategoryname = (TextView) itemView.findViewById(R.id.txt_categoryname);
            txtdate = (TextView) itemView.findViewById(R.id.txt_date);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buyrequest_newoffer_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txtname.setText(mData.get(position).getUserDetails().getFirstName()+" "+mData.get(position).getUserDetails().getLastName());
        holder.txtdate.setText( GlobalMethods.DateTime(mData.get(position).getUserDetails().getJoinDate()));
        holder.txtcategoryname.setText(mData.get(position).getCategoryName());
        if (!mData.get(position).getUserDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(mData.get(position).getUserDetails().getProfileImageUrl()).into(holder.imageview);
        }

        if (mData.get(position).getUserDetails().getIsMobileVerified().equals("1")) {
            holder.mobileverified.setImageResource(R.drawable.phone);

        } else {
            holder.mobileverified.setImageResource(R.drawable.phone_gray);
        }

        if (mData.get(position).getUserDetails().getIsEmailVerified().equals("1")) {
            holder.emailverified.setImageResource(R.drawable.mail);

        } else {
            holder.emailverified.setImageResource(R.drawable.mail_gray);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RequestDetailsActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("setlayout", "2");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

