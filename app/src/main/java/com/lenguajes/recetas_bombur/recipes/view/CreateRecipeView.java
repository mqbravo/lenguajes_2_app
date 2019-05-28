package com.lenguajes.recetas_bombur.recipes.view;

public interface CreateRecipeView {

    void updateUploadProgress(int newProgress);

    void finishUploadProcess();

    void showErrorMessage();
}
