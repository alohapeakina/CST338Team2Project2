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
 * @author Team 2
 * created: 8/4/2025
 * Explanation: Data access and database operations for application
 * This repository is a central point for all data access and database operations in the Pocket Meals application.
 * It provides a clean API for the rest of the application to retrieve and manipulate data,
 * abstracting the complexities of database interactions. It implements the singleton pattern
 * to ensure only one instance exists and uses a dedicated executor to perform all database
 * operations on a background thread.
 */
public class PocketMealsRepository {
  private static final String TAG = "POCKETMEALSREPOSITORY";
  private final UserDAO userDAO;
  private static PocketMealsRepository repository;
  private RecipeDAO recipeDAO;
  private MealDAO mealDAO;
  private LiveData<List<Recipe>> allRecipes;
  private LiveData<List<User>> allAccounts;

  /**
   * Private constructor to enforce the singleton pattern.
   *
   * @param application The application context.
   */
  private PocketMealsRepository(Application application) {
    PocketMealsDatabase db = PocketMealsDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
    this.recipeDAO = db.recipeDAO();
    this.mealDAO = db.mealDAO();
    allRecipes = recipeDAO.getAllRecipes();
    allAccounts = userDAO.getAllUsers();
  }

  /**
   * Returns the singleton instance of the `PocketMealsRepository`.
   * This method ensures that the repository is initialized on a background thread
   * to avoid blocking the UI thread.
   *
   * @param application The application context.
   * @return The singleton instance of `PocketMealsRepository`.
   */
  public static PocketMealsRepository getRepository(Application application) {
    if (repository != null) {
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
    try {
      return future.get();
    } catch (InterruptedException | ExecutionException e) {
      Log.i(MainActivity.TAG, "Problem getting PocketMealsRepository, thread error");
    }
    return null;


  }

  // ============= USER METHODS =============
  /**
   * Checks if a user with the given username already exists. If not, it creates a new user.
   * The callbacks are executed on the UI thread to update the UI after the database
   * operation is complete.
   *
   * @param username The username to check and create.
   * @param password The password for the new user.
   * @param onExists A `Runnable` to execute if the user already exists.
   * @param onCreate A `Runnable` to execute if the user is successfully created.
   */
  public void checkUserAndCreate(String username, String password, Runnable onExists,
      Runnable onCreate) {
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

  /**
   * Retrieves a `LiveData` object for a user based on their username.
   *
   * @param username The username of the user.
   * @return A `LiveData` object containing the {@link User} with the specified username.
   */
  public LiveData<User> getUserByUserName(String username) {
    return userDAO.getUserByUserName(username);
  }

  /**
   * Retrieves a `LiveData` object for a user based on their unique ID.
   *
   * @param userId The ID of the user.
   * @return A `LiveData` object containing the {@link User} with the specified ID.
   */
  public LiveData<User> getUserByUserId(int userId) {
    return userDAO.getUserByUserId(userId);
  }

  /**
   * Retrieves a `LiveData` object containing a list of all user accounts.
   *
   * @return A `LiveData` list of all {@link User} objects.
   */
  public LiveData<List<User>> getAllAccounts() {
    return allAccounts;
  }

  // ============= RECIPE METHODS =============
  /**
   * Retrieves a `LiveData` object containing a list of all recipes.
   *
   * @return A `LiveData` list of all {@link Recipe} objects.
   */
  public LiveData<List<Recipe>> getAllRecipes() {
    return allRecipes;
  }

  /**
   * Inserts a new recipe into the database on a background thread.
   *
   * @param recipe The {@link Recipe} object to insert.
   */
  public void insertRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.insert(recipe);
    });
  }

  /**
   * Retrieves a `LiveData` object for a recipe based on its unique ID.
   *
   * @param recipeId The ID of the recipe.
   * @return A `LiveData` object containing the {@link Recipe} with the specified ID.
   */
  public LiveData<Recipe> getRecipeById(int recipeId) {
    return recipeDAO.getRecipeById(recipeId);
  }

  /**
   * Updates an existing recipe in the database on a background thread.
   *
   * @param recipe The {@link Recipe} object to update.
   */
  public void updateRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.update(recipe);
    });
  }

  /**
   * Deletes a recipe from the database on a background thread.
   *
   * @param recipe The {@link Recipe} object to delete.
   */
  public void deleteRecipe(Recipe recipe) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> {
      recipeDAO.delete(recipe);
    });
  }

  // ============= MEAL METHODS =============
  /**
   * Inserts a new meal into the database on a background thread.
   *
   * @param meal The {@link Meal} object to insert.
   */
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

  /**
   * Retrieves a `LiveData` list of all meals for a specific user, with their corresponding recipe names.
   *
   * @param userId The ID of the user to filter the meals by.
   * @return A `LiveData` object containing a {@link List} of {@link MealRecipeName}.
   */
  public LiveData<List<MealRecipeName>> getAllMealsWithRecipeNameForUser(int userId) {
    return mealDAO.getAllMealsWithRecipeNameForUser(userId);
  }

  /**
   * Retrieves `LiveData` containing the sum of nutritional values (calories, protein, fat, carbs)
   * for all meals planned by a specific user.
   *
   * @param userId The ID of the user whose nutritional totals are to be calculated.
   * @return A `LiveData` object holding a {@link MealDAO.NutritionTotals} object.
   */
  public LiveData<MealDAO.NutritionTotals> getNutritionTotalsForUser(int userId) {
    return mealDAO.getNutritionTotalsForUser(userId);
  }

  /**
   * Deletes a meal from the database by its ID.
   * This is a synchronous operation.
   *
   * @param mealId The ID of the meal to delete.
   */
  public void deleteMealById(int mealId) {
    mealDAO.deleteMealById(mealId);
  }

  // ============= ADMIN METHODS =============
  /**
   * Deletes a user from the database on a background thread.
   *
   * @param user The {@link User} object to delete.
   */
  public void deleteUser(User user) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> userDAO.delete(user));
  }

  /**
   * Updates an existing user in the database on a background thread.
   *
   * @param user The {@link User} object to update.
   */
  public void updateUser(User user) {
    PocketMealsDatabase.databaseWriteExecutor.execute(() -> userDAO.update(user));
  }
}
