package com.cesi.bhs.api.data;

import com.cesi.bhs.api.orders.CreateOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderImpl implements Order {
  private int id;
  private Date orderdate;
  private Date deliverydate;
  private int price;
  private Client client;
  private HashMap<Integer, Integer> product;

  public OrderImpl(int id, Date orderdate, Date deliverydate, int price, Client client, HashMap<Integer, Integer> productQuantity) {
    this.id = id;
    this.orderdate = orderdate;
    this.deliverydate = deliverydate;
    this.price = price;
    this.client = client;
    this.product = productQuantity;
  }

  public OrderImpl(CreateOrder createOrder) {
    this.orderdate = new Date();
    this.price = 0;

    for (Map.Entry<Integer, Integer> integerIntegerEntry : createOrder.product.entrySet()) {
      this.price += integerIntegerEntry.getValue();
    }

    this.client = createOrder.client;
    this.product = createOrder.product;
  }

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
  public HashMap<Integer, Integer> getProduct() {
    return product;
  }

  @Override
  public void setProduct(HashMap<Integer, Integer> product) {
    this.product = product;
  }

  @Override
  public Client getClient() {
    return client;
  }

  @Override
  public void setClients(Client clients) {
    this.client = clients;
  }
}
