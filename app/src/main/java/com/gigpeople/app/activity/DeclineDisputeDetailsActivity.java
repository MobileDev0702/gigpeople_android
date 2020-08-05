package com.gigpeople.app.activity;

import android.app.Dialog;
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

public class DeclineDisputeDetailsActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.spinner_service_list)
    Spinner spinnerServiceList;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_decline)
    Button btnDecline;
    Dialog chat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decline_dispute_details);
        ButterKnife.bind(this);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(DeclineDisputeDetailsActivity.this, R.array.servicelist, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerServiceList.setAdapter(adapter);
    }

    @OnClick({R.id.img_back, R.id.img_send, R.id.btn_accept, R.id.btn_decline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_send:
                openchatdialog();
                break;
            case R.id.btn_accept:
                break;
            case R.id.btn_decline:
                break;
        }
    }

    private void openchatdialog() {
        chat = new Dialog(DeclineDisputeDetailsActivity.this);
        chat.requestWindowFeature(Window.FEATURE_NO_TITLE);
        chat.setContentView(R.layout.dialog_decline);
        chat.setCancelable(true);
        chat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chat.show();
    }
}
