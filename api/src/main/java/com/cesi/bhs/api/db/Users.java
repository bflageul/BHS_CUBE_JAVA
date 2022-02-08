package com.cesi.bhs.api.db;

/**
 * A user is a person, it can be a client or an employee
 * A user have a way to login in the app.
 */
public interface Users {
  public int getId();
  public String getUsername();
  public String getFirsname();
  public String getLastname();

  public void setId(int id);
  public void setUsername(String username);
  public void setFirsname(String firsname);
  public void setLastname(String lastname);
}
