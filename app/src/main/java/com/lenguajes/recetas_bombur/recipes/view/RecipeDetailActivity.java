package com.lenguajes.recetas_bombur.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecyclerView mImagesRecyclerView;
    private TextView mRecipeNameTextView;
    private TextView mIngredientsTextView;
    private TextView mPreparationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);

        setUpImagesRecycler();

        //Find views
        mRecipeNameTextView = findViewById(R.id.recipeDetail_tittle);
        mIngredientsTextView = findViewById(R.id.recipeDetail_ingredients);
        mPreparationTextView = findViewById(R.id.recipeDetail_preparation);

        //Set the texts
        mRecipeNameTextView.setText(getIntent().getStringExtra("name"));
        mIngredientsTextView.setText(getIntent().getStringExtra("ingredients"));
        mPreparationTextView.setText(getIntent().getStringExtra("preparation"));
    }

    private void setUpImagesRecycler(){
        mImagesRecyclerView = findViewById(R.id.recipeDetail_imagesRecycler);

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mImagesRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());


        mImagesRecyclerView.addItemDecoration(dividerItemDecoration);

        //Set its adapter by creating a new adapter from the CardView layout resource
        ImageRecyclerViewAdapter imageAdapter = new ImageRecyclerViewAdapter(getIntent().getStringArrayListExtra("urls"),
                R.layout.medium_image_card_view, R.id.mediumImage_card, this);


        mImagesRecyclerView.setAdapter(imageAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
