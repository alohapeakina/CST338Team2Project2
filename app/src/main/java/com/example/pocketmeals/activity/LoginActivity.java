package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.databinding.ActivityLoginBinding;

/**
 * @author Team 2
 * Created 8/5/2025
 * Explanation: The LoginActivity class is responsible for handling user authentication.
 * It provides a user interface for users to enter their username and password to log in.
 * Users who do not have an account can navigate to the {@link SignupActivity}.
 */
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private PocketMealsRepository repository;

    /**
     * Called when the activity is first created.
     * This method initializes the activity, sets up the layout using view binding,
     * and initializes the database repository. It also sets up click listeners for the
     * login button and the signup text view.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     * Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = PocketMealsRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> verifyUser());

        binding.signupTextView.setOnClickListener(view -> startActivity(SignupActivity.signupIntentFactory(LoginActivity.this)));
    }

    /**
     * Verifies the user's credentials.
     * This method retrieves the username from the input field and checks if it's empty.
     * If not, it queries the database for the user with the provided username.
     * If a user is found, it compares the entered password with the stored password.
     * Upon successful verification, it starts the {@link MainActivity}.
     * Otherwise, it displays a toast message indicating an invalid username or password.
     */
    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toastMaker("Username may not be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                   startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));

                    finish();
                } else {
                    toastMaker("Invalid password.");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.passwordLoginEditText.setSelection(0);
            }
        });
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
     * A factory method to create an {@link Intent} for starting the {@link LoginActivity}.
     *
     * @param context The context of the calling activity.
     * @return A new {@link Intent} configured to start the {@link LoginActivity}.
     */
    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}

