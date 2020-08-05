package com.gigpeople.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.CategoryAdapter;
import com.gigpeople.app.apiModel.DashBoardResponse;
import com.gigpeople.app.subfragment.ActiveFragment;
import com.gigpeople.app.subfragment.BuyreqnewofferFragment;
import com.gigpeople.app.subfragment.BuyreqsendofferFragment;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.gigpeople.app.subfragment.BuyreqnewofferFragment.tocall_NewRequest;
import static com.gigpeople.app.subfragment.BuyreqsendofferFragment.tocall_NewRequestSentTabTwo;

public class BuyerNewRequestFragment extends Fragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    MainActivity mainActivity;
    ViewPagerAdapter adapter;
    String gig;
    Dialog filter_dialog;

    public static String filterLocation = "", filterLat = "", filterLong = "", category_id = "", search = "";
    public static String maiCategoryId = "", subCategoryId = "";

    List<DashBoardResponse.MainCategoryList> category_list;
    CategoryAdapter categoryAdapter;
    List<DashBoardResponse.MainCategoryList.SubCategory> subCategoryList;

    public BuyerNewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buyer_new_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        pref = getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        gig = pref.getString("buyer_request_index", "");
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));

        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);

        PrefConnect.writeString(getContext(), PrefConnect.tabselection, "0");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String position = String.valueOf(tab.getPosition());
                Log.e("position", position + "::");
                PrefConnect.writeString(getContext(), PrefConnect.tabselection, position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (gig.equalsIgnoreCase("1")) {
            TabLayout.Tab tab = tabs.getTabAt(1);
            tab.select();
        } else if (gig.equalsIgnoreCase("0")) {
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
        }
        openfilterdialog();
        mainActivity.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterLocation = "";
                filterLat = "";
                filterLong = "";
                category_id = "";
                search = "";
                maiCategoryId = "";
                subCategoryId = "";
                filter_dialog.show();
               // openfilterdialog();
            }
        });

        return view;
    }

    private void openfilterdialog() {

        filterLocation = "";
        filterLat = "";
        filterLong = "";
        category_id = "";
        search = "";
        maiCategoryId = "";
        subCategoryId = "";

        filter_dialog = new Dialog(getContext());
        filter_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filter_dialog.setContentView(R.layout.dialog_filter_browserequest);
        filter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //filter_dialog.show();
        ImageView close = (ImageView) filter_dialog.findViewById(R.id.img_close);
        Button submit = (Button) filter_dialog.findViewById(R.id.btn_submit);
        LinearLayout location = (LinearLayout) filter_dialog.findViewById(R.id.location_layout);
        RecyclerView recyclerView = (RecyclerView) filter_dialog.findViewById(R.id.recycler);
        final PlacesAutocompleteTextView placesAutocompleteTextView = (PlacesAutocompleteTextView) filter_dialog.findViewById(R.id.places_autocomplete_address);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_dialog.dismiss();
            }
        });
        category_list = new ArrayList<>();
        category_list = GlobalVariables.dashmainCategorylist;

        for (int i = 0; i < category_list.size(); i++) {

            category_list.get(i).setStatus("0");
            subCategoryList = new ArrayList<>();
            subCategoryList = category_list.get(i).getSubCategory();

            for (int j = 0; j < subCategoryList.size(); j++) {
                subCategoryList.get(j).setStatus("0");
            }
        }
        categoryAdapter = new CategoryAdapter(getContext(), category_list);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < category_list.size(); i++) {
                    subCategoryList = new ArrayList<>();
                    subCategoryList = category_list.get(i).getSubCategory();

                    for (int j = 0; j < subCategoryList.size(); j++) {

                        if (subCategoryList.get(j).getStatus().equals("1")&&category_list.get(i).getStatus().equals("1")) {
                            if (subCategoryId.equals("")){
                                subCategoryId = subCategoryList.get(j).getSubCategoryId();
                            }else {
                                subCategoryId += ","+subCategoryList.get(j).getSubCategoryId();
                            }
                        }
                    }
                    if (category_list.get(i).getStatus().equals("1")) {
                        if (maiCategoryId.equals("")) {
                            maiCategoryId += category_list.get(i).getMainCategoryId() ;
                        }else {
                            maiCategoryId += "," +category_list.get(i).getMainCategoryId() ;
                        }
                    }
                }
                filter_dialog.dismiss();

                if (GlobalMethods.isNetworkAvailable(getContext())) {
                    Log.e("GigRequestReq113455", "UserId: " + "\nSearch: " + search + "\nLocation: " + filterLocation + "\nLat: " + filterLat + "\nLong: " + filterLong + "\nMainCategory: " + maiCategoryId + "\nSubCategory: " + subCategoryId);
                    String tabselection = PrefConnect.readString(getContext(), PrefConnect.tabselection, "0");

                    if (tabselection.equalsIgnoreCase("1")) {
                        tocall_NewRequestSentTabTwo();
                    } else if (tabselection.equalsIgnoreCase("0")) {
                        tocall_NewRequest();
                    }

                } else {
                    GlobalMethods.Toast(getContext(), getString(R.string.internet));
                }

            }
        });


        placesAutocompleteTextView.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place
                        placesAutocompleteTextView.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(final PlaceDetails details) {
                                filterLocation = details.name;
                                filterLat = String.valueOf(details.geometry.location.lat);
                                filterLong = String.valueOf(details.geometry.location.lng);
                            }

                            @Override
                            public void onFailure(final Throwable failure) {
                            }
                        });
                    }


                }
        );

        placesAutocompleteTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {

                filterLocation = "";
                filterLat = "";
                filterLong = "";
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(BuyreqnewofferFragment.newInstance("1"), "New");
        adapter.addFragment(BuyreqsendofferFragment.newInstance("2"), "Offers Sent");
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
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragmentList.remove(position);
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
