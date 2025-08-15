package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pocketmeals.activity.MainActivity;
import com.example.pocketmeals.database.dao.MealDAO;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.dao.UserDAO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Team 2
 * created: 8/4/2025
 * Explanation: The main database class for the Pocket Meals application.
 * It is a Room database that holds the `User`, `Recipe`, and `Meal` entities.
 * This class provides access to the Data Access Objects (DAOs) for each entity
 * and manages the singleton instance of the database to ensure efficient access.
 * It also includes constants for the database and table names, as well as an
 * `ExecutorService` for performing database operations on a background thread.
 */
@Database(entities = {User.class, Recipe.class, Meal.class}, version = 9, exportSchema = false)
public abstract class PocketMealsDatabase extends RoomDatabase {
  /**
   * Returns the DAO for the `User` entity.
   * @return The {@link UserDAO} object.
   */
  public abstract UserDAO userDAO();

  /**
   * Returns the DAO for the `Recipe` entity.
   * @return The {@link RecipeDAO} object.
   */
  public abstract RecipeDAO recipeDAO();

  /**
   * Returns the DAO for the `Meal` entity.
   * @return The {@link MealDAO} object.
   */
  public abstract MealDAO mealDAO();
  private static volatile PocketMealsDatabase INSTANCE;
  public static final String DATABASE_NAME = "PocketMealsDatabase";
  public static final String USER_TABLE = "user_table";
  public static final String RECIPE_TABLE = "recipes";
  public static final String MEAL_TABLE = "meal";
  private static final int NUMBER_OF_THREADS = 4;
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  /**
   * Gets the singleton instance of the `PocketMealsDatabase`.
   * This method uses the singleton pattern to ensure that only one instance of the
   * database is created. It also pre-populates the database from an asset file
   * on its first creation.
   *
   * @param context The application context.
   * @return The singleton instance of `PocketMealsDatabase`.
   */
  static PocketMealsDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (PocketMealsDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
                          context.getApplicationContext(),
                          PocketMealsDatabase.class,
                          DATABASE_NAME
                  )
                  .createFromAsset("database/pocketmeals.db")
                  .build();
          Log.i(MainActivity.TAG, "Database Created");
        }
      }
    }
    return INSTANCE;
  }
}
