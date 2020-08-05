package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Intent;
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
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.ImagesliderdynamicAdapter;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.apiModel.GigRequestResponse;
import com.gigpeople.app.subfragment.ReviewsFragment;
import com.gigpeople.app.subfragment.WorkHistoryFragment;
import com.gigpeople.app.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RequestDetailsActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_offer)
    Button btnOffer;
    String params;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    /*   @BindView(R.id.btn_delivery)
       Button btnDelivery;
       @BindView(R.id.btn_cancel)
       Button btnCancel;*/
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.tabs)
    TabLayout tabs;
    ViewPagerAdapter adapter;
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
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.btn_morereviews)
    Button btnMorereviews;
    int[] image = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    ImagesliderdynamicAdapter ImagesliderdynamicAdapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    public List<GigRequestResponse.RequestDetail> gignewrequestlistdetialspage;

    public List<GigRequestOffersentResponse.RequestDetail> gignewrequestlistoffersentdetialspage;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.edt_email)
    TextView edtEmail;
    @BindView(R.id.linearOne)
    LinearLayout linearOne;
    int page;
    @BindView(R.id.txtemailverifed)
    TextView txtemailverifed;
    @BindView(R.id.txtmobileverifed)
    TextView txtmobileverifed;
    @BindView(R.id.txtmaincategory)
    TextView txtmaincategory;
    @BindView(R.id.txt_subcatogory)
    TextView txtSubcatogory;
    @BindView(R.id.txt_delivery)
    TextView txtDelivery;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    String setlayout;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.txtsellername)
    TextView txtsellername;
    @BindView(R.id.offer_txt_delivery)
    TextView offerTxtDelivery;
    @BindView(R.id.offer_txt_description)
    TextView offerTxtDescription;
    @BindView(R.id.offer_txt_price)
    TextView offerTxtPrice;
    @BindView(R.id.linear_my_offer)
    LinearLayout linearMyOffer;
    @BindView(R.id.txt_quantity)
    TextView txtQuantity;
    @BindView(R.id.offer_txt_quantity)
    TextView offerTxtQuantity;
    String seller_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        ButterKnife.bind(this);
        Window window = RequestDetailsActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(RequestDetailsActivity.this, R.color.colorPrimaryDark));
        }

        if (getIntent() != null) {

            page = getIntent().getIntExtra("page", 0);
            setlayout = getIntent().getStringExtra("setlayout");

            toLayoutsetup(page, setlayout);

            if (setlayout.equalsIgnoreCase("1")) {
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);

            } else {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        }

        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        setupViewPager(viewpage);
        tabs.setupWithViewPager(viewpage);

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

    @OnClick({R.id.btn_update, R.id.btn_back_arrow, R.id.btn_offer, R.id.review, R.id.revision, R.id.message, R.id.img_profile, R.id.btn_morereviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                Intent intent1 = new Intent(RequestDetailsActivity.this, BuyOfferActivity.class);
                //1 offer submit
                //2 for update
                intent1.putExtra("page", page);
                intent1.putExtra("layoutsetup", "2");
                startActivity(intent1);
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_offer:
                Intent intent = new Intent(RequestDetailsActivity.this, BuyOfferActivity.class);
                //1 offer submit
                //2 for update
                intent.putExtra("page", page);
                intent.putExtra("layoutsetup", "1");
                startActivity(intent);
                break;
            case R.id.review:
                openratingdialog();
                break;
            case R.id.revision:
                openrevisiondialog();
                break;
            case R.id.message:
                Intent intent2 = new Intent(RequestDetailsActivity.this, MessengerActivity.class);
                startActivity(intent2);
                break;
            case R.id.cancel:
                Intent intent3 = new Intent(RequestDetailsActivity.this, MainActivity.class);
                intent3.putExtra("page", "1");
                startActivity(intent3);
                break;
            case R.id.img_profile:
                break;
            case R.id.btn_morereviews:
                Intent intent4 = new Intent(RequestDetailsActivity.this, ReviewListActivity.class);
                intent4.putExtra("review_list_from","3");
                intent4.putExtra("seller_id",seller_id);
                startActivity(intent4);
                break;

        }
    }

    private void openrevisiondialog() {

        reviewdialog = new Dialog(RequestDetailsActivity.this);
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
                Intent intent1 = new Intent(RequestDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "2");
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void toLayoutsetup(int position, String layout) {

        if (layout.equals("1")) {
            gignewrequestlistdetialspage = new ArrayList<>();
            gignewrequestlistdetialspage = GlobalVariables.gigNewRequestlist;

            txtPrice.setText("$" + gignewrequestlistdetialspage.get(position).getPrice());
            seller_id=gignewrequestlistdetialspage.get(position).getUserId();
            txtQuantity.setText(gignewrequestlistdetialspage.get(position).getQuantity());
            txtsellername.setText(gignewrequestlistdetialspage.get(position).getUserDetails().getFirstName() + " " + gignewrequestlistdetialspage.get(position).getUserDetails().getLastName());
            //profile image
            if (!gignewrequestlistdetialspage.get(position).getUserDetails().getProfileImageUrl().equals("")) {
                Glide.with(this).load(gignewrequestlistdetialspage.get(position).getUserDetails().getProfileImageUrl()).into(imgProfile);
            }
            //email verified or not
            if (gignewrequestlistdetialspage.get(position).getUserDetails().getIsEmailVerified().equals("1")) {

                txtemailverifed.setText("Verified");
                txtemailverifed.setBackgroundResource(R.drawable.button_background_profile_changes_three);

            } else {
                txtemailverifed.setText("Not Verified");
                txtemailverifed.setBackgroundResource(R.drawable.background_unverified);
            }
            //Log.e("Mobile", gignewrequestlistdetialspage.get(position).getUserDetails().getIsMobileVerified() + " Email: " + gignewrequestlistdetialspage.get(position).getUserDetails().getIs_email_verified() + " LastName: " + gignewrequestlistdetialspage.get(position).getUserDetails().getLastName());
            //mobilenumber verified or not
            if (gignewrequestlistdetialspage.get(position).getUserDetails().getIsMobileVerified().equals("1")) {

                txtmobileverifed.setText("Verified");

                txtmobileverifed.setBackgroundResource(R.drawable.button_background_profile_changes_three);
            } else {

                txtmobileverifed.setText("Not Verified");

                txtmobileverifed.setBackgroundResource(R.drawable.background_unverified);
            }

            //set main category name
            txtmaincategory.setText(gignewrequestlistdetialspage.get(position).getCategoryName());

            //set Ssubcategory name
            txtSubcatogory.setText(gignewrequestlistdetialspage.get(position).getSubCategoryName());

            // set images bannners

            List<GigRequestResponse.RequestDetail.ImageList> userbannerImagelist;

            userbannerImagelist = new ArrayList<>();


            userbannerImagelist = gignewrequestlistdetialspage.get(position).getImageList();


            List<String> images = new ArrayList<>();

            for (int i = 0; i < userbannerImagelist.size(); i++) {
                images.add(userbannerImagelist.get(i).getImageUrl());
            }

            ImagesliderdynamicAdapter = new ImagesliderdynamicAdapter(getBaseContext(), images);
            recycler.setAdapter(ImagesliderdynamicAdapter);
            recycler.setLayoutManager(new GridLayoutManager(RequestDetailsActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));

            //set Delicvery time
            if (gignewrequestlistdetialspage.get(position).getDeliverytime().equals("1")) {
                txtDelivery.setText(gignewrequestlistdetialspage.get(position).getDeliverytime()+" day");
            }else {
                txtDelivery.setText(gignewrequestlistdetialspage.get(position).getDeliverytime()+" days");
            }
            //set description

            txtDescription.setText(gignewrequestlistdetialspage.get(position).getDescription());


        } else if (layout.equals("2")) {
            gignewrequestlistoffersentdetialspage = new ArrayList<>();
            gignewrequestlistoffersentdetialspage = GlobalVariables.gigNewRequesSenttlist;
            Log.e("Mobile", gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getIsEmailVerified());
            seller_id=gignewrequestlistoffersentdetialspage.get(position).getUserId();

            txtPrice.setText("$" + gignewrequestlistoffersentdetialspage.get(position).getPrice());
            txtQuantity.setText(gignewrequestlistoffersentdetialspage.get(position).getQuantity());
            txtsellername.setText(gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getFirstName() + " " + gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getLastName());
            if (!gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getProfileImageUrl().equals("")) {
                Glide.with(this).load(gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getProfileImageUrl()).into(imgProfile);
            }
            //email verified or not
            if (gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getIsEmailVerified().equals("1")) {

                txtemailverifed.setText("Verified");
                txtemailverifed.setBackgroundResource(R.drawable.button_background_profile_changes_three);

            } else {
                txtemailverifed.setText("Not Verified");
                txtemailverifed.setBackgroundResource(R.drawable.background_unverified);
            }

            //mobilenumber verified or not

            if (gignewrequestlistoffersentdetialspage.get(position).getUserDetails().getIsMobileVerified().equals("1")) {

                txtmobileverifed.setText("Verified");

                txtmobileverifed.setBackgroundResource(R.drawable.button_background_profile_changes_three);
            } else {

                txtmobileverifed.setText("Not Verified");

                txtmobileverifed.setBackgroundResource(R.drawable.background_unverified);
            }

            //set main category name
            txtmaincategory.setText(gignewrequestlistoffersentdetialspage.get(position).getCategoryName());

            //set Ssubcategory name
            txtSubcatogory.setText(gignewrequestlistoffersentdetialspage.get(position).getSubCategoryName());

            // set images bannners

            List<GigRequestOffersentResponse.RequestDetail.ImageList> userbannerImagelist;

            userbannerImagelist = new ArrayList<>();
            userbannerImagelist = gignewrequestlistoffersentdetialspage.get(position).getImageList();
            List<String> images = new ArrayList<>();

            for (int i = 0; i < userbannerImagelist.size(); i++) {
                images.add(userbannerImagelist.get(i).getImageUrl());
            }

            ImagesliderdynamicAdapter = new ImagesliderdynamicAdapter(getBaseContext(), images);
            recycler.setAdapter(ImagesliderdynamicAdapter);
            recycler.setLayoutManager(new GridLayoutManager(RequestDetailsActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));

            //set Delicvery time
            if (gignewrequestlistoffersentdetialspage.get(position).getDeliverytime().equals("1")) {
                txtDelivery.setText(gignewrequestlistoffersentdetialspage.get(position).getDeliverytime()+" day");
            }else {
                txtDelivery.setText(gignewrequestlistoffersentdetialspage.get(position).getDeliverytime()+" days");
            }
            //set description
            txtDescription.setText(gignewrequestlistoffersentdetialspage.get(position).getDescription());

            if (gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getOffer_status().equals("0")) {
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("Waiting for Acceptance");
                txtStatus.setBackgroundResource(R.color.yellow);
                btnUpdate.setVisibility(View.VISIBLE);
            } else if (gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getOffer_status().equals("1")) {
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("Accepted");
                txtStatus.setBackgroundResource(R.color.colorGreen);
                btnUpdate.setVisibility(View.GONE);
            } else {
                txtStatus.setVisibility(View.VISIBLE);
                txtStatus.setText("Rejected");
                txtStatus.setBackgroundResource(R.color.colorRed);
                btnUpdate.setVisibility(View.GONE);
            }

            linearMyOffer.setVisibility(View.VISIBLE);
            if (gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getDeliverytime().equals("1")) {
                offerTxtDelivery.setText(gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getDeliverytime()+" day");
            }else {
                offerTxtDelivery.setText(gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getDeliverytime()+" days");
            }
            offerTxtDescription.setText(gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getDescription());
            offerTxtPrice.setText("$" + gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getPrice());
           // offerTxtQuantity.setText(gignewrequestlistoffersentdetialspage.get(position).getMyOffer().getQuantity());
        }


    }

    private void openratingdialog() {
        rating = new Dialog(RequestDetailsActivity.this);
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
                Intent intent1 = new Intent(RequestDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "1");
                startActivity(intent1);
            }
        });

    }

}



