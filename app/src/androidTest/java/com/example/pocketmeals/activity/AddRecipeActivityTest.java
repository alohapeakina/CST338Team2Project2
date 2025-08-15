package com.example.pocketmeals.activity;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Objects;

/**
 * Instrumented test class for AddRecipeActivity's Intent factory method.
 * This test will run on an Android device or emulator to verify the Intent.
 */
@RunWith(AndroidJUnit4.class)
public class AddRecipeActivityTest {

    /**
     * Tests the addRecipeIntentFactory method to ensure it creates a valid Intent
     * that correctly targets the AddRecipeActivity class.
     */
    @Test
    public void addRecipeIntentFactory_createsCorrectIntent() {
        // Get a test context from the application, required to create an Intent.
        Context context = ApplicationProvider.getApplicationContext();

        // Call the static factory method to get the Intent to test.
        Intent intent = AddRecipeActivity.addRecipeIntentFactory(context);

        // Use assertions to verify the Intent.
        // Check that the Intent object itself is not null.
        assertNotNull(intent);

        // Verify that the Intent's target component is the AddRecipeActivity.
        // Compare the class name of the component with the expected class name.
        assertEquals(AddRecipeActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());
    }
}
