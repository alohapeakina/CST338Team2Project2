package com.example.pocketmeals.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.entities.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private PocketMealsRepository repository;
    private LiveData<List<Recipe>> allRecipes;

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
}
