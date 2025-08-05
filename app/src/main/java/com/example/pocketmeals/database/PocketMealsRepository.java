package com.example.pocketmeals.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.pocketmeals.MainActivity;
import com.example.pocketmeals.database.entities.User;
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

  private PocketMealsRepository(Application application){
    PocketMealsDatabase db = PocketMealsDatabase.getDatabase(application);
    this.userDAO = db.userDAO();
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
    }catch (InterruptedException| ExecutionException e){
      Log.i(MainActivity.TAG,"Problem getting PocketMealsRepository, thread error");
    }
    return null;
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

}
