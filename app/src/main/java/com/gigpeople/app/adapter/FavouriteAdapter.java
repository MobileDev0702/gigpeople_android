package com.gigpeople.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.GigOwnerDetailsActivity;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;

import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.FavouriteSellerListResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalApiMethod;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.DataObjectHolder> {


    Context context;
    List<FavouriteSellerListResponse.FavouritesellerList> fav_list;
    int fav_status = 0;
    ApiService apiService;

    public interface CallBack {
        public void call(String name);
    }


    public FavouriteAdapter(Context context, List<FavouriteSellerListResponse.FavouritesellerList> fav_list) {

        this.context = context;
        this.fav_list = fav_list;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        ImageView fav, mobileverified, emailverified;
        RatingBar ratingBar;
        TextView verified, notverified, nametxt, emailtxt;
        CircleImageView imageView;

        public DataObjectHolder(View itemView) {
            super(itemView);

            fav = (ImageView) itemView.findViewById(R.id.img_fav);
            verified = (TextView) itemView.findViewById(R.id.mobile_verified);
            notverified = (TextView) itemView.findViewById(R.id.mobile_notverified);

            nametxt = (TextView) itemView.findViewById(R.id.txtname);
            emailtxt = (TextView) itemView.findViewById(R.id.txtemil);
            imageView = (CircleImageView) itemView.findViewById(R.id.imgview);
            //ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
            mobileverified = (ImageView) itemView.findViewById(R.id.img_mobile);
            emailverified = (ImageView) itemView.findViewById(R.id.img_email);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourites, parent, false);
        apiService = RetrofitSingleton.createService(ApiService.class);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

//        holder.ratingBar.setRating(Float.parseFloat(fav_list.get(position).getRating()));
//        holder.fav.setImageResource(Integer.parseInt(fav_list.get(position).getStatus()));


        holder.nametxt.setText(fav_list.get(position).getFirstName()+" "+fav_list.get(position).getLastName());

        holder.emailtxt.setText(fav_list.get(position).getEmail());
        if (!fav_list.get(position).getProfileImageUrl().trim().equals("")) {
            Glide.with(context).load(fav_list.get(position).getProfileImageUrl()).into(holder.imageView);
        }

        if (fav_list.get(position).getIsMobileVerified().equals("1")) {
            holder.mobileverified.setImageResource(R.drawable.phone);

        } else {
            holder.mobileverified.setImageResource(R.drawable.phone_gray);
        }

        if (fav_list.get(position).getIsEmailVerified().equals("1")) {
            holder.emailverified.setImageResource(R.drawable.mail);

        } else {
            holder.emailverified.setImageResource(R.drawable.mail_gray);
        }


        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GlobalApiMethod.toClickAddfavourite(GlobalMethods.GetUserID(context), "1", fav_list.get(position).getSellerId(), context);
                //toClickAddfavourite("6","1","1");
                if (fav_status == 0) {
                    holder.fav.setImageResource(R.drawable.fav_line);
                    fav_status = 1;
                } else {
                    holder.fav.setImageResource(R.drawable.fav_icon);
                    fav_status = 0;
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GigOwnerDetailsActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("from_detail", "favourite");
                intent.putExtra("seller_id", fav_list.get(position).getSellerId());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return fav_list.size();
    }


  /*  private void toClickAddfavourite(String userid, String favourite_type,String favourite_id)
    {

        Call<CommonResponse> call = apiService.callFavouriteAdd(userid,favourite_type,favourite_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("Favoriteaddresp", new Gson().toJson(response.body()));
                {
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {





                    }else
                    {
                        GlobalMethods.Toast(context,resp.getMessage());
                    }
                }
            }


            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("Favoriteaddfail", t.getMessage());

            }
        });





    }
*/
}
