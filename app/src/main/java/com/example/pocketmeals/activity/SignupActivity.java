package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketmeals.database.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private PocketMealsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = PocketMealsRepository.getRepository(getApplication());

        binding.signupButton.setOnClickListener(v -> createAccount());
    }

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

        repository.getUserByUserName(username).observe(this, existingUser -> {
            if (existingUser != null) {
                toastMaker("Username already exists.");
            } else {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                toastMaker("Account created! Please log in.");
                finish(); // Return to login screen
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent signupIntentFactory(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}
