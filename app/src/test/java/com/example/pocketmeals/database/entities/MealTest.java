package com.example.pocketmeals.database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew Lee
 * created: 8/15/2025
 * Explanation: Unit tests for Meal entity
 */public class MealTest {

   Meal mealOne;
   Meal mealTwo;

  @Before
  public void setUp() throws Exception {
    mealOne = new Meal("Sunday",1,2);
    mealTwo = new Meal("Monday",2,6);
    mealOne.setMealId(1);
    mealTwo.setMealId(2);
  }

  @After
  public void tearDown() throws Exception {
    mealOne = null;
    mealTwo = null;
  }

  @Test
  public void getMealId() {
    assertEquals(1,mealOne.getMealId());
    assertEquals(2,mealTwo.getMealId());
  }

  @Test
  public void setMealId() {
    mealOne.setMealId(4);
    mealTwo.setMealId(9);
    assertEquals(4,mealOne.getMealId());
    assertEquals(9,mealTwo.getMealId());
    assertNotEquals(1,mealOne.getMealId());
    assertNotEquals(2,mealTwo.getMealId());
  }

  @Test
  public void getDay() {
    assertEquals("Sunday",mealOne.getDay());
    assertEquals("Monday",mealTwo.getDay());
  }

  @Test
  public void setDay() {
    mealOne.setDay("Thursday");
    mealTwo.setDay("Wednesday");
    assertEquals("Thursday",mealOne.getDay());
    assertEquals("Wednesday",mealTwo.getDay());
    assertNotEquals("Sunday",mealOne.getDay());
    assertNotEquals("Monday",mealTwo.getDay());
  }

  @Test
  public void testToString() {
    assertEquals("Sunday: 1", mealOne.toString());
    assertEquals("Monday: 2", mealTwo.toString());
  }

  @Test
  public void getRecipeId() {
    assertEquals(1,mealOne.getRecipeId());
    assertEquals(2,mealTwo.getRecipeId());
  }

  @Test
  public void setRecipeId() {
    mealOne.setRecipeId(8);
    mealTwo.setRecipeId(5);
    assertEquals(8,mealOne.getRecipeId());
    assertEquals(5,mealTwo.getRecipeId());
    assertNotEquals(1,mealOne.getRecipeId());
    assertNotEquals(2,mealTwo.getRecipeId());
  }

  @Test
  public void getUserId() {
    assertEquals(2,mealOne.getUserId());
    assertEquals(6,mealTwo.getUserId());
  }

  @Test
  public void setUserId() {
    mealOne.setUserId(12);
    mealTwo.setUserId(42);
    assertEquals(12,mealOne.getUserId());
    assertEquals(42,mealTwo.getUserId());
    assertNotEquals(2,mealOne.getUserId());
    assertNotEquals(6,mealTwo.getUserId());
  }
}