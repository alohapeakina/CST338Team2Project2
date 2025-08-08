package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Ingredients entity for storing ingredient information in Room database
 */

@Entity(tableName = "ingredients_table")
public class Ingredients {

    @PrimaryKey(autoGenerate = true)
    private int ingredientId;

    private String ingredientName;
    private String category; // e.g., "Produce", "Dairy", "Meat", "Pantry", etc.

    // Constructor
    public Ingredients(String ingredientName, String category) {
        this.ingredientName = ingredientName;
        this.category = category;
    }

    // Getters and Setters
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
