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

/**
 * @author Team 2
 * Created 8/12/2025
 * Explanation: The AddRecipeActivity class is used for adding a new recipe to the database.
 * It provides a user interface with input fields for recipe details such as name, link,
 * calories, protein, fat, and carbohydrates. Upon saving, it inserts a new {@link Recipe}
 * into the database.
 */
public class AddRecipeActivity extends AppCompatActivity {

    private EditText etName, etLink, etCalories, etProtein, etFat, etCarbs;
    private PocketMealsRepository repository;

    /**
     * Called when the activity is first created.
     * This method initializes the activity's layout, finds and assigns the UI components,
     * and initializes the database repository. It sets up a click listener for the
     * "Save" button to collect the user input, create a new {@link Recipe} object,
     * insert it into the database, display a success toast message, and finish the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     * Note: Otherwise it is null.
     */
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
            double calories = Double.parseDouble(etCalories.getText().toString().trim());
            double protein = Double.parseDouble(etProtein.getText().toString().trim());
            double fat = Double.parseDouble(etFat.getText().toString().trim());
            double carbs = Double.parseDouble(etCarbs.getText().toString().trim());

            Recipe recipe = new Recipe(name, link, calories, protein, fat, carbs);
            repository.insertRecipe(recipe);

            Toast.makeText(this, "Recipe Added!", Toast.LENGTH_SHORT).show();
            finish(); // Return to RecipeActivity
        });
    }

    /**
     * A factory method to create an {@link Intent} for starting the {@link AddRecipeActivity}.
     *
     * @param packageContext The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link AddRecipeActivity}.
     */
    public static Intent addRecipeIntentFactory(Context packageContext) {
        return new Intent(packageContext, AddRecipeActivity.class);
    }
}
