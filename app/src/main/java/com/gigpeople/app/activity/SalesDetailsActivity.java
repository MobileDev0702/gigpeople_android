package com.gigpeople.app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.DeliveredSalesAdapter;
import com.gigpeople.app.adapter.DeliveryTimeAdapter;
import com.gigpeople.app.adapter.RevisionAdapterSales;
import com.gigpeople.app.adapter.SalefourAdapter;
import com.gigpeople.app.adapter.SalesReviewAdapterSales;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.DeliveryModel;
import com.gigpeople.app.apiModel.SellerSalesDetailResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
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

import static com.gigpeople.app.activity.PaymentHistoryActivity.amountEarnedList;
import static com.gigpeople.app.fragment.MysaleFragment.cancelledOrderLists;
import static com.gigpeople.app.fragment.MysaleFragment.completedOrderLists;
import static com.gigpeople.app.fragment.MysaleFragment.currentOrderLists;

public class SalesDetailsActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    SalesReviewAdapterSales salesReviewAdapter;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;
    Dialog share;
    @BindView(R.id.message)
    ImageView message;
    @BindView(R.id.cancel)
    ImageView cancel;
    Dialog rating, reviewdialog;
    ImageView fivestar, img_4star, img_3star, img_2star, img_1star;
    LinearLayout lin_5star, lin_4star, lin_3star, lin_2star, lin_1star;
    Dialog messagedialog;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.img_fav)
    ImageView imgFav;
    int fav_status = 0;
    @BindView(R.id.btn_delivery)
    Button btnDelivery;
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
    SalefourAdapter adapter1;
    List<SellerSalesDetailResponse.MessageList> sale_list;
    int position;
    @BindView(R.id.profile_img)
    CircleImageView profileImg;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_order)
    TextView txtOrder;
    @BindView(R.id.txt_order_date)
    TextView txtOrderDate;
    @BindView(R.id.txt_order_value)
    TextView txtOrderValue;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_qty)
    TextView txtQty;
    @BindView(R.id.txt_duration)
    TextView txtDuration;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    @BindView(R.id.txt_total_fee)
    TextView txtTotalFee;
    Context context;
    @BindView(R.id.txt_category)
    TextView txtCategory;
    String TIME, from, user_id, order_id;
    ApiService apiService;
    ProgressDialog progressDialog;
    @BindView(R.id.txt_order_start_date)
    TextView txtOrderStartDate;
    @BindView(R.id.txt_order_delivery_date)
    TextView txtOrderDeliveryDate;
    @BindView(R.id.recycler_revision)
    RecyclerView recyclerRevision;
    @BindView(R.id.txt_order_dispute_description)
    TextView txtOrderDisputeDescription;
    @BindView(R.id.txt_cancel_order_description)
    TextView txtCancelOrderDescription;
    @BindView(R.id.linear_cancell_order)
    LinearLayout linearCancellOrder;
    @BindView(R.id.dispute)
    ImageView dispute;
    @BindView(R.id.linear_order_dispute)
    LinearLayout linearOrderDispute;
    List<SellerSalesDetailResponse.RevisionOrder> revisionOrdersList;
    RevisionAdapterSales revisionAdapter;
    String buyer_id, rating_value = "";
    Dialog messagesialog, disputedialog, canceldialog;
    @BindView(R.id.userreview)
    ImageView userreview;
    @BindView(R.id.relativeOne)
    RelativeLayout relativeOne;
    @BindView(R.id.linear_bottom)
    LinearLayout linearBottom;
    Dialog deliveryTimeDialog, timeExtentionDialog;
    List<DeliveryModel> delivertTimeModelList;
    DeliveryTimeAdapter deliveryTimeAdapter;
    DeliveryTimeAdapter.ItemClickListener deliveryItemClickListener;
    @BindView(R.id.btn_time_extention)
    Button btnTimeExtention;
    EditText edt_date;
    String delivery_time = "";
    @BindView(R.id.txt_time_extension_description)
    TextView txtTimeExtensionDescription;
    @BindView(R.id.txt_time_extention_status)
    TextView txtTimeExtentionStatus;
    @BindView(R.id.linear_time_Extention)
    LinearLayout linearTimeExtention;
    @BindView(R.id.txt_time_extention)
    TextView txtTimeExtention;
    @BindView(R.id.txt_shipping_cost)
    TextView txtShippingCost;
    @BindView(R.id.relative_order_started)
    RelativeLayout relativeOrderStarted;
    @BindView(R.id.relative_your_delivery)
    RelativeLayout relativeYourDelivery;
    RevisionAdapterSales.CallBack callBack;
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
    DeliveredSalesAdapter deliveredSalesAdapter;
    List<SellerSalesDetailResponse.OrderDelivery> deliveryList;
    @BindView(R.id.recycler_deliver)
    RecyclerView recyclerDeliver;

    String gig_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_details);
        ButterKnife.bind(this);
        context = SalesDetailsActivity.this;
        Window window = SalesDetailsActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SalesDetailsActivity.this, R.color.colorPrimaryDark));
        }
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);

        callBack = new RevisionAdapterSales.CallBack() {
            @Override
            public void call(String status, String order_id, String msg_id) {
                setAcceptReject(status, order_id, msg_id);
            }
        };

        init();

        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            from = getIntent().getStringExtra("from");
            if (from.equals("current")) {
                setCurrentDetails();
            } else if (from.equals("amount_earned")) {
                setAmountEarnedDetails();
            } else if (from.equals("completed")) {
                setCompleteDetails();
            } else if (from.equals("cancelled")) {
                setCancelled();
            }else if (from.equals("fcm")) {
                order_id=getIntent().getStringExtra("order_id");
            }
        }

        if (GlobalMethods.isNetworkAvailable(context)) {
            setDetail();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }
    }

    private void setCurrentDetails() {

        if (!currentOrderLists.get(position).getBuyerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(currentOrderLists.get(position).getBuyerDetails().getProfileImageUrl()).into(profileImg);
        }
        buyer_id = currentOrderLists.get(position).getBuyerDetails().getBuyerId();
        order_id = currentOrderLists.get(position).getOrderId();
        txtName.setText(currentOrderLists.get(position).getBuyerDetails().getFirstName() + " " + currentOrderLists.get(position).getBuyerDetails().getLastName());
        txtDescription.setText(currentOrderLists.get(position).getDescription());
        txtQty.setText(currentOrderLists.get(position).getQuantity());
        txtDuration.setText(currentOrderLists.get(position).getDeliverytime() + " days");
        txtAmount.setText("$" + currentOrderLists.get(position).getPrice());
        txtTotalFee.setText("$" + currentOrderLists.get(position).getTotalCost());
        txtOrderValue.setText("$" + currentOrderLists.get(position).getTotalCost());
        txtShippingCost.setText("$" + currentOrderLists.get(position).getShippingPrice());
        txtOrder.setText("#" + currentOrderLists.get(position).getOrderId());
        txtRequirement.setText(currentOrderLists.get(position).getRequirement());
        txtOrderDate.setText(GlobalMethods.DateTime1(currentOrderLists.get(position).getOrderDate()));
        txtCategory.setText(currentOrderLists.get(position).getCategoryName() + "/ " + currentOrderLists.get(position).getSubCategoryName());
        String TIME = setTimeString(currentOrderLists.get(position).getDeliverytime());

        /*if (TIME.equals("0 minutes ago")) {
            txtDuration.setText("Just Now");
        } else {
            txtDuration.setText(TIME);
        }*/
    }

    private void setAmountEarnedDetails() {

        if (!amountEarnedList.get(position).getBuyerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(amountEarnedList.get(position).getBuyerDetails().getProfileImageUrl()).into(profileImg);
        }
        order_id = amountEarnedList.get(position).getOrderId();
        buyer_id = amountEarnedList.get(position).getBuyerDetails().getBuyerId();
        txtName.setText(amountEarnedList.get(position).getBuyerDetails().getFirstName() + " " + amountEarnedList.get(position).getBuyerDetails().getLastName());
        txtDescription.setText(amountEarnedList.get(position).getDescription());
        txtQty.setText(amountEarnedList.get(position).getQuantity());
        txtDuration.setText(amountEarnedList.get(position).getDeliverytime() + " days");
        txtAmount.setText("$" + amountEarnedList.get(position).getPrice());
        txtTotalFee.setText("$" + amountEarnedList.get(position).getTotalCost());
        txtOrderValue.setText("$" + amountEarnedList.get(position).getTotalCost());
        txtShippingCost.setText("$" + amountEarnedList.get(position).getShippingPrice());
        txtOrder.setText("#" + amountEarnedList.get(position).getOrderId());
        txtRequirement.setText(amountEarnedList.get(position).getRequirement());
        txtOrderDate.setText(GlobalMethods.DateTime1(amountEarnedList.get(position).getOrderDate()));
        txtCategory.setText(amountEarnedList.get(position).getCategoryName() + "/ " + amountEarnedList.get(position).getSubCategoryName());
        String TIME = setTimeString(amountEarnedList.get(position).getDeliverytime());

       /* if (TIME.equals("0 minutes ago")) {
            txtDuration.setText("Just Now");
        } else {
            txtDuration.setText(TIME);
        }*/
    }

    private void setCompleteDetails() {

        if (!completedOrderLists.get(position).getBuyerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(completedOrderLists.get(position).getBuyerDetails().getProfileImageUrl()).into(profileImg);
        }
        order_id = completedOrderLists.get(position).getOrderId();
        buyer_id = completedOrderLists.get(position).getBuyerDetails().getBuyerId();
        txtName.setText(completedOrderLists.get(position).getBuyerDetails().getFirstName() + " " + completedOrderLists.get(position).getBuyerDetails().getLastName());
        txtDescription.setText(completedOrderLists.get(position).getDescription());
        txtQty.setText(completedOrderLists.get(position).getQuantity());
        txtDuration.setText(completedOrderLists.get(position).getDeliverytime() + " days");
        txtAmount.setText("$" + completedOrderLists.get(position).getPrice());
        txtTotalFee.setText("$" + completedOrderLists.get(position).getTotalCost());
        txtOrderValue.setText("$" + completedOrderLists.get(position).getTotalCost());
        txtShippingCost.setText("$" + completedOrderLists.get(position).getShippingPrice());
        txtOrder.setText("#" + completedOrderLists.get(position).getOrderId());
        txtRequirement.setText(completedOrderLists.get(position).getRequirement());
        txtOrderDate.setText(GlobalMethods.DateTime1(completedOrderLists.get(position).getOrderDate()));
        txtCategory.setText(completedOrderLists.get(position).getCategoryName() + "/ " + completedOrderLists.get(position).getSubCategoryName());
        String TIME = setTimeString(completedOrderLists.get(position).getDeliverytime());

       /* if (TIME.equals("0 minutes ago")) {
            txtDuration.setText("Just Now");
        } else {
            txtDuration.setText(TIME);
        }*/
    }

    private void setCancelled() {

        if (!cancelledOrderLists.get(position).getBuyerDetails().getProfileImageUrl().equals("")) {
            Glide.with(context).load(cancelledOrderLists.get(position).getBuyerDetails().getProfileImageUrl()).into(profileImg);
        }
        order_id = cancelledOrderLists.get(position).getOrderId();
        buyer_id = cancelledOrderLists.get(position).getBuyerDetails().getBuyerId();
        txtName.setText(cancelledOrderLists.get(position).getBuyerDetails().getFirstName() + " " + cancelledOrderLists.get(position).getBuyerDetails().getLastName());
        txtDescription.setText(cancelledOrderLists.get(position).getDescription());
        txtQty.setText(cancelledOrderLists.get(position).getQuantity());
        txtDuration.setText(cancelledOrderLists.get(position).getDeliverytime() + " days");
        txtAmount.setText("$" + cancelledOrderLists.get(position).getPrice());
        txtTotalFee.setText("$" + cancelledOrderLists.get(position).getTotalCost());
        txtOrderValue.setText("$" + cancelledOrderLists.get(position).getTotalCost());
        txtShippingCost.setText("$" + cancelledOrderLists.get(position).getShippingPrice());
        txtOrder.setText("#" + cancelledOrderLists.get(position).getOrderId());
        txtRequirement.setText(cancelledOrderLists.get(position).getRequirement());
        txtOrderDate.setText(GlobalMethods.DateTime1(cancelledOrderLists.get(position).getOrderDate()));
        txtCategory.setText(cancelledOrderLists.get(position).getCategoryName() + "/ " + cancelledOrderLists.get(position).getSubCategoryName());
        String TIME = setTimeString(cancelledOrderLists.get(position).getDeliverytime());

        /*if (TIME.equals("0 minutes ago")) {
            txtDuration.setText("Just Now");
        } else {
            txtDuration.setText(TIME);
        }*/
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdf.parse(givenDateString);
            long_time = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        Log.e("TIME", TIME + " nan ");
        return TIME;
    }

    @OnClick({R.id.btn_accept_cancel, R.id.btn_decline_cancel, R.id.dispute, R.id.userreview, R.id.btn_back_arrow, R.id.btn_menu, R.id.message, R.id.cancel, R.id.img_share, R.id.img_fav, R.id.btn_delivery, R.id.workhistory_layout, R.id.review_layout, R.id.btn_time_extention})
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
            case R.id.dispute:
                setDisputeDialog();
                break;
            case R.id.userreview:
                openratingdialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                openmessagedialog();
                break;
            case R.id.message:
                setMessageDialog();
                break;
            case R.id.cancel:
                setCancellDialog();
                break;
            case R.id.img_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "GigPeople";
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
            case R.id.btn_delivery:
                Intent intent4 = new Intent(SalesDetailsActivity.this, SalesDeliveryActivity.class);
                intent4.putExtra("order_id", order_id);
                intent4.putExtra("buyer_id", buyer_id);
                startActivity(intent4);
                break;
            case R.id.workhistory_layout:
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                view1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.VISIBLE);
                recyclerReviews.setVisibility(View.GONE);
                break;
            case R.id.review_layout:
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                view2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.GONE);
                recyclerReviews.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_time_extention:
                openTimeExtensionDialog();
                break;
        }
    }

    private void setDetail() {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("MySalesDetailReq", "UserId: " + user_id + " OrderId: " + order_id);
        Call<SellerSalesDetailResponse> call = apiService.callMySalesDetail(user_id, order_id);
        call.enqueue(new Callback<SellerSalesDetailResponse>() {
            @Override
            public void onResponse(Call<SellerSalesDetailResponse> call, Response<SellerSalesDetailResponse> response) {
                Log.e("MySalesDetailResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    SellerSalesDetailResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            if (from.equals("fcm")){
                                if (!resp.getBuyerDetails().getProfileImageUrl().equals("")) {
                                    Glide.with(context).load(resp.getBuyerDetails().getProfileImageUrl()).into(profileImg);
                                }
                                buyer_id = resp.getBuyerDetails().getBuyerId();
                               // order_id = resp.getOrderId();
                                txtName.setText(resp.getBuyerDetails().getFirstName() + " " + resp.getBuyerDetails().getLastName());
                                txtDescription.setText(resp.getOrderDetails().getDescription());
                                txtQty.setText(resp.getOrderDetails().getQuantity());
                                txtDuration.setText(resp.getOrderDetails().getDeliverytime() + " days");
                                txtAmount.setText("$" + resp.getOrderDetails().getPrice());
                                txtTotalFee.setText("$" +resp.getOrderDetails().getTotalCost());
                                txtOrderValue.setText("$" + resp.getOrderDetails().getTotalCost());
                                txtShippingCost.setText("$" +resp.getOrderDetails().getShippingPrice());
                                txtOrder.setText("#" +resp.getOrderDetails().getOrderId());
                                txtRequirement.setText(resp.getOrderDetails().getRequirement());
                                txtOrderDate.setText(GlobalMethods.DateTime1(resp.getOrderDetails().getOrderDate()));
                                txtCategory.setText(resp.getOrderDetails().getCategoryName() + "/ " +resp.getOrderDetails().getSubCategoryName());
                                String TIME = setTimeString(resp.getOrderDetails().getDeliverytime());
                            }

                            gig_id = resp.getOrderDetails().getGig_id();

                            //FOR MESSAGE
                            sale_list = new ArrayList<>();
                            sale_list = resp.getMessageList();

                            adapter1 = new SalefourAdapter(SalesDetailsActivity.this, sale_list);
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
                                btnTimeExtention.setVisibility(View.VISIBLE);
                                linearTimeExtention.setVisibility(View.GONE);

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("1")) {
                                btnTimeExtention.setVisibility(View.GONE);
                                linearTimeExtention.setVisibility(View.VISIBLE);
                                txtTimeExtensionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("Request Sent");

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("2")) {
                                btnTimeExtention.setVisibility(View.GONE);
                                linearTimeExtention.setVisibility(View.VISIBLE);
                                txtTimeExtensionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("Accepted");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorGreen));

                            } else if (resp.getTimeExtensions().getTimeStatus().equals("3")) {
                                btnTimeExtention.setVisibility(View.GONE);
                                linearTimeExtention.setVisibility(View.VISIBLE);
                                txtTimeExtensionDescription.setText(resp.getTimeExtensions().getTimeDescription());
                                txtTimeExtention.setText(resp.getTimeExtensions().getTimeExtension() + " days");
                                txtTimeExtentionStatus.setText("Rejected");
                                txtTimeExtentionStatus.setTextColor(getResources().getColor(R.color.colorRed));

                            }

                            if (resp.getOrderStatus().equals("3")) {// Current
                                userreview.setVisibility(View.GONE);
                                message.setVisibility(View.VISIBLE);
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
                                    txtCancelStatus.setText("Order Cancel Accepteded");
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
                                btnDelivery.setVisibility(View.GONE);
                                btnTimeExtention.setVisibility(View.GONE);
                                userreview.setVisibility(View.VISIBLE);
                                message.setVisibility(View.GONE);
                                cancel.setVisibility(View.GONE);
                                dispute.setVisibility(View.VISIBLE);
                                linearCancellOrder.setVisibility(View.GONE);
                                linearCancellOrder.setVisibility(View.GONE);
                                relativeOrderStarted.setVisibility(View.GONE);
                                relativeYourDelivery.setVisibility(View.GONE);
                                linearTimeExtention.setVisibility(View.GONE);

                                /*deliveryList = new ArrayList<>();
                                deliveryList = resp.getOrderDelivery();
                                deliveredSalesAdapter = new DeliveredSalesAdapter(context, deliveryList);
                                recyclerDeliver.setLayoutManager(new LinearLayoutManager(context));
                                recyclerDeliver.setAdapter(deliveredSalesAdapter);*/

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

                                if (resp.getOtherRating().equals("0") || resp.getOtherRating().equals("")) {
                                    userreview.setVisibility(View.VISIBLE);
                                } else {
                                    userreview.setVisibility(View.GONE);
                                }

                                if (resp.getUserReviews().getRating().equals("0") || resp.getUserReviews().getRating().equals("")) {
                                    reviewLayout.setVisibility(View.GONE);
                                } else {
                                    reviewLayout.setVisibility(View.VISIBLE);
                                }


                            } else if (resp.getOrderStatus().equals("7")) {//CANCELLED
                                linearOrderDispute.setVisibility(View.GONE);
                                linearCancellOrder.setVisibility(View.VISIBLE);
                                linearBottom.setVisibility(View.GONE);
                                btnDelivery.setVisibility(View.GONE);
                                btnTimeExtention.setVisibility(View.GONE);
                                relativeOrderStarted.setVisibility(View.GONE);
                                relativeYourDelivery.setVisibility(View.GONE);
                                //FOR CANCEL
                                txtCancelOrderDescription.setText(resp.getCancelledOrder().getCancelReason());
                                linearAcceptRejectCancel.setVisibility(View.GONE);
                            }

                            //FOR REVISION
                            revisionOrdersList = new ArrayList<>();
                            revisionOrdersList = resp.getRevisionOrder();
                            revisionAdapter = new RevisionAdapterSales(context, revisionOrdersList, callBack);
                            recyclerRevision.setAdapter(revisionAdapter);
                            recyclerRevision.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            //FOR REVIEW
                            salesReviewAdapter = new SalesReviewAdapterSales(SalesDetailsActivity.this, resp.getUserReviews());
                            recyclerReviews.setAdapter(salesReviewAdapter);
                            recyclerReviews.setLayoutManager(new LinearLayoutManager(SalesDetailsActivity.this));
                            //FOR DATES
                            txtOrderStartDate.setText(resp.getOrderStarted() + " Days ago");
                            txtOrderDeliveryDate.setText(resp.getOrderDeliver() + " Days left");
                            //FOR DELIVERY
                            deliveryList = new ArrayList<>();
                            deliveryList = resp.getOrderDelivery();
                            if (deliveryList.size()>0) {
                                deliveredSalesAdapter = new DeliveredSalesAdapter(context, deliveryList);
                                recyclerDeliver.setLayoutManager(new LinearLayoutManager(context));
                                recyclerDeliver.setAdapter(deliveredSalesAdapter);
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SellerSalesDetailResponse> call, Throwable t) {
                Log.e("MySalesDetailResp", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setCancellAcceptReject(final String status) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("CancelAcceptRejectReq", "SellerUserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + status);
        Call<CommonResponse> call = apiService.callSellerCancelledStatus(user_id, order_id, status);// type 1 for buyer
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

    private void setAcceptReject(String status, String order_id, String msg_id) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("RevisionAcceptRejectReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nStatus: " + status + "\nMessageId: " + msg_id);
        Call<CommonResponse> call = apiService.callsellerRevision(user_id, order_id, status, msg_id);
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


    private void openDeliveryTimeDialog() {
        deliveryTimeDialog = new Dialog(context);
        deliveryTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deliveryTimeDialog.setContentView(R.layout.dialog_revisons);
        deliveryTimeDialog.setCancelable(true);
        deliveryTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deliveryTimeDialog.show();
        RecyclerView recycle_delivery = (RecyclerView) deliveryTimeDialog.findViewById(R.id.recycler_revisons);
        TextView txt_title = (TextView) deliveryTimeDialog.findViewById(R.id.txt_title);
        ImageView img_close = (ImageView) deliveryTimeDialog.findViewById(R.id.img_close);

        txt_title.setText("Ask Time Extension");
        deliveryTimeAdapter = new DeliveryTimeAdapter(context, delivertTimeModelList, deliveryItemClickListener);
        recycle_delivery.setAdapter(deliveryTimeAdapter);
        recycle_delivery.setLayoutManager(new LinearLayoutManager(context));

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryTimeDialog.dismiss();
            }
        });
    }

    private void openTimeExtensionDialog() {

        timeExtentionDialog = new Dialog(context);
        timeExtentionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        timeExtentionDialog.setContentView(R.layout.dialog_time_extention);
        timeExtentionDialog.setCancelable(true);
        timeExtentionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timeExtentionDialog.show();
        ImageView img_close = (ImageView) timeExtentionDialog.findViewById(R.id.img_close);
        LinearLayout linear_date = (LinearLayout) timeExtentionDialog.findViewById(R.id.linear_date);
        edt_date = (EditText) timeExtentionDialog.findViewById(R.id.edt_date);
        final EditText edt_message = (EditText) timeExtentionDialog.findViewById(R.id.edt_message);
        Button btn_submit = (Button) timeExtentionDialog.findViewById(R.id.btn_submit);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeExtentionDialog.dismiss();
            }
        });

        linear_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDeliveryTimeDialog();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMethods.isNetworkAvailable(context)) {
                    String description = "";
                    description = edt_message.getText().toString();

                    if (description.equals("")) {
                        GlobalMethods.Toast(context, "Enter Description");
                    } else if (delivery_time.equals("")) {
                        GlobalMethods.Toast(context, "Enter time");
                    } else {
                        setAskTime(description);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }

            }
        });
    }

    private void init() {

        delivertTimeModelList = new ArrayList<>();

        delivertTimeModelList.add(new DeliveryModel("1"));
        delivertTimeModelList.add(new DeliveryModel("2"));
        delivertTimeModelList.add(new DeliveryModel("3"));
        delivertTimeModelList.add(new DeliveryModel("4"));
        delivertTimeModelList.add(new DeliveryModel("5"));
        delivertTimeModelList.add(new DeliveryModel("6"));
        delivertTimeModelList.add(new DeliveryModel("7"));
        delivertTimeModelList.add(new DeliveryModel("8"));
        delivertTimeModelList.add(new DeliveryModel("9"));
        delivertTimeModelList.add(new DeliveryModel("10"));
        delivertTimeModelList.add(new DeliveryModel("11"));
        delivertTimeModelList.add(new DeliveryModel("12"));
        delivertTimeModelList.add(new DeliveryModel("13"));
        delivertTimeModelList.add(new DeliveryModel("14"));
        delivertTimeModelList.add(new DeliveryModel("15"));
        delivertTimeModelList.add(new DeliveryModel("16"));
        delivertTimeModelList.add(new DeliveryModel("17"));
        delivertTimeModelList.add(new DeliveryModel("18"));
        delivertTimeModelList.add(new DeliveryModel("19"));
        delivertTimeModelList.add(new DeliveryModel("20"));
        delivertTimeModelList.add(new DeliveryModel("21"));
        delivertTimeModelList.add(new DeliveryModel("22"));
        delivertTimeModelList.add(new DeliveryModel("23"));
        delivertTimeModelList.add(new DeliveryModel("24"));
        delivertTimeModelList.add(new DeliveryModel("25"));
        delivertTimeModelList.add(new DeliveryModel("26"));
        delivertTimeModelList.add(new DeliveryModel("27"));
        delivertTimeModelList.add(new DeliveryModel("28"));
        delivertTimeModelList.add(new DeliveryModel("29"));
        delivertTimeModelList.add(new DeliveryModel("30"));

        deliveryItemClickListener = new DeliveryTimeAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name) {
                if (name.equals("1")) {
                    edt_date.setText(name + " day");
                } else {
                    edt_date.setText(name + " days");
                }
                deliveryTimeDialog.dismiss();
                delivery_time = name;
            }
        };

    }

    private void setAskTime(String description) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("AskExtraTimeReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\ndelivery_time: " + delivery_time + "\ndescription: " + description);
        Call<CommonResponse> call = apiService.callAskTimeExtention(user_id, order_id, delivery_time, description);// type 2 for seller
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("AskExtraTimeResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            timeExtentionDialog.dismiss();
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
        Log.e("UserRatingReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nType: " + "2" + "\nRating: " + rating1 + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callUserReview(user_id, order_id, "2", rating1, message,gig_id);// type 2 for seller
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("UserRatingResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            rating.dismiss();
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

    private void setBottomButtonAPI(final String type, String message) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("MyOrdersBottomReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nBuyerId: " + buyer_id + "\nType: " + type + "\nMessage: " + message);
        Call<CommonResponse> call = apiService.callSellerOrderHistory(user_id, order_id, buyer_id, type, message);
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
                        setCancellOrder("2", message);
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
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

    private void openmessagedialog() {
        messagedialog = new Dialog(SalesDetailsActivity.this);
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

    private void openrevisiondialog() {

        reviewdialog = new Dialog(SalesDetailsActivity.this);
        reviewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewdialog.setContentView(R.layout.dialog_sales_revision);
        reviewdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reviewdialog.show();
        ImageView close = (ImageView) reviewdialog.findViewById(R.id.img_close);
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
                Intent intent1 = new Intent(SalesDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "3");
                startActivity(intent1);
            }
        });
    }

    private void openratingdialog() {

        rating_value = "";
        rating = new Dialog(SalesDetailsActivity.this);
        rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rating.setContentView(R.layout.dialog_rating);
        rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rating.show();
        final EditText edit_message = (EditText) rating.findViewById(R.id.edit_message);
        ImageView close = (ImageView) rating.findViewById(R.id.img_close);
        Button submit = (Button) rating.findViewById(R.id.btn_submit);
        TextView txt_title = (TextView) rating.findViewById(R.id.txt_title);
        txt_title.setText("User Feedback");
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
                        setUserRating(rating_value, message);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
