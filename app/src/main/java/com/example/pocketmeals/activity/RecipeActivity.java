package com.example.pocketmeals.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.RecipeAdapter;
import com.example.pocketmeals.database.RecipeViewModel;

import java.util.ArrayList;

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

        Button btnAddRecipe = findViewById(R.id.btnAddRecipe);
        btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });
    }
}

