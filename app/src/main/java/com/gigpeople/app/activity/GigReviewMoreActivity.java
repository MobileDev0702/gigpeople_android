package com.gigpeople.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.GigReviewAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.GiGDetailsResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GigReviewMoreActivity extends AppCompatActivity {

    ApiService apiService;
    Context context;
    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    List<GiGDetailsResponse.GigReview> reviewList;
    GigReviewAdapter adapter;

    String GigId,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigreviewmoreactivity);
        ButterKnife.bind(this);
        context = GigReviewMoreActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);

        if (getIntent() != null) {
            GigId = getIntent().getStringExtra("gigid");
            user_id = getIntent().getStringExtra("user_id");

            if (GlobalMethods.isNetworkAvailable(context)) {
                setGigDetail(user_id, GigId);
            } else {
                GlobalMethods.Toast(context, getString(R.string.internet));
            }
        }
    }

    @OnClick(R.id.btn_back_arrow)
    public void onViewClicked() {

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setGigDetail(String user_id, String Gigid) {
        Log.e("RequestDetailReq", "UserId:" + user_id + " RequestId: " + Gigid);
        Call<GiGDetailsResponse> call = apiService.callgigDetails(user_id, Gigid);
        call.enqueue(new Callback<GiGDetailsResponse>() {
            @Override
            public void onResponse(Call<GiGDetailsResponse> call, Response<GiGDetailsResponse> response) {
                Log.e("RequestDetailResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GiGDetailsResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            reviewList = new ArrayList<>();
                            reviewList = resp.getGigReviews();

                            adapter = new GigReviewAdapter(GigReviewMoreActivity.this, reviewList, reviewList.size());
                            recycleView.setAdapter(adapter);
                            recycleView.setLayoutManager(new LinearLayoutManager(GigReviewMoreActivity.this));


                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<GiGDetailsResponse> call, Throwable t) {

                Log.e("failure", t.getMessage());

            }
        });

    }

}