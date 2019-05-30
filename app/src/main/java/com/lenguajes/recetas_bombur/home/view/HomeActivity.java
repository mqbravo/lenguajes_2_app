package com.lenguajes.recetas_bombur.home.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;
import com.lenguajes.recetas_bombur.home.presenter.HomePresenter;
import com.lenguajes.recetas_bombur.home.presenter.HomePresenterImpl;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.recipes.view.CreateRecipeActivity;
import com.lenguajes.recetas_bombur.search.view.SearchActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private RecyclerView mRecipesRecyclerView;
    private ArrayList<Recipe> mRecipes;
    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.presenter = new HomePresenterImpl(this);

        ToolbarManager.setToolbar(this, "Home", false, R.id.toolbar);

        mRecipes = new ArrayList<>();

        mRecipesRecyclerView = findViewById(R.id.home_recipesRecyclerView);

        setRecipesRecyclerView();

        requestAllRecipesDownload();
    }

    private void setRecipesRecyclerView(){

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecipesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mRecipes,
                R.layout.recipe_card_view, this);


        mRecipesRecyclerView.setAdapter(recipeRecyclerViewAdapter);
    }


    public void newRecipe(View view) {
        Intent intent = new Intent(this, CreateRecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);

                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void requestAllRecipesDownload() {
        presenter.requestAllRecipesDownload(this);
    }

    @Override
    public void getAllRecipes(ArrayList<Recipe> recipes) {
        this.mRecipes = recipes;

        RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = (RecipeRecyclerViewAdapter)mRecipesRecyclerView.getAdapter();

        if (recipeRecyclerViewAdapter != null)
            recipeRecyclerViewAdapter.setDataSet(recipes);

    }
}
