package com.example.pocketmeals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.pocketmeals.database.typeConverters.PocketMealsRepository;
import com.example.pocketmeals.database.entities.User;
import com.example.pocketmeals.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PocketMealsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = PocketMealsRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> verifyUser());

        binding.signupTextView.setOnClickListener(view -> startActivity(SignupActivity.signupIntentFactory(LoginActivity.this)));
    }

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

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}

