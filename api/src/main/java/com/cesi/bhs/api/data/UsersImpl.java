package com.cesi.bhs.api.data;

import static com.kosprov.jargon2.api.Jargon2.*;

/**
 * User class, this give some basic definitions of a user
 */
public class UsersImpl implements Users {
  private int id;
  private String username;
  private String lastname;
  private String firsname;
  // This is the hashed password
  // YOU SHOULD NEVER STORE THE PASSWORD AS PLAIN TEXT
  private String password;
  private UsersRight right;

  // -- Getters --
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

  public UsersRight getRight() {
    return right;
  }

  // -- Setters --
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

  public void setPasswordHash(String password) {
    this.password = password;
  }

  public void setClearPassword(String password) {
    String hash = hashPassword(password);
    this.password = hash;
  }

  public void setRight(UsersRight right) {
    this.right = right;
  }

  public boolean validatePassword(String password) {
    // Convert string to byte array
    byte[] passwordByte = password.getBytes();

    // Get Jargon2 verifier
    Verifier verifier = jargon2Verifier();

    // Check if the password matches and return the result
    return verifier.hash(getPassword()).password(passwordByte).verifyEncoded();
  }

  private String hashPassword(String password) {
    // Convert string to byte array
    byte[] passwordByte = password.getBytes();

    // Configure the hasher
    Hasher hasher = jargon2Hasher()
      .type(Type.ARGON2i)  // Hashing algorithm, argon2i is better suited for passwords
      .memoryCost(131072)  // 64MB memory cost
      .timeCost(6)         // 3 passes through memory
      .parallelism(4)      // use 4 lanes and 4 threads
      .saltLength(32)      // 32 random bytes salt
      .hashLength(128);    // 128 bytes output hash

    // Pass the password to the hasher and return the result
    return hasher.password(passwordByte).encodedHash();
  }

  public boolean enabled() {
    String password = getPassword();

    if (password == null) {
      return false;
    }

    return password.length() > 0;
  }
}
