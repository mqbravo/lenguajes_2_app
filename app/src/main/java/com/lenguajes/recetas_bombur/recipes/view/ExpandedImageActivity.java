package com.lenguajes.recetas_bombur.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

public class ExpandedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_image);

        String mImagePath = getIntent().getStringExtra("path");

        ImageView mImage = findViewById(R.id.expandedImage_image);
        mImage.setOnTouchListener(new ImageMatrixTouchHandler(this));

        Glide.with(this).load(mImagePath).into(mImage);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);

    }
}
