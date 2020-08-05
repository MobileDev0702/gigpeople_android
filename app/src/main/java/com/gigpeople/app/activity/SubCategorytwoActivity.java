package com.gigpeople.app.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.CategoryListAdapter;
import com.gigpeople.app.adapter.HomeSubCategoryTwoAdapter;
import com.gigpeople.app.adapter.LanguageFilterAdapter;
import com.gigpeople.app.adapter.SubcategoryListAdapter;
import com.gigpeople.app.adapter.TagFilterAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.apiModel.InnerLanguageTagModel;
import com.gigpeople.app.apiModel.LanguageModel;
import com.gigpeople.app.apiModel.TagModel;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategorytwoActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    HomeSubCategoryTwoAdapter homeSubCategoryTwoAdapter;
    Dialog dialog;
    ImageView imagedailogclos;
    CrystalRangeSeekbar rangeSeekbar1;
    TextView txtmin, txtmax, txtenglish, txtchinese, txtarabic, txttag1, txttag2, txttag3;
    ImageView langtick1, langtick2, langtick3, tagtick1, tagtick2, tagtick3;
    LinearLayout english, chinese, arabic, tag1, tag2, tag3;
    Button search;
    //    Spinner category, subcategory;
    RadioGroup radiogroup1, radiogroup2;
    RadioButton radio1, radio2, radio3, radio4;
    int status1 = 1, status2 = 1, status3 = 1;
    int tag_status1 = 1, tag_status2 = 1, tag_status3 = 1;
    Context context;
    ApiService apiService;

    ProgressDialog progressDialog;
    List<GigCategoryResultResponse.CategorygigList> DashboardMenuDetialspage;

    //Filter Dialog
    String filterLocation = "", filterLat = "", filterLong = "", filterCategory, filterCategoryId, filterSubCategory, filterSubCategoryId, filterDeliveryTime = "", filterOnlineStatus = "", filterLanguage="", filterPriceMax="", filterPricemin="", filterTag="";
    Dialog category, sub_category;
    RecyclerView recycle_category, recyle_subCategory;
    CategoryListAdapter categoryListAdapter;
    CategoryListAdapter.ItemClickListener itemClickListenerCategory;
    int category_position;
    TextView txtCategory, txtSubCategory;
    SubcategoryListAdapter subcategoryListAdapter;
    SubcategoryListAdapter.ItemClickListener itemClickListenerSubcategory;
    RecyclerView recycleLanguage, recycleTag;
    List<String> filterListTage;
    List<String> filterListLanguage;


    //Language
    List<String> languageModels;
    List<InnerLanguageTagModel> innerLanguageModels;
    LanguageFilterAdapter languageFilterAdapter;

    //TagList
    List<String> TagString;
    List<InnerLanguageTagModel> innerTagModels;
    TagFilterAdapter tagFilterAdapter;
    String searchkey = "", userdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorytwo);
        ButterKnife.bind(this);
        Window window = SubCategorytwoActivity.this.getWindow();
        innerTagModels=new ArrayList<>();

        context = this;
        apiService = RetrofitSingleton.createService(ApiService.class);


        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SubCategorytwoActivity.this, R.color.colorPrimaryDark));
        }
        userdi = PrefConnect.readString(SubCategorytwoActivity.this, PrefConnect.USER_ID, "");

        if (getIntent() != null) {

            String page = getIntent().getStringExtra("page");

            filterSubCategoryId = getIntent().getStringExtra("subcategoryid");
            filterCategoryId = getIntent().getStringExtra("maincatgory_id");
            if (GlobalMethods.isNetworkAvailable(context)){
                tocalllApi();
            }else {
                GlobalMethods.Toast(context,getString(R.string.internet));
            }
            //txtTitle.setText(page);
        }
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    searchkey = s.toString();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (GlobalMethods.isNetworkAvailable(context)) {
                                tocalllApi();
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }
                        }
                    }, 2000);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemClickListenerCategory = new CategoryListAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name, int position, String str_category_id) {

                filterCategoryId = str_category_id;
                category_position = position;
                txtCategory.setText(name);
                category.dismiss();
                filterCategory = name;
                txtSubCategory.setText("");
                filterSubCategoryId = "";

                GlobalMethods.Toast(SubCategorytwoActivity.this, "Choose sub category to get Tag");
            }
        };
        itemClickListenerSubcategory = new SubcategoryListAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name, String id) {

                txtSubCategory.setText(name);
                sub_category.dismiss();
                filterSubCategoryId = id;
                filterSubCategory = name;
            }
        };

        if (GlobalMethods.isNetworkAvailable(SubCategorytwoActivity.this)) {
            callLanguage();
        } else {
            GlobalMethods.Toast(SubCategorytwoActivity.this, getString(R.string.internet));
        }
    }

    @SuppressLint("WrongViewCast")
    @OnClick({R.id.btn_back_arrow, R.id.btn_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                setFilterDialog();
                break;
        }
    }

    private void tocalllApi() {

        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e("SubCateDetialsResp", "UserId: " + userdi + "\nSubCategory: " + filterSubCategoryId + "\nsearchkey: " + searchkey + "\nfilterLocation: " + filterLocation + "\nfilterLat: " + filterLat
                + "\nfilterLong: " + filterLong + "\nfilterCategoryId: " + filterCategoryId + "\nfilterDeliveryTime: " + filterDeliveryTime + "\nfilterOnlineStatus: " + filterOnlineStatus + "\nfilterLanguage: " + filterLanguage
                + "\nfilterPricemin: " + filterPricemin + "\nfilterPriceMax: " + filterPriceMax + "\nfilterTag: " + filterTag);
        Call<GigCategoryResultResponse> call = apiService.callcategoryGiglist(userdi, filterSubCategoryId, searchkey, filterLocation, filterLat,
                filterLong, filterCategoryId, filterDeliveryTime, filterOnlineStatus, filterLanguage, filterPricemin, filterPriceMax, filterTag);
        call.enqueue(new Callback<GigCategoryResultResponse>() {
            @Override
            public void onResponse(Call<GigCategoryResultResponse> call, Response<GigCategoryResultResponse> response) {
                Log.e("SubCateDetialsResp", new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    GigCategoryResultResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalVariables.DashboardMenuDetialspage = new ArrayList<>();
                            GlobalVariables.DashboardMenuDetialspage = resp.getCategorygigList();
                            setuplayout();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }


            public void onFailure(Call<GigCategoryResultResponse> call, Throwable t) {
                Log.e("SubCatedetialsResp", t.getMessage());
                // progressDialog.dismiss();
            }
        });

    }

    private void setuplayout() {

        DashboardMenuDetialspage = new ArrayList<>();
        DashboardMenuDetialspage = GlobalVariables.DashboardMenuDetialspage;
        homeSubCategoryTwoAdapter = new HomeSubCategoryTwoAdapter(SubCategorytwoActivity.this, DashboardMenuDetialspage);
        recycleView.setAdapter(homeSubCategoryTwoAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(SubCategorytwoActivity.this));
    }

    private void setFilterDialog() {
        dialog = new Dialog(SubCategorytwoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter_category);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtmin = (TextView) dialog.findViewById(R.id.txt_min_price);
        txtmax = (TextView) dialog.findViewById(R.id.txt_max_price);

        search = (Button) dialog.findViewById(R.id.btn_search);
        radiogroup1 = (RadioGroup) dialog.findViewById(R.id.radiogroup1);
        radiogroup2 = (RadioGroup) dialog.findViewById(R.id.radiogroup2);
        radio1 = (RadioButton) dialog.findViewById(R.id.radio1);
        radio2 = (RadioButton) dialog.findViewById(R.id.radio2);
        radio3 = (RadioButton) dialog.findViewById(R.id.radio3);
        radio4 = (RadioButton) dialog.findViewById(R.id.radio4);

        recycleLanguage = (RecyclerView) dialog.findViewById(R.id.recycleLanguage);
        recycleTag = (RecyclerView) dialog.findViewById(R.id.recycleTag);

        imagedailogclos = (ImageView) dialog.findViewById(R.id.img_close);
        txtCategory = (TextView) dialog.findViewById(R.id.txt_category);
        txtSubCategory = (TextView) dialog.findViewById(R.id.txt_sub_category);

        Log.e("languagesize", innerLanguageModels.size() + "::");

        if (GlobalMethods.isNetworkAvailable(SubCategorytwoActivity.this)) {
            callTagList(filterSubCategoryId);
        } else {
            GlobalMethods.Toast(SubCategorytwoActivity.this, getString(R.string.internet));
        }

        if (innerLanguageModels.size() > 0) {

            languageFilterAdapter = new LanguageFilterAdapter(SubCategorytwoActivity.this, innerLanguageModels);
            recycleLanguage.setLayoutManager(new GridLayoutManager(SubCategorytwoActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
            recycleLanguage.setAdapter(languageFilterAdapter);
        }

        Switch onofswitch = (Switch) dialog.findViewById(R.id.switch_on_off);

        onofswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    filterOnlineStatus = "1";

                } else {

                    filterOnlineStatus = "0";
                }
            }
        });

        LinearLayout linearCategory = (LinearLayout) dialog.findViewById(R.id.linear_category);
        LinearLayout linearSubCategory = (LinearLayout) dialog.findViewById(R.id.linear_sub_category);

        linearCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog();
            }
        });

        linearSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCategory.getText().toString().equals("")){
                    GlobalMethods.Toast(context,"Choose category");
                }else {
                    SubCategoryDialog();
                }
            }
        });



       /* category = (Spinner) dialog.findViewById(R.id.spinner_category_list);
        subcategory = (Spinner) dialog.findViewById(R.id.spinner_subcategory_list);*/

        final PlacesAutocompleteTextView placesAutocompleteTextView = (PlacesAutocompleteTextView) dialog.findViewById(R.id.placeTextView);

      /*  ArrayAdapter adapter = ArrayAdapter.createFromResource(SubCategorytwoActivity.this, R.array.category, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        category.setAdapter(adapter);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(SubCategorytwoActivity.this, R.array.subcategory, R.layout.item_spinner_dropdown2);
        adapter1.setDropDownViewResource(R.layout.item_spinner_dropdown);
        subcategory.setAdapter(adapter1);*/


        rangeSeekbar1 = (CrystalRangeSeekbar) dialog.findViewById(R.id.rangeSeekbar1);
        rangeSeekbar1.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            public void valueChanged(Number minValue, Number maxValue) {
                txtmin.setText("$" + String.valueOf(minValue));
                txtmax.setText("$" + String.valueOf(maxValue));

                filterPriceMax = String.valueOf(maxValue);
                filterPricemin = String.valueOf(minValue);

            }
        });

        rangeSeekbar1.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
            }
        });
        placesAutocompleteTextView.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place


                        placesAutocompleteTextView.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(final PlaceDetails details) {
                                Log.d("test", "details " + details);
                                filterLocation = details.name;
                                filterLat = String.valueOf(details.geometry.location.lat);
                                filterLong = String.valueOf(details.geometry.location.lng);
                                Log.e("location", filterLat + "::" + filterLong + "::" + filterLocation);
                            }

                            @Override
                            public void onFailure(final Throwable failure) {
                                Log.d("test", "failure " + failure);
                            }
                        });
                    }
                }
        );

        imagedailogclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Log.e("filterreques", filterLocation + "::" + filterLat + "::" + filterLong + "::" + filterPriceMax + "::" + filterPricemin + "::" + filterCategory + "::" + filterSubCategory + "::" + filterOnlineStatus + "::" + filterDeliveryTime);

                if (innerLanguageModels.size() > 0) {
                    filterListLanguage = new ArrayList<>();
                    for (int i = 0; i < innerLanguageModels.size(); i++) {

                        if (innerLanguageModels.get(i).getLangStatus().equals("1")) {
                            filterListLanguage.add(innerLanguageModels.get(i).getLangName());
                            Log.e("innerLanguageModels", filterListLanguage.size() + "::");
                            filterLanguage = filterListLanguage.toString().replace("[", "").replace("]", "");

                            Log.e("innerTagModels", filterLanguage + "::");
                        }
                    }
                }
                if (innerTagModels.size() > 0) {
                    filterListTage = new ArrayList<>();
                    for (int i = 0; i < innerTagModels.size(); i++) {
                        if (innerTagModels.get(i).getLangStatus().equals("1")) {
                            filterListTage.add(innerTagModels.get(i).getLangName());

                            filterTag = filterListTage.toString().replace("[", "").replace("]", "");

                            Log.e("innerTagModels", filterTag + "::");
                        }
                    }
                }
                if (GlobalMethods.isNetworkAvailable(context)){
                    tocalllApi();
                }else {
                    GlobalMethods.Toast(context,getString(R.string.internet));
                }

            }
        });
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio1.setChecked(true);
                radio2.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);
                filterDeliveryTime = "1";
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio2.setChecked(true);
                radio1.setChecked(false);
                radio3.setChecked(false);
                radio4.setChecked(false);
                filterDeliveryTime = "2";
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio3.setChecked(true);
                radio1.setChecked(false);
                radio2.setChecked(false);
                radio4.setChecked(false);
                filterDeliveryTime = "3";
            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio4.setChecked(true);
                radio1.setChecked(false);
                radio3.setChecked(false);
                radio2.setChecked(false);
                filterDeliveryTime = "4";
            }
        });

    }

    private void CategoryDialog() {

        category = new Dialog(context);
        category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        category.setContentView(R.layout.dialog_category);
        category.setCancelable(true);
        category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        category.show();
        recycle_category = (RecyclerView) category.findViewById(R.id.recycler_category);
        ImageView img_close = (ImageView) category.findViewById(R.id.img_close);

        if (GlobalVariables.dashmainCategorylist.size() > 0) {
            categoryListAdapter = new CategoryListAdapter(context, GlobalVariables.dashmainCategorylist, itemClickListenerCategory);
            recycle_category.setAdapter(categoryListAdapter);
            recycle_category.setLayoutManager(new LinearLayoutManager(context));
        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.dismiss();
            }
        });
    }

    private void SubCategoryDialog() {
        sub_category = new Dialog(context);
        sub_category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sub_category.setContentView(R.layout.dialog_sub_category);
        sub_category.setCancelable(true);
        sub_category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sub_category.show();
        recyle_subCategory = (RecyclerView) sub_category.findViewById(R.id.recycler_sub_category);
        ImageView img_close = (ImageView) sub_category.findViewById(R.id.img_close);

        if (GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory().size() > 0) {

            subcategoryListAdapter = new SubcategoryListAdapter(context, GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory(), itemClickListenerSubcategory);
            recyle_subCategory.setAdapter(subcategoryListAdapter);
            recyle_subCategory.setLayoutManager(new LinearLayoutManager(context));
        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_category.dismiss();
            }
        });
    }

    private void callTagList(String subcatId) {
        innerTagModels = new ArrayList<>();
        TagString = new ArrayList<>();
        Log.e("subcatIdfilter", subcatId);
        Call<TagModel> call = apiService.callTagList(subcatId);
        call.enqueue(new Callback<TagModel>() {
            @Override
            public void onResponse(Call<TagModel> call, Response<TagModel> response) {
                Log.e("suceeTage", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    TagModel resp = response.body();
                    if (resp != null) {
                        String status = resp.getStatus();

                        if (status.equals("1")) {
                            TagString = resp.getTgList();
                            if (TagString.size() > 0) {

                                for (int i = 0; i < TagString.size(); i++) {
                                    String name = TagString.get(i);
                                    innerTagModels.add(new InnerLanguageTagModel(name, "0"));
                                }


                                if (innerTagModels.size() > 0) {

                                    tagFilterAdapter = new TagFilterAdapter(SubCategorytwoActivity.this, innerTagModels);
                                    recycleTag.setLayoutManager(new GridLayoutManager(SubCategorytwoActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
                                    recycleTag.setAdapter(tagFilterAdapter);
                                }


                            }

                        } else {


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TagModel> call, Throwable t) {

            }
        });
    }

    private void callLanguage() {
        languageModels = new ArrayList<>();
        innerLanguageModels = new ArrayList<>();
        Call<LanguageModel> call = apiService.callLanguage();
        call.enqueue(new Callback<LanguageModel>() {
            @Override
            public void onResponse(Call<LanguageModel> call, Response<LanguageModel> response) {
                Log.e("ResponseLangu", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    LanguageModel resp = response.body();
                    if (resp != null) {
                        String status = resp.getStatus();
                        if (status.equals("1")) {

                            languageModels = resp.getLanguageList();

                            if (languageModels.size() > 0) {

                                for (int i = 0; i < languageModels.size(); i++) {

                                    String name = languageModels.get(i);
                                    Log.e("name", name);

                                    innerLanguageModels.add(new InnerLanguageTagModel(name, "0"));

                                }


                            } else {


                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LanguageModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
