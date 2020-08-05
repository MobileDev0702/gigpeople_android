package com.gigpeople.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidpagecontrol.PageControl;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.NotificationListActivity;
import com.gigpeople.app.activity.SearchDetailActivity;
import com.gigpeople.app.adapter.AdapterImageSlide;
import com.gigpeople.app.adapter.HomeMainCategoryAdapter;
import com.gigpeople.app.adapter.SearchgigAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.SearchGiGListResponse;
import com.gigpeople.app.apiModel.SearchSellerListResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    /*@BindView(R.id.edt_search)
    EditText edtSearch;*/
    @BindView(R.id.linearEdit)
    RelativeLayout linearEdit;
    ViewPager viewpager;
    PageControl pageController;
    RecyclerView recycleMaincategory;
    Unbinder unbinder;
    View view;

    Context context;
    ApiService apiService;

    ProgressDialog progressDialog;
    public List<DashBoardResponse.BannerList> Bannerlist;
    public List<DashBoardResponse.MainCategoryList> mainCategorylist;

    AdapterImageSlide adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    HomeMainCategoryAdapter homeMainCategoryAdapter;
    @BindView(R.id.search)
    ImageView search;
    String user_search, user_id;
    MainActivity mainActivity;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    List<String> stringList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recycleMaincategory = (RecyclerView) view.findViewById(R.id.recycleMaincategory);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        pageController = (PageControl) view.findViewById(R.id.pageController);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);
        stringList=new ArrayList<>();

        mainActivity.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent not = new Intent(getContext(), NotificationListActivity.class);
                startActivity(not);
            }
        });
        if (GlobalMethods.isNetworkAvailable(context)) {
            if (GlobalVariables.dashmainCategorylist.size() <= 0) {
                Dashboard();
            } else {
                MainCategorymenu();
                setBanner();
            }
            // for predictive search
            callSellerListSearch();

        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_search = autoCompleteTextView.getText().toString();
                //user_search = edtSearch.getText().toString();
                Intent intent = new Intent(getContext(), SearchDetailActivity.class);
                intent.putExtra("search", user_search);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void Dashboard() {

        GlobalVariables.dash_boardBannerlist = new ArrayList<>();
        GlobalVariables.dashmainCategorylist = new ArrayList<>();

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Call<DashBoardResponse> call = apiService.callforDashboard();
        call.enqueue(new Callback<DashBoardResponse>() {
            @Override
            public void onResponse(Call<DashBoardResponse> call, Response<DashBoardResponse> response) {
                Log.e("DashBoardResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    DashBoardResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            GlobalVariables.dash_boardBannerlist = resp.getBannerList();
                            GlobalVariables.dashmainCategorylist = resp.getMainCategoryList();

                            MainCategorymenu();
                            setBanner();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            public void onFailure(Call<DashBoardResponse> call, Throwable t) {
                Log.e("DashBoardRespfail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void MainCategorymenu() {
        mainCategorylist = new ArrayList<>();
        mainCategorylist = GlobalVariables.dashmainCategorylist;
        homeMainCategoryAdapter = new HomeMainCategoryAdapter(getContext(), mainCategorylist);
        recycleMaincategory.setAdapter(homeMainCategoryAdapter);
        recycleMaincategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setBanner() {
        Bannerlist = new ArrayList<>();
        Bannerlist = GlobalVariables.dash_boardBannerlist;
        adapterImageSlide = new AdapterImageSlide(getContext(), Bannerlist);
        viewpager.setAdapter(adapterImageSlide);
        pageController.setViewPager(viewpager);

        try {
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {

                    if (currentPage == Bannerlist.size()) {
                        currentPage = 0;
                    }

                    try {

                        viewpager.setCurrentItem(currentPage++, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSellerListSearch() {
        user_search = autoCompleteTextView.getText().toString();
        Log.e("SellerListSearchReq", "UserId:" + user_id + " user_search: " + user_search);
        Call<SearchSellerListResponse> call = apiService.callsearchSeller(user_id, "");
        call.enqueue(new Callback<SearchSellerListResponse>() {
            @Override
            public void onResponse(Call<SearchSellerListResponse> call, Response<SearchSellerListResponse> response) {
                Log.e("SellerListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchSellerListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            List<SearchSellerListResponse.SellerList> sellerListsone = resp.getSellerList();

                            if (search != null) {

                                if (stringList.size() <= 0) {

                                    for (int i = 0; i < sellerListsone.size(); i++) {

                                        String name = sellerListsone.get(i).getFirstName();
                                        Log.e("name", name);
                                        stringList.add(name);
                                    }

                                    adapter = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown, stringList);
                                    adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                                   // adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, stringList);
                                    autoCompleteTextView.setThreshold(1);//will start working from first character
                                    autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                    // for predective search
                                    callGigListSearch();
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchSellerListResponse> call, Throwable t) {

                Log.e("failure", t.getMessage());

            }
        });

    }

    private void callGigListSearch() {
        Log.e("GigListSearchReq", "UserId:" + user_id + " gig_search: " + "");
        Call<SearchGiGListResponse> call = apiService.callsearchGig(user_id, "");
        call.enqueue(new Callback<SearchGiGListResponse>() {
            @Override
            public void onResponse(Call<SearchGiGListResponse> call, Response<SearchGiGListResponse> response) {
                Log.e("GigListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchGiGListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            List<SearchGiGListResponse.SearchgigList> searchgigListsone = resp.getSearchgigList();

                            //if (stringList.size() <= 0) {
                                for (int i = 0; i < searchgigListsone.size(); i++) {
                                    String name = searchgigListsone.get(i).getTitle();
                                    Log.e("name", name);
                                    stringList.add(name);
                                }
                               adapter.notifyDataSetChanged();
                           // }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchGiGListResponse> call, Throwable t) {

                Log.e("failure", t.getMessage());

            }
        });

    }
}
