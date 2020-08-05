package com.gigpeople.app.subfragment;

import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.SearchUserAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.SearchSellerListResponse;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recycleView_searchuser)
    RecyclerView recycleViewSearchuser;
   /* @BindView(R.id.edt_search)
    EditText edtSearch;*/
   @BindView(R.id.autoCompleteTextView)
   AutoCompleteTextView autoCompleteTextView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchUserAdapter searchUserAdapter;
    public static List<SearchSellerListResponse.SellerList> sellerLists;


    public  List<SearchSellerListResponse.SellerList> sellerListsone;

    String user_id, user_search="";
    ApiService apiService;

    List<String> stringList;
    ArrayAdapter<String> adapter;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageRequestItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchUserFragment newInstance(String param1, String param2) {
        SearchUserFragment fragment = new SearchUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            user_search=mParam1;
        }
        autoCompleteTextView.setText(user_search);

        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(getContext(), PrefConnect.USER_ID, "");

        sellerLists = new ArrayList<>();
        stringList = new ArrayList<>();

          if (GlobalMethods.isNetworkAvailable(getContext())) {
            callSellerListSearch();
        } else {
            GlobalMethods.Toast(getContext(), getString(R.string.internet));
        }

       /* adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, stringList);
        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView*/

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // user_search = s.toString();

                 if (GlobalMethods.isNetworkAvailable(getContext())) {
                    if (s.toString().length()==0) {
                        user_search="";
                        callSellerListSearch();
                    }
                } else {
                    GlobalMethods.Toast(getContext(), getResources().getString(R.string.internet));
                }

                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("itemclick",parent.getItemAtPosition(position).toString());
                String clickvalue = parent.getItemAtPosition(position).toString();
                //CallViewSearch(clickvalue);

                if (GlobalMethods.isNetworkAvailable(getContext())) {

                        user_search=clickvalue;
                        callSellerListSearch();

                } else {
                    GlobalMethods.Toast(getContext(), getResources().getString(R.string.internet));
                }
            }
        });


        return view;
    }


    private void callSellerListSearch() {
        user_search = autoCompleteTextView.getText().toString();
        Log.e("SellerListSearchReq", "UserId:" + user_id + " user_search: " + user_search);

        sellerListsone = new ArrayList<>();
        Call<SearchSellerListResponse> call = apiService.callsearchSeller(user_id, user_search);
        call.enqueue(new Callback<SearchSellerListResponse>() {
            @Override
            public void onResponse(Call<SearchSellerListResponse> call, Response<SearchSellerListResponse> response) {
                Log.e("SellerListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchSellerListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            sellerListsone = resp.getSellerList();
                            sellerLists = resp.getSellerList();
                            if (recycleViewSearchuser != null) {

                                if(stringList.size()<=0){

                                    for (int i=0;i<sellerListsone.size();i++){

                                        String name = sellerListsone.get(i).getFirstName();
                                        Log.e("name",name);
                                        stringList.add(name);
                                    }

                                    adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_dropdown, stringList);
                                    adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                                   //adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, stringList);
                                    autoCompleteTextView.setThreshold(1);//will start working from first character
                                    autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                }
                              searchUserAdapter = new SearchUserAdapter(getContext(), sellerLists);
                                recycleViewSearchuser.setAdapter(searchUserAdapter);
                                recycleViewSearchuser.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
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


   /* private void CallViewSearch(String value) {
        Log.e("SellerListSearchReq11", "UserId:" + user_id + " user_search: " + value);
        stringList = new ArrayList<>();
        Call<SearchSellerListResponse> call = apiService.callsearchSeller(user_id, value);
        call.enqueue(new Callback<SearchSellerListResponse>() {
            @Override
            public void onResponse(Call<SearchSellerListResponse> call, Response<SearchSellerListResponse> response) {
                Log.e("SellerListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchSellerListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            sellerLists = resp.getSellerList();
                            if (recycleViewSearchuser != null) {

                               *//* if(sellerLists.size()>0){

                                    for (int i=0;i<sellerLists.size();i++){

                                        String name = sellerLists.get(i).getFirstName()+sellerLists.get(i).getLastName();
                                        Log.e("name",name);
                                        stringList.add(name);
                                    }


                                    adapter = new ArrayAdapter<String>
                                            (getContext(), android.R.layout.select_dialog_item, stringList);
                                    autoCompleteTextView.setThreshold(1);//will start working from first character
                                    autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                                }

                                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Log.e("itemclick",parent.getItemAtPosition(position).toString());
                                    }
                                });*//*
                                searchUserAdapter = new SearchUserAdapter(getContext(), sellerLists);
                                recycleViewSearchuser.setAdapter(searchUserAdapter);
                                recycleViewSearchuser.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
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

    }*/


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
