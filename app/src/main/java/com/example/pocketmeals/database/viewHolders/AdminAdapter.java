package com.example.pocketmeals.database.viewHolders;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.entities.User;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/14/2025
 * Explanation: `RecyclerView.Adapter` for displaying a list of user accounts in the admin view.
 * This adapter is responsible for binding a list of {@link User} objects to the
 * `RecyclerView` in the {@link com.example.pocketmeals.activity.AdminActivity}.
 * It manages the creation of view holders and binds data to them. It also keeps track
 * of the currently selected user and provides a callback interface for handling item clicks.
 */
public class AdminAdapter extends RecyclerView.Adapter<AdminViewHolder> {
  private List<User> accounts;
  private User selectedUser;
  private final OnUserClickListener listener;

  /**
   * Interface for handling user click events.
   */
  public interface OnUserClickListener {
    /**
     * Called when a user item is clicked.
     * @param user The {@link User} object that was clicked.
     */
    void onUserClick(User user);
  }

  /**
   * Constructs an `AdminAdapter`.
   * @param accounts The initial list of {@link User} accounts to display.
   * @param listener The listener for handling user click events.
   */
  public AdminAdapter(List<User> accounts, OnUserClickListener listener) {
    this.accounts = accounts;
    this.listener = listener;
  }

  /**
   * Called when `RecyclerView` needs a new {@link AdminViewHolder} of the given type to represent an item.
   * This new ViewHolder should be constructed with a new View that can represent the items of the given type.
   *
   * @param parent The `ViewGroup` into which the new View will be added after it is bound to an adapter position.
   * @param viewType The view type of the new View.
   * @return A new {@link AdminViewHolder} that holds a View of the given view type.
   */
  @NonNull
  @Override
  public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.admin_list_item, parent, false);
    return new AdminViewHolder(view);
  }

  /**
   * Called by `RecyclerView` to display the data at the specified position.
   * This method updates the contents of the {@link AdminViewHolder} to reflect the item at the given position.
   * It also sets an `OnClickListener` to handle item clicks, update the selected user, and notify data changes.
   *
   * @param holder The {@link AdminViewHolder} which should be updated to represent the contents of the item at the given position in the data set.
   * @param position The position of the item within the adapter's data set.
   */
  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
    User user = accounts.get(position);
    holder.bind(user);
    holder.itemView.setOnClickListener(v -> {
      selectedUser = user;
      listener.onUserClick(user);
      notifyDataSetChanged();
    });
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   *
   * @return The total number of items.
   */
  @Override
  public int getItemCount() {
    return accounts.size();
  }

  /**
   * Updates the adapter's list of accounts and notifies the `RecyclerView` of the data change.
   *
   * @param accounts The new list of {@link User} accounts.
   */
  @SuppressLint("NotifyDataSetChanged")
  public void setAccounts(List<User> accounts) {
    this.accounts = accounts;
    notifyDataSetChanged();
  }

  /**
   * Returns the currently selected user.
   *
   * @return The selected {@link User}, or `null` if no user is selected.
   */
  public User getSelectedUser() {
    return selectedUser;
  }
}
