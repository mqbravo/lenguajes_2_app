package com.lenguajes.recetas_bombur.home.presenter;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import java.util.ArrayList;

public interface HomePresenter {

    void requestAllRecipesDownload(AppCompatActivity activity);

    void getAllRecipes (ArrayList<Recipe> recipes);

}
