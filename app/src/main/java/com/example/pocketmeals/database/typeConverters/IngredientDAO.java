package com.example.pocketmeals.database.typeConverters;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.pocketmeals.database.entities.Ingredient;
import java.util.List;

@Dao
public interface IngredientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient... ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Query("SELECT * FROM " + PocketMealsDatabase.INGREDIENT_TABLE + " ORDER BY name ASC")
    LiveData<List<Ingredient>> getAllIngredients();

}
