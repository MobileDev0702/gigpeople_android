package com.gigpeople.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.WithdrawalActivity;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link WithdrawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WithdrawFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;
    @BindView(R.id.txt_amount)
    TextView txtAmount;
    ProgressDialog progressDialog;
    Context context;
    String user_id;
    ApiService apiService;
    Double amount_earned=0.0;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WithdrawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WithdrawFragment newInstance(String param1, String param2) {
        WithdrawFragment fragment = new WithdrawFragment();
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
        View view = inflater.inflate(R.layout.fragment_withdraw, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = RetrofitSingleton.createService(ApiService.class);
        context = getContext();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        if (GlobalMethods.isNetworkAvailable(context)) {
            setAmount();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_withdraw)
    public void onViewClicked() {

        /*Intent draw = new Intent(getContext(), WithdrawalActivity.class);
        startActivity(draw);*/

        if (GlobalMethods.isNetworkAvailable(context)) {
            if (amount_earned==0.0){
                GlobalMethods.Toast(context,"No amount to withdraw");
            }else {
                withdraw();
            }
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }
    }

    private void setAmount() {
        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e("OrderCancelReq", "UserId: " + user_id);
        Call<CommonResponse> call = apiService.callwalletList(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                //progressDialog.dismiss();
                Log.e("OrderCancelResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            //GlobalMethods.Toast(context, resp.getMessage());
                            txtAmount.setText("$" + resp.getAmount_earned());
                            amount_earned=Double.parseDouble(resp.getAmount_earned());
                            if (amount_earned==0.0){
                                btnWithdraw.setVisibility(View.GONE);
                            }else {
                                btnWithdraw.setVisibility(View.VISIBLE);
                            }
                        } else {
                            //GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                //progressDialog.dismiss();
            }
        });
    }

    private void withdraw() {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("WithdrawReq", "UserId: " + user_id);
        Call<CommonResponse> call = apiService.callwalletList(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("WithdrawResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());

                            if (GlobalMethods.isNetworkAvailable(context)) {
                                setAmount();
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }


                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("WithdrawRespFail",t.getMessage());
            }
        });
    }
}
