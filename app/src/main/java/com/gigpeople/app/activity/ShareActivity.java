package com.gigpeople.app.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.fb_share)
    ImageView fbShare;
    @BindView(R.id.fb_google)
    ImageView fbGoogle;
    @BindView(R.id.fb_twitter)
    ImageView fbTwitter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        context = ShareActivity.this;
        Window window = ShareActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ShareActivity.this, R.color.colorPrimaryDark));
        }

    }


    @OnClick({R.id.img_back, R.id.fb_share, R.id.fb_google, R.id.fb_twitter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.fb_share:
                break;
            case R.id.fb_google:
                break;
            case R.id.fb_twitter:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
