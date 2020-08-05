package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.ManageRequestOfferDetailsActivity;
import com.gigpeople.app.apiModel.GiGDetailsResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GigReviewAdapter extends RecyclerView.Adapter<GigReviewAdapter.DataObjectHolder> {


    Context context;
    List<GiGDetailsResponse.GigReview> reviewList;
    String status="0";
 int listsize;
    public interface CallBack{
        public void call(String name);
    }


    public GigReviewAdapter(Context context,List<GiGDetailsResponse.GigReview> reviewList ,int listsize) {

        this.context = context;
        this.reviewList=reviewList;
        this.listsize=listsize;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView txtreviwerNAme,txtReviewDescription,txtPostedtime;
        LinearLayout layout;
        CircleImageView image;
        RatingBar ratingBar;

        public DataObjectHolder(View itemView){
            super(itemView);

            txtreviwerNAme = (TextView) itemView.findViewById(R.id.txt_revievername);
            txtReviewDescription= (TextView) itemView.findViewById(R.id.txt_reviews);

            txtPostedtime=(TextView) itemView.findViewById(R.id.txtpostedtime);
            image = (CircleImageView) itemView.findViewById(R.id.reviewUserImage);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingbar);


        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gig_reviews, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {


         holder.txtreviwerNAme.setText(reviewList.get(position).getFirstName()+" "+reviewList.get(position).getLastName());
        if(!reviewList.get(position).getProfileImageUrl().equals("")) {
            Glide.with(context).load(reviewList.get(position).getProfileImageUrl()).into(holder.image);
        }
        holder.txtReviewDescription.setText(reviewList.get(position).getReview());
        holder.txtPostedtime.setText(GlobalMethods.DateTime1(reviewList.get(position).getIsDate()));
        Float rating= Float.valueOf(reviewList.get(position).getRating());

        holder.ratingBar.setRating(rating);

/*
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,ManageRequestOfferDetailsActivity.class);
        context.startActivity(intent);
    }
});



*/





    }


    @Override
    public int getItemCount() {
        return listsize;
    }


}
