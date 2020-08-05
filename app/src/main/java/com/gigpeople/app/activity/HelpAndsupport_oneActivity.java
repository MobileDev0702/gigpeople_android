package com.gigpeople.app.activity;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.HelpAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.HelpandSupportResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HelpAndsupport_oneActivity extends AppCompatActivity implements HelpAdapter.HelpAdapterListener {
    Context context;

    HelpAdapter adapter;


    ApiService apiService;

    ProgressDialog progressDialog;
    List<HelpandSupportResponse.FaqList> helpandsupport_List = new ArrayList();
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.header)
    RelativeLayout header;

    @BindView(R.id.search_icon)
    ImageView searchIcon;
    /*@BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;*/
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.btn_contatcus)
    Button btnContatcus;
    @BindView(R.id.txt_search)
    SearchView txtSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_helpandsupportnew);
        ButterKnife.bind(this);
        context = HelpAndsupport_oneActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        Window window = HelpAndsupport_oneActivity.this.getWindow();
        callHelpandSupport();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(HelpAndsupport_oneActivity.this, R.color.colorPrimaryDark));
        }



        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        txtSearch.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        txtSearch.setMaxWidth(Integer.MAX_VALUE);

        AutoCompleteTextView search_text = (AutoCompleteTextView) txtSearch.findViewById(txtSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        search_text.setTextColor(Color.BLACK);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.hinted_geomanist_regular_0);
        search_text.setTypeface(typeface);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.txt_12));
        txtSearch.setQueryHint("Search Help and support");
        // listening to search query text change
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });

/*
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() >= 0) {


                    //searchList.setVisibility(View.VISIBLE);
                } else {
                    // searchList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                HelpAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    /*  @OnClick({R.id.im_back, R.id.linear_txt, R.id.linear_search_list*//*, R.id.btn_contatcus*//*})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();

                break;
           *//* case R.id.txt_search:

                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        if (s.length() >= 2) {
                            searchList.setVisibility(View.VISIBLE);
                        } else {
                            searchList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
*//*
            case R.id.linear_search_list:

                Intent one = new Intent(context, HelpDetailActivity.class);
                one.putExtra("title", "0");
                one.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(one);
                break;
            case R.id.linear_txt:

                //  searchList.setVisibility(View.GONE);
                Intent two = new Intent(context, HelpDetailActivity.class);
                two.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(two);
                break;

           *//* case R.id.btn_contatcus:
                Intent intent = new Intent(context, HelpandSupportActivity.class);
                startActivity(intent);
                break;*//*

        }
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
      //  txtSearch.setText("");
        // searchList.setVisibility(View.GONE);
    }


    private void callHelpandSupport() {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Call<HelpandSupportResponse> call = apiService.callforHelpandSupport();
        call.enqueue(new Callback<HelpandSupportResponse>() {
            @Override
            public void onResponse(Call<HelpandSupportResponse> call, Response<HelpandSupportResponse> response) {
                Log.e("help&supportresp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    HelpandSupportResponse resp = response.body();


                    helpandsupport_List = resp.getFaqList();

                    tosetupHelpandSupportlayout();
                    //resp.

                }
            }

            @Override
            public void onFailure(Call<HelpandSupportResponse> call, Throwable t) {
                Log.e("help&supportfail", t.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    private void tosetupHelpandSupportlayout() {

        adapter = new HelpAdapter(getApplicationContext(), helpandsupport_List);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @OnClick({R.id.im_back, R.id.btn_contatcus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.btn_contatcus:
                Intent intent = new Intent(context, HelpandSupportActivity.class);
                startActivity(intent);
                break;

            case R.id.txt_search:


                break;
        }
    }

    @Override
    public void onHelpSelected(HelpandSupportResponse.FaqList help) {

    }


   /*@OnClick({R.id.im_back, R.id.txt_search, R.id.relative_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;

            case R.id.relative_layout:
                break;
        }
    }

    */


}




