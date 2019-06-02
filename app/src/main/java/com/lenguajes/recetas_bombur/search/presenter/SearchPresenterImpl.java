package com.lenguajes.recetas_bombur.search.presenter;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.search.interactor.SeacrhInteractor;
import com.lenguajes.recetas_bombur.search.interactor.SearchInteractorImpl;
import com.lenguajes.recetas_bombur.search.model.SearchBy;
import com.lenguajes.recetas_bombur.search.view.SearchView;

import java.util.ArrayList;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView view;
    private SeacrhInteractor interactor;

    public SearchPresenterImpl(SearchView view) {
        this.view = view;
        this.interactor = new SearchInteractorImpl(this);
    }

    @Override
    public void requestSearchRecipes(SearchBy searchBy, String search, AppCompatActivity activity){

        interactor.requestSearchRecipes(searchBy, search, activity);
    }

    @Override
    public void showRecipes(ArrayList<Recipe> recipes) {
        view.showRecipes(recipes);
    }
}
