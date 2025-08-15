package com.example.pocketmeals.database.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.MealRecipeName;
import java.util.List;

/**
 * @author Team 2
 * Created 8/5/2025
 * Explanation: This Data Access Object defines the database operations for the {@link Meal} entity.
 * It provides methods for inserting, updating, deleting, and querying meal data.
 * The methods include retrieving all meals, meals with associated recipe names,
 * and nutritional totals for a specific user.
 */

@Dao
public interface MealDAO {

  /**
   * Inserts a new meal into the database.
   *
   * @param meal The {@link Meal} object to insert.
   */
  @Insert
  void insert(Meal meal);

  /**
   * Updates an existing meal in the database.
   *
   * @param meal The {@link Meal} object to update.
   */
  @Update
  void update(Meal meal);

  /**
   * Deletes a meal from the database.
   *
   * @param meal The {@link Meal} object to delete.
   */
  @Delete
  void delete(Meal meal);

  /**
   * Retrieves a list of all meals from the database, ordered by day.
   *
   * @return A {@link List} of {@link Meal} objects.
   */
  @Query("SELECT * FROM meal ORDER BY day ASC")
  List<Meal> getAllMeals();

  /**
   * Retrieves a `LiveData` list of all meals, each with its corresponding recipe name.
   * The result is a join between the `meal` and `recipes` tables.
   *
   * @return A `LiveData` object containing a {@link List} of {@link MealRecipeName}.
   */
  @Query("SELECT meal.mealId AS mealId, meal.day AS day, recipes.recipeName AS recipeName " +
      "FROM meal " +
      "LEFT JOIN recipes ON meal.recipeId = recipes.recipeId " +
      "ORDER BY meal.day ASC")
  LiveData<List<MealRecipeName>> getAllMealsWithRecipeName();

  /**
   * Retrieves a `LiveData` list of all meals for a specific user, with their corresponding recipe names.
   *
   * @param userId The ID of the user to filter the meals by.
   * @return A `LiveData` object containing a {@link List} of {@link MealRecipeName}.
   */
  @Query("SELECT meal.mealId AS mealId, meal.day AS day, recipes.recipeName AS recipeName " +
      "FROM meal " +
      "LEFT JOIN recipes ON meal.recipeId = recipes.recipeId " +
      "WHERE meal.userId = :userId " +
      "ORDER BY meal.day ASC")
  LiveData<List<MealRecipeName>> getAllMealsWithRecipeNameForUser(int userId);

  /**
   * Retrieves a meal by its unique ID.
   *
   * @param mealId The ID of the meal to retrieve.
   * @return The {@link Meal} object corresponding to the given ID.
   */
  @Query("SELECT * FROM meal WHERE mealId = :mealId")
  Meal getMealById(int mealId);

  /**
   * Retrieves a list of meals for a specific day.
   *
   * @param day The day of the week to filter by.
   * @return A {@link List} of {@link Meal} objects for the specified day.
   */
  @Query("SELECT * FROM meal WHERE day = :day")
  List<Meal> getMealsByDay(String day);

  /**
   * Deletes a meal from the database by its ID.
   *
   * @param mealId The ID of the meal to delete.
   */
  @Query("DELETE FROM meal WHERE mealId = :mealId")
  void deleteMealById(int mealId);

  /**
   * Retrieves a distinct list of all days for which meals are planned.
   * The list is ordered to maintain a consistent display order.
   *
   * @return A {@link List} of unique day strings.
   */
  @Query("SELECT DISTINCT day FROM meal ORDER BY day ASC")
  List<String> getAllDays();

  /**
   * Retrieves `LiveData` containing the sum of nutritional values (calories, protein, fat, carbs)
   * for all meals planned by a specific user.
   *
   * @param userId The ID of the user whose nutritional totals are to be calculated.
   * @return A `LiveData` object holding a {@link NutritionTotals} object.
   */
  @Query("SELECT " +
      "SUM(r.totalCalories) AS totalCalories, " +
      "SUM(r.protein) AS totalProtein, " +
      "SUM(r.fat) AS totalFat, " +
      "SUM(r.carbs) AS totalCarbs " +
      "FROM meal m " +
      "INNER JOIN recipes r ON m.recipeId = r.recipeId " +
      "WHERE m.userId = :userId")
  LiveData<NutritionTotals> getNutritionTotalsForUser(int userId);

  /**
   * A class to hold the aggregated nutritional totals.
   */
  class NutritionTotals {
    public int totalCalories;
    public int totalProtein;
    public int totalFat;
    public int totalCarbs;
  }
}