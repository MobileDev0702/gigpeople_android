package com.gigpeople.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.CategoryListAdapter;
import com.gigpeople.app.adapter.DeliveryTimeAdapter;
import com.gigpeople.app.adapter.ExtraGigAdapter;
import com.gigpeople.app.adapter.LanguageAdapter;
import com.gigpeople.app.adapter.NewImageAddAdapter;
import com.gigpeople.app.adapter.RevisonsAdapter;
import com.gigpeople.app.adapter.SubcategoryListAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.DeliveryModel;
import com.gigpeople.app.apiModel.ImageUploadResponse;
import com.gigpeople.app.apiModel.MultipleImageModel;
import com.gigpeople.app.apiModel.RevisonsModel;
import com.gigpeople.app.model.ExtraGigModel;
import com.gigpeople.app.model.ImageAddModel;
import com.gigpeople.app.utils.FilePath;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gigpeople.app.activity.GigDetailsActivity.imgDetailsList;


public class EditGigActivitytwo extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    Dialog camera_dialog;
    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;
    @BindView(R.id.spinner_subcategory)
    Spinner spinnerSubcategory;
    @BindView(R.id.recyclePhoto)
    RecyclerView recyclePhoto;
    @BindView(R.id.img_camera)
    ImageView imgCamera;
    @BindView(R.id.horizantalSCroll)
    HorizontalScrollView horizantalSCroll;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.radio_yes)
    RadioButton radioYes;
    @BindView(R.id.radio_no)
    RadioButton radioNo;
    @BindView(R.id.spinner_revisions)
    Spinner spinnerRevisions;
    @BindView(R.id.add_extragig)
    ImageView addExtragig;
    @BindView(R.id.spinner_cost)
    Spinner spinnerCost;
    @BindView(R.id.spinner_days)
    Spinner spinnerDays;
    @BindView(R.id.extragiglayout)
    LinearLayout extragiglayout;
    @BindView(R.id.btn_gig_update)
    Button btnGigUpdate;
    @BindView(R.id.img_add_lang)
    ImageView imgAddLang;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edttags)
    EditText edttags;
    @BindView(R.id.spinner_price)
    Spinner spinnerPrice;
    @BindView(R.id.shippingview)
    View shippingview;
    @BindView(R.id.shipping_price_layout)
    LinearLayout shippingPriceLayout;
   /* @BindView(R.id.edt_time)
    EditText edtTime;*/
    @BindView(R.id.txt_category)
    TextView txtCategory;
    @BindView(R.id.img_category)
    ImageView imgCategory;
    @BindView(R.id.txt_sub_category)
    TextView txtSubCategory;
    @BindView(R.id.img_sub)
    ImageView imgSub;
    @BindView(R.id.edt_price)
    EditText edtPrice;
    @BindView(R.id.txt_revisions)
    TextView txtRevisions;
    @BindView(R.id.img_revisions)
    ImageView imgRevisions;
    @BindView(R.id.linear_revisons)
    LinearLayout linearRevisons;
    @BindView(R.id.linear_category)
    LinearLayout linearCategory;
    @BindView(R.id.linear_sub_category)
    LinearLayout linearSubCategory;
    @BindView(R.id.edt_shippingprice)
    EditText edtShippingprice;
    @BindView(R.id.spinner_delivery_time)
    Spinner spinnerDeliveryTime;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_VIDEO = 2, REQUEST_VIDEO = 3;
    private Uri uri;
    private Bitmap bitmap;
    List<ImageAddModel> imageAddModelList;
    NewImageAddAdapter newImageAddAdapter;
    String path;
    Uri yourUri;
    Calendar calendar;
    int year, month, day;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Dialog extragig;
    List<ExtraGigModel> gigModels;
    ExtraGigAdapter extraGigAdapter;
    ExtraGigAdapter.ItemClickListener itemClickListener;
    LanguageAdapter languageAdapter;
    String lang;
    LinearLayout lineartitle;
    View linearview;
    LanguageAdapter.CallBack callBack;
    private int mYear, mMonth, mDay;
    String PickDate;
    DatePickerDialog datePickerDialog;
    Dialog category, sub_category, revisons, deliveryTimeDialog;
    Context context;
    RecyclerView recycle_category, recyle_subCategory, recycle_revisions;
    CategoryListAdapter categoryListAdapter;
    SubcategoryListAdapter subcategoryListAdapter;
    CategoryListAdapter.ItemClickListener itemClickListenerCategory;
    SubcategoryListAdapter.ItemClickListener itemClickListenerSubcategory;
    String category_name, sub_category_id, category_id, sub_category_name;
    int category_position;
    Bitmap rotatedBitmap = null;
    List<MultipleImageModel> imageList = new ArrayList<>();
    ProgressDialog progressDialog;

    public static String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};

    String user_id, images, tittle, type, price, delivery_time="", shipping = "1", shippinprice = "0", tags = "", revisions="", description, gig_staus = "1", gig_id;
    public static final String TAG = AddNewGigActivity.class.getSimpleName();
    ApiService apiService;
    List<RevisonsModel> revisonsModelList;
    List<DeliveryModel> delivertTimeModelList;
    RevisonsAdapter revisonsAdapter;
    DeliveryTimeAdapter deliveryTimeAdapter;
    DeliveryTimeAdapter.ItemClickListener deliveryItemClickListener;
    RevisonsAdapter.ItemClickListener itemClickListener3;
    File thumb_file;
    int position;
    String TIME;

    List<String> stringListSub, stringList, stringListRevision, stringListDeliveryTime;
    int main_position;
    ArrayAdapter<String> adapter, adapterSub, adapterRevision, adapterDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gig);
        ButterKnife.bind(this);
        context = EditGigActivitytwo.this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        calendar = Calendar.getInstance();
        Window window = EditGigActivitytwo.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(EditGigActivitytwo.this, R.color.colorPrimaryDark));
        }
        CategoryDialog();
        init();

        itemClickListener = new ExtraGigAdapter.ItemClickListener() {
            public void itemClick() {
                if (gigModels.size() == 0) {
                    lineartitle.setVisibility(View.GONE);
                    linearview.setVisibility(View.GONE);
                }
            }
        };
        itemClickListenerCategory = new CategoryListAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name, int position, String str_category_id) {

                category_id = str_category_id;
                category_position = position;
                txtCategory.setText(name);
                category.dismiss();

                txtSubCategory.setText("");
                sub_category_id = "";
            }
        };

        itemClickListenerSubcategory = new SubcategoryListAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name, String id) {

                txtSubCategory.setText(name);
                sub_category_id = id;
                sub_category.dismiss();
            }
        };

        itemClickListener3 = new RevisonsAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name) {
                txtRevisions.setText(name);
                revisions = name;
                revisons.dismiss();

            }
        };

        deliveryItemClickListener = new DeliveryTimeAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name) {

                if (name.equals("1")) {
                   // edtTime.setText(name + " day");
                } else {
                   // edtTime.setText(name + " days");
                }
                deliveryTimeDialog.dismiss();
                delivery_time = name;
            }
        };
        callBack = new LanguageAdapter.CallBack() {
            @Override
            public void call() {
                if (imageAddModelList.size() > 0) {
                    Log.e("TAG", "TRUW");
                    recycler.setVisibility(View.VISIBLE);
                } else {
                    recycler.setVisibility(View.GONE);

                }
            }
        };

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            callMultiplePermissions();
        } else {
            // Pre-Marshmallow
        }

        if (getIntent() != null) {
            gig_id = getIntent().getStringExtra("gig_id");
            position = getIntent().getIntExtra("position", 0);
            tittle = getIntent().getStringExtra("title");
            category_id = getIntent().getStringExtra("category_id");
            sub_category_id = getIntent().getStringExtra("subcategory_id");
            category_name = getIntent().getStringExtra("category");
            sub_category_name = getIntent().getStringExtra("subcategory");
            shipping = getIntent().getStringExtra("shippings");
            shippinprice = getIntent().getStringExtra("shipping_price");
            delivery_time = getIntent().getStringExtra("deliver_time");
            revisions = getIntent().getStringExtra("revisions");
            tags = getIntent().getStringExtra("tags");
            price = getIntent().getStringExtra("price");
            description = getIntent().getStringExtra("description");

            Log.e("DelivertTime",delivery_time+" Revision: "+revisions+""+sub_category_name);

            for (int i = 0; i < GlobalVariables.dashmainCategorylist.size(); i++) {
                if (GlobalVariables.dashmainCategorylist.get(i).getMainCategoryId().equals(category_id)) {
                    category_position = i;
                    break;
                }
            }

            SetEditGIG();

        }
    }

    private void SetEditGIG() {

        if (description != null) {

            txtCategory.setText(category_name);
            txtSubCategory.setText(sub_category_name);
            txtRevisions.setText(revisions);
            edtDescription.setText(description);
            //edtTime.setText(delivery_time);
            edtPrice.setText(price);
            String title_str = tittle.toString().replace("I will", "");
            edtTitle.setText(title_str);
            //spinnerDeliveryTime.setSelection(Integer.parseInt(delivery_time)+1);
            //spinnerRevisions.setSelection(Integer.parseInt(revisions)+1);

            //String TIME = setTimeString(delivery_time);
            //spinnerCategory.setSelected("");

            for (int i=0;i<stringList.size();i++){
                if (category_name.equals(stringList.get(i))){
                    spinnerCategory.setSelection(i);
                }
            }

            for (int i=0;i<stringListSub.size();i++){
                Log.e("SubcategoryName",sub_category_name+" mmm "+stringListSub.get(i));
                if (sub_category_name.equals(stringListSub.get(i))){
                    Log.e("SubcategoryName","yes");
                    spinnerSubcategory.setSelection(i);
                }
            }

            for (int i=0;i<stringListRevision.size();i++){
                if (revisions.equals(stringListRevision.get(i))){
                    spinnerRevisions.setSelection(i);
                }
            }

            for (int i=0;i<stringListDeliveryTime.size();i++){
                    if (delivery_time.equals(stringListDeliveryTime.get(i))){
                        spinnerDeliveryTime.setSelection(i);
                    }
            }

            if (delivery_time.equals("0")) {
               // edtTime.setText(delivery_time);
            } else {
               // edtTime.setText(delivery_time);
            }

            if (shipping.equals("1")) {

                shippingPriceLayout.setVisibility(View.GONE);
                shippingview.setVisibility(View.GONE);
                radioYes.setChecked(false);
                radioNo.setChecked(true);
            } else {
                edtShippingprice.setText(shippinprice);
                shippingPriceLayout.setVisibility(View.VISIBLE);
                shippingview.setVisibility(View.VISIBLE);
                radioYes.setChecked(true);
                radioNo.setChecked(false);
            }

            edttags.setText(tags);
            String TEMP[] = tags.split(",");
            for (int i = 0; i < TEMP.length; i++) {
                imageAddModelList.add(new ImageAddModel(null, TEMP[i]));
            }
            languageAdapter = new LanguageAdapter(EditGigActivitytwo.this, imageAddModelList);
            recycler.setLayoutManager(new GridLayoutManager(EditGigActivitytwo.this, 1, LinearLayoutManager.HORIZONTAL, false));
            recycler.setAdapter(languageAdapter);
            recycler.setVisibility(View.GONE);
            languageAdapter.notifyDataSetChanged();

            for (int i = 0; i < imgDetailsList.size(); i++) {
                imageList.add(new MultipleImageModel(imgDetailsList.get(i).getFile(),
                        imgDetailsList.get(i).getFile(), imgDetailsList.get(i).getType(), imgDetailsList.get(i).getThumnail()));
            }
            setImage();
        }
    }

    private String setTimeString(String time) {

        long long_time = 0, long_now;
        String givenDateString = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            long_time = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        long_now = calendar.getTimeInMillis();

        TIME = String.valueOf(DateUtils.getRelativeTimeSpanString(long_time, long_now, DateUtils.MINUTE_IN_MILLIS));
        return TIME;
    }

    @OnClick({R.id.linear_revisons, R.id.linear_category, R.id.linear_sub_category, R.id.img_camera, R.id.btn_back_arrow/*, R.id.edt_time*/, R.id.btn_gig_update, R.id.add_extragig, R.id.img_add_lang, R.id.radio_yes, R.id.radio_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_camera:
                cameradialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
           /* case R.id.edt_time:
                //DatePickerDialog();
                openDeliveryTimeDialog();
                break;*/
            case R.id.linear_revisons:
               // opencaryeardialog();
                break;
            case R.id.linear_category:
                //CategoryDialog();
                break;
            case R.id.linear_sub_category:

                if (GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory().size() > 0) {
                    //SubCategoryDialog();
                } else {
                    GlobalMethods.Toast(EditGigActivitytwo.this, "Subcategory not avilable");
                }
                break;
            case R.id.btn_gig_update:

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (validation()) {

                        callAddNewGIG();
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.add_extragig:
                extragigdialog();
                break;
            case R.id.img_add_lang:
                lang = edttags.getText().toString();
                if (!TextUtils.isEmpty(lang)) {
                    imageAddModelList.add(new ImageAddModel(null, lang));
                    languageAdapter.notifyDataSetChanged();
                    edttags.setText("");
                }
                break;
            case R.id.radio_yes:
                shipping = "2";
                shippingPriceLayout.setVisibility(View.VISIBLE);
                shippingview.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_no:
                shipping = "1";
                shippingPriceLayout.setVisibility(View.GONE);
                shippingview.setVisibility(View.GONE);
                break;

        }
    }

    private void callAddNewGIG() {

        String gig_tags = "";
        /*for (int i = 0; i < imageAddModelList.size(); i++) {
            if (gig_tags.equals("")) {
                gig_tags = imageAddModelList.get(i).getStatus();
            } else {
                gig_tags += "," + imageAddModelList.get(i).getStatus();
            }
        }*/

        gig_tags = edttags.getText().toString();
        String str_title = "I will" + " " + tittle;
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e(TAG, "AddEditGigREQ: " + "\nUserId: " + user_id + "\ngig_id: " + gig_id + "\ntittle:" + str_title + "\ncategory_id: " + category_id + "\nsub_category_id: " + sub_category_id + "\nimages: " + images
                + "\nprice: " + price + "\ndelivery_time: " + delivery_time + "\nShipping:: " + shipping + "\nshipping_price" + shippinprice + "\nrevisons:" + revisions + "\ndescription: " + description + "\ngig_staus" + gig_staus + "\ngig_tags" + gig_tags);
        Call<CommonResponse> call = apiService.callgigEditAPI(user_id, gig_id, str_title, category_id, sub_category_id, images, price, delivery_time, shipping, shippinprice, revisions, gig_tags, description, gig_staus);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e(TAG, "AddEditGigRESP: " + new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
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
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public boolean validation() {

        JSONObject objMainList1 = new JSONObject();
        //prepare item array for "A"
        JSONArray arrForA = new JSONArray();
        JSONObject itemA;
        try {
            for (int j = 0; j < imageList.size(); j++) {
                itemA = new JSONObject();
                itemA.put("file", imageList.get(j).getUrl());
                itemA.put("thumnail", imageList.get(j).getThumbnail());
                itemA.put("type", imageList.get(j).getType());
                arrForA.put(itemA);
            }
            objMainList1.put("image_list", arrForA);
            Log.e("arraylist", objMainList1.toString() + "");
            images = objMainList1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        category_name = txtCategory.getText().toString();
        sub_category_name = txtSubCategory.getText().toString();
        tittle = edtTitle.getText().toString();
        price = edtPrice.getText().toString();
        description = edtDescription.getText().toString();
        shippinprice = edtShippingprice.getText().toString();
        Log.e("Shippimg", shipping);

        if (edtTitle.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter title");
            return false;
        } else if (edtTitle.getText().toString().length() > 25) {
            GlobalMethods.Toast(context, "Title should not exceed 25 characters");
            return false;
        } else if (edtTitle.getText().toString().contains("I will")) {
            GlobalMethods.Toast(context, "Gig title have i will");
            return false;
        }else if (category_id.equals("0") || category_id.equals("")){
            GlobalMethods.Toast(context, "Choose Category");
            return false;
        } else if (sub_category_id.equals("0") || sub_category_id.equals("")) {
            GlobalMethods.Toast(context, "Choose Sub Category");
            return false;
        } else if (imageList.size() == 0) {
            GlobalMethods.Toast(context, "Please upload the images");
            return false;
        } else if (edtPrice.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter Price");
            return false;
        } else if (delivery_time.equals("")||delivery_time.equals("Delivery Time")){
            GlobalMethods.Toast(context, "Enter Delivery Time");
            return false;
        } else if (shipping.trim().equals("2") && edtShippingprice.getText().toString().trim().equals("")) {
            GlobalMethods.Toast(context, "Enter shipping price");
            return false;
        } else if (edttags.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Add Tags");
            return false;
        } else if (edtDescription.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter Description");
            return false;
        } else if (edtDescription.getText().toString().length() > 50) {
            GlobalMethods.Toast(context, "Description should not exceed 50 characters");
            return false;
        }

        return true;
    }

    private void UploadImage(File imageUri) {

        if (type.equals("1") || type.equals("2")) {
            progressDialog = ProgressDialog.show(context, "", "", true);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_progress);
        }

        Map<String, RequestBody> map = null;
        try {
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), imageUri);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "gig_image");
            map = new HashMap<>();
            map.put("file_name\"; filename=\"" + imageUri.getName(), mFile);
            map.put("file_type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<ImageUploadResponse> call = apiService.callUploadImageAPI(map);
        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                Log.e("UploadImageResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    ImageUploadResponse resp = response.body();
                    if (resp.getStatus().equals("1")) {

                        if (type.equals("2")) {
                            imageList.add(new MultipleImageModel(response.body().getFileName(), response.body().getFileName(), type, "0"));
                            if (GlobalMethods.isNetworkAvailable(context)) {
                                type = "3";
                                UploadImage(thumb_file);
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }
                        } else if (type.equals("1")) {
                            progressDialog.dismiss();
                            imageList.add(new MultipleImageModel(response.body().getFileName(), response.body().getFileName(), type, response.body().getFileName()));
                            setImage();
                        } else if (type.equals("3")) {
                            progressDialog.dismiss();
                            imageList.get(imageList.size() - 1).setThumbnail(resp.getFileName());
                            setImage();
                        }

                    } else {
                        GlobalMethods.Toast(context, resp.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("UploadImageResp", t.getMessage());
            }
        });
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

    private void CategoryDialog() {

        stringList = new ArrayList<>();
        stringListSub = new ArrayList<>();

        stringList.add("Category");
        for (int i = 0; i < GlobalVariables.dashmainCategorylist.size(); i++) {
            stringList.add(GlobalVariables.dashmainCategorylist.get(i).getMainCategoryName());
        }
        adapter = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringList);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getSelectedItem().toString();
                main_position = position;
                if (position == 0) {
                    category_id = "0";
                    stringListSub = new ArrayList<>();
                    stringListSub.add("Sub Category");
                    adapterSub = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringListSub);
                    adapterSub.setDropDownViewResource(R.layout.item_spinner_dropdown);
                    spinnerSubcategory.setAdapter(adapterSub);
                    Log.e("I am in", "main positon 0");
                } else {
                    category_id = GlobalVariables.dashmainCategorylist.get(position - 1).getMainCategoryId();

                    stringListSub = new ArrayList<>();
                    stringListSub.add("Sub Category");
                    Log.e("I am in", "main positon 1,2,3.." + GlobalVariables.dashmainCategorylist.get(position - 1).getSubCategory().size());
                    for (int i = 0; i < GlobalVariables.dashmainCategorylist.get(position - 1).getSubCategory().size(); i++) {
                        Log.e("I am in", "main positon 0");
                        stringListSub.add(GlobalVariables.dashmainCategorylist.get(position - 1).getSubCategory().get(i).getSubCategoryName());
                    }
                    adapterSub = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringListSub);
                    adapterSub.setDropDownViewResource(R.layout.item_spinner_dropdown);
                    spinnerSubcategory.setAdapter(adapterSub);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //sub adapter
        spinnerSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getSelectedItem().toString();
                if (position == 0) {
                    sub_category_id = "0";

                } else {
                    sub_category_id = GlobalVariables.dashmainCategorylist.get(main_position - 1).getSubCategory().get(position - 1).getSubCategoryId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        category = new Dialog(context);
        category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        category.setContentView(R.layout.dialog_category);
        category.setCancelable(true);
        category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // category.show();
        recycle_category = (RecyclerView) category.findViewById(R.id.recycler_category);
        ImageView img_close = (ImageView) category.findViewById(R.id.img_close);

        if (GlobalVariables.dashmainCategorylist.size() > 0) {
            categoryListAdapter = new CategoryListAdapter(context, GlobalVariables.dashmainCategorylist, itemClickListenerCategory);
            recycle_category.setAdapter(categoryListAdapter);
            recycle_category.setLayoutManager(new LinearLayoutManager(context));

        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category.dismiss();
            }
        });

    }

    private void SubCategoryDialog() {
        sub_category = new Dialog(context);
        sub_category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sub_category.setContentView(R.layout.dialog_sub_category);
        sub_category.setCancelable(true);
        sub_category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sub_category.show();
        recyle_subCategory = (RecyclerView) sub_category.findViewById(R.id.recycler_sub_category);
        ImageView img = (ImageView) sub_category.findViewById(R.id.img_close);

        if (GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory().size() > 0) {

            subcategoryListAdapter = new SubcategoryListAdapter(context, GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory(), itemClickListenerSubcategory);
            recyle_subCategory.setAdapter(subcategoryListAdapter);
            recyle_subCategory.setLayoutManager(new LinearLayoutManager(context));
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("cloase", "sub");
                sub_category.dismiss();
            }
        });
    }

    private void opencaryeardialog() {
        revisons = new Dialog(context);
        revisons.requestWindowFeature(Window.FEATURE_NO_TITLE);
        revisons.setContentView(R.layout.dialog_revisons);
        revisons.setCancelable(true);
        revisons.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        revisons.show();
        recycle_revisions = (RecyclerView) revisons.findViewById(R.id.recycler_revisons);
        ImageView img_close = (ImageView) revisons.findViewById(R.id.img_close);

        revisonsAdapter = new RevisonsAdapter(context, revisonsModelList, itemClickListener3);
        recycle_revisions.setAdapter(revisonsAdapter);
        recycle_revisions.setLayoutManager(new LinearLayoutManager(context));

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revisons.dismiss();
            }
        });
    }

    private void DatePickerDialog() {

        final Calendar c = Calendar.getInstance();
        Date SelectedDate = c.getTime();
        DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                int months = monthOfYear + 1;

                delivery_time = (year + "-" + (String.format("%02d", months)) + "-" + String.format("%02d", dayOfMonth));

                //edtTime.setText(delivery_time);


            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void extragigdialog() {
        extragig = new Dialog(EditGigActivitytwo.this);
        extragig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        extragig.setContentView(R.layout.dialog_extragig);
        extragig.setCancelable(false);
        extragig.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        extragig.show();

        ImageView close = (ImageView) extragig.findViewById(R.id.img_close);
        final EditText title = (EditText) extragig.findViewById(R.id.gigtitle);
        final EditText desc = (EditText) extragig.findViewById(R.id.desc);
        final Spinner cost = (Spinner) extragig.findViewById(R.id.spinner_cost);
        final Spinner days = (Spinner) extragig.findViewById(R.id.spinner_days);
        lineartitle = (LinearLayout) extragig.findViewById(R.id.lineartitle);
        linearview = (View) extragig.findViewById(R.id.lineraview);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(EditGigActivitytwo.this, R.array.cost, R.layout.item_spinner_dropdown2);
        adapter5.setDropDownViewResource(R.layout.item_spinner_dropdown);
        cost.setAdapter(adapter5);

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(EditGigActivitytwo.this, R.array.noofdays, R.layout.item_spinner_dropdown2);
        adapter6.setDropDownViewResource(R.layout.item_spinner_dropdown);
        days.setAdapter(adapter6);
        Button btnsubmit = (Button) extragig.findViewById(R.id.btn_submit);
        Button btn_add = (Button) extragig.findViewById(R.id.btn_add);
        final RecyclerView recyclerView = (RecyclerView) extragig.findViewById(R.id.recycler_extragig);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enter_value = title.getText().toString().trim();
                String next_value = desc.getText().toString().trim();
                String cost1 = cost.getSelectedItem().toString();
                String days1 = days.getSelectedItem().toString();
                if (!enter_value.equals("")) {
                    gigModels.add(new ExtraGigModel(enter_value, next_value, cost1, days1));
                    title.setText("");
                    desc.setText("");
                    lineartitle.setVisibility(View.VISIBLE);
                    linearview.setVisibility(View.VISIBLE);

                    extraGigAdapter = new ExtraGigAdapter(EditGigActivitytwo.this, gigModels, itemClickListener);
                    recyclerView.setAdapter(extraGigAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(EditGigActivitytwo.this));

                } else {
                    GlobalMethods.Toast(EditGigActivitytwo.this, "Enter text");
                }
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extragig.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extragig.dismiss();
            }
        });

    }

    private void init() {


        stringListRevision = new ArrayList<>();
        stringListDeliveryTime = new ArrayList<>();

        stringListRevision.add("Revision");
        stringListRevision.add("1");
        stringListRevision.add("2");
        stringListRevision.add("3");
        stringListRevision.add("4");
        stringListRevision.add("5");
        stringListRevision.add("6");
        stringListRevision.add("7");

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

        adapterRevision = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringListRevision);
        adapterRevision.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerRevisions.setAdapter(adapterRevision);
        spinnerRevisions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getSelectedItem().toString();
                revisions = selected;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapterDeliveryTime = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown2, stringListDeliveryTime);
        adapterDeliveryTime.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerDeliveryTime.setAdapter(adapterDeliveryTime);
        spinnerDeliveryTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getSelectedItem().toString();

                delivery_time = selected;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gigModels = new ArrayList<>();

        imageAddModelList = new ArrayList<>();
        revisonsModelList = new ArrayList<>();
        delivertTimeModelList = new ArrayList<>();

        revisonsModelList.add(new RevisonsModel("1"));
        revisonsModelList.add(new RevisonsModel("2"));
        revisonsModelList.add(new RevisonsModel("3"));
        revisonsModelList.add(new RevisonsModel("4"));
        revisonsModelList.add(new RevisonsModel("5"));
        revisonsModelList.add(new RevisonsModel("6"));
        revisonsModelList.add(new RevisonsModel("7"));

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
        delivertTimeModelList.add(new DeliveryModel("30"));
    }

    private void cameradialog() {
        camera_dialog = new Dialog(EditGigActivitytwo.this);
        camera_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        camera_dialog.setContentView(R.layout.dialog_photo_video);
        camera_dialog.setCancelable(false);
        camera_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        camera_dialog.show();
        TextView txt_gallery = (TextView) camera_dialog.findViewById(R.id.txt_gallery);
        TextView txt_take_photo = (TextView) camera_dialog.findViewById(R.id.txt_take_photo);
        TextView txt_take_video = (TextView) camera_dialog.findViewById(R.id.txt_take_video);
        TextView txt_uploadvideo = (TextView) camera_dialog.findViewById(R.id.txt_upload_video);
        TextView btn_cancel = (Button) camera_dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_dialog.dismiss();

            }
        });

        txt_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_dialog.dismiss();
                galleryIntent();
            }
        });
        txt_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera_dialog.dismiss();
                cameraIntent();
            }
        });

        txt_take_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(intent, REQUEST_VIDEO);
            }
        });

        txt_uploadvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_VIDEO);

            }
        });

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        String filePath = GlobalMethods.getRealPathFromURIPath(uri, EditGigActivitytwo.this);
                        Log.e("SELECT_FILE", filePath);
                        File file = new File(filePath);
                        if (GlobalMethods.isNetworkAvailable(context)) {
                            //1 - Image 2 - Video
                            type = "1";
                            UploadImage(file);
                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == REQUEST_CAMERA) {

                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);
                        ExifInterface ei = new ExifInterface(f.getAbsolutePath());
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }

                        Uri uri = GlobalMethods.getImageUri(context, rotatedBitmap);
                        String filepath = GlobalMethods.getRealPathFromURI(context, uri);
                        File file2 = new File(filepath);
                        if (GlobalMethods.isNetworkAvailable(context)) {
                            type = "1";
                            UploadImage(file2);
                        } else {
                            GlobalMethods.Toast(context, getString(R.string.internet));
                        }
                        String path = Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "Phoenix" + File.separator + "default";
                        f.delete();
                        OutputStream outFile = null;
                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (requestCode == SELECT_VIDEO) {
                    uri = data.getData();
                    String realPath = FilePath.getPath(EditGigActivitytwo.this, data.getData());
                    Log.e("SELECT_VIDEO", realPath + " " + uri);

                    File file = new File(realPath);
                    setThumb(uri);

                    if (GlobalMethods.isNetworkAvailable(context)) {
                        type = "2";
                        UploadImage(file);
                    } else {
                        GlobalMethods.Toast(context, getString(R.string.internet));
                    }

                } else if (requestCode == REQUEST_VIDEO) {
                    uri = data.getData();
                    String realPath = FilePath.getPath(EditGigActivitytwo.this, data.getData());
                    Log.e("REQUEST_VIDEO", realPath);
                    Bitmap thumb = ThumbnailUtils.createVideoThumbnail(realPath, MediaStore.Video.Thumbnails.MINI_KIND);

                    File file = new File(realPath);

                    setThumb(uri);

                    if (GlobalMethods.isNetworkAvailable(context)) {
                        type = "2";
                        UploadImage(file);
                    } else {
                        GlobalMethods.Toast(context, getString(R.string.internet));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setThumb(Uri videouri) {

        Log.e("SELECT_VIDEO", "THUMB IN");

        long fileId = GlobalMethods.getFileId(EditGigActivitytwo.this, videouri);
        Log.e("SELECT_VIDEO", fileId + "nan");
        MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), fileId, MediaStore.Video.Thumbnails.MINI_KIND, null);
        Cursor thumbCursor = null;
        try {
            thumbCursor = managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = " + fileId, null, null);
            if (thumbCursor.moveToFirst()) {
                String thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                String videopath = thumbPath;
                Log.e("value", videopath);
                thumb_file = new File(videopath);
                Log.e("SELECT_VIDEO", thumb_file.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SELECT_VIDEO", e.getMessage());
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void setImage() {
        if (imageList.size() > 0) {

            Log.e("image", "list" + imageList.size());
            recyclePhoto.setVisibility(View.VISIBLE);
            newImageAddAdapter = new NewImageAddAdapter(context, imageList, 1);
            recyclePhoto.setAdapter(newImageAddAdapter);

            recyclePhoto.setLayoutManager(new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false));
            recyclePhoto.smoothScrollToPosition(newImageAddAdapter.getItemCount() - 1);
            horizantalSCroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        }
    }

    private void callMultiplePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE EXTERNAL STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ EXTERNAL STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add(" CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("ACCESS COARSE LOCATION");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("ACCESS FINE LOCATION");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                if (Build.VERSION.SDK_INT >= 23) {
                    // Marshmallow+
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                } else {
                    // Pre-Marshmallow
                }

                return;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            } else {
                // Pre-Marshmallow
            }

            return;
        }

    }

    /**
     * add Permissions
     *
     * @param permissionsList
     * @param permission
     * @return
     */
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        } else {
            // Pre-Marshmallow
        }

        return true;
    }

    /**
     * Permissions results
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION and others

                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                } else {
                    // Permission Denied
                    Toast.makeText(EditGigActivitytwo.this, "Permission is Denied", Toast.LENGTH_SHORT)
                            .show();

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
