package com.example.pocketmeals.database.entities;

/**
 * @author Andrew Lee
 * created: 8/15/2025
 * Explanation: POJO Helper class to combine meals with recipes
 * This is a Plain Old Java Object used as a helper class for database queries.
 * It's designed to hold a combined result from a join operation between the `Meal`
 * and `Recipe` entities. It specifically stores the meal's ID and day, along with
 * the name of the associated recipe, which is useful for displaying a user's meal plan.
 */
public class MealRecipeName {
  public int mealId;
  public String day;
  public String recipeName;

  /**
   * Constructs a new `MealRecipeName` object.
   *
   * @param mealId The unique ID of the meal.
   * @param day The day of the week for the meal.
   * @param recipeName The name of the recipe.
   */
  public MealRecipeName(int mealId, String day, String recipeName) {
    this.mealId = mealId;
    this.day = day;
    this.recipeName = recipeName;
  }
}
