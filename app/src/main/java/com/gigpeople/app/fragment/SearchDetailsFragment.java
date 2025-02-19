package com.gigpeople.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigpeople.app.R;
import com.gigpeople.app.subfragment.ManageRequestItemFragment;
import com.gigpeople.app.subfragment.SearchGigFragment;
import com.gigpeople.app.subfragment.SearchUserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SearchDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPagerAdapter adapter;


    public SearchDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDetailsFragment newInstance(String param1, String param2) {
        SearchDetailsFragment fragment = new SearchDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#ff7500"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        return view;
    }
    private void setupViewPager(ViewPager viewpager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new SearchUserFragment(),"User");
        adapter.addFragment( new SearchGigFragment(),"Gig");

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
            // PrefConnect.writeString(getContext(), PrefConnect.CATEGORY_ID, resp.getCategories().get(position).getCategoryId());

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
/*
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mFragmentList.remove(position);
            super.destroyItem(container, position, object);
        }*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
