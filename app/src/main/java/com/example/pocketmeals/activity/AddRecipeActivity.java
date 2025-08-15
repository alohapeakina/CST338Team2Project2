package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Recipe;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText etName, etLink, etCalories, etProtein, etFat, etCarbs;
    private PocketMealsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        repository = PocketMealsRepository.getRepository(getApplication());

        etName = findViewById(R.id.etName);
        etLink = findViewById(R.id.etLink);
        etCalories = findViewById(R.id.etCalories);
        etProtein = findViewById(R.id.etProtein);
        etFat = findViewById(R.id.etFat);
        etCarbs = findViewById(R.id.etCarbs);
        Button btnSave = findViewById(R.id.btnSaveRecipe);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String link = etLink.getText().toString().trim();
            int calories = Integer.parseInt(etCalories.getText().toString().trim());
            int protein = Integer.parseInt(etProtein.getText().toString().trim());
            int fat = Integer.parseInt(etFat.getText().toString().trim());
            int carbs = Integer.parseInt(etCarbs.getText().toString().trim());

            Recipe recipe = new Recipe(name, link, calories, protein, fat, carbs);
            repository.insertRecipe(recipe);

            Toast.makeText(this, "Recipe Added!", Toast.LENGTH_SHORT).show();
            finish(); // Return to RecipeActivity
        });
    }

    public static Intent addRecipeIntentFactory(Context packageContext) {
        return new Intent(packageContext, AddRecipeActivity.class);
    }
}
