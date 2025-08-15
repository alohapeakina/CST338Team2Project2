package com.example.pocketmeals.database.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;

/**
 * @author Team 2
 * Created 8/5/2025
 * Explanation: Meal entity for storing meal information in Room database
 * This entity represents a single meal in the user's meal plan. It is a Room database
 * table that stores information about the day of the week, the associated recipe, and
 * the user who planned the meal. It uses foreign keys to link to the {@link Recipe}
 * and {@link User} entities, ensuring data integrity.
 */

@Entity(tableName = PocketMealsDatabase.MEAL_TABLE,
    foreignKeys = {
        @ForeignKey(entity = Recipe.class,
            parentColumns = "recipeId",    // correct for Recipe
            childColumns = "recipeId",
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
            parentColumns = "id",          // correct for Ingredient
            childColumns = "userId",
            onDelete = ForeignKey.CASCADE)
    })
public class Meal {
  @PrimaryKey(autoGenerate = true)
  private int mealId;
  private int recipeId;
  private int userId;
  private String day; // e.g., "Monday", "Tuesday", etc. or date format

  /**
   * Constructs a new Meal object.
   *
   * @param day The day of the week for the meal (e.g., "Monday").
   * @param recipeId The unique ID of the recipe associated with this meal.
   * @param userId The unique ID of the user who planned this meal.
   */
  public Meal(String day, int recipeId, int userId) {
    this.day = day;
    this.recipeId = recipeId;
    this.userId = userId;
  }

// Getters and Setters
  /**
   * Gets the unique ID of the meal.
   *
   * @return The meal's unique ID.
   */
  public int getMealId() {
    return mealId;
  }

  /**
   * Sets the unique ID of the meal.
   *
   * @param mealId The new unique ID.
   */
  public void setMealId(int mealId) {
    this.mealId = mealId;
  }

  /**
   * Gets the day of the week for the meal.
   *
   * @return The day of the week as a string.
   */
  public String getDay() {
    return day;
  }

  /**
   * Sets the day of the week for the meal.
   *
   * @param day The new day of the week as a string.
   */
  public void setDay(String day) {
    this.day = day;
  }

  /**
   * Returns a string representation of the meal, useful for debugging.
   *
   * @return A string containing the day and recipe ID.
   */
  @NonNull
  @Override
  public String toString() {
    return day + ": " + getRecipeId();
  }

  /**
   * Gets the unique ID of the associated recipe.
   *
   * @return The recipe's unique ID.
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Sets the unique ID of the associated recipe.
   *
   * @param recipeId The new recipe ID.
   */
  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  /**
   * Gets the unique ID of the user who planned the meal.
   *
   * @return The user's unique ID.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Sets the unique ID of the user who planned the meal.
   *
   * @param userId The new user ID.
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }
}