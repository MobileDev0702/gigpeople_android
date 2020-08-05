package com.gigpeople.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.BillingAddResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;


import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edt_company_name)
    EditText edtCompanyName;
    @BindView(R.id.edt_full_name)
    EditText edtFullName;
    @BindView(R.id.edt_country)
    EditText edtCountry;
    @BindView(R.id.linear_location)
    LinearLayout linearLocation;
    @BindView(R.id.edt_zipcode)
    EditText edtZipcode;
    AutocompleteSupportFragment autocompleteFragment;
    ProgressDialog progressDialog;
    ApiService apiService;
    Context context;

    String company_name, full_name, country, address = "", zipcode, user_id = "1", billing_latitude, billing_longitude;
    public static final String TAG = BillingActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        ButterKnife.bind(this);
        Window window = BillingActivity.this.getWindow();
        context = BillingActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id=PrefConnect.readString(context,PrefConnect.USER_ID,"");
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(BillingActivity.this, R.color.colorPrimaryDark));
        }

        setBookingLocation();

        if (GlobalMethods.isNetworkAvailable(context)){
            setBillingDetail();
        }else {
            GlobalMethods.Toast(context,getString(R.string.internet));
        }
    }

    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (validation()) {
                        callBillingAdd();
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
        }
    }

    private void setBillingDetail() {
        Log.e(TAG,"BillingDetailReq: "+"UserId: "+user_id);
        Call<BillingAddResponse> call=apiService.callBillingDetailAPI(user_id);
        call.enqueue(new Callback<BillingAddResponse>() {
            @Override
            public void onResponse(Call<BillingAddResponse> call, Response<BillingAddResponse> response) {
                Log.e(TAG,"BillingDetailResp: "+new Gson().toJson(response.body()));
                if (response.isSuccessful()){
                    BillingAddResponse resp=response.body();
                    if (resp!=null){
                        if (resp.getStatus().equals("1")){
                            company_name = resp.getBillingDetails().getCompanyName();
                            full_name = resp.getBillingDetails().getFullName();
                            zipcode = resp.getBillingDetails().getZipcode();
                            country = resp.getBillingDetails().getCountry();
                            address = resp.getBillingDetails().getAddress();
                            billing_latitude = resp.getBillingDetails().getLattitude();
                            billing_longitude = resp.getBillingDetails().getLongitude();

                            edtCompanyName.setText(company_name);
                            edtFullName.setText(full_name);
                            edtZipcode.setText(zipcode);
                            edtCountry.setText(country);
                            autocompleteFragment.setText(address);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BillingAddResponse> call, Throwable t) {

            }
        });
    }

    private void callBillingAdd() {
        company_name = edtCompanyName.getText().toString();
        full_name = edtFullName.getText().toString();
        zipcode = edtZipcode.getText().toString();
        country = edtCountry.getText().toString();

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e(TAG, "BillingAddReq: " + "\nUserId: " + user_id + "\ncompany_name: " + company_name + "\nfull_name: " + full_name + "\naddress: " + address + "\nbilling_latiyude: " + billing_latitude
                + "\nbilling_longitude: " + billing_longitude + "\ncountry: " + country + "\nzipcode: " + zipcode);
        Call<BillingAddResponse> call = apiService.callbillingAddAPI(user_id, company_name, full_name, address, billing_latitude, billing_longitude, country, zipcode);
        call.enqueue(new Callback<BillingAddResponse>() {
            @Override
            public void onResponse(Call<BillingAddResponse> call, Response<BillingAddResponse> response) {
                Log.e(TAG, "BillingAddResp: " + new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    BillingAddResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            finish();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BillingAddResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public boolean validation() {
        if (edtCompanyName.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter company name");
            return false;
        } else if (edtFullName.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter full name");
            return false;
        } else if (edtCountry.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter country");
            return false;
        } else if (address.equals("")) {
            GlobalMethods.Toast(context, "Enter Address");
            return false;
        } else if (edtZipcode.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter zipcode");
            return false;
        }
        return true;
    }

    private void setBookingLocation() {

        if (!Places.isInitialized()) {
            Places.initialize(context, getString(R.string.google_places_key));
        }
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete);
        autocompleteFragment.setHint("Address");
        ImageView searchicon = (ImageView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(0);
        TextView textView = (TextView) ((LinearLayout) autocompleteFragment.getView()).getChildAt(1);
        textView.setTextSize(14);

        ((LinearLayout) autocompleteFragment.getView()).removeView(searchicon);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.e("Name", place.getName().toString());
                billing_latitude = place.getLatLng().latitude + "";
                billing_longitude = place.getLatLng().longitude + "";
                address = place.getAddress().toString();
                getAddressFromLocation(place.getLatLng().latitude ,place.getLatLng().longitude,context );
            }

            @Override
            public void onError(Status status) {
                Log.e("An error occurred: ", status + " ");
            }
        });
    }

    public void getAddressFromLocation(final double latitude, final double longitude, final Context context) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        final String booking_location = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        final String addresslocatlity = addressList.get(0).getLocality();
                        String state = addressList.get(0).getAdminArea();
                        final String country = addressList.get(0).getCountryName();
                        final String postalCode = addressList.get(0).getPostalCode();
                        String knownName = addressList.get(0).getFeatureName(); // Only if available else return NULL
                        Log.e("adreessonegetLocation", addresslocatlity + "::" );
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String lat,log;
                                    lat = String.valueOf(latitude);
                                    edtCountry.setText(country);
                                    edtZipcode.setText(postalCode);
                                    log = String.valueOf(longitude);
                                    //autocompleteFragment.setText(booking_location);
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                } catch (IOException e) {
                    Log.e("Address", "Unable connect to Geocoder", e);
                }
            }
        };
        thread.start();
    }
}
