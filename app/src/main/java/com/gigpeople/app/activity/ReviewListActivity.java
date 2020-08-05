package com.gigpeople.app.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.BuyerReviewAdapter;
import com.gigpeople.app.adapter.ReviewListAdapter;
import com.gigpeople.app.adapter.ReviewListSellerAdapter;
import com.gigpeople.app.adapter.UserReviewAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.MoreReviewResponse;
import com.gigpeople.app.apiModel.ProfileViewResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListActivity extends AppCompatActivity {

    ReviewListAdapter reviewListAdapter;
    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    List<MoreReviewResponse.BuyerReview> buyerReviewList;
    List<MoreReviewResponse.SellerReview> sellerReviewList;
    ReviewListSellerAdapter reviewListSellerAdapter;

    Context context;
    ApiService apiService;
    String user_id,review_list_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        ButterKnife.bind(this);
        context=ReviewListActivity.this;
        apiService= RetrofitSingleton.createService(ApiService.class);
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");
        buyerReviewList=new ArrayList<>();
        sellerReviewList=new ArrayList<>();
        Window window = ReviewListActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ReviewListActivity.this, R.color.colorPrimaryDark));
        }
        if (getIntent()!=null){

            review_list_from=getIntent().getStringExtra("review_list_from");

            Log.e("review_list_from",review_list_from);
            if (review_list_from.equals("3")){
                user_id=getIntent().getStringExtra("seller_id");
                Log.e("review_list_from",user_id);
            }
        }


        if (GlobalMethods.isNetworkAvailable(context)) {
            callReviewList();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

    }

    @OnClick(R.id.btn_back_arrow)
    public void onViewClicked() {
        onBackPressed();
    }


    private void callReviewList() {

        Log.e( "ReviewListReq: " ,"\nUserId: " + user_id);
        Call<MoreReviewResponse> call = apiService.calluserReviewList(user_id);
        call.enqueue(new Callback<MoreReviewResponse>() {
            @Override
            public void onResponse(Call<MoreReviewResponse> call, Response<MoreReviewResponse> response) {
                Log.e( "ReviewListResp: " , new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    MoreReviewResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                                buyerReviewList=resp.getBuyerReviews();
                                sellerReviewList=resp.getSellerReviews();

                            if (review_list_from.equals("1")){
                                reviewListAdapter = new ReviewListAdapter(ReviewListActivity.this,buyerReviewList);
                                recycleView.setAdapter(reviewListAdapter);
                                recycleView.setLayoutManager(new LinearLayoutManager(ReviewListActivity.this));

                            }else {
                                reviewListSellerAdapter = new ReviewListSellerAdapter(ReviewListActivity.this,sellerReviewList);
                                recycleView.setAdapter(reviewListSellerAdapter);
                                recycleView.setLayoutManager(new LinearLayoutManager(ReviewListActivity.this));

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MoreReviewResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
