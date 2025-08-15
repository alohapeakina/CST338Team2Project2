package com.example.pocketmeals.database.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Meal entity for storing meal information in Room database
 */

@Entity(tableName = PocketMealsDatabase.MEAL_TABLE,
    foreignKeys = {
        @ForeignKey(entity = Recipe.class,
            parentColumns = "recipeId",    // correct for Recipe
            childColumns = "recipeId",
            onDelete = ForeignKey.CASCADE)/*,
        @ForeignKey(entity = User.class,
            parentColumns = "id",          // correct for Ingredient
            childColumns = "userId",
            onDelete = ForeignKey.CASCADE)*/
    })
public class Meal {

  @PrimaryKey(autoGenerate = true)
  private int mealId;
  private int recipeId;
//  private int userId;

  private String day; // e.g., "Monday", "Tuesday", etc. or date format

  // Constructor TODO: Add userID once meal plan is functional
  public Meal(String day, int recipeId) {
    this.day = day;
    this.recipeId = recipeId;
//    this.userId = userId;
  }

  // Getters and Setters
  public int getMealId() {
    return mealId;
  }

  public void setMealId(int mealId) {
    this.mealId = mealId;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  @Override
  public String toString() {
    return day + ": " + getRecipeId();
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  /*  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }*/
}