package com.gigpeople.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gigpeople.app.R;
import com.gigpeople.app.fragment.AnalyticsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalyticsActivity extends AppCompatActivity {


    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.img_back)
    ImageView imgBack;
    String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        ButterKnife.bind(this);
        page=getIntent().getStringExtra("page");
        Log.e("AnalyticsPagw",page);
        if(getIntent()!=null)
        {
            if(page.equals("1"))
            {
                beginTransction(new AnalyticsFragment());
            }
        }


    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void beginTransction(final Fragment fragment) {
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 250);*/
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.framelayout, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
