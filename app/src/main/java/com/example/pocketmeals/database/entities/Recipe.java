package com.example.pocketmeals.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Team 2
 * Created 8/5/2025
 * Explanation: Recipe entity for storing recipe information in Room database
 */

@Entity(tableName = "recipe_table")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;

    private String recipeName;
    private String recipeLink;
    private int numberOfServings;
    private int caloriesPerServing;
    private double gramsOfProtein;
    private double gramsOfFat;
    private double gramsOfCarbohydrates;

    // Constructor
    public Recipe(String recipeName, String recipeLink, int numberOfServings,
                  int caloriesPerServing, double gramsOfProtein, double gramsOfFat,
                  double gramsOfCarbohydrates) {
        this.recipeName = recipeName;
        this.recipeLink = recipeLink;
        this.numberOfServings = numberOfServings;
        this.caloriesPerServing = caloriesPerServing;
        this.gramsOfProtein = gramsOfProtein;
        this.gramsOfFat = gramsOfFat;
        this.gramsOfCarbohydrates = gramsOfCarbohydrates;
    }

    // Getters and Setters
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeLink() {
        return recipeLink;
    }

    public void setRecipeLink(String recipeLink) {
        this.recipeLink = recipeLink;
    }

    public int getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(int caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public double getGramsOfProtein() {
        return gramsOfProtein;
    }

    public void setGramsOfProtein(double gramsOfProtein) {
        this.gramsOfProtein = gramsOfProtein;
    }

    public double getGramsOfFat() {
        return gramsOfFat;
    }

    public void setGramsOfFat(double gramsOfFat) {
        this.gramsOfFat = gramsOfFat;
    }

    public double getGramsOfCarbohydrates() {
        return gramsOfCarbohydrates;
    }

    public void setGramsOfCarbohydrates(double gramsOfCarbohydrates) {
        this.gramsOfCarbohydrates = gramsOfCarbohydrates;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", recipeName='" + recipeName + '\'' +
                ", recipeLink='" + recipeLink + '\'' +
                ", numberOfServings=" + numberOfServings +
                ", caloriesPerServing=" + caloriesPerServing +
                ", gramsOfProtein=" + gramsOfProtein +
                ", gramsOfFat=" + gramsOfFat +
                ", gramsOfCarbohydrates=" + gramsOfCarbohydrates +
                '}';
    }
}