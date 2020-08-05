package com.gigpeople.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.ImageUploadResponse;
import com.gigpeople.app.utils.FilePath;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesDeliveryActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.attachments)
    ImageView attachments;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Dialog camera_dialog;
    @BindView(R.id.edit_description)
    EditText editDescription;
    @BindView(R.id.image_attached)
    ImageView imageAttached;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_VIDEO = 2, REQUEST_VIDEO = 3;
    private Uri uri;
    private Bitmap bitmap;
    String path;
    Uri yourUri;
    public static String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA};
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    String user_id, order_id, buyer_id, project = "";
    ApiService apiService;
    ProgressDialog progressDialog;
    Bitmap rotatedBitmap = null;
    File thumb_file;
    String type, thumb_image = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_delivery);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = preferences.edit();
        Window window = SalesDeliveryActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SalesDeliveryActivity.this, R.color.colorPrimaryDark));
        }
        context = SalesDeliveryActivity.this;
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");
        apiService = RetrofitSingleton.createService(ApiService.class);
        if (getIntent() != null) {
            order_id = getIntent().getStringExtra("order_id");
            buyer_id = getIntent().getStringExtra("buyer_id");
        }
    }

    @OnClick({R.id.img_back, R.id.attachments, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.attachments:
                cameradialog();
                break;
            case R.id.btn_submit:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    if (validate()) {
                        setDelivery();
                    }
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
        }
    }

    private void setDelivery() {

        String description;
        description = editDescription.getText().toString();
        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);
        Log.e("DeliveryReq", "UserId: " + user_id + "\nOrderId: " + order_id + "\nbuyer_id: " + buyer_id + "\nproject: " + project + "\ndescription: " + description
                + "\nType: " + type+ "\nThumpImage: " + thumb_image);
        Call<CommonResponse> call = apiService.callsellerDelivery(user_id, order_id, buyer_id, project, description,type,thumb_image);// type 2 for seller
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progressDialog.dismiss();
                Log.e("DeliveryResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            GlobalMethods.Toast(context, resp.getMessage());
                            Intent intent = new Intent(SalesDeliveryActivity.this, MainActivity.class);
                            intent.putExtra("page", "3");
                            editor.putString("sales_index", "2");
                            editor.commit();
                            startActivity(intent);
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

    public boolean validate() {
        if (project.equals("")) {
            GlobalMethods.Toast(context, "Add Document");
            return false;
        } else if (editDescription.getText().toString().equals("")) {
            GlobalMethods.Toast(context, "Enter description");
            return false;
        }
        return true;
    }

    private void UploadImage(File imageUri) {

        Log.e("UploadImageReq", imageUri + " nan ");

        if (type.equals("1") || type.equals("2")) {
            progressDialog = ProgressDialog.show(context, "", "", true);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.dialog_progress);
        }

        Map<String, RequestBody> map = null;
        try {
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), imageUri);
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "project");
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

                            project = resp.getFileName();
                            if (GlobalMethods.isNetworkAvailable(context)) {
                                type = "3";
                                UploadImage(thumb_file);
                            } else {
                                GlobalMethods.Toast(context, getString(R.string.internet));
                            }
                        } else if (type.equals("1")) {

                            project = resp.getFileName();
                            Glide.with(context).load(resp.getFileUrl()).into(imageAttached);
                            progressDialog.dismiss();
                        } else if (type.equals("3")) {

                            thumb_image = resp.getFileName();
                            Glide.with(context).load(resp.getFileUrl()).into(imageAttached);
                            progressDialog.dismiss();
                            type="2";
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

    private void cameradialog() {
        camera_dialog = new Dialog(SalesDeliveryActivity.this);
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
            public void onClick(View view) {
                camera_dialog.dismiss();

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

                        String filePath = GlobalMethods.getRealPathFromURIPath(uri, SalesDeliveryActivity.this);
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
                    String realPath = FilePath.getPath(SalesDeliveryActivity.this, data.getData());
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
                    String realPath = FilePath.getPath(SalesDeliveryActivity.this, data.getData());
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

        long fileId = GlobalMethods.getFileId(SalesDeliveryActivity.this, videouri);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
