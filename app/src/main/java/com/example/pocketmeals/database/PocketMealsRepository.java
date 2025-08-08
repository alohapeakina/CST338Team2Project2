package com.example.pocketmeals.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.dao.UserDAO;
import com.example.pocketmeals.database.entities.User;
import java.util.List;

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
 * Explanation: Repository class to manage database operations for Recipe, Meal, and Ingredients entities
 */

public class PocketMealsRepository {
  private static final String TAG = "DataRepository";
  private final RecipeDAO recipeDAO;
  private final MealDAO mealDAO;
  private final IngredientsDAO ingredientsDAO;
  private final ExecutorService executor;
  private final UserDAO userDAO;
  private static PocketMealsRepository repository;


  // Constructor
  public PocketMealsRepository(Context context) {
      PocketMealsDatabase database = PocketMealsDatabase.getDatabase(context);
    this.recipeDAO = database.recipeDAO();
    this.mealDAO = database.mealDAO();
    this.ingredientsDAO = database.ingredientsDAO();
    this.executor = Executors.newFixedThreadPool(4);
    this.userDAO = database.userDAO();
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

    public static void setRepository(PocketMealsRepository repository) {
        PocketMealsRepository.repository = repository;
    }

    public void insertUser(User... user){
    PocketMealsDatabase.databaseWriteExecutor.execute(()->
            userDAO.insert(user));
  }

  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUserName(username);
  }

  public LiveData<User> getUserByUserID(int userId) {
    return userDAO.getUserByUserId(userId);
  }


  public List<Recipe> getAllRecipes() {
    try {
      return recipeDAO.getAllRecipes();
    } catch (Exception e) {
      Log.e(TAG, "Error getting all recipes", e);
      return null;
    }
  }


  public void insertMeal(Meal meal) {
    executor.execute(() -> {
      try {
        mealDAO.insert(meal);
        Log.d(TAG, "Meal inserted: " + meal.getMealType() + " on " + meal.getDay());
      } catch (Exception e) {
        Log.e(TAG, "Error inserting meal", e);
      }
    });
  }

  public void deleteMeal(Meal meal) {
    executor.execute(() -> {
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

  public Meal getMealByDayAndType(String day, String mealType) {
    try {
      return mealDAO.getMealByDayAndType(day, mealType);
    } catch (Exception e) {
      Log.e(TAG, "Error getting meal by day and type", e);
      return null;
    }
  }

  public void shutdown() {
    if (executor != null && !executor.isShutdown()) {
      executor.shutdown();
    }
  }


}