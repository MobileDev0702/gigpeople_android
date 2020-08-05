package com.gigpeople.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.AnalyticsActivity;
import com.gigpeople.app.activity.BillingActivity;
import com.gigpeople.app.activity.ChangePasswordActivity;
import com.gigpeople.app.activity.HelpAndsupport_oneActivity;
import com.gigpeople.app.activity.LoginActivity;
import com.gigpeople.app.activity.NotificationListActivity;
import com.gigpeople.app.activity.PaymentHistoryActivity;
import com.gigpeople.app.activity.PrivacyPolicyActivity;
import com.gigpeople.app.activity.SecurityActivity;
import com.gigpeople.app.activity.TermsandConditionActivity;
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
 * Use the {@link Settingsfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settingsfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.paymentlayout)
    LinearLayout paymentlayout;
    @BindView(R.id.tclayout)
    LinearLayout tclayout;
    @BindView(R.id.privacylayout)
    LinearLayout privacylayout;
    @BindView(R.id.changepasswordlayout)
    LinearLayout changepasswordlayout;
    @BindView(R.id.layoutbilling)
    LinearLayout layoutbilling;
    @BindView(R.id.notification_layout)
    LinearLayout notificationLayout;
    @BindView(R.id.logout_layout)
    LinearLayout logoutLayout;
    Unbinder unbinder;
    @BindView(R.id.security_layout)
    LinearLayout securityLayout;
    Dialog logout;
    @BindView(R.id.help_layout)
    LinearLayout helpLayout;
    @BindView(R.id.analyticslayout)
    LinearLayout analyticslayout;
    @BindView(R.id.switchordernotify)
    Switch switchordernotify;
    @BindView(R.id.switchpushnotify)
    Switch switchpushnotify;
    @BindView(R.id.img_facebook)
    ImageView imgFacebook;
    @BindView(R.id.img_twitter)
    ImageView imgTwitter;
    @BindView(R.id.img_instagram)
    ImageView imgInstagram;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ApiService apiService;
    String user_id;
    Context context;
    String notificationstatus, order_status, notification_type = "", login_status;
    ProgressDialog progressDialog;
    Dialog dialoglogout;

    public Settingsfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settingsfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Settingsfragment newInstance(String param1, String param2) {
        Settingsfragment fragment = new Settingsfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitSingleton.createService(ApiService.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getContext();


        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        notificationstatus = PrefConnect.readString(context, PrefConnect.NOTIFICATION, "");
        order_status = PrefConnect.readString(context, PrefConnect.ORDER_STATUS, "");

        unbinder = ButterKnife.bind(this, view);


        if (GlobalMethods.isNetworkAvailable(context)) {
            setNotification();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }
        if (GlobalMethods.isNetworkAvailable(context)) {
            setorder();
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

    @OnClick({R.id.paymentlayout, R.id.tclayout, R.id.privacylayout, R.id.changepasswordlayout, R.id.layoutbilling, R.id.notification_layout, R.id.logout_layout, R.id.security_layout, R.id.help_layout, R.id.analyticslayout,R.id.img_facebook, R.id.img_twitter, R.id.img_instagram})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.paymentlayout:
                Intent payment = new Intent(getContext(), PaymentHistoryActivity.class);
                startActivity(payment);
                break;
            case R.id.tclayout:
                Intent tc = new Intent(getContext(), TermsandConditionActivity.class);
                startActivity(tc);
                break;
            case R.id.privacylayout:
                Intent privacy = new Intent(getContext(), PrivacyPolicyActivity.class);
                startActivity(privacy);
                break;
            case R.id.changepasswordlayout:
                Intent pwd = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(pwd);
                break;
            case R.id.layoutbilling:
                Intent billing = new Intent(getContext(), BillingActivity.class);
                startActivity(billing);
                break;
            case R.id.notification_layout:
                Intent notification = new Intent(getContext(), NotificationListActivity.class);
                startActivity(notification);
                break;
            case R.id.security_layout:
                Intent sec = new Intent(getContext(), SecurityActivity.class);
                startActivity(sec);
                break;
            case R.id.help_layout:
                Intent help = new Intent(getContext(), HelpAndsupport_oneActivity.class);
                startActivity(help);
                break;
            case R.id.analyticslayout:
                Intent analytics = new Intent(getContext(), AnalyticsActivity.class);
                analytics.putExtra("page", "1");
                startActivity(analytics);
                break;
            case R.id.logout_layout:
                AlertLogout();
                break;
            case R.id.img_facebook:
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/GigPeople/"));
                startActivity(viewIntent);
                break;
            case R.id.img_twitter:
                Intent viewIntentTwitter = new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/GigPeople"));
                startActivity(viewIntentTwitter);
                break;
            case R.id.img_instagram:
                Intent viewIntentIns = new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/gigpeople/?hl=en"));
                startActivity(viewIntentIns);
                break;
        }
    }

    private void setNotification() {
        if (notificationstatus.equalsIgnoreCase("1")) {
            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                switchpushnotify.setChecked(false);
            } else {
                switchpushnotify.setChecked(true);
            }

        } else {
            switchpushnotify.setChecked(false);
        }

        if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {

        } else {
            switchpushnotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (GlobalMethods.isNetworkAvailable(context)) {
                            notificationstatus = "1";

                            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                                switchpushnotify.setChecked(false);

                            } else {
                                callnotification(notificationstatus);
                            }

                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    } else {

                        if (GlobalMethods.isNetworkAvailable(context)) {
                            notificationstatus = "0";
                            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                                switchpushnotify.setChecked(false);

                            } else {
                                callnotification(notificationstatus);
                            }

                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    }
                }
            });
        }

    }

    private void setorder() {
        if (order_status.equalsIgnoreCase("1")) {
            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                switchordernotify.setChecked(false);
            } else {
                switchordernotify.setChecked(true);
            }
        } else {
            switchordernotify.setChecked(false);
        }

        if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {

        } else {
            switchordernotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (GlobalMethods.isNetworkAvailable(context)) {
                            order_status = "1";
                            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                                switchordernotify.setChecked(false);
                            } else {
                                callorderstatus(order_status);
                            }

                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    } else {

                        if (GlobalMethods.isNetworkAvailable(context)) {
                            order_status = "0";
                            if (user_id.equals("") || user_id.equals(null) || user_id.isEmpty()) {
                                switchordernotify.setChecked(false);

                            } else {
                                callorderstatus(order_status);
                            }

                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    }
                }
            });
        }

    }

    private void callnotification(final String status_notify) {
        Log.e("NotifyStatusUpdateReq", "UserId: " + user_id + "\nStatus: " + status_notify);
        Call<CommonResponse> call = apiService.callnotification(user_id, status_notify);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("NotifyStatusUpdateResp", new Gson().toJson(response.body()));
                CommonResponse resp = response.body();
                String status = resp.getStatus();
                if (status.equals("1")) {
                    PrefConnect.writeString(context, PrefConnect.NOTIFICATION, notificationstatus);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void callorderstatus(final String orderstatus) {
        Log.e("OrderStatusUpdateReq", "UserId: " + user_id + "\nStatus: " + orderstatus);
        Call<CommonResponse> call = apiService.callcustomerOrders(user_id, orderstatus);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("OrderStatusUpdateResp", new Gson().toJson(response.body()));
                CommonResponse resp = response.body();
                String status = resp.getStatus();
                if (status.equals("1")) {
                    PrefConnect.writeString(context, PrefConnect.ORDER_STATUS, order_status);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void AlertLogout() {

        dialoglogout = new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (GlobalMethods.isNetworkAvailable(context)) {
                            calllogout(user_id);
                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void calllogout(String user_id) {
        Log.e("LogoutReq", "UserId: " + user_id);
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Call<CommonResponse> call = apiService.callLogout(user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("LogoutResp", response.body().getMessage());
                progressDialog.dismiss();
                dialoglogout.dismiss();
                if (response.isSuccessful()) {

                    String status = response.body().getStatus();
                    if (status.equals("1")) {
                        PrefConnect.clearAllPrefs(getContext());
                        Intent i = new Intent(getContext(), LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
                dialoglogout.dismiss();
            }
        });
    }

}
