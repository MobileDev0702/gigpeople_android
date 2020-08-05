package com.gigpeople.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gigpeople.app.R;
import com.jsibbold.zoomage.ZoomageView;

public class ZoomImageActivity extends AppCompatActivity {

    String post_image_list,image;
    private ZoomageView demoView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        demoView = (ZoomageView) findViewById(R.id.demoView);
        context = ZoomImageActivity.this;
        post_image_list = getIntent().getStringExtra("image");

        Glide.with(context).load(post_image_list).into(demoView);
    }
}
