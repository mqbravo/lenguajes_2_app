package com.lenguajes.recetas_bombur.home.view;


import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import java.util.ArrayList;

public interface HomeView {

    void requestAllRecipesDownload();

    void getAllRecipes(ArrayList<Recipe> recipes);
}
