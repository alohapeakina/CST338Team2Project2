package com.example.cst338team2project2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cst338team2project2.databinding.ActivityMainBinding;

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
  }
}