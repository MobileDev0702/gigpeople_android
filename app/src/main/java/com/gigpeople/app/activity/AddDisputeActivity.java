package com.gigpeople.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gigpeople.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDisputeActivity extends AppCompatActivity {
    @BindView(R.id.spinner_service_list)
    Spinner spinnerServiceList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dispute);
        ButterKnife.bind(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(AddDisputeActivity.this, R.array.servicelist, R.layout.item_spinner_dropdown2);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerServiceList.setAdapter(adapter);

    }
}
