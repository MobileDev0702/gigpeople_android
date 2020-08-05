package com.gigpeople.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.androidpagecontrol.PageControl;
import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.AdapterImageSlideCommon;
import com.gigpeople.app.adapter.RequestReviewAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.RequestDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;
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

public class ManageRequestDetails_2 extends AppCompatActivity {


    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.relativeMenu)
    RelativeLayout relativeMenu;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.page_controller)
    PageControl pageController;
    @BindView(R.id.recycel_reviews)
    RecyclerView recycelReviews;
    RequestReviewAdapter adapter;
    List<String> banner;
    AdapterImageSlideCommon adapterImageSlide;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    @BindView(R.id.btn_edit)
    Button btnEdit;
    Context context;
    ApiService apiService;
    String user_id, request_id;
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.txt_sub_category)
    TextView txtSubCategory;
    @BindView(R.id.txt_delivery_time)
    TextView txtDeliveryTime;
    @BindView(R.id.txt_quantity)
    TextView txtQuantity;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    String title, category, subcategory, category_id, subcategory_id, price, quantityt, description, deliver_time;
    public static List<RequestDetailResponse.OfferDetail> offerDetailList;

    public static List<RequestDetailResponse.RequestDetails.ImageList> imageLists;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.linear_request)
    LinearLayout linearRequest;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.txt_live_status)
    TextView txtLiveStatus;
    @BindView(R.id.txt_live)
    TextView txtLive;
    @BindView(R.id.online_layout)
    LinearLayout onlineLayout;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_skills)
    TextView txtSkills;
    @BindView(R.id.txt_join)
    TextView txtJoin;
    @BindView(R.id.txt_count_order)
    TextView txtCountOrder;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    @BindView(R.id.offer_txt_delivery_time)
    TextView offerTxtDeliveryTime;
    @BindView(R.id.offer_txt_quantity)
    TextView offerTxtQuantity;
    @BindView(R.id.offer_txt_price)
    TextView offerTxtPrice;
    @BindView(R.id.offer_txt_description)
    TextView offerTxtDescription;
    @BindView(R.id.linear_accept)
    LinearLayout linearAccept;
    String other_userid,other_username,other_image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestdetails_2);
        ButterKnife.bind(this);
        Window window = ManageRequestDetails_2.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ManageRequestDetails_2.this, R.color.colorPrimaryDark));
        }
        context = ManageRequestDetails_2.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        if (getIntent() != null) {
            request_id = getIntent().getStringExtra("request_id");
        }
    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_menu, R.id.btn_edit,R.id.img_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                finish();
                break;
            case R.id.btn_menu:
                break;
            case R.id.btn_edit:
                Intent edit = new Intent(ManageRequestDetails_2.this, EditRequestActivity.class);
                edit.putExtra("request_id", request_id);
                edit.putExtra("category", category);
                edit.putExtra("subcategory", subcategory);
                edit.putExtra("category_id", category_id);
                edit.putExtra("subcategory_id", subcategory_id);
                edit.putExtra("price", price);
                edit.putExtra("deliver_time", deliver_time);
                edit.putExtra("description", description);
                edit.putExtra("quantity", quantityt);
                startActivity(edit);
                break;
            case R.id.img_chat:
                Intent intent4 = new Intent(ManageRequestDetails_2.this, ChatDetailsActivity.class);
                intent4.putExtra("other_user_id",other_userid );
                intent4.putExtra("other_user_name",other_username);
                intent4.putExtra("other_user_image",other_image);
                startActivity(intent4);
                break;
        }
    }

    private void setRequestDetail() {
        Log.e("RequestDetailReq", "UserId:" + user_id + " RequestId: " + request_id);
        Call<RequestDetailResponse> call = apiService.callRequestDetailsAPI(user_id, request_id);
        call.enqueue(new Callback<RequestDetailResponse>() {
            @Override
            public void onResponse(Call<RequestDetailResponse> call, Response<RequestDetailResponse> response) {
                Log.e("RequestDetailResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    RequestDetailResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            txtCategory.setText(resp.getRequestDetails().getCategoryName());
                            txtSubCategory.setText(resp.getRequestDetails().getSubCategoryName());
                            if (resp.getRequestDetails().getDeliverytime().equals("1")) {
                                txtDeliveryTime.setText(resp.getRequestDetails().getDeliverytime()+" day");
                            }else {
                                txtDeliveryTime.setText(resp.getRequestDetails().getDeliverytime()+" days");
                            }
                            txtQuantity.setText(resp.getRequestDetails().getQuantity());
                            txtDescription.setText(resp.getRequestDetails().getDescription());

                            category = resp.getRequestDetails().getCategoryName();
                            category_id = resp.getRequestDetails().getCategoryId();
                            subcategory = resp.getRequestDetails().getSubCategoryName();
                            subcategory_id = resp.getRequestDetails().getSubCategoryId();
                            price = resp.getRequestDetails().getPrice();
                            description = resp.getRequestDetails().getDescription();
                            request_id = resp.getRequestDetails().getRequestId();
                            deliver_time = resp.getRequestDetails().getDeliverytime();
                            quantityt = resp.getRequestDetails().getQuantity();

                            txtPrice.setText("$" + price);

                            imageLists = resp.getRequestDetails().getImageList();
                            banner = new ArrayList<>();
                            for (int i = 0; i < resp.getRequestDetails().getImageList().size(); i++) {
                                banner.add(resp.getRequestDetails().getImageList().get(i).getImageUrl());
                            }
                            adapterImageSlide = new AdapterImageSlideCommon(ManageRequestDetails_2.this, banner);
                            viewPager.setAdapter(adapterImageSlide);
                            pageController.setViewPager(viewPager);

                            setSliderImage();

                            if (resp.getRequestDetails().getRequest_status().equals("1")) {
                                linearRequest.setVisibility(View.GONE);
                                btnEdit.setVisibility(View.GONE);
                                linearAccept.setVisibility(View.VISIBLE);

                                txtName.setText(resp.getAcceptDetails().getFirstName() + " " + resp.getAcceptDetails().getLastName());
                                txtAddress.setText(resp.getAcceptDetails().getAddress());
                                txtDescription.setText(resp.getAcceptDetails().getDescription());
                                txtAbout.setText(resp.getAcceptDetails().getAbout());
                                txtPrice.setText("$" + resp.getAcceptDetails().getPrice());
                                //txtCategory.setText(offerDetailList.get(position).getCategoryName() + "/" + offerDetailList.get(position).getSubCategoryName());
                                txtSkills.setText(resp.getAcceptDetails().getSkills());
                                if (!resp.getAcceptDetails().getProfileImageUrl().equals("")) {
                                    Glide.with(context).load(resp.getAcceptDetails().getProfileImageUrl()).into(userImage);
                                }
                                txtJoin.setText(" " + GlobalMethods.DateConverdion(resp.getAcceptDetails().getJoinDate()));
                                txtCountOrder.setText(" " + resp.getAcceptDetails().getOrdersCompleted());

                                if (resp.getAcceptDetails().getLiveStatus().equals("1")) {
                                    txtLiveStatus.setBackgroundResource(R.drawable.circlebackground_green);
                                    txtLive.setText("Online");
                                    txtLive.setTextColor(getResources().getColor(R.color.colorGreen));

                                } else {
                                    txtLiveStatus.setBackgroundResource(R.drawable.circlebackground_red);
                                    txtLive.setText("Offline");
                                    txtLive.setTextColor(getResources().getColor(R.color.colorRed));
                                }

                                if (resp.getAcceptDetails().getRating() != null) {
                                    ratingbar.setRating(Float.parseFloat(resp.getAcceptDetails().getRating()));
                                }
                                String delivery_time="";
                                if (resp.getAcceptDetails().getDeliverytime().equals("1")){
                                    delivery_time=resp.getAcceptDetails().getDeliverytime()+" day";
                                }else {
                                    delivery_time=resp.getAcceptDetails().getDeliverytime()+" days";
                                }
                                offerTxtDeliveryTime.setText(delivery_time);
                                offerTxtPrice.setText("$"+resp.getAcceptDetails().getPrice());
                                offerTxtDescription.setText(resp.getAcceptDetails().getDescription());

                                other_userid=resp.getAcceptDetails().getSellerId();
                                other_image=resp.getAcceptDetails().getProfileImageUrl();
                                other_username=resp.getAcceptDetails().getFirstName()+" "+resp.getAcceptDetails().getLastName();


                            } else {
                                linearRequest.setVisibility(View.VISIBLE);
                                btnEdit.setVisibility(View.VISIBLE);
                                linearAccept.setVisibility(View.GONE);
                                offerDetailList = new ArrayList<>();
                                offerDetailList = resp.getOfferDetails();
                                Log.e("OfferSize", offerDetailList.size() + " ");
                                if (recycelReviews != null) {
                                    adapter = new RequestReviewAdapter(ManageRequestDetails_2.this, offerDetailList, request_id);
                                    recycelReviews.setAdapter(adapter);
                                    recycelReviews.setLayoutManager(new LinearLayoutManager(ManageRequestDetails_2.this));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestDetailResponse> call, Throwable t) {
                Log.e("RequestDetailFail",t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalMethods.isNetworkAvailable(context)) {
            setRequestDetail();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

    }

    private void setSliderImage() {

        try {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

                    if (currentPage == banner.size()) {
                        currentPage = 0;
                    }
                    String count = String.valueOf(currentPage + 1);

                    try {
                        viewPager.setCurrentItem(currentPage++, true);
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

}
