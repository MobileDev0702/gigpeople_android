package com.gigpeople.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.solver.GoalRow;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.ForgotPasswordResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.img_back)
    ImageView imgBack;

    ApiService apiService;
    String email_id;

    ProgressDialog progressDialog;
    private static final String TAG =ForgotPasswordActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        context = ForgotPasswordActivity.this;
        ButterKnife.bind(this);
        apiService= RetrofitSingleton.createService(ApiService.class);
        Window window = ForgotPasswordActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ForgotPasswordActivity.this, R.color.colorPrimaryDark));
        }

    }

    @OnClick({R.id.btn_submit, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:

                if (GlobalMethods.isNetworkAvailable(context)){

                    if (Validation()){
                        callForgotPassword();
                    }

                }else {

                    GlobalMethods.Toast(context,getString(R.string.internet));
                }

                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    private void callForgotPassword() {

        email_id=edtEmail.getText().toString().trim();

        Log.e(TAG,"ForgotREQ: "+"\nemail_id"+email_id);
        progressDialog=ProgressDialog.show(context,"","",true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Call<ForgotPasswordResponse>call=apiService.callforgotPasswordAPI(email_id);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.dismiss();
                Log.e(TAG,"\nFORGOTSuccessResp: "+new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    ForgotPasswordResponse resp=response.body();

                    if (resp!=null){
                        if (resp.getStatus().equals("1")){
                            GlobalMethods.Toast(context,resp.getMessage()+" "+resp.getNewPassword());
                            onBackPressed();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                progressDialog.dismiss();
                Log.e(TAG,"failureFORGOT"+t.getMessage());

            }
        });
    }

    private boolean Validation(){

        if(edtEmail.getText().toString().equals("")){
            GlobalMethods.Toast(context,"Enter Email ID");
            return false;
        }else if (!edtEmail.getText().toString().contains("@")){
            GlobalMethods.Toast(context,"Enter Valid Email ID");
            return  false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
