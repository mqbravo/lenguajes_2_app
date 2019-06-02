package com.lenguajes.recetas_bombur.search.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.activitymanagement.ToolbarManager;
import com.lenguajes.recetas_bombur.home.view.RecipeRecyclerViewAdapter;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.search.model.SearchBy;
import com.lenguajes.recetas_bombur.search.presenter.SearchPresenter;
import com.lenguajes.recetas_bombur.search.presenter.SearchPresenterImpl;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private RadioGroup mRadioGroup;
    private RadioButton mSelectedRadioBtn;
    private RecyclerView mRecipesRecyclerView;
    private TextView mSearchField;
    private ArrayList<Recipe> mRecipes;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ToolbarManager.setToolbar(this, "", true, R.id.toolbar_search);

        this.mRadioGroup = findViewById(R.id.search_radioGroup);
        this.mSelectedRadioBtn = findViewById(R.id.search_radioBtnName);
        this.mRecipesRecyclerView = findViewById(R.id.search_recipesRecyclerView);
        this.mSearchField = findViewById(R.id.search_searchField);

        mSearchField.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                requestSearchRecipes();
                return true;
            }

            return false;
        });

        this.presenter = new SearchPresenterImpl(this);
        this.mRecipes = new ArrayList<>();

        setRecipesRecyclerView();
    }


    private void setRecipesRecyclerView(){

        //Adding the "format" or behaviour the recycler will have
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecipesRecyclerView.setLayoutManager(linearLayoutManager);

        //Set its adapter by creating a new adapter from the CardView layout resource
        RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mRecipes,
                R.layout.recipe_card_view, this);


        mRecipesRecyclerView.setAdapter(recipeRecyclerViewAdapter);
    }



    public void checkSelection(View view) {
        int checkID = mRadioGroup.getCheckedRadioButtonId();
        mSelectedRadioBtn = findViewById(checkID);
    }

    @Override
    public void requestSearchRecipes() {

        //Get the selected search method
        SearchBy searchBy;

        switch (mRadioGroup.getCheckedRadioButtonId()){

            case R.id.search_radioBtnName:
                searchBy = SearchBy.NAME;
                break;

            case R.id.search_radioBtnType:
                searchBy = SearchBy.TYPE;
                break;

            case R.id.search_radioIngredient:
                searchBy = SearchBy.INGREDIENT;
                break;

            default:
                searchBy = null;
                break;
        }

        //Get the search string
        String search = mSearchField.getText().toString();


        presenter.requestSearchRecipes(searchBy, search, this);
    }

    @Override
    public void showRecipes(ArrayList<Recipe> recipes) {
        mRecipes = recipes;

        setRecipesRecyclerView();

    }


}
