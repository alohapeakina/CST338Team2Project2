package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.entities.Ingredient;
import com.example.pocketmeals.database.viewHolders.RecipeAdapter;
import com.example.pocketmeals.database.viewHolders.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private RecipeAdapter adapter;
    private RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        RecyclerView recyclerView = findViewById(R.id.rvRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecipeAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        viewModel.getAllRecipes().observe(this, recipes -> adapter.setRecipes(recipes));

        findViewById(R.id.btnAddRecipe).setOnClickListener(v -> startActivity(AddRecipeActivity.addRecipeIntentFactory(RecipeActivity.this)));

        findViewById(R.id.btn_delete_recipe).setOnClickListener(v -> startActivity(DeleteRecipeActivity.deleteRecipeIntentFactory(RecipeActivity.this)));
    }

    public static Intent recipeIntentFactory(Context packageContext) {
        return new Intent(packageContext, RecipeActivity.class);
    }
}