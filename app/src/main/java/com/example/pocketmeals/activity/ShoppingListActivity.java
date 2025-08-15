package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.viewHolders.ShoppingListAdapter;
import com.example.pocketmeals.database.viewHolders.ShoppingListViewModel;
import java.util.ArrayList;

/**
 * @author Andrew Lee
 * created: 8/12/2025
 * Explanation: Provides a combined list of ingredients from recipes in current meal plan
 * This activity displays a shopping list of ingredients. The list is generated
 * by combining all ingredients from the recipes that are currently part of the
 * user's meal plan. It uses a {@link RecyclerView} to display the list and a
 * {@link ShoppingListViewModel} to manage and observe the ingredient data.
 */
public class ShoppingListActivity extends AppCompatActivity {
  private ShoppingListAdapter adapter;
  private ShoppingListViewModel viewModel;

  /**
   * Called when the activity is first created.
   * This method initializes the activity's layout, sets up the {@link RecyclerView}
   * with a {@link LinearLayoutManager} and a {@link ShoppingListAdapter}. It then
   * initializes the {@link ShoppingListViewModel} and observes the LiveData for
   * all ingredients, updating the adapter whenever the data changes.
   *
   * @param savedInstanceState If the activity is being re-initialized after
   * previously being shut down then this Bundle contains the data it most
   * recently supplied in {@link #onSaveInstanceState(Bundle)}. Note: Otherwise it is null.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_list);

    RecyclerView recyclerView = findViewById(R.id.shoppingListRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    adapter = new ShoppingListAdapter(new ArrayList<>());
    recyclerView.setAdapter(adapter);

    viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
    viewModel.getAllIngredients().observe(this, ingredients -> adapter.setIngredients(ingredients));

  }

  /**
   * A factory method to create an {@link Intent} for starting the {@link ShoppingListActivity}.
   * This method provides a standardized way to create an intent to navigate to this activity.
   *
   * @param context The context of the calling activity.
   * @return A new {@link Intent} configured to start the {@link ShoppingListActivity}.
   */
  public static Intent shoppingListIntentFactory(Context context) {
    return new Intent(context, ShoppingListActivity.class);
  }
}
