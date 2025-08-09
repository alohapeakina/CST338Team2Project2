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
    binding = com.example.pocketmeals.databinding.ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);

    repository = PocketMealsRepository.getRepository(getApplication());
    loginUser(savedInstanceState);

    // If no one is logged in, give choice to log in or sign up
    if (loggedInUserID == LOGGED_OUT) {
      Log.i(TAG, "No user logged in — showing login/signup options");
      showLoginSignupDialog();
    }

    updatedSharedPreference();
  }
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.logoutMenuItem){
      showLogoutDialog();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void loginUser(Bundle savedInstanceState) {
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
            Context.MODE_PRIVATE);
    loggedInUserID = sharedPreferences.getInt(getString(R.string.preference_userID_key),LOGGED_OUT);

    if (loggedInUserID == LOGGED_OUT & savedInstanceState != null && savedInstanceState
            .containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
      loggedInUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
    }
    if(loggedInUserID==LOGGED_OUT){
      loggedInUserID=getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID,LOGGED_OUT);
    }
    if (loggedInUserID==LOGGED_OUT){
      return;
    }
    LiveData<User> userObserver = repository.getUserByUserId(loggedInUserID);
    userObserver.observe(this, user-> {
      this.user=user;
      if(this.user!=null){
        invalidateOptionsMenu();
      }
    });
  }

  private void showLoginSignupDialog() {
    new AlertDialog.Builder(this)
            .setTitle("Welcome to PocketMeals")
            .setMessage("Please log in or create a new account.")
            .setPositiveButton("Login", (dialog, which) -> {
              startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            })
            .setNegativeButton("Sign Up", (dialog, which) -> {
              startActivity(SignupActivity.signupIntentFactory(getApplicationContext()));
            })
            .setCancelable(false)
            .show();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY,loggedInUserID);
    updatedSharedPreference();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.logout_menu, menu);

    SharedPreferences prefs = getSharedPreferences("PocketMealsPrefs", MODE_PRIVATE);
    String username = prefs.getString("username", "Unknown");
    menu.findItem(R.id.logoutMenuItem).setTitle("Logout (" + username + ")");

    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // Optional: Show username next to logout
    MenuItem logoutItem = menu.findItem(R.id.logoutMenuItem);
    if (user != null) {
      logoutItem.setTitle("Logout (" + user.getUsername() + ")");
    } else {
      logoutItem.setTitle("Logout");
    }
    logoutItem.setVisible(true); // Always show it
    return super.onPrepareOptionsMenu(menu);
  }

  private void showLogoutDialog(){
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
    final AlertDialog alertDialog = alertBuilder.create();

    alertBuilder.setMessage("Logout?");

    alertBuilder.setPositiveButton("Logout", (dialog, which) -> logout());

    alertBuilder.setNegativeButton("Cancel", (dialog, which) -> alertDialog.dismiss());
    alertBuilder.create().show();
  }

  private void logout() {
    SharedPreferences prefs = getSharedPreferences("PocketMealsPrefs", MODE_PRIVATE);
    prefs.edit().putBoolean("isLoggedIn", false).apply();
    prefs.edit().remove("loggedInUserId").apply();

    Intent intent = new Intent(this, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);

    finish();
  }

  private void updatedSharedPreference() {
    SharedPreferences sharedPreferences = getApplicationContext()
            .getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
    sharedPrefEditor.putInt(getString(R.string.preference_userID_key),loggedInUserID);
    sharedPrefEditor.apply();
  }

  static Intent mainActivityIntentFactory(Context context, int userID) {
    Intent intent = new Intent(context,MainActivity.class);
    intent.putExtra(MAIN_ACTIVITY_USER_ID,userID);
    return intent;
  }
}