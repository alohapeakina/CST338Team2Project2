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
 * @author Team 2
 * Created 8/12/2025
 * Explanation: This activity provides administrative functionalities, allowing an admin user
 * to manage other user accounts. It displays a list of all users and provides
 * options to delete a user, and grant or revoke admin privileges.
 */
public class AdminActivity extends AppCompatActivity {
  private AdminAdapter adapter;
  private PocketMealsRepository repository;

  /**
   * Called when the activity is first created.
   * This method initializes the activity's layout using view binding, sets up the
   * {@link PocketMealsRepository}, and configures the {@link androidx.recyclerview.widget.RecyclerView}
   * with a {@link LinearLayoutManager} and an {@link AdminAdapter}. It uses an
   * {@link AdminViewModel} to observe all user accounts and updates the adapter accordingly.
   * It also sets click listeners for the delete, make admin, and remove admin buttons.
   *
   * @param savedInstanceState If the activity is being re-initialized after previously being shut down
   * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
   * Note: Otherwise it is null.
   */
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

  /**
   * Deletes the currently selected user.
   * This method retrieves the user selected in the {@link AdminAdapter}. If no user
   * is selected, it shows a toast message. Otherwise, it calls the repository to
   * delete the user from the database and displays a confirmation toast.
   */
  private void deleteUser() {
    User user = adapter.getSelectedUser();
    if (user == null) {
      toastMaker("Select a user first");
      return;
    }
    repository.deleteUser(user);
    toastMaker("Deleted user: " + user.getUsername());
  }

  /**
   * Grants admin privileges to the currently selected user.
   * This method retrieves the selected user, sets their admin status to `true`,
   * updates the user in the database via the repository, and shows a confirmation toast.
   * A toast message is shown if no user is selected.
   */
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

  /**
   * Revokes admin privileges from the currently selected user.
   * This method retrieves the selected user, sets their admin status to `false`,
   * updates the user in the database, and shows a confirmation toast.
   * A toast message is shown if no user is selected.
   */
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

  /**
   * Displays a short toast message to the user.
   *
   * @param message The message to be displayed in the toast.
   */
  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  /**
   * A factory method to create an {@link Intent} for starting the {@link AdminActivity}.
   *
   * @param context The context of the calling activity.
   * @return A new {@link Intent} configured to start the {@link AdminActivity}.
   */
  public static Intent adminIntentFactory(Context context) {
    return new Intent(context, AdminActivity.class);
  }
}