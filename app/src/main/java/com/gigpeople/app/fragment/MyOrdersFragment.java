package com.gigpeople.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.gigpeople.app.activity.ActiveOrdersActivity;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.AnalyticsReportResponse;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
import com.gigpeople.app.subfragment.ActiveFragment;
import com.gigpeople.app.subfragment.DeliveredFragment;
import com.gigpeople.app.subfragment.OrdersActiveFragment;
import com.gigpeople.app.subfragment.OrdersCancelledFragment;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
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
 * Use the {@link MyOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    TextView txt_batch_count_tab, txt_view;
    String[] title_tab = {"Active", "Delivered", "Cancelled"};
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPagerAdapter adapter;
    ApiService apiService;
    ProgressDialog progressDialog;
    ArrayList<String> count;
    Context context;
    String order_id,order_status;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrdersFragment newInstance(String param1, String param2) {
        MyOrdersFragment fragment = new MyOrdersFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        apiService = RetrofitSingleton.createService(ApiService.class);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));

        if (GlobalMethods.isNetworkAvailable(context)) {
            tocallOrdersList(GlobalMethods.GetUserID(context));
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        order_id= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_ORDER_ID,"");
        order_status= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_ORDER_STATUS,"");
        Log.e("MyOrderGig","ID: "+order_id+" STATUS: "+order_status);

        if (!order_id.equals("")){

            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_ORDER_ID,"");
            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_ORDER_STATUS,"");

            Intent intent = new Intent(context, ActiveOrdersActivity.class);
            intent.putExtra("page", "fcm");
            intent.putExtra("position", 0);
            intent.putExtra("order_id", order_id);
            context.startActivity(intent);
        }




        return view;
    }

    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OrdersActiveFragment(), "Active");
        adapter.addFragment(new DeliveredFragment(), "Delivered");
        adapter.addFragment(new OrdersCancelledFragment(), "Cancelled");

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setTablayout(ArrayList<String> count) {

        tabs.setupWithViewPager(viewpager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view_tab = tab.getCustomView();
                txt_view = (TextView) view_tab.findViewById(R.id.txt_view);
                txt_view.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view_tab = tab.getCustomView();
                txt_view = (TextView) view_tab.findViewById(R.id.txt_view);
                txt_view.setTextColor(getResources().getColor(R.color.colorBlack));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(R.layout.custom_tabview);
            View view_tab = tabs.getTabAt(i).getCustomView();
            txt_batch_count_tab = (TextView) view_tab.findViewById(R.id.txt_batch_count_tab);
            txt_view = (TextView) view_tab.findViewById(R.id.txt_view);
            txt_batch_count_tab.setVisibility(View.VISIBLE);
            txt_view.setText(title_tab[i]);
            txt_batch_count_tab.setText(count.get(i));
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
            tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        }
        View view_tab = tabs.getTabAt(0).getCustomView();
        txt_view = (TextView) view_tab.findViewById(R.id.txt_view);
        txt_view.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void tocallOrdersList(String UserId) {
        count = new ArrayList<>();

        Call<MyOrdersListResponse> call = apiService.callMyordersList(UserId);
        call.enqueue(new Callback<MyOrdersListResponse>() {
            @Override
            public void onResponse(Call<MyOrdersListResponse> call, Response<MyOrdersListResponse> response) {
                Log.e("MyorderslistResp", new Gson().toJson(response.body()));
                MyOrdersListResponse resp = response.body();

                if (response.isSuccessful()) {

                    if (resp.getStatus().equals("1"))
                        GlobalVariables.MyorderActiveListpage = new ArrayList();
                    GlobalVariables.MyorderDeliveryListpage = new ArrayList();
                    GlobalVariables.MyorderCancelListpage = new ArrayList();

                    GlobalVariables.MyorderActiveListpage = resp.getActiveOrderList();
                    GlobalVariables.MyorderDeliveryListpage = resp.getDeliveredOrderList();
                    GlobalVariables.MyorderCancelListpage = resp.getCancelledOrderList();

                    count.add(String.valueOf(resp.getActiveCount()));
                    count.add(String.valueOf(resp.getDeliveredCount()));
                    count.add(String.valueOf(resp.getCancelledCount()));
                    setupViewPager(viewpager);
                    setTablayout(count);

                    //THIS FOR FCM TAB SELECTION
                    if (!order_id.equals("")) {

                        if (order_status.equals("3")) {

                            TabLayout.Tab tab = tabs.getTabAt(0);
                            tab.select();

                        } else if (order_status.equals("4")) {

                            TabLayout.Tab tab = tabs.getTabAt(1);
                            tab.select();

                        } else if (order_status.equals("7")) {

                            TabLayout.Tab tab = tabs.getTabAt(2);
                            tab.select();

                        }
                    }
                }

            }

            public void onFailure(Call<MyOrdersListResponse> call, Throwable t) {
                Log.e("Myorderslistfail", t.getMessage());
            }
        });

    }

}
