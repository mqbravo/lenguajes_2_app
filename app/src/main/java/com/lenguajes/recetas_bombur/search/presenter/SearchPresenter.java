package com.lenguajes.recetas_bombur.search.presenter;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.search.model.SearchBy;

import java.util.ArrayList;

public interface SearchPresenter {

    void requestSearchRecipes(SearchBy searchBy, String search, AppCompatActivity activity);

    void showRecipes(ArrayList<Recipe> recipes);
}
