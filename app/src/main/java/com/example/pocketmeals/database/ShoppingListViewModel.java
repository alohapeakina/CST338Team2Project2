package com.example.pocketmeals.database;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.pocketmeals.database.entities.Ingredient;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/12/2025
 * Explanation: View Model for Shopping List Activity
 */
public class ShoppingListViewModel extends AndroidViewModel {
  private PocketMealsRepository repository;
  private LiveData<List<Ingredient>> allIngredients;

  public ShoppingListViewModel(@NonNull Application application) {
    super(application);
    repository = PocketMealsRepository.getRepository(application);
    allIngredients = repository.getAllIngredients();
  }

  public LiveData<List<Ingredient>> getAllIngredients() {
    return allIngredients;
  }

}
