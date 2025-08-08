package com.example.pocketmeals.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.PocketMealsDatabase;

import java.util.List;

@Dao
public interface UserDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... user);

  @Delete
  void delete(User user);

  @Query("SELECT * FROM user_table ORDER BY username")
  LiveData<List<User>> getAllUsers();

  @Query("DELETE FROM user_table" )
  void deleteAll();

  @Query("SELECT * FROM user_table WHERE username = :username")
  LiveData<User> getUserByUserName(String username);

  @Query("SELECT * FROM user_table WHERE id = :userID")
  LiveData<User> getUserByUserID(int userID);
  @Query("SELECT * FROM user_table WHERE id = :userId")
  LiveData<User> getUserByUserId(int userId);
}