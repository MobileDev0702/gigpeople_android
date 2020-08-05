package com.gigpeople.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.gigpeople.app.MainActivity;
import com.gigpeople.app.R;
import com.gigpeople.app.utils.PrefConnect;

public class SplashActivity extends AppCompatActivity {

    Context context;
    String user_id;
    String page="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        context=SplashActivity.this;
        user_id= PrefConnect.readString(context,PrefConnect.USER_ID,"");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String value = String.valueOf(bundle.get("status"));
            Log.e("FCM", "Key: status" + " Value: " + value);

            if (value.equals("null")) {
                page = "0";
                Log.e("FCM", "if");
            } else {
                switch (value){
                    case "0"://Gig Request
                        page="0";
                        break;
                    case "1"://Gig Request
                        page="1";
                        break;
                    case "2"://My Gig Fragment
                        page="2";
                        break;
                    case "3"://Manage Request
                        page="4";
                        break;
                    case "4"://My Orders
                        page="5";
                        break;
                    case "5"://Favourite
                        page="6";
                        break;
                    case "6"://Chat
                        page="11";
                        break;
                    case "7"://My sale
                        page="3";
                        break;
                }
                Log.e("FCM", "else "+page);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user_id.equals("")) {
                    Intent intent = new Intent(context, WalkthroughActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("page",page);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}

