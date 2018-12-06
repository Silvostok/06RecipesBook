package com.example.miguelangelrubiocaballero.a06recipesbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredients {


    @SerializedName("recipe")
    @Expose
    private IngredientsResources ingredientsResources;


    public Ingredients() {
    }


    public Ingredients(IngredientsResources ingredientsResources) {
        this.ingredientsResources = ingredientsResources;
    }

    public IngredientsResources getIngredientsResources() {
        return ingredientsResources;
    }

    public void setIngredientsResources(IngredientsResources ingredientsResources) {
        this.ingredientsResources = ingredientsResources;
    }
}
