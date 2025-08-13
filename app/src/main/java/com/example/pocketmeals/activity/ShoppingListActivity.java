package com.example.pocketmeals.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.ShoppingListAdapter;
import com.example.pocketmeals.database.ShoppingListViewModel;
import java.util.ArrayList;

/**
 * @author Andrew Lee
 * created: 8/12/2025
 * Explanation: Provides a combined list of ingredients from recipes in current meal plan
 */
public class ShoppingListActivity extends AppCompatActivity {

  private ShoppingListAdapter adapter;
  private ShoppingListViewModel viewModel;

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

}
