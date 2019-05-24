package com.lenguajes.recetas_bombur.recipes.model;

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
}
