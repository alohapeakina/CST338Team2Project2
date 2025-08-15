package com.example.pocketmeals.database.viewHolders;
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
 * Explanation:
 */
public class AdminAdapter extends RecyclerView.Adapter<AdminViewHolder> {
  private List<User> accounts;
  private User selectedUser;
  private final OnUserClickListener listener;

  public interface OnUserClickListener {
    void onUserClick(User user);
  }

  public AdminAdapter(List<User> accounts, OnUserClickListener listener) {
    this.accounts = accounts;
    this.listener = listener;
  }

  @NonNull
  @Override
  public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.admin_list_item, parent, false);
    return new AdminViewHolder(view);
  }

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

  @Override
  public int getItemCount() {
    return accounts.size();
  }

  public void setAccounts(List<User> accounts) {
    this.accounts = accounts;
    notifyDataSetChanged();
  }

  public User getSelectedUser() {
    return selectedUser;
  }
}
