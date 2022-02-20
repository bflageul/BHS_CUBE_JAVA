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

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Date getOrderdate() {
    return orderdate;
  }

  @Override
  public void setOrderdate(Date orderdate) {
    this.orderdate = orderdate;
  }

  @Override
  public Date getDeliverydate() {
    return deliverydate;
  }

  @Override
  public void setDeliverydate(Date deliverydate) {
    this.deliverydate = deliverydate;
  }

  @Override
  public int getPrice() {
    return price;
  }

  @Override
  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public HashMap<String, Integer> getProduct() {
    return Product;
  }

  @Override
  public void setProduct(HashMap<String, Integer> product) {
    Product = product;
  }

  @Override
  public Client[] getClients() {
    return clients;
  }

  @Override
  public void setClients(Client[] clients) {
    this.clients = clients;
  }
}
