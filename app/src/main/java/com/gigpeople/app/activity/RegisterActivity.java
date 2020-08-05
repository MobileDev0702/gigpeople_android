package com.gigpeople.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.LoginResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.edt_lastname)
    EditText edtLastname;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.terms)
    TextView terms;
    @BindView(R.id.privacy)
    TextView privacy;
    @BindView(R.id.cpatchacheckbox)
    CheckBox captchacheckbox;
    @BindView(R.id.edt_firstname)
    EditText edtFirstname;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.view1)
    View view1;
    CheckBox cpatchacheckbox;
    private static final String SAFETY_NET_API_SITE_KEY = "6Lf8z0sUAAAAAP80KqD1U-3e7M_JlOrgWSms5XDd";
    private static final String URL_VERIFY_ON_SERVER = "https://www.google.com/recaptcha/api/siteverify";

    ApiService apiService;
    ProgressDialog progressDialog;
    String first_Name, last_Name, email, password, con_Password, device_Type = "Android", device_Token = "1222222";

    private static final String TAG = RegisterActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        context = RegisterActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);

        try {
            device_Token = FirebaseInstanceId.getInstance().getToken();
            Log.d("devicetoken", device_Token+"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Window window = RegisterActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimaryDark));
        }
    }

    @OnClick({R.id.cpatchacheckbox,R.id.btn_signup, R.id.txt_login, R.id.terms, R.id.privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cpatchacheckbox:
                if (GlobalMethods.isNetworkAvailable(context)){
                    recaptcha();
                }else {
                    GlobalMethods.Toast(context,getString(R.string.internet));
                }
                break;
            case R.id.btn_signup:
                first_Name = edtFirstname.getText().toString().trim();
                last_Name = edtLastname.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                con_Password = edtConfirmPassword.getText().toString().trim();

                if (GlobalMethods.isNetworkAvailable(context)){
                    if (validation()) {
                        to_Click_Signup(first_Name, last_Name, email, password, device_Type, device_Token);
                    }
                }else {
                    GlobalMethods.Toast(context,getString(R.string.internet));
                }
                break;
            case R.id.txt_login:
                Intent main1 = new Intent(context, LoginActivity.class);
                startActivity(main1);
                break;
            case R.id.terms:
                Intent terms = new Intent(context, TermsandConditionActivity.class);
                startActivity(terms);
                break;
            case R.id.privacy:
                Intent privacy = new Intent(context, PrivacyPolicyActivity.class);
                startActivity(privacy);
                break;
        }
    }

    private boolean validation() {

        if (first_Name.equals("")) {
            GlobalMethods.Toast(context, "Enter First Name");
            return false;
        }else if (last_Name.equals("")) {
            GlobalMethods.Toast(context, "Enter Last Name");
            return false;
        }else if (email.equals("")) {
            GlobalMethods.Toast(context, "Enter Email Id");
            return false;
        } else if (!Pattern.compile(GlobalMethods.EMAIL_PATTERN).matcher(email).matches()) {
            GlobalMethods.Toast(context, "Enter valid emailId");
            return false;
        } else if (password.equals("")) {
            GlobalMethods.Toast(context, "Enter Password");
            return false;
        } else if (con_Password.equals("")) {
            GlobalMethods.Toast(context, "Enter Confirm Password");
            return false;
        } else if (!con_Password.equals(password)) {
            GlobalMethods.Toast(context, "Password doesn't match");
            return false;
        } else if (!captchacheckbox.isChecked()) {
            GlobalMethods.Toast(context, "Verify Recaptcha");
            return false;
        }

        return true;
    }

    /**
     * to call Sign up
     *
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param devicetype
     * @param devicetoken
     */

    private void to_Click_Signup(String firstname, String lastname, String email, String password, String devicetype, String devicetoken) {

        if (devicetoken==null || devicetoken.equals("null")){
            try {
                devicetoken = FirebaseInstanceId.getInstance().getToken();
                Log.e("Token",devicetoken+"");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TokenError",e.getMessage());
            }
        }
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e(TAG,"SignupResp: "+"\nFirstName: "+firstname+"\nLastName: "+lastname+"\nEmail: "+email+"\nPassword: "+password+"\nDeviceType: "+devicetype+"\nDeviceToken: "+devicetoken);
        Call<LoginResponse> call = apiService.callSignUpAPI(firstname, lastname, email, password, devicetype, devicetoken.trim());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG,"SignupResp: "+ new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        LoginResponse signUpResponse;
                        GlobalMethods.Toast(context,response.body().getMessage());
                        signUpResponse = response.body();
                        PrefConnect.writeString(context, PrefConnect.USER_ID, signUpResponse.getUserDetails().getUserId());
                        PrefConnect.writeString(context, PrefConnect.FIRSTNAME, signUpResponse.getUserDetails().getFirstName());
                        PrefConnect.writeString(context, PrefConnect.LASTNAME, signUpResponse.getUserDetails().getLastName());
                        PrefConnect.writeString(context, PrefConnect.EMAIL, signUpResponse.getUserDetails().getEmail());
                        PrefConnect.writeString(context, PrefConnect.NOTIFICATION, signUpResponse.getUserDetails().getNotification());
                        PrefConnect.writeString(context, PrefConnect.ORDER_STATUS, signUpResponse.getUserDetails().getCustomerOrder());

                        PrefConnect.writeString(context, PrefConnect.PROFILE_LOCATION, signUpResponse.getUserDetails().getAddress());
                        PrefConnect.writeString(context, PrefConnect.PROFILE_LAT, signUpResponse.getUserDetails().getLattitude());
                        PrefConnect.writeString(context, PrefConnect.PROFILE_LONG, signUpResponse.getUserDetails().getLongitude());

                        Intent main = new Intent(context, MainActivity.class);
                        startActivity(main);
                        finish();
                    }else {
                        GlobalMethods.Toast(context,response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG,"Signuprespfail "+ t.getMessage().toString());
                progressDialog.dismiss();
            }
        });
    }

    private void recaptcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(SAFETY_NET_API_SITE_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        Log.e(TAG, "onSuccessReCaptcha");

                        if (!response.getTokenResult().isEmpty()) {

                            // Received captcha token
                            // This token still needs to be validated on the server
                            // using the SECRET key
                            //verifyTokenOnServer(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.e(TAG, "Error messageCaptcha: " + CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Log.e(TAG, "Unknown type of errorCaptcha: " + e.getMessage());
                        }
                    }
                });
    }
}



