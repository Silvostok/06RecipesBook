package com.example.miguelangelrubiocaballero.a06recipesbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("recipes")
    @Expose
    private List<RecipesResources> recipesResources = null;

    public Recipe() {
    }

    public Recipe(Integer count, List<RecipesResources> recipesResources) {
        this.count = count;
        this.recipesResources = recipesResources;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public List<RecipesResources> getRecipesResources() {
        return recipesResources;
    }


    public void setRecipesResources(List<RecipesResources> recipesResources) {
        this.recipesResources = recipesResources;
    }



}
