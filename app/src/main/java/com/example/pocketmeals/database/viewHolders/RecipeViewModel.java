package com.example.pocketmeals.database.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.Observer;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;

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

    public LiveData<User> getUserById(int userId) {
        return repository.getUserByUserId(userId);
    }
}
