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
 * @author Andrew Lee
 * Created 8/4/2025
 * Explanation: Main landing page for meal planning app
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

    binding.viewRecipesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RecipeActivity.class)));

    binding.viewShoppingListButton.setOnClickListener(view -> {
      Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
      startActivity(intent);
    });

    binding.viewWeeklyPlanButton.setOnClickListener(v -> {
      // TODO: Start weekly plan activity
      Intent intent = new Intent(MainActivity.this, MealPlanActivity.class);
      startActivity(intent);
    });

    binding.manageUsersButton.setOnClickListener(view -> {
      Intent intent = new Intent(MainActivity.this, AdminActivity.class);
      startActivity(intent);
    });
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.logoutMenuItem){
      showLogoutDialog();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void updateSharedPreference() {
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(getString(R.string.preference_userID_key), loggedInUserID);
    editor.apply();
  }

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

  private void logout() {
    loggedInUserID = LOGGED_OUT;
    updateSharedPreference();
    getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.logout_menu, menu);

    return true;
  }

  private void showLogoutDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
    final AlertDialog alertDialog = alertBuilder.create();

    alertBuilder.setMessage("Logout?");

    alertBuilder.setPositiveButton("Logout", (dialog, which) -> logout());

    alertBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss());
    alertBuilder.create().show();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserID);
    updateSharedPreference();
  }

  public static Intent mainActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
    return intent;
  }
}