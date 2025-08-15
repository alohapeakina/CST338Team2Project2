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
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Data Access Object for Meal entity
 */

@Dao
public interface MealDAO {

  @Insert
  void insert(Meal meal);

  @Update
  void update(Meal meal);

  @Delete
  void delete(Meal meal);

  @Query("SELECT * FROM meal ORDER BY day ASC")
  List<Meal> getAllMeals();

  @Query("SELECT meal.mealId AS mealId, meal.day AS day, recipes.recipeName AS recipeName " +
      "FROM meal " +
      "LEFT JOIN recipes ON meal.recipeId = recipes.recipeId " +
      "ORDER BY meal.day ASC")
  LiveData<List<MealRecipeName>> getAllMealsWithRecipeName();

  // Filter meals for a specific user
  @Query("SELECT meal.mealId AS mealId, meal.day AS day, recipes.recipeName AS recipeName " +
      "FROM meal " +
      "LEFT JOIN recipes ON meal.recipeId = recipes.recipeId " +
      "WHERE meal.userId = :userId " +
      "ORDER BY meal.day ASC")
  LiveData<List<MealRecipeName>> getAllMealsWithRecipeNameForUser(int userId);

  @Query("SELECT * FROM meal WHERE mealId = :mealId")
  Meal getMealById(int mealId);

  @Query("SELECT * FROM meal WHERE day = :day")
  List<Meal> getMealsByDay(String day);

  @Query("DELETE FROM meal WHERE mealId = :mealId")
  void deleteMealById(int mealId);

  // Keeps list of meals in meal plan in order
  @Query("SELECT DISTINCT day FROM meal ORDER BY day ASC")
  List<String> getAllDays();

  @Query("SELECT " +
      "SUM(r.totalCalories) AS totalCalories, " +
      "SUM(r.protein) AS totalProtein, " +
      "SUM(r.fat) AS totalFat, " +
      "SUM(r.carbs) AS totalCarbs " +
      "FROM meal m " +
      "INNER JOIN recipes r ON m.recipeId = r.recipeId " +
      "WHERE m.userId = :userId")
  LiveData<NutritionTotals> getNutritionTotalsForUser(int userId);

  public static class NutritionTotals {
    public int totalCalories;
    public int totalProtein;
    public int totalFat;
    public int totalCarbs;
  }

}