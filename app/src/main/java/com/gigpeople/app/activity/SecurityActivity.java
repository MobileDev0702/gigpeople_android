package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityActivity extends AppCompatActivity {


    Context context;
    @BindView(R.id.img_back)
    ImageView imgBack;
    Dialog email, mobile;
    @BindView(R.id.emaillayout)
    LinearLayout emaillayout;
    @BindView(R.id.mobilelayout)
    LinearLayout mobilelayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        context = SecurityActivity.this;
        Window window = SecurityActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(SecurityActivity.this, R.color.colorPrimaryDark));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.img_back, R.id.emaillayout, R.id.mobilelayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.emaillayout:
                openemaildialog();
                break;
            case R.id.mobilelayout:
                openmobiledialog();
                break;
        }
    }

    private void openmobiledialog() {



        mobile = new Dialog(SecurityActivity.this);
        mobile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mobile.setContentView(R.layout.dialog_mobile_verify);
        mobile.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mobile.show();

        ImageView imgclose = (ImageView) mobile.findViewById(R.id.img_close);
        Button submit=(Button)mobile.findViewById(R.id.btn_submit);

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile.dismiss();
            }
        });

    }




    private void openemaildialog() {

        email = new Dialog(SecurityActivity.this);
        email.requestWindowFeature(Window.FEATURE_NO_TITLE);
        email.setContentView(R.layout.dialog_email_verify);
// camera_dialog.setCancelable(false);
        email.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        email.show();

        ImageView imgclose = (ImageView) email.findViewById(R.id.img_close);
        Button submit=(Button)email.findViewById(R.id.btn_submit);

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.dismiss();
            }
        });

    }
}
