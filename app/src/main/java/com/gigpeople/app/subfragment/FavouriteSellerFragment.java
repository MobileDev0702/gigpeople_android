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
import com.gigpeople.app.adapter.FavouriteAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.FavouriteSellerListResponse;
import com.gigpeople.app.model.FavouriteModel;
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
 * Use the {@link FavouriteSellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteSellerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recycler_fav)
    RecyclerView recyclerFav;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<FavouriteModel> favlist;
    FavouriteAdapter adapter;
    Context context;
    ApiService apiService;
    String user_id;
    ProgressDialog progressDialog;
    public List<FavouriteSellerListResponse.FavouritesellerList> Favouritesellerlist;

    public FavouriteSellerFragment() {
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
    public static FavouriteSellerFragment newInstance(String param1, String param2) {
        FavouriteSellerFragment fragment = new FavouriteSellerFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourite_seller, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);

        tocallFavriteSellerList(user_id);

        return view;
    }

    private void tocallFavriteSellerList(String userId) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("FavoriteListReq"," UserId: "+userId);
        Call<FavouriteSellerListResponse> call = apiService.callFavouriteSellerlist(userId);
        call.enqueue(new Callback<FavouriteSellerListResponse>() {
            @Override
            public void onResponse(Call<FavouriteSellerListResponse> call, Response<FavouriteSellerListResponse> response) {
                Log.e("FavoriteListResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    FavouriteSellerListResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {

                        GlobalVariables.FavouriteSellerList = new ArrayList<>();
                        GlobalVariables.FavouriteSellerList = resp.getFavouritesellerList();

                        setupDisplay();

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<FavouriteSellerListResponse> call, Throwable t) {
                Log.e("FavoriteListFail", t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    private void setupDisplay() {
        Favouritesellerlist = new ArrayList<>();
        Favouritesellerlist = GlobalVariables.FavouriteSellerList;
        adapter = new FavouriteAdapter(getContext(), Favouritesellerlist);
        recyclerFav.setAdapter(adapter);
        recyclerFav.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
