package com.example.pocketmeals.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import androidx.room.PrimaryKey;
import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.dao.RecipeIngredientDAO;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    //TODO: Find which class to put this logic in
/*    public void selectIngredientsForRecipe(int recipeId, List<Ingredient> selectedIngredients) {
        List<RecipeIngredient> recipeIngredients = RecipeIngredientDAO.getIngredientsForRecipe(recipeId);

        double totalCalories = 0;
        double totalProtein = 0;
        double totalFat = 0;
        double totalCarbs = 0;

        for(Ingredient ingredient : selectedIngredients) {

            double ingredientCalories = ingredient.getCalories();
            double ingredientProtein = ingredient.getProtein();
            double ingredientFat = ingredient.getFat();
            double ingredientCarbs = ingredient.getCarbs();


            totalCalories += ingredientCalories;
            totalProtein += ingredientProtein;
            totalFat += ingredientFat;
            totalCarbs += ingredientCarbs;
        }

        updateRecipeNutritionalInfo(recipeId, totalCalories, totalProtein, totalFat, totalCarbs);
    }*/
}



