package com.cesi.bhs.api.data;

import java.sql.Date;

/**
 * Interface using Product table, associated with Order table informations
 */
public interface Product {
  int getId() ;

  void setId(int id);

  String getName();

  void setName(String name);

  int getStock();

  void setStock(int stock);

  String getDescription() ;

  void setDescription(String description);

  String getType();

  void setType(String type);

  String getOrigin() ;

  void setOrigin(String origin) ;

  String getMedal() ;

  void setMedal(String medal);

  Date getBirthdate() ;

  void setBirthdate(Date birthdate);

  String getProductorname() ;

  void setProductorname(String productorname);


  Supplier[] getSupplier() ;

  void setSupplier(Supplier[] supplier) ;

}
