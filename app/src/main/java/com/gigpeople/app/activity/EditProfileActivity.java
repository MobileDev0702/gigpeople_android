package com.gigpeople.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.LanguageAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.ImageUploadResponse;
import com.gigpeople.app.apiModel.LoginResponse;
import com.gigpeople.app.apiModel.ProfileViewResponse;
import com.gigpeople.app.model.ImageAddModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.GlobalVariables;
import com.gigpeople.app.utils.PrefConnect;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.switch_status)
    Switch switchStatus;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.edt_languages)
    EditText edtLanguages;
    @BindView(R.id.recyclePhoto)
    RecyclerView recyclePhoto;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    /*@BindView(R.id.places_autocomplete_address)
    PlacesAutocompleteTextView placesAutocompleteAddress;*/
    @BindView(R.id.edt_firstname)
    EditText edtFirstname;
    @BindView(R.id.edt_lastname)
    EditText edtLastname;
    @BindView(R.id.edt_about)
    EditText edtAbout;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.img_plus)
    ImageView imgPlus;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.img_add_lang)
    ImageView imgAddLang;
    @BindView(R.id.layouttwo)
    LinearLayout layouttwo;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    AutocompleteSupportFragment autocompleteFragment;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
   /* @BindView(R.id.edt_skills)
    EditText edtSkills;*/

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.edt_other_link)
    EditText edtLink;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri uri;
    private Bitmap bitmap;
    Dialog camera_dialog;
    Bitmap rotatedBitmap = null;
    int switch_status = 1;
    List<ImageAddModel> imageAddModelList;
    LanguageAdapter languageAdapter;
    String lang, user_id, str_image, location = "", lattitude, longitude, languages = "", about = "", otherlink = "", first_name, last_name, email, mobile, mobile_country_code, country, skills;
    ProgressDialog progressDialog;
    ApiService apiService;
    private static final String TAG = EditProfileActivity.class.getSimpleName();
    Geocoder geocoder;
    List<Address> addresses;
    ArrayAdapter<String> adapter;
    List<String> stringList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = EditProfileActivity.this;
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);
        ButterKnife.bind(this);
        setBookingLocation();
        Window window = EditProfileActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(EditProfileActivity.this, R.color.colorPrimaryDark));
        }

        imageAddModelList = new ArrayList<>();
        languageAdapter = new LanguageAdapter(EditProfileActivity.this, imageAddModelList);
        recyclePhoto.setLayoutManager(new GridLayoutManager(EditProfileActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
        recyclePhoto.setAdapter(languageAdapter);

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

        if (GlobalMethods.isNetworkAvailable(context)) {
            setProfile();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        stringList = new ArrayList<>();

        for (int i = 0; i < GlobalVariables.dashmainCategorylist.size(); i++) {
            stringList.add(GlobalVariables.dashmainCategorylist.get(i).getMainCategoryName());
            for (int j = 0; j < GlobalVariables.dashmainCategorylist.get(i).getSubCategory().size(); j++) {
                stringList.add(GlobalVariables.dashmainCategorylist.get(i).getSubCategory().get(j).getSubCategoryName());
            }
        }


        adapter = new ArrayAdapter<String>(context, R.layout.item_spinner_dropdown, stringList);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

    }

    @OnClick({R.id.im_back, R.id.switch_status, R.id.img_plus, R.id.btn_update, R.id.img_add_lang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.switch_status:
                break;
            case R.id.img_plus:
                setVerifiedDialog();
                break;
            case R.id.btn_update:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (validation()) {
                        setUpdateProfile();
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
            case R.id.img_add_lang:
                lang = edtLanguages.getText().toString();
                if (!TextUtils.isEmpty(lang)) {
                    imageAddModelList.add(new ImageAddModel(null, lang));
                    languageAdapter.notifyDataSetChanged();
                    edtLanguages.setText("");
                }
                break;
        }
    }

    private void setUpdateProfile() {
        first_name = edtFirstname.getText().toString();
        last_name = edtLastname.getText().toString();
        email = edtEmail.getText().toString();
        mobile_country_code = ccp.getSelectedCountryCode();
        mobile = edtPhone.getText().toString();
        mobile = mobile_country_code + " " + mobile;
        about = edtAbout.getText().toString();
        otherlink = edtLink.getText().toString();
        skills = autoCompleteTextView.getText().toString();
        String languages_api = "";
        for (int i = 0; i < imageAddModelList.size(); i++) {
            if (languages_api.equals("")) {
                languages_api = imageAddModelList.get(i).getStatus();
            } else {
                languages_api += "," + imageAddModelList.get(i).getStatus();
            }
        }
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Log.e(TAG, "UpdateProfileReq: " + "\nUserId: " + user_id + "\nFirstName: " + first_name + "\nLatName: " + last_name + "\nEmail: " + email + "\nMobileNumber: " + mobile
                + "\nAddress: " + location + "\nLattitude: " + lattitude + "\nLongitude: " + longitude + "\nCountry: " + country + "\nProfileImage: " + str_image
                + "\nLanguages: " + languages_api + "\nAbout: " + about + "\nSkills: " + skills);
        Call<LoginResponse> call = apiService.callEditProfileAPI(user_id, first_name, last_name, email, mobile, location, lattitude, longitude, country, str_image, languages_api, about, skills, otherlink);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG, "UpdateProfileResp: " + new Gson().toJson(response.body()));
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    LoginResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            PrefConnect.writeString(context, PrefConnect.FIRSTNAME, resp.getUserDetails().getFirstName());
                            PrefConnect.writeString(context, PrefConnect.LASTNAME, resp.getUserDetails().getLastName());
                            PrefConnect.writeString(context, PrefConnect.EMAIL, resp.getUserDetails().getEmail());
                            PrefConnect.writeString(context, PrefConnect.IMAGE_URL, resp.getUserDetails().getProfileImageUrl());

                            PrefConnect.writeString(context, PrefConnect.PROFILE_LOCATION, resp.getUserDetails().getAddress());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LAT, resp.getUserDetails().getLattitude());
                            PrefConnect.writeString(context, PrefConnect.PROFILE_LONG, resp.getUserDetails().getLongitude());

                            finish();
                        } else {
                            GlobalMethods.Toast(context, resp.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "UpdateProfileResp: " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    public boolean validation() {
        if (edtFirstname.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter first name");
            return false;
        } else if (edtLastname.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter last name");
            return false;
        } else if (edtEmail.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter emailId");
            return false;
        } else if (!edtEmail.getText().toString().contains("@")) {
            GlobalMethods.Toast(context, "Enter valid emailId");
            return false;
        } else if (autoCompleteTextView.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter skills");
            return false;
        } else if (edtPhone.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter mobile number");
            return false;
        } else if (location.equals("")) {
            GlobalMethods.Toast(context, "Enter Address");
            return false;
        } else if (imageAddModelList.size() == 0) {
            GlobalMethods.Toast(context, "Add languages");
            return false;
        } else if (edtAbout.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter about yourself");
            return false;
        }
        return true;
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
                            first_name = resp.getUserDetails().getFirstName();
                            last_name = resp.getUserDetails().getLastName();
                            email = resp.getUserDetails().getEmail();
                            mobile = resp.getUserDetails().getPhoneNo();
                            mobile_country_code = resp.getUserDetails().getMobileCountry();
                            location = resp.getUserDetails().getAddress();
                            lattitude = resp.getUserDetails().getLattitude();
                            longitude = resp.getUserDetails().getLongitude();
                            about = resp.getUserDetails().getAbout();
                            otherlink = resp.getUserDetails().getLink();
                            languages = resp.getUserDetails().getLanguage();
                            str_image = resp.getUserDetails().getProfile();
                            skills = resp.getUserDetails().getSkills();

                            edtFirstname.setText(first_name);
                            edtLastname.setText(last_name);
                            edtEmail.setText(email);
                            autoCompleteTextView.setText(skills);
                            if (!mobile.equals("")) {
                                edtPhone.setText(mobile);
                                ccp.setCountryForPhoneCode(Integer.parseInt(mobile_country_code));
                                edtAbout.setText(about);
                                edtLink.setText(otherlink);
                                autocompleteFragment.setText(location);
                                //for language list
                                String TEMP[] = languages.split(",");
                                for (int i = 0; i < TEMP.length; i++) {
                                    imageAddModelList.add(new ImageAddModel(null, TEMP[i]));
                                }
                                languageAdapter.notifyDataSetChanged();
                            }
                            if (!str_image.trim().equals("")) {
                                Glide.with(context).load(resp.getUserDetails().getProfileImageUrl()).into(imgProfile);
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

    private void UploadImage(File imageUri) {

        Log.e("UploadImageReq", imageUri + " nan ");
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Map<String, RequestBody> map = null;
        try {
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), imageUri);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "profile_image");
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
                        GlobalMethods.Toast(context, resp.getMessage());
                        str_image = resp.getFileName();
                        Glide.with(context).load(resp.getFileUrl()).into(imgProfile);
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
                /*Log.e("Place", place.getName() + ", " + place.getId()+", "+place.getTypes().get(0));*/
                Log.e("Address", place.getAddress().toString());
                Log.e("Name", place.getName().toString());
                lattitude = place.getLatLng().latitude + "";
                longitude = place.getLatLng().longitude + "";
                location = place.getAddress().toString();

                getAddress(lattitude, longitude, location);

            }

            @Override
            public void onError(Status status) {
                Log.e("An error occurred: ", status + " ");
            }
        });
    }

    private void getAddress(String latitude, String longitude, String location) {

        try {
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            //address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

           /* Log.e("Address", addresses + " ");
            Log.e("FeatureName", addresses.get(0).getFeatureName() + " ");
            Log.e("AdminArea", addresses.get(0).getAdminArea() + " ");
            Log.e("SubAdminArea", addresses.get(0).getSubAdminArea() + " ");
            Log.e("Locality", addresses.get(0).getLocality() + " ");
            Log.e("SubLocality", addresses.get(0).getSubLocality() + " ");
            Log.e("Thoroughfare", addresses.get(0).getThoroughfare() + " ");
            Log.e("SubThoroughfare", addresses.get(0).getSubThoroughfare() + " ");
            Log.e("Premises", addresses.get(0).getPremises() + " ");
            Log.e("CountryCode", addresses.get(0).getCountryCode() + " ");
            Log.e("CountryName", addresses.get(0).getCountryName() + " ");
            Log.e("Latitude", addresses.get(0).getLatitude() + " ");
            Log.e("Longitude", addresses.get(0).getLongitude() + " ");
            Log.e("DisplayName", addresses.get(0).getLocale().getDisplayName() + " ");*/
            Log.e("country", addresses.get(0).getCountryName() + " ");

            country = addresses.get(0).getCountryName();

            setEditText(location);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage() + "nsn");
        }

    }

    private void setEditText(String location) {

        autocompleteFragment.setText(location);
    }

    private void setVerifiedDialog() {

        camera_dialog = new Dialog(EditProfileActivity.this);
        camera_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        camera_dialog.setContentView(R.layout.dialog_photo);
        camera_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        camera_dialog.show();
        camera_dialog.setCancelable(false);
        TextView txt_gallery = (TextView) camera_dialog.findViewById(R.id.txt_gallery);
        TextView txt_take_photo = (TextView) camera_dialog.findViewById(R.id.txt_take_photo);
        Button btn_cancel = (Button) camera_dialog.findViewById(R.id.btn_cancel);
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

                        String filePath = GlobalMethods.getRealPathFromURIPath(uri, EditProfileActivity.this);
                        Log.e("filePathGallery", filePath);
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


}
