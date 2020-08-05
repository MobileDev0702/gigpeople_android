package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.AdapterImageSlideSupportAdapter;
import com.gigpeople.app.adapter.FavouriteSubListDetialsAdapter;
import com.gigpeople.app.adapter.GigImageListAdapterAdapter;
import com.gigpeople.app.adapter.HomeUserSubCategoryAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.FavouriteSellerListResponse;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.model.TempNameAndIMageUrlModel;
import com.gigpeople.app.utils.GlobalApiMethod;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gigpeople.app.subfragment.SearchUserFragment.sellerLists;
import static com.gigpeople.app.fragment.MyGigFragment.publishGigLists;

public class GigOwnerDetailsActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_cart)
    Button btnCart;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.relativeMenu)
    RelativeLayout relativeMenu;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.recycler_giglist)
    RecyclerView recyclerGiglist;
    FavouriteSubListDetialsAdapter homeSubCategoryTwoAdapter;
    List<TempNameAndIMageUrlModel> mainCategories;
    Dialog share;
    @BindView(R.id.ratinglayout)
    LinearLayout ratinglayout;
    @BindView(R.id.img_fav)
    ImageView imgFav;
    String fav_status = "0";
    @BindView(R.id.btn_all_reviews)
    Button btnAllReviews;
    int page;
    List<FavouriteSellerListResponse.FavouritesellerList> fav_listdetialspage;
    @BindView(R.id.txtonlinestatusicon)
    TextView txtonlinestatusicon;
    @BindView(R.id.txtonlinestatus)
    TextView txtonlinestatus;
    @BindView(R.id.txtname)
    TextView txtname;
    @BindView(R.id.txtlocation)
    TextView txtlocation;
    @BindView(R.id.txtlanguageknown)
    TextView txtlanguageknown;
    @BindView(R.id.txtjoindate)
    TextView txtjoindate;
    @BindView(R.id.txtorderscompletedcount)
    TextView txtorderscompletedcount;
    @BindView(R.id.txtdescription)
    TextView txtdescription;
    @BindView(R.id.txtskills)
    TextView txtskills;

    String from_detail, seller_id, userid,is_favourite,other_userId,other_userName,otherUserImage;
    ApiService apiService;
    Context context;

    GigImageListAdapterAdapter gigImageListAdapterAdapter;
    List<GiGListResponse.PublishGigList> publishGigLists;

    String TIME;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_owner_details);
        ButterKnife.bind(this);
        context = GigOwnerDetailsActivity.this;

        userid=GlobalMethods.GetUserID(this);
        apiService = RetrofitSingleton.createService(ApiService.class);

        if (getIntent() != null) {
            page = getIntent().getIntExtra("page", 0);
            from_detail = getIntent().getStringExtra("from_detail");
            seller_id = getIntent().getStringExtra("seller_id");
            is_favourite = getIntent().getStringExtra("is_favourite");

            Log.e("from_detail",from_detail);
            Window window = GigOwnerDetailsActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(GigOwnerDetailsActivity.this, R.color.colorPrimaryDark));
            }

            Log.e("seller_id", seller_id);
            if (from_detail.equals("search")) {
                setSearchDetails(page);
            } else if (from_detail.equals("favourite")) {
                to_Pagesetup(page);
            }

        }
    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_menu, R.id.btn_all_reviews, R.id.img_fav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                Intent intent1 = new Intent(GigOwnerDetailsActivity.this, ChatDetailsActivity.class);
                intent1.putExtra("other_user_id", other_userId);
                intent1.putExtra("other_user_name", other_userName);
                intent1.putExtra("other_user_image", otherUserImage);
                startActivity(intent1);
                break;
            case R.id.btn_all_reviews:
                Intent intent2 = new Intent(GigOwnerDetailsActivity.this, ReviewListActivity.class);
                intent2.putExtra("review_list_from", "3");
                intent2.putExtra("seller_id", seller_id);
                startActivity(intent2);
                break;
            case R.id.img_fav:
                //GlobalApiMethod.toClickAddfavourite(GlobalMethods.GetUserID(this), "1", seller_id, this);

                toClickAddfavourite();
                break;
        }
    }

    private void opensharedialog() {

        share = new Dialog(GigOwnerDetailsActivity.this);
        share.requestWindowFeature(Window.FEATURE_NO_TITLE);
        share.setContentView(R.layout.dialog_share);
        share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        share.show();
        ImageView close = (ImageView) share.findViewById(R.id.img_close);
        final LinearLayout share1 = (LinearLayout) share.findViewById(R.id.share_layout);
        LinearLayout chat = (LinearLayout) share.findViewById(R.id.chat_layout);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();
            }
        });
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();
       /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "GigPeople";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));*/

                Intent share = new Intent(GigOwnerDetailsActivity.this, ShareActivity.class);
                startActivity(share);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();

                Intent intent = new Intent(GigOwnerDetailsActivity.this, ChatDetailsActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void toClickAddfavourite() {
        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        apiService = RetrofitSingleton.createService(ApiService.class);
        Log.e("FavoriteAddReq","UserId: "+userid+"\nFavaouriteType: "+"1"+"\nId: "+seller_id);
        Call<CommonResponse> call = apiService.callFavouriteAdd(userid, "1", seller_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("FavoriteAddResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        GlobalMethods.Toast(context,resp.getMessage());

                        if(from_detail.equals("search")){
                            if (fav_status.equals("0")) {
                                imgFav.setImageResource(R.drawable.fav_icon);
                                fav_status = "1";
                                sellerLists.get(page).setIsFavourite("1");
                            } else {
                                imgFav.setImageResource(R.drawable.fav_line);
                                fav_status = "0";
                                sellerLists.get(page).setIsFavourite("0");
                            }
                        } else if (from_detail.equals("favourite")) {

                            if (fav_status.equals("0")) {
                                imgFav.setImageResource(R.drawable.fav_icon);
                                fav_status = "1";
                                GlobalVariables.FavouriteSellerList.get(page).getGigDetails().get(0).setIsFavourite("1");
                            } else {
                                imgFav.setImageResource(R.drawable.fav_line);
                                fav_status = "0";
                                GlobalVariables.FavouriteSellerList.get(page).getGigDetails().get(0).setIsFavourite("0");
                            }
                        }


                    }else {
                        GlobalMethods.Toast(context,resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("Favoriteaddfail", t.getMessage());

            }
        });

    }

    private void setSearchDetails(int positionoflist) {


        txtname.setText(sellerLists.get(positionoflist).getFirstName() + " " + sellerLists.get(positionoflist).getLastName());
        txtTitle.setText(sellerLists.get(positionoflist).getFirstName() + " " + sellerLists.get(positionoflist).getLastName());
        seller_id = sellerLists.get(positionoflist).getSellerId();
        if (!sellerLists.get(positionoflist).getRating().equals("0")) {
            btnAllReviews.setVisibility(View.VISIBLE);
            ratingbar.setRating(Float.parseFloat(sellerLists.get(positionoflist).getRating()));
        }else {
            btnAllReviews.setVisibility(View.GONE);
        }

        other_userId=sellerLists.get(page).getSellerId();
        other_userName=sellerLists.get(page).getFirstName() + " " + sellerLists.get(page).getLastName();
        otherUserImage=sellerLists.get(page).getProfileImageUrl();

        if (!sellerLists.get(positionoflist).getProfileImageUrl().equals("")) {
            Glide.with(this).load(sellerLists.get(positionoflist).getProfileImageUrl()).into(userImage);

        } else {
            userImage.setImageResource(R.drawable.profile_placeholder);
        }
        txtlocation.setText(sellerLists.get(positionoflist).getAddress());
        txtskills.setText(sellerLists.get(positionoflist).getSkills());
        txtlanguageknown.setText(sellerLists.get(positionoflist).getLanguage());


        txtjoindate.setText(" " + GlobalMethods.DateTime(sellerLists.get(positionoflist).getJoinDate()));

        txtorderscompletedcount.setText(" " + sellerLists.get(positionoflist).getOrdersCompleted());
        txtdescription.setText(sellerLists.get(positionoflist).getAbout());

        if (sellerLists.get(positionoflist).getLiveStatus().equals("1")) {

            txtonlinestatusicon.setBackgroundResource(R.drawable.circlebackground_green);
            txtonlinestatus.setText("Online");
        } else {

            txtonlinestatusicon.setBackgroundResource(R.drawable.circlebackground_red);
            txtonlinestatus.setText("Offline");
        }

        fav_status = sellerLists.get(positionoflist).getIsFavourite();

        Log.e("Fav_status",fav_status);
        if (sellerLists.get(positionoflist).getIsFavourite().equals("1")) {
            imgFav.setImageResource(R.drawable.fav_icon);

        } else {
            imgFav.setImageResource(R.drawable.fav_line);

        }

        if (GlobalMethods.isNetworkAvailable(context)) {
            setGIGList();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }
    }

    private void to_Pagesetup(int positionoflist) {

        fav_listdetialspage = new ArrayList<>();
        fav_listdetialspage = GlobalVariables.FavouriteSellerList;

        txtname.setText(fav_listdetialspage.get(positionoflist).getFirstName()+" "+fav_listdetialspage.get(positionoflist).getLastName());
        txtTitle.setText(fav_listdetialspage.get(positionoflist).getFirstName()+" "+fav_listdetialspage.get(positionoflist).getLastName());
        seller_id = fav_listdetialspage.get(positionoflist).getSellerId();

        other_userId=fav_listdetialspage.get(page).getSellerId();
        other_userName=fav_listdetialspage.get(page).getFirstName() + " " + fav_listdetialspage.get(page).getLastName();
        otherUserImage=fav_listdetialspage.get(page).getProfileImageUrl();

        if (!fav_listdetialspage.get(positionoflist).getProfileImageUrl().equals("")) {
            Glide.with(this).load(fav_listdetialspage.get(positionoflist).getProfileImageUrl()).into(userImage);

        } else {
            userImage.setImageResource(R.drawable.profile_placeholder);
        }
        txtlocation.setText(fav_listdetialspage.get(positionoflist).getAddress());
        txtskills.setText(fav_listdetialspage.get(positionoflist).getSkills());
        txtlanguageknown.setText(fav_listdetialspage.get(positionoflist).getLanguage());


        txtjoindate.setText(GlobalMethods.DateTime(fav_listdetialspage.get(positionoflist).getJoinDate()));

        txtorderscompletedcount.setText(fav_listdetialspage.get(positionoflist).getOrdersCompleted());
        txtdescription.setText(fav_listdetialspage.get(positionoflist).getAbout());
        if (!fav_listdetialspage.get(positionoflist).getRating().equals("0")||!fav_listdetialspage.get(positionoflist).getRating().equals("")) {
            ratingbar.setRating(Float.parseFloat(fav_listdetialspage.get(positionoflist).getRating()));
            btnAllReviews.setVisibility(View.VISIBLE);
        }else {
            btnAllReviews.setVisibility(View.GONE);
        }

        if (fav_listdetialspage.get(positionoflist).getLiveStatus().equals("1")) {

            txtonlinestatusicon.setBackgroundResource(R.drawable.circlebackground_green);
            txtonlinestatus.setText("Online");
        } else {

            txtonlinestatusicon.setBackgroundResource(R.drawable.circlebackground_red);
            txtonlinestatus.setText("Offline");
        }

        fav_status = fav_listdetialspage.get(positionoflist).getGigDetails().get(0).getIsFavourite();

        if (fav_listdetialspage.get(positionoflist).getGigDetails().get(0).getIsFavourite().equals("1")) {
            imgFav.setImageResource(R.drawable.fav_icon);

        } else {
            imgFav.setImageResource(R.drawable.fav_line);

        }

        int size = fav_listdetialspage.get(positionoflist).getGigDetails().get(0).getImageList().size();


        mainCategories = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            mainCategories.add(new TempNameAndIMageUrlModel(fav_listdetialspage.get(positionoflist).getGigDetails().get(0).getCategoryName(), fav_listdetialspage.get(positionoflist).getGigDetails().get(0).getImageList().get(i).getThumnail()));
        }
        homeSubCategoryTwoAdapter = new FavouriteSubListDetialsAdapter(GigOwnerDetailsActivity.this, mainCategories);
        recyclerGiglist.setAdapter(homeSubCategoryTwoAdapter);
        recyclerGiglist.setLayoutManager(new LinearLayoutManager(GigOwnerDetailsActivity.this));

        if (GlobalMethods.isNetworkAvailable(context)) {
            setGIGList();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }


    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date mDate = sdf.parse(givenDateString);
            long_time = mDate.getTime();
            Log.e("Date in milli", time + " ");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        Log.e("TIME", TIME + " nan ");
        return TIME;
    }

    private void setGIGList() {
        Log.e("RequestListReq", "seller_id: " + seller_id);
        Call<GiGListResponse> call = apiService.callGigList(seller_id);
        call.enqueue(new Callback<GiGListResponse>() {
            @Override
            public void onResponse(Call<GiGListResponse> call, Response<GiGListResponse> response) {
                Log.e("RequestListResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GiGListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {


                            publishGigLists = new ArrayList<>();
                            publishGigLists = resp.getPublishGigList();

                            gigImageListAdapterAdapter = new GigImageListAdapterAdapter(GigOwnerDetailsActivity.this, publishGigLists);
                            recyclerGiglist.setAdapter(gigImageListAdapterAdapter);
                            recyclerGiglist.setLayoutManager(new LinearLayoutManager(GigOwnerDetailsActivity.this));

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiGListResponse> call, Throwable t) {

            }
        });
    }
}
