package com.gigpeople.app.subfragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.PaymentHistoryActivity;
import com.gigpeople.app.adapter.BuyerRequestAdapter;
import com.gigpeople.app.adapter.CategoryAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.apiModel.GigRequestResponse;
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
import butterknife.OnClick;
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


public class BuyreqnewofferFragment extends Fragment {

    static RecyclerView recycleView;
    Unbinder unbinder;
    static BuyerRequestAdapter buyerRequestAdapter;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.txtredirct)
    TextView txtredirct;
    static LinearLayout orderenablelayout;

    static Context context;
    static ApiService apiService;
    public static String user_id;

    MainActivity mainActivity;

    public static List<GigRequestResponse.RequestDetail> gignewrequestlist;

    public static BuyreqnewofferFragment newInstance(String param1) {
        BuyreqnewofferFragment fragment = new BuyreqnewofferFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyreqnewoffer, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        orderenablelayout = (LinearLayout) view.findViewById(R.id.orderenablelayout);

        if (GlobalMethods.isNetworkAvailable(context)) {
            tocall_NewRequest();
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
                            tocall_NewRequest();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static void tocall_NewRequest() {

        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e("GigNewRequestReq", "UserId: " + user_id + "\nSearch: " + search + "\nLocation: " + filterLocation + "\nLat: " + filterLat + "\nLong: " + filterLong + "\nCategory: " + maiCategoryId+ "\nSubCategory: " + subCategoryId);
        Call<GigRequestResponse> call = apiService.callGigRequestListNew(user_id, search, filterLocation, filterLat, filterLong, maiCategoryId, subCategoryId);
        call.enqueue(new Callback<GigRequestResponse>() {
            @Override
            public void onResponse(Call<GigRequestResponse> call, Response<GigRequestResponse> response) {
                Log.e("GigNewRequestResp", new Gson().toJson(response.body()));
                // progressDialog.dismiss();
                if (response.isSuccessful()) {
                    GigRequestResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {

                        GlobalVariables.gigNewRequestlist = new ArrayList<>();
                        GlobalVariables.gigNewRequestlist = resp.getRequestDetails();
                        setupDisplay();

                    } else if (resp.getStatus().equals("2")) {

                        setupDisplayforEnableOrders();
                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<GigRequestResponse> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("GigNewRequestResp", t.getMessage());
            }
        });
    }

    private static void setupDisplay() {
        recycleView.setVisibility(View.VISIBLE);
        gignewrequestlist = new ArrayList<>();
        gignewrequestlist = GlobalVariables.gigNewRequestlist;
        if (gignewrequestlist.size() > 0) {
            recycleView.setVisibility(View.VISIBLE);
            buyerRequestAdapter = new BuyerRequestAdapter(context, gignewrequestlist);
            recycleView.setAdapter(buyerRequestAdapter);
            recycleView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        } else {
            recycleView.setVisibility(View.GONE);
        }
    }

    private static void setupDisplayforEnableOrders() {
        orderenablelayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txtredirct)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtredirct:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("page", "10");
                startActivity(intent);
                break;
        }
    }
}
