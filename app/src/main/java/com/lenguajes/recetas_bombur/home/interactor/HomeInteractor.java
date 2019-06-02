package com.lenguajes.recetas_bombur.home.interactor;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import org.json.JSONObject;

import java.util.ArrayList;

public interface HomeInteractor {

    void requestAllRecipesDownload(AppCompatActivity activity);

    void getAllRecipes(JSONObject json);

}
