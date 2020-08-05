package com.gigpeople.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.AddNewRequestActivity;
import com.gigpeople.app.adapter.ManageRequestAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.RequestListResponse;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ManageRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageRequestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ManageRequestAdapter manageRequestAdapter;
    Context context;
    ApiService apiService;
    String user_id,address;
    List<RequestListResponse.RequestList> request_list;
    MainActivity mainActivity;
    Dialog profileDialog;

    public ManageRequestFragment() {
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
    public static ManageRequestFragment newInstance(String param1, String param2) {
        ManageRequestFragment fragment = new ManageRequestFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_request_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        context=getContext();
        apiService= RetrofitSingleton.createService(ApiService.class);
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");

        mainActivity.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address=PrefConnect.readString(context,PrefConnect.PROFILE_LOCATION,"");
                if (address.trim().equals("")){
                    openUpdateProfileDialog();
                }else {
                    Intent add = new Intent(getContext(), AddNewRequestActivity.class);
                    add.putExtra("from_request", "add");
                    startActivity(add);
                }
            }
        });

        return view;
    }

    private void setRequestList() {
        Log.e("RequestListReq","UserId: "+user_id);
        Call<RequestListResponse> call=apiService.callRequestListAPI(user_id);
        call.enqueue(new Callback<RequestListResponse>() {
            @Override
            public void onResponse(Call<RequestListResponse> call, Response<RequestListResponse> response) {
                Log.e("RequestListResp",new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    RequestListResponse resp=response.body();
                    if (resp!=null){
                        if (resp.getStatus().equals("1")){
                            request_list=new ArrayList<>();
                            request_list=resp.getRequestList();

                            if (recycleView!=null) {

                                manageRequestAdapter = new ManageRequestAdapter(context, request_list);
                                recycleView.setAdapter(manageRequestAdapter);
                                recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


        if (GlobalMethods.isNetworkAvailable(context)){
            setRequestList();
        }else {
            GlobalMethods.Toast(context,getString(R.string.internet));
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
