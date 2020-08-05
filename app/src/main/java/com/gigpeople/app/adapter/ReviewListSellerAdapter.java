package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.MoreReviewResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by praveensamuel on 26/01/19.
 */

public class ReviewListSellerAdapter extends RecyclerView.Adapter<ReviewListSellerAdapter.DataObjectHolder> {


    Context context;
    List<MoreReviewResponse.SellerReview> mData;
    String types;
    int selectposition = 0;

    public interface CallBack{
        public void call(String name);
    }


    public ReviewListSellerAdapter(Context context, List<MoreReviewResponse.SellerReview> mData) {

        this.context = context;
        this.mData = mData;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{

        ImageView imagFav;
        TextView txtMenu,txt_name,txt_description,txt_time;
        CircleImageView profile_img;
        RatingBar ratingBar;


        public DataObjectHolder(View itemView){
            super(itemView);

            imagFav = (ImageView)itemView.findViewById(R.id.img_fav);

            txt_name = (TextView)itemView.findViewById(R.id.txt_name);
            txt_description = (TextView)itemView.findViewById(R.id.txt_description);
            txt_time = (TextView)itemView.findViewById(R.id.txt_time);

            profile_img = (CircleImageView)itemView.findViewById(R.id.profile_img);

            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingbar);



        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_review_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
//holder.imagFav.setImageResource(mData.get(position).getImage());

        holder.txt_name.setText(mData.get(position).getFirstName()+" "+mData.get(position).getLastName());
        holder.txt_description.setText(mData.get(position).getReview());
        holder.txt_time.setText(GlobalMethods.DateTime12(mData.get(position).getIsDate()));
        holder.ratingBar.setRating(Float.parseFloat(mData.get(position).getRating()));
        if (!mData.get(position).getProfileImageUrl().equals("")){

            Glide.with(context).load(mData.get(position).getProfileImageUrl()).into(holder.profile_img);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

