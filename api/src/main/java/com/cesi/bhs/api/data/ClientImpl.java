package com.cesi.bhs.api.data;

import com.cesi.bhs.api.authentication.RegistrationUser;
import org.jetbrains.annotations.NotNull;

public class ClientImpl extends UsersImpl implements Client {
  private Address address;
  private String mail;

  public ClientImpl(@NotNull RegistrationUser registrationUser) {
    setUsername(registrationUser.username);
    setFirsname(registrationUser.username);
    setLastname(registrationUser.lastname);
    setClearPassword(registrationUser.password);
    this.mail = registrationUser.mail;
  }

  public ClientImpl() {

  }

  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public String getMail() {
    return mail;
  }

  @Override
  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public void setMail(String mail) {
    this.mail = mail;
  }
}
