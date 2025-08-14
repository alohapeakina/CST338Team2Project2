package com.example.pocketmeals.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Recipe;

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
}
