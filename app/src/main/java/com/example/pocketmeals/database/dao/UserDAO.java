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
 * @author Team 2
 * created: 8/4/2025
 * Explanation: This Data Access Object defines the methods for interacting with the `user` table
 * in the database. It provides operations to insert, delete, update, and query user data.
 * The methods include both synchronous and asynchronous (LiveData) queries to fit
 * different application needs.
 */
@Dao
public interface UserDAO {

  /**
   * Inserts one or more users into the database. If a user with the same primary key
   * already exists, it will be replaced.
   *
   * @param user An array of {@link User} objects to insert.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(User... user);

  /**
   * Deletes a user from the database.
   *
   * @param user The {@link User} object to delete.
   */
  @Delete
  void delete(User user);

  /**
   * Retrieves a `LiveData` list of all users from the database, ordered by username.
   * The use of `LiveData` allows for observing changes to the data.
   *
   * @return A `LiveData` object containing a {@link List} of all {@link User} objects.
   */
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " ORDER BY username")
  LiveData<List<User>> getAllUsers();

  /**
   * Deletes all users from the database.
   */
  @Query("DELETE FROM " + PocketMealsDatabase.USER_TABLE)
  void deleteAll();

  /**
   * Retrieves a `LiveData` object for a user based on their username.
   *
   * @param username The username of the user to retrieve.
   * @return A `LiveData` object containing the {@link User} with the specified username.
   */
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE username == :username")
  LiveData<User> getUserByUserName(String username);

  /**
   * Retrieves a `LiveData` object for a user based on their unique ID.
   *
   * @param userId The ID of the user to retrieve.
   * @return A `LiveData` object containing the {@link User} with the specified ID.
   */
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE id == :userId")
  LiveData<User> getUserByUserId(int userId);

  /**
   * Updates one or more existing users in the database.
   *
   * @param users An array of {@link User} objects to update.
   */
  @Update
  void update(User... users);

  /**
   * Retrieves a single {@link User} object from the database based on their username.
   * This is a synchronous query and should not be run on the main thread.
   *
   * @param username The username of the user to retrieve.
   * @return The {@link User} with the specified username, or null if not found.
   */
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " WHERE username == :username")
  User getUserByUserNameSync(String username);

  /**
   * Retrieves a `List` of all users from the database, ordered by username.
   * This is a synchronous query, intended for testing purposes to avoid
   * the complexities of `LiveData` in a testing environment.
   *
   * @return A {@link List} of all {@link User} objects.
   */
  @Query("SELECT * FROM " + PocketMealsDatabase.USER_TABLE + " ORDER BY username")
  List<User> getUserList();
}
