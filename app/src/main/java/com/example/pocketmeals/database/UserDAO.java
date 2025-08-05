package com.example.pocketmeals.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.pocketmeals.database.entities.User;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: DAO for user class
 */
@Dao
public interface UserDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... user);

  @Delete
  void delete(User user);

  @Query("SELECT * FROM" + PocketMealsDatabase.USER_TABLE + " ORDER BY username")
  LiveData<List<User>> getAllUsers();

  @Query("DELETE FROM " + PocketMealsDatabase.USER_TABLE)
  void deleteAll();


}
