package com.gigpeople.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.NotificationAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.NotificationResponse;
import com.gigpeople.app.model.NotificationModel;
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

public class NotificationListActivity extends AppCompatActivity {

    Context context;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.recycler_notify)
    RecyclerView recyclerNotify;
    List<NotificationResponse.NotificationList> notificationLists;
    ProgressDialog progressDialog;
    ApiService apiService;
    String user_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        ButterKnife.bind(this);
        Window window = NotificationListActivity.this.getWindow();
        context = NotificationListActivity.this;
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(NotificationListActivity.this, R.color.colorPrimaryDark));
        }

        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id= PrefConnect.readString(context, PrefConnect.USER_ID,"");

        if (GlobalMethods.isNetworkAvailable(context)) {
            callUpdate();
            callNotification();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

    }

    private void callNotification() {

        notificationLists = new ArrayList<>();

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("NotificationListResp", "UserId: " + user_id);
        Call<NotificationResponse> call = apiService.callNotificationList(user_id);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                progressDialog.dismiss();
                Log.e("NotificationListResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    notificationLists = response.body().getNotificationList();
                    if (recyclerNotify != null) {
                        notificationAdapter = new NotificationAdapter(context, notificationLists);
                        recyclerNotify.setAdapter(notificationAdapter);
                        recyclerNotify.setLayoutManager(new LinearLayoutManager(context));
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.e("NotifiListRespFail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void callUpdate() {

        Log.e("NotificationUpdateResp", "UserId: " + user_id);
        Call<CommonResponse> call = apiService.callUpdateNoti(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("NotificationUpdateResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("NotifiListRespFail", t.getMessage());
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

}
