package com.gigpeople.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.DisputeAdapter;
import com.gigpeople.app.model.DisputeModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisputeActivity extends AppCompatActivity {

    @BindView(R.id.recycler_dispute)
    RecyclerView recyclerDispute;
    DisputeAdapter adapter;
    List<DisputeModel> list;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_add)
    ImageView imgAdd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        list.add(new DisputeModel("$10.00", "Graphic Design"));
        list.add(new DisputeModel("$5.00", "Graphic Design"));
        list.add(new DisputeModel("$16.00", "Graphic Design"));
        list.add(new DisputeModel("$45.00", "Graphic Design"));
        list.add(new DisputeModel("$55.00", "Graphic Design"));
        adapter = new DisputeAdapter(DisputeActivity.this, list, "0");
        recyclerDispute.setAdapter(adapter);
        recyclerDispute.setLayoutManager(new LinearLayoutManager(DisputeActivity.this));


    }

    @OnClick({R.id.img_back, R.id.img_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add:
                Intent add=new Intent(DisputeActivity.this,AddDisputeActivity.class);
                startActivity(add);
                break;
        }
    }
}
