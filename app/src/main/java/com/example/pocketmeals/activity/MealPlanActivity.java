package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Explanation: This activity allows users to create and manage a weekly meal plan. Users can
 * select a day and a recipe to add to their plan. It displays the current weekly
 * meal plan and a summary of the total nutritional information for the planned meals.
 * Users can also remove meals from the plan by long-pressing on an item in the list.
 */

public class MealPlanActivity extends AppCompatActivity {

    private static final String TAG = "MEALPLANACTIVITY";
    private PocketMealsRepository repository;
    private int loggedInUserID;
    private ExecutorService executor;

    // UI Components
    private TextView caloriesTextView, proteinTextView, fatTextView, carbsTextView;
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
    private  List<MealRecipeName> displayedMeals = new ArrayList<>();


    /**
     * Called when the activity is first created.
     * This method initializes the activity, retrieves the logged-in user's ID from
     * shared preferences, and sets up the UI components. It initializes the database
     * repository and an executor for background operations. It also observes the
     * nutritional totals from the database to update the UI in real-time.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState(Bundle)}. Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);
        caloriesTextView = findViewById(R.id.caloriesTextView);
        proteinTextView = findViewById(R.id.proteinTextView);
        fatTextView = findViewById(R.id.fatTextView);
        carbsTextView = findViewById(R.id.carbsTextView);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userID_key), -1);
        if (loggedInUserID == -1) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize repository and executor
        repository = PocketMealsRepository.getRepository(getApplication());

        //Updates nutritional information in grid
        repository.getNutritionTotalsForUser(loggedInUserID).observe(this, totals -> {
            if (totals != null) {
                caloriesTextView.setText("Calories: " + totals.totalCalories);
                proteinTextView.setText("Protein: " + totals.totalProtein + "g");
                fatTextView.setText("Fat: " + totals.totalFat + "g");
                carbsTextView.setText("Carbs: " + totals.totalCarbs + "g");
            } else {
                caloriesTextView.setText("Calories:0g");
                proteinTextView.setText("Protein: 0g");
                fatTextView.setText("Fat: 0g");
                carbsTextView.setText("Carbs: 0g");
            }
        });


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

    /**
     * Initializes the UI components by finding them in the layout and assigning them to member variables.
     */
    private void initializeViews() {
        daySpinner = findViewById(R.id.daySpinner);
        recipeSpinner = findViewById(R.id.recipeSpinner);
        mealPlanListView = findViewById(R.id.mealPlanListView);
        addMealButton = findViewById(R.id.addMealButton);
        viewWeeklyPlanButton = findViewById(R.id.viewWeeklyPlanButton);
        mealPlanTitle = findViewById(R.id.mealPlanTitle);
    }

    /**
     * Sets up the data for the spinners and the list view.
     * Initializes the list of days of the week and prepares the adapters for the spinners and list view.
     */
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

    /**
     * Sets up the click and long-click listeners for the UI components.
     * This includes listeners for the add meal button, view weekly plan button,
     * and the long-press listener for the meal plan list view to enable deletion.
     */
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

    /**
     * Loads all recipes from the database and populates the recipe spinner.
     * It observes changes in the recipe data and updates the spinner accordingly.
     */
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

    /**
     * Loads the user's meal plan from the database and updates the list view.
     * It observes the list of meals and their associated recipe names and displays them in the list view.
     */
    private void loadMealPlan() {
        repository.getAllMealsWithRecipeNameForUser(loggedInUserID).observe(this, meals -> {
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

    /**
     * Adds a new meal to the user's meal plan.
     * It retrieves the selected day and recipe from the spinners, validates the input,
     * and inserts the new meal into the database on a background thread. Upon
     * successful insertion, it shows a toast message and reloads the meal plan list.
     */
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
        Meal newMeal = new Meal(selectedDay,selectedRecipeId, loggedInUserID);


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

    /**
     * A factory method to create an {@link Intent} for starting the {@link MealPlanActivity}.
     *
     * @param packageContext The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link MealPlanActivity}.
     */
    public static Intent mealPlanIntentFactory(Context packageContext) {
        return new Intent(packageContext, MealPlanActivity.class);
    }
}