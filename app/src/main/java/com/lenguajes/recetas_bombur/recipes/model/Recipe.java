package com.lenguajes.recetas_bombur.recipes.model;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recipe {
    private int durationMinutes;
    private String name;
    private String type;
    private String preparation;
    private ArrayList<String> ingredients;
    private ArrayList<String> imageURLs;

    public Recipe(int durationMinutes, String name, String type, String preparation, ArrayList<String> ingredients, ArrayList<String> imageURLs) {
        this.durationMinutes = durationMinutes;
        this.name = name;
        this.type = type;
        this.preparation = preparation;
        this.ingredients = ingredients;
        this.imageURLs = imageURLs;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setpreparation(String preparation) {
        this.preparation = preparation;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(ArrayList<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    @Override
    public String toString() {
        return name + ", " + type;
    }

    public static Recipe createRecipeFromJSONObject(JSONObject json) throws JSONException {

        String name = json.getString("name");
        String preparation = json.getString("preparation");
        String type = json.getString("type");
        JSONArray jsonIngredients = json.getJSONArray("ingredients");
        JSONArray jsonURLs = json.getJSONArray("URLs");


        //Create the ingredients and URLs lists from the JSON arrays
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> URLs = new ArrayList<>();


        for(int j = 0; j<jsonIngredients.length(); j++)
            ingredients.add(jsonIngredients.getString(j));


        for(int j = 0; j<jsonURLs.length(); j++)
            URLs.add(jsonURLs.getString(j));

        return new Recipe(0, name, type, preparation, ingredients, URLs);
    }


    public JSONObject createJSON(){

        JSONObject jsonRecipe = new JSONObject();

        JSONArray jsonRecipeArray = new JSONArray();

        JSONObject jsonName = new JSONObject();
        JSONObject jsonType = new JSONObject();
        JSONObject jsonIngredients = new JSONObject();
        JSONObject jsonPreparation = new JSONObject();
        JSONObject jsonURLS = new JSONObject();

        try {
            jsonName.put("name", name);
            jsonType.put("type", type);
            jsonURLS.put("imageURLs", formatURLs());
            jsonPreparation.put("preparation", formatPreparation());
            jsonIngredients.put("ingredients", formatIngredients());


            jsonRecipeArray.put(jsonName);
            jsonRecipeArray.put(jsonType);
            jsonRecipeArray.put(jsonIngredients);
            jsonRecipeArray.put(jsonPreparation);
            jsonRecipeArray.put(jsonURLS);

            jsonRecipe.put("recipe", jsonRecipeArray);

            return jsonRecipe;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private JSONArray formatPreparation() {
        JSONArray jsonArray = new JSONArray();

        jsonArray.put(preparation);

        return jsonArray;
    }

    private JSONArray formatIngredients(){
        JSONArray jsonArray = new JSONArray();

        for(String ingredient : ingredients){

            String ingredientString = "|" + ingredient;

            jsonArray.put(ingredientString);

        }

        return jsonArray;
    }

    private JSONArray formatURLs(){

        JSONArray jsonArray = new JSONArray();

        for(String url : imageURLs){

            String urlString = "|" + url;

            jsonArray.put(urlString);

        }

        return jsonArray;
    }

}
