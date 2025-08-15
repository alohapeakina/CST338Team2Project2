package com.example.pocketmeals.database.entities;

/**
 * @author Andrew Lee
 * created: 8/15/2025
 * Explanation: POJO Helper class to combine meals with recipes
 */
public class MealRecipeName {
  public int mealId;
  public String day;
  public String recipeName;

  public MealRecipeName(int mealId, String day, String recipeName) {
    this.mealId = mealId;
    this.day = day;
    this.recipeName = recipeName;
  }
}
