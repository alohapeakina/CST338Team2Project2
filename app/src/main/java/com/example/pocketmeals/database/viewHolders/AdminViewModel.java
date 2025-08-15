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
 * Explanation: This `ViewModel` provides access to user data for the admin view. It acts as a
 * communication bridge between the UI ({@link com.example.pocketmeals.activity.AdminActivity})
 * and the data layer ({@link PocketMealsRepository}). By using an `AndroidViewModel`,
 * it has access to the application context and can survive configuration changes,
 * ensuring that the data is not re-queried on every screen rotation. It exposes a
 * `LiveData` list of all user accounts.
 */
public class AdminViewModel extends AndroidViewModel {

  private final PocketMealsRepository repository;
  private final LiveData<List<User>> allAccounts;

  /**
   * Constructs an `AdminViewModel`.
   * <p>
   * This constructor initializes the {@link PocketMealsRepository} and retrieves
   * a `LiveData` list of all user accounts from the repository, which is then
   * made available to the UI.
   *
   * @param application The application context.
   */
  public AdminViewModel(@NonNull Application application) {
    super(application);
    repository = PocketMealsRepository.getRepository(application);
    allAccounts = repository.getAllAccounts();
  }

  /**
   * Returns a `LiveData` object containing a list of all user accounts.
   * <p>
   * The UI can observe this `LiveData` to automatically receive updates whenever
   * the list of users changes in the database.
   *
   * @return A `LiveData` list of all {@link User} objects.
   */
  public LiveData<List<User>> getAllAccounts() { return allAccounts; }

}
