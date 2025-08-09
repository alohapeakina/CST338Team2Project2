package com.example.pocketmeals;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

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
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = PocketMealsRepository.getRepository(getApplication());
    loginUser(savedInstanceState);

    if(loggedInUserID == -1){
      Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    }

    updateSharedPreference();

    binding.viewRecipesButton.setOnClickListener(v -> {
      // TODO: Start recipes activity
    });

    binding.viewShoppingListButton.setOnClickListener(v -> {
      // TODO: Start shopping list activity
    });

    binding.viewWeeklyPlanButton.setOnClickListener(v -> {
      // TODO: Start weekly plan activity
    });
  }

  private void updateSharedPreference() {
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(getString(R.string.preference_userID_key), loggedInUserID);
    editor.apply();
  }

  private void loginUser(Bundle savedInstanceState) {
    SharedPreferences sharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userID_key), LOGGED_OUT);

    if (loggedInUserID == LOGGED_OUT && savedInstanceState != null &&
            savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
      loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
    }

    if (loggedInUserID == LOGGED_OUT) {
      loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
    }

    if (loggedInUserID == LOGGED_OUT) return;

    LiveData<User> userObserver = repository.getUserByUserId(loggedInUserID);
    userObserver.observe(this, u -> {
      this.user = u;
      if (this.user != null) {
        if(this.user.isAdmin()){
        binding.welcomeMessageTextView.setText("[Admin]\nWelcome " + user.getUsername());
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

  private void showMenuDialog() {
    String[] options = {"Logout", "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(user != null ? user.getUsername() : "Menu");
    builder.setItems(options, (dialog, which) -> {
      if (which == 0) {
        logout();
      }
    });
    builder.show();
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