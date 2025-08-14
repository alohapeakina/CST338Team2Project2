package com.example.pocketmeals.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.RecipeViewModel;
import com.example.pocketmeals.database.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class DeleteRecipeActivity extends AppCompatActivity {
    private RecipeViewModel recipeViewModel;

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
}
