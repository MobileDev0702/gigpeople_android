package com.gigpeople.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidpagecontrol.PageControl;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.GigReviewAdapter;
import com.gigpeople.app.adapter.ImageVideoAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CartCount;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.GiGDetailsResponse;
import com.gigpeople.app.model.ImageVideoModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GigDetailsActivity extends AppCompatActivity {

    int currentPage = 0;
    Timer timer;
    int fav_status = 0;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.

    Dialog share;
    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.relativeMenu)
    RelativeLayout relativeMenu;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.img_fav)
    ImageView imgFav;
    @BindView(R.id.pageController)
    PageControl pageController;
    @BindView(R.id.btn_editgig)
    Button btnEditgig;
    String page;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    @BindView(R.id.review_layout)
    LinearLayout reviewLayout;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;
    GigReviewAdapter adapter;
    @BindView(R.id.btn_morereviews)
    Button btnMorereviews;
    @BindView(R.id.reviewlayout)
    LinearLayout reviewlayout;
    ApiService apiService;
    Context context;
    String user_id, gig_id;
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.txt_sub_category)
    TextView txtSubCategory;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_delivery_time)
    TextView txtDeliveryTime;
    @BindView(R.id.txt_revisions)
    TextView txtRevisions;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    public static List<GiGDetailsResponse.GigDetails.ImageList> imgDetailsList = new ArrayList<>();
    int position;
    ImageVideoAdapter imageAdapter;
    ArrayList<String> imageList;
    List<ImageVideoModel> imageListNew;
    String title, category, subcategory, category_id, subcategory_id, price, shippings, shipping_price, revisions, tags, description, deliver_time, from,seller_id;
    String TIME;
    @BindView(R.id.txt_gig_title)
    TextView txtGigTitle;
    @BindView(R.id.txt_rating)
    TextView txtRating;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    String shareBody;
    @BindView(R.id.txt_shipping_price)
    TextView txtShippingPrice;
    @BindView(R.id.txt_total_price)
    TextView txtTotalPrice;
    @BindView(R.id.linear_editbtn)
    LinearLayout linearEditbtn;
    String _Currentpage;
    List<GiGDetailsResponse.GigReview> reviewList;
    ProgressDialog progressDialog;

    @BindView(R.id.btn_ordernow)
    Button btnOrdernow;
    @BindView(R.id.linear_addToCart)
    LinearLayout linearAddToCart;
    @BindView(R.id.relativeCount)
    RelativeLayout relativeCount;
    @BindView(R.id.txtcount)
    TextView txtcount;
    @BindView(R.id.btn_cart)
    Button btnCart;
    Dialog decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig_details);
        ButterKnife.bind(this);
        context = GigDetailsActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        imageList = new ArrayList<>();
        imageListNew = new ArrayList<>();
        Window window = GigDetailsActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(GigDetailsActivity.this, R.color.colorPrimaryDark));
        }

        if (getIntent() != null) {
            page = getIntent().getStringExtra("page");
            if (page.equalsIgnoreCase("1")) {
                reviewlayout.setVisibility(View.GONE);
            } else {
                reviewlayout.setVisibility(View.VISIBLE);
            }
        }

        if (getIntent() != null) {
            gig_id = getIntent().getStringExtra("gig_id");
            position = getIntent().getIntExtra("position", 0);
            from = getIntent().getStringExtra("from");
        }

        Log.e("Page", page + " nan " + from);

        if (from.equals("search")) {
            linearEditbtn.setVisibility(View.GONE);
            _Currentpage = "search";

            seller_id=getIntent().getStringExtra("seller_id");
            user_id=seller_id;

            if (GlobalMethods.isNetworkAvailable(context)) {
                toShowCartCount();
            } else {
                GlobalMethods.Toast(context, getString(R.string.internet));
            }

        } else if (from.equals("gig_active")) {
            _Currentpage = "gig_active";
            linearEditbtn.setVisibility(View.GONE);
            relativeCount.setVisibility(View.GONE);
            linearAddToCart.setVisibility(View.GONE);

        } else if (from.equals("gig_draft")) {
            _Currentpage = "gig_draft";

            reviewlayout.setVisibility(View.GONE);
            txtRating.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            linearEditbtn.setVisibility(View.VISIBLE);
            btnEditgig.setVisibility(View.VISIBLE);
            btnPublish.setVisibility(View.VISIBLE);
            relativeCount.setVisibility(View.GONE);
            linearAddToCart.setVisibility(View.GONE);

        } else if (from.equals("gig_wait")) {
            _Currentpage = "gig_wait";

            Log.e("Page", page + " in wait " + from);

            reviewlayout.setVisibility(View.GONE);
            txtRating.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            linearEditbtn.setVisibility(View.VISIBLE);
            btnEditgig.setVisibility(View.VISIBLE);
            btnPublish.setVisibility(View.GONE);
            relativeCount.setVisibility(View.GONE);
            linearAddToCart.setVisibility(View.GONE);
        }

        if (GlobalMethods.isNetworkAvailable(context)) {
            setGigDetail();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        if (getIntent() != null) {
            if (page.equalsIgnoreCase("1")) {
                btnPublish.setVisibility(View.VISIBLE);
                Log.e("Page", page + " if " + from);

            }
        }
    }

    @OnClick({R.id.btn_cart,R.id.btn_ordernow, R.id.btn_back_arrow, R.id.btn_menu, R.id.img_fav, R.id.btn_editgig, R.id.btn_publish, R.id.btn_morereviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cart:
                Intent intent = new Intent(GigDetailsActivity.this, cartItemActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ordernow:
                opendescdialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_morereviews:
                Intent intent5 = new Intent(GigDetailsActivity.this, GigReviewMoreActivity.class);
                intent5.putExtra("gigid", gig_id);
                intent5.putExtra("user_id", user_id);
                startActivity(intent5);
                break;
            case R.id.btn_editgig:
                Intent intent1 = new Intent(GigDetailsActivity.this, EditGigActivitytwo.class);
                intent1.putExtra("gig_id", gig_id);
                intent1.putExtra("title", title);
                intent1.putExtra("category", category);
                intent1.putExtra("subcategory", subcategory);
                intent1.putExtra("category_id", category_id);
                intent1.putExtra("subcategory_id", subcategory_id);
                intent1.putExtra("price", price);
                intent1.putExtra("deliver_time", deliver_time);
                intent1.putExtra("shippings", shippings);
                intent1.putExtra("shipping_price", shipping_price);
                intent1.putExtra("revisions", revisions);
                intent1.putExtra("tags", tags);
                intent1.putExtra("description", description);
                startActivity(intent1);
                break;
            case R.id.btn_menu:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.img_fav:
                if (fav_status == 0) {
                    imgFav.setImageResource(R.drawable.fav_icon);
                    fav_status = 1;
                } else {
                    imgFav.setImageResource(R.drawable.fav_line);
                    fav_status = 0;
                }
                break;
            case R.id.btn_publish:

                if (GlobalMethods.isNetworkAvailable(context)) {
                    Click_to_PublishGig(GlobalMethods.GetUserID(context), gig_id);
                } else {
                    GlobalMethods.Toast(context, getResources().getString(R.string.internet));
                }
                break;
        }
    }

    private void setGigDetail() {
        Log.e("RequestDetailReq", "UserId:" + user_id + " RequestId: " + gig_id);
        Call<GiGDetailsResponse> call = apiService.callgigDetails(user_id, gig_id);
        call.enqueue(new Callback<GiGDetailsResponse>() {
            @Override
            public void onResponse(Call<GiGDetailsResponse> call, Response<GiGDetailsResponse> response) {
                Log.e("RequestDetailResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GiGDetailsResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            txtCategory.setText(resp.getGigDetails().getCategoryName());
                            txtSubCategory.setText(resp.getGigDetails().getSubCategoryName());
                            txtDeliveryTime.setText(resp.getGigDetails().getDeliveryTime());
                            txtDescription.setText(resp.getGigDetails().getDescription());
                            txtRevisions.setText(resp.getGigDetails().getRevisions());
                            txtPrice.setText("$" + resp.getGigDetails().getPrice());
                            txtShippingPrice.setText("$" + resp.getGigDetails().getShippingPrice());
                            txtTotalPrice.setText("$" + resp.getGigDetails().getTotalCost());
                            txtGigTitle.setText(resp.getGigDetails().getTitle());
                            ratingBar.setRating(Float.parseFloat(resp.getGigDetails().getGigRating()));
                            txtRating.setText(resp.getGigDetails().getReviewCount() + " " + "reviews");

                            category = resp.getGigDetails().getCategoryName();
                            category_id = resp.getGigDetails().getCategoryId();
                            subcategory = resp.getGigDetails().getSubCategoryName();
                            subcategory_id = resp.getGigDetails().getSubCategoryId();
                            price = resp.getGigDetails().getPrice();
                            shippings = resp.getGigDetails().getShipping();
                            shipping_price = resp.getGigDetails().getShippingPrice();
                            revisions = resp.getGigDetails().getRevisions();
                            tags = resp.getGigDetails().getGigTag();
                            description = resp.getGigDetails().getDescription();
                            gig_id = resp.getGigDetails().getGigId();
                             deliver_time = resp.getGigDetails().getDeliveryTime();
                            title = resp.getGigDetails().getTitle();

                            // String TIME = setTimeString(deliver_time);

                            if (deliver_time.equals("1")) {
                                txtDeliveryTime.setText(deliver_time + " day");
                            } else {
                                txtDeliveryTime.setText(deliver_time + " days");
                            }

                            imgDetailsList = new ArrayList<>();
                            imgDetailsList = resp.getGigDetails().getImageList();

                            for (int i = 0; i < resp.getGigDetails().getImageList().size(); i++) {
                                imageList.add(imgDetailsList.get(i).getThumnail());
                                imageListNew.add(new ImageVideoModel(GlobalMethods.GIG_IMAGE_URL + imgDetailsList.get(i).getFile(), GlobalMethods.GIG_IMAGE_URL + imgDetailsList.get(i).getThumnail(), imgDetailsList.get(i).getType()));
                            }
                            imageAdapter = new ImageVideoAdapter(context, imageListNew);
                            viewpager.setAdapter(imageAdapter);
                            pageController.setViewPager(viewpager);

                            String image = "";
                            for (int i = 0; i < resp.getGigDetails().getImageList().size(); i++) {

                                if (image.equals("")) {
                                    image = GlobalMethods.GIG_IMAGE_URL + resp.getGigDetails().getImageList().get(i).getFile();
                                } else {
                                    image += "\n" + GlobalMethods.GIG_IMAGE_URL + resp.getGigDetails().getImageList().get(i).getFile();
                                }
                            }
                            String devlivey;
                            if (resp.getGigDetails().getDeliveryTime().equals("1")) {
                                devlivey = resp.getGigDetails().getDeliveryTime() + " day";
                            } else {
                                devlivey = resp.getGigDetails().getDeliveryTime() + " days";
                            }
                            txtDeliveryTime.setText(devlivey);
                            shareBody = "\nGigPeople" + "\nTitle: " + resp.getGigDetails().getTitle() + "\nCategory: " + resp.getGigDetails().getCategoryName() +
                                    "\nSubcategory: " + resp.getGigDetails().getSubCategoryName() + "\nRevision: " + resp.getGigDetails().getRevisions() + "\nPrice: " + "$" + resp.getGigDetails().getPrice() +
                                    "\nDeliveryTime: " + devlivey /*+ "\nImage: " + image*/;
                            setSliderImage();

                            if (_Currentpage.equals("gig_active")||_Currentpage.equals("search")) {
                                btnMorereviews.setVisibility(View.GONE);
                                reviewList = new ArrayList<>();
                                //GlobalVariables.ReviewList= new ArrayList<>();
                                reviewList = resp.getGigReviews();
                                if (reviewList.size()==0){
                                    reviewlayout.setVisibility(View.GONE);
                                }

                                if (reviewList.size() > 2) {

                                    btnMorereviews.setVisibility(View.VISIBLE);
                                    // Log.e("Gigreview",resp.gigDetails.getGigReviews().toString());
                                    adapter = new GigReviewAdapter(GigDetailsActivity.this, reviewList, 2);
                                    recyclerReviews.setAdapter(adapter);
                                    recyclerReviews.setLayoutManager(new LinearLayoutManager(GigDetailsActivity.this));
                                } else {
                                    btnMorereviews.setVisibility(View.GONE);
                                    adapter = new GigReviewAdapter(GigDetailsActivity.this, reviewList, reviewList.size());
                                    recyclerReviews.setAdapter(adapter);
                                    recyclerReviews.setLayoutManager(new LinearLayoutManager(GigDetailsActivity.this));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiGDetailsResponse> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    private void opendescdialog() {

        decline = new Dialog(GigDetailsActivity.this);
        decline.requestWindowFeature(Window.FEATURE_NO_TITLE);
        decline.setContentView(R.layout.dialog_description);
        decline.setCancelable(true);
        decline.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        decline.show();
        ImageView close = (ImageView) decline.findViewById(R.id.img_close);
        Button submit = (Button) decline.findViewById(R.id.btn_submit);
        final EditText edt_description = (EditText) decline.findViewById(R.id.txtdescription);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc = edt_description.getText().toString();

                if (desc.equals("")){
                    GlobalMethods.Toast(context,"Enter message");
                }else {
                    if (GlobalMethods.isNetworkAvailable(context)) {
                        toClickAddtocart(GlobalMethods.GetUserID(context), gig_id, seller_id, desc);
                    } else {
                        GlobalMethods.Toast(context, getString(R.string.internet));
                    }
                }
            }
        });
    }
    // add to cart
    private void toClickAddtocart(String userid, String gigid, String seller_ID, String description) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        String logg = "USER ID:" + userid + "\nGigId :" + gigid + "\nSeller :" + seller_ID + "\nDescription :" + description + "\nQuantity :" + "1";
        Log.e("ClickAddCartReq", logg);
        Call<CommonResponse> call = apiService.callcartAdd(userid, gigid, seller_ID, description, "1");
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("ClickAddCartResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    decline.dismiss();
                    if (resp.getStatus().equals("1")) {
                        GlobalMethods.Toast(context, resp.getMessage());
                        toShowCartCount();
                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("ClickAddCartFail", t.getMessage());
                progressDialog.dismiss();
                decline.dismiss();
            }
        });
    }

    public void toShowCartCount() {
        user_id=GlobalMethods.GetUserID(context);
        Log.e("CartCountReq", "UserId: " + user_id);
        Call<CartCount> call = apiService.callShoppingcartCount(user_id);
        call.enqueue(new Callback<CartCount>() {
            @Override
            public void onResponse(Call<CartCount> call, Response<CartCount> response) {
                Log.e("CartCountResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CartCount resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        txtcount.setText(resp.getCartCount());
                    } else {
                        txtcount.setText("0");
                    }
                }
            }

            public void onFailure(Call<CartCount> call, Throwable t) {
                Log.e("CartCountRespFail", t.getMessage());
            }
        });
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    private void setSliderImage() {

        try {
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {

                    if (currentPage == imageList.size()) {
                        currentPage = 0;
                    }
                    String count = String.valueOf(currentPage + 1);

                    try {
                        viewpager.setCurrentItem(currentPage++, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void Click_to_PublishGig(String userid, String Gigid) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e("PublishGigrequest", "UserId: " + user_id + "\nGigid: " + Gigid);
        Call<CommonResponse> call = apiService.callPublishGig(user_id, Gigid);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("Publishresponse", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                CommonResponse resp = response.body();
                String status = resp.getStatus();
                if (status.equals("1")) {
                    GlobalMethods.Toast(context, resp.getMessage());
                    Intent intent2 = new Intent(GigDetailsActivity.this, MainActivity.class);
                    intent2.putExtra("page", "2");
                    //editor.putString("gig_index", "0");
                    //editor.commit();
                    startActivity(intent2);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("Publishresponsefail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }
}
