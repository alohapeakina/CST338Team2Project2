package com.example.pocketmeals.database.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Recipe;
import com.example.pocketmeals.database.entities.User;

import java.util.List;

/**
 * @author Team 2
 * created: 8/14/2025
 * Explanation: This class isolates the UI from the data layer, ensuring data is retained
 * during configuration changes (e.g., screen rotations). It manages `LiveData`
 * for a list of all recipes and provides methods to insert, delete, and query recipes
 * without direct interaction with the database.
 */
public class RecipeViewModel extends AndroidViewModel {
    private final PocketMealsRepository repository;
    private final LiveData<List<Recipe>> allRecipes;

    /**
     * Constructs a new `RecipeViewModel`.
     * This constructor initializes the `PocketMealsRepository` and retrieves
     * a `LiveData` list of all recipes, which is then made available to the UI.
     *
     * @param application The application context.
     */
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = PocketMealsRepository.getRepository(application);
        assert repository != null;
        allRecipes = repository.getAllRecipes();
    }

    /**
     * Retrieves a `LiveData` object containing a list of all recipes.
     * The UI can observe this `LiveData` to automatically receive updates whenever
     * the list of recipes changes in the database.
     *
     * @return A `LiveData` list of all {@link Recipe} objects.
     */
    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    /**
     * Inserts a new recipe into the database via the repository.
     *
     * @param recipe The {@link Recipe} object to insert.
     */
    public void insert(Recipe recipe) {
        repository.insertRecipe(recipe);
    }

    /**
     * Deletes a recipe from the database via the repository.
     *
     * @param recipe The {@link Recipe} object to delete.
     */
    public void delete(Recipe recipe) {
        repository.deleteRecipe(recipe);
    }

    /**
     * Retrieves a `LiveData` object for a user based on their unique ID.
     *
     * @param userId The unique ID of the user.
     * @return A `LiveData` object containing the {@link User} with the specified ID.
     */
    public LiveData<User> getUserById(int userId) {
        return repository.getUserByUserId(userId);
    }
}
