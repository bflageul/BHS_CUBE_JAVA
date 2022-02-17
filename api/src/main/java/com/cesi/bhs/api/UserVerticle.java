package com.cesi.bhs.api;

import com.cesi.bhs.api.authentication.AuthenticationManager;
import com.cesi.bhs.api.authentication.AuthenticationManagerImpl;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.*;


public class UserVerticle {
  static String dbURL = "jdbc:postgresql://localhost/stivedb";
  static String username = "cesi";
  static String password = "cesi";

  static AuthenticationManager currentUser = new AuthenticationManagerImpl();

  // Get all users
  public static void getAllUsers(RoutingContext routingContext) {
    //if (currentUser.checkToken())
      try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

        String query = "SELECT * FROM users";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
          String username = result.getString(2);
          String firstname = result.getString(3);
          String lastname = result.getString(4);
          String password = result.getString(5);
          System.out.println(" " + username);
        }

        routingContext.response()
          .setStatusCode(200)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily("Hello"));
        return;
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
  }
}


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
