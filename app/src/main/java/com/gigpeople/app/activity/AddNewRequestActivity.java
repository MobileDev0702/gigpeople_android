package com.gigpeople.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.CategoryListAdapter;
import com.gigpeople.app.adapter.DeliveryTimeAdapter;
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
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
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

public class AddNewRequestActivity extends AppCompatActivity {


    Dialog camera_dialog;
    @BindView(R.id.btn_back_arrow)
    Button btnBackArrow;
    @BindView(R.id.txt_title)
    TextView txtTitle;
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
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.spinner_time)
    Spinner spinnerTime;
    @BindView(R.id.edt_date)
    EditText edtDate;
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
    @BindView(R.id.edt_quantity)
    EditText edtQuantity;
    @BindView(R.id.linear_category)
    LinearLayout linearCategory;
    @BindView(R.id.linear_sub_category)
    LinearLayout linearSubCategory;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri uri;
    private Bitmap bitmap;
    NewImageAddAdapter newImageAddAdapter;
    Calendar calendar;
    int year, month, day;
    DatePickerDialog datePickerDialog;
    Dialog category, sub_category,deliveryTimeDialog;
    Context context;
    RecyclerView recycle_category, recyle_subCategory;
    CategoryListAdapter categoryListAdapter;
    SubcategoryListAdapter subcategoryListAdapter;
    CategoryListAdapter.ItemClickListener itemClickListenerCategory;
    SubcategoryListAdapter.ItemClickListener itemClickListenerSubcategory;
    String category_name, sub_category_id, category_id, sub_category_name;
    int category_position;
    Bitmap rotatedBitmap = null;
    List<MultipleImageModel> imageList = new ArrayList<>();
    ProgressDialog progressDialog;
    String user_id, images, quantity, price, delivery_time, description;
    public static final String TAG = AddNewRequestActivity.class.getSimpleName();
    ApiService apiService;
    ArrayList<String> image_array;

    List<DeliveryModel> delivertTimeModelList;
    DeliveryTimeAdapter deliveryTimeAdapter;
    DeliveryTimeAdapter.ItemClickListener deliveryItemClickListener;

    List<String> stringListSub, stringList, stringListRevision, stringListDeliveryTime;
    int main_position;
    ArrayAdapter<String> adapter, adapterSub, adapterRevision, adapterDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_request);
        ButterKnife.bind(this);
        context = AddNewRequestActivity.this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        calendar = Calendar.getInstance();
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        init();

        setImage();

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

        deliveryItemClickListener=new DeliveryTimeAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name) {
                if (name.equals("1")) {
                    edtDate.setText(name + " day");
                }else {
                    edtDate.setText(name + " days");
                }
                deliveryTimeDialog.dismiss();
               // delivery_time = name;
            }
        };

        itemClickListenerSubcategory = new SubcategoryListAdapter.ItemClickListener() {
            @Override
            public void itemClick(String name, String id) {

                txtSubCategory.setText(name);
                sub_category.dismiss();
                sub_category_id = id;
            }
        };

    }

    @OnClick({R.id.linear_category, R.id.linear_sub_category, R.id.img_camera, R.id.btn_back_arrow, R.id.btn_submit, R.id.edt_date, R.id.img_sub, R.id.img_category})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_camera:
                cameradialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;
            case R.id.edt_date:
               // DatePickerDialog();
                //openDeliveryTimeDialog();
                break;
            case R.id.img_sub:
                //SubCategoryDialog();
                break;
            case R.id.img_category:
                //CategoryDialog();
                break;
            case R.id.linear_category:
                //CategoryDialog();
                break;
            case R.id.linear_sub_category:
                //SubCategoryDialog();
                break;
            case R.id.btn_submit:
                image_array = new ArrayList<String>();
                for (int i = 0; i < imageList.size(); i++) {

                    image_array.add(imageList.get(i).getImage());
                    images = image_array.toString().replace("[", "").replace("]", "").replace(" ", "");
                }

                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (validation()) {
                        callAddNewRequest();
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
        }
    }

    private void callAddNewRequest() {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e(TAG, "AddNewRequestREQ: " + "\nUserId: " + user_id + "\ncategory_id: " + category_id + "\nsub_category_id: " + sub_category_id + "\nimages: " + images + "\nquantity: " + quantity
                + "\nprice: " + price + "\ndelivery_time: " + delivery_time + "\ndescription: " + description);
        Call<CommonResponse> call = apiService.callrequestAddAPI(user_id, category_id, sub_category_id, images, quantity, price, delivery_time, description);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e(TAG, "AddNewRequestRESP: " + new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                           /* Intent submit = new Intent(AddNewRequestActivity.this, MainActivity.class);
                            submit.putExtra("page", "5");
                            startActivity(submit);*/
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

        category_name = txtCategory.getText().toString();
        sub_category_name = txtSubCategory.getText().toString();
        price = edtPrice.getText().toString();
        quantity = edtQuantity.getText().toString();
        description = edtDescription.getText().toString();
        if (category_id.equals("0") || category_id.equals("")) {
            GlobalMethods.Toast(context, "Choose Category");
            return false;
        } else if (sub_category_id.equals("0") || sub_category_id.equals("")) {
            GlobalMethods.Toast(context, "Choose Sub Category");
            return false;
        } else if (imageList.size() == 0) {
            GlobalMethods.Toast(AddNewRequestActivity.this, "Please upload the images");
            return false;
        } else if (edtPrice.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter Price");
            return false;
        } else if (edtQuantity.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter Quantity");
            return false;
        } else if (delivery_time.equals("0") || delivery_time.equals("Delivery Time")) {
            GlobalMethods.Toast(context, "Enter Delivery Time");
            return false;
        } else if (edtDescription.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter Description");
            return false;
        }else if (edtDescription.getText().toString().length()>50) {
            GlobalMethods.Toast(context, "Description exceed 50 letters");
            return false;
        }

        return true;
    }

    private void CategoryDialog() {

        category = new Dialog(context);
        category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        category.setContentView(R.layout.dialog_category);
        category.setCancelable(true);
        category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        category.show();

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
        ImageView img_close = (ImageView) sub_category.findViewById(R.id.img_close);


        if (GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory().size() > 0) {
            subcategoryListAdapter = new SubcategoryListAdapter(context, GlobalVariables.dashmainCategorylist.get(category_position).getSubCategory(), itemClickListenerSubcategory);
            recyle_subCategory.setAdapter(subcategoryListAdapter);
            recyle_subCategory.setLayoutManager(new LinearLayoutManager(context));
        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_category.dismiss();
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

    private void DatePickerDialog() {

        final Calendar c = Calendar.getInstance();
        Date SelectedDate = c.getTime();
        DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        String StringDateformat_US = dateformat_US.format(SelectedDate);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                int months = monthOfYear + 1;

                delivery_time = (year + "-" + (String.format("%02d", months)) + "-" + String.format("%02d", dayOfMonth));

                edtDate.setText(delivery_time);


            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void UploadImage(File imageUri) {

        Log.e("UploadImageReq", imageUri + " nan ");
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Map<String, RequestBody> map = null;
        try {
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), imageUri);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "request_image");
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
                        imageList.add(new MultipleImageModel(response.body().getFileName(), response.body().getFileName(), "", ""));
                        setImage();
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

    private void cameradialog() {
        camera_dialog = new Dialog(AddNewRequestActivity.this);
        camera_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        camera_dialog.setContentView(R.layout.dialog_photo);
        camera_dialog.setCancelable(false);
        camera_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        camera_dialog.show();
        TextView txt_gallery = (TextView) camera_dialog.findViewById(R.id.txt_gallery);
        TextView txt_take_photo = (TextView) camera_dialog.findViewById(R.id.txt_take_photo);
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

    }

    private void init() {

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

                delivery_time = selected;

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
        delivertTimeModelList.add(new DeliveryModel("30"));
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
                        //imgProfile.setImageBitmap(bitmap);
                        String filePath = GlobalMethods.getRealPathFromURIPath(uri, AddNewRequestActivity.this);
                        File file = new File(filePath);
                        // Glide.with(EditProfileActivity.this).load(bitmap).into(imorgLogo);
                        if (GlobalMethods.isNetworkAvailable(context)) {
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
                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                        Log.e("path", f.getAbsolutePath());
                        ExifInterface ei = new ExifInterface(f.getAbsolutePath());
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

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

                        Uri uri = GlobalMethods.getImageUri(getApplicationContext(), rotatedBitmap);
                        String filepath = GlobalMethods.getRealPathFromURI(getApplicationContext(), uri);
                        File file2 = new File(filepath);
                        // Glide.with(EditProfileActivity.this).load(bitmap).into(imorgLogo);

                        if (GlobalMethods.isNetworkAvailable(context)) {
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

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            newImageAddAdapter = new NewImageAddAdapter(AddNewRequestActivity.this, imageList,2);
            recyclePhoto.setAdapter(newImageAddAdapter);

            recyclePhoto.setLayoutManager(new GridLayoutManager(AddNewRequestActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
            recyclePhoto.smoothScrollToPosition(newImageAddAdapter.getItemCount() - 1);
            horizantalSCroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        }
    }

}
