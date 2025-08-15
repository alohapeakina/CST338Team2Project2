package com.example.pocketmeals.database.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.Observer;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Ingredient;
import com.example.pocketmeals.database.entities.Recipe;

import com.example.pocketmeals.database.entities.RecipeIngredient;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private final PocketMealsRepository repository;
    private final LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = PocketMealsRepository.getRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public void insert(Recipe recipe) {
        repository.insertRecipe(recipe);
    }

    public void delete(Recipe recipe) {
        repository.deleteRecipe(recipe);
    }

    public void selectIngredientsForRecipe(int recipeId, List<Ingredient> selectedIngredients) {
        // Get the ingredients for the recipe (non-static)
        List<RecipeIngredient> recipeIngredients = repository.getIngredientsForRecipe(recipeId);

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
    }

    // Method to update nutritional info for the recipe
    private void updateRecipeNutritionalInfo(int recipeId, double totalCalories, double totalProtein, double totalCarbs, double totalFat) {
        // Observe the recipe and update its nutritional info
        LiveData<Recipe> recipeLiveData = repository.getRecipeById(recipeId);
        recipeLiveData.observeForever(new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    recipe.setTotalCalories(totalCalories);
                    recipe.setProtein(totalProtein);
                    recipe.setCarbs(totalCarbs);
                    recipe.setFat(totalFat);
                    repository.updateRecipe(recipe);
                }
            }
        });
    }
}
