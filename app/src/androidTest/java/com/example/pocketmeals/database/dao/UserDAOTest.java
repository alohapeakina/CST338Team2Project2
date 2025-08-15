package com.example.pocketmeals.database.dao;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.pocketmeals.database.PocketMealsDatabase;
import com.example.pocketmeals.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UserDAOTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private PocketMealsDatabase db;
  private UserDAO userDAO;

  @Before
  public void setUp() {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, PocketMealsDatabase.class)
            .allowMainThreadQueries()
            .build();
    userDAO = db.userDAO();
  }

  @After
  public void tearDown() throws IOException {
    db.close();
  }

  @Test
  public void insertAndGetUserSync() {
    User user = new User("testuser", "password123");
    userDAO.insert(user);

    User fetched = userDAO.getUserByUserNameSync("testuser");
    assertNotNull(fetched);
    assertEquals("testuser", fetched.getUsername());
    assertEquals("password123", fetched.getPassword());
  }

  @Test
  public void insertAndGetUserList() {
    userDAO.insert(new User("u1", "p1"));
    userDAO.insert(new User("u2", "p2"));

    List<User> users = userDAO.getUserList();
    assertEquals(2, users.size());
  }

  @Test
  public void updateUser() {
    User user = new User("oldname", "pass");
    userDAO.insert(user);

    User inserted = userDAO.getUserByUserNameSync("oldname");
    inserted.setUsername("newname");
    userDAO.update(inserted);

    User updated = userDAO.getUserByUserNameSync("newname");
    assertNotNull(updated);
    assertEquals("newname", updated.getUsername());
  }

  @Test
  public void deleteUser() {
    User user = new User("deleteMe", "pass");
    userDAO.insert(user);

    User inserted = userDAO.getUserByUserNameSync("deleteMe");
    assertNotNull(inserted);

    userDAO.delete(inserted);
    User deleted = userDAO.getUserByUserNameSync("deleteMe");
    assertNull(deleted);
  }

  @Test
  public void deleteAllUsers() {
    userDAO.insert(new User("u1", "p1"));
    userDAO.insert(new User("u2", "p2"));

    userDAO.deleteAll();
    List<User> users = userDAO.getUserList();
    assertTrue(users.isEmpty());
  }
}
