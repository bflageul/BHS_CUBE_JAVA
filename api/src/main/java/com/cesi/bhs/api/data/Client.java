package com.cesi.bhs.api.data;

/**
 * Represent a Client, a client is extended from the Users interface
 * A client can pass orders.
 * They have an Address and an e-mail
 */
public interface Client extends Users {
  Address getAddress();

  String getMail();

  void setAddress(Address address);

  void setMail(String mail);
}
