package com.example.pocketmeals.database.viewHolders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketmeals.R;
import com.example.pocketmeals.database.entities.Recipe;

import java.util.List;

/**
 * @author Team 2
 * created: 8/14/2025
 * Explanation: An adapter for displaying a list of recipes in a {@link RecyclerView}.
 * This adapter binds {@link Recipe} data to the views defined in `item_recipe.xml`.
 * It manages the creation of `RecipeViewHolder` instances and updates the contents
 * of the views with the corresponding recipe details (title, description, and nutrition).
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;

    /**
     * Constructs a new `RecipeAdapter`.
     *
     * @param recipes The initial list of {@link Recipe} objects to display.
     */
    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Updates the list of recipes and notifies the `RecyclerView` that the data set has changed.
     *
     * @param recipes The new list of {@link Recipe} objects.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    /**
     * Called when `RecyclerView` needs a new `RecipeViewHolder` to represent an item.
     * This method inflates the layout for a single recipe item and returns a new `ViewHolder`
     * that holds the views.
     *
     * @param parent   The `ViewGroup` into which the new `View` will be added.
     * @param viewType The view type of the new `View`.
     * @return A new `RecipeViewHolder` that holds the views for a recipe item.
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    /**
     * Called by `RecyclerView` to display the data at the specified position.
     * This method updates the contents of the `RecipeViewHolder` with the data from the
     * `Recipe` object at the given position.
     *
     * @param holder   The `RecipeViewHolder` to be updated.
     * @param position The position of the item in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvTitle.setText(recipe.getRecipeName());
        holder.tvDescription.setText(recipe.getRecipeLink());
        holder.tvNutrition.setText(recipe.nutritionToString());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of recipes, or 0 if the list is null.
     */
    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    /**
     * A `ViewHolder` class for the recipe items.
     * This class holds and manages the views for a single recipe list item.
     */
    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvNutrition;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRecipeTitle);
            tvDescription = itemView.findViewById(R.id.tvRecipeDescription);
            tvNutrition = itemView.findViewById(R.id.tvRecipeNutrition);
        }
    }
}
