package com.cesi.bhs.api.db;

public class Client extends Users {
  private Address address;
  private String mail;

  public Address getAddress() {
    return address;
  }

  public String getMail() {
    return mail;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }
}
