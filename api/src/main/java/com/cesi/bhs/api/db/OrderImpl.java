package com.cesi.bhs.api.db;

import java.sql.Date;
import java.util.HashMap;

/**
 *  Interface for Order model, associated with client and products models
 */
public interface OrderImpl {

  public int getId() ;

  public void setId(int id) ;

  public Date getOrderdate();

  public void setOrderdate(Date orderdate);

  public Date getDeliverydate();

  public void setDeliverydate(Date deliverydate);

  public int getPrice() ;

  public void setPrice(int price);

  public HashMap<String, Integer> getProduct();

  public void setProduct(HashMap<String, Integer> product);
}
