package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.viewHolders.RecipeAdapter;
import com.example.pocketmeals.database.viewHolders.RecipeViewModel;

import java.util.ArrayList;

/**
 * @author Andrew Lee
 * Created 8/12/2025
 * Explanation: The RecipeActivity class displays a list of all available recipes in a
 * {@link RecyclerView}. It allows users to view, add, and delete recipes.
 * The recipe data is managed using a {@link RecipeViewModel}.
 */
public class RecipeActivity extends AppCompatActivity {
    private RecipeAdapter adapter;
    private RecipeViewModel viewModel;

    /**
     * Called when the activity is first created.
     * This method initializes the activity's layout, sets up the {@link RecyclerView}
     * with a {@link LinearLayoutManager} and a {@link RecipeAdapter}. It then
     * initializes the {@link RecipeViewModel} and observes the list of all recipes,
     * updating the adapter whenever the data changes. It also sets up click
     * listeners for the "Add Recipe" and "Delete Recipe" buttons to navigate
     * to the respective activities.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     * Note: Otherwise it is null.
     */
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

        setupAdminDeleteButton(findViewById(R.id.btn_delete_recipe), savedInstanceState);

        findViewById(R.id.btn_delete_recipe).setOnClickListener(v ->
                startActivity(DeleteRecipeActivity.deleteRecipeIntentFactory(RecipeActivity.this))
        );
    }

    /**
     * Sets up the visibility of the delete button based on whether the
     * currently logged-in user is an admin. Uses LiveData observation.
     *
     * @param deleteButton The delete recipe button to toggle visibility.
     * @param savedInstanceState Saved instance state bundle.
     */
    private void setupAdminDeleteButton(View deleteButton, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int loggedInUserId = sharedPreferences.getInt(
                getString(R.string.preference_userID_key), -1);

        if (loggedInUserId == -1 && savedInstanceState != null
                && savedInstanceState.containsKey("SAVED_INSTANCE_STATE_USERID_KEY")) {
            loggedInUserId = savedInstanceState.getInt("SAVED_INSTANCE_STATE_USERID_KEY", -1);
        }

        if (loggedInUserId == -1) {
            loggedInUserId = getIntent().getIntExtra("MAIN_ACTIVITY_USER_ID", -1);
        }

        if (loggedInUserId != -1) {
            viewModel.getUserById(loggedInUserId).observe(this, user -> {
                if (user != null && user.isAdmin()) {
                    deleteButton.setVisibility(View.VISIBLE);
                } else {
                    deleteButton.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * A factory method to create an {@link Intent} for starting the {@link RecipeActivity}.
     *
     * @param packageContext The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link RecipeActivity}.
     */
    public static Intent recipeIntentFactory(Context packageContext) {
        return new Intent(packageContext, RecipeActivity.class);
    }
}