package com.example.pocketmeals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.pocketmeals.database.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew Lee
 * created: 8/13/2025
 * Explanation: Unit tests for User entity object
 */public class UserTest {

   User adminUser;
   User testUser;

   @Before
  public void setUp() {
     adminUser = new User("adminaccount","adminpassword");
     adminUser.setAdmin(true);
     testUser = new User("testaccount","testpassword");
   }

   @After
   public void tearDown() {
     adminUser = null;
     testUser = null;
   }

  @Test
  public void getUsername(){
    String adminAccountName = adminUser.getUsername();
    String testAccountName = testUser.getUsername();
    assertEquals("adminaccount", adminAccountName);
    assertEquals("testaccount",testAccountName);
  }

  @Test
  public void getPassword() {
     String adminAccountPassword = adminUser.getPassword();
     String testAccountPassword = testUser.getPassword();
     assertEquals("adminpassword",adminAccountPassword);
     assertEquals("testpassword",testAccountPassword);
  }

  @Test
  public void isAdmin() {
     assertFalse(testUser.isAdmin());
     assertTrue(adminUser.isAdmin());
  }

  @Test
  public void setUsername(){
    adminUser.setUsername("newadminaccount");
    testUser.setUsername("newtestaccount");
    String adminAccountName = adminUser.getUsername();
    String testAccountName = testUser.getUsername();
    assertEquals("newadminaccount", adminAccountName);
    assertEquals("newtestaccount",testAccountName);
  }

  @Test
  public void setPassword() {
    adminUser.setPassword("newadminpassword");
    testUser.setPassword("newtestpassword");
    String adminAccountPassword = adminUser.getPassword();
    String testAccountPassword = testUser.getPassword();
    assertEquals("newadminpassword",adminAccountPassword);
    assertEquals("newtestpassword",testAccountPassword);
  }

}
