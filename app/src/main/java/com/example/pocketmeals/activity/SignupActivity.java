package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.databinding.ActivitySignupBinding;

/**
 * @author Team 2
 * Created 8/9/2025
 * Explanation: The SignupActivity class allows new users to create an account.
 * It provides a user interface for entering a desired username and password,
 * and handles the validation and creation of a new user in the database.
 */
public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private PocketMealsRepository repository;

    /**
     * Called when the activity is first created.
     * This method initializes the activity, sets up the layout using view binding,
     * and initializes the database repository. It also sets a click listener for the
     * signup button to initiate the account creation process.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     * Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = PocketMealsRepository.getRepository(getApplication());

        binding.signupButton.setOnClickListener(v -> createAccount());
    }

    /**
     * Creates a new user account if the username does not already exist.
     * This method validates the user input for username, password, and confirmation password.
     * It checks for empty fields and ensures the passwords match. If the input is valid,
     * it calls a repository method to check for an existing user with the same username.
     * If the username is unique, a new {@link User} is created and inserted into the database,
     * and the user is redirected to the login screen. If the username is taken,
     * an error message is displayed.
     */
    private void createAccount() {
        String username = binding.userNameSignupEditText.getText().toString().trim();
        String password = binding.passwordSignupEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordSignupEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            toastMaker("All fields are required.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            toastMaker("Passwords do not match.");
            return;
        }

        repository.checkUserAndCreate(
                username,
                password,
                () -> runOnUiThread(() -> toastMaker("Username already exists.")),
                () -> runOnUiThread(() -> {
                    toastMaker("Account created! Please log in.");
                    finish();
                })
        );
    }

    /**
     * Displays a short toast message to the user.
     *
     * @param message The message to be displayed in the toast.
     */
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * A factory method to create an {@link Intent} for starting the {@link SignupActivity}.
     *
     * @param context The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link SignupActivity}.
     */
    public static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}
