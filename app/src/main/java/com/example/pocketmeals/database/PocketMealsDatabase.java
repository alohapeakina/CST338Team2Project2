package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pocketmeals.activity.MainActivity;
import com.example.pocketmeals.database.dao.IngredientDAO;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.dao.RecipeIngredientDAO;
import com.example.pocketmeals.database.dao.UserDAO;
import com.example.pocketmeals.database.entities.Ingredient;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.RecipeIngredient;
import com.example.pocketmeals.database.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Recipe.class, Ingredient.class, RecipeIngredient.class}, version = 5, exportSchema = false)
public abstract class PocketMealsDatabase extends RoomDatabase {
  public abstract UserDAO userDAO();
  public abstract RecipeDAO recipeDAO();
  public abstract IngredientDAO ingredientDAO();
  public abstract RecipeIngredientDAO recipeIngredientDAO();
  private static volatile PocketMealsDatabase INSTANCE;
  public static final String DATABASE_NAME = "PocketMealsDatabase";
  public static final String USER_TABLE = "user_table";
  public static final String RECIPE_TABLE = "recipes";
  public static final String INGREDIENT_TABLE = "ingredient_table";
  public static final String RECIPE_INGREDIENTS_TABLE = "recipeingredients";
  private static final int NUMBER_OF_THREADS = 4;
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  //TODO: Uncomment code as tables are set up
  /*
  public static final String MEAL_TABLE = "meal";
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
                  .fallbackToDestructiveMigration()
                  .addCallback(addDefaultValues(context))
                  .build();
        }
      }
    }
    return INSTANCE;
  }

  private static RoomDatabase.Callback addDefaultValues(Context context) {
    return new RoomDatabase.Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Log.i(MainActivity.TAG, "Database Created");

        databaseWriteExecutor.execute(() -> {
          // Add default users
          UserDAO dao = INSTANCE.userDAO();
          dao.deleteAll();
          User admin = new User("admin1", "admin1");
          admin.setAdmin(true);
          dao.insert(admin);
          Log.i(MainActivity.TAG, "Default Admin user inserted");

          User testUser1 = new User("testuser", "testuser1");
          dao.insert(testUser1);
          Log.i(MainActivity.TAG, "Default test user inserted");

          // Add ingredients from file, not functioning at the moment
          List<Ingredient> ingredients = loadIngredientsFromFile(context);
          INSTANCE.ingredientDAO().insertAll(ingredients);
          Log.i(MainActivity.TAG, "Default ingredients inserted: " + ingredients.size());
        });
      }
    };
  }

  private static List<Ingredient> loadIngredientsFromFile(Context context) {
    List<Ingredient> ingredients = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(context.getAssets().open("ingredientslist.txt")))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length == 7) {
          ingredients.add(new Ingredient(
                  parts[0].trim(), // name
                  parts[1].trim(), // unit
                  Double.parseDouble(parts[2].trim()), // calories
                  Double.parseDouble(parts[3].trim()), // protein
                  Double.parseDouble(parts[4].trim()), // fat
                  Double.parseDouble(parts[5].trim()), // carbs
                  parts[6].trim()  // category
          ));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ingredients;
  }
}
