package com.gigpeople.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.AdapterImageSlideSupportAdapter;
import com.gigpeople.app.adapter.GigReviewAdapter;
import com.gigpeople.app.adapter.ImageVideoAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CartCount;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.FavouriteGigListResponse;
import com.gigpeople.app.apiModel.GiGDetailsResponse;
import com.gigpeople.app.apiModel.GigCategoryResultResponse;
import com.gigpeople.app.model.ImageVideoModel;
import com.gigpeople.app.utils.GlobalApiMethod;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gigpeople.app.subfragment.SearchGigFragment.searchgigLists;

public class CategoryDetailsActivity extends AppCompatActivity {

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
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.pageController)
    PageControl pageController;
    int[] banner = {R.drawable.banner_one, R.drawable.banner_two};
    AdapterImageSlideSupportAdapter adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    String fav_status = "1";
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    @BindView(R.id.img_fav)
    ImageView imgFav;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    Dialog share;
    @BindView(R.id.img_add_cart)
    ImageView imgAddCart;
    @BindView(R.id.btn_ordernow)
    Button btnOrdernow;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;
    GigReviewAdapter adapter;
    @BindView(R.id.ratingbar1)
    RatingBar ratingbar1;
    @BindView(R.id.review_layout)
    LinearLayout reviewLayout;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    @BindView(R.id.btn_morereviews)
    Button btnMorereviews;
    Dialog decline;
    @BindView(R.id.txtprice)
    TextView txtprice;
    @BindView(R.id.txtdays)
    TextView txtdays;
    @BindView(R.id.txtrevision)
    TextView txtrevision;
    @BindView(R.id.online_layout)
    LinearLayout onlineLayout;
    @BindView(R.id.txtaddress)
    TextView txtaddress;
    @BindView(R.id.txtskils)
    TextView txtskils;
    @BindView(R.id.txtjoind)
    TextView txtjoind;
    @BindView(R.id.txtcompleted)
    TextView txtcompleted;
    @BindView(R.id.txtdescriptionsa)
    TextView txtdescriptionsa;
    Context context;
    List<GigCategoryResultResponse.CategorygigList> mData;
    @BindView(R.id.txtname)
    TextView txtname;
    @BindView(R.id.txtonlinedot)
    TextView txtonlinedot;
    @BindView(R.id.txtonlinestatus)
    TextView txtonlinestatus;
    static ApiService apiService;
    int page;
    TextView description;
    String Gig_Id, Seller_ID, UserId = "6";
    @BindView(R.id.txtcount)
    TextView txtcount;
    public static String count;
    /*@BindView(R.id.txtreviewcount)
    TextView txtreviewcount;*/
   /* @BindView(R.id.txt_gigtitle)
    TextView txtGigtitle;*/
    /*@BindView(R.id.txt_gigdescription)
    TextView txtGigdescription;*/
    String image = "";
    String from, shareBody;

    String otheruserId, otherusername, otheruserimageurl;
    List<FavouriteGigListResponse.FavouritegigList> mDataFav;
    @BindView(R.id.txt_gig_title)
    TextView txtGigTitle;
    @BindView(R.id.txt_reviews)
    TextView txtReviews;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    @BindView(R.id.txt_shipping_price)
    TextView txtShippingPrice;
    @BindView(R.id.txt_total_price)
    TextView txtTotalPrice;
    List<ImageVideoModel> imageListNew;
    ImageVideoAdapter imageAdapter;
    ProgressDialog progressDialog;
    List<GiGDetailsResponse.GigReview> reviewList;
    @BindView(R.id.img_call)
    ImageView imgCall;
    String  other_user_mobile="";
    @BindView(R.id.linear_review)
    LinearLayout linearReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        ButterKnife.bind(this);
        Window window = CategoryDetailsActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(CategoryDetailsActivity.this, R.color.colorPrimaryDark));
        }
        context = this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        UserId = GlobalMethods.GetUserID(context);
        if (getIntent() != null) {
            page = getIntent().getIntExtra("page", 0);
            from = getIntent().getStringExtra("from");
            //SetLayout(page);

            if (from.equals("category")) {
                SetLayout(page);
            } else if (from.equals("search")) {
                setGiGDetails(page);
            } else if (from.equals("Favourite")) {
                setlayoutfromFavroite(page);
            } else {
                SetLayout(page);
            }

            Log.e("MobileNumber",other_user_mobile+" nan");
            if (other_user_mobile.trim().equals("")){
                imgCall.setVisibility(View.GONE);
                Log.e("MobileNumber",other_user_mobile+" nan if");
            }else {
                imgCall.setVisibility(View.VISIBLE);
                Log.e("MobileNumber",other_user_mobile+" nan");
            }

            if (GlobalMethods.isNetworkAvailable(context)) {
                setGigReviewsList(Seller_ID, Gig_Id);
            } else {
                GlobalMethods.Toast(context, getString(R.string.internet));
            }


        }
    }

    @OnClick({R.id.img_call,R.id.btn_back_arrow, R.id.btn_cart, R.id.btn_menu, R.id.img_fav, R.id.userImage, R.id.img_add_cart, R.id.btn_ordernow, R.id.review_layout, R.id.img_chat, R.id.btn_morereviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_call:
                Intent call_intent = new Intent(Intent.ACTION_DIAL);
                call_intent.setData(Uri.parse("tel:"+other_user_mobile));
                startActivity(call_intent);
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_cart:
                Intent intent = new Intent(CategoryDetailsActivity.this, cartItemActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ordernow:
                opendescdialog();
                break;
            case R.id.btn_morereviews:
                Intent intent5 = new Intent(CategoryDetailsActivity.this, GigReviewMoreActivity.class);
                intent5.putExtra("gigid", Gig_Id);
                intent5.putExtra("user_id", Seller_ID);
                startActivity(intent5);
                break;
            case R.id.btn_menu:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.img_fav:
                GlobalApiMethod gam = new GlobalApiMethod();
                gam.toClickAddfavourite(GlobalMethods.GetUserID(this), "2", Gig_Id, context);
                if (fav_status.equals("0")) {
                    imgFav.setImageResource(R.drawable.fav_icon);
                    fav_status = "1";
                } else {
                    imgFav.setImageResource(R.drawable.fav_line);
                    fav_status = "0";
                }
                break;
            case R.id.userImage:
               /* Intent intent2 = new Intent(CategoryDetailsActivity.this, GigOwnerDetailsActivity.class);
                intent2.putExtra("seller_id",otheruserId);
                startActivity(intent2);*/
                break;
            case R.id.img_add_cart:
                if (TextUtils.isEmpty(UserId)) {
                    GlobalMethods.Toast(context, "Permission Denied");
                } else {
                    toClickAddtocart(UserId, Gig_Id, Seller_ID, "");
                }
                break;
            case R.id.review_layout:
                break;
            case R.id.img_chat:
                Intent intent4 = new Intent(CategoryDetailsActivity.this, ChatDetailsActivity.class);
                //otheruserId,tousername,otheruserimageurl;
                intent4.putExtra("other_user_id", otheruserId);
                intent4.putExtra("other_user_name", otherusername);
                intent4.putExtra("other_user_image", otheruserimageurl);
                startActivity(intent4);
                break;
        }
    }

    private void setGiGDetails(int position) {

        if (!searchgigLists.get(position).getSellerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(searchgigLists.get(position).getSellerDetails().getProfileImageUrl()).into(userImage);
        } else {
            userImage.setImageResource(R.drawable.profile_placeholder);
        }
        otheruserId = searchgigLists.get(position).getSellerDetails().getSellerId();
        other_user_mobile =searchgigLists.get(position).getSellerDetails().getMobileCountry()+" "+ searchgigLists.get(position).getSellerDetails().getPhoneNo();
        otherusername = searchgigLists.get(position).getSellerDetails().getFirstName() + searchgigLists.get(position).getSellerDetails().getLastName();
        otheruserimageurl = searchgigLists.get(position).getSellerDetails().getProfileImageUrl();

        Gig_Id = searchgigLists.get(position).getGigId();
        fav_status = searchgigLists.get(position).getIs_favourite();
        Seller_ID = searchgigLists.get(position).getSellerDetails().getSellerId();
        txtprice.setText("$" + searchgigLists.get(position).getPrice());
        txtShippingPrice.setText("$" + searchgigLists.get(position).getShippingPrice());
        txtTotalPrice.setText("$" + searchgigLists.get(position).getTotalCost());
        txtrevision.setText(searchgigLists.get(position).getRevisions());
        txtname.setText(searchgigLists.get(position).getSellerDetails().getFirstName() + " " + searchgigLists.get(position).getSellerDetails().getLastName());
        txtaddress.setText(searchgigLists.get(position).getSellerDetails().getAddress());
        txtskils.setText(searchgigLists.get(position).getSellerDetails().getSkills());
        txtGigTitle.setText(searchgigLists.get(position).getTitle());
        txtAbout.setText(searchgigLists.get(position).getDescription());
       /* String joindate = searchgigLists.get(position).getSellerDetails().getJoinDate();
        String[] splitt = joindate.split(" ");
        txtjoind.setText(splitt[0]);*/
        txtjoind.setText(" " + GlobalMethods.DateTime(searchgigLists.get(position).getSellerDetails().getJoinDate()));
        txtcompleted.setText(" " + searchgigLists.get(position).getSellerDetails().getOrdersCompleted());
        txtdescriptionsa.setText(searchgigLists.get(position).getSellerDetails().getAbout());
        txtTitle.setText(searchgigLists.get(position).getCategoryName());

        if (searchgigLists.get(position).getGigRating() != null) {

            ratingbar1.setRating(Float.parseFloat(searchgigLists.get(position).getGigRating()));
        }

        if (searchgigLists.get(position).getSellerDetails().getRating() != null) {

            ratingbar.setRating(Float.parseFloat(searchgigLists.get(position).getSellerDetails().getRating()));

        }

        txtReviews.setText(searchgigLists.get(position).getReview_count() + " " + "reviews");
        if (searchgigLists.get(position).getReview_count().equals("0")){
            linearReview.setVisibility(View.GONE);
        }
        List<String> temp = new ArrayList<>();
        int size = searchgigLists.get(position).getImageList().size();

        for (int i = 0; i < searchgigLists.get(page).getImageList().size(); i++) {

            if (image.equals("")) {
                image = searchgigLists.get(page).getImageList().get(i).getFile();
            } else {
                image += "\n" + searchgigLists.get(page).getImageList().get(i).getFile();
            }
        }
        String deliveryTime = "";
        if (searchgigLists.get(position).getDeliveryTime().equals("1")) {
            deliveryTime = searchgigLists.get(position).getDeliveryTime() + " day";
        } else {
            deliveryTime = searchgigLists.get(position).getDeliveryTime() + " days";
        }
        txtdays.setText(deliveryTime);
        shareBody = "\nGigPeople" + "\nTitle: " + searchgigLists.get(page).getTitle() + "\nCategory: " + searchgigLists.get(page).getCategoryName() +
                "\nSubcategory: " + searchgigLists.get(page).getSubCategoryName()+ "\nRevision: " + searchgigLists.get(page).getRevisions() + "\nPrice: " + "$" + searchgigLists.get(page).getPrice() +
                "\nDeliveryTime: " + deliveryTime /*+ "\nImage: " + image*/;


        if (searchgigLists.get(position).getSellerDetails().getLiveStatus().equals("1")) {
            txtonlinestatus.setText("Online");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_green);

        } else {

            txtonlinestatus.setText("Offline");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_red);

        }

        if (searchgigLists.get(position).getIs_favourite().equals("1")) {
            imgFav.setImageResource(R.drawable.fav_icon);

        } else {
            imgFav.setImageResource(R.drawable.fav_line);

        }


        for (int i = 0; i < size; i++) {
            temp.add(searchgigLists.get(position).getImageList().get(i).getThumnail());
        }
        adapterImageSlide = new AdapterImageSlideSupportAdapter(CategoryDetailsActivity.this, temp);
        viewpager.setAdapter(adapterImageSlide);
        pageController.setViewPager(viewpager);

        try {


            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;

                    //Log.e("position",currentPage+"");
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

                    if (currentPage == banner.length) {
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
       /* adapter = new GigReviewAdapter(CategoryDetailsActivity.this);
        recyclerReviews.setAdapter(adapter);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(CategoryDetailsActivity.this));*/


    }

    private void SetLayout(int position) {


        mData = new ArrayList<>();

        mData = GlobalVariables.DashboardMenuDetialspage;
        if (!mData.get(position).getSellerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(mData.get(position).getSellerDetails().getProfileImageUrl()).into(userImage);
        } else {
            userImage.setImageResource(R.drawable.profile_placeholder);
        }

        otheruserId = mData.get(position).getSellerDetails().getSellerId();
        other_user_mobile = mData.get(position).getSellerDetails().getMobileCountry()+" "+mData.get(position).getSellerDetails().getPhoneNo();
        otherusername = mData.get(position).getSellerDetails().getFirstName() + mData.get(position).getSellerDetails().getLastName();
        otheruserimageurl = mData.get(position).getSellerDetails().getProfileImageUrl();

        Gig_Id = mData.get(position).getGigId();
        fav_status = mData.get(position).getIsFavourite();
        Seller_ID = mData.get(position).getSellerDetails().getSellerId();
        txtprice.setText("$" + mData.get(position).getPrice());
        txtShippingPrice.setText("$" + mData.get(position).getShippingPrice());
        txtTotalPrice.setText("$" + mData.get(position).getTotalCost());
        txtAbout.setText(mData.get(position).getDescription());
        txtrevision.setText(mData.get(position).getRevisions());
        txtGigTitle.setText(mData.get(position).getTitle());
        txtname.setText(mData.get(position).getSellerDetails().getFirstName() + " " + mData.get(position).getSellerDetails().getLastName());
        txtaddress.setText(mData.get(position).getSellerDetails().getAddress());
        txtskils.setText(mData.get(position).getSellerDetails().getSkills());

        for (int i = 0; i < mData.get(page).getImageList().size(); i++) {

            if (image.equals("")) {
                image = mData.get(page).getImageList().get(i).getFile();
            } else {
                image += "\n" + mData.get(page).getImageList().get(i).getFile();
            }
        }
        String delivery_time = "";
        if (mData.get(position).getDeliveryTime().equals("1")) {
            delivery_time = mData.get(position).getDeliveryTime() + " day";
        } else {
            delivery_time = mData.get(position).getDeliveryTime() + " days";
        }
        txtdays.setText(delivery_time);
        shareBody = "\nGigPeople" + "\nTitle: " + mData.get(page).getTitle() + "\nCategory: " + mData.get(page).getCategoryName() +
                "\nSubcategory: " + mData.get(page).getSubCategoryName()+"\nRevision: " + mData.get(page).getRevisions() + "\nPrice: " + "$" + mData.get(page).getPrice() +
                "\nDeliveryTime: " + delivery_time /*+ "\nImage: " + image*/;

        txtjoind.setText(" " + GlobalMethods.DateTime(mData.get(position).getSellerDetails().getJoinDate()));
        txtcompleted.setText(" " + mData.get(position).getSellerDetails().getOrdersCompleted());
        txtdescriptionsa.setText(mData.get(position).getSellerDetails().getAbout());
        txtTitle.setText(mData.get(position).getCategoryName());
        List<String> temp = new ArrayList<>();
        int size = mData.get(position).getImageList().size();

        if (mData.get(position).getGigRating() != null) {

            ratingbar1.setRating(Float.parseFloat(mData.get(position).getGigRating()));
        }

        if (mData.get(position).getSellerDetails().getRating() != null) {

            ratingbar.setRating(Float.parseFloat(mData.get(position).getSellerDetails().getRating()));
        }

        txtReviews.setText(mData.get(position).getReviewCount() + " " + "reviews");
        if (mData.get(position).getReviewCount() .equals("0")){
            linearReview.setVisibility(View.GONE);
        }
        if (mData.get(position).getSellerDetails().getLiveStatus().equals("1")) {
            txtonlinestatus.setText("Online");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_green);

        } else {

            txtonlinestatus.setText("Offline");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_red);
        }


        if (mData.get(position).getIsFavourite().equals("1")) {
            imgFav.setImageResource(R.drawable.fav_icon);

        } else {
            imgFav.setImageResource(R.drawable.fav_line);

        }


        /*for (int i = 0; i < size; i++) {
            temp.add(mData.get(position).getImageList().get(i).getThumnail());
        }
        adapterImageSlide = new ImageVideoAdapter(CategoryDetailsActivity.this, temp);
        viewpager.setAdapter(adapterImageSlide);
        pageController.setViewPager(viewpager);*/

        imageListNew = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            imageListNew.add(new ImageVideoModel(mData.get(position).getImageList().get(i).getFile(), mData.get(position).getImageList().get(i).getThumnail(), mData.get(position).getImageList().get(i).getType()));
        }
        imageAdapter = new ImageVideoAdapter(context, imageListNew);
        viewpager.setAdapter(imageAdapter);
        pageController.setViewPager(viewpager);

        try {

            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;

                    //Log.e("position",currentPage+"");
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

                    if (currentPage == imageListNew.size()) {
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
        /*adapter = new GigReviewAdapter(CategoryDetailsActivity.this);
        recyclerReviews.setAdapter(adapter);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(CategoryDetailsActivity.this));*/

    }

    private void setlayoutfromFavroite(int position) {

        mDataFav = new ArrayList<>();
        mDataFav = GlobalVariables.FavouriteGigList;

        if (!mDataFav.get(position).getProfileImageUrl().trim().equals("")) {
            Glide.with(context).load(mDataFav.get(position).getProfileImageUrl()).into(userImage);
        }
        Gig_Id = mDataFav.get(position).getGigDetails().get(0).getGigId();
        Seller_ID = mDataFav.get(position).getSellerId();
        // need fill data on here
        txtGigTitle.setText(mDataFav.get(position).getGigDetails().get(0).getTitle());

        txtShippingPrice.setText("$" + mDataFav.get(position).getGigDetails().get(0).getShippingPrice());
        txtTotalPrice.setText("$" + mDataFav.get(position).getGigDetails().get(0).getTotalCost());

        txtAbout.setText(mDataFav.get(position).getGigDetails().get(0).getDescription());

        ratingbar1.setRating(Float.parseFloat(mDataFav.get(position).getGigDetails().get(0).getGigRating()));
        txtReviews.setText(mDataFav.get(position).getGigDetails().get(0).getReviewCount() + " " + "reviews");
        if (mDataFav.get(position).getGigDetails().get(0).getReviewCount() .equals("0")){
            linearReview.setVisibility(View.GONE);
        }

        for (int i = 0; i < mDataFav.get(page).getGigDetails().get(0).getImageList().size(); i++) {

            if (image.equals("")) {
                image = mDataFav.get(page).getGigDetails().get(0).getImageList().get(i).getFile();
            } else {
                image += "\n" + mDataFav.get(page).getGigDetails().get(0).getImageList().get(i).getFile();
            }
        }
        String delivery_time = "";
        if (mDataFav.get(position).getGigDetails().get(0).getDeliveryTime().equals("1")) {
            delivery_time = mDataFav.get(position).getGigDetails().get(0).getDeliveryTime() + " day";
        } else {
            delivery_time = mDataFav.get(position).getGigDetails().get(0).getDeliveryTime() + " days";
        }
        txtdays.setText(delivery_time);
        shareBody = "\nGigPeople" + "\nTitle: " + mDataFav.get(page).getGigDetails().get(0).getTitle() + "\nCategory: " + mDataFav.get(page).getGigDetails().get(0).getCategoryName() +
                "\nSubcategory: " + mDataFav.get(page).getGigDetails().get(0).getSubCategoryName()+ "\nRevision: " + mDataFav.get(page).getGigDetails().get(0).getRevisions() + "\nPrice: " + "$" + mDataFav.get(page).getGigDetails().get(0).getPrice() +
                "\nDeliveryTime: " + delivery_time /*+ "\nImage: " + image*/;

       /* sharegigtitle=mDataFav.get(position).getGigDetails().get(0).getTitle();
        sharecategoryname=mDataFav.get(position).getGigDetails().get(0).getCategoryName();
        sharesubcategoryname=mDataFav.get(position).getGigDetails().get(0).getSubCategoryName();
        shareprice=mDataFav.get(position).getGigDetails().get(0).getPrice();
        sharedeliverytime=mDataFav.get(position).getGigDetails().get(0).getDeliveryTime();*/


        otheruserId = mDataFav.get(position).getSellerId();
        other_user_mobile = mDataFav.get(position).getMobileCountry()+" "+mDataFav.get(position).getPhoneNo();
        otherusername = mDataFav.get(position).getFirstName() + mDataFav.get(position).getLastName();
        otheruserimageurl = mDataFav.get(position).getProfileImageUrl();


        txtprice.setText("$" + mDataFav.get(position).getGigDetails().get(0).getPrice());
        if (mDataFav.get(position).getGigDetails().get(0).getDeliveryTime().equals("1")) {
            txtdays.setText(mDataFav.get(position).getGigDetails().get(0).getDeliveryTime() + " day");
        } else {
            txtdays.setText(mDataFav.get(position).getGigDetails().get(0).getDeliveryTime() + " days");
        }
        txtrevision.setText(mDataFav.get(position).getGigDetails().get(0).getRevisions());
        txtname.setText(mDataFav.get(position).getFirstName() + " " + mDataFav.get(position).getLastName());
        txtaddress.setText(mDataFav.get(position).getAddress());
        txtskils.setText(mDataFav.get(position).getSkills());
//txtdescriptionsa.setText();
        float ratting = Float.parseFloat(mDataFav.get(position).getRating());
        ratingbar.setRating(ratting);

        /*String joindate = mData.get(position).getSellerDetails().getJoin_date();
        String[] splitt = joindate.split(" ");
*/
        txtjoind.setText(" " + GlobalMethods.DateConverdion(mDataFav.get(position).getJoinDate()));
        txtcompleted.setText(" " + mDataFav.get(position).getOrdersCompleted());
        txtdescriptionsa.setText(mDataFav.get(position).getAbout());
        txtTitle.setText(mDataFav.get(position).getGigDetails().get(0).getCategoryName());
        int size = mDataFav.get(position).getGigDetails().get(0).getImageList().size();


        if (mDataFav.get(position).getLiveStatus().equals("1")) {
            txtonlinestatus.setText("Online");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_green);
        } else {
            txtonlinestatus.setText("Offline");
            txtonlinedot.setBackgroundResource(R.drawable.circlebackground_red);
        }
        fav_status = mDataFav.get(position).getGigDetails().get(0).getIsFavourite();
        if (mDataFav.get(position).getGigDetails().get(0).getIsFavourite().equals("1")) {
            imgFav.setImageResource(R.drawable.fav_icon);

        } else {
            imgFav.setImageResource(R.drawable.fav_line);
        }

        imageListNew = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            imageListNew.add(new ImageVideoModel(mDataFav.get(position).getGigDetails().get(0).getImageList().get(i).getFile(), mDataFav.get(position).getGigDetails().get(0).getImageList().get(i).getThumnail(), mDataFav.get(position).getGigDetails().get(0).getImageList().get(i).getType()));
        }
        imageAdapter = new ImageVideoAdapter(context, imageListNew);
        viewpager.setAdapter(imageAdapter);
        pageController.setViewPager(viewpager);
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

                    if (currentPage == imageListNew.size()) {
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
                        toShowCartCount(UserId);
                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("ClickAddCartFail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void getcount() {

        String userid = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        if (GlobalMethods.isNetworkAvailable(context)) {
            toShowCartCount(userid);
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

    }

    public void toShowCartCount(String userId) {
        Log.e("CartCountReq", "UserId: " + userId);
        Call<CartCount> call = apiService.callShoppingcartCount(userId);
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
                count = "0";
            }
        });
    }

    private void opendescdialog() {

        decline = new Dialog(CategoryDetailsActivity.this);
        decline.requestWindowFeature(Window.FEATURE_NO_TITLE);
        decline.setContentView(R.layout.dialog_description);
        decline.setCancelable(true);
        decline.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        decline.show();
        ImageView close = (ImageView) decline.findViewById(R.id.img_close);
        Button submit = (Button) decline.findViewById(R.id.btn_submit);
        description = (EditText) decline.findViewById(R.id.txtdescription);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc = description.getText().toString();

                if (desc.equals("")){
                    GlobalMethods.Toast(context,"Enter message");
                }else {
                    if (GlobalMethods.isNetworkAvailable(context)) {
                        toClickAddtocart(UserId, Gig_Id, Seller_ID, desc);
                    } else {
                        GlobalMethods.Toast(context, getString(R.string.internet));
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getcount();
    }

    private void setGigReviewsList(String user_id, String Gigid) {
        Log.e("RequestDetailReq", "UserId:" + user_id + " RequestId: " + Gigid);
        Call<GiGDetailsResponse> call = apiService.callgigDetails(user_id, Gigid);
        call.enqueue(new Callback<GiGDetailsResponse>() {
            @Override
            public void onResponse(Call<GiGDetailsResponse> call, Response<GiGDetailsResponse> response) {
                Log.e("RequestDetailResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GiGDetailsResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            reviewList = new ArrayList<>();
                            reviewList = resp.getGigReviews();


                            if (reviewList.size() > 2) {
                                btnMorereviews.setVisibility(View.VISIBLE);
                                adapter = new GigReviewAdapter(context, reviewList, 2);
                                recyclerReviews.setAdapter(adapter);
                                recyclerReviews.setLayoutManager(new LinearLayoutManager(CategoryDetailsActivity.this));
                            } else {
                                btnMorereviews.setVisibility(View.GONE);
                                adapter = new GigReviewAdapter(context, reviewList, reviewList.size());
                                recyclerReviews.setAdapter(adapter);
                                recyclerReviews.setLayoutManager(new LinearLayoutManager(CategoryDetailsActivity.this));

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
}
