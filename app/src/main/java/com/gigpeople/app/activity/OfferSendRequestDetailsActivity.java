package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.ImageSliderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OfferSendRequestDetailsActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_offer)
    Button btnOffer;
    String params;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.btn_delivery)
    Button btnDelivery;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.tabs)
    TabLayout tabs;
    // ViewPagerAdapter adapter;
    /*   @BindView(R.id.review)
       ImageView review;
       @BindView(R.id.message)
       ImageView message;
       @BindView(R.id.revision)
       ImageView revision;
       @BindView(R.id.cancel)
       ImageView cancel;*/
    Dialog rating, reviewdialog;
    ImageView fivestar, img_4star, img_3star, img_2star, img_1star;
    LinearLayout lin_5star, lin_4star, lin_3star, lin_2star, lin_1star;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    String sttatus;
    @BindView(R.id.txt_statausaccepted)
    TextView txtStatausaccepted;
    @BindView(R.id.txtstatusrejected)
    TextView txtstatusrejected;
    @BindView(R.id.btn_morereviews)
    Button btnMorereviews;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.txt_waiting)
    TextView txtWaiting;
    int[] image = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    ImageSliderAdapter imageSliderAdapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offersendcategory_details);
        ButterKnife.bind(this);

        Window window = OfferSendRequestDetailsActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(OfferSendRequestDetailsActivity.this, R.color.colorPrimaryDark));
        }
        params = getIntent().getStringExtra("page");
        sttatus = getIntent().getStringExtra("status");
        if (getIntent() != null) {
            if (params.equalsIgnoreCase("1")) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);

            } else {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);

            }

            if (sttatus.equalsIgnoreCase("1")) {
                txtStatausaccepted.setVisibility(View.GONE);
                txtstatusrejected.setVisibility(View.VISIBLE);
                txtWaiting.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);


            } else if (sttatus.equals("2")) {
                txtStatausaccepted.setVisibility(View.VISIBLE);
                txtstatusrejected.setVisibility(View.GONE);
                txtWaiting.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE);


            } else if (sttatus.equals("3")) {
                txtStatausaccepted.setVisibility(View.GONE);
                txtstatusrejected.setVisibility(View.GONE);
                txtWaiting.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
            }
        }
        imageSliderAdapter = new ImageSliderAdapter(OfferSendRequestDetailsActivity.this, image);
        recycler.setAdapter(imageSliderAdapter);
        recycler.setLayoutManager(new GridLayoutManager(OfferSendRequestDetailsActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));


       /* tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        setupViewPager(viewpage);
        tabs.setupWithViewPager(viewpage);*/
    }

    /*private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(WorkHistoryFragment.newInstance(), "Work History and Feedback");
        adapter.addFragment(ReviewsFragment.newInstance(), "Reviews");

        viewpager.setAdapter(adapter);


    }*/

/*
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
*/

    @OnClick({R.id.btn_back_arrow, R.id.btn_offer, R.id.img_profile, R.id.btn_morereviews, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_offer:
                Intent intent = new Intent(OfferSendRequestDetailsActivity.this, BuyOfferActivity.class);
                intent.putExtra("page", "1");
                startActivity(intent);
                break;
            case R.id.img_profile:

               /* Intent intent4 = new Intent(OfferSendRequestDetailsActivity.this, ProfileDetailsActivity.class);
                startActivity(intent4);*/

                break;
           /* case R.id.review:
                openratingdialog();

                break;

            case R.id.revision:
                openrevisiondialog();

                break;
            case R.id.message:

                Intent intent2=new Intent(OfferSendRequestDetailsActivity.this,MessengerActivity.class);
                startActivity(intent2);

                break;

            case R.id.cancel:

                Intent intent3=new Intent(OfferSendRequestDetailsActivity.this,MainActivity.class);
                intent3.putExtra("page","2");
                startActivity(intent3);

                break;*/

            case R.id.btn_morereviews:
                Intent intent3 = new Intent(OfferSendRequestDetailsActivity.this, ReviewListActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_update:
                Intent intent4 = new Intent(OfferSendRequestDetailsActivity.this, BuyOfferActivity.class);
                intent4.putExtra("page", "1");
                startActivity(intent4);
                break;


        }
    }

    private void openrevisiondialog() {

        reviewdialog = new Dialog(OfferSendRequestDetailsActivity.this);
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
                Intent intent1 = new Intent(OfferSendRequestDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "1");
                startActivity(intent1);
            }
        });
    }

    private void openratingdialog() {
        rating = new Dialog(OfferSendRequestDetailsActivity.this);
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
                Intent intent1 = new Intent(OfferSendRequestDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "1");
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
