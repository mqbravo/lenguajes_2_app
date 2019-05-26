package com.lenguajes.recetas_bombur.recipes.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenguajes.recetas_bombur.R;

import java.util.ArrayList;

public class CreateRecipeIngredientTextAdapter extends RecyclerView.Adapter<CreateRecipeIngredientTextAdapter.IngredientsViewHolder> {

    private ArrayList<String> mIngredients;
    private int cardLayout;
    private Activity activity;

    public CreateRecipeIngredientTextAdapter(ArrayList<String> mIngredients, int cardLayout, Activity activity) {
        this.mIngredients = mIngredients;
        this.cardLayout = cardLayout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Gets the view from the XML layout
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(cardLayout, viewGroup, false);

        //Pass the view with the card cardLayout
        return new CreateRecipeIngredientTextAdapter.IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        final String ingredient = mIngredients.get(i);

        String text = (i + 1) + ".\t" +ingredient;

        ingredientsViewHolder.mText.setText(text);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView mText;

        IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textCard_text);
        }
    }

}
