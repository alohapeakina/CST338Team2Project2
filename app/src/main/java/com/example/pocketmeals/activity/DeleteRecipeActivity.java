package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.viewHolders.RecipeViewModel;
import com.example.pocketmeals.database.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Team 2
 * Created 8/12/2025
 * Explanation: The DeleteRecipeActivity allows users to delete recipes from the database.
 * It displays a list of all available recipes and, upon a user clicking on an item,
 * the corresponding recipe is removed from the database.
 */
public class DeleteRecipeActivity extends AppCompatActivity {
    private RecipeViewModel recipeViewModel;

    /**
     * Called when the activity is first created.
     * This method initializes the activity's layout, sets up the {@link ListView}
     * to display a list of all recipes. It uses a {@link RecipeViewModel} to
     * observe the list of recipes from the database. When a recipe is clicked,
     * it is deleted from the database and a toast message is displayed.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     * Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recipe);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        ListView recipeListView = findViewById(R.id.recipe_list_view);

        recipeViewModel.getAllRecipes().observe(this, recipes -> {
            List<String> recipeNames = new ArrayList<>();
            for (Recipe recipe : recipes) {
                recipeNames.add(recipe.getRecipeName()); // or whatever your getter is
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    recipeNames
            );
            recipeListView.setAdapter(adapter);

            recipeListView.setOnItemClickListener((parent, view, position, id) -> {
                Recipe recipeToDelete = recipes.get(position);
                recipeViewModel.delete(recipeToDelete);
                Toast.makeText(this, "Deleted: " + recipeToDelete.getRecipeName(), Toast.LENGTH_SHORT).show();
            });
        });
    }

    /**
     * A factory method to create an {@link Intent} for starting the {@link DeleteRecipeActivity}.
     *
     * @param packageContext The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link DeleteRecipeActivity}.
     */
    public static Intent deleteRecipeIntentFactory(Context packageContext) {
        return new Intent(packageContext, DeleteRecipeActivity.class);
    }
}
