package com.cesi.bhs.api.db;

/**
 * Represent a Client, it can
 */
public interface Client {
  public Address getAddress();
  public String getMail();
  public void setAddress(Address address);
  public void setMail(String mail);
}
