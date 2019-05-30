package com.lenguajes.recetas_bombur.search.view;

import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import java.util.ArrayList;

public interface SearchView {

    void requestSearchRecipes();

    void showRecipes(ArrayList<Recipe> recipes);

}
