package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.AdapterImageSlide;
import com.gigpeople.app.adapter.SaleAdapter;
import com.gigpeople.app.adapter.SaleTwoAdapter;
import com.gigpeople.app.adapter.SalesReviewAdapter;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.subfragment.ReviewsFragment;
import com.gigpeople.app.subfragment.WorkHistoryFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CancelledOrdersActivity extends AppCompatActivity {


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
    @BindView(R.id.userImage)
    CircleImageView userImage;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    int fav_status = 0;
    int[] banner = {R.drawable.banner_one, R.drawable.banner_two};
    AdapterImageSlide adapterImageSlide;
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
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.linearOne)
    LinearLayout linearOne;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.review)
    ImageView review;
    @BindView(R.id.message)
    ImageView message;
    @BindView(R.id.revision)
    ImageView revision;
    @BindView(R.id.cancel)
    ImageView cancel;
    ViewPagerAdapter adapter;
    Dialog messagedialog;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    SaleTwoAdapter adapter1;
    List<ChatModel> sale_list;
    SalesReviewAdapter salesReviewAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders_cancelled_details);
        ButterKnife.bind(this);
        //adapterImageSlide = new AdapterImageSlide(CancelledOrdersActivity.this, banner);
        viewpager.setAdapter(adapterImageSlide);
        pageController.setViewPager(viewpager);
        Window window = CancelledOrdersActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(CancelledOrdersActivity.this, R.color.colorPrimaryDark));
        }
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
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        setupViewPager(viewpage);
        tabs.setupWithViewPager(viewpage);
        //salesReviewAdapter = new SalesReviewAdapter(CancelledOrdersActivity.this);
        recyclerReview.setAdapter(salesReviewAdapter);
        recyclerReview.setLayoutManager(new LinearLayoutManager(CancelledOrdersActivity.this));
        sale_list = new ArrayList<>();
        sale_list.add(new ChatModel("", "Me"));
        sale_list.add(new ChatModel("", "John"));
        sale_list.add(new ChatModel("", "Me"));
        sale_list.add(new ChatModel("", "John"));

        sale_list.add(new ChatModel("", "Me"));
        adapter1 = new SaleTwoAdapter(CancelledOrdersActivity.this, sale_list);
        recyclerWorkhistory.setAdapter(adapter1);
        recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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


    @OnClick({R.id.btn_back_arrow, R.id.btn_menu, R.id.img_fav, R.id.userImage, R.id.btn_cancel, R.id.review, R.id.revision, R.id.message, R.id.cancel, R.id.img_share, R.id.img_chat, R.id.workhistory_layout, R.id.review_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_menu:
                //opensharedialog();
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
                Intent intent1 = new Intent(CancelledOrdersActivity.this, ReviewListActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_cancel:
                //opencanceldialog();
                openmessagedialog();
                break;

            case R.id.review:
                openratingdialog();

                break;

            case R.id.revision:
                openrevisiondialog();

                break;
            case R.id.message:

                Intent intent2 = new Intent(CancelledOrdersActivity.this, MessengerActivity.class);
                startActivity(intent2);

                break;

            case R.id.cancel:

                Intent intent3 = new Intent(CancelledOrdersActivity.this, MainActivity.class);
                intent3.putExtra("page", "5");
                startActivity(intent3);

                break;

            case R.id.img_chat:

                Intent intent4 = new Intent(CancelledOrdersActivity.this, ChatDetailsActivity.class);
                startActivity(intent4);

                break;
            case R.id.img_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "GigPeople";
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
                sale_list=new ArrayList<>();
                sale_list.add(new ChatModel("","Me"));
                sale_list.add(new ChatModel("","John"));
                sale_list.add(new ChatModel("","Me"));
                sale_list.add(new ChatModel("","John"));

                sale_list.add(new ChatModel("","Me"));
                adapter1 = new SaleTwoAdapter(CancelledOrdersActivity.this, sale_list);
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
        messagedialog = new Dialog(CancelledOrdersActivity.this);
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

        reviewdialog = new Dialog(CancelledOrdersActivity.this);
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
                Intent intent1 = new Intent(CancelledOrdersActivity.this, MainActivity.class);
                intent1.putExtra("page", "5");
                startActivity(intent1);
            }
        });
    }

    private void openratingdialog() {
        rating = new Dialog(CancelledOrdersActivity.this);
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
                Intent intent1 = new Intent(CancelledOrdersActivity.this, MainActivity.class);
                intent1.putExtra("page", "5");
                startActivity(intent1);
            }
        });

    }

    private void opencanceldialog() {
        canceldialog = new Dialog(CancelledOrdersActivity.this);
        canceldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        canceldialog.setContentView(R.layout.dialog_order_cancel);
        canceldialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        canceldialog.show();
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
                Intent intent1 = new Intent(CancelledOrdersActivity.this, MainActivity.class);
                intent1.putExtra("page", "5");
                startActivity(intent1);
            }
        });

    }

    private void opensharedialog() {

        share = new Dialog(CancelledOrdersActivity.this);
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

                Intent share = new Intent(CancelledOrdersActivity.this, ShareActivity.class);
                startActivity(share);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.dismiss();

                Intent intent = new Intent(CancelledOrdersActivity.this, ChatDetailsActivity.class);
                startActivity(intent);

            }
        });
    }

}
