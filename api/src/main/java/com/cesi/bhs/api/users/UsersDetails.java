package com.cesi.bhs.api.users;

import com.cesi.bhs.api.data.UsersRight;

import java.sql.Date;

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
  public String job;
  public Date orderdate;
  public Date deliverydate;
  public int price;
  public UsersRight right;
}
