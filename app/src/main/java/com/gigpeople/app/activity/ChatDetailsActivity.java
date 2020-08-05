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
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.gigpeople.app.R;
import com.gigpeople.app.adapter.AdapterChatDetail;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.CommonResponse;
import com.gigpeople.app.apiModel.ImageUploadResponse;

import com.gigpeople.app.model.UserDetailsOther;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

public class ChatDetailsActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_user)
    CircleImageView imgUser;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.relativeHeader)
    RelativeLayout relativeHeader;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.relatice_send)
    RelativeLayout relaticeSend;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @BindView(R.id.rv_chat)
    RecyclerView rvChat;
    @BindView(R.id.edt_message)
    EditText edtMessage;
    @BindView(R.id.btn_send)
    Button btnSend;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri uri;
    private Bitmap bitmap;
    Dialog camera_dialog;
    Bitmap rotatedBitmap = null;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    AdapterChatDetail adapterChatDetailTwo;
    Firebase reference1;
    ApiService apiService;
    Context context;
    MenuItem item_block;
    DatabaseReference rootRef;
    List<UserDetailsOther> userDetailsOthers = new ArrayList<>();
    String user_id = "1", other_user_id = "3", other_user_name, other_user_image, chat_else = "0", time, message, fcm_id, type = "1", blocked_status = "0";
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        Firebase.setAndroidContext(this);
        ButterKnife.bind(this);
        context = ChatDetailsActivity.this;
        Window window = ChatDetailsActivity.this.getWindow();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(ChatDetailsActivity.this, R.color.colorPrimaryDark));
        }
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id = PrefConnect.readString(context, PrefConnect.USER_ID, "");

        if (getIntent() != null) {
            other_user_id = getIntent().getStringExtra("other_user_id");
            other_user_name = getIntent().getStringExtra("other_user_name");
            other_user_image = getIntent().getStringExtra("other_user_image");
            //blocked_status = getIntent().getStringExtra("block_status");
            txtName.setText(other_user_name);
        }

        if (GlobalMethods.isNetworkAvailable(context)) {
            setChatRead();
        } else {
            GlobalMethods.Toast(context, getString(R.string.internet));
        }

        rootRef = FirebaseDatabase.getInstance().getReference("message");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.e("USERID OTHER USER ID", user_id + "::" + other_user_id);
                if (dataSnapshot.hasChild(user_id + "-" + other_user_id)) {
                    reference1 = new Firebase(GlobalMethods.FIREBASE_CHAT_URL + "message/" + user_id + "-" + other_user_id);
                    chat_else = "0";
                    fcm_id = user_id + "-" + other_user_id;
                    Log.e("FIREBASE", " in if" + "\nFCM_ID: " + fcm_id);
                    setFirebaseChatDetail();
                } else if (dataSnapshot.hasChild(other_user_id + "-" + user_id)) {
                    reference1 = new Firebase(GlobalMethods.FIREBASE_CHAT_URL + "message/" + other_user_id + "-" + user_id);
                    chat_else = "0";
                    fcm_id = other_user_id + "-" + user_id;
                    Log.e("FIREBASE", " in else if" + "\nFCM_ID: " + fcm_id);
                    setFirebaseChatDetail();
                } else {
                    reference1 = new Firebase(GlobalMethods.FIREBASE_CHAT_URL + "message/" + other_user_id + "-" + user_id);
                    chat_else = "1";
                    fcm_id = other_user_id + "-" + user_id;
                    Log.e("FIREBASE", " in else" + "\nFCM_ID: " + fcm_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onDataChange", " cancel" + databaseError.getMessage());
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        item_block = menu.findItem(R.id.action_block);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_block:
                if (GlobalMethods.isNetworkAvailable(context)) {
                    BlockUnblock();
                } else {
                    GlobalMethods.Toast(context, getString(R.string.internet));
                }
                break;
        }
        return true;
    }

    @OnClick({R.id.btn_back, R.id.img_add, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.img_add:
                setVerifiedDialog();
                break;
            case R.id.btn_send:
                if (!edtMessage.getText().toString().trim().equals("")) {
                    type = "1";
                    message = edtMessage.getText().toString().trim();
                    if (!blocked_status.equals("2")) {
                        setFirebaseSenMesg(message, type);
                    }else {
                        GlobalMethods.Toast(context, "Unblock this user");
                    }
                } else {
                    GlobalMethods.Toast(context, "Enter message");
                }
                break;
        }
    }

    private void setFirebaseSenMesg(String message, String type) {

        Map<String, String> map = new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        time = calendar.getTimeInMillis() + "";
        map.put("message", message);
        map.put("user_id", user_id);
        map.put("time", time);
        map.put("type", type);

        String block;

        if (blocked_status.equals("3")) {
            block = "9-" + other_user_id;
        } else {
            block = "1";
            if (GlobalMethods.isNetworkAvailable(context)) {
                setChatAdd();
            } else {
                GlobalMethods.Toast(context, getString(R.string.internet));
            }

        }
        map.put("block_status", block);

        reference1.push().setValue(map);


        edtMessage.setText("");

        if (chat_else.equals("1")) {
            chat_else = "0";
            setFirebaseChatDetail();
        }
    }

    private void setFirebaseChatDetail() {

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String useRid = map.get("user_id").toString();
                String type = map.get("type").toString();
                String block_status = map.get("block_status").toString();
                String time = getDate(Long.parseLong(map.get("time").toString()), "dd MMM yy hh:mm a");

                UserDetailsOther other = new UserDetailsOther();
                Log.e("block",block_status);
                if (block_status.contains("-")){
                      Log.e("TYPE IF","IN");
                    String TEMP[]=block_status.split("-");
                    if (TEMP[1].equals(user_id)){
                         Log.e("TYPE IF IF","IN");
                    }else {
                        other.setUserid(useRid);
                        other.setUserMessage(message);
                        other.setTime(time);
                        other.setType(type);
                        other.setBlock_status(block_status);
                        userDetailsOthers.add(other);
                          Log.e("TYPE IF ELSE","IN");
                    }
                }else {
                    other.setUserid(useRid);
                    other.setUserMessage(message);
                    other.setTime(time);
                    other.setType(type);
                    other.setBlock_status(block_status);
                    userDetailsOthers.add(other);
                     Log.e("TYPE ELSE","IN");
                }

                final LinearLayoutManager layoutManager = new LinearLayoutManager(ChatDetailsActivity.this);
                rvChat.setLayoutManager(layoutManager);
                layoutManager.setStackFromEnd(true);
                adapterChatDetailTwo = new AdapterChatDetail(ChatDetailsActivity.this, userDetailsOthers, other_user_image);
                rvChat.setAdapter(adapterChatDetailTwo);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void BlockUnblock() {
        Log.e("BlockUnblockReq", "\nuserid: " + user_id + "\nother_userid: " + other_user_id);
        Call<CommonResponse> call = apiService.callblockChat(user_id, other_user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("BlockUnblockResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    String status = response.body().getStatus();
                    CommonResponse resp = response.body();
                    if (status.equalsIgnoreCase("1")) {

                        GlobalMethods.Toast(context, resp.getMessage());
                        /*if (blocked_status.equals("0") || blocked_status.equals("2")) {
                            item_block.setTitle("Block this user");
                        } else if (blocked_status.equals("1") || blocked_status.equals("3")) {
                            item_block.setTitle("Unblock this user");
                        }*/
                        setChatRead();

                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("BlockUnblockRespFail", t.getMessage());
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
                        type = "2";
                        message = resp.getFileUrl();
                        setFirebaseSenMesg(resp.getFileUrl(), type);
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

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void setChatRead() {
        Log.e("ReadChatReq", "\nFromUserId: " + user_id + "\nOtherUserId: " + other_user_id);
        Call<CommonResponse> call = apiService.callCahtReadAPI(user_id, other_user_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("ReadChatResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            blocked_status = resp.getChatStatus();
                            Log.e("block_staus", blocked_status);

                            if (blocked_status.equals("0") || blocked_status.equals("1")|| blocked_status.equals("3")) {
                                item_block.setTitle("Block this user");
                            } else if (blocked_status.equals("2") ) {
                                item_block.setTitle("Unblock this user");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void setChatAdd() {
        Log.e("AddChatReq", "\nFromUserId: " + user_id + "\nOtherUserId: " + other_user_id + "Message: " + message + "\nFcmId: " + fcm_id);
        Call<CommonResponse> call = apiService.callCahtAddAPI(user_id, other_user_id, message, fcm_id);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                Log.e("AddChatResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    CommonResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            edtMessage.setText("");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void setVerifiedDialog() {

        camera_dialog = new Dialog(ChatDetailsActivity.this);
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
                        String filePath = GlobalMethods.getRealPathFromURIPath(uri, ChatDetailsActivity.this);
                        File file = new File(filePath);
                        // Glide.with(EditProfileActivity.this).load(bitmap).into(imorgLogo);
                        if (GlobalMethods.isNetworkAvailable(context)) {
                            type = "2";
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
