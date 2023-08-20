package com.storagesensei.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name="username")
  private String username;

  @Column(name="password")
  private String password;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  public User() { }

  public User(String username, String password, String firstName, String lastName) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String email) {
    this.username = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
