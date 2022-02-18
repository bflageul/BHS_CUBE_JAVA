package com.cesi.bhs.api;

import com.cesi.bhs.api.authentication.AuthenticationManager;
import com.cesi.bhs.api.authentication.AuthenticationManagerImpl;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.GetAllUsers;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.*;
import java.util.*;


public class UserVerticle {
  static String dbURL = "jdbc:postgresql://localhost/stivedb";
  static String username = "cesi";
  static String password = "cesi";

  static AuthenticationManager currentUser = new AuthenticationManagerImpl();

  // Get all users
  public static void getAllUsers(RoutingContext routingContext) {
    //if (currentUser.checkToken())
    try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

      String query = "SELECT users.*, client.address, client.mail, employee.job FROM users LEFT JOIN client ON client.users = id LEFT JOIN employee ON employee.users = id;";
      Statement statement = conn.createStatement();
      ResultSet result = statement.executeQuery(query);

      List<GetAllUsers> usersList = new ArrayList<GetAllUsers>();

      while (result.next()) {
        if (result.getString(8) != null) {
          GetAllUsers getAllUsers = new GetAllUsers(
            result.getString(2),
            result.getString(3),
            result.getString(4),
            UsersRight.NORMAL_EMPLOYEE
          );
          usersList.add(getAllUsers);
        } else {
          GetAllUsers getAllUsers = new GetAllUsers(
            result.getString(2),
            result.getString(3),
            result.getString(4),
            UsersRight.CLIENT
          );
          usersList.add(getAllUsers);
        }
      }

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(usersList));
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
