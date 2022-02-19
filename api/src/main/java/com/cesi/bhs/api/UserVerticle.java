package com.cesi.bhs.api;

import com.cesi.bhs.api.users.UsersManagerImpl;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;


public class UserVerticle {

  // Get all users
  public static void getAllUsers(RoutingContext routingContext) {
    List usersManager = null;
    try {
      usersManager = UsersManagerImpl.getAllUsers();

      //if (currentUser.checkToken())

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(usersManager));
      return;
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
