package com.example.pocketmeals.database.viewHolders;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/14/2025
 * Explanation:
 */
public class AdminViewModel extends AndroidViewModel {

  private final PocketMealsRepository repository;
  private final LiveData<List<User>> allAccounts;

  public AdminViewModel(@NonNull Application application) {
    super(application);
    repository = PocketMealsRepository.getRepository(application);
    allAccounts = repository.getAllAccounts();
  }

  public LiveData<List<User>> getAllAccounts() { return allAccounts; }

}
