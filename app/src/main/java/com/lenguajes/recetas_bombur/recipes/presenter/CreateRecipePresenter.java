package com.lenguajes.recetas_bombur.recipes.presenter;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public interface CreateRecipePresenter {

    void createNewRecipe(String name, String type, String preparation, ArrayList<String> ingredients,
                         ArrayList<String> imagePaths,int durationMinutes, AppCompatActivity activity) ;



    void updateUploadProgress(int newProgress);

    void finishUploadProcess();

}
