package com.cesi.bhs.api.users;

import com.cesi.bhs.api.data.UsersRight;

public class GetAllUsers {
  private String username;
  private String lastname;
  private String firstname;
  private UsersRight right;

  public GetAllUsers(String username, String lastname, String firstname, UsersRight right) {
    this.username = username;
    this.lastname = lastname;
    this.firstname = firstname;
    this.right = right;
  }

  // Getters implementation
  public String getUsername() {
    return username;
  }
  public String getLastname() {
    return lastname;
  }
  public String getFirstname() {
    return firstname;
  }
  public UsersRight getRight() {
    return right;
  }

  // Setters implementation
  public void setUsername(String username) {
    this.username = username;
  }
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  public void setRight(UsersRight right) {
    this.right = right;
  }
}
