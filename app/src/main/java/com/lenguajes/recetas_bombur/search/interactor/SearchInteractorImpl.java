package com.lenguajes.recetas_bombur.search.interactor;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.search.model.SearchBy;
import com.lenguajes.recetas_bombur.search.presenter.SearchPresenter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchInteractorImpl implements SeacrhInteractor {

    private SearchPresenter presenter;
    private String TAG = "SearchInteractorImplTAG";

    public SearchInteractorImpl(SearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestSearchRecipes(SearchBy searchBy, String search, AppCompatActivity activity) {

        String paramName = getParamName(searchBy);

        String getURL = "https://prolog-api.herokuapp.com/api/recetas?" + paramName + "=" + search;

        RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest myReq = new StringRequest(Request.Method.GET,
                getURL,

                response -> Log.d(TAG , "Searching by: " + paramName + ", search: " + search +", response: " + response),

                error -> {

                });

        requestQueue.add(myReq);

    }

    @Override
    public void sendResults(JSONObject json) {

        //presenter.showRecipes(new ArrayList<>());
    }

    private String getParamName(SearchBy searchBy) {

        String param = "";

        switch (searchBy) {
            case NAME:
                param = "nombreReceta";
                break;

            case TYPE:
                param = "nombreTipo";
                break;

            case INGREDIENT:
                param = "nombreIngrediente";
                break;

            default:
                break;
        }

        return param;

    }
}
