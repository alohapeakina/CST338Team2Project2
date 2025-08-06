package com.example.cst338team2project2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst338team2project2.database.dao.IngredientsDAO;
import com.example.cst338team2project2.database.dao.MealDAO;
import com.example.cst338team2project2.database.dao.RecipeDAO;
import com.example.cst338team2project2.database.entities.Ingredients;
import com.example.cst338team2project2.database.entities.Meal;
import com.example.cst338team2project2.database.entities.Recipe;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Room database configuration for meal planning app
 */

@Database(
        entities = {Recipe.class, Meal.class, Ingredients.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "meal_planning_database";
    private static volatile AppDatabase INSTANCE;

    // Abstract methods to get DAOs
    public abstract RecipeDAO recipeDAO();
    public abstract MealDAO mealDAO();
    public abstract IngredientsDAO ingredientsDAO();

    // Singleton pattern for database instance
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration() // For development - remove in production
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Method to close database
    public static void closeDatabase() {
        if (INSTANCE != null && INSTANCE.isOpen()) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }
}