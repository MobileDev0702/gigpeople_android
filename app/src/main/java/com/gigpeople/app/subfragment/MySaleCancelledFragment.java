package com.gigpeople.app.subfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.MyCancelledSaleAdapter;
import com.gigpeople.app.adapter.MyCompletedSaleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.gigpeople.app.fragment.MysaleFragment.cancelledOrderLists;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MySaleCancelledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySaleCancelledFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;
    MyCancelledSaleAdapter mysaleAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MySaleCancelledFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MysaleItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MySaleCancelledFragment newInstance(String param1) {
        MySaleCancelledFragment fragment = new MySaleCancelledFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        View view = inflater.inflate(R.layout.fragment_mysalecancelled_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }

        if (recycleView != null) {

            mysaleAdapter = new MyCancelledSaleAdapter(getContext(),cancelledOrderLists);
            recycleView.setAdapter(mysaleAdapter);
            recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        }



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
