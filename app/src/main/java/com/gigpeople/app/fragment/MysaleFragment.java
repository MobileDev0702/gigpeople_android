package com.gigpeople.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.SalesDetailsActivity;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.apiModel.MySalesListResponse;
import com.gigpeople.app.subfragment.MySaleCancelledFragment;
import com.gigpeople.app.subfragment.MySaleCompletedFragment;
import com.gigpeople.app.subfragment.MySaleUpcomingFragment;
import com.gigpeople.app.subfragment.MysaleItemFragment;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MysaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MysaleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String sale_index, user_id;
    ApiService apiService;
    @BindView(R.id.txt_balance)
    TextView txtBalance;
    @BindView(R.id.total_earning)
    TextView totalEarning;
    @BindView(R.id.txt_revenue)
    TextView txtRevenue;
    String sales_id,sales_status;
    Context context;

    public static   List<MySalesListResponse.OrderRequestList>orderRequestLists;
    public static   List<MySalesListResponse.CurrentOrderList>currentOrderLists;
    public static   List<MySalesListResponse.CompletedOrderList>completedOrderLists;
    public static   List<MySalesListResponse.CancelledOrderList>cancelledOrderLists;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPagerAdapter adapter;

    public MysaleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MysaleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MysaleFragment newInstance(String param1, String param2) {
        MysaleFragment fragment = new MysaleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mysale, container, false);
        unbinder = ButterKnife.bind(this, view);
        preferences = getContext().getSharedPreferences("MyPref", 0);
        editor = preferences.edit();
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        context=getContext();

        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(getContext(), PrefConnect.USER_ID, "");
        sale_index = preferences.getString("sales_index", "");
        Log.e("SALE INDEX", sale_index);

        if (GlobalMethods.isNetworkAvailable(getContext())){
            callMysalesList();
        }else {
            GlobalMethods.Toast(getContext(),getString(R.string.internet));
        }

        sales_id= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_SALES_ID,"");
        sales_status= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_SALES_STATUS,"");
        Log.e("MySales","ID: "+sales_id+" STATUS: "+sales_status);

        if (!sales_id.equals("") && !sales_id.equals("1")) {

            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_ID, "");
            PrefConnect.writeString(getApplicationContext(), PrefConnect.FCM_SALES_STATUS, "");

            Intent intent = new Intent(context, SalesDetailsActivity.class);
            intent.putExtra("from","fcm");
            intent.putExtra("position",0);
            intent.putExtra("order_id",sales_id);
            context.startActivity(intent);
        }



        return view;
    }

    private void callMysalesList() {
        Log.e("MySalesListReq", "UserId: " + user_id);
        Call<MySalesListResponse> call = apiService.callmySales(user_id);
        call.enqueue(new Callback<MySalesListResponse>() {
            @Override
            public void onResponse(Call<MySalesListResponse> call, Response<MySalesListResponse> response) {
                Log.e("MySalesListResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    MySalesListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            orderRequestLists = new ArrayList<>();
                            orderRequestLists = resp.getOrderRequestList();

                            currentOrderLists = new ArrayList<>();
                            currentOrderLists = resp.getCurrentOrderList();

                            completedOrderLists = new ArrayList<>();
                            completedOrderLists = resp.getCompletedOrderList();

                            cancelledOrderLists = new ArrayList<>();
                            cancelledOrderLists = resp.getCancelledOrderList();

                            txtBalance.setText("$"+resp.getTotalBalance());
                            txtRevenue.setText("$"+resp.getRevenueOfMonth());
                            totalEarning.setText("$"+resp.getTotalEaarning());

                            if (viewpager != null) {
                                setupViewPager(viewpager);
                                tabs.setupWithViewPager(viewpager);
                            }

                           /* if (sale_index.equalsIgnoreCase("0")) {
                                TabLayout.Tab tab = tabs.getTabAt(0);
                                tab.select();
                            } else if (sale_index.equalsIgnoreCase("1")) {
                                TabLayout.Tab tab = tabs.getTabAt(1);
                                tab.select();
                            } else if (sale_index.equalsIgnoreCase("3")) {
                                TabLayout.Tab tab = tabs.getTabAt(3);
                                tab.select();
                            } else if (sale_index.equalsIgnoreCase("2")) {
                                TabLayout.Tab tab = tabs.getTabAt(2);
                                tab.select();
                            }*/

                            if (sales_status.equalsIgnoreCase("1")) {
                                TabLayout.Tab tab = tabs.getTabAt(0);
                                tab.select();
                            } else if (sales_status.equalsIgnoreCase("3")) {
                                TabLayout.Tab tab = tabs.getTabAt(1);
                                tab.select();
                            } else if (sales_status.equalsIgnoreCase("4")) {
                                TabLayout.Tab tab = tabs.getTabAt(3);
                                tab.select();
                            } else if (sales_status.equalsIgnoreCase("5")) {
                                TabLayout.Tab tab = tabs.getTabAt(2);
                                tab.select();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MySalesListResponse> call, Throwable t) {

            }
        });
    }
    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(MySaleUpcomingFragment.newInstance("1"), "Order Requests");
        adapter.addFragment(MysaleItemFragment.newInstance("2"), "Current");
        adapter.addFragment(MySaleCompletedFragment.newInstance("3"), "Completed");
        adapter.addFragment(MySaleCancelledFragment.newInstance("4"), "Cancelled");

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
            // PrefConnect.writeString(getContext(), PrefConnect.CATEGORY_ID, resp.getCategories().get(position).getCategoryId());

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
/*
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragmentList.remove(position);
            super.destroyItem(container, position, object);
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
