package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.pocketmeals.MainActivity;
import com.example.pocketmeals.database.dao.UserDAO;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.dao.IngredientsDAO;
import com.example.pocketmeals.database.dao.MealDAO;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.entities.Ingredients;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.Recipe;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Room database configuration for meal planning app
 */

@Database(
        entities = {Recipe.class, Meal.class, Ingredients.class, User.class},
        version = 1,
        exportSchema = false
)
public abstract class PocketMealsDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "PocketMealsDatabase";
  private static volatile PocketMealsDatabase INSTANCE;
  private static final int NUMBER_OF_THREADS = 4;

  // Abstract methods to get DAOs
  public abstract RecipeDAO recipeDAO();
  public abstract MealDAO mealDAO();
  public abstract IngredientsDAO ingredientsDAO();
  public abstract UserDAO userDAO();
  static final ExecutorService databaseWriteExecutor =
          Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  // Singleton pattern for database instance
  public static PocketMealsDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (PocketMealsDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(
                          context.getApplicationContext(),
                          PocketMealsDatabase.class,
                          DATABASE_NAME
                  )
                  .fallbackToDestructiveMigration() // For development - remove in production
                  .build();
        }
      }
    }
    return INSTANCE;
  }

  private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db){
      super.onCreate(db);
      Log.i(MainActivity.TAG,"Database Created");
      databaseWriteExecutor.execute(() -> {
        UserDAO dao = INSTANCE.userDAO();
        dao.deleteAll();
        User admin = new User("admin1","admin1");
        admin.setAdmin(true);
        dao.insert(admin);
        Log.i(MainActivity.TAG,"Default Admin user inserted");
      });
    }
  };

  // Method to close database
  public static void closeDatabase() {
    if (INSTANCE != null && INSTANCE.isOpen()) {
      INSTANCE.close();
      INSTANCE = null;
    }
  }
  
}
