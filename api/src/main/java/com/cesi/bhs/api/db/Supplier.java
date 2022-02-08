
package com.cesi.bhs.api.db;

/** Supplier interface define , Supplier class with his variable , it contains address (Class Address)
 * name, phone, id **/

public interface Supplier {

  /** getter de supplier **/

  public int getId();
  public Address getAddress();
  public String getName();
  public String getPhone();

  /** setters de Supllier **/

  public void setAddress(Address address);
  public void setId(int id);
  public void setName(String name);
  public void setPhone(String phone);

}

