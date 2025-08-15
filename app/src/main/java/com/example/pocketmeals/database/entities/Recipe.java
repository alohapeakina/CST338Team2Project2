package com.example.pocketmeals.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;

/**
 * @author Team 2
 * Created: 8/15/2025
 * Explanation: The Recipe class is a Room database entity that represents a recipe.
 * It stores information about a recipe's name, a link to its source, and its nutritional content.
 * The class includes getters and setters for each field, a constructor for easy object creation,
 * and `toString()` methods for convenient display and debugging.
 */
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

    /**
     * Constructs a new `Recipe` object.
     *
     * @param recipeName    The name of the recipe.
     * @param recipeLink    A URL link to the recipe.
     * @param totalCalories The total calories of the recipe.
     * @param protein       The protein content in grams.
     * @param fat           The fat content in grams.
     * @param carbs         The carbohydrate content in grams.
     */
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
    /**
     * Gets the unique ID of the recipe.
     * @return The recipe's unique ID.
     */
    public int getRecipeId() { return recipeId; }

    /**
     * Sets the unique ID of the recipe. This is typically handled by Room during insertion.
     * @param recipeId The unique ID to set.
     */
    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    /**
     * Gets the name of the recipe.
     * @return The recipe name.
     */
    public String getRecipeName() { return recipeName; }

    /**
     * Sets the name of the recipe.
     * @param recipeName The name to set.
     */
    public void setRecipeName(String recipeName) { this.recipeName = recipeName; }

    /**
     * Gets the URL link to the recipe.
     * @return The recipe link.
     */
    public String getRecipeLink() { return recipeLink; }

    /**
     * Sets the URL link to the recipe.
     * @param recipeLink The link to set.
     */
    public void setRecipeLink(String recipeLink) { this.recipeLink = recipeLink; }

    /**
     * Gets the total calories of the recipe.
     * @return The total calories.
     */
    public double getTotalCalories() { return totalCalories; }

    /**
     * Sets the total calories of the recipe.
     * @param totalCalories The total calories to set.
     */
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }

    /**
     * Gets the protein content of the recipe.
     * @return The protein content in grams.
     */
    public double getProtein() { return protein; }

    /**
     * Sets the protein content of the recipe.
     * @param protein The protein content to set.
     */
    public void setProtein(double protein) { this.protein = protein; }

    /**
     * Gets the fat content of the recipe.
     * @return The fat content in grams.
     */
    public double getFat() { return fat; }

    /**
     * Sets the fat content of the recipe.
     * @param fat The fat content to set.
     */
    public void setFat(double fat) { this.fat = fat; }

    /**
     * Gets the carbohydrate content of the recipe.
     * @return The carbohydrate content in grams.
     */
    public double getCarbs() { return carbs; }

    /**
     * Sets the carbohydrate content of the recipe.
     * @param carbs The carbohydrate content to set.
     */
    public void setCarbs(double carbs) { this.carbs = carbs; }

    /**
     * Provides a detailed string representation of the `Recipe` object,
     * including its name, link, and nutritional information.
     * @return A formatted string with all recipe details.
     */
    @NonNull
    @Override
    public String toString() {
        return recipeName + "\n" + recipeLink + "\n" +
            "kCal: " + totalCalories +
            " Protein: " + protein +
            " Fat: " + fat +
            " Carbs: " + carbs + "\n";
    }

    /**
     * Provides a concise string representation of the recipe's nutritional information only.
     * @return A formatted string with nutritional details.
     */
    @NonNull
    public String nutritionToString() {
        return "Calories: " + totalCalories +
            " Protein: " + protein +
            " Fat: " + fat +
            " Carbs: " + carbs;
    }
}
