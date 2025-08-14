package com.example.pocketmeals.database.typeConverters;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pocketmeals.database.entities.RecipeIngredient;

import java.util.List;

@Dao
public interface RecipeIngredientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeIngredient recipeIngredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RecipeIngredient> recipeIngredients);

    @Query("SELECT * FROM recipeingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);
}
