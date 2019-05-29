package com.lenguajes.recetas_bombur.home.interactor;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lenguajes.recetas_bombur.home.presenter.HomePresenter;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeInteractorImpl implements HomeInteractor{
    private HomePresenter presenter;

    public HomeInteractorImpl(HomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestAllRecipesDownload(AppCompatActivity activity) {
        String getURL = "http://pruebamau.herokuapp.com/all";

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest getAllRequest = new JsonObjectRequest(Request.Method.GET,
                getURL, null,

                response -> getAllRecipes(response),

                error -> {
                    //TODO send error message to GUI
                }
        );

        requestQueue.add(getAllRequest);

    }

    @Override
    public void getAllRecipes(JSONObject json) {
        try {

            JSONArray jsonRecipes = json.getJSONArray("recipes");

            ArrayList<Recipe> recipes = new ArrayList<>();

            for (int i =0; i<jsonRecipes.length(); i++){
                //Get the i-th recipe
                JSONObject recipe = jsonRecipes.getJSONObject(i);

                //Get the i-th recipe information
                String name = recipe.getString("name");
                String preparation = recipe.getString("preparation");
                String type = recipe.getString("type");
                JSONArray jsonIngredients = recipe.getJSONArray("ingredients");
                JSONArray jsonURLs = recipe.getJSONArray("URLs");


                //Create the ingredients and URLs lists from the JSON arrays
                ArrayList<String> ingredients = new ArrayList<>();
                ArrayList<String> URLs = new ArrayList<>();


                for(int j = 0; j<jsonIngredients.length(); j++)
                    ingredients.add(jsonIngredients.getString(j));


                for(int j = 0; j<jsonURLs.length(); j++)
                    URLs.add(jsonURLs.getString(j));

                Recipe newRecipe = new Recipe(0, name, type, preparation, ingredients, URLs);

                Log.d("XDXD", newRecipe.toString());

                //Add recipe object to the list
                recipes.add(newRecipe);
            }

            presenter.getAllRecipes(recipes);


        } catch (JSONException e) {
            //TODO send error message to GUI
            e.printStackTrace();
        }

    }


}
