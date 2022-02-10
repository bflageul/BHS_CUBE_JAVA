
package com.cesi.bhs.api.data;

/** Supplier interface define ,
 * Supplier class with his variable ,
 * it contains address (Class Address)
 * name, phone, id **/

public interface Supplier {



  int getId();
  Address getAddress();
  String getName();
  String getPhone();



  void setAddress(Address address);
  void setId(int id);
  void setName(String name);
  void setPhone(String phone);

}

