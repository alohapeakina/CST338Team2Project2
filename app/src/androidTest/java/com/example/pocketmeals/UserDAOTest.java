package com.example.pocketmeals;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.dao.UserDAO;
import com.example.pocketmeals.database.entities.User;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Andrew Lee
 * created: 8/13/2025
 * Explanation: Database tests for modification of UserDAO within user_table
 */
public class UserDAOTest {
  private UserDAO userDao;
  private PocketMealsDatabase db;

  @Before
  public void createDb() throws Exception {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, PocketMealsDatabase.class).build();
    userDao = db.userDAO();
  }

  @After
  public void closeDb() throws IOException {
    db.close();
  }

  @Test
  public void insert() {
    User user = new User("Test User","testpassword");
    userDao.insert(user);

    List<User> users = userDao.getUserList();
    assertEquals(1,users.size());
    assertEquals("Test User", users.get(0).getUsername());

    //Adding second user
    User user2 = new User("guestaccount","guestpassword");
    userDao.insert(user2);

    //Refreshing the list of users and validating new size and user entries
    List<User> users2 = userDao.getUserList();
    assertEquals(2,users2.size());
    assertEquals("Test User",users2.get(0).getUsername());
    assertEquals("guestaccount", users2.get(1).getUsername());
  }

  @Test
  public void delete() {
    //TODO: Add test logic
  }

  @Test
  public void getAllUsers() {
    //TODO: Add test logic
  }

  @Test
  public void deleteAll() {
    //TODO: Add test logic
  }

  @Test
  public void getUserByUserName() {
    //TODO: Add test logic
  }

  @Test
  public void getUserByUserId() {
    //TODO: Add test logic
  }
}