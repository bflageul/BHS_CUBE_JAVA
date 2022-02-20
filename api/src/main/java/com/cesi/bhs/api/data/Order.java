package com.cesi.bhs.api.data;

import java.sql.Date;
import java.util.HashMap;

/**
 * Interface for Order model, associated with client and products models
 */
public interface Order {

  int getId();

  void setId(int id);

  Date getOrderdate();

  void setOrderdate(Date orderdate);

  Date getDeliverydate();

  void setDeliverydate(Date deliverydate);

  int getPrice();

  void setPrice(int price);

  HashMap<String, Integer> getProduct();

  void setProduct(HashMap<String, Integer> product);

  Client[] getClients();

  void setClients(Client[] clients);
}
