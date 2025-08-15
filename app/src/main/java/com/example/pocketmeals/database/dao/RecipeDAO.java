package com.example.pocketmeals.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketmeals.database.entities.Recipe;

import java.util.List;

/**
 * @author Team 2
 * Created 8/5/2025
 * The Data Access Object for the {@link Recipe} entity.
 * This interface defines the standard database operations that can be performed
 * on the `recipes` table, such as inserting, updating, deleting, and querying recipes.
 * The methods are designed to be used by the repository to abstract database access
 * from the rest of the application.
 */
@Dao
public interface RecipeDAO {

    /**
     * Inserts one or more recipes into the database.
     *
     * @param recipes An array of {@link Recipe} objects to insert.
     */
    @Insert
    void insert(Recipe... recipes);

    /**
     * Updates one or more existing recipes in the database.
     *
     * @param recipes An array of {@link Recipe} objects to update.
     */
    @Update
    void update(Recipe... recipes);

    /**
     * Deletes a single recipe from the database.
     *
     * @param recipe The {@link Recipe} object to delete.
     */
    @Delete
    void delete(Recipe recipe);

    /**
     * Retrieves a `LiveData` list of all recipes from the database,
     * ordered alphabetically by recipe name.
     *
     * @return A `LiveData` object containing a {@link List} of {@link Recipe}.
     */
    @Query("SELECT * FROM recipes ORDER BY recipeName ASC")
    LiveData<List<Recipe>> getAllRecipes();

    /**
     * Retrieves a `LiveData` object for a single recipe by its unique ID.
     *
     * @param id The unique ID of the recipe to retrieve.
     * @return A `LiveData` object containing the {@link Recipe} with the specified ID.
     */
    @Query("SELECT * FROM recipes WHERE recipeId = :id LIMIT 1")
    LiveData<Recipe> getRecipeById(int id);
}
