package com.example.cst338team2project2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst338team2project2.database.entities.Recipe;

import java.util.List;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Data Access Object for Recipe entity
 */

@Dao
public interface RecipeDAO {

    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipe_table ORDER BY recipeName ASC")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipe_table WHERE recipeId = :recipeId")
    Recipe getRecipeById(int recipeId);

    @Query("SELECT * FROM recipe_table WHERE recipeName = :recipeName")
    Recipe getRecipeByName(String recipeName);

    @Query("SELECT * FROM recipe_table WHERE caloriesPerServing <= :maxCalories")
    List<Recipe> getRecipesByMaxCalories(int maxCalories);

    @Query("SELECT * FROM recipe_table WHERE gramsOfProtein >= :minProtein")
    List<Recipe> getRecipesByMinProtein(double minProtein);

    @Query("DELETE FROM recipe_table")
    void deleteAllRecipes();

    @Query("SELECT COUNT(*) FROM recipe_table")
    int getRecipeCount();
}