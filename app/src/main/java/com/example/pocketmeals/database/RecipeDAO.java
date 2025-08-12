package com.example.pocketmeals.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketmeals.database.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insert(Recipe... recipes);

    @Update
    void update(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipes ORDER BY recipeName ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE recipeId = :id LIMIT 1")
    LiveData<Recipe> getRecipeById(int id);
}
