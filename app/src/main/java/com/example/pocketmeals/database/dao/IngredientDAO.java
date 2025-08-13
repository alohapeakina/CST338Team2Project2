package com.example.pocketmeals.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pocketmeals.database.entities.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredient_table ORDER BY name ASC")
    List<Ingredient> getAllIngredients();
}
