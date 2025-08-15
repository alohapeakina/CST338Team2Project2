package com.example.pocketmeals.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.database.viewHolders.AdminAdapter;
import com.example.pocketmeals.database.viewHolders.AdminViewModel;
import com.example.pocketmeals.databinding.ActivityAdminBinding;
import java.util.ArrayList;

/**
 * @author Andrew Lee
 * created: 8/8/2025
 * Explanation: Activity for all admin actions
 */
public class AdminActivity extends AppCompatActivity {

    private AdminAdapter adapter;
  private PocketMealsRepository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

      com.example.pocketmeals.databinding.ActivityAdminBinding binding = ActivityAdminBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = PocketMealsRepository.getRepository(getApplication());

    adapter = new AdminAdapter(new ArrayList<>(), user -> toastMaker("Selected: " + user.getUsername()));

    binding.manageUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    binding.manageUsersRecyclerView.setAdapter(adapter);

      AdminViewModel viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
    viewModel.getAllAccounts().observe(this, users -> adapter.setAccounts(users));

    binding.deleteUserButton.setOnClickListener(v -> deleteUser());
    binding.makeAdminButton.setOnClickListener(v -> makeAdmin());
    binding.removeAdminButton.setOnClickListener(v -> removeAdmin());
  }

  private void deleteUser() {
    User user = adapter.getSelectedUser();
    if (user == null) {
      toastMaker("Select a user first");
      return;
    }
    repository.deleteUser(user);
    toastMaker("Deleted user: " + user.getUsername());
  }

  private void makeAdmin() {
    User user = adapter.getSelectedUser();
    if (user == null) {
      toastMaker("Select a user first");
      return;
    }
    user.setAdmin(true);
    repository.updateUser(user);
    toastMaker(user.getUsername() + " is now an admin");
  }

  private void removeAdmin() {
    User user = adapter.getSelectedUser();
    if (user == null) {
      toastMaker("Select a user first");
      return;
    }
    user.setAdmin(false);
    repository.updateUser(user);
    toastMaker(user.getUsername() + " is now a regular user");
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  public static Intent adminIntentFactory(Context context) {
    return new Intent(context, AdminActivity.class);
  }
}