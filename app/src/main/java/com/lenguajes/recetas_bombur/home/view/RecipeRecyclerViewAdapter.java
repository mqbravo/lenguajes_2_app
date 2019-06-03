package com.lenguajes.recetas_bombur.home.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lenguajes.recetas_bombur.R;
import com.lenguajes.recetas_bombur.recipes.model.Recipe;
import com.lenguajes.recetas_bombur.recipes.view.RecipeDetailActivity;

import java.util.ArrayList;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipes;
    private int resource;
    private Activity activity;


    public RecipeRecyclerViewAdapter(ArrayList<Recipe> recipes, int resource, Activity activity) {
        this.recipes = recipes;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Gets the view from the XML layout
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        //Pass the view with the card resource
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        //The recipe we will refer to
        final Recipe recipe = recipes.get(i);

        String duration = recipe.getDurationMinutes() + " mins";

        //Sets the view holder info from the current recipe
        //recipeViewHolder.durationCard.setText(duration);
        recipeViewHolder.nameCard.setText(recipe.getName());
        recipeViewHolder.typeCard.setText(recipe.getType());

        //Setting a progressbar for the loading image
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(activity);
        progressDrawable.setCenterRadius(100f);
        progressDrawable.setStrokeWidth(8f);
        progressDrawable.start();

        //Load the first image into the view
        Glide.with(activity).load(recipe.getImageURLs().get(0)).placeholder(progressDrawable).into(recipeViewHolder.imageCard);


        //When the card is clicked
        recipeViewHolder.imageCard.setOnClickListener(v -> {
            Intent intent = new Intent(activity, RecipeDetailActivity.class);

            //Extras
            intent.putExtra("name", recipe.getName());
            intent.putExtra("preparation", recipe.getPreparation());
            intent.putExtra("ingredients", ingredientsToString(recipe.getIngredients()));
            intent.putStringArrayListExtra("urls", recipe.getImageURLs());

            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageCard;
        private TextView nameCard;
        //private TextView durationCard;
        private TextView typeCard;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCard = itemView.findViewById(R.id.recipeCard_image);
            nameCard = itemView.findViewById(R.id.recipeCard_recipeName);
            //durationCard = itemView.findViewById(R.id.recipeCard_recipeDuration_Detail);
            typeCard = itemView.findViewById(R.id.recipeCard_recipeType_Detail);
        }
    }


    public void setDataSet(ArrayList<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    private String ingredientsToString(ArrayList<String> ingredients){

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i<ingredients.size(); i++){
            builder.append("• ");
            builder.append(ingredients.get(i));
            builder.append('\n');
        }

        return builder.toString();
    }
}
