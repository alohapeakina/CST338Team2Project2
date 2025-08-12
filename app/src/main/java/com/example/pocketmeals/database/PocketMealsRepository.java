package com.example.pocketmeals.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.pocketmeals.MainActivity;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.RecipeDAO;
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
  private final UserDAO userDAO;
  private static PocketMealsRepository repository;
  private RecipeDAO recipeDAO;
  private LiveData<List<Recipe>> allRecipes;

  private PocketMealsRepository(Application application){
    PocketMealsDatabase db = PocketMealsDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
    recipeDAO = db.recipeDAO();
    allRecipes = recipeDAO.getAllRecipes();
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
}
