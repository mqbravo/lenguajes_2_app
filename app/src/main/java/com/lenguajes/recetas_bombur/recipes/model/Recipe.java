package com.lenguajes.recetas_bombur.recipes.model;

import java.util.ArrayList;

public class Recipe {
    private int durationMinutes;
    private String name;
    private String type;
    private ArrayList<String> instructions;
    private ArrayList<String> ingredients;
    private String imageURL;

    public Recipe(int durationMinutes, String name, String type, ArrayList<String> instructions, ArrayList<String> ingredients, String imageURL) {
        this.durationMinutes = durationMinutes;
        this.name = name;
        this.type = type;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.imageURL = imageURL;
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

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
