package com.example.pocketmeals.database;

import android.app.Application;

import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.activity.MainActivity;
import com.example.pocketmeals.database.dao.MealDAO;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.MealRecipeName;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.dao.RecipeDAO;
import com.example.pocketmeals.database.dao.UserDAO;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
  private LiveData<List<Recipe>> allRecipes;
  private LiveData<List<User>> allAccounts;

  private PocketMealsRepository(Application application){
    PocketMealsDatabase db = PocketMealsDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
    this.recipeDAO = db.recipeDAO();
    this.mealDAO = db.mealDAO();
    allRecipes = recipeDAO.getAllRecipes();
    allAccounts = userDAO.getAllUsers();
  }

  public static PocketMealsRepository getRepository(Application application){
    if(repository != null){
      return repository;
    }

    Future<PocketMealsRepository> future = PocketMealsDatabase.databaseWriteExecutor.submit(
        new Callable<PocketMealsRepository>() {
          @Override
          public PocketMealsRepository call() throws Exception {
            return new PocketMealsRepository(application);
          }
        }
    );
    try{
      return future.get();
    }catch (InterruptedException | ExecutionException e){
      Log.i(MainActivity.TAG,"Problem getting PocketMealsRepository, thread error");
    }
    return null;


  }

  // ============= USER METHODS =============
  public void insertUser(User... user){
    PocketMealsDatabase.databaseWriteExecutor.execute(()->
    {
      userDAO.insert(user);
    });
  }

  public void checkUserAndCreate(String username, String password, Runnable onExists, Runnable onCreate) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      User existingUser = userDAO.getUserByUserNameSync(username);
      if (existingUser != null) {
        onExists.run();
      } else {
        userDAO.insert(new User(username, password));
        onCreate.run();
      }
    });
  }

  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUserName(username);
  }

  public LiveData<User> getUserByUserId(int userId) {
    return userDAO.getUserByUserId(userId);
  }
  public LiveData<List<User>> getAllAccounts() { return allAccounts; }

  // ============= RECIPE METHODS =============
  public LiveData<List<Recipe>> getAllRecipes() {
    return allRecipes;
  }

  public void insertRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.insert(recipe);
    });
  }

  public LiveData<Recipe> getRecipeById(int recipeId) {
    return recipeDAO.getRecipeById(recipeId);
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
        Log.d(TAG, "Meal inserted: " + " on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error inserting meal", e);
      }
    });
  }

  public void updateMeal(Meal meal) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      try {
        mealDAO.update(meal);
        Log.d(TAG, "Meal updated: on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error updating meal", e);
      }
    });
  }

  public void deleteMeal(Meal meal) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      try {
        mealDAO.delete(meal);
        Log.d(TAG, "Meal deleted: on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error deleting meal", e);
      }
    });
  }

  public void deleteMealById(int mealId) {
    mealDAO.deleteMealById(mealId);
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

  public List<String> getAllDays() {
    try {
      return mealDAO.getAllDays();
    } catch (Exception e) {
      Log.e(TAG, "Error getting all days", e);
      return null;
    }
  }

  public LiveData<List<MealRecipeName>> getAllMealsWithRecipeName() {
    return mealDAO.getAllMealsWithRecipeName();
  }
  public LiveData<List<MealRecipeName>> getAllMealsWithRecipeNameForUser(int userId) {
    return mealDAO.getAllMealsWithRecipeNameForUser(userId);
  }

  // ============= ADMIN METHODS =============
  public void deleteUser(User user) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> userDAO.delete(user));
  }

  public void updateUser(User user) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> userDAO.update(user));
  }


}
