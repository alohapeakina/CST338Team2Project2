package com.example.pocketmeals.database;

import android.app.Application;

import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.dao.MealDAO;
import com.example.pocketmeals.database.entities.Ingredient;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;

import com.example.pocketmeals.database.dao.IngredientDAO;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.dao.UserDAO;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: Data access and database operations for application
 */
public class PocketMealsRepository {
  private static final String TAG = "POCKETMEALSREPOSITORY";
  private final UserDAO userDAO;
  private static PocketMealsRepository repository;
  private RecipeDAO recipeDAO;
  private MealDAO mealDAO;
  private IngredientDAO ingredientDAO;
  private LiveData<List<Recipe>> allRecipes;
  private LiveData<List<Ingredient>> allIngredients;

  private PocketMealsRepository(Application application){
    PocketMealsDatabase db = PocketMealsDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
    this.recipeDAO = db.recipeDAO();
    this.mealDAO = db.mealDAO();
    this.ingredientDAO = db.ingredientDAO();
    allRecipes = recipeDAO.getAllRecipes();
    allIngredients = ingredientDAO.getAllIngredients();
  }

  public static PocketMealsRepository getRepository(Application application){
    if(repository != null){
      return repository;
    }

    //TODO: Replace with Future functionality
    return new PocketMealsRepository(application);

/*    Future<PocketMealsRepository> future = PocketMealsDatabase.databaseWriteExecutor.submit(
        new Callable<PocketMealsRepository>() {
          @Override
          public PocketMealsRepository call() throws Exception {
            return new PocketMealsRepository(application);
          }
        }
    );
    try{
      return future.get();
    }catch (InterruptedException| ExecutionException e){
      Log.i(MainActivity.TAG,"Problem getting PocketMealsRepository, thread error");
    }
    return null;*/


  }

  public void insertUser(User... user){
    PocketMealsDatabase.databaseWriteExecutor.execute(()->
    {
      userDAO.insert(user);
    });
  }

  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUserName(username);
  }

  public LiveData<User> getUserByUserId(int userId) {
    return userDAO.getUserByUserId(userId);
  }
  public LiveData<List<Recipe>> getAllRecipes() {
    return allRecipes;
  }

  public LiveData<List<Ingredient>> getAllIngredients() {
    return allIngredients;
  }

  public void insertRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.insert(recipe);
    });
  }

  public void updateRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.update(recipe);
    });
  }

  public void deleteRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.delete(recipe);
    });
  }

  // ============= MEAL METHODS =============

  public void insertMeal(Meal meal) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      try {
        mealDAO.insert(meal);
        Log.d(TAG, "Meal inserted: " + meal.getMealType() + " on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error inserting meal", e);
      }
    });
  }

  public void updateMeal(Meal meal) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      try {
        mealDAO.update(meal);
        Log.d(TAG, "Meal updated: " + meal.getMealType() + " on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error updating meal", e);
      }
    });
  }

  public void deleteMeal(Meal meal) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      try {
        mealDAO.delete(meal);
        Log.d(TAG, "Meal deleted: " + meal.getMealType() + " on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error deleting meal", e);
      }
    });
  }

  public List<Meal> getAllMeals() {
    try {
      return mealDAO.getAllMeals();
    } catch (Exception e) {
      Log.e(TAG, "Error getting all meals", e);
      return null;
    }
  }

  public Meal getMealById(int mealId) {
    try {
      return mealDAO.getMealById(mealId);
    } catch (Exception e) {
      Log.e(TAG, "Error getting meal by ID", e);
      return null;
    }
  }

  public List<Meal> getMealsByDay(String day) {
    try {
      return mealDAO.getMealsByDay(day);
    } catch (Exception e) {
      Log.e(TAG, "Error getting meals by day", e);
      return null;
    }
  }

  public List<Meal> getMealsByType(String mealType) {
    try {
      return mealDAO.getMealsByType(mealType);
    } catch (Exception e) {
      Log.e(TAG, "Error getting meals by type", e);
      return null;
    }
  }

  public Meal getMealByDayAndType(String day, String mealType) {
    try {
      return mealDAO.getMealByDayAndType(day, mealType);
    } catch (Exception e) {
      Log.e(TAG, "Error getting meal by day and type", e);
      return null;
    }
  }

  public List<String> getAllDays() {
    try {
      return mealDAO.getAllDays();
    } catch (Exception e) {
      Log.e(TAG, "Error getting all days", e);
      return null;
    }
  }

  public void shutdown() {
    if (PocketMealsDatabase.databaseWriteExecutor != null && !PocketMealsDatabase.databaseWriteExecutor.isShutdown()) {
      PocketMealsDatabase.databaseWriteExecutor.shutdown();
    }
  }


}
