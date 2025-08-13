package com.example.pocketmeals.database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew Lee
 * created: 8/13/2025
 * Explanation: Unit tests for Ingredient entity object
 */
public class IngredientTest {

  Ingredient cucumber;
  Ingredient carrot;

  @Before
  public void setUp() throws Exception {
    cucumber = new Ingredient("cucumber","count",45,1,0.2,7,"Vegetables");
    cucumber.setId(1);
    carrot = new Ingredient("carrot","count",25,0.5,0,6,"Vegetables");
    carrot.setId(2);
  }

  @After
  public void tearDown() throws Exception {
    cucumber = null;
    carrot = null;
  }

  @Test
  public void getId() {
  }

  @Test
  public void setId() {
  }

  @Test
  public void getName() {
  }

  @Test
  public void setName() {
  }

  @Test
  public void getUnit() {
  }

  @Test
  public void setUnit() {
  }

  @Test
  public void getCalories() {
  }

  @Test
  public void setCalories() {
  }

  @Test
  public void getProtein() {
  }

  @Test
  public void setProtein() {
  }

  @Test
  public void getFat() {
  }

  @Test
  public void setFat() {
  }

  @Test
  public void getCarbs() {
  }

  @Test
  public void setCarbs() {
  }

  @Test
  public void getCategory() {
  }

  @Test
  public void setCategory() {
  }

  @Test
  public void toShoppingList() {
  }
}