package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.Order;
import com.cesi.bhs.api.data.OrderImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.cesi.bhs.api.dao.Users.getUserById;

public class Orders {
  @Contract("_ -> param1")
  public static @NotNull Order createOrder(@NotNull Order order) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement orderStatement = connect.getConnection().prepareStatement("INSERT INTO orders (orderdate, deliverydate, price) VALUES (?, ?, ?) RETURNING id");
    orderStatement.setDate(1, (Date) order.getOrderdate());
    orderStatement.setDate(2, (Date) order.getDeliverydate());
    orderStatement.setInt(3, order.getPrice());

    ResultSet resultSet = orderStatement.executeQuery();

    resultSet.next();
    order.setId(resultSet.getInt("id"));

    PreparedStatement orderJoinClient = connect.getConnection().prepareStatement("INSERT INTO order_join_client VALUES (?, ?)");
    orderJoinClient.setInt(1, order.getClient().getId());
    orderJoinClient.setInt(2, order.getId());

    orderJoinClient.execute();

    for (Map.Entry<Integer, Integer> integerIntegerEntry : order.getProduct().entrySet()) {

      PreparedStatement orderJoinProducts = connect.getConnection().prepareStatement("INSERT INTO orders_join_product VALUES (?, ?, ?)");
      orderJoinProducts.setInt(1, order.getId());
      orderJoinProducts.setInt(2, integerIntegerEntry.getKey());
      orderJoinProducts.setInt(3, integerIntegerEntry.getValue());
      orderJoinProducts.execute();
    }

    return order;
  }

  public static @NotNull Order getOrderById(int id) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = connect.getConnection().prepareStatement("SELECT orders.*, " +
      "ARRAY_AGG(orders_join_product.product) as products, " +
      "ARRAY_AGG(orders_join_product.quantity) as quantity, " +
      "MIN(orders_join_client.users) as user " +
      "FROM orders " +
      "LEFT JOIN orders_join_product ON orders.id = orders_join_product.orders " +
      "LEFT JOIN orders_join_client ON orders.id = orders_join_client.orders " +
      "WHERE id = ? " +
      "GROUP BY orders.id ;");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    Order order;

    resultSet.next();
    Integer[] quantity = (Integer[]) resultSet.getArray("quantity").getArray();
    Integer[] products = (Integer[]) resultSet.getArray("products").getArray();

    HashMap<Integer, Integer> productQuantity = new HashMap<>();

    for (int i = 0; i < products.length; i++) {
      productQuantity.put(products[i], quantity[i]);
    }

    Client client = (Client) getUserById(resultSet.getInt("user")).getValue0();

    order = new OrderImpl(
      resultSet.getInt("id"),
      resultSet.getDate("orderdate"),
      resultSet.getDate("deliverydate"),
      resultSet.getInt("price"),
      client,
      productQuantity
    );

    return order;
  }

  public static @NotNull Order updateOrder(@NotNull Order order) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement orderStatement = connect.getConnection().prepareStatement("UPDATE orders SET orderdate = ? SET deliverydate = ? SET price = ? WHERE id = ?");
    orderStatement.setDate(1, (Date) order.getOrderdate());
    orderStatement.setDate(2, (Date) order.getDeliverydate());
    orderStatement.setInt(3, order.getPrice());
    orderStatement.setInt(4, order.getId());

    orderStatement.executeUpdate();

    PreparedStatement orderJoinClient = connect.getConnection().prepareStatement("UPDATE order_join_client SET product = ? WHERE client = ?");
    orderJoinClient.setInt(2, order.getClient().getId());
    orderJoinClient.setInt(1, order.getId());

    orderJoinClient.execute();

    for (Map.Entry<Integer, Integer> entry : order.getProduct().entrySet()) {
      PreparedStatement orderJoinProducts = connect.getConnection().prepareStatement("UPDATE orders_join_product SET product = ? SET quantity = ? WHERE product = ?");
      orderJoinProducts.setInt(3, order.getId());
      orderJoinProducts.setInt(1, entry.getKey());
      orderJoinProducts.setInt(2, entry.getValue());
      orderJoinProducts.execute();
    }

    return order;
  }

  public static void deleteOrder(int id) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = connect.getConnection().prepareStatement("DELETE FROM orders WHERE id = ? RETURNING id");
    preparedStatement.setInt(1, id);

    preparedStatement.executeQuery();
  }

  public static @NotNull List<Order> getAllOrders() throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = connect.getConnection().prepareStatement(
      "SELECT orders.* ," +
        "ARRAY_AGG(orders_join_product.product) as products, " +
        "ARRAY_AGG(orders_join_product.quantity) as quantity," +
        "MIN(orders_join_client.users) as user" +
        "FROM orders" +
        "LEFT JOIN orders_join_product ON orders.id = orders_join_product.orders" +
        "LEFT JOIN orders_join_client ON orders.id = orders_join_client.orders" +
        "GROUP BY orders.id");

    ResultSet resultSet = preparedStatement.executeQuery();

    List<Order> orderList = new ArrayList<>();

    while (resultSet.next()) {
      int[] quantity = (int[]) resultSet.getArray("quantity").getArray();
      int[] products = (int[]) resultSet.getArray("products").getArray();

      HashMap<Integer, Integer> productQuantity = new HashMap<>();

      for (int i = 0; i < products.length; i++) {
        productQuantity.put(products[i], quantity[i]);
      }

      Client client = (Client) getUserById(resultSet.getInt("client")).getValue0();

      orderList.add(new OrderImpl(
        resultSet.getInt("id"),
        resultSet.getDate("orderdate"),
        resultSet.getDate("deliverydate"),
        resultSet.getInt("price"),
        client,
        productQuantity
      ));
    }

    return orderList;
  }

  public static @NotNull List<Order> getOrdersFromUser(int id) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = connect.getConnection().prepareStatement(
      "SELECT orders.* ," +
        "ARRAY_AGG(orders_join_product.product) as products, " +
        "ARRAY_AGG(orders_join_product.quantity) as quantity," +
        "MIN(orders_join_client.users) as user" +
        "FROM orders" +
        "LEFT JOIN orders_join_product ON orders.id = orders_join_product.orders" +
        "LEFT JOIN orders_join_client ON orders.id = orders_join_client.orders" +
        "GROUP BY orders.id " +
        "WHERE user = ?");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    List<Order> orderList = new ArrayList<>();

    while (resultSet.next()) {
      int[] quantity = (int[]) resultSet.getArray("quantity").getArray();
      int[] products = (int[]) resultSet.getArray("products").getArray();

      HashMap<Integer, Integer> productQuantity = new HashMap<>();

      for (int i = 0; i < products.length; i++) {
        productQuantity.put(products[i], quantity[i]);
      }

      Client client = (Client) getUserById(resultSet.getInt("client")).getValue0();

      orderList.add(new OrderImpl(
        resultSet.getInt("id"),
        resultSet.getDate("orderdate"),
        resultSet.getDate("deliverydate"),
        resultSet.getInt("price"),
        client,
        productQuantity
      ));
    }

    return orderList;
  }
  // getOrdersToSupplier
}

