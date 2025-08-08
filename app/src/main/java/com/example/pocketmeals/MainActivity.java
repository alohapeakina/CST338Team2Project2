package com.example.pocketmeals;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
  private ActivityMainBinding binding;
  private PocketMealsRepository repository;
  public static final String TAG = "POCKETMEALS";
  private int loggedInUserID = -1;
  private User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = PocketMealsRepository.getRepository(getApplication());
    //TODO: Clean up test use cases
    User testUser = new User("testuser1","testpassword"); //Test case for inserting into DB which ensures DB is created
    repository.insertUser(testUser);

    TextView welcomeMessageTextView = findViewById(R.id.welcomeMessageTextView);
    String userName = "[User]"; //TODO: Update with logic to find username once database is ready
    String welcomeMessage = "Welcome " + userName;
    welcomeMessageTextView.setText(welcomeMessage);
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
    LiveData<User> userObserver = repository.getUserByUserID(loggedInUserID);
    userObserver.observe(this, user-> {
      this.user=user;
      if(this.user!=null){
        invalidateOptionsMenu();
      }
    });
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
    inflater.inflate(R.menu.logout_menu,menu);
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem item = menu.findItem(R.id.logoutMenuItem);
    item.setVisible(true);
    if(user==null){
      return false;
    }
    item.setTitle(user.getUsername());
    item.setOnMenuItemClickListener(item1 -> {
      showLogoutDialog();
      return false;
    });
    return true;
  }

  private void showLogoutDialog(){
    new AlertDialog.Builder(this)
            .setMessage("Logout?")
            .setPositiveButton("Logout", (dialog, which) -> logout())
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .create()
            .show();
  }

  private void logout() {
    loggedInUserID = LOGGED_OUT;
    updatedSharedPreference();

    getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);

    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
  }

  private void updatedSharedPreference() {
    SharedPreferences sharedPreferences = getApplicationContext()
            .getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
    sharedPrefEditor.putInt(getString(R.string.preference_userID_key),loggedInUserID);
    sharedPrefEditor.apply();
  }

  static Intent mainActivityIntentFactory(Context context, int userID) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(MAIN_ACTIVITY_USER_ID, userID);
    return intent;
  }
}