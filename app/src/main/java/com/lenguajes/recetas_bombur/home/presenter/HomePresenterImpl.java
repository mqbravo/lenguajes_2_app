package com.lenguajes.recetas_bombur.home.presenter;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.home.interactor.HomeInteractor;
import com.lenguajes.recetas_bombur.home.interactor.HomeInteractorImpl;
import com.lenguajes.recetas_bombur.home.view.HomeView;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;

import java.util.ArrayList;

public class HomePresenterImpl implements HomePresenter {

    private HomeInteractor interactor;
    private HomeView view;

    public HomePresenterImpl(HomeView view) {
        this.interactor = new HomeInteractorImpl(this);
        this.view = view;
    }

    @Override
    public void requestAllRecipesDownload(AppCompatActivity activity) {
        interactor.requestAllRecipesDownload(activity);
    }

    @Override
    public void getAllRecipes(ArrayList<Recipe> recipes) {
        view.getAllRecipes(recipes);
    }
}
