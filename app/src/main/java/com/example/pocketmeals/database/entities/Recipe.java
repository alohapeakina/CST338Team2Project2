package com.example.pocketmeals.database.entities;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.dao.RecipeDAO;

@Entity(tableName = PocketMealsDatabase.RECIPE_TABLE)
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;
    private String recipeName;
    private String recipeLink;
    private int servings;
    private double totalCalories;
    private double protein;
    private double fat;
    private double carbs;

    public Recipe(String recipeName, String recipeLink, int servings,
                  double totalCalories, double protein, double fat, double carbs) {
        this.recipeName = recipeName;
        this.recipeLink = recipeLink;
        this.servings = servings;
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

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

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
            " Carbs: " + carbs;
    }

    //TODO: Find which class to put this logic in
/*    private void updateNutritionalInfo(int recipeId, double totalCalories, double totalProtein, double totalFat, double totalCarbs) {

        LiveData<Recipe> recipeLiveData = RecipeDAO.getRecipeById(recipeId);

        recipeLiveData.observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    recipe.setTotalCalories(totalCalories);
                    recipe.setProtein(totalProtein);
                    recipe.setFat(totalFat);
                    recipe.setCarbs(totalCarbs);

                    RecipeDAO.update(recipe);
                }
            }
        });
    }*/
}
