package com.example.cst338team2project2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338team2project2.database.entities.Ingredients;

import java.util.List;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Data Access Object for Ingredients entity
 */

@Dao
public interface IngredientsDAO {

    @Insert
    void insert(Ingredients ingredients);

    @Update
    void update(Ingredients ingredients);

    @Delete
    void delete(Ingredients ingredients);

    @Query("SELECT * FROM ingredients_table ORDER BY ingredientName ASC")
    List<Ingredients> getAllIngredients();

    @Query("SELECT * FROM ingredients_table WHERE ingredientId = :ingredientId")
    Ingredients getIngredientById(int ingredientId);

    @Query("SELECT * FROM ingredients_table WHERE ingredientName = :ingredientName")
    Ingredients getIngredientByName(String ingredientName);

    @Query("SELECT * FROM ingredients_table WHERE category = :category ORDER BY ingredientName ASC")
    List<Ingredients> getIngredientsByCategory(String category);

    @Query("SELECT DISTINCT category FROM ingredients_table ORDER BY category ASC")
    List<String> getAllCategories();

    @Query("DELETE FROM ingredients_table")
    void deleteAllIngredients();

    @Query("DELETE FROM ingredients_table WHERE category = :category")
    void deleteIngredientsByCategory(String category);

    @Query("SELECT COUNT(*) FROM ingredients_table")
    int getIngredientsCount();

    @Query("SELECT COUNT(*) FROM ingredients_table WHERE category = :category")
    int getIngredientsCountByCategory(String category);
}