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
    assertEquals(1,cucumber.getId());
    assertEquals(2, carrot.getId());
  }

  @Test
  public void setId() {
    cucumber.setId(3);
    carrot.setId(4);
    assertEquals(3,cucumber.getId());
    assertEquals(4,carrot.getId());
    assertNotEquals(1,cucumber.getId());
    assertNotEquals(2,carrot.getId());
  }

  @Test
  public void getName() {
    assertEquals("cucumber",cucumber.getName());
    assertEquals("carrot",carrot.getName());
  }

  @Test
  public void setName() {
    cucumber.setName("zucchini");
    assertEquals("zucchini",cucumber.getName());
    assertNotEquals("cucumber",cucumber.getName());
    cucumber.setName("cucumber");
    assertEquals("cucumber",cucumber.getName());
  }

  @Test
  public void getUnit() {
    assertEquals("count",cucumber.getUnit());
    assertEquals("count",carrot.getUnit());
  }

  @Test
  public void setUnit() {
    cucumber.setUnit("each");
    carrot.setUnit("each");
    assertEquals("each",cucumber.getUnit());
    assertNotEquals("count",cucumber.getUnit());
    assertEquals("each",carrot.getUnit());
    assertNotEquals("count",carrot.getUnit());
  }

  @Test
  public void getCalories() {
    assertEquals(45,cucumber.getCalories(),0);
    assertEquals(25,carrot.getCalories(),0);
  }

  @Test
  public void setCalories() {
  }

  @Test
  public void getProtein() {
    assertEquals(1,cucumber.getProtein(),0);
    assertEquals(0.5,carrot.getProtein(),0);
  }

  @Test
  public void setProtein() {
  }

  @Test
  public void getFat() {
    assertEquals(0.2,cucumber.getFat(),0);
    assertEquals(0,carrot.getFat(),0);
  }

  @Test
  public void setFat() {
  }

  @Test
  public void getCarbs() {
    assertEquals(7,cucumber.getCarbs(),0);
    assertEquals(6,carrot.getCarbs(),0);
  }

  @Test
  public void setCarbs() {
  }

  @Test
  public void getCategory() {
    assertEquals("Vegetables",cucumber.getCategory());
    assertEquals("Vegetables",carrot.getCategory());
  }

  @Test
  public void setCategory() {
  }

  @Test
  public void toShoppingList() {
  }
}