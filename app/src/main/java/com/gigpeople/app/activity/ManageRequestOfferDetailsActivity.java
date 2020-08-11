package com.gigpeople.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adyen.Client;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.DefaultPaymentMethodDetails;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Charge;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gigpeople.app.activity.ManageRequestDetails_2.offerDetailList;

public class ManageRequestOfferDetailsActivity extends AppCompatActivity implements TextWatcher {

    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x walknew2
    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with walknew4
    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char CARD_NUMBER_DIVIDER = '-';
    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with walknew4
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';
    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;

    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;
    Dialog dialog;
    @BindView(R.id.img_chat)
    ImageView imgChat;
    int position;
    @BindView(R.id.online_layout)
    LinearLayout onlineLayout;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_des)
    TextView txtDes;
    String TIME;
    ApiService apiService;
    String user_id, request_id = "", offer_id = "", seller_id, payment_option = "1", offer_status = "";
    @BindView(R.id.txt_live_status)
    TextView txtLiveStatus;
    @BindView(R.id.txt_live)
    TextView txtLive;
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.txt_join)
    TextView txtJoin;
    @BindView(R.id.txt_count_order)
    TextView txtCountOrder;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    ProgressDialog progressDialog;

    Dialog dialog_payment, dialog_thank_general;
    Context context;
    EditText edtCardNo, edtCardMonth, edtCardYear, edtCvv;
    String card_year, card_month, card_cvv, card_num, payment_id = "0";
    com.stripe.android.model.Card cardToSave;
    Checkout checkout;
    PaymentsRequest paymentsRequest;

    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .acceptCreditCards(false)
            .clientId(GlobalMethods.PAYPAL_LIVE);
    @BindView(R.id.txt_rejected)
    TextView txtRejected;
    @BindView(R.id.linear_accept_reject)
    LinearLayout linearAcceptReject;
    String price;
    //SANDBOX
    //ASLIi1IubHx_ejlla-S47N3nM_AfCuK7yu4yCrKATqCT5GswjRTX0cNRpLd2pLUlvytLm5rz337vkRRt
    //PRODUCTION
    //AZ4iMP729BWXV52LGI3cNooYTR-C0qpsyh-h-ko7iDZp7JFrF28A-YgMIPL1R3jqRE7kVg1CHeBfB0ZW

    // Paystack....
    String cardNumber = "4084084084084081";

    int expiryMonth = 8; //any month in the future

    int expiryYear = 2021; // any year in the future

    String cvv = "408";

    Charge charge;
    co.paystack.android.model.Card card;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managereq_details);
        ButterKnife.bind(this);
        context = ManageRequestOfferDetailsActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        Window window = ManageRequestOfferDetailsActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ManageRequestOfferDetailsActivity.this, R.color.colorPrimaryDark));
        }

        if (getIntent() != null) {

            position = getIntent().getIntExtra("position", 0);
            request_id = getIntent().getStringExtra("request_id");
            setOfferDetails();
        }
        initPaystack();
    }

    private void initPaystack() {
        PaystackSdk.initialize(context);
        card = new co.paystack.android.model.Card(cardNumber, expiryMonth, expiryYear, cvv);
    }

    private void performCharge() {
        //create a Charge object
        charge = new Charge();

        //set the card to charge
        charge.setCard(card);

        //call this method if you set a plan
        //charge.setPlan("PLN_yourplan");

        charge.setEmail("mytestemail@test.com"); //dummy email address

        charge.setAmount(Integer.parseInt(price)); //test amount

        PaystackSdk.chargeCard(ManageRequestOfferDetailsActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                    callAcceptReject();
                } else {
                    GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
                }
                String paymentReference = transaction.getReference();
                Toast.makeText(ManageRequestOfferDetailsActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
            }
        });
    }

    private void setOfferDetails() {

        seller_id = offerDetailList.get(position).getSeller_id();
        offer_id = offerDetailList.get(position).getOffer_id();

        txtName.setText(offerDetailList.get(position).getFirstName() + " " + offerDetailList.get(position).getLastName());
        txtAddress.setText(offerDetailList.get(position).getAddress());
        txtDes.setText(offerDetailList.get(position).getDescription());
        txtAbout.setText(offerDetailList.get(position).getAbout());
        txtPrice.setText("$" + offerDetailList.get(position).getPrice());
        price=offerDetailList.get(position).getPrice()+"";
        //txtCategory.setText(offerDetailList.get(position).getCategoryName() + "/" + offerDetailList.get(position).getSubCategoryName());
        txtCategory.setText(offerDetailList.get(position).getSkills());
        if (!offerDetailList.get(position).getProfileImageUrl().equals("")) {
            Glide.with(ManageRequestOfferDetailsActivity.this).load(offerDetailList.get(position).getProfileImageUrl()).into(userImage);
        }

        txtJoin.setText(" " + GlobalMethods.DateConverdion(offerDetailList.get(position).getJoinDate()));

        txtCountOrder.setText(" " + offerDetailList.get(position).getOrdersCompleted());

        if (offerDetailList.get(position).getLiveStatus().equals("1")) {
            txtLiveStatus.setBackgroundResource(R.drawable.circlebackground_green);
            txtLive.setText("Online");
            txtLive.setTextColor(getResources().getColor(R.color.colorGreen));

        } else {
            txtLiveStatus.setBackgroundResource(R.drawable.circlebackground_red);
            txtLive.setText("Offline");
            txtLive.setTextColor(getResources().getColor(R.color.colorRed));
        }

        if (offerDetailList.get(position).getRating() != null) {
            ratingbar.setRating(Float.parseFloat(offerDetailList.get(position).getRating()));
        }
        if (offerDetailList.get(position).getDeliverytime().equals("1")) {
            txtTime.setText(offerDetailList.get(position).getDeliverytime() + " day");
        } else {
            txtTime.setText(offerDetailList.get(position).getDeliverytime() + " days");
        }

        if (offerDetailList.get(position).getOfferStatus().equals("2")){
            txtRejected.setVisibility(View.VISIBLE);
            linearAcceptReject.setVisibility(View.GONE);
        }else {
            txtRejected.setVisibility(View.GONE);
            linearAcceptReject.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btn_back_arrow, R.id.userImage, R.id.btn_accept, R.id.btn_reject, R.id.img_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.userImage:
                Intent user = new Intent(ManageRequestOfferDetailsActivity.this, GigOwnerDetailsActivity.class);
                startActivity(user);
                break;
            case R.id.btn_accept:
                offer_status = "1";

                openpaymentdialog();
                break;
            case R.id.btn_reject:
                offer_status = "2";

                if (GlobalMethods.isNetworkAvailable(context)) {
                    callAcceptReject();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }

                /*Intent intent1 = new Intent(ManageRequestOfferDetailsActivity.this, MainActivity.class);
                intent1.putExtra("page", "4");
                startActivity(intent1);*/
                break;

            case R.id.img_chat:
                Intent intent4 = new Intent(ManageRequestOfferDetailsActivity.this, ChatDetailsActivity.class);
                intent4.putExtra("other_user_id", offerDetailList.get(position).getUserId());
                intent4.putExtra("other_user_name", offerDetailList.get(position).getFirstName() + " " + offerDetailList.get(position).getLastName());
                intent4.putExtra("other_user_image", offerDetailList.get(position).getProfileImageUrl());
                startActivity(intent4);
                break;
        }
    }

    private void callAcceptReject() {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("offAcceptReq", "UserId:" + user_id + " RequestId: " + request_id + "\nseller_id: " + seller_id + "\noffer_id: " + offer_id +
                "\npayment_option: " + payment_option + "\noffer_status: " + offer_status + "\npayment_id: " + payment_id);
        Call<CommonResponse> call = apiService.callbuyerOfferStatusdAPI(user_id, request_id, seller_id, offer_id, payment_option, payment_id, offer_status);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("offAcceptResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {

                            if (offer_status.equals("1")) {
                                Intent intent = new Intent(ManageRequestOfferDetailsActivity.this, MainActivity.class);
                                intent.putExtra("page", "5");
                                startActivity(intent);
                            } else {
                                Intent intent1 = new Intent(ManageRequestOfferDetailsActivity.this, MainActivity.class);
                                intent1.putExtra("page", "4");
                                startActivity(intent1);

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure", t.getMessage());

            }
        });
    }
    private void openpaymentdialog() {

        dialog = new Dialog(ManageRequestOfferDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        LinearLayout linearpaypal = (LinearLayout) dialog.findViewById(R.id.linear_paypal);
        LinearLayout linearwechat = (LinearLayout) dialog.findViewById(R.id.linear_wechat);

        LinearLayout linearcredit = (LinearLayout) dialog.findViewById(R.id.linear_creditcard);
        LinearLayout linearpaystack = (LinearLayout) dialog.findViewById(R.id.linear_paystack);
        ImageView imgclose = (ImageView) dialog.findViewById(R.id.img_close);

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        linearpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                payment_option = "1";


                if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                    getPaymentToPaypal(price);
                } else {
                    GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
                }
            }
        });
        linearcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                payment_option = "3";
                if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                    //callAcceptReject();
                } else {
                    GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
                }

                 paymentdialog();

            }
        });

        linearwechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                payment_option = "2";

                // Set YOUR_X-API-KEY with the API key from the Customer Area.
                // Change to Environment.LIVE and add the Live URL prefix when you're ready to accept live payments.
                Client client = new Client("7db34be31d1d28a0efd5fc23c6acae0d8d9f3bb2582a41974aee5d3995568383", Environment.LIVE);
                checkout = new Checkout(client);

                paymentsRequest = new PaymentsRequest();

                String merchantAccount = "1101795";
                paymentsRequest.setMerchantAccount(merchantAccount);

                Amount amount = new Amount();
                amount.setCurrency("CNY");
                amount.setValue(1500L);
                paymentsRequest.setAmount(amount);

                DefaultPaymentMethodDetails paymentMethodDetails = new DefaultPaymentMethodDetails();
                paymentMethodDetails.setType("wechatpaySDK");
                paymentsRequest.setPaymentMethod(paymentMethodDetails);

                paymentsRequest.setReference("1");

                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Status = " , Toast.LENGTH_LONG).show();

                        PaymentsResponse paymentsResponse = null;

                        try {
                            paymentsResponse = checkout.payments(paymentsRequest);
                            Toast.makeText(ManageRequestOfferDetailsActivity.this,paymentsResponse+" nan",Toast.LENGTH_LONG).show();
                        } catch (ApiException e) {
                            e.printStackTrace();
                            Toast.makeText(ManageRequestOfferDetailsActivity.this," nan1",Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(ManageRequestOfferDetailsActivity.this," nan2",Toast.LENGTH_LONG).show();
                        }
                    }
                });

               /* if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                    callAcceptReject();
                } else {
                    GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
                }*/
            }
        });

        linearpaystack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                payment_option = "4";
                if (card.isValid()) {
                    performCharge();
                }
            }
        });

    }

    private void paymentdialog() {
        dialog_payment = new Dialog(context);
        dialog_payment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_payment.setContentView(R.layout.dialog_credit_payment);
        dialog_payment.setCancelable(true);
        dialog_payment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_submit = (Button) dialog_payment.findViewById(R.id.btn_submit);
        ImageView img_close = (ImageView) dialog_payment.findViewById(R.id.img_close);

        edtCardNo = (EditText) dialog_payment.findViewById(R.id.cardno);
        edtCvv = (EditText) dialog_payment.findViewById(R.id.cardCVCEditText);
        edtCardMonth = (EditText) dialog_payment.findViewById(R.id.cardMonthEditText);
        edtCardYear = (EditText) dialog_payment.findViewById(R.id.cardYearEditText);

        edtCardNo.getText().toString().trim();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtCardNo.addTextChangedListener(this);


        dialog_payment.show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Validation();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_payment.dismiss();
            }
        });
    }

    private void Validation() {

        boolean cancel = false;
        card_cvv = edtCvv.getText().toString();
        card_year = edtCardYear.getText().toString();
        card_month = edtCardMonth.getText().toString();
        card_num = edtCardNo.getText().toString();

        if (TextUtils.isEmpty(card_num)) {
            cancel = true;
            Toast.makeText(context, " Card number is Empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_cvv)) {
            cancel = true;
            Toast.makeText(context, " Cvv is Empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_month)) {
            cancel = true;
            Toast.makeText(context, " Card month is Empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_year)) {
            cancel = true;
            Toast.makeText(context, "Card Year is Empty", Toast.LENGTH_SHORT).show();
        }

        if (!cancel) {

            dialog.dismiss();
            if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                cardToSave = new Card(card_num,Integer.parseInt(card_month),Integer.parseInt(card_year),card_cvv);
                getStripe();
            } else {
                GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
            }
        }

    }

    private void getStripe() {

        progressDialog = ProgressDialog.show(context, "", "", false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e("CardDetailsStripe", card_num + "\n" + card_month + "\n" + card_year + "\n" + card_cvv);
        try {
            Stripe stripe = new Stripe(ManageRequestOfferDetailsActivity.this, GlobalMethods.STRIPE_LIVE);///"pk_live_59orcPbv0TxOZ2Ok2uxSCTfC-live && pk_test_eqWb9xJnUueJernZfR39qcHG-tes
            stripe.createToken(
                    cardToSave,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            // Send token to your server
                            Log.e("Token1", String.valueOf(token));
                            Log.e("Token", token.getId());
                            Log.e("Type", token.getType());
                            Log.e("BankAccount", String.valueOf(token.getBankAccount()));
                            Log.e("Card", String.valueOf(token.getCard()));
                            Log.e("Created", String.valueOf(token.getCreated()));
                            Log.e("Livemode", String.valueOf(token.getLivemode()));
                            Log.e("Used", String.valueOf(token.getUsed()));
                            progressDialog.dismiss();
                            payment_id = token.getId();

                            if (GlobalMethods.isNetworkAvailable(context)) {
                                callAcceptReject();
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }

                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(context,
                                    error.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                            progressDialog.dismiss();
                            Log.e("Stripe Erreor", error.getMessage());
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
            Log.e("StripeException", e.getMessage());
            GlobalMethods.Toast(context, "Invalid Card Number");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void afterTextChanged(Editable s) {
        card_num = String.valueOf(s);

        Log.i("TAG", "Card Date:" + s);
        Log.i("TAG", "Card Date :" + edtCardYear.getEditableText());

        if (s == edtCardNo.getEditableText()) {
            if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO, CARD_NUMBER_DIVIDER)) {
                s.replace(0, s.length(), concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS), CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER));

            }
            // DO STH
        } else if (s == edtCardYear.getEditableText()) {
            if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
                s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));

            } else if (s == edtCvv.getEditableText()) {
                if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
                    s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());

                }
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    private void getPaymentToPaypal(String cost) {
        Log.e("checkout cost", cost);
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(cost), "USD", "Gigpeople",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(ManageRequestOfferDetailsActivity.this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.e("PaypalResp", paymentDetails);
                        String pay_json = paymentDetails;
                        JSONObject object = new JSONObject(paymentDetails);
                        JSONObject obj_res = object.getJSONObject("response");
                        String paypal_token_id = obj_res.getString("id");
                        String status = obj_res.getString("state");

                        payment_id = paypal_token_id;
                        if (GlobalMethods.isNetworkAvailable(ManageRequestOfferDetailsActivity.this)) {
                            callAcceptReject();
                        } else {
                            GlobalMethods.Toast(ManageRequestOfferDetailsActivity.this, getString(R.string.internet));
                        }

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

}
