package com.gigpeople.app.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.HomeSubCategoryAdapter;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSubCategoryActivity extends AppCompatActivity implements HomeSubCategoryAdapter.HomeSubCategoryAdapterListener {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.edt_search)
    SearchView edtSearch;
    @BindView(R.id.linearEdit)
    LinearLayout linearEdit;
    @BindView(R.id.recycleCategory)
    RecyclerView recycleCategory;
    HomeSubCategoryAdapter homeSubCategoryAdapter;
    List<DashBoardResponse.MainCategoryList.SubCategory> subCategoryList;
    String title,maincatgory_id;
    int page;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sub_category);
        ButterKnife.bind(this);
        Window window = HomeSubCategoryActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(HomeSubCategoryActivity.this, R.color.colorPrimaryDark));
        }
        if (getIntent() != null) {
            page = getIntent().getIntExtra("page", 0);
            maincatgory_id = getIntent().getStringExtra("maincatgory_id");
            SubCategoryMenu(page);

        }
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        edtSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        edtSearch.setMaxWidth(Integer.MAX_VALUE);
        AutoCompleteTextView search_text = (AutoCompleteTextView) edtSearch.findViewById(edtSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        search_text.setTextColor(Color.BLACK);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.hinted_geomanist_regular_0);
        search_text.setTypeface(typeface);
       // search_text.setTypeface(search_text.getTypeface(), Typeface.NORMAL);

        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.txt_12));

        edtSearch.setQueryHint("Search Sub Category...");

        edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                homeSubCategoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                homeSubCategoryAdapter.getFilter().filter(query);
                return false;
            }
        });

    }

    @OnClick(R.id.btn_back_arrow)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void SubCategoryMenu(int position) {
        subCategoryList = new ArrayList<>();
        subCategoryList = GlobalVariables.dashmainCategorylist.get(position).getSubCategory();
        final String title = GlobalVariables.dashmainCategorylist.get(position).getMainCategoryName();
        txtTitle.setText(title);

        homeSubCategoryAdapter = new HomeSubCategoryAdapter(HomeSubCategoryActivity.this, subCategoryList,maincatgory_id);
        recycleCategory.setAdapter(homeSubCategoryAdapter);
        recycleCategory.setLayoutManager(new GridLayoutManager(HomeSubCategoryActivity.this, 3, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onHelpSelected(DashBoardResponse.MainCategoryList.SubCategory filt) {

    }
}
//  TextView searchText = (TextView) edtSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
// Typeface.createFromAsset(getAssets(),"hinted_geomanist_book");

///Typeface myCustomFont = ResourcesCompat.getFont(this, R.font.hinted_geomanist_book);
//searchText.setTypeface(myCustomFont);
// listening to search query text change