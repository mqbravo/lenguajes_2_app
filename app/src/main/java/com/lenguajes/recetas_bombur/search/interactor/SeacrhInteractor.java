package com.lenguajes.recetas_bombur.search.interactor;

import android.support.v7.app.AppCompatActivity;

import com.lenguajes.recetas_bombur.search.model.SearchBy;

import org.json.JSONObject;


public interface SeacrhInteractor {

    void requestSearchRecipes(SearchBy searchBy, String search, AppCompatActivity activity);

    void sendResults(JSONObject json);

}
