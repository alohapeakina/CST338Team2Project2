package com.example.pocketmeals.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.pocketmeals.database.entities.User;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: DAO for user class
 */
@Dao
public interface UserDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... user);

}
