package com.example.pocketmeals;

import com.example.pocketmeals.activity.MealPlanActivity;
import android.content.Context;
import android.content.Intent;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Objects;

/**
 * Instrumented test class for MealPlanActivity's Intent factory method.
 * This test is designed to be run on an Android device or emulator.
 */
@RunWith(AndroidJUnit4.class)
public class MealPlanActivityTest {

    /**
     * Tests the mealPlanIntentFactory method to ensure it creates a valid Intent
     * that correctly targets the MealPlanActivity class.
     */
    @Test
    public void mealPlanIntentFactory_createsCorrectIntent() {
        // Get a test context from the application, required to create an Intent.
        Context context = ApplicationProvider.getApplicationContext();

        // Call the static factory method to get the Intent to test.
        Intent intent = MealPlanActivity.mealPlanIntentFactory(context);

        // Use assertions to verify the Intent.
        // Check that the Intent object itself is not null.
        assertNotNull(intent);

        // Verify that the Intent's target component is the MealPlanActivity.
        // Compare the class name of the component with the expected class name.
        assertEquals(MealPlanActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());
    }
}
