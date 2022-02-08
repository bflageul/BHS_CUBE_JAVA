package com.cesi.bhs.api.db;
/** classe de modele Supplier avec les meme champs de nommage que la db supplier -> fournisseurs  **/

 public class Supplier {
  private int id;
  private String name;
  private Address address;
  private String phone;

  /** constructor de Supplier **/

  public Supplier(int id, String name, Address address, String phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  /** getter de supplier **/

  public int getId() {
    return id;
  }

  public String getAddress() {
    return adress;
  }

  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }
   /** setters de Supllier **/
  public void setAddress(String address) {
    this.adress = adress;
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

