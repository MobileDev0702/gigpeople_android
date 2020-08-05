package com.gigpeople.app.subfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
import com.gigpeople.app.adapter.SearchgigAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.SearchGiGListResponse;
import com.gigpeople.app.apiModel.SearchSellerListResponse;
import com.gigpeople.app.model.MainCategory;
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

public class SearchGigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recycleView_searchgig)
    RecyclerView recycleViewSearchgig;
   /* @BindView(R.id.edt_search)
    EditText edtSearch;*/

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SearchgigAdapter searchgigAdapter;
    public static List<SearchGiGListResponse.SearchgigList> searchgigLists;

    public List<SearchGiGListResponse.SearchgigList> searchgigListsone;

    String user_id, gig_search = "";
    ApiService apiService;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    List<String> stringList;

    public SearchGigFragment() {
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
    public static SearchGigFragment newInstance(String param1, String param2) {
        SearchGigFragment fragment = new SearchGigFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_gig, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            gig_search = mParam1;
        }
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(getContext(), PrefConnect.USER_ID, "");
        autoCompleteTextView.setText(gig_search);
        searchgigLists = new ArrayList<>();
        stringList = new ArrayList<>();

        if (GlobalMethods.isNetworkAvailable(getContext())) {
            callSellerListSearch();
        } else {
            GlobalMethods.Toast(getContext(), getResources().getString(R.string.internet));
        }

        /*adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, stringList);
        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView*/
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              // gig_search = s.toString();

                if (GlobalMethods.isNetworkAvailable(getContext())) {
                    if (s.toString().length()==0) {
                        gig_search="";
                        callSellerListSearch();
                    }
                } else {
                    GlobalMethods.Toast(getContext(), getResources().getString(R.string.internet));
                }

               /* new Handler().postDelayed(new Runnable() {
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

                Log.e("itemclick", parent.getItemAtPosition(position).toString());
                String clickvalue = parent.getItemAtPosition(position).toString();
                gig_search = clickvalue;
                callSellerListSearch();
            }
        });
        return view;
    }

    private void callSellerListSearch() {
        searchgigListsone = new ArrayList<>();
        gig_search = autoCompleteTextView.getText().toString();
        Log.e("GigListSearchReq", "UserId:" + user_id + " gig_search: " + gig_search);
        Call<SearchGiGListResponse> call = apiService.callsearchGig(user_id, gig_search);
        call.enqueue(new Callback<SearchGiGListResponse>() {
            @Override
            public void onResponse(Call<SearchGiGListResponse> call, Response<SearchGiGListResponse> response) {
                Log.e("GigListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchGiGListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            searchgigListsone = resp.getSearchgigList();
                            searchgigLists = resp.getSearchgigList();
                            //if(searchgigListsone.size()>0){

                            if (stringList.size() <= 0) {
                                for (int i = 0; i < searchgigListsone.size(); i++) {
                                    String name = searchgigListsone.get(i).getTitle();
                                    Log.e("name", name);
                                    stringList.add(name);
                                }
                                adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_dropdown, stringList);
                                adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                               // adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, stringList);
                                autoCompleteTextView.setThreshold(1);//will start working from first character
                                autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                            }
                            searchgigAdapter = new SearchgigAdapter(getContext(), searchgigLists);
                            recycleViewSearchgig.setAdapter(searchgigAdapter);
                            recycleViewSearchgig.setLayoutManager(new LinearLayoutManager(getContext()));
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


   /* private void CallViewSearch(String value) {

        Log.e("SellerListSearchReq", "UserId:" + user_id + " gig_search: " + value);
        Call<SearchGiGListResponse> call = apiService.callsearchGig(user_id, value);
        call.enqueue(new Callback<SearchGiGListResponse>() {
            @Override
            public void onResponse(Call<SearchGiGListResponse> call, Response<SearchGiGListResponse> response) {
                Log.e("SellerListSearchResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    SearchGiGListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            searchgigLists = resp.getSearchgigList();
                            if (recycleViewSearchgig != null) {

                                searchgigAdapter = new SearchgigAdapter(getContext(), searchgigLists);
                                recycleViewSearchgig.setAdapter(searchgigAdapter);
                                recycleViewSearchgig.setLayoutManager(new LinearLayoutManager(getContext()));

                            }

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
*/

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
