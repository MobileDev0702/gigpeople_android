package com.gigpeople.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalApiMethod {

    static ApiService apiService;
    static ProgressDialog progressDialog;

    public static void toClickAddfavourite(String userid, String favourite_type, String favourite_id, final Context context) {
        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        apiService = RetrofitSingleton.createService(ApiService.class);
        Log.e("FavoriteAddReq","UserId: "+userid+"\nFavaouriteType: "+favourite_type+"\nId: "+favourite_id);
        Call<CommonResponse> call = apiService.callFavouriteAdd(userid, favourite_type, favourite_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("FavoriteAddResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        GlobalMethods.Toast(context,resp.getMessage());
                    }else {
                        GlobalMethods.Toast(context,resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("Favoriteaddfail", t.getMessage());

            }
        });

    }
}
