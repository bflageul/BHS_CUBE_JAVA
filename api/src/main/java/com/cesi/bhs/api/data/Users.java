package com.cesi.bhs.api.data;

/**
 * A user is a person, it can be a client or an employee
 * A user have a way to login in the app.
 */
public interface Users {
  int getId();

  String getUsername();

  String getFirsname();

  String getLastname();

  String getPassword();

  UsersRight getRight();

  void setId(int id);

  void setUsername(String username);

  void setFirsname(String firsname);

  void setLastname(String lastname);

  void setRight(UsersRight right);

  /**
   * Directly set the password, no check is done on this
   * ! WARNING !
   * This does not hash the password
   *
   * @param password
   */
  void setPasswordHash(String password);

  /**
   * Take a password, hash it and then set it to the user
   *
   * @param password
   */
  void setClearPassword(String password);

  /**
   * Take the password and validate it by comparing it to this.password
   *
   * @param password
   * @return
   */
  boolean validatePassword(String password);

  /**
   * Check if the user is active and allowed to login
   *
   * @return
   */
  boolean enabled();
}
