package com.cesi.bhs.api;

import com.cesi.bhs.api.authentication.AuthenticationManager;
import com.cesi.bhs.api.authentication.AuthenticationManagerImpl;
import com.cesi.bhs.api.authentication.LoginUser;
import com.cesi.bhs.api.authentication.RegistrationUser;
import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.ClientImpl;
import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.*;
import java.util.regex.Pattern;

public class AuthenticationHandler {
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

      AuthenticationManager authenticationManager = new AuthenticationManagerImpl();

      String token = authenticationManager.login(user, loginUser.password);

      if (token == "Token creation failed") {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "Login failed, please check the username and password.")));
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
    } catch (NullPointerException e) {
      e.printStackTrace();
      routingContext.response()
       .setStatusCode(400)
       .putHeader("content-type", "application/json; charset=utf-8")
       .end(Json.encodePrettily(new SimpleHttpResult(400, "Body is empty")));
    }
  }

  /**
   * Check the user data, and if unique register the user in database
   */
  public static void register(RoutingContext routingContext) {
    // Get data from request
    try {
      final RegistrationUser registrationUser = Json.decodeValue(routingContext.getBodyAsString(), RegistrationUser.class);

      if (!emailValidation(registrationUser.mail)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid e-mail address")));
        return;
      }

      if (registrationUser.password.length() <= 0) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Password cannot be empty")));
        return;
      }

      if (registrationUser.username.length() <= 0) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "You should provide a username")));
        return;
      }

      if (registrationUser.firstname.length() <= 0) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Firstname is mandatory")));
        return;
      }

      if (registrationUser.lastname.length() <= 0) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Lastname is mandatory")));
        return;
      }

      Client client = new ClientImpl(registrationUser);

      AuthenticationManager authenticationManager = new AuthenticationManagerImpl();

      String registrationResult = authenticationManager.register(client);

      if (registrationResult == "Registration done, please login") {
        routingContext.response()
          .setStatusCode(201)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(200, registrationResult)));
        return;
      } else if (registrationResult == "Registration failed, internal server error") {
        routingContext.response()
        .setStatusCode(500)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(500, registrationResult)));
        return;
      }

      routingContext.response()
        .setStatusCode(400)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(400, registrationResult)));

    } catch (DecodeException e) {
      e.printStackTrace();
      routingContext.response()
       .setStatusCode(400)
       .putHeader("content-type", "application/json; charset=utf-8")
       .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
    }
  }

  private static boolean emailValidation(String email) {
    String regex = "^(.+)@(.+)$";
    Pattern pattern = Pattern.compile(regex);
    if (email == null) {
      return false;
    }
    return pattern.matcher(email).matches();
  }
}
