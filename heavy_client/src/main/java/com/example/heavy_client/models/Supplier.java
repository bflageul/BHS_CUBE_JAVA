package com.example.heavy_client.models;

/** Implementation for Supplier ,
 *  and hello Lead Tech**/

public class Supplier {
  private int id;
  private String name;
  private String address;
  private String phone;



  public Supplier(int id, String name, String address, String phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }



  public int getId() {
    return id;
  }
  public String getAddress() {
    return address;
  }
  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }


  public void setAddress(String address) {
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
