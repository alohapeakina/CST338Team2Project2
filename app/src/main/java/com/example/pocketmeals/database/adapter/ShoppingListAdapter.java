package com.example.pocketmeals.database.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.database.entities.Ingredient;
import java.util.List;

/**
 * Explanation: Creates and binds views for items provided by the ShoppingListViewModel
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
  private List<Ingredient> ingredients;

  public ShoppingListAdapter(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
    return new ShoppingListAdapter.ShoppingListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
    holder.textView.setText(ingredients.get(position).toShoppingList());
  }

  @Override
  public int getItemCount() {
    return ingredients.size();
  }

  static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public ShoppingListViewHolder(View itemView) {
      super(itemView);
      textView = itemView.findViewById(android.R.id.text1);
    }
  }
}
