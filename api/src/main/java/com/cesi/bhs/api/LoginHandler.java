package com.cesi.bhs.api;

import com.cesi.bhs.api.authentication.LoginManager;
import com.cesi.bhs.api.authentication.LoginManagerImpl;
import com.cesi.bhs.api.authentication.LoginUser;
import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.*;

public class LoginHandler {
  /**
   * Log the user in, generate a token and return it in JSON
   */
  public static void login(RoutingContext routingContext) {
    // Get data from request
    try {
      final LoginUser loginUser = Json.decodeValue(routingContext.getBodyAsString(), LoginUser.class);

      // Get password from database
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/stivedb", "cesi", "cesi");

      PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE (username = ?)");
      preparedStatement.setString(1, loginUser.username);

      ResultSet resultSet = preparedStatement.executeQuery();
      Users user = new UsersImpl();
      if (resultSet.next()) {
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setFirsname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setPasswordHash(resultSet.getString("password"));
      }

      conn.close();

      // If user does not have a password it is considered as disabled
      if (!user.enabled()) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This user is not allowed to connect.")));
        return;
      }

      LoginManager loginManager = new LoginManagerImpl();

      String token = loginManager.login(user, loginUser.password);

      if (token == "Token creation failed") {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "Token creation failed, check the username and password.")));
        return;
      }

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end("{\"token\": \"" + token + "\"}");
      return;
    } catch (SQLException e) {
      e.printStackTrace();
      routingContext.response()
       .setStatusCode(500)
       .putHeader("content-type", "application/json; charset=utf-8")
       .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
       .setStatusCode(400)
       .putHeader("content-type", "application/json; charset=utf-8")
       .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }
}
