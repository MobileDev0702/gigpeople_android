package com.gigpeople.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends AppCompatActivity {


    Context context;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_selected_paypal)
    ImageView imgSelectedPaypal;
    @BindView(R.id.paypal_layout)
    LinearLayout paypalLayout;
    @BindView(R.id.img_selected_bank)
    ImageView imgSelectedBank;
    @BindView(R.id.bank_layout)
    LinearLayout bankLayout;
    String paypal_status = "1";
    String bank_status = "0";
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);
        context = WithdrawalActivity.this;

        Window window = WithdrawalActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(WithdrawalActivity.this, R.color.colorPrimaryDark));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.img_back, R.id.paypal_layout, R.id.bank_layout,R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.paypal_layout:
                if (paypal_status.equalsIgnoreCase("1")) {
                    imgSelectedPaypal.setVisibility(View.VISIBLE);
                    imgSelectedBank.setVisibility(View.GONE);
                    paypal_status = "0";
                    bank_status = "1";
                } else {
                    imgSelectedPaypal.setVisibility(View.GONE);
                    paypal_status = "1";
                    bank_status = "0";

                }

                break;
            case R.id.bank_layout:
                if (bank_status.equalsIgnoreCase("0")) {
                    imgSelectedBank.setVisibility(View.VISIBLE);
                    imgSelectedPaypal.setVisibility(View.GONE);
                    bank_status = "1";
                    paypal_status = "0";
                } else {
                    imgSelectedBank.setVisibility(View.GONE);
                    bank_status = "0";
                    paypal_status = "1";

                }
                break;
            case R.id.btn_submit:
               Intent intent=new Intent(WithdrawalActivity.this,MainActivity.class);
               intent.putExtra("page","8");
               startActivity(intent);
                break;

        }
    }
}
