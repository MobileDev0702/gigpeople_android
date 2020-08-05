package com.gigpeople.app.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.LoginResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.testfairy.TestFairy;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fblayout)
    LinearLayout fblayout;
    @BindView(R.id.googlelayout)
    LinearLayout googlelayout;
    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    /* @BindView(R.id.txt_continue_as_guest)
     TextView txtContinueAsGuest;*/
    Context context;
    @BindView(R.id.cpatchacheckbox)
    CheckBox cpatchacheckbox;
    private static final String SAFETY_NET_API_SITE_KEY = "6Lf8z0sUAAAAAP80KqD1U-3e7M_JlOrgWSms5XDd";
    private static final String URL_VERIFY_ON_SERVER = "https://www.google.com/recaptcha/api/siteverify";
    @BindView(R.id.login_button_fb)
    LoginButton loginButtonFb;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private CallbackManager callbackManager;

    String email, password,token="";
    ApiService apiService;
    ProgressDialog progressDialog;

    private static final String TAG = LoginActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = LoginActivity.this;
        apiService= RetrofitSingleton.createService(ApiService.class);
        TestFairy.begin(this, "SDK-z4l0Jemk");
        //bejedod715@prowerl.com / uniflyn123
        getKeyHash();
        try {
             token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Token",token+"");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TokenError",e.getMessage());
        }

        Window window = LoginActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimaryDark));
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(LoginActivity.this, LoginActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        new Login().execute();

    }

    @OnClick({R.id.cpatchacheckbox,R.id.btn_login, R.id.fblayout, R.id.googlelayout, R.id.txt_forgot_password, R.id.txt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cpatchacheckbox:
                if (GlobalMethods.isNetworkAvailable(context)){
                    recaptcha();
                }else {
                    GlobalMethods.Toast(context,getString(R.string.internet));
                }
                break;
            case R.id.btn_login:
                if (GlobalMethods.isNetworkAvailable(context)){
                    if (validation()){
                        callLogin();
                    }
                }else {
                    GlobalMethods.Toast(context,getString(R.string.internet));
                }
                break;
            case R.id.fblayout:
                if (GlobalMethods.isNetworkAvailable(LoginActivity.this)) {
                    loginButtonFb.performClick();
                } else {
                    GlobalMethods.Toast(LoginActivity.this, getString(R.string.internet));
                }
                break;
            case R.id.googlelayout:
                if (GlobalMethods.isNetworkAvailable(LoginActivity.this)) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                    mGoogleApiClient.connect();
                } else {
                    GlobalMethods.Toast(LoginActivity.this, getString(R.string.internet));
                }
                break;
            case R.id.txt_forgot_password:
                Intent forgot = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgot);
                break;
            case R.id.txt_register:
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
          /*  case R.id.txt_continue_as_guest:
                Intent main1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main1);
                break;*/
        }
    }

    public boolean validation(){
        if (edtEmail.getText().toString().equals("")){
            GlobalMethods.Toast(context,"Enter email Id or mobile number");
            return false;
        }/*else if (!edtEmail.getText().toString().contains("@")){
            GlobalMethods.Toast(context,"Enter valid email Id");
            return false;
        }*/else if (edtPassword.getText().toString().equals("")){
            GlobalMethods.Toast(context,"Enter password");
            return false;
        }else if (!cpatchacheckbox.isChecked()) {
            GlobalMethods.Toast(context, "Verify Recaptcha");
            return false;
        }
        return true;
    }

    private void callLogin() {

        if (token==null || token.equals("null")){
            try {
                token = FirebaseInstanceId.getInstance().getToken();
                Log.e("Token",token+"");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TokenError",e.getMessage());
            }
        }
        email=edtEmail.getText().toString();
        password=edtPassword.getText().toString();
        Log.e(TAG,"LoginRequest: \nEmail: "+email+"\nPassword: "+password+"\nToken: "+token+"\nDeviceType: "+"Android");
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Call<LoginResponse> call=apiService.callLoginAPI(email,password,"Android",token.trim());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG,"LoginResp: "+new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    LoginResponse resp=response.body();
                    if (resp!=null){
                        if (resp.getStatus().equals("1")){
                            GlobalMethods.Toast(context,resp.getMessage());

                            PrefConnect.writeString(context,PrefConnect.USER_ID,resp.getUserDetails().getUserId());
                            PrefConnect.writeString(context, PrefConnect.FIRSTNAME, resp.getUserDetails().getFirstName());
                            PrefConnect.writeString(context, PrefConnect.LASTNAME, resp.getUserDetails().getLastName());
                            PrefConnect.writeString(context, PrefConnect.EMAIL, resp.getUserDetails().getEmail());
                            PrefConnect.writeString(context, PrefConnect.IMAGE_URL, resp.getUserDetails().getProfileImageUrl());
                            PrefConnect.writeString(context, PrefConnect.NOTIFICATION, resp.getUserDetails().getNotification());
                            PrefConnect.writeString(context, PrefConnect.ORDER_STATUS, resp.getUserDetails().getCustomerOrder());

                            PrefConnect.writeString(context, PrefConnect.PROFILE_LOCATION, resp.getUserDetails().getAddress());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LAT, resp.getUserDetails().getLattitude());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LONG, resp.getUserDetails().getLongitude());

                            Intent main = new Intent(context, MainActivity.class);
                            startActivity(main);
                            finish();
                        }else {
                            GlobalMethods.Toast(context,resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG,"LoginFailResp: "+t.getMessage());
            }
        });

    }

    private void callSocialLogin(String email,String first_name,String last_name) {

        if (token==null || token.equals("null")){
            try {
                token = FirebaseInstanceId.getInstance().getToken();
                Log.e("Token",token+"");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TokenError",e.getMessage());
            }
        }

        Log.e(TAG,"SocialLoginRequest: \nEmail: "+email+"\nToken: "+token+"\nDeviceType: "+"Android"+"\nfirst_name: "+first_name+"\nlast_name: "+last_name);
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Call<LoginResponse> call=apiService.callSocialLoginAPI(email,first_name,last_name,"Android",token.trim());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG,"SocialLoginResp: "+new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    LoginResponse resp=response.body();
                    if (resp!=null){
                        if (resp.getStatus().equals("1")){
                            GlobalMethods.Toast(context,resp.getMessage());

                            PrefConnect.writeString(context,PrefConnect.USER_ID,resp.getUserDetails().getUserId());
                            PrefConnect.writeString(context, PrefConnect.FIRSTNAME, resp.getUserDetails().getFirstName());
                            PrefConnect.writeString(context, PrefConnect.LASTNAME, resp.getUserDetails().getLastName());
                            PrefConnect.writeString(context, PrefConnect.EMAIL, resp.getUserDetails().getEmail());
                            PrefConnect.writeString(context, PrefConnect.IMAGE_URL, resp.getUserDetails().getProfileImageUrl());
                            PrefConnect.writeString(context, PrefConnect.NOTIFICATION, resp.getUserDetails().getNotification());
                            PrefConnect.writeString(context, PrefConnect.ORDER_STATUS, resp.getUserDetails().getCustomerOrder());

                            PrefConnect.writeString(context, PrefConnect.PROFILE_LOCATION, resp.getUserDetails().getAddress());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LAT, resp.getUserDetails().getLattitude());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LONG, resp.getUserDetails().getLongitude());

                            Intent main = new Intent(context, MainActivity.class);
                            startActivity(main);
                            finish();

                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {

                                        }
                                    });
                            LoginManager.getInstance().logOut();
                        }else {
                            GlobalMethods.Toast(context,resp.getMessage());
                            Log.e(TAG,"SocialLoginFailResp: "+resp.getMessage());
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {

                                        }
                                    });
                            LoginManager.getInstance().logOut();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG,"SocialLoginFailResp: "+t.getMessage());
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            Log.e("GoogleResp", String.valueOf(result.getStatus()) + "\n" + String.valueOf(result.getSignInAccount()));

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("GoogleSignInResult ", String.valueOf(result));
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

           String  google_name = acct.getDisplayName();
            //google_gmail_id = acct.getId();
            email = acct.getEmail();

            //Log.e("google_name", google_name);
            //Log.e("google_gmail_id", google_gmail_id);
            Log.e("google_email_address", email);

            StringTokenizer tokens = new StringTokenizer(google_name, " ");
            String fsname = null;// this will contain "Fruit"
            String lsname = null;
            try {
                fsname = tokens.nextToken();
                lsname = tokens.nextToken();
            } catch (Exception e) {
                e.printStackTrace();
            }
             callSocialLogin(email,fsname,lsname);

        } else {
             GlobalMethods.Toast(LoginActivity.this, "Google Syncing, Try again");
            Log.e("GoogleSignInResultFail", "google");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class Login extends AsyncTask {

        @SuppressLint("WrongThread")
        @Override
        protected Object doInBackground(Object[] objects) {
            loginButtonFb.setReadPermissions(Arrays.asList("email"));
            loginButtonFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    if (AccessToken.getCurrentAccessToken() != null) {
                        RequestData();
                    }
                }

                @Override
                public void onCancel() {
                    LoginManager.getInstance().logOut();

                }

                @Override
                public void onError(FacebookException exception) {
                }
            });

            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("FB Login Response", new Gson().toJson(response.getJSONObject()));
                        Log.e("FB Login Error", new Gson().toJson(response.getError()));

                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {

                                email = json.getString("email");
                                //fb_your_name = json.getString("name");
                                //fb_id = json.getString("id");
                                String fsname = json.getString("first_name");
                                String lsname = json.getString("last_name");
                                //Log.e("fb_your_name", fb_your_name);
                                Log.e("fb_email_address", email);
                                //Log.e("fb_id", fb_id);

                                callSocialLogin(email,fsname,lsname);
                            }

                        } catch (JSONException e) {
                             Toast.makeText(context, getResources().getString(R.string.email_not_found), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void getKeyHash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.gigpeople.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                // GlobalMethods.Toast(LoginActivity.this,Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            //Log.e("package", e.getMessage());

        } catch (NoSuchAlgorithmException e) {
            //Log.e("nosuch", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
