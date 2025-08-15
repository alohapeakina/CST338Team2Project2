package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;
import java.util.Objects;

/**
 * @author Andrew Lee
 * created: 8/4/2025
 * Explanation: This class represents a user entity in the Room database.
 * It is a Plain Old Java Object (POJO) with fields for a user's unique ID, username, password,
 * and an administrator status flag. The class is mapped to a table named `user_table`
 * as defined in {@link PocketMealsDatabase}. It includes a constructor, getters, setters, and
 * overrides of `equals()` and `hashCode()` for object comparison.
 */
@Entity(tableName = PocketMealsDatabase.USER_TABLE)
public class User {
  @PrimaryKey(autoGenerate = true)
  private int id;
  private String username;
  private String password;
  private boolean isAdmin;

  /**
   * Constructs a new `User` object. The administrator status is set to `false` by default.
   *
   * @param username The username of the new user.
   * @param password The password of the new user.
   */
  public User(String username, String password){
    this.username = username;
    this.password = password;
    isAdmin = false;
  }

  /**
   * Compares this user object with another object for equality.
   * Two users are considered equal if all their fields (`id`, `username`, `password`, `isAdmin`) are the same.
   *
   * @param o The object to compare with.
   * @return `true` if the objects are equal, `false` otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id && isAdmin == user.isAdmin && Objects.equals(username,
        user.username) && Objects.equals(password, user.password);
  }

  /**
   * Generates a hash code for the user object based on its fields.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, isAdmin);
  }

  // Getters and Setters
  /**
   * Gets the unique ID of the user.
   *
   * @return The user's unique ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique ID of the user.
   *
   * @param id The new ID to set.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the username of the user.
   *
   * @return The user's username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username The new username to set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password of the user.
   *
   * @return The user's password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password The new password to set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Checks if the user has administrator privileges.
   *
   * @return `true` if the user is an admin, `false` otherwise.
   */
  public boolean isAdmin() {
    return isAdmin;
  }

  /**
   * Sets the administrator status of the user.
   *
   * @param admin The new admin status (`true` for admin, `false` for regular user).
   */
  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
