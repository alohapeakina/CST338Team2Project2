package com.example.pocketmeals.database.dao;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.entities.Meal;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MealDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MealDAO mealDAO;
    private PocketMealsDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PocketMealsDatabase.class)
                .allowMainThreadQueries() // fine for testing only
                .build();
        mealDAO = db.mealDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertAndGetMeal() {
        Meal meal = new Meal("Monday", 1);
        mealDAO.insert(meal);

        List<Meal> meals = mealDAO.getAllMeals();
        assertEquals(1, meals.size());
        assertEquals("Monday", meals.get(0).getDay());
    }

    @Test
    public void updateMeal() {
        Meal meal = new Meal("Monday", 1);
        mealDAO.insert(meal);

        Meal savedMeal = mealDAO.getAllMeals().get(0);
        savedMeal.setDay("Tuesday");
        mealDAO.update(savedMeal);

        Meal updatedMeal = mealDAO.getMealById(savedMeal.getMealId());
        assertEquals("Tuesday", updatedMeal.getDay());
    }

    @Test
    public void deleteMeal() {
        Meal meal = new Meal("Monday",1 );
        mealDAO.insert(meal);

        Meal savedMeal = mealDAO.getAllMeals().get(0);
        mealDAO.delete(savedMeal);

        assertTrue(mealDAO.getAllMeals().isEmpty());
    }

    @Test
    public void getMealsByDay() {
        mealDAO.insert(new Meal("Monday",1 ));
        mealDAO.insert(new Meal("Monday", 1));
        mealDAO.insert(new Meal("Tuesday", 1));

        List<Meal> mondayMeals = mealDAO.getMealsByDay("Monday");
        assertEquals(2, mondayMeals.size());
    }
}

