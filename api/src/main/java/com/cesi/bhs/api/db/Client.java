package com.cesi.bhs.api.db;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Represent a Client, a client is extended from the Users interface
 * A client can pass orders.
 * They have an Address and an e-mail
 */
public interface Client extends Users {
  public Address getAddress();
  public String getMail();
  public void setAddress(Address address);
  public void setMail(String mail);
}
