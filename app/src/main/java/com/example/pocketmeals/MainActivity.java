package com.example.pocketmeals;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pocketmeals.databinding.ActivityMainBinding;

/**
 * @author Andrew Lee
 * Created 8/4/2025
 * Explanation: Main landing page for meal planning app
 */

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    TextView welcomeMessageTextView = findViewById(R.id.welcomeMessageTextView);

    String userName = "[User]"; //TODO: Update with logic to find username once database is ready
    String welcomeMessage = "Welcome " + userName;
    welcomeMessageTextView.setText(welcomeMessage);

  }

}