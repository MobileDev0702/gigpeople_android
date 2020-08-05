package com.gigpeople.app.subfragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.BuyerRequestSendOfferAdapter;
import com.gigpeople.app.adapter.CategoryAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.fragment.BuyerNewRequestFragment;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.gigpeople.app.fragment.BuyerNewRequestFragment.filterLocation;
import static com.gigpeople.app.fragment.BuyerNewRequestFragment.filterLong;
import static com.gigpeople.app.fragment.BuyerNewRequestFragment.filterLat;
import static com.gigpeople.app.fragment.BuyerNewRequestFragment.maiCategoryId;
import static com.gigpeople.app.fragment.BuyerNewRequestFragment.search;

import static com.gigpeople.app.fragment.BuyerNewRequestFragment.subCategoryId;

public class BuyreqsendofferFragment extends Fragment {

    public static RecyclerView recycleView;
    Unbinder unbinder;
    static BuyerRequestSendOfferAdapter buyerRequestAdapter;
    @BindView(R.id.edt_search)
    EditText edtSearch;

    static Context context;
    static ApiService apiService;

    Dialog filter_dialog;
    List<DashBoardResponse.MainCategoryList> category_list;
    CategoryAdapter categoryAdapter;
    List<DashBoardResponse.MainCategoryList.SubCategory> subCategoryList;

    public static String UserId;

    public static List<GigRequestOffersentResponse.RequestDetail> gignewrequestsentlist;

    MainActivity mainActivity;

    public static BuyreqsendofferFragment newInstance(String param1) {
        BuyreqsendofferFragment fragment = new BuyreqsendofferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyreqsendoffer, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        apiService = RetrofitSingleton.createService(ApiService.class);
        UserId = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);

        if (GlobalMethods.isNetworkAvailable(context)) {

            tocall_NewRequestSentTabTwo();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search = s.toString();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (GlobalMethods.isNetworkAvailable(context)) {

                            tocall_NewRequestSentTabTwo();
                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    }
                }, 2000);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public static void tocall_NewRequestSentTabTwo() {

       /* progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/

        Log.e("ReqOfferSendReq", "UserId: " + UserId + "\nSearch: " + search + "\nLocation: " + filterLocation + "\nLat: " + filterLat + "\nLong: " + filterLong + "\nCategory: " + maiCategoryId + "\nSubCategory: " + subCategoryId);
        Call<GigRequestOffersentResponse> call = apiService.callGigRequestListNewSent(UserId, search, filterLocation, filterLat, filterLong, maiCategoryId, subCategoryId);
        call.enqueue(new Callback<GigRequestOffersentResponse>() {
            @Override
            public void onResponse(Call<GigRequestOffersentResponse> call, Response<GigRequestOffersentResponse> response) {
                Log.e("ReqOfferSendResp", new Gson().toJson(response.body()));
                // progressDialog.dismiss();
                if (response.isSuccessful()) {
                    GigRequestOffersentResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        GlobalVariables.gigNewRequesSenttlist = new ArrayList<>();
                        GlobalVariables.gigNewRequesSenttlist = resp.getRequestDetails();
                        toLayoutSetup();

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<GigRequestOffersentResponse> call, Throwable t) {
                Log.e("ReqOfferSendResp", t.getMessage());
                // progressDialog.dismiss();
            }
        });
    }

    private static void toLayoutSetup() {

        gignewrequestsentlist = new ArrayList<>();
        gignewrequestsentlist = GlobalVariables.gigNewRequesSenttlist;
        if (gignewrequestsentlist.size() > 0) {
            recycleView.setVisibility(View.VISIBLE);
            buyerRequestAdapter = new BuyerRequestSendOfferAdapter(context, gignewrequestsentlist);
            recycleView.setAdapter(buyerRequestAdapter);
            recycleView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        } else {
            recycleView.setVisibility(View.GONE);
        }

    }
}
