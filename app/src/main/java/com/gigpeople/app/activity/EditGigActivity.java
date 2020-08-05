package com.gigpeople.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.ExtraGigAdapter;
import com.gigpeople.app.adapter.LanguageAdapter;
import com.gigpeople.app.adapter.NewImageAddAdapter;
import com.gigpeople.app.model.ExtraGigModel;
import com.gigpeople.app.model.ImageAddModel;
import com.gigpeople.app.utils.GlobalMethods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class EditGigActivity extends AppCompatActivity {


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
    /* @BindView(R.id.spinner_min)
     Spinner spinnerMin;
     @BindView(R.id.spinner_max)
     Spinner spinnerMax;*/
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
    /* @BindView(R.id.spinner_time)
     Spinner spinnerTime;*/
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
    @BindView(R.id.edt_time)
    EditText edtTime;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, TAKEVIDEO = 2, REQUEST_VIDEO_CAPTURE = 3;
    private Uri uri;
    private Bitmap bitmap;
    List<ImageAddModel> imageAddModelList;
    NewImageAddAdapter newImageAddAdapter;
    String path;
    Uri yourUri;
    Calendar calendar;
    int year, month, day, hour, minute;
    String selected_date, selected_time;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String gig_index;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gig);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        calendar = Calendar.getInstance();
        Window window = EditGigActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(EditGigActivity.this, R.color.colorPrimaryDark));
        }
        gigModels = new ArrayList<>();

        imageAddModelList = new ArrayList<>();
        itemClickListener = new ExtraGigAdapter.ItemClickListener() {
            public void itemClick() {
                if (gigModels.size() == 0) {
                    lineartitle.setVisibility(View.GONE);
                    linearview.setVisibility(View.GONE);
                }

              /*  if (gigModels.size()<3){
                    if (edtcomment.isEnabled()){

                    }else {
                        edtcomment.setEnabled(true);
                        btn_add.setClickable(true);
                    }
                }*/
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
        imageAddModelList = new ArrayList<>();
        ArrayAdapter adapter = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.category, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerCategory.setAdapter(adapter);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.subcategory, R.layout.item_spinner_dropdown2);
        adapter1.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerSubcategory.setAdapter(adapter1);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.price, R.layout.item_spinner_dropdown2);
        adapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerPrice.setAdapter(adapter2);
       /* ArrayAdapter adapter2 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.minprice, R.layout.item_spinner_dropdown2);
        adapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerMin.setAdapter(adapter2);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.maxprice, R.layout.item_spinner_dropdown2);
        adapter3.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerMax.setAdapter(adapter3);*/

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.revisions, R.layout.item_spinner_dropdown2);
        adapter4.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerRevisions.setAdapter(adapter4);


        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.cost, R.layout.item_spinner_dropdown2);
        adapter5.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerCost.setAdapter(adapter5);


        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.noofdays, R.layout.item_spinner_dropdown2);
        adapter6.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerDays.setAdapter(adapter6);
       /* ArrayAdapter adapter7 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.deliverytime, R.layout.item_spinner_dropdown2);
        adapter7.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerTime.setAdapter(adapter7);*/

        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            callMultiplePermissions();
        } else {
            // Pre-Marshmallow
        }
    }

    private void callMultiplePermissions() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
       /* if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE))
            permissionsNeeded.add("NETWORK STATE");
       */
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

              /*  perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&*/

                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted

                } else {
                    // Permission Denied
                    Toast.makeText(EditGigActivity.this, "Permission is Denied", Toast.LENGTH_SHORT)
                            .show();

                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick({R.id.img_camera, R.id.btn_back_arrow,R.id.edt_time, R.id.btn_gig_update, R.id.add_extragig, R.id.img_add_lang, R.id.radio_yes, R.id.radio_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_camera:
                cameradialog();
                break;
            case R.id.btn_back_arrow:
                onBackPressed();
                break;

            case R.id.edt_time:
                getPickDate();
                break;


            case R.id.btn_gig_update:
                Intent intent1 = new Intent(EditGigActivity.this, MainActivity.class);
                intent1.putExtra("page", "1");
                editor.putString("gig_index", "0");
                editor.commit();
                startActivity(intent1);
                break;
            case R.id.add_extragig:
                //extragiglayout.setVisibility(View.VISIBLE);
                extragigdialog();
                break;
            case R.id.img_add_lang:

                lang = edttags.getText().toString();
                Log.e("LANG", lang);

                if (!TextUtils.isEmpty(lang)) {
                    imageAddModelList.add(new ImageAddModel(null, lang));
                    edttags.setText("");

                    if (imageAddModelList.size() > 0) {
                        Log.e("TAG", "TRUW");
                        recycler.setVisibility(View.VISIBLE);
                        languageAdapter = new LanguageAdapter(EditGigActivity.this, imageAddModelList);
                        recycler.setLayoutManager(new GridLayoutManager(EditGigActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));

                        recycler.setAdapter(languageAdapter);
                    } else {
                        recycler.setVisibility(View.GONE);

                    }

                } else {
                    //Toast.makeText(getApplicationContext(),"Tag is Empty",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.radio_yes:
                shippingPriceLayout.setVisibility(View.VISIBLE);
                shippingview.setVisibility(View.VISIBLE);
                break;

            case R.id.radio_no:
                shippingPriceLayout.setVisibility(View.GONE);
                shippingview.setVisibility(View.GONE);
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

        datePickerDialog = new DatePickerDialog(EditGigActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                PickDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                edtTime.setText(PickDate);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void extragigdialog() {
        extragig = new Dialog(EditGigActivity.this);
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
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.cost, R.layout.item_spinner_dropdown2);
        adapter5.setDropDownViewResource(R.layout.item_spinner_dropdown);
        cost.setAdapter(adapter5);


        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(EditGigActivity.this, R.array.noofdays, R.layout.item_spinner_dropdown2);
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

                    extraGigAdapter = new ExtraGigAdapter(EditGigActivity.this, gigModels, itemClickListener);
                    recyclerView.setAdapter(extraGigAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(EditGigActivity.this));
                    /*if (must_haves_list.size() >= 3) {
                        btn_add.setClickable(false);
                        edtcomment.setEnabled(false);
                    }*/
                } else {
                    GlobalMethods.Toast(EditGigActivity.this, "Enter text");
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


    private void cameradialog() {
        camera_dialog = new Dialog(EditGigActivity.this);
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

                dispatchTakeVideoIntent();
            }
        });

        txt_uploadvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_dialog.dismiss();
                videointent();
            }
        });

    }

    private void videointent() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, TAKEVIDEO);

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        imageAddModelList.add(new ImageAddModel(bitmap, "0"));
                        Log.e("SIZE", imageAddModelList.size() + "");


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("ERROR", e.getMessage());
                    }

                } else if (requestCode == REQUEST_CAMERA) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageAddModelList.add(new ImageAddModel(photo, "0"));

                } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
                    imageAddModelList.add(new ImageAddModel(null, "1"));

                } else if (requestCode == TAKEVIDEO) {
                    imageAddModelList.add(new ImageAddModel(null, "1"));

                }


                if (imageAddModelList.size() > 0) {
                    Log.e("TAG", "TRUW");
                    recyclePhoto.setVisibility(View.VISIBLE);
                   // newImageAddAdapter = new NewImageAddAdapter(EditGigActivity.this, imageAddModelList);

                    recyclePhoto.setAdapter(newImageAddAdapter);
                    recyclePhoto.setLayoutManager(new GridLayoutManager(EditGigActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
                    recyclePhoto.smoothScrollToPosition(newImageAddAdapter.getItemCount() - 1);
                    horizantalSCroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void onCaptureImageResult(Intent data) {
         /* uri = data.getData();
                    bitmap = (Bitmap) data.getExtras().get("data");*/
        Bitmap thumbnail = null;
        ByteArrayOutputStream bytes = null;
        File destination = null;
        FileOutputStream fo;
        try {
            thumbnail = (Bitmap) data.getExtras().get("data");
            bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            System.out.println("path capture" + destination.toString());

            Log.e("TAG", "Getting PAth" + destination.toString());

            yourUri = Uri.fromFile(destination);
            path = yourUri.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // imgLicense.setImageBitmap(thumbnail);
        try {
            sendThumbnail(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendThumbnail(Bitmap thumbnail) {
        try {


            String path = MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, "Title", null);
            Uri imageUri = Uri.parse(path);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //  imgLicense.setImageBitmap(bm);
        try {
            sendBitmap(bm);
            Uri selectedImageURI = data.getData();
            path = selectedImageURI.toString();
            System.out.println("test gallery path : " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sendBitmap(Bitmap bm) {
        try {

            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bm, "Title", null);
            Uri imageUri = Uri.parse(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
