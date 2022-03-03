package com.cesi.bhs.api.data;

import com.cesi.bhs.api.supplier.RegistrationSupplier;

/**
 * Implementation for Supplier ,
 * and hello Lead Tech
 **/

public class SupplierImpl implements Supplier {
  private int id;
  private String name;
  private Address address;
  private String phone;
  private String mail;

  public SupplierImpl(int id, String name, Address address, String phone, String mail) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.mail = mail;
  }

  public SupplierImpl(int supplierid, String suppliername) {
    this.id = supplierid;
    this.name = suppliername;
  }

  public SupplierImpl() {
  }

  public SupplierImpl(RegistrationSupplier registrationSupplier) {
    this.address = registrationSupplier.address;
    this.name = registrationSupplier.name;
    this.phone = registrationSupplier.phone;
    this.mail = registrationSupplier.mail;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getMail() {
    return mail;
  }

  @Override
  public void setMail(String mail) {
    this.mail = mail;
  }

  @Override
  public String toString() {
    return String.format("%s - %s - %s", address, name, phone);
  }
}
