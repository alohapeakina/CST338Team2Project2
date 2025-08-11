package com.example.pocketmeals;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.databinding.ActivityAdminBinding;

/**
 * @author Andrew Lee
 * created: 8/8/2025
 * Explanation: Activity for all admin actions
 */
public class AdminActivity extends AppCompatActivity {

  private ActivityAdminBinding binding;
  private PocketMealsRepository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    repository = PocketMealsRepository.getRepository(getApplication());

    binding.deleteUserButton.setOnClickListener(v -> {
      // TODO: Should send to view that provides text box to select from list of usernames to delete
    });

    binding.makeAdminButton.setOnClickListener(view -> {
      //TODO: Should send to view that provides text box to select from a list of usernames to promote
    });
   }

   private void deleteUser(){
    //TODO: Add deletion logic
   }

   private void makeAdmin(){
    //TODO: Add logic for promoting account to admin
   }


}
