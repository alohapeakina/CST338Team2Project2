package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.pocketmeals.database.typeConverters.PocketMealsDatabase;

@Entity(tableName = PocketMealsDatabase.RECIPE_INGREDIENTS_TABLE,
        primaryKeys = {"recipeId", "ingredientId"},
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
    public int recipeId;
    public int ingredientId;
    public double quantity;

    public RecipeIngredient(int recipeId, int ingredientId, double quantity) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}



