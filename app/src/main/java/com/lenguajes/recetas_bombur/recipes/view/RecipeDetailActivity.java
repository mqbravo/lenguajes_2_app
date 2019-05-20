package com.lenguajes.recetas_bombur.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);
    }
}
