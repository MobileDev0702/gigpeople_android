package com.gigpeople.app.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.PaymentHistoryResponse;
import com.gigpeople.app.subfragment.AmountEarnedFragment;
import com.gigpeople.app.subfragment.AmountSpentFragment;
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

public class PaymentHistoryActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    ViewPagerAdapter adapter;
    String user_id;
    ApiService apiService;
    Context context;
    public static List<PaymentHistoryResponse.AmountEarning> amountEarnedList;
    public static List<PaymentHistoryResponse.AmountSpending> amountSpendingList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        ButterKnife.bind(this);
        Window window = PaymentHistoryActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(PaymentHistoryActivity.this, R.color.colorPrimaryDark));
        }
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));


        amountEarnedList=new ArrayList<>();
        amountSpendingList=new ArrayList<>();

        context=PaymentHistoryActivity.this;
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");
        apiService= RetrofitSingleton.createService(ApiService.class);
        if (GlobalMethods.isNetworkAvailable(context)){
            setPaymentHistory();
        }else {
            GlobalMethods.Toast(context,getString(R.string.internet));
        }
    }

    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(AmountSpentFragment.newInstance(), "Amount Spent");
        adapter.addFragment(AmountEarnedFragment.newInstance(), "Amount Earned");

        viewpager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    private void setPaymentHistory() {
        Log.e("PaymentHistoryReq","UserId: "+user_id);
        Call<PaymentHistoryResponse> call=apiService.callPaymentHistory(user_id);
        call.enqueue(new Callback<PaymentHistoryResponse>() {
            @Override
            public void onResponse(Call<PaymentHistoryResponse> call, Response<PaymentHistoryResponse> response) {
                Log.e("PaymentHistoryResponse",new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    PaymentHistoryResponse res=response.body();
                    if (res!=null){
                        if (res.getStatus().equals("1")){
                            amountEarnedList=res.getAmountEarning();
                            amountSpendingList=res.getAmountSpending();

                            setupViewPager(viewpage);
                            tabs.setupWithViewPager(viewpage);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentHistoryResponse> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
