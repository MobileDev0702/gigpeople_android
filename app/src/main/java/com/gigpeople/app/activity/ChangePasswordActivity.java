package com.gigpeople.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String currentPassword, newPassword, confirmnewPassword, user_id;
    @BindView(R.id.edt_oldpassword)
    EditText edtOldpassword;
    @BindView(R.id.edt_newpassword)
    EditText edtNewpassword;
    @BindView(R.id.edt_connewpassword)
    EditText edtConnewpassword;
    Context context;
    ApiService apiService;

    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        context = ChangePasswordActivity.this;
        Window window = ChangePasswordActivity.this.getWindow();
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ChangePasswordActivity.this, R.color.colorPrimaryDark));
        }
    }

    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                loginvalidation();
                break;
        }
    }

    private void loginvalidation() {

        boolean cancel = false;
        currentPassword = edtOldpassword.getText().toString();
        newPassword = edtNewpassword.getText().toString();
        confirmnewPassword = edtConnewpassword.getText().toString();

        if (TextUtils.isEmpty(currentPassword)) {
            cancel = true;
            GlobalMethods.Toast(context, "Enter Current Password");
        } else if (TextUtils.isEmpty(newPassword)) {
            cancel = true;
            GlobalMethods.Toast(context, "Enter New Password");
        } else if (TextUtils.isEmpty(confirmnewPassword)) {
            cancel = true;
            GlobalMethods.Toast(context, "Confirm New Password");
        } else if (!newPassword.equals(confirmnewPassword)) {
            cancel = true;
            GlobalMethods.Toast(context, "Password doesn't match");
        }

        if (!cancel) {

            if (GlobalMethods.isNetworkAvailable(ChangePasswordActivity.this)) {
                calltoChangePassword(user_id, currentPassword, newPassword);

            } else {
                GlobalMethods.Toast(ChangePasswordActivity.this, getString(R.string.internet));
            }
        }
    }

    private void calltoChangePassword(String userID, String curPass, String newpass) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("ChangePassResp","UserId: "+userID+"\nCurrentPassword: "+curPass+"\nNewPassword: "+newpass);
        Call<CommonResponse> call = apiService.callChangepassword(userID, curPass, newpass);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("ChangePassResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("ChangePassFail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}
