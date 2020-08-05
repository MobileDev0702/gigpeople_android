package com.gigpeople.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.activity.AddNewGigActivity;
import com.gigpeople.app.activity.AddNewRequestActivity;
import com.gigpeople.app.activity.EditProfileActivity;
import com.gigpeople.app.activity.LoginActivity;
import com.gigpeople.app.activity.NotificationListActivity;
import com.gigpeople.app.adapter.CategoryAdapter;
import com.gigpeople.app.adapter.LeftNavAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.fragment.BuyerNewRequestFragment;
import com.gigpeople.app.fragment.ChatFragment;
import com.gigpeople.app.fragment.FavouriteFragment;
import com.gigpeople.app.fragment.HomeFragment;
import com.gigpeople.app.fragment.ManageRequestFragment;
import com.gigpeople.app.fragment.MyGigFragment;
import com.gigpeople.app.fragment.MyOrdersFragment;
import com.gigpeople.app.fragment.MysaleFragment;
import com.gigpeople.app.fragment.ProfileFragment;
import com.gigpeople.app.fragment.Settingsfragment;
import com.gigpeople.app.fragment.WithdrawFragment;
import com.gigpeople.app.model.CategoryModel;
import com.gigpeople.app.model.ClassLeftDrawer;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.testfairy.TestFairy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_menu_open)
    Button btnMenuOpen;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.btn_menu_two)
    Button btnMenuTwo;
    @BindView(R.id.img_menu_two)
    ImageView imgMenuTwo;
    @BindView(R.id.reltaiveTwo)
    RelativeLayout reltaiveTwo;
    //@BindView(R.id.btn_menu)
    public  Button btnMenu;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.relativeMenu)
    RelativeLayout relativeMenu;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.img_header)
    CircleImageView imgHeader;
    @BindView(R.id.txt_header_name)
    TextView txtHeaderName;
    @BindView(R.id.linearUserDetails)
    LinearLayout linearUserDetails;
    @BindView(R.id.list_nav)
    ListView listNav;
    @BindView(R.id.drawer_layout_new)
    DrawerLayout drawerLayoutNew;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.txt_header_email)
    TextView txtHeaderEmail;
    @BindView(R.id.txt_notification_count)
    TextView txtNotificationCount;
    private ActionBarDrawerToggle mDrawerToggle;
    LeftNavAdapter leftNavAdapter;
    List<ClassLeftDrawer> rowItems;
    public static final String[] titles = new String[]{"Home", "Gig Requests", "My Gigs", "My Sales", "Manage Requests", "My Orders", "Favourites", "Chat", "Withdrawal", "Settings", "Logout"};
    private float lastTranslate = 0.0f;
    public static final Integer[] images = {R.drawable.home_icon, R.drawable.buyers_request_icon, R.drawable.mygigs_icon, R.drawable.my_sales_icon, R.drawable.manage_request_icon, R.drawable.my_orders_icon, R.drawable.favourites_icon, R.drawable.chat_icon, R.drawable.withdrawls_icon, R.drawable.setting_icon, R.drawable.logout_icon};
    int fragment_status = 0;
    int notify_status = 0;
    String page, user_id;
    Dialog logout,profileDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    ApiService apiService;
    ProgressDialog progressDialog;
    String address="";
    boolean is_nitification_count_visible=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        ButterKnife.bind(this);
        btnMenu=(Button)findViewById(R.id.btn_menu);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        drawerLayoutNew = (DrawerLayout) findViewById(R.id.drawer_layout_new);
        TestFairy.begin(this, "SDK-z4l0Jemk");
        //bejedod715@prowerl.com / uniflyn123
        Window window1 = MainActivity.this.getWindow();
        window1.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window1.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window1.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        }

        setupDrawer();
        setupnavigation();
        beginTransction(new HomeFragment());
        txtTitle.setText("gigpeople");
        imgMenu.setVisibility(View.VISIBLE);
        imgMenu.setImageResource(R.drawable.notification_main);
        imgMenuTwo.setVisibility(View.GONE);
        notify_status = 1;
        page = getIntent().getStringExtra("page");

        Log.e("PAGE",page+" nandhini");
        try {
            if (getIntent() != null) {
                if (page.equalsIgnoreCase("1")) {
                    fragment_status = 2;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new BuyerNewRequestFragment());
                    txtTitle.setText("Gig Requests");
                    imgMenu.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.filter);
                    txtNotificationCount.setVisibility(View.GONE);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }

                } else if (page.equalsIgnoreCase("6")) {
                    fragment_status = 6;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new FavouriteFragment());
                    txtTitle.setText(titles[6]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                } else if (page.equalsIgnoreCase("3")) {
                    fragment_status = 3;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new MysaleFragment());
                    txtTitle.setText(titles[3]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                } else if (page.equalsIgnoreCase("5")) {
                    fragment_status = 4;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new MyOrdersFragment());
                    txtTitle.setText(titles[5]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                } else if (page.equalsIgnoreCase("4")) {
                    fragment_status = 5;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new ManageRequestFragment());
                    txtTitle.setText(titles[4]);
                    imgMenu.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.add_round_icon);
                    imgMenuTwo.setVisibility(View.GONE);
                    imgMenuTwo.setBackgroundResource(R.drawable.notification_main);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                } else if (page.equalsIgnoreCase("2")) {
                    fragment_status = 1;
                    is_nitification_count_visible=false;
                    notify_status = 2;

                    beginTransction(new MyGigFragment());
                    txtTitle.setText(titles[2]);
                    imgMenu.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.add_round_icon);
                    imgMenuTwo.setVisibility(View.GONE);
                    imgMenuTwo.setBackgroundResource(R.drawable.notification_main);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }

                } else if (page.equalsIgnoreCase("8")) {
                    fragment_status = 8;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new WithdrawFragment());
                    txtTitle.setText(titles[8]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                } else if (page.equalsIgnoreCase("10")) {
                    fragment_status = 10;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new Settingsfragment());
                    txtTitle.setText(titles[10]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                }else if (page.equalsIgnoreCase("7")) {

                    fragment_status = 14;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new ProfileFragment());
                    txtTitle.setText("My Profile");
                    imgMenu.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.edit_profile);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();

                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                }else if (page.equals("11")){

                    fragment_status = 7;
                    notify_status = 2;
                    is_nitification_count_visible=false;

                    beginTransction(new ChatFragment());
                    txtTitle.setText(titles[7]);
                    imgMenu.setVisibility(View.GONE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();

                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                }else if (page.equals("0")){
                    fragment_status = 0;
                    is_nitification_count_visible=true;

                    beginTransction(new HomeFragment());
                    txtTitle.setText("gigpeople");
                    imgMenu.setVisibility(View.VISIBLE);
                    imgMenu.setImageResource(R.drawable.notification_main);
                    imgMenuTwo.setVisibility(View.GONE);
                    notify_status = 1;

                    header.setBackgroundResource(R.color.colorPrimary);
                    Window window = MainActivity.this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        linearUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_status = 14;
                notify_status = 2;
                is_nitification_count_visible=false;

                beginTransction(new ProfileFragment());
                txtTitle.setText("My Profile");
                imgMenu.setVisibility(View.VISIBLE);
                imgMenu.setImageResource(R.drawable.edit_profile);
                imgMenuTwo.setVisibility(View.GONE);
                header.setBackgroundResource(R.color.colorPrimary);
                Window window = MainActivity.this.getWindow();

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                }
            }
        });
    }

    @OnClick({R.id.img_back, R.id.btn_menu_open, R.id.img_menu, R.id.btn_menu, R.id.btn_menu_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.btn_menu_open:
                drawerLayoutNew.openDrawer(GravityCompat.START);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    invalidateOptionsMenu();
                }
                break;
            case R.id.img_menu:
                break;
            case R.id.btn_menu:
                Log.e("btn","mainactivity");


              /* *//* if (fragment_status == 1) {
                    Intent add = new Intent(MainActivity.this, AddNewGigActivity.class);
                    startActivity(add);
                } else *//*if (fragment_status == 5) {
                    Intent add = new Intent(MainActivity.this, AddNewRequestActivity.class);
                    add.putExtra("from_request", "add");
                    startActivity(add);
                } else if (notify_status == 1) {
                    Intent not = new Intent(MainActivity.this, NotificationListActivity.class);
                    startActivity(not);
                } else if (fragment_status == 14) {
                    Intent edit = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(edit);
                } else if (fragment_status == 2) {
                    // ();
                }*/
                break;
            case R.id.btn_menu_two:
               /* Intent not = new Intent(MainActivity.this, NotificationListActivity.class);
                startActivity(not);*/
                break;
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutNew, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                address=PrefConnect.readString(context,PrefConnect.PROFILE_LOCATION,"");
                /*if (address.trim().equals("")){
                    openUpdateProfileDialog();
                }*/

                setHeader();
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayoutNew.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();

       /* address=PrefConnect.readString(context,PrefConnect.PROFILE_LOCATION,"");
        if (address.trim().equals("")){
            openUpdateProfileDialog();
        }*/

        setHeader();

        if (GlobalMethods.isNetworkAvailable(context)){
            callUpdateCount();
        }else {
            GlobalMethods.Toast(context,getString(R.string.internet));
        }
    }

    private void callUpdateCount() {

        Log.e("NotificationCountResp", "UserId: " + user_id);
        Call<CommonResponse> call = apiService.callNotifiCount(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("NotificationCountResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp=response.body();
                    if (resp.getStatus().equals("1")){
                        if (txtNotificationCount!=null) {
                            if (resp.getUnreadNotificationCount().equals("0")) {
                                txtNotificationCount.setVisibility(View.GONE);
                            } else {
                                if (is_nitification_count_visible) {
                                    txtNotificationCount.setVisibility(View.VISIBLE);
                                }
                                txtNotificationCount.setText(resp.getUnreadNotificationCount()+"");
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("NotifiListRespFail", t.getMessage());
            }
        });
    }

    private void setHeader() {

        String first_name = PrefConnect.readString(context, PrefConnect.FIRSTNAME, "");
        String last_name = PrefConnect.readString(context, PrefConnect.LASTNAME, "");
        String email = PrefConnect.readString(context, PrefConnect.EMAIL, "");
        String profile_image = PrefConnect.readString(context, PrefConnect.IMAGE_URL, "");
        txtHeaderName.setText(first_name + " " + last_name);
        txtHeaderEmail.setText(email);
        Log.e("NavHeader",first_name+" "+last_name+" "+email+" "+profile_image);
        if (!profile_image.equals("")) {
            Glide.with(context).load(profile_image).into(imgHeader);
        }
    }

    private void setupnavigation() {

        rowItems = new ArrayList<ClassLeftDrawer>();
        for (int i = 0; i < titles.length; i++) {
            ClassLeftDrawer item = new ClassLeftDrawer(images[i], titles[i]);
            rowItems.add(item);
        }

        leftNavAdapter = new LeftNavAdapter(this,
                R.layout.activity_main_left_drawer_menu, rowItems);
        listNav.setAdapter(leftNavAdapter);
        listNav.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) {
            fragment_status = 0;
            beginTransction(new HomeFragment());
            txtTitle.setText("gigpeople");
            is_nitification_count_visible=true;
            imgMenu.setVisibility(View.VISIBLE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            notify_status = 1;

            if (GlobalMethods.isNetworkAvailable(context)){
                callUpdateCount();
            }else {
                GlobalMethods.Toast(context,getString(R.string.internet));
            }

            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }

        } else if (position == 1) {

            fragment_status = 2;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new BuyerNewRequestFragment());
            txtTitle.setText("Gig Requests");
            imgMenu.setVisibility(View.VISIBLE);
            imgMenu.setImageResource(R.drawable.filter);
            imgMenuTwo.setVisibility(View.GONE);

            header.setBackgroundResource(R.color.colorPrimary);
            editor.putString("buyer_request_index", "0");
            editor.commit();
            Window window = MainActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } else if (position == 2) {
            fragment_status = 1;
            Log.e("STATS", String.valueOf(fragment_status));
            is_nitification_count_visible=false;
            notify_status = 2;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new MyGigFragment());
            txtTitle.setText(titles[2]);
            imgMenu.setVisibility(View.VISIBLE);
            imgMenu.setImageResource(R.drawable.add_round_icon);
            imgMenuTwo.setVisibility(View.GONE);
            imgMenuTwo.setBackgroundResource(R.drawable.notification_main);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();
            editor.putString("gig_index", "0");
            editor.commit();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } else if (position == 3) {
            fragment_status = 3;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new MysaleFragment());
            txtTitle.setText(titles[3]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            editor.putString("sales_index", "0");
            editor.commit();
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } else if (position == 4) {
            fragment_status = 5;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new ManageRequestFragment());
            txtTitle.setText(titles[4]);
            imgMenu.setVisibility(View.VISIBLE);
            imgMenu.setImageResource(R.drawable.add_round_icon);
            imgMenuTwo.setVisibility(View.GONE);
            imgMenuTwo.setBackgroundResource(R.drawable.notification_main);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }

        } else if (position == 5) {
            fragment_status = 4;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new MyOrdersFragment());
            txtTitle.setText(titles[5]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }

        } else if (position == 6) {
            fragment_status = 6;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new FavouriteFragment());
            txtTitle.setText(titles[6]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }

        } /*else if (position == 7) {
            fragment_status = 7;
            notify_status=1;

            beginTransction(new ProfileFragment());
            txtTitle.setText(titles[7]);
            imgMenu.setVisibility(View.VISIBLE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        }*/ else if (position == 9) {
            fragment_status = 9;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new Settingsfragment());
            txtTitle.setText(titles[9]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } else if (position == 7) {
            fragment_status = 7;
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            beginTransction(new ChatFragment());
            txtTitle.setText(titles[7]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } else if (position == 8) {
            notify_status = 2;
            is_nitification_count_visible=false;
            txtNotificationCount.setVisibility(View.GONE);
            fragment_status = 8;
            beginTransction(new WithdrawFragment());
            txtTitle.setText(titles[8]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        } /*else if (position == 9) {
                notify_status = 2;

            fragment_status = 9;
            beginTransction(new AnalyticsFragment());
            txtTitle.setText(titles[9]);
            imgMenu.setVisibility(View.GONE);
            imgMenu.setImageResource(R.drawable.notification_main);
            imgMenuTwo.setVisibility(View.GONE);
            header.setBackgroundResource(R.color.colorPrimary);
            Window window = MainActivity.this.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
            }
        }*/ else if (position == 10) {
            openlogoutdialog();
        }
    }

    private void openlogoutdialog() {

        logout = new Dialog(MainActivity.this);
        logout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logout.setContentView(R.layout.dialog_logout);
        logout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button yes = (Button) logout.findViewById(R.id.btn_yes);
        Button no = (Button) logout.findViewById(R.id.btn_no);
        logout.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalMethods.isNetworkAvailable(context)) {
                    calllogout();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout.dismiss();
            }
        });
    }

    private void openUpdateProfileDialog() {

        profileDialog = new Dialog(MainActivity.this);
        profileDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        profileDialog.setContentView(R.layout.dialog_update_profile);
        profileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        profileDialog.setCancelable(false);
        TextView txtredirct = (TextView) profileDialog.findViewById(R.id.txtredirct);
        profileDialog.show();
        txtredirct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_status = 14;
                notify_status = 2;
                is_nitification_count_visible=false;

                beginTransction(new ProfileFragment());
                txtTitle.setText("My Profile");
                imgMenu.setVisibility(View.VISIBLE);
                imgMenu.setImageResource(R.drawable.edit_profile);
                imgMenuTwo.setVisibility(View.GONE);
                header.setBackgroundResource(R.color.colorPrimary);
                Window window = MainActivity.this.getWindow();

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                }
                profileDialog.dismiss();
            }
        });
    }

    private void calllogout() {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Call<CommonResponse> call = apiService.callLogout(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("Logout", response.body().getMessage());
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    String status = response.body().getStatus();
                    if (status.equals("1")) {
                        logout.dismiss();
                        PrefConnect.clearAllPrefs(context);
                        Intent i = new Intent(context, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void beginTransction(final Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawerLayoutNew.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
