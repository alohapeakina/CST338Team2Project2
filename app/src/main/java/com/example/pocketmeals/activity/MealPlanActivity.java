package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.Meal;
import com.example.pocketmeals.database.entities.MealRecipeName;
import com.example.pocketmeals.database.entities.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Team 2
 * Created 8/5/2025
 * Explanation: Activity for managing meal plans
 */

public class MealPlanActivity extends AppCompatActivity {

    private static final String TAG = "MEALPLANACTIVITY";
    private PocketMealsRepository repository;
    private ExecutorService executor;

    // UI Components
    private Spinner daySpinner;
    private Spinner recipeSpinner;
    private ListView mealPlanListView;
    private Button addMealButton;
    private Button viewWeeklyPlanButton;
    private TextView mealPlanTitle;

    // Data lists
    private List<String> days;
    private List<Recipe> recipes;
    private List<Meal> currentMealPlan;
    private ArrayAdapter<String> mealPlanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        // Initialize repository and executor
        repository = PocketMealsRepository.getRepository(getApplication());
        executor = Executors.newSingleThreadExecutor();

        // Initialize UI components
        initializeViews();

        // Setup data
        setupData();

        // Setup listeners
        setupListeners();

        // Load initial data
        loadMealPlan();
    }

    private void initializeViews() {
        daySpinner = findViewById(R.id.daySpinner);
        recipeSpinner = findViewById(R.id.recipeSpinner);
        mealPlanListView = findViewById(R.id.mealPlanListView);
        addMealButton = findViewById(R.id.addMealButton);
        viewWeeklyPlanButton = findViewById(R.id.viewWeeklyPlanButton);
        mealPlanTitle = findViewById(R.id.mealPlanTitle);
    }

    private void setupData() {
        // Initialize days of the week
        days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday");

        // Initialize empty lists
        recipes = new ArrayList<>();
        currentMealPlan = new ArrayList<>();

        // Setup spinners
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // Setup meal plan list view
        mealPlanAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<>());
        mealPlanListView.setAdapter(mealPlanAdapter);

        // Load recipes for spinner
        loadRecipes();
    }

    private void setupListeners() {
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMealToPlan();
            }
        });

        viewWeeklyPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMealPlan();
            }
        });

        // Long click listener for removing meals
        mealPlanListView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position < displayedMeals.size()) {
                int mealId = displayedMeals.get(position).mealId;
                executor.execute(() -> {
                    repository.deleteMealById(mealId);
                    runOnUiThread(() -> Toast.makeText(this, "Meal removed successfully!", Toast.LENGTH_SHORT).show());
                });
                return true;
            }
            return false;
        });
    }

    private void loadRecipes() {
        repository.getAllRecipes().observe(this, recipeList -> {
            Log.d(TAG, "Recipes receivedL " + recipeList.size());
            for (Recipe r : recipeList) {
                Log.d(TAG, "Recipe: " + r.getRecipeName());
            }
            if (recipeList != null) {
                recipes.clear();
                recipes.addAll(recipeList);

                List<String> recipeNames = new ArrayList<>();
                recipeNames.add("Select a recipe...");
                for (Recipe recipe : recipes) {
                    recipeNames.add(recipe.getRecipeName());
                }

                ArrayAdapter<String> recipeAdapter = new ArrayAdapter<>(
                    MealPlanActivity.this,
                    android.R.layout.simple_spinner_item,
                    recipeNames);
                recipeAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
                recipeSpinner.setAdapter(recipeAdapter);
            }
        });
    }

    private  List<MealRecipeName> displayedMeals = new ArrayList<>();
    private void loadMealPlan() {
        repository.getAllMealsWithRecipeName().observe(this, meals -> {
            displayedMeals.clear();
            if (meals != null) {
                displayedMeals.addAll(meals);
            }

            List<String> displayItems = new ArrayList<>();

            if (meals == null || meals.isEmpty()) {
                displayItems.add("No meals planned yet. Add some meals!");
            } else {
                for (MealRecipeName m : meals) {
                    String recipeName = (m.recipeName != null) ? m.recipeName : "No recipe selected";
                    displayItems.add(m.day + ": " + recipeName);
                }
            }

            mealPlanAdapter.clear();
            mealPlanAdapter.addAll(displayItems);
            mealPlanAdapter.notifyDataSetChanged();
        });
    }

    private void addMealToPlan() {
        String selectedDay = (String) daySpinner.getSelectedItem();
        int recipePosition = recipeSpinner.getSelectedItemPosition();

        // Validate selections
        if (selectedDay == null) {
            Toast.makeText(this, "Please select day", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recipePosition == 0 || recipePosition == -1) {
            Toast.makeText(this, "Please select a recipe", Toast.LENGTH_SHORT).show();
            return;
        }

        //Offset by 1 for "Select a recipe..." text
        Recipe selectedRecipe = recipes.get(recipePosition - 1);

        int selectedRecipeId = selectedRecipe.getRecipeId();
        Meal newMeal = new Meal(selectedDay,selectedRecipeId);


        // Check if meal already exists for this day and type
        executor.execute(() -> {
            try {
//                Meal newMeal = new Meal(selectedDay, selectedRecipe);
                repository.insertMeal(newMeal);
                runOnUiThread(() -> {
                    Toast.makeText(MealPlanActivity.this,
                            "Meal added successfully!", Toast.LENGTH_SHORT).show();

                    // Reset spinners
                    recipeSpinner.setSelection(0);

                    // Reload meal plan
                    loadMealPlan();
                });
            } catch (Exception e) {
                Log.e(TAG, "Error adding meal", e);
                runOnUiThread(() -> {
                    Toast.makeText(MealPlanActivity.this,
                            "Error adding meal", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void removeMealFromPlan(int position) {
        if (position >= currentMealPlan.size()) {
            return;
        }

        Meal mealToRemove = currentMealPlan.get(position);

        executor.execute(() -> {
            try {
                repository.deleteMeal(mealToRemove);

                runOnUiThread(() -> {
                    Toast.makeText(MealPlanActivity.this,
                            "Meal removed successfully!", Toast.LENGTH_SHORT).show();
                    loadMealPlan();
                });

            } catch (Exception e) {
                Log.e(TAG, "Error removing meal", e);
                runOnUiThread(() -> {
                    Toast.makeText(MealPlanActivity.this,
                            "Error removing meal", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        if (repository != null) {
            repository.shutdown();
        }
    }

    public static Intent mealPlanIntentFactory(Context packageContext) {
        return new Intent(packageContext, MealPlanActivity.class);
    }
}