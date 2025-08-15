package com.example.pocketmeals.database.viewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketmeals.R;
import com.example.pocketmeals.database.entities.User;

/**
 * @author Andrew Lee
 * created: 8/14/2025
 * Explanation: A `ViewHolder` for the `RecyclerView` in the admin view.
 * This class holds and manages the views for a single user item in the list.
 * It provides a `bind` method to populate the views with data from a {@link User} object,
 * displaying the username and the user's admin status.
 */
public class AdminViewHolder extends RecyclerView.ViewHolder {
    private final TextView usernameTextView;
    private final TextView adminStatusTextView;

    /**
     * Constructs a new `AdminViewHolder`.
     *
     * @param itemView The `View` for a single list item.
     */
    public AdminViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.usernameTextView);
        adminStatusTextView = itemView.findViewById(R.id.adminStatusTextView);
    }

    /**
     * Binds a {@link User} object to the views in the ViewHolder.
     * <p>
     * This method sets the text of the username TextView to the user's username
     * and the text of the admin status TextView to "Admin" or "User" based on
     * the user's admin status.
     *
     * @param user The {@link User} object to bind to the view.
     */
    public void bind(User user) {
        usernameTextView.setText(user.getUsername());
        adminStatusTextView.setText(user.isAdmin() ? "Admin" : "User");
    }
}
