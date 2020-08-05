package com.gigpeople.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.Gravity;
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
import com.gigpeople.app.adapter.DeliveredOrderedAdapter;
import com.gigpeople.app.adapter.ImageAdapter;
import com.gigpeople.app.adapter.RevisionAdapter;
import com.gigpeople.app.adapter.SaleAdapter;
import com.gigpeople.app.adapter.SalesReviewAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.MyOrdersDetailResponse;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
import com.gigpeople.app.apiModel.PaymentHistoryResponse;
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

import static com.gigpeople.app.activity.PaymentHistoryActivity.amountSpendingList;

public class ActiveOrdersActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.relativeMenu)
    RelativeLayout relativeMenu;
    @BindView(R.id.viewpagergig)
    ViewPager viewpagergig;
    @BindView(R.id.img_fav)
    ImageView imgFav;
    @BindView(R.id.pageController)
    PageControl pageController;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    /*@BindView(R.id.btn_cancel)
    Button btnCancel;*/
    int fav_status = 0;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 300;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 2000; // time in milliseconds between successive task executions.
    Dialog share, canceldialog;
    Dialog rating, reviewdialog;
    ImageView fivestar, img_4star, img_3star, img_2star, img_1star;
    LinearLayout lin_5star, lin_4star, lin_3star, lin_2star, lin_1star;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.review)
    ImageView review;
    @BindView(R.id.userreview)
    ImageView userreview;
    @BindView(R.id.message)
    ImageView message;
    @BindView(R.id.revision)
    ImageView revision;
    @BindView(R.id.dispute)
    ImageView dispute;
    @BindView(R.id.cancel)
    ImageView cancel;
    Dialog messagedialog;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    @BindView(R.id.workhistory)
    TextView workhistory;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.workhistory_layout)
    LinearLayout workhistoryLayout;
    @BindView(R.id.reviews)
    TextView reviews;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.review_layout)
    LinearLayout reviewLayout;
    @BindView(R.id.recycler_workhistory)
    RecyclerView recyclerWorkhistory;
    @BindView(R.id.recycler_review)
    RecyclerView recyclerReview;

    @BindView(R.id.txt_icon_status)
    TextView txtIcononlineStatus;
    @BindView(R.id.txt_onlinesatus)
    TextView txtOnlinesatus;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_expertinfo)
    TextView txtExpertinfo;
    @BindView(R.id.txt_joindate)
    TextView txtJoindate;
    @BindView(R.id.txt_orddercompleted)
    TextView txtOrddercompleted;
    @BindView(R.id.txt_gigquantity)
    TextView txtGigquantity;
    @BindView(R.id.txt_shippingprice)
    TextView txtShippingprice;
    @BindView(R.id.txt_totalcast)
    TextView txtTotalcast;
    @BindView(R.id.txt_gig_title)
    TextView txtGigTitle;
    List<MyOrdersListResponse.ActiveOrderList> mData;
    ArrayList<String> imageList;
    @BindView(R.id.txt_gig_description)
    TextView txtGigDescription;
    @BindView(R.id.txt_user_description)
    TextView txtUserDescription;
    int position;
    List<MyOrdersListResponse.ActiveOrderList> ActiveOrdersList;
    List<MyOrdersListResponse.DeliveredOrderList> DeliveredOrdersList;
    List<MyOrdersListResponse.CancelledOrderList> CancelOrdersList;
    List<PaymentHistoryResponse.AmountSpending> amountSpendList;
    SaleAdapter adapter1;
    List<MyOrdersDetailResponse.MessageList> sale_list;
    SalesReviewAdapter salesReviewAdapter;
    String page, user_id, order_id;
    ImageAdapter imageAdapter;
    ApiService apiService;
    Context context;
    ProgressDialog progressDialog;
    Dialog messagesialog, disputedialog;
    String userimage, userName, gigTitle, category, requirement, gigDes, userRating, userOnlineStatus, userLocation, userInfo, userJoinedDate, price, userCompletedOrdercount, userDes, gigQuantity, shippingPrice, totalCost, userAddress, shareBody, other_userId;
    @BindView(R.id.recycler_revision)
    RecyclerView recyclerRevision;
    @BindView(R.id.txt_order_dispute_description)
    TextView txtOrderDisputeDescription;
    @BindView(R.id.txt_cancel_order_description)
    TextView txtCancelOrderDescription;
    @BindView(R.id.linear_cancell_order)
    LinearLayout linearCancellOrder;
    @BindView(R.id.linear_order_dispute)
    LinearLayout linearOrderDispute;
    RevisionAdapter revisionAdapter;
    List<MyOrdersDetailResponse.RevisionOrder> revisionOrdersList;
    String rating_value = "";
    @BindView(R.id.txt_order_start_date)
    TextView txtOrderStartDate;
    @BindView(R.id.txt_order_delivery_date)
    TextView txtOrderDeliveryDate;
    @BindView(R.id.linear_bottom)
    LinearLayout linearBottom;
    @BindView(R.id.txt_time_extention)
    TextView txtTimeExtention;
    @BindView(R.id.txt_time_extention_description)
    TextView txtTimeExtentionDescription;
    @BindView(R.id.txt_time_extention_status)
    TextView txtTimeExtentionStatus;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_decline)
    Button btnDecline;
    @BindView(R.id.linear_accept_reject)
    LinearLayout linearAcceptReject;
    @BindView(R.id.linear_accept_reject_time_extension)
    LinearLayout linearAcceptRejectTimeExtension;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_orderId)
    TextView txtOrderId;
    @BindView(R.id.relative_order_started)
    RelativeLayout relativeOrderStarted;
    @BindView(R.id.relative_your_delivery)
    RelativeLayout relativeYourDelivery;
    @BindView(R.id.txt_categort)
    TextView txtCategort;
    @BindView(R.id.txt_requirement)
    TextView txtRequirement;
    @BindView(R.id.txt_cancel_status)
    TextView txtCancelStatus;
    @BindView(R.id.btn_accept_cancel)
    Button btnAcceptCancel;
    @BindView(R.id.btn_decline_cancel)
    Button btnDeclineCancel;
    @BindView(R.id.linear_accept_reject_cancel)
    LinearLayout linearAcceptRejectCancel;
    @BindView(R.id.recycler_delivery)
    RecyclerView recyclerDelivery;
    DeliveredOrderedAdapter deliveredOrderedAdapter;
    List<MyOrdersDetailResponse.OrderDelivery> delivered_list;
    @BindView(R.id.linear_time)
    LinearLayout linearTime;

    String gig_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders_actice_details);
        ButterKnife.bind(this);
        Window window = ActiveOrdersActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ActiveOrdersActivity.this, R.color.colorPrimaryDark));
        }
        context = ActiveOrdersActivity.this;
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);

        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            page = getIntent().getStringExtra("page");
            Log.e("page",page);
            if (page.equals("fcm")) {
                order_id = getIntent().getStringExtra("order_id");

                if (GlobalMethods.isNetworkAvailable(context)) {
                    setDetail();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            } else {
                setlayoutdataloadfromChahed(position, page);
            }

        }

    }

    @OnClick({R.id.btn_accept_cancel, R.id.btn_decline_cancel, R.id.btn_decline, R.id.btn_accept, R.id.dispute, R.id.btn_back_arrow, R.id.btn_menu, R.id.img_fav, R.id.userImage, R.id.review, R.id.userreview, R.id.revision, R.id.message, R.id.cancel, R.id.img_share, R.id.img_chat, R.id.workhistory_layout, R.id.review_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept_cancel:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setCancellAcceptReject("1");
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.btn_decline_cancel:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setCancellAcceptReject("2");
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.btn_decline:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setAcceptReject("3");
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.btn_accept:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setAcceptReject("2");
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.dispute:
                setDisputeDialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                openmessagedialog();
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
            case R.id.userImage:
                /*Intent intent1 = new Intent(ActiveOrdersActivity.this, ReviewListActivity.class);
                startActivity(intent1);*/
                break;
            case R.id.review:
                openratingdialog("User Feedback", "UF");
                break;
            case R.id.userreview:
                openratingdialog("Gig Feedback", "GF");
                break;
            case R.id.revision:
                openrevisiondialog();
                break;
            case R.id.message:
                setMessageDialog();
                break;
            case R.id.cancel:
                setCancellDialog();
                break;
            case R.id.img_chat:
                Intent intent4 = new Intent(ActiveOrdersActivity.this, ChatDetailsActivity.class);
                intent4.putExtra("other_user_id", other_userId);
                intent4.putExtra("other_user_name", userName);
                intent4.putExtra("other_user_image", userimage);
                startActivity(intent4);
                break;
            case R.id.img_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.workhistory_layout:
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                view1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.VISIBLE);
                recyclerReview.setVisibility(View.GONE);
                break;
            case R.id.review_layout:
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                view2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.GONE);
                recyclerReview.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void setlayoutdataloadfromChahed(int position, String page) {

        ActiveOrdersList = new ArrayList<>();
        DeliveredOrdersList = new ArrayList<>();
        CancelOrdersList = new ArrayList<>();
        amountSpendList = new ArrayList<>();
        imageList = new ArrayList<>();
        switch (page) {
            case "active":
                ActiveOrdersList = GlobalVariables.MyorderActiveListpage;
                order_id = ActiveOrdersList.get(position).getRequestId();
                userInfo = ActiveOrdersList.get(position).getSellerDetails().getSkills();
                other_userId = ActiveOrdersList.get(position).getSellerDetails().getSellerId();
                userimage = ActiveOrdersList.get(position).getSellerDetails().getProfileImageUrl();
                userOnlineStatus = ActiveOrdersList.get(position).getSellerDetails().getLiveStatus();
                userAddress = ActiveOrdersList.get(position).getSellerDetails().getAddress();
                totalCost = ActiveOrdersList.get(position).getTotalCost();
                userJoinedDate = ActiveOrdersList.get(position).getSellerDetails().getJoinDate();
                gigQuantity = ActiveOrdersList.get(position).getQuantity();
                shippingPrice = ActiveOrdersList.get(position).getShippingPrice();
                price = ActiveOrdersList.get(position).getPrice();
                userRating = ActiveOrdersList.get(position).getSellerDetails().getRating();
                userCompletedOrdercount = ActiveOrdersList.get(position).getSellerDetails().getOrdersCompleted();
                gigTitle = ActiveOrdersList.get(position).getTitle();
                gigDes = ActiveOrdersList.get(position).getDescription();
                userLocation = ActiveOrdersList.get(position).getSellerDetails().getAddress();
                userDes = ActiveOrdersList.get(position).getSellerDetails().getAbout();
                userName = ActiveOrdersList.get(position).getSellerDetails().getFirstName() + " " + ActiveOrdersList.get(position).getSellerDetails().getLastName();
                category = ActiveOrdersList.get(position).getCategoryName() + "/" + ActiveOrdersList.get(position).getSubCategoryName();
                requirement = ActiveOrdersList.get(position).getRequirement();
                Log.e("seller_id",other_userId+"id");

                if (ActiveOrdersList.get(position).getImageList().size() > 0) {
                    for (int i = 0; i < ActiveOrdersList.get(position).getImageList().size(); i++) {
                        imageList.add(ActiveOrdersList.get(position).getImageList().get(i).getImageUrl());
                    }
                }
               /* String image = "";
                for (int i = 0; i < ActiveOrdersList.get(position).getImageList().size(); i++) {
                    if (image.equals("")) {
                        image = ActiveOrdersList.get(position).getImageList().get(i).getImageUrl();
                    } else {
                        image += "\n" + ActiveOrdersList.get(position).getImageList().get(i).getImageUrl();
                    }
                }*/
                shareBody = "\nGigPeople" + "\nTitle: " + ActiveOrdersList.get(position).getTitle() + "\nCategory: " + ActiveOrdersList.get(position).getCategoryName() +
                        "\nSubcategory: " + ActiveOrdersList.get(position).getSubCategoryName() + "\nPrice: " + "$" + ActiveOrdersList.get(position).getPrice() +
                        "\nDeliveryTime: " + ActiveOrdersList.get(position).getDeliverytime() + " days" /*+ "\nImage: " + image*/;
                break;

            case "cancelled":
                CancelOrdersList = GlobalVariables.MyorderCancelListpage;
                order_id = CancelOrdersList.get(position).getRequestId();
                userInfo = CancelOrdersList.get(position).getSellerDetails().getSkills();
                userimage = CancelOrdersList.get(position).getSellerDetails().getProfileImageUrl();
                other_userId = CancelOrdersList.get(position).getSellerDetails().getSellerId();
                userOnlineStatus = CancelOrdersList.get(position).getSellerDetails().getLiveStatus();
                userAddress = CancelOrdersList.get(position).getSellerDetails().getAddress();
                totalCost = CancelOrdersList.get(position).getTotalCost();
                userJoinedDate = CancelOrdersList.get(position).getSellerDetails().getJoinDate();
                gigQuantity = CancelOrdersList.get(position).getQuantity();
                shippingPrice = CancelOrdersList.get(position).getShippingPrice();
                price = CancelOrdersList.get(position).getPrice();
                userRating = CancelOrdersList.get(position).getSellerDetails().getRating();
                userCompletedOrdercount = CancelOrdersList.get(position).getSellerDetails().getOrdersCompleted();
                gigTitle = CancelOrdersList.get(position).getTitle();
                gigDes = CancelOrdersList.get(position).getDescription();
                userLocation = CancelOrdersList.get(position).getSellerDetails().getAddress();
                userDes = CancelOrdersList.get(position).getSellerDetails().getAbout();
                userName = CancelOrdersList.get(position).getSellerDetails().getFirstName() + " " + CancelOrdersList.get(position).getSellerDetails().getLastName();
                category = CancelOrdersList.get(position).getCategoryName() + "/" + CancelOrdersList.get(position).getSubCategoryName();
                requirement = CancelOrdersList.get(position).getRequirement();

                if (CancelOrdersList.get(position).getImageList().size() > 0) {
                    for (int i = 0; i < CancelOrdersList.get(position).getImageList().size(); i++) {
                        imageList.add(CancelOrdersList.get(position).getImageList().get(i).getImageUrl());
                    }
                }
               /* String image1 = "";
                for (int i = 0; i < CancelOrdersList.get(position).getImageList().size(); i++) {
                    if (image1.equals("")) {
                        image1 = CancelOrdersList.get(position).getImageList().get(i).getImageUrl();
                    } else {
                        image1 += "\n" + CancelOrdersList.get(position).getImageList().get(i).getImageUrl();
                    }
                }*/
                shareBody = "\nGigPeople" + "\nTitle: " + CancelOrdersList.get(position).getTitle() + "\nCategory: " + CancelOrdersList.get(position).getCategoryName() +
                        "\nSubcategory: " + CancelOrdersList.get(position).getSubCategoryName() + "\nPrice: " + "$" + CancelOrdersList.get(position).getPrice() +
                        "\nDeliveryTime: " + CancelOrdersList.get(position).getDeliverytime() + " days" /*+ "\nImage: " + image1*/;
                break;
            case "delivered":
                DeliveredOrdersList = GlobalVariables.MyorderDeliveryListpage;
                order_id = DeliveredOrdersList.get(position).getRequestId();
                userInfo = DeliveredOrdersList.get(position).getSellerDetails().getSkills();
                other_userId = DeliveredOrdersList.get(position).getSellerDetails().getSellerId();
                userimage = DeliveredOrdersList.get(position).getSellerDetails().getProfileImageUrl();
                userOnlineStatus = DeliveredOrdersList.get(position).getSellerDetails().getLiveStatus();
                userAddress = DeliveredOrdersList.get(position).getSellerDetails().getAddress();
                totalCost = DeliveredOrdersList.get(position).getTotalCost();
                userJoinedDate = DeliveredOrdersList.get(position).getSellerDetails().getJoinDate();
                gigQuantity = DeliveredOrdersList.get(position).getQuantity();
                shippingPrice = DeliveredOrdersList.get(position).getShippingPrice();
                price = DeliveredOrdersList.get(position).getPrice();
                userRating = DeliveredOrdersList.get(position).getSellerDetails().getRating();
                userCompletedOrdercount = DeliveredOrdersList.get(position).getSellerDetails().getOrdersCompleted();
                gigTitle = DeliveredOrdersList.get(position).getTitle();
                gigDes = DeliveredOrdersList.get(position).getDescription();
                userLocation = DeliveredOrdersList.get(position).getSellerDetails().getAddress();
                userDes = DeliveredOrdersList.get(position).getSellerDetails().getAbout();
                userName = DeliveredOrdersList.get(position).getSellerDetails().getFirstName() + " " + DeliveredOrdersList.get(position).getSellerDetails().getLastName();
                category = DeliveredOrdersList.get(position).getCategoryName() + "/" + DeliveredOrdersList.get(position).getSubCategoryName();
                requirement = DeliveredOrdersList.get(position).getRequirement();

                for (int i = 0; i < DeliveredOrdersList.get(position).getImageList().size(); i++) {
                    imageList.add(DeliveredOrdersList.get(position).getImageList().get(i).getImageUrl());
                }
               /* String image2 = "";
                for (int i = 0; i < DeliveredOrdersList.get(position).getImageList().size(); i++) {
                    if (image2.equals("")) {
                        image2 = DeliveredOrdersList.get(position).getImageList().get(i).getImageUrl();
                    } else {
                        image2 += "\n" + DeliveredOrdersList.get(position).getImageList().get(i).getImageUrl();
                    }
                }*/
                shareBody = "\nGigPeople" + "\nTitle: " + DeliveredOrdersList.get(position).getTitle() + "\nCategory: " + DeliveredOrdersList.get(position).getCategoryName() +
                        "\nSubcategory: " + DeliveredOrdersList.get(position).getSubCategoryName() + "\nPrice: " + "$" + DeliveredOrdersList.get(position).getPrice() +
                        "\nDeliveryTime: " + DeliveredOrdersList.get(position).getDeliverytime() + " days" /*+ "\nImage: " + image2*/;
                break;
            case "amount_spend":

                txtRequirement.setText(amountSpendList.get(position).getRequirement());
                txtCategort.setText(amountSpendList.get(position).getCategoryName() + "/" + amountSpendList.get(position).getSubCategoryName());
                txtOrderId.setText("#" + amountSpendList.get(position).getOrderId());
                txtUsername.setText(amountSpendList.get(position).getSellerDetails().getFirstName() + " " + amountSpendList.get(position).getSellerDetails().getLastName());
                txtAddress.setText(amountSpendList.get(position).getSellerDetails().getAddress());
                txtExpertinfo.setText(amountSpendList.get(position).getSellerDetails().getSkills());
                txtGigTitle.setText(amountSpendList.get(position).getTitle());
                txtJoindate.setText(" " + GlobalMethods.DateTime(amountSpendList.get(position).getSellerDetails().getJoinDate()));
                if (!amountSpendList.get(position).getSellerDetails().getRating().equals("")) {
                    ratingbar.setRating(Float.parseFloat(amountSpendList.get(position).getSellerDetails().getRating()));
                }

                txtOrddercompleted.setText(" " + userCompletedOrdercount);
                txtGigquantity.setText(gigQuantity);
                txtShippingprice.setText("$" + shippingPrice);
                txtPrice.setText("$" + price);
                txtTotalcast.setText("$" + totalCost);/*ActiveOrdersList.get(pagenumber).get*/
                txtGigDescription.setText(gigDes);
                txtUserDescription.setText(userDes);


                amountSpendList = amountSpendingList;
                order_id = amountSpendList.get(position).getOrderId();
                userInfo = amountSpendList.get(position).getSellerDetails().getSkills();
                userimage = amountSpendList.get(position).getSellerDetails().getProfileImageUrl();
                other_userId = amountSpendList.get(position).getSellerDetails().getSellerId();
                userOnlineStatus = amountSpendList.get(position).getSellerDetails().getLiveStatus();
                userAddress = amountSpendList.get(position).getSellerDetails().getAddress();
                totalCost = amountSpendList.get(position).getTotalCost();
                userJoinedDate = amountSpendList.get(position).getSellerDetails().getJoinDate();
                gigQuantity = amountSpendList.get(position).getQuantity();
                shippingPrice = amountSpendList.get(position).getShippingPrice();
                price = amountSpendList.get(position).getPrice();
                userRating = amountSpendList.get(position).getSellerDetails().getRating();
                userCompletedOrdercount = amountSpendList.get(position).getSellerDetails().getOrdersCompleted();
                gigTitle = amountSpendList.get(position).getTitle();
                gigDes = amountSpendList.get(position).getDescription();
                userLocation = amountSpendList.get(position).getSellerDetails().getAddress();
                userDes = amountSpendList.get(position).getSellerDetails().getAbout();
                userName = amountSpendList.get(position).getSellerDetails().getFirstName() + " " + amountSpendList.get(position).getSellerDetails().getLastName();
                category = amountSpendList.get(position).getCategoryName() + "/" + amountSpendList.get(position).getSubCategoryName();
                requirement = amountSpendList.get(position).getRequirement();

                for (int i = 0; i < amountSpendList.get(position).getImageList().size(); i++) {
                    imageList.add(amountSpendList.get(position).getImageList().get(i).getImageUrl());
                }

               /* String image3 = "";
                for (int i = 0; i < amountSpendList.get(position).getImageList().size(); i++) {
                    if (image3.equals("")) {
                        image3 = amountSpendList.get(position).getImageList().get(i).getImageUrl();
                    } else {
                        image3 += "\n" + amountSpendList.get(position).getImageList().get(i).getImageUrl();
                    }
                }*/
                shareBody = "\nGigPeople" + "\nTitle: " + amountSpendList.get(position).getTitle() + "\nCategory: " + amountSpendList.get(position).getCategoryName() +
                        "\nSubcategory: " + amountSpendList.get(position).getSubCategoryName() + "\nPrice: " + "$" + amountSpendList.get(position).getPrice() +
                        "\nDeliveryTime: " + amountSpendList.get(position).getDeliverytime() + " days" /*+ "\nImage: " + image3*/;
                break;
        }

        setProfile();

        if (GlobalMethods.isNetworkAvailable(context)) {
            setDetail();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

    }

    private void setProfile() {

        //profile images
        imageAdapter = new ImageAdapter(ActiveOrdersActivity.this, imageList);
        viewpagergig.setAdapter(imageAdapter);
        pageController.setViewPager(viewpagergig);
        if (!userimage.equals("")) {
            Glide.with(this).load(userimage).into(userImage);
        }

        if (userOnlineStatus.equals("1")) {
            txtOnlinesatus.setText("Online");
            txtOnlinesatus.setTextColor(ContextCompat.getColor(this, R.color.colorGreen));
            txtIcononlineStatus.setBackgroundResource(R.drawable.circlebackground_green);
        } else {
            txtOnlinesatus.setText("Offline");
            txtOnlinesatus.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
            txtIcononlineStatus.setBackgroundResource(R.drawable.circlebackground_red);
        }
        txtRequirement.setText(requirement);
        txtCategort.setText(category);
        txtOrderId.setText("#" + order_id);
        txtUsername.setText(userName);
        txtAddress.setText(userAddress);
        txtExpertinfo.setText(userInfo);
        txtGigTitle.setText(gigTitle);
        txtJoindate.setText(" " + GlobalMethods.DateTime(userJoinedDate));
        if (!userRating.equals("")) {
            ratingbar.setRating(Float.parseFloat(userRating));
        }

        txtOrddercompleted.setText(" " + userCompletedOrdercount);
        txtGigquantity.setText(gigQuantity);
        txtShippingprice.setText("$" + shippingPrice);
        txtPrice.setText("$" + price);
        txtTotalcast.setText("$" + totalCost);/*ActiveOrdersList.get(pagenumber).get*/
        txtGigDescription.setText(gigDes);
        txtUserDescription.setText(userDes);

        setupView();
    }

    private void setDetail() {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("MyOrdersDetailReq", "UserId: " + user_id + " OrderId: " + order_id);
        Call<MyOrdersDetailResponse> call = apiService.callMyOrderDetail(user_id, order_id);
        call.enqueue(new Callback<MyOrdersDetailResponse>() {
            @Override
            public void onResponse(Call<MyOrdersDetailResponse> call, Response<MyOrdersDetailResponse> response) {
                Log.e("MyOrderDetailResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    MyOrdersDetailResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {


                            if (page.equals("fcm")) {
                                imageList = new ArrayList<>();
                                // order_id = amountSpendList.get(position).getOrderId();
                                userInfo = resp.getSellerDetails().getSkills();
                                userimage = resp.getSellerDetails().getProfileImageUrl();
                                other_userId = resp.getSellerDetails().getSellerId();
                                userOnlineStatus = resp.getSellerDetails().getLiveStatus();
                                userAddress = resp.getSellerDetails().getAddress();
                                totalCost = resp.getOrderDetails().getTotalCost();
                                userJoinedDate = resp.getSellerDetails().getJoinDate();
                                gigQuantity = resp.getOrderDetails().getQuantity();
                                shippingPrice = resp.getOrderDetails().getShippingPrice();
                                price = resp.getOrderDetails().getPrice();
                                userRating = resp.getSellerDetails().getRating();
                                userCompletedOrdercount = resp.getSellerDetails().getOrdersCompleted();
                                gigTitle = resp.getOrderDetails().getTitle();
                                gigDes = resp.getOrderDetails().getDescription();
                                userLocation = resp.getSellerDetails().getAddress();
                                userDes = resp.getSellerDetails().getAbout();
                                userName = resp.getSellerDetails().getFirstName() + " " + resp.getSellerDetails().getLastName();
                                category = resp.getOrderDetails().getCategoryName() + "/" + resp.getOrderDetails().getSubCategoryName();
                                requirement = resp.getOrderDetails().getRequirement();

                                for (int i = 0; i < resp.getOrderDetails().getImageList().size(); i++) {
                                    imageList.add(resp.getOrderDetails().getImageList().get(i).getImageUrl());
                                }

                                shareBody = "\nGigPeople" + "\nTitle: " + resp.getOrderDetails().getTitle() + "\nCategory: " + resp.getOrderDetails().getCategoryName() +
                                        "\nSubcategory: " + resp.getOrderDetails().getSubCategoryName() + "\nPrice: " + "$" + resp.getOrderDetails().getPrice() +
                                        "\nDeliveryTime: " + resp.getOrderDetails().getDeliverytime() + " days";

                                setProfile();

                            }

                            other_userId = resp.getSellerDetails().getSellerId();
                            gig_id = resp.getOrderDetails().getGig_id();


                            //FOR MESSAGE
                            sale_list = new ArrayList<>();
                            sale_list = resp.getMessageList();
                            adapter1 = new SaleAdapter(ActiveOrdersActivity.this, sale_list);
                            recyclerWorkhistory.setAdapter(adapter1);
                            recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            reviewLayout.setVisibility(View.GONE);
                            workhistory.setGravity(Gravity.CENTER);

                            if (resp.getAdminReport().equals("0")) {
                                btnMenu.setVisibility(View.VISIBLE);
                            } else {
                                btnMenu.setVisibility(View.GONE);
                            }

                            //ASK TIME EXTENSION
                            if (resp.getTimeExtensions().getTimeStatus().equals("0")) {
                                linearAcceptRejectTimeExtension.setVisibility(View.GONE);

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("1")) {
                                linearAcceptRejectTimeExtension.setVisibility(View.VISIBLE);
                                linearAcceptReject.setVisibility(View.VISIBLE);
                                txtTimeExtentionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("");

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("2")) {
                                linearAcceptRejectTimeExtension.setVisibility(View.VISIBLE);
                                linearAcceptReject.setVisibility(View.GONE);
                                txtTimeExtentionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("Accepted");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorGreen));

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("3")) {
                                linearAcceptRejectTimeExtension.setVisibility(View.VISIBLE);
                                linearAcceptReject.setVisibility(View.GONE);
                                txtTimeExtentionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("Rejected");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorRed));

                            }

                            if (resp.getOrderStatus().equals("3")) {// Current
                                review.setVisibility(View.GONE);
                                userreview.setVisibility(View.GONE);
                                message.setVisibility(View.VISIBLE);
                                revision.setVisibility(View.GONE);
                                cancel.setVisibility(View.VISIBLE);
                                dispute.setVisibility(View.GONE);

                                linearOrderDispute.setVisibility(View.GONE);
                                linearCancellOrder.setVisibility(View.GONE);

                                if (resp.getCancelledOrder().getCancelStatus().equals("")) {

                                    linearCancellOrder.setVisibility(View.GONE);
                                    linearAcceptRejectCancel.setVisibility(View.GONE);
                                } else if (resp.getCancelledOrder().getCancelStatus().equals("0")) {

                                    txtCancelOrderDescription.setText(resp.getCancelledOrder().getCancelReason());
                                    if (user_id.equals(resp.getCancelledOrder().getCancelBy())) {
                                        txtCancelStatus.setText("Order Cancel Requested");
                                        linearAcceptRejectCancel.setVisibility(View.GONE);
                                    } else {
                                        txtCancelStatus.setText("Order Cancel request Received");
                                        linearAcceptRejectCancel.setVisibility(View.VISIBLE);
                                    }
                                    linearCancellOrder.setVisibility(View.VISIBLE);
                                    txtCancelStatus.setTextColor(context.getResources().getColor(R.color.colorAccent));
                                    cancel.setVisibility(View.GONE);
                                } else if (resp.getCancelledOrder().getCancelStatus().equals("1")) {

                                    linearCancellOrder.setVisibility(View.VISIBLE);
                                    linearAcceptRejectCancel.setVisibility(View.VISIBLE);
                                    txtCancelOrderDescription.setText(resp.getCancelledOrder().getCancelReason());
                                    txtCancelStatus.setText("Order Cancel Accepted");
                                    txtCancelStatus.setTextColor(context.getResources().getColor(R.color.colorGreen));
                                } else if (resp.getCancelledOrder().getCancelStatus().equals("2")) {

                                    linearCancellOrder.setVisibility(View.VISIBLE);
                                    linearAcceptRejectCancel.setVisibility(View.GONE);
                                    txtCancelOrderDescription.setText(resp.getCancelledOrder().getCancelReason());
                                    txtCancelStatus.setText("Order Cancel request Rejected");
                                    txtCancelStatus.setTextColor(context.getResources().getColor(R.color.colorRed));
                                    cancel.setVisibility(View.GONE);
                                } else {
                                    linearCancellOrder.setVisibility(View.GONE);
                                    linearAcceptRejectCancel.setVisibility(View.GONE);
                                }

                            } else if (resp.getOrderStatus().equals("4")) {//Completed

                                linearAcceptRejectCancel.setVisibility(View.GONE);
                                relativeOrderStarted.setVisibility(View.GONE);
                                relativeYourDelivery.setVisibility(View.GONE);
                                review.setVisibility(View.VISIBLE);
                                userreview.setVisibility(View.VISIBLE);
                                message.setVisibility(View.GONE);
                                revision.setVisibility(View.VISIBLE);
                                cancel.setVisibility(View.GONE);
                                dispute.setVisibility(View.VISIBLE);
                                linearCancellOrder.setVisibility(View.GONE);
                                linearAcceptRejectTimeExtension.setVisibility(View.GONE);

                                /*delivered_list=new ArrayList<>();
                                delivered_list=resp.getOrderDelivery();
                                deliveredOrderedAdapter=new DeliveredOrderedAdapter(context,delivered_list);
                                recyclerDelivery.setAdapter(deliveredOrderedAdapter);
                                recyclerDelivery.setLayoutManager(new LinearLayoutManager(context));*/


                                if (resp.getDisputeOrder().size() > 0) {
                                    linearOrderDispute.setVisibility(View.VISIBLE);
                                    dispute.setVisibility(View.GONE);
                                    //FOR DISPUTE
                                    if (resp.getDisputeOrder().size() > 0) {
                                        txtOrderDisputeDescription.setText(resp.getDisputeOrder().get(0).getMessage());
                                    }

                                } else {
                                    linearOrderDispute.setVisibility(View.GONE);
                                    dispute.setVisibility(View.VISIBLE);
                                }
                                //for rating
                                if (resp.getOtherRating().equals("0") || resp.getOtherRating().equals("")) {
                                    review.setVisibility(View.VISIBLE);

                                } else {
                                    review.setVisibility(View.GONE);

                                }

                                if (resp.getOrderRating().equals("0") || resp.getOrderRating().equals("")) {
                                    userreview.setVisibility(View.VISIBLE);
                                } else {
                                    userreview.setVisibility(View.GONE);
                                }


                                if (resp.getUserReviews().getReview().equals("0") || resp.getUserReviews().getReview().equals("")) {
                                    reviewLayout.setVisibility(View.GONE);
                                } else {
                                    reviewLayout.setVisibility(View.VISIBLE);
                                }

                                //FOR REVISION
                                if (resp.getGigRevision().equals("0")) {
                                    revision.setVisibility(View.VISIBLE);
                                } else if (resp.getGigRevision().equals(resp.getRevisionCount())) {
                                    revision.setVisibility(View.GONE);
                                }

                            } else if (resp.getOrderStatus().equals("7")) {//CANCELLED
                                linearOrderDispute.setVisibility(View.GONE);
                                linearCancellOrder.setVisibility(View.VISIBLE);
                                linearBottom.setVisibility(View.GONE);
                                relativeOrderStarted.setVisibility(View.GONE);
                                relativeYourDelivery.setVisibility(View.GONE);
                                //FOR CANCEL
                                txtCancelOrderDescription.setText(resp.getCancelledOrder().getCancelReason());
                                linearAcceptRejectCancel.setVisibility(View.GONE);
                            }

                            //FOR REVISION
                            revisionOrdersList = new ArrayList<>();
                            revisionOrdersList = resp.getRevisionOrder();
                            revisionAdapter = new RevisionAdapter(ActiveOrdersActivity.this, revisionOrdersList);
                            recyclerRevision.setAdapter(revisionAdapter);
                            recyclerRevision.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            //FOR REVIEW
                            salesReviewAdapter = new SalesReviewAdapter(ActiveOrdersActivity.this, resp.getUserReviews());
                            recyclerReview.setAdapter(salesReviewAdapter);
                            recyclerReview.setLayoutManager(new LinearLayoutManager(ActiveOrdersActivity.this));
                            //FOR DATES
                            txtOrderStartDate.setText(resp.getOrderStarted() + " Days ago");
                            txtOrderDeliveryDate.setText(resp.getOrderDeliver() + " Days left");
                            //for delivery
                            delivered_list = new ArrayList<>();
                            delivered_list = resp.getOrderDelivery();
                            if (delivered_list.size() > 0) {
                                deliveredOrderedAdapter = new DeliveredOrderedAdapter(context, delivered_list);
                                recyclerDelivery.setAdapter(deliveredOrderedAdapter);
                                recyclerDelivery.setLayoutManager(new LinearLayoutManager(context));
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyOrdersDetailResponse> call, Throwable t) {
                Log.e("MyOrderDetailResp", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setCancellAcceptReject(final String status) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("CancelAcceptRejectReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + status);
        Call<CommonResponse> call = apiService.callBuyerCancelledStatus(user_id, order_id, status);// type 1 for buyer
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("CancelAcceptRejectResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();

                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setAcceptReject(final String status) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("AcceptRejectReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + status);
        Call<CommonResponse> call = apiService.callAcceptTimeExtention(user_id, order_id, status);// type 1 for buyer
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("AcceptRejectResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();
                            if (status.equals("2")) {
                                txtTimeExtentionStatus.setText("Accepted");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorGreen));

                            } else if (status.equals("3")) {
                                txtTimeExtentionStatus.setText("Rejected");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorRed));

                            }
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setBottomButtonAPI(final String type, String message) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("MyOrdersBottomReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nSellerId: " + other_userId + "\nType: " + type + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callBottomButtonMyOrder(user_id, order_id, other_userId, type, message);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("MyOrderButtomResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            if (type.equals("1")) {

                                messagesialog.dismiss();
                                if (GlobalMethods.isNetworkAvailable(context)) {
                                    setDetail();
                                } else {
                                    GlobalMethods.Toast(context, getString(R.string.internet));
                                }
                            } else if (type.equals("2")) {

                                reviewdialog.dismiss();
                                if (GlobalMethods.isNetworkAvailable(context)) {
                                    setDetail();
                                } else {
                                    GlobalMethods.Toast(context, getString(R.string.internet));
                                }
                            } else if (type.equals("3")) {
                                disputedialog.dismiss();
                                finish();
                            } else if (type.equals("4")) {
                                messagedialog.dismiss();
                            }
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setCancellOrder(final String type, String message) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("OrderCancelReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + type + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callOrderCancell(user_id, order_id, type, message);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("OrderCancelResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();

                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setUserRating(final String rating1, String message) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("UserRatingReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + "1" + "\nRating: " + rating1 + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callUserReview(user_id, order_id, "1", rating1, message,gig_id);// type 1 for buyer
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                rating.dismiss();
                Log.e("UserRatingResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setGigRating(final String rating1, String message) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("GigRatingReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + "1" + "\nRating: " + rating1 + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callGigReview(user_id, order_id, rating1, message,gig_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                rating.dismiss();
                Log.e("GigRatingResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            rating.dismiss();
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void setCancellDialog() {

        canceldialog = new Dialog(context);
        canceldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        canceldialog.setContentView(R.layout.dialog_order_cancel);
        canceldialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        canceldialog.show();
        final EditText edt_message = (EditText) canceldialog.findViewById(R.id.edt_message);
        ImageView close = (ImageView) canceldialog.findViewById(R.id.img_close);
        Button submit = (Button) canceldialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceldialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceldialog.dismiss();

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edt_message.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edt_message.getText().toString();
                        setCancellOrder("1", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });

    }

    private void setDisputeDialog() {
        disputedialog = new Dialog(context);
        disputedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        disputedialog.setContentView(R.layout.dialog_dispute);
        disputedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        disputedialog.show();
        ImageView close = (ImageView) disputedialog.findViewById(R.id.img_close);
        final EditText edit_comment = (EditText) disputedialog.findViewById(R.id.edit_comment);
        Button submit = (Button) disputedialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disputedialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edit_comment.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edit_comment.getText().toString();
                        setBottomButtonAPI("3", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });
    }

    private void setMessageDialog() {

        messagesialog = new Dialog(context);
        messagesialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        messagesialog.setContentView(R.layout.dialog_sales_message);
        messagesialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        messagesialog.show();
        ImageView close = (ImageView) messagesialog.findViewById(R.id.img_close);
        final EditText edit_message = (EditText) messagesialog.findViewById(R.id.edit_message);
        Button submit = (Button) messagesialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagesialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edit_message.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edit_message.getText().toString();
                        setBottomButtonAPI("1", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });


    }

    private void openrevisiondialog() {

        reviewdialog = new Dialog(ActiveOrdersActivity.this);
        reviewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewdialog.setContentView(R.layout.dialog_sales_revision);
        reviewdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reviewdialog.show();
        ImageView close = (ImageView) reviewdialog.findViewById(R.id.img_close);
        final EditText edit_message = (EditText) reviewdialog.findViewById(R.id.edit_message);
        Button submit = (Button) reviewdialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewdialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent1 = new Intent(ActiveOrdersActivity.this, MainActivity.class);
                intent1.putExtra("page", "5");
                startActivity(intent1);*/

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edit_message.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edit_message.getText().toString();
                        setBottomButtonAPI("2", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });
    }

    private void openmessagedialog() {
        messagedialog = new Dialog(ActiveOrdersActivity.this);
        messagedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        messagedialog.setContentView(R.layout.dialog_sales_adminmessage);
        messagedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        messagedialog.show();
        ImageView close = (ImageView) messagedialog.findViewById(R.id.img_close);
        final EditText edit_message = (EditText) messagedialog.findViewById(R.id.edit_message);
        Button submit = (Button) messagedialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagedialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edit_message.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edit_message.getText().toString();
                        setBottomButtonAPI("4", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void openratingdialog(String title, final String type) {
        rating_value = "";
        rating = new Dialog(ActiveOrdersActivity.this);
        rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rating.setContentView(R.layout.dialog_rating);
        rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rating.show();
        ImageView close = (ImageView) rating.findViewById(R.id.img_close);
        TextView txt_title = (TextView) rating.findViewById(R.id.txt_title);
        final EditText edit_message = (EditText) rating.findViewById(R.id.edit_message);
        txt_title.setText(title);
        Button submit = (Button) rating.findViewById(R.id.btn_submit);
        fivestar = (ImageView) rating.findViewById(R.id.fivestar);
        img_4star = (ImageView) rating.findViewById(R.id.img_4star);
        img_3star = (ImageView) rating.findViewById(R.id.img_3star);
        img_2star = (ImageView) rating.findViewById(R.id.img_2star);
        img_1star = (ImageView) rating.findViewById(R.id.img_onestar);
        lin_5star = (LinearLayout) rating.findViewById(R.id.lin_5star);
        lin_4star = (LinearLayout) rating.findViewById(R.id.lin_4star);
        lin_3star = (LinearLayout) rating.findViewById(R.id.lin_3star);
        lin_2star = (LinearLayout) rating.findViewById(R.id.lin_2star);
        lin_1star = (LinearLayout) rating.findViewById(R.id.lin_1star);

        lin_5star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.five_star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);
                rating_value = "5";

            }
        });
        lin_4star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.four_star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);
                rating_value = "4";

            }
        });
        lin_3star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.three_star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);
                rating_value = "3";

            }
        });
        lin_2star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.two_star);
                img_1star.setImageResource(R.drawable.gray_1star);
                rating_value = "2";

            }
        });
        lin_1star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.one_star);
                rating_value = "1";

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (edit_message.getText().toString().equals("")) {
                        GlobalMethods.Toast(context, "Enter message");
                    } else {
                        String message = "";
                        message = edit_message.getText().toString();
                        if (type.equals("UF")) {
                            setUserRating(rating_value, message);
                        } else {
                            setGigRating(rating_value, message);
                        }
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });

    }

    private void opensharedialog() {

        share = new Dialog(ActiveOrdersActivity.this);
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

                Intent share = new Intent(ActiveOrdersActivity.this, ShareActivity.class);
                startActivity(share);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();

                Intent intent = new Intent(ActiveOrdersActivity.this, ChatDetailsActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setupView() {
        viewpagergig.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }
                String count = String.valueOf(currentPage + 1);

                try {
                    viewpagergig.setCurrentItem(currentPage++, true);
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
    }

}
