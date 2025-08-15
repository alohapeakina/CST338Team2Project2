package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;

@Entity(tableName = PocketMealsDatabase.RECIPE_INGREDIENTS_TABLE,
        foreignKeys = {
                @ForeignKey(entity = Recipe.class,
                        parentColumns = "recipeId",    // correct for Recipe
                        childColumns = "recipeId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Ingredient.class,
                        parentColumns = "id",          // correct for Ingredient
                        childColumns = "ingredientId",
                        onDelete = ForeignKey.CASCADE)
        })
public class RecipeIngredient {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int recipeId;
    public int ingredientId;
    public double quantity;

    public RecipeIngredient(int recipeId, int ingredientId, double quantity) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}



