package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pocketmeals.activity.MainActivity;
import com.example.pocketmeals.database.dao.MealDAO;
import com.example.pocketmeals.database.entities.Ingredient;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.RecipeIngredient;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.dao.IngredientDAO;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.dao.RecipeIngredientDAO;
import com.example.pocketmeals.database.dao.UserDAO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Recipe.class, Ingredient.class, RecipeIngredient.class, Meal.class}, version = 6, exportSchema = false)
public abstract class PocketMealsDatabase extends RoomDatabase {
  public abstract UserDAO userDAO();
  public abstract RecipeDAO recipeDAO();
  public abstract IngredientDAO ingredientDAO();
  public abstract RecipeIngredientDAO recipeIngredientDAO();
  public abstract MealDAO mealDAO();
  private static volatile PocketMealsDatabase INSTANCE;
  public static final String DATABASE_NAME = "PocketMealsDatabase";
  public static final String USER_TABLE = "user_table";
  public static final String RECIPE_TABLE = "recipes";
  public static final String INGREDIENT_TABLE = "ingredient_table";
  public static final String RECIPE_INGREDIENTS_TABLE = "recipeingredients";
  public static final String MEAL_TABLE = "meal";
  private static final int NUMBER_OF_THREADS = 4;
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  //TODO: Review whether asynchronous call to populate database should be created
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
                  .fallbackToDestructiveMigration()
                  .build();
          Log.i(MainActivity.TAG, "Database Created");
        }
      }
    }
    return INSTANCE;
  }
}
