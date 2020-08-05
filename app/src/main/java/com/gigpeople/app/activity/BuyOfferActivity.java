package com.gigpeople.app.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.DeliveryTimeAdapter;
import com.gigpeople.app.adapter.RevisonsAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.DeliveryModel;
import com.gigpeople.app.apiModel.GigRequestOffersentResponse;
import com.gigpeople.app.apiModel.GigRequestResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyOfferActivity extends AppCompatActivity {

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.edt_buyeroffer_comment)
    EditText edtBuyerofferComment;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_delivery)
    Button btnDelivery;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.linearOfferSend)
    LinearLayout linearOfferSend;
    Calendar calendar;
    @BindView(R.id.spinner_time)
    Spinner spinnerTime;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @BindView(R.id.edt_date)
    EditText edtDate;
    @BindView(R.id.edtprice)
    EditText edtprice;
    private int mYear, mMonth, mDay;
    String PickDate;
    DatePickerDialog datePickerDialog;
    String date, price, description;

    String UserId, RequestId;
    Context context;
    ApiService apiService;
    ProgressDialog progressDialog;
    public List<GigRequestResponse.RequestDetail> gignewrequestlistdetialspage;
    int page;
    String layoutstup;
    Dialog deliveryTimeDialog;

    public List<GigRequestOffersentResponse.RequestDetail> gignewrequestlistoffersentdetialspage;

    List<DeliveryModel> delivertTimeModelList;
    DeliveryTimeAdapter deliveryTimeAdapter;
    DeliveryTimeAdapter.ItemClickListener deliveryItemClickListener;

    List<String> stringListDeliveryTime;
    ArrayAdapter<String>  adapterDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_offer);
        ButterKnife.bind(this);
        context = BuyOfferActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);

        UserId = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        init();

        Window window = BuyOfferActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(BuyOfferActivity.this, R.color.colorPrimaryDark));
        }
        calendar = Calendar.getInstance();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        /*ArrayAdapter adapter7 = ArrayAdapter.createFromResource(BuyOfferActivity.this, R.array.deliverytime, R.layout.item_spinner_dropdown2);
        adapter7.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerTime.setAdapter(adapter7);*/
        if (getIntent() != null) {

            page = getIntent().getIntExtra("page", 0);

            layoutstup = getIntent().getStringExtra("layoutsetup");


            if (layoutstup.equalsIgnoreCase("1")) {

                linearOfferSend.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);

                //userdetilas
                gignewrequestlistdetialspage = new ArrayList<>();
                gignewrequestlistdetialspage = GlobalVariables.gigNewRequestlist;
                RequestId = gignewrequestlistdetialspage.get(page).getRequestId();
            } else {
                linearOfferSend.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);


                gignewrequestlistoffersentdetialspage = new ArrayList<>();
                gignewrequestlistoffersentdetialspage = GlobalVariables.gigNewRequesSenttlist;

                date=gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getDeliverytime();
                for (int i=0;i<stringListDeliveryTime.size();i++){
                    if (date.equals(stringListDeliveryTime.get(i))){
                        spinnerTime.setSelection(i);
                    }
                }

                RequestId = gignewrequestlistoffersentdetialspage.get(page).getRequestId();
                if (gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getDeliverytime().equals("1")) {
                    edtDate.setText(gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getDeliverytime()+" day");
                }else {
                    edtDate.setText(gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getDeliverytime()+" days");
                }
                edtprice.setText(gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getPrice());
                edtBuyerofferComment.setText(gignewrequestlistoffersentdetialspage.get(page).getMyOffer().getDescription());

            }
        }

        deliveryItemClickListener=new DeliveryTimeAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name) {
                if (name.equals("1")) {
                    edtDate.setText(name + " day");
                }else {
                    edtDate.setText(name + " days");
                }
                deliveryTimeDialog.dismiss();
                date = name;
            }
        };

    }

    private void init() {

        stringListDeliveryTime=new ArrayList<>();

        stringListDeliveryTime.add("Delivery Time");
        stringListDeliveryTime.add("1");
        stringListDeliveryTime.add("2");
        stringListDeliveryTime.add("3");
        stringListDeliveryTime.add("4");
        stringListDeliveryTime.add("5");
        stringListDeliveryTime.add("6");
        stringListDeliveryTime.add("7");
        stringListDeliveryTime.add("8");
        stringListDeliveryTime.add("9");
        stringListDeliveryTime.add("10");
        stringListDeliveryTime.add("11");
        stringListDeliveryTime.add("12");
        stringListDeliveryTime.add("13");
        stringListDeliveryTime.add("14");
        stringListDeliveryTime.add("15");
        stringListDeliveryTime.add("16");
        stringListDeliveryTime.add("17");
        stringListDeliveryTime.add("18");
        stringListDeliveryTime.add("19");
        stringListDeliveryTime.add("20");
        stringListDeliveryTime.add("21");
        stringListDeliveryTime.add("22");
        stringListDeliveryTime.add("23");
        stringListDeliveryTime.add("24");
        stringListDeliveryTime.add("25");
        stringListDeliveryTime.add("26");
        stringListDeliveryTime.add("27");
        stringListDeliveryTime.add("28");
        stringListDeliveryTime.add("29");
        stringListDeliveryTime.add("30");

        adapterDeliveryTime = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringListDeliveryTime);
        adapterDeliveryTime.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerTime.setAdapter(adapterDeliveryTime);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getSelectedItem().toString();

                date = selected;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        delivertTimeModelList=new ArrayList<>();

        delivertTimeModelList.add(new DeliveryModel("1"));
        delivertTimeModelList.add(new DeliveryModel("2"));
        delivertTimeModelList.add(new DeliveryModel("3"));
        delivertTimeModelList.add(new DeliveryModel("4"));
        delivertTimeModelList.add(new DeliveryModel("5"));
        delivertTimeModelList.add(new DeliveryModel("6"));
        delivertTimeModelList.add(new DeliveryModel("7"));
        delivertTimeModelList.add(new DeliveryModel("8"));
        delivertTimeModelList.add(new DeliveryModel("9"));
        delivertTimeModelList.add(new DeliveryModel("10"));
        delivertTimeModelList.add(new DeliveryModel("11"));
        delivertTimeModelList.add(new DeliveryModel("12"));
        delivertTimeModelList.add(new DeliveryModel("13"));
        delivertTimeModelList.add(new DeliveryModel("14"));
        delivertTimeModelList.add(new DeliveryModel("15"));
        delivertTimeModelList.add(new DeliveryModel("16"));
        delivertTimeModelList.add(new DeliveryModel("17"));
        delivertTimeModelList.add(new DeliveryModel("18"));
        delivertTimeModelList.add(new DeliveryModel("19"));
        delivertTimeModelList.add(new DeliveryModel("20"));
        delivertTimeModelList.add(new DeliveryModel("21"));
        delivertTimeModelList.add(new DeliveryModel("22"));
        delivertTimeModelList.add(new DeliveryModel("23"));
        delivertTimeModelList.add(new DeliveryModel("24"));
        delivertTimeModelList.add(new DeliveryModel("25"));
        delivertTimeModelList.add(new DeliveryModel("26"));
        delivertTimeModelList.add(new DeliveryModel("27"));
        delivertTimeModelList.add(new DeliveryModel("28"));
        delivertTimeModelList.add(new DeliveryModel("29"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_submit, R.id.btn_delivery, R.id.btn_cancel, R.id.edt_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_submit:
                if (validation()) {
                    if (GlobalMethods.isNetworkAvailable(context)) {

                        toCallOfferRequest(UserId, RequestId, date, price, description);
                    } else {
                        GlobalMethods.Toast(context, getString(R.string.internet));
                    }
                }
                break;
            case R.id.btn_delivery:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.edt_date:
                //getPickDate();
                openDeliveryTimeDialog();
                break;

        }
    }

    private void getPickDate() {
        final Calendar c = Calendar.getInstance();
        Date SelectedDate = c.getTime();
        DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String StringDateformat_US = dateformat_US.format(SelectedDate);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(BuyOfferActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                PickDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                edtDate.setText(PickDate);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private boolean validation() {


       // date = edtDate.getText().toString();
        price = edtprice.getText().toString();
        description = edtBuyerofferComment.getText().toString();

        if (TextUtils.isEmpty(date)) {
            GlobalMethods.Toast(getApplicationContext(), "Select delivery time");
            return false;

        }else if (date.equalsIgnoreCase("delivery time")) {
            GlobalMethods.Toast(getApplicationContext(), "Select delivery time");
            return false;

        } else if (TextUtils.isEmpty(price)) {
            GlobalMethods.Toast(getApplicationContext(), "Enter Price");
            return false;

        } else if (TextUtils.isEmpty(description)) {
            GlobalMethods.Toast(getApplicationContext(), "Enter Description");
            return false;

        }else if (description.length()>50) {
            GlobalMethods.Toast(context, "Description should not exceed 50 characters");
            return false;
        }

        return true;
    }

    private void openDeliveryTimeDialog() {

        deliveryTimeDialog = new Dialog(context);
        deliveryTimeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deliveryTimeDialog.setContentView(R.layout.dialog_revisons);
        deliveryTimeDialog.setCancelable(true);
        deliveryTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deliveryTimeDialog.show();
        RecyclerView recycle_delivery = (RecyclerView) deliveryTimeDialog.findViewById(R.id.recycler_revisons);
        TextView txt_title = (TextView) deliveryTimeDialog.findViewById(R.id.txt_title);
        ImageView img_close = (ImageView) deliveryTimeDialog.findViewById(R.id.img_close);

        txt_title.setText("Delivery Time");
        deliveryTimeAdapter = new DeliveryTimeAdapter(context, delivertTimeModelList, deliveryItemClickListener);
        recycle_delivery.setAdapter(deliveryTimeAdapter);
        recycle_delivery.setLayoutManager(new LinearLayoutManager(context));

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryTimeDialog.dismiss();
            }
        });
    }

    private void toCallOfferRequest(String userid, String requestid, String deliveryTime, String price, String description) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("OfferSendReq", "UserId: " + userid + "\nRequestId: " + requestid + "\nDeliveryTime: " + deliveryTime + "\nPrice: " + price + "\nDescription: " + description);
        Call<CommonResponse> call = apiService.callOferSendRequest(userid, requestid, deliveryTime, price, description);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("OfferSendResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        GlobalMethods.Toast(context, resp.getMessage());//1
                        Intent submit = new Intent(context, MainActivity.class);
                        submit.putExtra("page", "1");
                        startActivity(submit);

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());

                    }
                }

            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("Gignewofferequestfail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    // Fun & Lifestyle,Global Culture,Gaming,Celebrity Impersonators,Pranks & Stunts,QA,User Testing,Databases,Support & IT,Convert Files,Writing & Translation,Articles & Blog Posts,Legal Writing,Transcription,Proof Reading & Editing,Translation,Research & Summaries,

}
