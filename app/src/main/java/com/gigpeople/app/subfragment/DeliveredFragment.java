package com.gigpeople.app.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.MyOrdersAdapter;
import com.gigpeople.app.adapter.MyOrdersDeliveredAdapter;
import com.gigpeople.app.apiModel.MyOrdersListResponse;
import com.gigpeople.app.model.MainCategory;
import com.gigpeople.app.utils.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DeliveredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveredFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyOrdersDeliveredAdapter myOrdersAdapter;
    List<MyOrdersListResponse.DeliveredOrderList> mData;

    public DeliveredFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveredFragment newInstance(String param1, String param2) {
        DeliveredFragment fragment = new DeliveredFragment();
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
        View view = inflater.inflate(R.layout.fragment_delivered, container, false);
        unbinder = ButterKnife.bind(this, view);
        mData = new ArrayList<>();

        mData = GlobalVariables.MyorderDeliveryListpage;

        myOrdersAdapter = new MyOrdersDeliveredAdapter(getContext(), mData);
        recycleView.setAdapter(myOrdersAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
