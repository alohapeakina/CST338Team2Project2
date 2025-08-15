package com.example.pocketmeals.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketmeals.database.PocketMealsDatabase;
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

  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " ORDER BY username")
  LiveData<List<User>> getAllUsers();

  @Query("DELETE FROM " + PocketMealsDatabase.USER_TABLE)
  void deleteAll();

  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE username == :username")
  LiveData<User> getUserByUserName(String username);

  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE id == :userId")
  LiveData<User> getUserByUserId(int userId);
  
  @Update
  void update(User... users);

  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE username == :username")
  User getUserByUserNameSync(String username);

  //Test purposes to avoid needing to create a testing utility
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " ORDER BY username")
  List<User> getUserList();

  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE id == :userId")
  User getUserByUserIdSync(int userId);
}
