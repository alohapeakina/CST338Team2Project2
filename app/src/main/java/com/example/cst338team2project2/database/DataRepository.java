package com.example.cst338team2project2.database;

import android.content.Context;
import android.util.Log;

import com.example.cst338team2project2.database.dao.IngredientsDAO;
import com.example.cst338team2project2.database.dao.MealDAO;
import com.example.cst338team2project2.database.dao.RecipeDAO;
import com.example.cst338team2project2.database.entities.Ingredients;
import com.example.cst338team2project2.database.entities.Meal;
import com.example.cst338team2project2.database.entities.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jake Castro
 * Created 8/5/2025
 * Explanation: Repository class to manage database operations for Recipe, Meal, and Ingredients entities
 */

public class DataRepository {
    private static final String TAG = "DataRepository";
    private RecipeDAO recipeDAO;
    private MealDAO mealDAO;
    private IngredientsDAO ingredientsDAO;
    private ExecutorService executor;

    // Constructor
    public DataRepository(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        this.recipeDAO = database.recipeDAO();
        this.mealDAO = database.mealDAO();
        this.ingredientsDAO = database.ingredientsDAO();
        this.executor = Executors.newFixedThreadPool(4);
    }

    // ============= RECIPE METHODS =============

    public void insertRecipe(Recipe recipe) {
        executor.execute(() -> {
            try {
                recipeDAO.insert(recipe);
                Log.d(TAG, "Recipe inserted: " + recipe.getRecipeName());
            } catch (Exception e) {
                Log.e(TAG, "Error inserting recipe", e);
            }
        });
    }

    public void updateRecipe(Recipe recipe) {
        executor.execute(() -> {
            try {
                recipeDAO.update(recipe);
                Log.d(TAG, "Recipe updated: " + recipe.getRecipeName());
            } catch (Exception e) {
                Log.e(TAG, "Error updating recipe", e);
            }
        });
    }

    public void deleteRecipe(Recipe recipe) {
        executor.execute(() -> {
            try {
                recipeDAO.delete(recipe);
                Log.d(TAG, "Recipe deleted: " + recipe.getRecipeName());
            } catch (Exception e) {
                Log.e(TAG, "Error deleting recipe", e);
            }
        });
    }

    public List<Recipe> getAllRecipes() {
        try {
            return recipeDAO.getAllRecipes();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all recipes", e);
            return null;
        }
    }

    public Recipe getRecipeById(int recipeId) {
        try {
            return recipeDAO.getRecipeById(recipeId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting recipe by ID", e);
            return null;
        }
    }

    public Recipe getRecipeByName(String recipeName) {
        try {
            return recipeDAO.getRecipeByName(recipeName);
        } catch (Exception e) {
            Log.e(TAG, "Error getting recipe by name", e);
            return null;
        }
    }

    public List<Recipe> getRecipesByMaxCalories(int maxCalories) {
        try {
            return recipeDAO.getRecipesByMaxCalories(maxCalories);
        } catch (Exception e) {
            Log.e(TAG, "Error getting recipes by max calories", e);
            return null;
        }
    }

    public List<Recipe> getRecipesByMinProtein(double minProtein) {
        try {
            return recipeDAO.getRecipesByMinProtein(minProtein);
        } catch (Exception e) {
            Log.e(TAG, "Error getting recipes by min protein", e);
            return null;
        }
    }

    // ============= MEAL METHODS =============

    public void insertMeal(Meal meal) {
        executor.execute(() -> {
            try {
                mealDAO.insert(meal);
                Log.d(TAG, "Meal inserted: " + meal.getMealType() + " on " + meal.getDay());
            } catch (Exception e) {
                Log.e(TAG, "Error inserting meal", e);
            }
        });
    }

    public void updateMeal(Meal meal) {
        executor.execute(() -> {
            try {
                mealDAO.update(meal);
                Log.d(TAG, "Meal updated: " + meal.getMealType() + " on " + meal.getDay());
            } catch (Exception e) {
                Log.e(TAG, "Error updating meal", e);
            }
        });
    }

    public void deleteMeal(Meal meal) {
        executor.execute(() -> {
            try {
                mealDAO.delete(meal);
                Log.d(TAG, "Meal deleted: " + meal.getMealType() + " on " + meal.getDay());
            } catch (Exception e) {
                Log.e(TAG, "Error deleting meal", e);
            }
        });
    }

    public List<Meal> getAllMeals() {
        try {
            return mealDAO.getAllMeals();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all meals", e);
            return null;
        }
    }

    public Meal getMealById(int mealId) {
        try {
            return mealDAO.getMealById(mealId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting meal by ID", e);
            return null;
        }
    }

    public List<Meal> getMealsByDay(String day) {
        try {
            return mealDAO.getMealsByDay(day);
        } catch (Exception e) {
            Log.e(TAG, "Error getting meals by day", e);
            return null;
        }
    }

    public List<Meal> getMealsByType(String mealType) {
        try {
            return mealDAO.getMealsByType(mealType);
        } catch (Exception e) {
            Log.e(TAG, "Error getting meals by type", e);
            return null;
        }
    }

    public Meal getMealByDayAndType(String day, String mealType) {
        try {
            return mealDAO.getMealByDayAndType(day, mealType);
        } catch (Exception e) {
            Log.e(TAG, "Error getting meal by day and type", e);
            return null;
        }
    }

    public List<String> getAllDays() {
        try {
            return mealDAO.getAllDays();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all days", e);
            return null;
        }
    }

    // ============= INGREDIENTS METHODS =============

    public void insertIngredient(Ingredients ingredient) {
        executor.execute(() -> {
            try {
                ingredientsDAO.insert(ingredient);
                Log.d(TAG, "Ingredient inserted: " + ingredient.getIngredientName());
            } catch (Exception e) {
                Log.e(TAG, "Error inserting ingredient", e);
            }
        });
    }

    public void updateIngredient(Ingredients ingredient) {
        executor.execute(() -> {
            try {
                ingredientsDAO.update(ingredient);
                Log.d(TAG, "Ingredient updated: " + ingredient.getIngredientName());
            } catch (Exception e) {
                Log.e(TAG, "Error updating ingredient", e);
            }
        });
    }

    public void deleteIngredient(Ingredients ingredient) {
        executor.execute(() -> {
            try {
                ingredientsDAO.delete(ingredient);
                Log.d(TAG, "Ingredient deleted: " + ingredient.getIngredientName());
            } catch (Exception e) {
                Log.e(TAG, "Error deleting ingredient", e);
            }
        });
    }

    public List<Ingredients> getAllIngredients() {
        try {
            return ingredientsDAO.getAllIngredients();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all ingredients", e);
            return null;
        }
    }

    public Ingredients getIngredientById(int ingredientId) {
        try {
            return ingredientsDAO.getIngredientById(ingredientId);
        } catch (Exception e) {
            Log.e(TAG, "Error getting ingredient by ID", e);
            return null;
        }
    }

    public Ingredients getIngredientByName(String ingredientName) {
        try {
            return ingredientsDAO.getIngredientByName(ingredientName);
        } catch (Exception e) {
            Log.e(TAG, "Error getting ingredient by name", e);
            return null;
        }
    }

    public List<Ingredients> getIngredientsByCategory(String category) {
        try {
            return ingredientsDAO.getIngredientsByCategory(category);
        } catch (Exception e) {
            Log.e(TAG, "Error getting ingredients by category", e);
            return null;
        }
    }

    public List<String> getAllCategories() {
        try {
            return ingredientsDAO.getAllCategories();
        } catch (Exception e) {
            Log.e(TAG, "Error getting all categories", e);
            return null;
        }
    }

    // ============= UTILITY METHODS =============

    public void clearAllData() {
        executor.execute(() -> {
            try {
                recipeDAO.deleteAllRecipes();
                mealDAO.deleteAllMeals();
                ingredientsDAO.deleteAllIngredients();
                Log.d(TAG, "All data cleared");
            } catch (Exception e) {
                Log.e(TAG, "Error clearing all data", e);
            }
        });
    }

    public void shutdown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}