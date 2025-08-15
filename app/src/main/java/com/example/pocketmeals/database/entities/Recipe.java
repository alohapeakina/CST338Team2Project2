package com.example.pocketmeals.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;

@Entity(tableName = PocketMealsDatabase.RECIPE_TABLE)
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;
    private String recipeName;
    private String recipeLink;
    private double totalCalories;
    private double protein;
    private double fat;
    private double carbs;

    public Recipe(String recipeName, String recipeLink,
                  double totalCalories, double protein, double fat, double carbs) {
        this.recipeName = recipeName;
        this.recipeLink = recipeLink;
        this.totalCalories = totalCalories;
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

    public double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    @NonNull
    @Override
    public String toString() {
        return recipeName + "\n" + recipeLink + "\n" +
            "kCal: " + totalCalories +
            " Protein: " + protein +
            " Fat: " + fat +
            " Carbs: " + carbs + "\n";
    }

    @NonNull
    public String nutritionToString() {
        return "Calories: " + totalCalories +
            " Protein: " + protein +
            " Fat: " + fat +
            " Carbs: " + carbs;
    }
}
