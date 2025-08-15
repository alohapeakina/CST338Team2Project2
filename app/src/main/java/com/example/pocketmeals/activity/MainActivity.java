package com.example.pocketmeals.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.databinding.ActivityMainBinding;

/**
 * @author Team 2
 * Created 8/4/2025
 * Explanation: This activity serves as the primary entry point for logged-in users.
 * It displays a welcome message,provides navigation to other parts of
 * the application (recipes, weekly plan), and includes
 * a logout option. If a user is not logged in, it redirects them to the {@link LoginActivity}.
 * Admin users are also provided with a button to manage other users.
 */
public class MainActivity extends AppCompatActivity {
  private static final String MAIN_ACTIVITY_USER_ID = "com.example.pocketmeals.MAIN_ACTIVITY_USER_ID";
  private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.pocketmeals.SAVED_INSTANCE_STATE_USERID_KEY";
  private static final int LOGGED_OUT = -1;
  private int loggedInUserID = LOGGED_OUT;
  private User user;
  private ActivityMainBinding binding;
  private PocketMealsRepository repository;
  public static final String TAG = "POCKETMEALS";

  /**
   * Called when the activity is first created.
   * This method initializes the activity, sets up the layout using view binding,
   * configures the toolbar, and initializes the repository. It then attempts to log
   * in a user based on saved state or intent data. If no user is found, it redirects
   * to the login activity. It also sets up click listeners for the main navigation buttons.
   *
   * @param savedInstanceState If the activity is being re-initialized after previously being shut down
   * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
   * Note: Otherwise it is null.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = com.example.pocketmeals.databinding.ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);

    repository = PocketMealsRepository.getRepository(getApplication());
    loginUser(savedInstanceState);

    if (loggedInUserID == LOGGED_OUT) {
      Log.i(TAG, "No user logged in — showing login/signup options");
      startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
      finish();
    }

    if (user != null) {
      String welcomeMessage = "Welcome " + user.getUsername();
      binding.welcomeMessageTextView.setText(welcomeMessage);
    }

    updateSharedPreference();

    binding.viewRecipesButton.setOnClickListener(v -> startActivity(RecipeActivity.recipeIntentFactory(MainActivity.this)));

    binding.addRecipeButton.setOnClickListener(view -> startActivity(AddRecipeActivity.addRecipeIntentFactory(MainActivity.this)));

    binding.viewWeeklyPlanButton.setOnClickListener(v -> startActivity(MealPlanActivity.mealPlanIntentFactory(MainActivity.this)));

    binding.manageUsersButton.setOnClickListener(view -> startActivity(AdminActivity.adminIntentFactory(MainActivity.this)));
  }

  /**
   * This hook is called whenever an item in your options menu is selected.
   * This method handles the selection of the logout menu item by calling {@link #showLogoutDialog()}.
   *
   * @param item The menu item that was selected.
   * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
   */
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.logoutMenuItem){
      showLogoutDialog();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * Updates the user ID stored in shared preferences.
   * This method saves the {@link #loggedInUserID} to a shared preference file,
   * which is used to persist the logged-in state across app sessions.
   */
  private void updateSharedPreference() {
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(getString(R.string.preference_userID_key), loggedInUserID);
    editor.apply();
  }

  /**
   * Logs in a user based on saved state, shared preferences, or intent data.
   * This method first checks for a user ID in shared preferences. If not found,
   * it checks the saved instance state from a previous session. Finally, it
   * checks the intent that started the activity. If a user ID is found, it
   * fetches the corresponding {@link User} object from the database and updates the UI,
   * including the welcome message and, for admin users, the visibility of the manage users button.
   *
   * @param savedInstanceState The bundle containing the activity's previously saved state.
   */
  @SuppressLint("SetTextI18n")
  private void loginUser(Bundle savedInstanceState) {
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
    loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userID_key), LOGGED_OUT);

    if (loggedInUserID == LOGGED_OUT & savedInstanceState != null && savedInstanceState
            .containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
      loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
    }
    if (loggedInUserID == LOGGED_OUT) {
      loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
    }
    if (loggedInUserID == LOGGED_OUT) {
      return;
    }
    LiveData<User> userObserver = repository.getUserByUserId(loggedInUserID);
    userObserver.observe(this, user -> {
      this.user = user;
      if (this.user != null) {
        invalidateOptionsMenu();
        if (this.user.isAdmin()) {
          binding.welcomeMessageTextView.setText("[Admin]\nWelcome " + user.getUsername());
          binding.manageUsersButton.setVisibility(View.VISIBLE);
        } else {
          binding.welcomeMessageTextView.setText("Welcome " + user.getUsername());
        }
      }
    });
  }

  /**
   * Handles the logout process.
   * This method resets the logged-in user ID, updates shared preferences,
   * and navigates the user back to the {@link LoginActivity}. The current activity is then finished.
   */
  private void logout() {
    loggedInUserID = LOGGED_OUT;
    updateSharedPreference();
    getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    finish();
  }

  /**
   * Initialize the contents of the Activity's standard options menu.
   * This method inflates the menu layout resource for the toolbar, which includes the logout option.
   *
   * @param menu The options menu in which you place your items.
   * @return You must return true for the menu to be displayed; if you return false it will not be shown.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.logout_menu, menu);

    return true;
  }

  /**
   * Displays a confirmation dialog for logging out.
   * <p>
   * This method creates and shows an {@link AlertDialog} asking the user to confirm if they want to log out.
   * If the user confirms, the {@link #logout()} method is called.
   */
  private void showLogoutDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
    final AlertDialog alertDialog = alertBuilder.create();

    alertBuilder.setMessage("Logout?");

    alertBuilder.setPositiveButton("Logout", (dialog, which) -> logout());

    alertBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss());
    alertBuilder.create().show();
  }

  /**
   * Called to retrieve per-instance state from an activity before being killed.
   * This method saves the {@link #loggedInUserID} into the provided bundle,
   * which can be used to restore the user's state if the activity is recreated.
   *
   * @param outState Bundle in which to place your saved state.
   */
  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserID);
    updateSharedPreference();
  }

  /**
   * A factory method to create an {@link Intent} for starting the {@link MainActivity}.
   * This method packages the user ID into the intent, which the {@link MainActivity}
   * can then use to log in the correct user upon creation.
   *
   * @param context The context of the calling activity.
   * @param userId The ID of the user to be logged in.
   * @return A new {@link Intent} configured to start the {@link MainActivity}.
   */
  public static Intent mainActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
    return intent;
  }
}