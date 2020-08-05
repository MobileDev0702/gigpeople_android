package com.gigpeople.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.apiModel.ProfileViewResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.utils.GlobalMethods;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.DataObjectHolder> {


    Context context;
    List<ProfileViewResponse.SellerReview> sellerReviewList;
    String status="0";

    public interface CallBack{
        public void call(String name);
    }


    public UserReviewAdapter(Context context, List<ProfileViewResponse.SellerReview> sellerReviewList) {

        this.context = context;
        this.sellerReviewList=sellerReviewList;

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView txt_name,txt_description,txt_time,message;
        CircleImageView profile_img;
        RatingBar ratingBar;
        LinearLayout layout;
        public DataObjectHolder(View itemView){
            super(itemView);

            message=(TextView)itemView.findViewById(R.id.overall);
            txt_time=(TextView)itemView.findViewById(R.id.txt_time);
            txt_description=(TextView)itemView.findViewById(R.id.txt_description);
            txt_name=(TextView)itemView.findViewById(R.id.txt_name);
            profile_img=(CircleImageView)itemView.findViewById(R.id.profile_img);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingbar);




        }
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_reviews, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
if(position==0)
{
    holder.message.setVisibility(View.GONE);
}
if(position==1)
{
    holder.message.setVisibility(View.GONE);

}


        holder.txt_name.setText(sellerReviewList.get(position).getFirstName()+" "+sellerReviewList.get(position).getLastName());
        holder.txt_description.setText(sellerReviewList.get(position).getReview());
        holder.txt_time.setText( GlobalMethods.DateTime12(sellerReviewList.get(position).getIsDate()));
        holder.ratingBar.setRating(Float.parseFloat(sellerReviewList.get(position).getRating()));

        if (!sellerReviewList.get(position).getProfileImageUrl().equals("")){

            Glide.with(context).load(sellerReviewList.get(position).getProfileImageUrl()).into(holder.profile_img);
        }
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
        return sellerReviewList.size();
    }


}
