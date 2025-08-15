package com.example.pocketmeals.database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew Lee
 * created: 8/15/2025
 * Explanation:
 */public class RecipeTest {

   Recipe testRecipeOne;
   Recipe testRecipeTwo;

  @Before
  public void setUp() throws Exception {
    testRecipeOne = new Recipe("SampleOne","site.com",14.6,12,6,9.1);
    testRecipeTwo = new Recipe("SampleTwo","example.com",145.2,95.1,64.1,12.2);
    testRecipeOne.setRecipeId(1);
    testRecipeTwo.setRecipeId(2);
  }

  @After
  public void tearDown() throws Exception {
    testRecipeOne = null;
    testRecipeTwo = null;
  }

  @Test
  public void getRecipeId() {
    assertEquals(1,testRecipeOne.getRecipeId());
    assertEquals(2,testRecipeTwo.getRecipeId());
    assertNotEquals(3,testRecipeOne.getRecipeId());
    assertNotEquals(4,testRecipeTwo.getRecipeId());
  }

  @Test
  public void setRecipeId() {
    testRecipeOne.setRecipeId(3);
    testRecipeTwo.setRecipeId(4);
    assertEquals(3,testRecipeOne.getRecipeId());
    assertEquals(4,testRecipeTwo.getRecipeId());
    assertNotEquals(1,testRecipeOne.getRecipeId());
    assertNotEquals(2,testRecipeTwo.getRecipeId());
  }

  @Test
  public void getRecipeName() {
    assertEquals("SampleOne",testRecipeOne.getRecipeName());
    assertEquals("SampleTwo",testRecipeTwo.getRecipeName());
  }

  @Test
  public void setRecipeName() {
    testRecipeOne.setRecipeName("SampleUno");
    testRecipeTwo.setRecipeName("SampleDos");
    assertEquals("SampleUno",testRecipeOne.getRecipeName());
    assertEquals("SampleDos",testRecipeTwo.getRecipeName());
    assertNotEquals("SampleOne",testRecipeOne.getRecipeName());
    assertNotEquals("SampleTwo",testRecipeTwo.getRecipeName());
  }

  @Test
  public void getRecipeLink() {
    assertEquals("site.com",testRecipeOne.getRecipeLink());
    assertEquals("example.com",testRecipeTwo.getRecipeLink());
  }

  @Test
  public void setRecipeLink() {
    testRecipeOne.setRecipeLink("csumb.edu");
    testRecipeTwo.setRecipeLink("fakesite.com");
    assertEquals("csumb.edu",testRecipeOne.getRecipeLink());
    assertEquals("fakesite.com",testRecipeTwo.getRecipeLink());
    assertNotEquals("site.com",testRecipeOne.getRecipeLink());
    assertNotEquals("example.com",testRecipeTwo.getRecipeLink());
  }

  @Test
  public void getTotalCalories() {
    assertEquals(14.6,testRecipeOne.getTotalCalories(),0);
    assertEquals(145.2,testRecipeTwo.getTotalCalories(),0);
  }

  @Test
  public void setTotalCalories() {
    testRecipeOne.setTotalCalories(126.1);
    testRecipeTwo.setTotalCalories(802.6);
    assertEquals(126.1,testRecipeOne.getTotalCalories(),0);
    assertEquals(802.6,testRecipeTwo.getTotalCalories(),0);
    assertNotEquals(14.6,testRecipeOne.getTotalCalories(),0);
    assertNotEquals(145.2,testRecipeTwo.getTotalCalories(),0);
  }

  @Test
  public void getProtein() {
    assertEquals(12,testRecipeOne.getProtein(),0);
    assertEquals(95.1,testRecipeTwo.getProtein(),0);
  }

  @Test
  public void setProtein() {
    testRecipeOne.setProtein(19.3);
    testRecipeTwo.setProtein(29.5);
    assertEquals(19.3,testRecipeOne.getProtein(),0);
    assertEquals(29.5,testRecipeTwo.getProtein(),0);
    assertNotEquals(12,testRecipeOne.getProtein(),0);
    assertNotEquals(95.1,testRecipeTwo.getProtein(),0);
  }

  @Test
  public void getFat() {
    assertEquals(6,testRecipeOne.getFat(),0);
    assertEquals(64.1,testRecipeTwo.getFat(),0);
  }

  @Test
  public void setFat() {
    testRecipeOne.setFat(34.4);
    testRecipeTwo.setFat(51.8);
    assertEquals(34.4,testRecipeOne.getFat(),0);
    assertEquals(51.8,testRecipeTwo.getFat(),0);
    assertNotEquals(6,testRecipeOne.getFat(),0);
    assertNotEquals(64.1,testRecipeTwo.getFat(),0);
  }

  @Test
  public void getCarbs() {
    assertEquals(9.1,testRecipeOne.getCarbs(),0);
    assertEquals(12.2,testRecipeTwo.getCarbs(),0);
  }

  @Test
  public void setCarbs() {
    testRecipeOne.setCarbs(16.7);
    testRecipeTwo.setCarbs(22.9);
    assertEquals(16.7,testRecipeOne.getCarbs(),0);
    assertEquals(22.9,testRecipeTwo.getCarbs(),0);
    assertNotEquals(9.1,testRecipeOne.getCarbs(),0);
    assertNotEquals(12.2,testRecipeTwo.getCarbs(),0);
  }
}