package com.cesi.bhs.api.db;

/** Implementation for Supplier ,
 *  and hello Lead Tech**/

public class SupplierImpl {
  private int id;
  private String name;
  private Address address;
  private String phone;



  public SupplierImpl(int id, String name, Address address, String phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }



  public int getId() {
    return id;
  }
  public Address getAddress() {
    return address;
  }
  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }


  public void setAddress(Address address) {
    this.address = address;
  }
  public void setId(int id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String toString() {
    return String.format("%s - %s - %s", address, name, phone);
  }

}
