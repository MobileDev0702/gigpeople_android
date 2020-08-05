package com.gigpeople.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MysaleItemActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_delivery)
    Button btnDelivery;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.linearAccepteReject)
    LinearLayout linearAccepteReject;
    @BindView(R.id.btn_cancel_refund)
    Button btnCancelRefund;
    @BindView(R.id.btn_deliver_now)
    Button btnDeliverNow;
    @BindView(R.id.linearcancelRefenddevlery)
    LinearLayout linearcancelRefenddevlery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysale_item);
        ButterKnife.bind(this);
        Window window = MysaleItemActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(MysaleItemActivity.this, R.color.colorPrimaryDark));
        }

        if(getIntent()!=null){
            String page = getIntent().getStringExtra("page");
            if(page.equalsIgnoreCase("1")){
                linearAccepteReject.setVisibility(View.VISIBLE);
                linearcancelRefenddevlery.setVisibility(View.GONE);
            }else {
                linearAccepteReject.setVisibility(View.GONE);
                linearcancelRefenddevlery.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_delivery, R.id.btn_cancel, R.id.btn_cancel_refund, R.id.btn_deliver_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_delivery:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_cancel_refund:
                break;
            case R.id.btn_deliver_now:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
