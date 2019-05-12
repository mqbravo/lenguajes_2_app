package com.lenguajes.recetas_bombur.home.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.login.LoginActivity;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecipesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecipesRecyclerView = findViewById(R.id.home_recipesRecyclerView);

        setRecipesRecyclerView();
    }

    private void setRecipesRecyclerView(){

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecipesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(getResults(),
                R.layout.recipe_card_view, this);


        mRecipesRecyclerView.setAdapter(recipeRecyclerViewAdapter);
    }

    private ArrayList<Recipe> getResults() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(40, "Tacos al pastor", "Mexicana", null, null, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/190130-tacos-al-pastor-horizontal-1-1549571422.png?crop=0.668xw:1.00xh;0.175xw,0&resize=768:*"));
        recipes.add(new Recipe(20, "Pasta carbonara", "Italiana", null, null, "https://s3.amazonaws.com/finecooking.s3.tauntonclud.com/app/uploads/2017/04/18173701/051092056-01-spaghetti-carbonara-recipe-thumb16x9.jpg"));
        recipes.add(new Recipe(40, "Pizza mozzarella", "Italiana", null, null, "https://www.laespanolaaceites.com/uploads/recetas/fotos/pizza-con-tomate-albahaca-y-mozzarella.jpg"));

        return recipes;
    }

    public void pb(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
