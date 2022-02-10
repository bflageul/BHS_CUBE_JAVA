package com.cesi.bhs.api.data;

import java.sql.Date;
import java.util.HashMap;

public class OrderImpl implements Order {

  private int id;
  private Date orderdate;
  private Date deliverydate;
  private int price;
  private Client[] clients;
  private HashMap<String, Integer> Product;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getOrderdate() {
    return orderdate;
  }

  public void setOrderdate(Date orderdate) {
    this.orderdate = orderdate;
  }

  public Date getDeliverydate() {
    return deliverydate;
  }

  public void setDeliverydate(Date deliverydate) {
    this.deliverydate = deliverydate;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public HashMap<String, Integer> getProduct() {
    return Product;
  }

  public void setProduct(HashMap<String, Integer> product) {
    Product = product;
  }

  public Client[] getClients() {
    return clients;
  }

  public void setClients(Client[] clients) {
    this.clients = clients;
  }
}
