package com.example.pocketmeals.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
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

  private ActivityAdminBinding binding;
  private AdminViewModel viewModel;
  private AdminAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityAdminBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    RecyclerView recyclerView = findViewById(R.id.manageUsersRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new AdminAdapter(new ArrayList<>());
    recyclerView.setAdapter(adapter);

    viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
    viewModel.getAllAccounts().observe(this,users -> adapter.setAccounts(users));


    binding.deleteUserButton.setOnClickListener(v -> {
      // TODO: Should send to view that provides text box to select from list of usernames to delete
      toastMaker("User is deleted");
        });

    binding.makeAdminButton.setOnClickListener(view -> {
      //TODO: Should send to view that provides text box to select from a list of usernames to promote
      toastMaker("Congrats on the promotion");
        });

    binding.removeAdminButton.setOnClickListener(view -> {
      //TODO: Should send to view that provides text box to select from a list of usernames to promote
      toastMaker("Returned to normal user");
        });
    }

    private void deleteUser(){
    //TODO: Add deletion logic
    }

    private void makeAdmin(){
    //TODO: Add logic for promoting account to admin
    }

    private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent adminIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
    }
}
