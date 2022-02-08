package com.cesi.bhs.api.db;

import db.Client;

import java.sql.Date;
import java.util.HashMap;

public class Orders implements OrderImpl{

  private int id;
  private Date orderdate;
  private Date deliverydate;
  private int price;
  private db.Client[] Client;
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
}
