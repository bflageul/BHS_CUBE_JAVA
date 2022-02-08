package com.cesi.bhs.api.db;

import java.sql.Date;

/**
 * Interface using Product table, associated with Order table informations
 */
public interface Product {
  public int getId() ;

  public void setId(int id);

  public String getName();

  public void setName(String name);

  public int getStock();

  public void setStock(int stock);

  public String getDescription() ;

  public void setDescription(String description);

  public String getType();

  public void setType(String type);

  public String getOrigin() ;

  public void setOrigin(String origin) ;

  public String getMedal() ;

  public void setMedal(String medal);

  public Date getBirthdate() ;

  public void setBirthdate(Date birthdate);

  public String getProductorname() ;

  public void setProductorname(String productorname);


  public Supplier[] getSupplier() ;

  public void setSupplier(Supplier[] supplier) ;

}
