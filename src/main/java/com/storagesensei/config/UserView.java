package com.storagesensei.config;

//@Data // Lombok
public class UserView {
  private String id;
  private String username;
  private String fullName;

  public UserView() {
  }

  public UserView(String id, String username, String fullName) {
    this.id = id;
    this.username = username;
    this.fullName = fullName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
