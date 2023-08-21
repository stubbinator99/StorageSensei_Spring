package com.storagesensei.config;

import com.storagesensei.db.UserRepository;
import com.storagesensei.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class UserViewMapper {
  @Autowired
  private UserRepository userRepo;

  public abstract UserView toUserView(User user);

  public abstract List<UserView> toUserView(List<User> users);

  //public UserView toUserViewById(ObjectId id) {
  public UserView toUserViewById(String id) {
    if (id == null) {
      return null;
    }
    return toUserView(userRepo.findByUsername(id).orElse(null));
  }
}
