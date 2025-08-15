package com.example.pocketmeals.database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Meal entity for storing meal information in Room database
 */

@Entity(tableName = "meal_table")
public class Meal {

  @PrimaryKey(autoGenerate = true)
  private int mealId;

  private String day; // e.g., "Monday", "Tuesday", etc. or date format

  // Constructor
  public Meal(String day) {
    this.day = day;
  }

  // Getters and Setters
  public int getMealId() {
    return mealId;
  }

  public void setMealId(int mealId) {
    this.mealId = mealId;
  }

  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  @Override
  public String toString() {
    return "Meal{" +
        "mealId=" + mealId +
/*        ", mealType='" + mealType + '\'' +*/
        ", day='" + day + '\'' +
        '}';
  }
}