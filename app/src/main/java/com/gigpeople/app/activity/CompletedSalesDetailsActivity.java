package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.SalefourAdapter;
import com.gigpeople.app.adapter.SalesReviewAdapter;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.subfragment.ReviewsFragment;
import com.gigpeople.app.subfragment.WorkHistoryFragment;
import com.gigpeople.app.utils.GlobalMethods;

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

import static com.gigpeople.app.fragment.MysaleFragment.completedOrderLists;
import static com.gigpeople.app.fragment.MysaleFragment.currentOrderLists;
import static com.gigpeople.app.fragment.MysaleFragment.orderRequestLists;

public class CompletedSalesDetailsActivity extends AppCompatActivity {


    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu)
    Button btnMenu;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    SalesReviewAdapter salesReviewAdapter;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerReviews;
    ViewPagerAdapter adapter;
    Dialog share;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_decline)
    Button btnDecline;
    @BindView(R.id.review)
    ImageView review;
    @BindView(R.id.message)
    ImageView message;
    @BindView(R.id.revision)
    ImageView revision;
    @BindView(R.id.cancel)
    ImageView cancel;
    Dialog rating, reviewdialog;
    ImageView fivestar, img_4star, img_3star, img_2star, img_1star;
    LinearLayout lin_5star, lin_4star, lin_3star, lin_2star, lin_1star;
    @BindView(R.id.btn_acceptsales)
    Button btnAcceptsales;
    @BindView(R.id.btn_rejectsales)
    Button btnRejectsales;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String page;
    @BindView(R.id.button_layout)
    LinearLayout buttonLayout;
    Dialog messagedialog;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.img_fav)
    ImageView imgFav;
    int fav_status = 0;
    @BindView(R.id.workhistory)
    TextView workhistory;
    @BindView(R.id.workhistory_layout)
    LinearLayout workhistoryLayout;
    @BindView(R.id.reviews)
    TextView reviews;
    @BindView(R.id.review_layout)
    LinearLayout reviewLayout;
    @BindView(R.id.recycler_workhistory)
    RecyclerView recyclerWorkhistory;
    @BindView(R.id.recycler_review)
    RecyclerView recyclerReview;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    SalefourAdapter adapter1;
    List<ChatModel> sale_list;
    @BindView(R.id.imgadmin)
    ImageView imgadmin;

    int position;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
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
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.txt_qty)
    TextView txtQty;
    @BindView(R.id.txt_duration)
    TextView txtDuration;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    @BindView(R.id.txt_total_fee)
    TextView txtTotalFee;
    @BindView(R.id.linearOne)
    LinearLayout linearOne;

    Context context;
    String TIME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_sales_details);
        ButterKnife.bind(this);
        context=CompletedSalesDetailsActivity.this;
        preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = preferences.edit();
        imgadmin.setVisibility(View.GONE);
        /*page = getIntent().getStringExtra("page");
        Log.e("Page",page);
        if (getIntent() != null) {
            if (page.equalsIgnoreCase("1")) {
                buttonLayout.setVisibility(View.GONE);
                workhistoryLayout.setVisibility(View.VISIBLE);
                reviewLayout.setVisibility(View.VISIBLE);
               // recyclerReview.setVisibility(View.VISIBLE);
                recyclerWorkhistory.setVisibility(View.VISIBLE);

            } else if(page.equalsIgnoreCase("2")){
                buttonLayout.setVisibility(View.VISIBLE);
                workhistoryLayout.setVisibility(View.GONE);
                reviewLayout.setVisibility(View.GONE);
                recyclerReview.setVisibility(View.GONE);
                recyclerWorkhistory.setVisibility(View.GONE);

            }
            else if(page.equalsIgnoreCase("3"))
            {
                buttonLayout.setVisibility(View.GONE);
                workhistoryLayout.setVisibility(View.GONE);
                reviewLayout.setVisibility(View.GONE);
                recyclerReview.setVisibility(View.GONE);
                recyclerWorkhistory.setVisibility(View.GONE);
            }
        }*/

        Window window = CompletedSalesDetailsActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(CompletedSalesDetailsActivity.this, R.color.colorPrimaryDark));
        }
       /* tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        setupViewPager(viewpage);
        tabs.setupWithViewPager(viewpage);*/
       /// salesReviewAdapter = new SalesReviewAdapter(CompletedSalesDetailsActivity.this);
        recyclerReview.setAdapter(salesReviewAdapter);
        recyclerReview.setLayoutManager(new LinearLayoutManager(CompletedSalesDetailsActivity.this));
        sale_list = new ArrayList<>();
        sale_list.add(new ChatModel("", "Me"));
        sale_list.add(new ChatModel("", "John"));
        sale_list.add(new ChatModel("", "Me"));
        sale_list.add(new ChatModel("", "John"));

        sale_list.add(new ChatModel("", "Me"));
       // adapter1 = new SalefourAdapter(CompletedSalesDetailsActivity.this, sale_list);
        recyclerWorkhistory.setAdapter(adapter1);
        recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);

            setCompleteDetails();
        }
    }

    private void setCompleteDetails() {
        if (!completedOrderLists.get(position).getBuyerDetails().getProfileImageUrl().equals("")){
            Glide.with(context).load(completedOrderLists.get(position).getBuyerDetails().getProfileImageUrl()).into(imgProfile);

        }
        txtName.setText(completedOrderLists.get(position).getBuyerDetails().getFirstName() + " " + completedOrderLists.get(position).getBuyerDetails().getLastName());
        txtDescription.setText(completedOrderLists.get(position).getDescription());
        txtQty.setText(completedOrderLists.get(position).getQuantity());
       // txtDuration.setText(completedOrderLists.get(position).getDeliverytime());
        txtAmount.setText("$" + completedOrderLists.get(position).getPrice());
        txtTotalFee.setText("$" + completedOrderLists.get(position).getPrice());
        txtOrderValue.setText("$" + completedOrderLists.get(position).getPrice());
        txtOrder.setText("#" + completedOrderLists.get(position).getOrderId());
        txtOrderDate.setText(GlobalMethods.DateTime24(completedOrderLists.get(position).getOrderDate()));
        txtCategory.setText(completedOrderLists.get(position).getCategoryName() + "/ " + completedOrderLists.get(position).getSubCategoryName());

        String TIME = setTimeString(completedOrderLists.get(position).getDeliverytime());

        if (TIME.equals("0 minutes ago")) {
            txtDuration.setText("Just Now");
        } else {
            txtDuration.setText(TIME);
        }
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(WorkHistoryFragment.newInstance(), "Work History and Feedback");
        adapter.addFragment(ReviewsFragment.newInstance(), "Reviews");

        viewpager.setAdapter(adapter);


    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_menu, R.id.btn_accept, R.id.btn_decline, R.id.review, R.id.revision, R.id.message, R.id.cancel, R.id.btn_acceptsales, R.id.btn_rejectsales, R.id.img_share, R.id.img_fav, R.id.workhistory_layout, R.id.review_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                //opensharedialog();
                openmessagedialog();

                break;

            case R.id.btn_accept:
               /* Intent intent = new Intent(SalesDetailsActivity.this, SalesDeliveryActivity.class);
                startActivity(intent);*/

                break;
            case R.id.btn_decline:
                Intent intent1 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "3");
                startActivity(intent1);

                break;
            case R.id.review:
                openratingdialog();

                break;

            case R.id.revision:
                openrevisiondialog();

                break;
            case R.id.message:

                Intent intent2 = new Intent(CompletedSalesDetailsActivity.this, MessengerActivity.class);
                startActivity(intent2);

                break;

            case R.id.cancel:

                Intent intent3 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent3.putExtra("page", "3");
                startActivity(intent3);

                break;
            case R.id.btn_acceptsales:

                Intent intent4 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent4.putExtra("page", "3");
                editor.putString("sales_index", "1");
                editor.commit();
                startActivity(intent4);

                break;
            case R.id.btn_rejectsales:

                Intent intent5 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent5.putExtra("page", "3");
                editor.putString("sales_index", "3");
                editor.commit();
                startActivity(intent5);

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

            case R.id.workhistory_layout:
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                view1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.VISIBLE);
                recyclerReview.setVisibility(View.GONE);
                sale_list = new ArrayList<>();
                sale_list.add(new ChatModel("", "Me"));
                sale_list.add(new ChatModel("", "John"));
                sale_list.add(new ChatModel("", "Me"));
                sale_list.add(new ChatModel("", "John"));

                sale_list.add(new ChatModel("", "Me"));
               //adapter1 = new SalefourAdapter(CompletedSalesDetailsActivity.this, sale_list);
                recyclerWorkhistory.setAdapter(adapter1);
                recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                break;

            case R.id.review_layout:
                reviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                workhistory.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                view2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                recyclerWorkhistory.setVisibility(View.GONE);
                recyclerReview.setVisibility(View.VISIBLE);
                //salesReviewAdapter = new SalesReviewAdapter(getApplicationContext());
                recyclerReview.setAdapter(salesReviewAdapter);
                recyclerReview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                break;

        }
    }

    private void openmessagedialog() {
        messagedialog = new Dialog(CompletedSalesDetailsActivity.this);
        messagedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        messagedialog.setContentView(R.layout.dialog_sales_adminmessage);
        messagedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        messagedialog.show();
        ImageView close = (ImageView) messagedialog.findViewById(R.id.img_close);
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
               /* Intent intent1=new Intent(SalesDetailsActivity.this,MainActivity.class);
                intent1.putExtra("page","1");
              startActivity(intent1);*/

                messagedialog.dismiss();
            }
        });
    }

    private void openrevisiondialog() {

        reviewdialog = new Dialog(CompletedSalesDetailsActivity.this);
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
                Intent intent1 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "3");
                startActivity(intent1);
            }
        });
    }

    private void openratingdialog() {
        rating = new Dialog(CompletedSalesDetailsActivity.this);
        rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rating.setContentView(R.layout.dialog_rating);
        rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rating.show();
        ImageView close = (ImageView) rating.findViewById(R.id.img_close);
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
                Intent intent1 = new Intent(CompletedSalesDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "3");
                startActivity(intent1);
            }
        });

    }

    private void opensharedialog() {
        share = new Dialog(CompletedSalesDetailsActivity.this);
        share.requestWindowFeature(Window.FEATURE_NO_TITLE);
        share.setContentView(R.layout.dialog_sales_share);
        share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        share.show();
        ImageView close = (ImageView) share.findViewById(R.id.img_close);
        final LinearLayout share1 = (LinearLayout) share.findViewById(R.id.share_layout);
        LinearLayout fav = (LinearLayout) share.findViewById(R.id.fav_layout);
        LinearLayout admin = (LinearLayout) share.findViewById(R.id.admin_layout);

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
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "GigPeople";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "GigPeople");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

               /* Intent share = new Intent(SalesDetailsActivity.this, ShareActivity.class);
                startActivity(share);*/
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();

/*
                Intent intent = new Intent(SalesDetailsActivity.this, MainActivity.class);
                intent.putExtra("page", "6");
                startActivity(intent);*/

            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();


                /*Intent intent=new Intent(SalesDetailsActivity.this,MainActivity.class);
                startActivity(intent);*/

            }
        });


    }
}
