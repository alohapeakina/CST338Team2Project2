package com.example.pocketmeals.database.viewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.entities.User;

public class AdminViewHolder extends RecyclerView.ViewHolder {
    private final TextView usernameTextView;
    private final TextView adminStatusTextView;

    public AdminViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.usernameTextView);
        adminStatusTextView = itemView.findViewById(R.id.adminStatusTextView);
    }

    public void bind(User user) {
        usernameTextView.setText(user.getUsername());
        adminStatusTextView.setText(user.isAdmin() ? "Admin" : "User");
    }
}
