package com.gigpeople.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.AddNewGigActivity;
import com.gigpeople.app.activity.GigDetailsActivity;
import com.gigpeople.app.activity.UpcomingSalesDetailsActivity;
import com.gigpeople.app.adapter.ManageRequestAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.GiGListResponse;
import com.gigpeople.app.apiModel.RequestListResponse;
import com.gigpeople.app.subfragment.ActiveFragment;
import com.gigpeople.app.subfragment.GigActiveFragment;
import com.gigpeople.app.subfragment.GigDeniedFragment;
import com.gigpeople.app.subfragment.GigDraftFragment;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MyGigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyGigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    ViewPagerAdapter adapter;
    String gig_index;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ApiService apiService;
    public static List<GiGListResponse.AllGigList> allGigLists;
    public static List<GiGListResponse.DeniedGigList> deniedGigLists;
    public static List<GiGListResponse.DraftlGigList> draftlGigLists;
    public static List<GiGListResponse.PublishGigList> publishGigLists;
    String user_id;

    MainActivity mainActivity;
    String address;
    Dialog profileDialog;
    Context context;

    public MyGigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyGigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyGigFragment newInstance(String param1, String param2) {
        MyGigFragment fragment = new MyGigFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_gig, container, false);
        unbinder = ButterKnife.bind(this, view);
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        context=getContext();
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        user_id = PrefConnect.readString(getContext(), PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);
        gig_index = pref.getString("gig_index", "");

        mainActivity.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address=PrefConnect.readString(context,PrefConnect.PROFILE_LOCATION,"");
                if (address.trim().equals("")){
                    openUpdateProfileDialog();
                }else {
                    Intent add = new Intent(getContext(), AddNewGigActivity.class);
                    startActivity(add);
                }
            }
        });

        if (GlobalMethods.isNetworkAvailable(getContext())) {
            setGIGList();
        } else {
            GlobalMethods.Toast(getContext(), getString(R.string.internet));
        }

        String gig_id= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_GIG_ID,"");
        String gig_status= PrefConnect.readString(getApplicationContext(),PrefConnect.FCM_GIG_STATUS,"");
        Log.e("FCMGig","ID: "+gig_id+" STATUS: "+gig_status);

        if (!gig_id.equals("")){

            Log.e("FCMGig","ID: "+gig_id+" STATUS: "+gig_status);

            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_GIG_ID,"");
            PrefConnect.writeString(getApplicationContext(),PrefConnect.FCM_GIG_STATUS,"");

            if (gig_status.equals("1")) {
                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "1");
                share.putExtra("gig_id",gig_id);
                share.putExtra("position", 0);
                share.putExtra("from", "gig_draft");
                context.startActivity(share);

            } else if (gig_status.equals("2")) {
                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "0");
                share.putExtra("gig_id", gig_id);
                share.putExtra("position", 0);
                share.putExtra("from", "gig_active");
                context.startActivity(share);

            } else if (gig_status.equals("3")) {

                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "0");
                share.putExtra("gig_id",gig_id);
                share.putExtra("position", 0);
                share.putExtra("from", "gig_denied");
                context.startActivity(share);

            }else if (gig_status.equals("0")) {

                Intent share = new Intent(context, GigDetailsActivity.class);
                share.putExtra("page", "0");
                share.putExtra("gig_id",gig_id);
                share.putExtra("position", 0);
                share.putExtra("from", "gig_wait");
                context.startActivity(share);
            }
        }

        return view;
    }

    private void setGIGList() {
        Log.e("RequestListReq", "UserId: " + user_id);
        Call<GiGListResponse> call = apiService.callGigList(user_id);
        call.enqueue(new Callback<GiGListResponse>() {
            @Override
            public void onResponse(Call<GiGListResponse> call, Response<GiGListResponse> response) {
                Log.e("RequestListResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GiGListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            allGigLists = new ArrayList<>();
                            allGigLists = resp.getAllGigList();

                            draftlGigLists = new ArrayList<>();
                            draftlGigLists = resp.getDraftlGigList();

                            deniedGigLists = new ArrayList<>();
                            deniedGigLists = resp.getDeniedGigList();

                            publishGigLists = new ArrayList<>();
                            publishGigLists = resp.getPublishGigList();

                            if (viewpager != null) {
                                setupViewPager(viewpager);
                                tabs.setupWithViewPager(viewpager);
                            }

                            if (gig_index.equalsIgnoreCase("2")) {
                                TabLayout.Tab tab = tabs.getTabAt(2);
                                tab.select();
                            } else if (gig_index.equalsIgnoreCase("1")) {
                                TabLayout.Tab tab = tabs.getTabAt(1);
                                tab.select();
                            } else if (gig_index.equalsIgnoreCase("0")) {
                                TabLayout.Tab tab = tabs.getTabAt(0);
                                tab.select();
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GiGListResponse> call, Throwable t) {

            }
        });
    }

    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveFragment(), "All");
        adapter.addFragment(new GigActiveFragment(), "Active");
        adapter.addFragment(new GigDraftFragment(), "Draft/Paused");
        adapter.addFragment(new GigDeniedFragment(), "Denied");
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

    private void openUpdateProfileDialog() {

        profileDialog = new Dialog(context);
        profileDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        profileDialog.setContentView(R.layout.dialog_update_profile);
        profileDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        profileDialog.setCancelable(true);
        TextView txtredirct = (TextView) profileDialog.findViewById(R.id.txtredirct);
        profileDialog.show();
        txtredirct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("page", "7");
                startActivity(intent1);

                profileDialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
