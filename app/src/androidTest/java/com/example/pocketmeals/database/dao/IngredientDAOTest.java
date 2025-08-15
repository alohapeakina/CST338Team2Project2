package com.example.pocketmeals.database.dao;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pocketmeals.LiveDataTestUtil;
import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.entities.Ingredient;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

@RunWith(AndroidJUnit4.class)
public class IngredientDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PocketMealsDatabase db;
    private IngredientDAO ingredientDAO;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PocketMealsDatabase.class)
                .allowMainThreadQueries()
                .build();
        ingredientDAO = db.ingredientDAO();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Tomato", "pcs", 18, 0.9, 0.2, 3.9, "Vegetable");
        ingredientDAO.insert(ingredient);

        List<Ingredient> allIngredients = LiveDataTestUtil.getValue(ingredientDAO.getAllIngredients());
        assertEquals(1, allIngredients.size());
        assertEquals("Tomato", allIngredients.get(0).getName());
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Apple", "pcs", 52, 0.3, 0.2, 14, "Fruit");
        ingredientDAO.insert(ingredient);

        Ingredient inserted = LiveDataTestUtil.getValue(ingredientDAO.getAllIngredients()).get(0);
        inserted.setCalories(60);
        ingredientDAO.update(inserted);

        Ingredient updated = LiveDataTestUtil.getValue(ingredientDAO.getAllIngredients()).get(0);
        assertEquals(60, updated.getCalories(), 0.001);
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        Ingredient ingredient = new Ingredient("Potato", "pcs", 77, 2, 0.1, 17, "Vegetable");
        ingredientDAO.insert(ingredient);

        Ingredient inserted = LiveDataTestUtil.getValue(ingredientDAO.getAllIngredients()).get(0);
        ingredientDAO.delete(inserted);

        List<Ingredient> allIngredients = LiveDataTestUtil.getValue(ingredientDAO.getAllIngredients());
        assertTrue(allIngredients.isEmpty());
    }
}
