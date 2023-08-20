package com.storagesensei.db;

import com.storagesensei.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestUserRepository {
  @Autowired
  private UserRepository repo;

  @Autowired
  private TestEntityManager entityManager;

  /*@Test
  public void testCreateUser() {
    User user = new User();
    user.setUsername("testemail@test.com");
    user.setPassword("pass");
    user.setFirstName("FirstName");
    user.setLastName("LastName");

    User savedUser = repo.save(user);

    User persistedUser = entityManager.find(User.class, savedUser.getUsername());

    assertEquals(user.getUsername(), persistedUser.getUsername());
    assertEquals(user.getPassword(), persistedUser.getPassword());
    assertEquals(user.getFirstName(), persistedUser.getFirstName());
    assertEquals(user.getLastName(), persistedUser.getLastName());
  }*/
}
