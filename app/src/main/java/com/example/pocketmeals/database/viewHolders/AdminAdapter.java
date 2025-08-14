package com.example.pocketmeals.database.viewHolders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.database.entities.User;
import java.util.List;

/**
 * @author Andrew Lee
 * created: 8/14/2025
 * Explanation:
 */
public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

  private List<User> accounts;

public AdminAdapter(List<User> accounts) {
  this.accounts = accounts;
}

public void setAccounts(List<User> accounts) {
  this.accounts = accounts;
  notifyDataSetChanged();
}

  @NonNull
  @Override
  public AdminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
    return new AdminAdapter.AdminViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {
    holder.textAccountName.setText(accounts.get(position).getUsername());
  }

  @Override
  public int getItemCount() {
    return accounts.size();
  }

  static class AdminViewHolder extends RecyclerView.ViewHolder {
    TextView textAccountName;
    public AdminViewHolder(View itemView) {
      super(itemView);
      textAccountName = itemView.findViewById(android.R.id.text1);
    }
  }

}
