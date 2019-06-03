package com.lenguajes.recetas_bombur.home.interactor;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lenguajes.recetas_bombur.RecetasBomburApplication;
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
        String getURL = RecetasBomburApplication.getURL().concat("/api/recetas/all?username="+RecetasBomburApplication.getSessionUsername()+"&token="+RecetasBomburApplication.getSessionToken());

        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest getAllRequest = new JsonObjectRequest(Request.Method.GET,
                getURL, null,

                this::getAllRecipes,

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
                JSONObject jsonRecipe = jsonRecipes.getJSONObject(i);

                //Get the i-th recipe information
                Recipe newRecipe = Recipe.createRecipeFromJSONObject(jsonRecipe);


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
