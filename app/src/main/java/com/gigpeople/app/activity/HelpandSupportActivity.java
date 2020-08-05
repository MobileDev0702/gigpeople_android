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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

/**
 * Created by praveensamuel on 27/01/19.
 */

public class HelpandSupportActivity extends AppCompatActivity {


    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.spinner_menu)
    Spinner spinnerMenu;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Context context;
    ApiService apiService;

    ProgressDialog progressDialog;
    String description, user_id, SelectedMenu;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    int dropDownSellectedPosition=-1;
    String dropdownmenu[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpandsupport);
        ButterKnife.bind(this);
        context = HelpandSupportActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        Window window = HelpandSupportActivity.this.getWindow();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        dropdownmenu = context.getResources().getStringArray(R.array.menu);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(HelpandSupportActivity.this, R.color.colorPrimaryDark));
        }
        ArrayAdapter adapter = ArrayAdapter.createFromResource(HelpandSupportActivity.this, R.array.menu, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerMenu.setAdapter(adapter);
    }


    @OnClick({R.id.btn_back_arrow, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_submit:
                validation();

              /*  Intent intent=new Intent(HelpandSupportActivity.this,MainActivity.class);
                intent.putExtra("page","10");
                startActivity(intent);*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void validation() {
        boolean cancel = false;

        description = edtDescription.getText().toString().trim();
        dropDownSellectedPosition = spinnerMenu.getSelectedItemPosition();

        if (dropDownSellectedPosition == 0) {
            cancel = true;
            GlobalMethods.Toast(context, "Select a menu");
        } else if (TextUtils.isEmpty(description)) {
            cancel = true;
            GlobalMethods.Toast(context, "Enter a querys");
        }

        SelectedMenu = dropdownmenu[dropDownSellectedPosition];

        if (!cancel) {

            if (GlobalMethods.isNetworkAvailable(HelpandSupportActivity.this)) {

                to_Click_ContactUs(user_id, SelectedMenu, description);

            } else {
                GlobalMethods.Toast(HelpandSupportActivity.this, getString(R.string.internet));
            }
        }
    }

    private void to_Click_ContactUs(String userid, String type, String description) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e("ContactUsReq","UserId: "+userid+"\nType: "+type+"\nDescription: "+description);
        Call<CommonResponse> call = apiService.callContactusrequest(userid, type, description);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("ContacatUsResp", new Gson().toJson(response.body()));
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
                Log.e("ContacatusRespfail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

}
