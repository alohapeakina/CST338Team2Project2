package com.example.pocketmeals.activity;

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
 * Instrumented test class for MainActivity's Intent factory method.
 * This test runs on an Android device or emulator to verify the Intent.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Define the extra key here to ensure consistency between the Activity and the test.
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.pocketmeals.MAIN_ACTIVITY_USER_ID";

    /**
     * Tests the mainActivityIntentFactory method to ensure it creates a valid Intent
     * that correctly targets the MainActivity class and includes the correct user ID.
     */
    @Test
    public void mainActivityIntentFactory_createsCorrectIntentWithUserId() {
        // Get a test context from the application.
        Context context = ApplicationProvider.getApplicationContext();

        // Define a test user ID to pass to the factory method.
        int testUserId = 99;

        // Call the static factory method to get the Intent to test.
        Intent intent = MainActivity.mainActivityIntentFactory(context, testUserId);

        // Use assertions to verify the Intent.
        // Check that the Intent object itself is not null.
        assertNotNull(intent);

        // Verify that the Intent's target component is the MainActivity.
        assertEquals(MainActivity.class.getName(), Objects.requireNonNull(intent.getComponent()).getClassName());

        // Verify that the Intent contains the correct user ID extra.
        // Retrieve the integer extra and compare it with the test value.
        assertEquals(testUserId, intent.getIntExtra(MAIN_ACTIVITY_USER_ID, -1));
    }
}
