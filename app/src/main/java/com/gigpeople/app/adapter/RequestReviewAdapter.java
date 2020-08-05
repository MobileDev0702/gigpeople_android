package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.ChatDetailsActivity;
import com.gigpeople.app.activity.ManageRequestOfferDetailsActivity;
import com.gigpeople.app.apiModel.RequestDetailResponse;
import com.gigpeople.app.model.ChatModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestReviewAdapter extends RecyclerView.Adapter<RequestReviewAdapter.DataObjectHolder> {

    Context context;
    List<RequestDetailResponse.OfferDetail> offerDetailList;
    String status = "0";
    String request_id;

    public interface CallBack {
        public void call(String name);
    }

    public RequestReviewAdapter(Context context, List<RequestDetailResponse.OfferDetail> offerDetailList, String request_id) {

        this.context = context;
        this.offerDetailList = offerDetailList;
        this.request_id = request_id;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        CircleImageView img;
        LinearLayout layout;
        RatingBar ratingBar;

        public DataObjectHolder(View itemView) {
            super(itemView);

            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            img = (CircleImageView) itemView.findViewById(R.id.img);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.txt_name.setText(offerDetailList.get(position).getFirstName() + " " + offerDetailList.get(position).getLastName());
        if (!offerDetailList.get(position).getProfileImageUrl().equals("")) {
            Glide.with(context).load(offerDetailList.get(position).getProfileImageUrl()).into(holder.img);
        }

        if (offerDetailList.get(position).getRating() != null) {
            holder.ratingBar.setRating(Float.parseFloat(offerDetailList.get(position).getRating()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageRequestOfferDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("request_id", request_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return offerDetailList.size();
    }

}
