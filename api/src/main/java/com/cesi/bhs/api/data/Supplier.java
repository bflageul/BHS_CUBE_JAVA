
package com.cesi.bhs.api.data;

/**
 * Supplier interface define ,
 * Supplier class with his variable ,
 * it contains address (Class Address)
 * name, phone, id
 **/

public interface Supplier {
  int getId();

  String getAddress();

  String getName();

  String getPhone();

  String getMail();

  void setAddress(Address address);

  void setId(int id);

  void setName(String name);

  void setPhone(String phone);

  void setMail(String mail);



void setAddress(Address[] address);

  void setAddress(String address);

  void setSupplier(com.cesi.bhs.api.dao.Supplier[] toArray);
}

