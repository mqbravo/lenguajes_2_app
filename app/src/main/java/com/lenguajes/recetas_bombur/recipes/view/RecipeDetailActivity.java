package com.lenguajes.recetas_bombur.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private RecyclerView mImagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ToolbarManager.setToolbar(this, "", true, R.id.toolbar);

        setUpImagesRecycler();
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
        ImageRecyclerViewAdapter imageAdapter = new ImageRecyclerViewAdapter(getImagesURLs(),
                R.layout.medium_image_card_view, R.id.mediumImage_card, this);


        mImagesRecyclerView.setAdapter(imageAdapter);

    }

    private ArrayList<String> getImagesURLs() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://firebasestorage.googleapis.com/v0/b/recetas-bombur.appspot.com/o/test%2Fagridulce9.jpg?alt=media&token=8bde515d-f203-42c7-aadd-239576938440");
        urls.add("https://firebasestorage.googleapis.com/v0/b/recetas-bombur.appspot.com/o/test%2Fcerdo-agridulce-salsa-soja.jpg?alt=media&token=78f2cc61-6195-48b1-9cf0-a848066d385f");
        urls.add("https://firebasestorage.googleapis.com/v0/b/recetas-bombur.appspot.com/o/test%2FPollo-agridulce-2.jpg?alt=media&token=3879e80f-3119-4231-9e11-a74f4e7d29b0");

        return urls;
    }
}
