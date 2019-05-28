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
import com.lenguajes.recetas_bombur.login.LoginActivity;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.recipes.view.CreateRecipeActivity;
import com.lenguajes.recetas_bombur.search.SearchActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecipesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecipesRecyclerView = findViewById(R.id.home_recipesRecyclerView);

        ToolbarManager.setToolbar(this, "Home", false, R.id.toolbar);

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
        ArrayList<String> ingredientsDummy = new ArrayList<>();

        ingredientsDummy.add("Dummy");
        ingredientsDummy.add("ingredients");
        ingredientsDummy.add("list");

        ArrayList<String> recipe1 = new ArrayList<>();
        ArrayList<String> recipe2 = new ArrayList<>();
        ArrayList<String> recipe3 = new ArrayList<>();

        recipe1.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/190130-tacos-al-pastor-horizontal-1-1549571422.png?crop=0.668xw:1.00xh;0.175xw,0&resize=768:*");
        recipe1.add("https://www.comedera.com/wp-content/uploads/2017/08/tacos-al-pastor-receta.jpg");
        recipe1.add("https://cocina-casera.com/mx/wp-content/uploads/2018/06/tacoas-al-pastor-700x390.jpg");
        recipe2.add("https://s3.amazonaws.com/finecooking.s3.tauntonclud.com/app/uploads/2017/04/18173701/051092056-01-spaghetti-carbonara-recipe-thumb16x9.jpg");
        recipe3.add("https://www.laespanolaaceites.com/uploads/recetas/fotos/pizza-con-tomate-albahaca-y-mozzarella.jpg");

        recipes.add(new Recipe(40, "Tacos al pastor", "Mexicana", "Lmaoo",ingredientsDummy ,recipe1));
        recipes.add(new Recipe(20, "Pasta carbonara", "Italiana", "Lmaoo", ingredientsDummy ,recipe2));
        recipes.add(new Recipe(40, "Pizza mozzarella", "Italiana", "Lmaoo", ingredientsDummy , recipe3));

        return recipes;
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
}
