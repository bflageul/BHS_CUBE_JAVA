package com.cesi.bhs.api.data;

import java.util.Date;
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

  HashMap<Integer, Integer> getProduct();

  void setProduct(HashMap<Integer, Integer> product);

  Client getClient();

  void setClients(Client clients);
}
