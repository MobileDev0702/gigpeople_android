package com.gigpeople.app.subfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.FavgigAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.FavouriteGigListResponse;
import com.gigpeople.app.model.MainCategory;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FavouriteGigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteGigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FavgigAdapter myOrdersAdapter;
    String user_id;

    Context context;
    ApiService apiService;

    ProgressDialog progressDialog;
    public List<FavouriteGigListResponse.FavouritegigList> FavouriteGiglist;

    public FavouriteGigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteGigFragment newInstance(String param1, String param2) {
        FavouriteGigFragment fragment = new FavouriteGigFragment();
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
        View view = inflater.inflate(R.layout.fragment_fav_gig, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);
        tocallFavriteSellerList(user_id);

        return view;
    }

    private void tocallFavriteSellerList(String userId) {

        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e("FavoriteGigListResp","UserId: "+userId);
        Call<FavouriteGigListResponse> call = apiService.callFavouriteGiglist(userId);
        call.enqueue(new Callback<FavouriteGigListResponse>() {
            @Override
            public void onResponse(Call<FavouriteGigListResponse> call, Response<FavouriteGigListResponse> response) {
                Log.e("FavoriteGigListResp", new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    FavouriteGigListResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {

                        GlobalVariables.FavouriteGigList = new ArrayList<>();
                        GlobalVariables.FavouriteGigList = resp.getFavouritegigList();

                        setupDisplay();

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<FavouriteGigListResponse> call, Throwable t) {
                Log.e("FavoriteGigListFail", t.getMessage());
                //progressDialog.dismiss();
            }
        });

    }

    private void setupDisplay() {
        FavouriteGiglist = new ArrayList<>();
        FavouriteGiglist = GlobalVariables.FavouriteGigList;

        myOrdersAdapter = new FavgigAdapter(getContext(), FavouriteGiglist);
        recycleView.setAdapter(myOrdersAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
