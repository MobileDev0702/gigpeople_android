package com.gigpeople.app.activity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HelpDetailActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.im_back)
    ImageView imBack;
    String position, title;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    String question, answer;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.layouttop)
    RelativeLayout layouttop;
    @BindView(R.id.txtanswer)
    TextView txtanswer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support_detail);
        context = HelpDetailActivity.this;
        ButterKnife.bind(this);

        Window window = HelpDetailActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(HelpDetailActivity.this, R.color.colorPrimaryDark));
        }
        if (getIntent() != null) {

                // txtTitle.setText("I forgot my Password");


                question = getIntent().getStringExtra("question");
                answer = getIntent().getStringExtra("answer");

                txtTitle.setText(question);


            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtanswer.setText(Html.fromHtml(answer, Html.FROM_HTML_MODE_COMPACT));

            } else {
                txtanswer.setText(Html.fromHtml(answer));

            }

           /* if (position.equalsIgnoreCase("0")) {
                txtTitle.setText("How do I reset my passw

            } else if (position.equalsIgnoreCase("1")) {
                txtTitle.setText("How to change my profile picture?");


            } else if (position.equalsIgnoreCase("2")) {
                txtTitle.setText("Cancel payment that I've already sent?");


            } else if (position.equalsIgnoreCase("3")) {
                txtTitle.setText("What are the fees for accounts?");


            } else if (position.equalsIgnoreCase("4")) {
                txtTitle.setText("How do I receive  payments?");


            }
            else if (position.equalsIgnoreCase("5")) {
                txtTitle.setText("How to select graphic design category");


            }
            else if (position.equalsIgnoreCase("6")) {
                txtTitle.setText("How do I submit a gig?");


            }
            else if (position.equalsIgnoreCase("7")) {
                txtTitle.setText("How can I view reviews?");


            }
            else if (position.equalsIgnoreCase("8")) {
                txtTitle.setText("How to select a best provider?");


            }
            else if (position.equalsIgnoreCase("9")) {
                txtTitle.setText("How to withdraw wallet amount");


            }
            else if (position.equalsIgnoreCase("10")) {
                txtTitle.setText("How to view status of my gig?");


            }*/
        }



    @OnClick({R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;


        }
    }


}
