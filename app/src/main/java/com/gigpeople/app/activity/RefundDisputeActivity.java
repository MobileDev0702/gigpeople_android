package com.gigpeople.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefundDisputeActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.spinner_service_list)
    Spinner spinnerServiceList;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_decline)
    Button btnDecline;
    Dialog decline;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_dispute);
        ButterKnife.bind(this);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(RefundDisputeActivity.this, R.array.servicelist, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerServiceList.setAdapter(adapter);

    }

    @OnClick({R.id.img_back, R.id.btn_accept, R.id.btn_decline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.btn_accept:
                Intent dispute=new Intent(RefundDisputeActivity.this,DisputeActivity.class);
                startActivity(dispute);
                break;
            case R.id.btn_decline:
                opendeclinedialog();
                break;
        }
    }

    private void opendeclinedialog() {
        decline = new Dialog(RefundDisputeActivity.this);
        decline.requestWindowFeature(Window.FEATURE_NO_TITLE);
        decline.setContentView(R.layout.dialog_decline);
        decline.setCancelable(true);
        decline.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        decline.show();
        ImageView close = (ImageView) decline.findViewById(R.id.img_close);
        Button submit = (Button) decline.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decline.dismiss();
                Intent intent=new Intent(RefundDisputeActivity.this,DeclineDisputeDetailsActivity.class);
                startActivity(intent);
            }
        });
}}
