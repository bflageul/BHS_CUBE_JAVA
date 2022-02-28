package com.cesi.bhs.api;

import com.cesi.bhs.api.dao.UsersManagerImpl;
import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.UsersDetails;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;


public class UserVerticle {

  /**
   * Get all users method
   * @param routingContext
   */
  public static void getAllUsers(RoutingContext routingContext) {
    List usersList = null;
    try {
      usersList = UsersManagerImpl.getAllUsers();

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(usersList));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void getUserById(RoutingContext routingContext) {
    Users userById = new UsersImpl();
    try {
      int id = Integer.parseInt(routingContext.pathParam("id"));
      userById = UsersManagerImpl.getUserById(id);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(userById));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public static void createUser(RoutingContext routingContext) {
    UsersDetails usersDetails = Json.decodeValue(routingContext.getBodyAsString(), UsersDetails.class);
    UsersRight usersRight = usersDetails.right;
    System.out.println(usersDetails);
    try {
      UsersDetails userCreated = UsersManagerImpl.createUser(usersRight, usersDetails);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily("A new "+ userCreated + "has been created !"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
};


/*
// Create a new user

  public static void createUser


    try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

      String sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";

      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, "bill");
      statement.setString(2, "secretpass");
      statement.setString(3, "Bill Gates");
      statement.setString(4, "bill.gates@microsoft.com");

      int rowsInserted = statement.executeUpdate();
      if (rowsInserted > 0) {
        System.out.println("A new user was inserted successfully!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
*/
