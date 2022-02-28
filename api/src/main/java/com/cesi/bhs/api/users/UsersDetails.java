package com.cesi.bhs.api.users;

import com.cesi.bhs.api.data.UsersRight;

/**
 * Special class used to create a list of users in UsersManagerImpl.getAllUsers (then routed with /users)
 */
public class UsersDetails {
  public String username;
  public String firstname;
  public String lastname;
  public String password;
  public String email;
  public String postcode;
  public String street;
  public String city;
  public String country;
  public UsersRight right;



//  private String username;
//  private String lastname;
//  private String firstname;
//  private UsersRight right;
//
//  // Getters implementation
//  public String getUsername() {
//    return username;
//  }
//  public String getLastname() {
//    return lastname;
//  }
//  public String getFirstname() {
//    return firstname;
//  }
//  public UsersRight getRight() {
//    return right;
//  }
//
//  // Setters implementation
//  public void setUsername(String username) {
//    this.username = username;
//  }
//  public void setLastname(String lastname) {
//    this.lastname = lastname;
//  }
//  public void setFirstname(String firstname) {
//    this.firstname = firstname;
//  }
//  public void setRight(UsersRight right) {
//    this.right = right;
//  }
}
