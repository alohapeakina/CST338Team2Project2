package com.example.pocketmeals.database.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketmeals.database.entities.Meal;
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

  @Query("SELECT * FROM meal WHERE mealId = :mealId")
  Meal getMealById(int mealId);

  @Query("SELECT * FROM meal WHERE day = :day")
  List<Meal> getMealsByDay(String day);

  @Query("SELECT * FROM meal WHERE day = :day")
  Meal getSingleMealByDay(String day);

  @Query("DELETE FROM meal")
  void deleteAllMeals();

  @Query("SELECT COUNT(*) FROM meal")
  int getMealCount();

  @Query("SELECT DISTINCT day FROM meal ORDER BY day ASC")
  List<String> getAllDays();

}