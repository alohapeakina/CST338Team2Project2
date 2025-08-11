package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.pocketmeals.MainActivity;
import com.example.pocketmeals.database.entities.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: Core database setup for PocketMeals application
 */
@Database(entities = {User.class},version = 1, exportSchema = false)
public abstract class PocketMealsDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "PocketMealsDatabase";
  private static volatile PocketMealsDatabase INSTANCE;
  private static final int NUMBER_OF_THREADS = 4;
  public static final String USER_TABLE = "usertable";

  //TODO: Uncomment code as tables are set up
  /*
  public static final String RECIPE_TABLE = "recipes";
  public static final String RECIPE_INGREDIENTS_TABLE = "recipeingredients";
  public static final String INGREDIENTS_TABLE = "igredients";
  public static final String MEAL_TABLE = "meal";
  */

  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  static PocketMealsDatabase getDatabase(final Context context){
    if(INSTANCE == null){
      synchronized (PocketMealsDatabase.class){
        if(INSTANCE == null){
          INSTANCE = Room.databaseBuilder(
              context.getApplicationContext(),
              PocketMealsDatabase.class,
              DATABASE_NAME
              )
              .fallbackToDestructiveMigration()
              .addCallback(addDefaultValues)
              .build();
        }
      }
    }
    return INSTANCE;
  }

  //Ensures that an admin user account is created by default whenever the database is created
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
        User testUser1 = new User("testuser","testuser1");
        dao.insert(testUser1);
        Log.i(MainActivity.TAG,"Default test user inserted");
      });
    }
  };

  public abstract UserDAO userDAO();

}
