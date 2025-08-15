package com.example.pocketmeals.database.dao;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.pocketmeals.LiveDataTestUtil;
import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.entities.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class RecipeDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RecipeDAO recipeDAO;
    private PocketMealsDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PocketMealsDatabase.class)
                .allowMainThreadQueries()
                .build();
        recipeDAO = db.recipeDAO();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertRecipe() throws Exception {
        Recipe recipe = new Recipe("Pasta", "http://example.com", 500, 20, 10, 60);
        recipeDAO.insert(recipe);

        List<Recipe> recipes = LiveDataTestUtil.getValue(recipeDAO.getAllRecipes());
        assertEquals(1, recipes.size());
        assertEquals("Pasta", recipes.get(0).getRecipeName());
    }

    @Test
    public void updateRecipe() throws Exception {
        Recipe recipe = new Recipe("Pasta", "http://example.com", 500, 20, 10, 60);
        recipeDAO.insert(recipe);

        Recipe insertedRecipe = LiveDataTestUtil.getValue(recipeDAO.getAllRecipes()).get(0);
        insertedRecipe.setRecipeName("Updated Pasta");
        recipeDAO.update(insertedRecipe);

        Recipe updatedRecipe = LiveDataTestUtil.getValue(recipeDAO.getAllRecipes()).get(0);
        assertEquals("Updated Pasta", updatedRecipe.getRecipeName());
    }

    @Test
    public void deleteRecipe() throws Exception {
        Recipe recipe = new Recipe("Pasta", "http://example.com", 500, 20, 10, 60);
        recipeDAO.insert(recipe);

        Recipe insertedRecipe = LiveDataTestUtil.getValue(recipeDAO.getAllRecipes()).get(0);
        recipeDAO.delete(insertedRecipe);

        List<Recipe> recipes = LiveDataTestUtil.getValue(recipeDAO.getAllRecipes());
        assertTrue(recipes.isEmpty());
    }
}

