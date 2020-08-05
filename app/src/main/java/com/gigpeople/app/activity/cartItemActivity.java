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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.CartAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CartItemResponse;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.utils.GlobalMethods;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cartItemActivity extends AppCompatActivity implements TextWatcher {

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
    CartAdapter cartAdapter;
    @BindView(R.id.btn_proceedpayment)
    Button btnProceedpayment;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    Dialog dialog;
    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    ApiService apiService;
    ProgressDialog progressDialog;
    Context context;
    String payment_option = "1", payment_id = "123456",total_cost,user_id;
    List<CartItemResponse.CartItem> cartitems;
    @BindView(R.id.txt_total_cost)
    TextView txtTotalCost;
    CartAdapter.CallBack callBack;
    String total_cost_check="";
    Checkout checkout;
    PaymentsRequest paymentsRequest;

    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .acceptCreditCards(false)
            .clientId(GlobalMethods.PAYPAL_LIVE);
    //SANDBOX
    //ASLIi1IubHx_ejlla-S47N3nM_AfCuK7yu4yCrKATqCT5GswjRTX0cNRpLd2pLUlvytLm5rz337vkRRt
    //PRODUCTION
    //AZ4iMP729BWXV52LGI3cNooYTR-C0qpsyh-h-ko7iDZp7JFrF28A-YgMIPL1R3jqRE7kVg1CHeBfB0ZW

    Dialog dialog_payment, dialog_thank_general;
    EditText edtCardNo, edtCardMonth, edtCardYear, edtCvv;
    String card_year, card_month, card_cvv, card_num;
    Card cardToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_item);
        ButterKnife.bind(this);
        context = cartItemActivity.this;
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id=GlobalMethods.GetUserID(context);

        Window window = cartItemActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(cartItemActivity.this, R.color.colorPrimaryDark));
        }

        if (GlobalMethods.isNetworkAvailable(context)) {
            tocallCartItems(GlobalMethods.GetUserID(context));
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        callBack=new CartAdapter.CallBack() {
            @Override
            public void call(String name) {
                if (GlobalMethods.isNetworkAvailable(context)) {

                    tocallCartItems(GlobalMethods.GetUserID(context));
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
            }
        };

    }

    @OnClick({R.id.btn_back_arrow, R.id.btn_cart, R.id.btn_proceedpayment, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_cart:
                break;
            case R.id.btn_proceedpayment:
                if (total_cost_check.equals("0")||total_cost_check.equals("0.0")){
                    GlobalMethods.Toast(context,"No Item(s) in cart");
                }else {
                    dialog = new Dialog(cartItemActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_payment);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    LinearLayout linearpaypal = (LinearLayout) dialog.findViewById(R.id.linear_paypal);
                    LinearLayout linearwechat = (LinearLayout) dialog.findViewById(R.id.linear_wechat);
                    LinearLayout linearcredit = (LinearLayout) dialog.findViewById(R.id.linear_creditcard);
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
                            if (GlobalMethods.isNetworkAvailable(context)) {

                                getPaymentToPaypal(total_cost);
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }

                        }
                    });
                    linearcredit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            payment_option = "2";
                            if (GlobalMethods.isNetworkAvailable(context)) {
                                paymentdialog();
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }

                        }
                    });
                    linearwechat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            payment_option = "3";

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
                                        Toast.makeText(cartItemActivity.this,paymentsResponse+" nan",Toast.LENGTH_LONG).show();
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                        Toast.makeText(cartItemActivity.this," nan1",Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(cartItemActivity.this," nan2",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                            if (GlobalMethods.isNetworkAvailable(context)) {
                                toClickOrder(user_id);
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }
                        }
                    });
                }
                break;
            case R.id.btn_cancel:
                /*Intent intent = new Intent(cartItemActivity.this, HomeSubCategoryActivity.class);
                intent.putExtra("page","1");
                startActivity(intent);*/
                finish();
                break;
        }
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
            if (GlobalMethods.isNetworkAvailable(context)) {
                cardToSave = new Card(card_num,Integer.parseInt(card_month),Integer.parseInt(card_year),card_cvv);
                getStripe();
            } else {
                GlobalMethods.Toast(context, getString(R.string.internet));
            }
        }

    }

    private void getStripe() {

        progressDialog = ProgressDialog.show(context, "", "", false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e("CardDetailsStripe", card_num + "\n" + card_month + "\n" + card_year + "\n" + card_cvv);
        try {
            Stripe stripe = new Stripe(context, GlobalMethods.STRIPE_LIVE);///"pk_live_59orcPbv0TxOZ2Ok2uxSCTfC-live && pk_test_eqWb9xJnUueJernZfR39qcHG-tes
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

                            if (GlobalMethods.isNetworkAvailable(cartItemActivity.this)) {
                                toClickOrder(user_id);
                            } else {
                                GlobalMethods.Toast(context, "No internet connection");
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

    private void tocallCartItems(String userId) {
        cartitems = new ArrayList<>();
        Log.e("CartListResp","UserId: "+userId);
        Call<CartItemResponse> call = apiService.callCartList(userId);
        call.enqueue(new Callback<CartItemResponse>() {
            @Override
            public void onResponse(Call<CartItemResponse> call, Response<CartItemResponse> response) {
                Log.e("CartListResp", new Gson().toJson(response.body()));
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CartItemResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        total_cost=resp.getGrandTotal();
                        txtTotalCost.setText("$"+resp.getGrandTotal());
                        total_cost_check=resp.getGrandTotal();
                        cartitems = resp.getCartItem();
                        setListview();
                    }
                }
            }

            public void onFailure(Call<CartItemResponse> call, Throwable t) {
                Log.e("cartlistrespfail", t.getMessage());
                //progressDialog.dismiss();
            }
        });
    }

    private void setListview() {

        cartAdapter = new CartAdapter(getApplicationContext(), cartitems,callBack);
        recyclerCart.setAdapter(cartAdapter);
        recyclerCart.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    // to order now
    private void toClickOrder(String userid) {
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        String logg = "USER ID:" + userid + "\ntotal_cost :" + total_cost + "\npayment_option: " + payment_option + "\npayment_id: " + payment_id;
        Log.e("OrderNowReq", logg);
        Call<CommonResponse> call = apiService.callOrderNow(userid,  total_cost, payment_option, payment_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("OrderNowResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {
                        GlobalMethods.Toast(context, resp.getMessage());

                        Intent intent = new Intent(cartItemActivity.this, MainActivity.class);
                        intent.putExtra("page", "5");
                        startActivity(intent);

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("ClickAddCartFail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getPaymentToPaypal(String cost) {
        Log.e("checkout cost", cost);
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(cost), "USD", "Gigpeople",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(cartItemActivity.this, PaymentActivity.class);

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
                        if (GlobalMethods.isNetworkAvailable(cartItemActivity.this)) {
                            toClickOrder(user_id);
                        } else {
                            GlobalMethods.Toast(context, "No internet connection");
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
}
