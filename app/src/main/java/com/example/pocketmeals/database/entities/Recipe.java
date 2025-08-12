package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pocketmeals.database.PocketMealsDatabase;

@Entity(tableName = PocketMealsDatabase.RECIPE_TABLE)
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;

    private String recipeName;
    private String recipeLink;
    private int servings;
    private double caloriesPerServing;
    private double protein;
    private double fat;
    private double carbs;

    public Recipe(String recipeName, String recipeLink, int servings,
                  double caloriesPerServing, double protein, double fat, double carbs) {
        this.recipeName = recipeName;
        this.recipeLink = recipeLink;
        this.servings = servings;
        this.caloriesPerServing = caloriesPerServing;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // Getters & Setters
    public int getRecipeId() { return recipeId; }
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public String getRecipeName() { return recipeName; }
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    public String getRecipeLink() { return recipeLink; }
    public void setRecipeLink(String recipeLink) { this.recipeLink = recipeLink; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public double getCaloriesPerServing() { return caloriesPerServing; }
    public void setCaloriesPerServing(double caloriesPerServing) { this.caloriesPerServing = caloriesPerServing; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }
}
