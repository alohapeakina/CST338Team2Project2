package com.example.pocketmeals.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.pocketmeals.MainActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: Core database setup for PocketMeals application
 */
public abstract class PocketMealsDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "PocketMealsDatabase";
  private static volatile PocketMealsDatabase INSTANCE;
  private static final int NUMBER_OF_THREADS = 4;

  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  //TODO: Uncomment code after UserDAO is created and addDefaultValues is functional
/*  static PocketMealsDatabase getDatabase(final Context context){
    if(INSTANCE == null){
      synchronized (PocketMealsDatabase.class){
        if(INSTANCE == null){
          INSTANCE = Room.databaseBuilder(
              context.getApplicationContext(),
              PocketMealsDatabase.class,
              DATABASE_NAME
              )
              .addCallback(addDefaultValues)
              .build;
        }
      }
    }
    return INSTANCE;
  }*/

  //TODO: Uncomment this code after UserDAO is created
  //Ensures that an admin user account is created by default whenever the database is created
/*  private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db){
      super.onCreate(db);
      Log.i(MainActivity.TAG,"Database Created");
      databaseWriteExecutor.execute(()->{
        UserDAO dao = INSTANCE.userDAO();
        dao.deleteAll();
        User admin = new User("admin1","admin1");
        admin.setAdmin(true);
        dao.insert(admin);
      });
    }
  };

  public abstract UserDAO userDAO();*/

}
