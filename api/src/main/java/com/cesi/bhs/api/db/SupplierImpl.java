package com.cesi.bhs.api.db;
/** classe de modele Supplier avec les meme champs de nommage que la db supplier -> fournisseurs  **/

public class SupplierImpl {
  private int id;
  private String name;
  private Address address;
  private String phone;

  /** constructor de Supplier **/

  public SupplierImpl(int id, String name, Address address, String phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  /** getter de supplier **/

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
  /** setters de Supllier **/
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

}
