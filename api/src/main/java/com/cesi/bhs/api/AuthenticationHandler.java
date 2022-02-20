package com.cesi.bhs.api;

import com.cesi.bhs.api.authentication.*;
import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.ClientImpl;
import com.cesi.bhs.api.data.SimpleHttpResult;
import com.cesi.bhs.api.data.Users;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.regex.Pattern;

import static com.cesi.bhs.api.dao.Authentication.getUser;

public class AuthenticationHandler {
  /**
   * Log the user in, generate a token and return it in JSON
   */
  public static void login(RoutingContext routingContext) {
    // Get data from request
    try {
      final LoginUser loginUser = Json.decodeValue(routingContext.getBodyAsString(), LoginUser.class);

      Users user = getUser(loginUser.username);

      // If user does not have a password it is considered as disabled
      if (!user.enabled()) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This user is not allowed to connect.")));
        return;
      }

      AuthenticationManager authenticationManager = AuthenticationManagerImpl.getInstance();

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
  public static void register(@NotNull RoutingContext routingContext) {
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

      AuthenticationManager authenticationManager = AuthenticationManagerImpl.getInstance();

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

  /**
   * Check if the user request is allowed in this context
   */
  public static void checkToken(@NotNull RoutingContext routingContext) {
    final String authorizationHeader = routingContext.request().getHeader("Authorization");
    final String token = authorizationHeader.split(" ")[1];

    AuthenticationManager authenticationManager = AuthenticationManagerImpl.getInstance();

    if (!authenticationManager.checkToken(token, AuthenticationContext.LOGIN)) {
      routingContext.response()
        .setStatusCode(401)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(new SimpleHttpResult(401, "You should login before accessing this resource")));
    }
    routingContext.next();
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
