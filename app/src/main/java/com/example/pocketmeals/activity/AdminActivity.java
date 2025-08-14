package com.example.pocketmeals.activity;
import android.os.Bundle;
import android.widget.Toast;
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


}
