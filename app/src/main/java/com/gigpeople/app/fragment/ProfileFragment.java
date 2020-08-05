package com.gigpeople.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.activity.EditProfileActivity;
import com.gigpeople.app.activity.ReviewListActivity;
import com.gigpeople.app.adapter.BuyerReviewAdapter;
import com.gigpeople.app.adapter.UserReviewAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.ProfileViewResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.switch_status)
    Switch switchStatus;
    @BindView(R.id.btn_editprofile)
    Button btnEditprofile;
    Unbinder unbinder;
    int switch_status = 1;
    @BindView(R.id.txt_verifi_email)
    TextView txtVerifiEmail;
    @BindView(R.id.txt_verifi_mobile)
    TextView txtVerifiMobile;
    Dialog verificationdialog, emailverifydialog;
    @BindView(R.id.txt_verified_emaill)
    TextView txtVerifiedEmaill;
    @BindView(R.id.txt_verified_mobile)
    TextView txtVerifiedMobile;
    @BindView(R.id.buyer)
    TextView buyer;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.buyer_layout)
    LinearLayout buyerLayout;
    @BindView(R.id.seller)
    TextView seller;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.seller_layout)
    LinearLayout sellerLayout;
    @BindView(R.id.recycler_workhistory)
    RecyclerView recyclerWorkhistory;
    @BindView(R.id.recycler_review)
    RecyclerView recyclerReview;
    UserReviewAdapter adapter;
    List<ChatModel> reviwelist;
    @BindView(R.id.btn_morereviews)
    Button btnMorereviews;
    private static final String TAG = ProfileFragment.class.getSimpleName();
    ApiService apiService;
    Context context;
    String user_id;
    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_first_name)
    TextView txtFirstName;
    @BindView(R.id.txt_lat_name)
    TextView txtLatName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_mobile)
    TextView txtMobile;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    @BindView(R.id.txt_wallet)
    TextView txtWallet;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    ProgressDialog progressDialog;
    String mobile_number = "", email = "", is_mobile_verified = "0", is_email_verified = "0", mobile_country_code = "", email_otp = "", mobile_otp = "", enter_email_otp = "", enter_mobile_otp = "";
    @BindView(R.id.txt_languages_known)
    TextView txtLanguagesKnown;
    @BindView(R.id.txt_skills)
    TextView txtSkills;
    List<ProfileViewResponse.BuyerReview> buyerReviewList;
    List<ProfileViewResponse.SellerReview> sellerReviewList;
    BuyerReviewAdapter buyerReviewAdapter;
    String review_list_from = "1";
    int buyer_review_count, seller_review_count;
    @BindView(R.id.txt_gig_post)
    TextView txtGigPost;
    @BindView(R.id.txt_reviews)
    TextView txtReviews;
    MainActivity mainActivity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = RetrofitSingleton.createService(ApiService.class);
        context = getContext();
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        mainActivity.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getContext(), EditProfileActivity.class);
                startActivity(edit);
            }
        });

        buyerReviewList = new ArrayList<>();
        sellerReviewList = new ArrayList<>();

        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Log.e("ISCHECKED", String.valueOf(isChecked));
                    txtStatus.setText("Online");
                    txtStatus.setTextColor(getResources().getColor(R.color.colorGreen));
                    switch_status = 0;

                } else {
                    txtStatus.setText("Offline");
                    txtStatus.setTextColor(getResources().getColor(R.color.colorRed));
                    switch_status = 1;
                    Log.e("ISCHECKED", String.valueOf(switch_status));
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GlobalMethods.isNetworkAvailable(context)) {
            setProfile();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_status, R.id.switch_status, R.id.btn_editprofile, R.id.txt_verifi_email, R.id.txt_verifi_mobile, R.id.buyer_layout, R.id.seller_layout, R.id.btn_morereviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_status:
                break;
            case R.id.switch_status:
                break;
            case R.id.btn_editprofile:
                Intent edit = new Intent(getContext(), EditProfileActivity.class);
                startActivity(edit);
                break;
            case R.id.txt_verifi_email:
                if (is_email_verified.equals("0")) {
                    openotpemaildialog();
                }
                break;
            case R.id.txt_verifi_mobile:
                if (mobile_number.equals("")) {
                    GlobalMethods.Toast(context, "Update mobile number");
                } else if (is_mobile_verified.equals("0")) {
                    openotpmobiledialog();
                }
                break;
            case R.id.buyer_layout:
                review_list_from = "1";

                if (buyer_review_count > 2) {
                    btnMorereviews.setVisibility(View.VISIBLE);
                } else {
                    btnMorereviews.setVisibility(View.GONE);
                }
                buyer.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                seller.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

                view1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.VISIBLE);
                recyclerReview.setVisibility(View.GONE);

                buyerReviewAdapter = new BuyerReviewAdapter(getContext(), buyerReviewList);
                recyclerWorkhistory.setAdapter(buyerReviewAdapter);
                recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getContext()));

                break;
            case R.id.seller_layout:
                review_list_from = "2";

                if (seller_review_count > 2) {

                    btnMorereviews.setVisibility(View.VISIBLE);
                } else {
                    btnMorereviews.setVisibility(View.GONE);
                }
                seller.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                buyer.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

                view2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                recyclerWorkhistory.setVisibility(View.GONE);
                recyclerReview.setVisibility(View.VISIBLE);

                adapter = new UserReviewAdapter(getContext(), sellerReviewList);
                recyclerReview.setAdapter(adapter);
                recyclerReview.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case R.id.btn_morereviews:
                Intent intent5 = new Intent(getContext(), ReviewListActivity.class);
                intent5.putExtra("review_list_from", review_list_from);
                startActivity(intent5);
                break;
        }
    }

    private void setProfile() {

        Log.e(TAG, "ProfileReq: " + "\nUserId: " + user_id);
        Call<ProfileViewResponse> call = apiService.callProfileAPI(user_id);
        call.enqueue(new Callback<ProfileViewResponse>() {
            @Override
            public void onResponse(Call<ProfileViewResponse> call, Response<ProfileViewResponse> response) {
                Log.e(TAG, "ProfileResp: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    ProfileViewResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            if (!resp.getUserDetails().getProfileImageUrl().equals("")) {
                                Glide.with(context).load(resp.getUserDetails().getProfileImageUrl()).into(imgProfile);
                            }
                            email = resp.getUserDetails().getEmail();
                            mobile_number = resp.getUserDetails().getPhoneNo();
                            mobile_country_code = resp.getUserDetails().getMobileCountry();
                            is_email_verified = resp.getUserDetails().getIsEmailVerified();
                            is_mobile_verified = resp.getUserDetails().getIsMobileVerified();
                            email_otp = resp.getUserDetails().getEmailOtp();
                            mobile_otp = resp.getUserDetails().getMobileOtp();
                            txtName.setText(resp.getUserDetails().getFirstName() + " " + resp.getUserDetails().getLastName());
                            txtFirstName.setText(resp.getUserDetails().getFirstName());
                            txtLatName.setText(resp.getUserDetails().getLastName());
                            txtEmail.setText(email);
                            txtSkills.setText(resp.getUserDetails().getSkills());
                            if (!resp.getUserDetails().getPhoneNo().equals("")) {
                                txtMobile.setText("+" + mobile_country_code + " " + mobile_number);
                            }
                            txtAbout.setText(resp.getUserDetails().getAbout());
                            txtWallet.setText("$" + resp.getUserDetails().getWallet());
                            txtLanguagesKnown.setText(resp.getUserDetails().getLanguage());
                            txtGigPost.setText(resp.getUserDetails().getNoOfGigpost());
                            txtReviews.setText(resp.getUserDetails().getNoOfReviews());

                            if (is_email_verified.equals("1")) {
                                txtVerifiedEmaill.setVisibility(View.VISIBLE);
                                txtVerifiEmail.setVisibility(View.GONE);
                            }

                            if (is_mobile_verified.equals("1")) {
                                txtVerifiedMobile.setVisibility(View.VISIBLE);
                                txtVerifiMobile.setVisibility(View.GONE);
                            }

                            buyer_review_count = Integer.parseInt(resp.getBuyerReviewcount());
                            seller_review_count = Integer.parseInt(resp.getSellerReviewcount());

                            buyerReviewList = resp.getBuyerReviews();
                            sellerReviewList = resp.getSellerReviews();

                            recyclerWorkhistory.setVisibility(View.VISIBLE);
                            recyclerReview.setVisibility(View.GONE);
                            buyerReviewAdapter = new BuyerReviewAdapter(getContext(), buyerReviewList);
                            recyclerWorkhistory.setAdapter(buyerReviewAdapter);
                            recyclerWorkhistory.setLayoutManager(new LinearLayoutManager(getContext()));

                            if (buyer_review_count > 2) {

                                btnMorereviews.setVisibility(View.VISIBLE);
                            } else {
                                btnMorereviews.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileViewResponse> call, Throwable t) {

            }
        });
    }

    private void setEmailResendOtp() {
        /*progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        Log.e(TAG, "EmailResendOtpReq: " + "\nUserId: " + user_id + "\nEmailId: " + email);
        Call<CommonResponse> call = apiService.callResendEmailOtpAPI(user_id, email);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e(TAG, "EmailResendOtpResp: " + new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage() + " " + resp.getEmailOtp());
                            email_otp = resp.getEmailOtp();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
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

    private void setMobileResendOtp() {
       /* progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);*/
        String monilr_num = "";
        monilr_num = mobile_country_code + " " + mobile_number;
        Log.e(TAG, "MobileResendOtpReq: " + "\nUserId: " + user_id + "\nMobileNumber: " + monilr_num);
        Call<CommonResponse> call = apiService.callResendMobileOtpAPI(user_id, monilr_num);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e(TAG, "MobileResendOtpResp: " + new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage() + " " + resp.getMobileOtp());
                            mobile_otp = resp.getMobileOtp();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                // progressDialog.dismiss();
            }
        });
    }

    private void setVerifyOtp(final String type) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e(TAG, "VerifyReq: " + "\nUserId: " + user_id + "\nType: " + type);
        Call<CommonResponse> call = apiService.callVerifyAPI(user_id, type);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e(TAG, "VerifyResp: " + new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            if (type.equals("2")) {
                                emailverifydialog.dismiss();
                                txtVerifiedEmaill.setVisibility(View.VISIBLE);
                                txtVerifiEmail.setVisibility(View.GONE);
                            } else if (type.equals("1")) {
                                verificationdialog.dismiss();
                                txtVerifiedMobile.setVisibility(View.VISIBLE);
                                txtVerifiMobile.setVisibility(View.GONE);
                            }
                        } else {

                            if (type.equals("2")) {
                                emailverifydialog.dismiss();
                            } else if (type.equals("1")) {
                                verificationdialog.dismiss();
                            }

                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void openotpemaildialog() {

        emailverifydialog = new Dialog(getContext());
        emailverifydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        emailverifydialog.setContentView(R.layout.dialog_emailotp_verification);
        emailverifydialog.setCancelable(true);
        emailverifydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailverifydialog.show();
        Button submit = (Button) emailverifydialog.findViewById(R.id.btn_dialog_submit);
        final EditText textone = (EditText) emailverifydialog.findViewById(R.id.edt_one);
        final EditText texttwo = (EditText) emailverifydialog.findViewById(R.id.edt_two);
        final EditText textthree = (EditText) emailverifydialog.findViewById(R.id.edt_three);
        final EditText textfour = (EditText) emailverifydialog.findViewById(R.id.edt_four);
        final TextView mobile = (TextView) emailverifydialog.findViewById(R.id.txt_no);
        final ImageView close = (ImageView) emailverifydialog.findViewById(R.id.img_close);
        final TextView txt_resend = (TextView) emailverifydialog.findViewById(R.id.txt_resend);
        mobile.setText(email);

        GlobalMethods.Toast(context, email_otp);

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setEmailResendOtp();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String one = "", two = "", three = "", four = "";
                one = textone.getText().toString().trim();
                two = texttwo.getText().toString().trim();
                three = textthree.getText().toString().trim();
                four = textfour.getText().toString().trim();
                enter_email_otp = one + two + three + four;
                if (one.equals("") || two.equals("") || three.equals("") || four.equals("")) {
                    GlobalMethods.Toast(context, "Enter OTP");
                } else if (!email_otp.equals(enter_email_otp)) {
                    GlobalMethods.Toast(context, "Enter valid OTP");
                } else {
                    setVerifyOtp("2");
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailverifydialog.dismiss();
            }
        });

        textone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    texttwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        texttwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    textthree.requestFocus();
                } else if (s.length() == 0) {
                    textone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textthree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    textfour.requestFocus();
                } else if (s.length() == 0) {
                    texttwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textfour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    textthree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void openotpmobiledialog() {
        verificationdialog = new Dialog(getContext());
        verificationdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verificationdialog.setContentView(R.layout.dialog_otp_verification);
        verificationdialog.setCancelable(true);
        verificationdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        verificationdialog.show();
        Button submit = (Button) verificationdialog.findViewById(R.id.btn_dialog_submit);
        final EditText textone = (EditText) verificationdialog.findViewById(R.id.edt_one);
        final EditText texttwo = (EditText) verificationdialog.findViewById(R.id.edt_two);
        final EditText textthree = (EditText) verificationdialog.findViewById(R.id.edt_three);
        final EditText textfour = (EditText) verificationdialog.findViewById(R.id.edt_four);
        final TextView mobile = (TextView) verificationdialog.findViewById(R.id.txt_no);
        final ImageView close = (ImageView) verificationdialog.findViewById(R.id.img_close);
        final TextView txt_resend = (TextView) verificationdialog.findViewById(R.id.txt_resend);
        mobile.setText("+" + mobile_country_code + " " + mobile_number);
        GlobalMethods.Toast(context, mobile_otp);

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMethods.isNetworkAvailable(context)) {
                    setMobileResendOtp();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String one = "", two = "", three = "", four = "";
                one = textone.getText().toString().trim();
                two = texttwo.getText().toString().trim();
                three = textthree.getText().toString().trim();
                four = textfour.getText().toString().trim();
                enter_mobile_otp = one + two + three + four;
                if (one.equals("") || two.equals("") || three.equals("") || four.equals("")) {
                    GlobalMethods.Toast(context, "Enter OTP");
                } else if (!mobile_otp.equals(enter_mobile_otp)) {
                    GlobalMethods.Toast(context, "Enter valid OTP");
                } else {
                    setVerifyOtp("1");
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationdialog.dismiss();
            }
        });

        textone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    texttwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        texttwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    textthree.requestFocus();
                } else if (s.length() == 0) {
                    textone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textthree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    textfour.requestFocus();
                } else if (s.length() == 0) {
                    texttwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textfour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    textthree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
