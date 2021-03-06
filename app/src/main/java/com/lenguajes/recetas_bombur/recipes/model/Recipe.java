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

        try {
            jsonRecipe.put("name", name);
            jsonRecipe.put("ingredients", getIngredientsAsJSONArray());
            jsonRecipe.put("imageURLs", getURLsAsJSONArray());
            jsonRecipe.put("type", type);
            jsonRecipe.put("preparation", preparation);

            return jsonRecipe;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONArray getIngredientsAsJSONArray(){
        JSONArray ingredientsArray = new JSONArray();

        for (String ingredient : ingredients)
            ingredientsArray.put(ingredient);

        return ingredientsArray;
    }

    private JSONArray getURLsAsJSONArray(){
        JSONArray urlsArray = new JSONArray();

        for (String url : imageURLs)
            urlsArray.put(url);

        return urlsArray;
    }


    private String formatIngredients(){

        StringBuilder builder = new StringBuilder();

        for(String ingredient : ingredients){

            builder.append('"');
            builder.append('|');
            builder.append(ingredient);
            builder.append('"');
            builder.append(',');
        }

        String formatted = builder.toString();

        return formatted.substring(0, formatted.length()-1);
    }

    private String formatURLs(){

        StringBuilder builder = new StringBuilder();

        for(String url : imageURLs){

            builder.append('"');
            builder.append('|');
            builder.append(url);
            builder.append('"');
            builder.append(',');
        }

        String formatted = builder.toString();

        return formatted.substring(0, formatted.length()-1);
    }

    public String formatForBackend(){

            return "{\"recipe\":[\n" +
                    "{\"name\":\"" + name + "\"},\n" +
                    "{\"type\":\"" + type + "\"},\n" +
                    "{\"preparation\":[\"" + preparation + "\"]},\n" +
                    "{\"ingredients\":[" + formatIngredients() + "]},\n" +
                    "{\"imageURLs\":[" + formatURLs() + "]}"
                    +"]}";

    }

}
