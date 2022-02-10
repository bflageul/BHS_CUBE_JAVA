package com.cesi.bhs.api.data;

import com.cesi.bhs.api.authentication.RegistrationUser;

public class ClientImpl extends UsersImpl implements Client {
  private Address address;
  private String mail;

  public ClientImpl(RegistrationUser registrationUser) {
    setUsername(registrationUser.username);
    setFirsname(registrationUser.username);
    setLastname(registrationUser.lastname);
    setClearPassword(registrationUser.password);
    this.mail = registrationUser.mail;
  }

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
