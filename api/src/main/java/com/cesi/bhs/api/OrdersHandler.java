package com.cesi.bhs.api;

import com.cesi.bhs.api.dao.Orders;
import com.cesi.bhs.api.data.Order;
import com.cesi.bhs.api.data.OrderImpl;
import com.cesi.bhs.api.data.SimpleHttpResult;
import com.cesi.bhs.api.orders.CreateOrder;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public class OrdersHandler {
  public static void createOrder(RoutingContext routingContext) {
    try {
      final CreateOrder createOrder = Json.decodeValue(routingContext.getBodyAsString(), CreateOrder.class);

      Order order = new OrderImpl(createOrder);

      Order result = Orders.createOrder(order);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(result));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }

  public static void getOrderById(RoutingContext routingContext) {
    try {
      final int id = Integer.parseInt(routingContext.request().getParam("id"));

      Order order = Orders.getOrderById(id);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(order));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }

  public static void updateOrderById(RoutingContext routingContext) {
    try {
      final CreateOrder createOrder = Json.decodeValue(routingContext.getBodyAsString(), CreateOrder.class);

      Order order = new OrderImpl(createOrder);

      Order result = Orders.updateOrder(order);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(result));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }

  public static void deleteOrder(RoutingContext routingContext) {
    try {
      final int id = Integer.parseInt(routingContext.request().getParam("id"));

      Orders.deleteOrder(id);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(200, "Deleted id")));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }

  public static void getAllOrders(RoutingContext routingContext) {
    try {
      final @NotNull List<Order> orders = Orders.getAllOrders();

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(orders));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    }
  }

  public static void getOrdersFromUser(RoutingContext routingContext) {
    try {
      final int id = Integer.parseInt(routingContext.request().getParam("id"));

      final @NotNull List<Order> orders = Orders.getOrdersFromUser(id);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(orders));
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    }
  }

  public static void getOrdersToSupplier(RoutingContext routingContext) {
    // Unimplemented
  }
}
