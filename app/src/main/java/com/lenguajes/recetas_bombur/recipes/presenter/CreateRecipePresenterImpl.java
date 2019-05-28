package com.lenguajes.recetas_bombur.recipes.presenter;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.recipes.interactor.CreateRecipeInteractor;
import com.lenguajes.recetas_bombur.recipes.interactor.CreateRecipeInteractorImpl;
import com.lenguajes.recetas_bombur.recipes.view.CreateRecipeView;

import java.util.ArrayList;

public class CreateRecipePresenterImpl implements CreateRecipePresenter{

    private CreateRecipeView view;
    private CreateRecipeInteractor interactor;


    public CreateRecipePresenterImpl(CreateRecipeView view) {
        this.view = view;
        this.interactor = new CreateRecipeInteractorImpl(this);
    }

    @Override
    public void createNewRecipe(String name, String type, String preparation, ArrayList<String> ingredients,
                                ArrayList<String> imagePaths, int durationMinutes, AppCompatActivity activity) {


        interactor.createNewRecipe(name, type, preparation, ingredients, imagePaths, durationMinutes, activity);

    }

    @Override
    public void updateUploadProgress(int newProgress) {
        view.updateUploadProgress(newProgress);
    }


    @Override
    public void finishUploadProcess() {
        view.finishUploadProcess();
    }

    @Override
    public void sendErrorMessage() {
        view.showErrorMessage();
    }

}
