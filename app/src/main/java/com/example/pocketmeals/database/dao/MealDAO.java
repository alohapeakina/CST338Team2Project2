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

  @Query("SELECT * FROM meal WHERE day = :day")
  Meal getSingleMealByDay(String day);

  @Query("DELETE FROM meal")
  void deleteAllMeals();

  @Query("DELETE FROM meal WHERE mealId = :mealId")
  void deleteMealById(int mealId);

  @Query("SELECT COUNT(*) FROM meal")
  int getMealCount();

  @Query("SELECT DISTINCT day FROM meal ORDER BY day ASC")
  List<String> getAllDays();

}