package com.cesi.bhs.api.db;

/**
 * User class, this give some basic definitions of a user
 */
public abstract class Users {
  private int id;
  private String username;
  private String lastname;
  private String firsname;
  // This is the hashed password
  // YOU SHOULD NEVER STORE THE PASSWORD AS PLAIN TEXT
  private String password;

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFirsname() {
    return firsname;
  }

  public String getLastname() {
    return lastname;
  }

  protected String getPassword() {
    return password;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setFirsname(String firsname) {
    this.firsname = firsname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  protected void setPassword(String password) {
    this.password = password;
  }
}
