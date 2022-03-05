package com.cesi.bhs.api;

import com.cesi.bhs.api.dao.Users;
import com.cesi.bhs.api.data.SimpleHttpResult;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.UsersDetails;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;

import static com.cesi.bhs.api.users.UsersManagerImpl.checkUsername;


public class UserHandler {
  /**
   * Routed by GET '/users'
   */
  public static void getAllUsers(RoutingContext routingContext) {
    List<com.cesi.bhs.api.data.Users> usersList;
    try {
      usersList = Users.getAllUsers();
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(usersList));
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

  /**
   * Routed by GET '/user/id'
   */
  public static void getUserById(RoutingContext routingContext) {
    try {
      com.cesi.bhs.api.data.Users userById;
      int id = Integer.parseInt(routingContext.pathParam("id"));
      userById = Users.getUserById(id);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(userById));
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
   * Routed by POST '/user'
   */
  public static void createUser(RoutingContext routingContext) {
    try {
      UsersDetails usersDetails = Json.decodeValue(routingContext.getBodyAsString(), UsersDetails.class);
      UsersRight usersRight = usersDetails.right;
      if (checkUsername(usersDetails.username)) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This username already exists. Please try another username.")));
        return;
      }
      UsersDetails userCreated = Users.createUser(usersRight, usersDetails);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily("A new user (AKA " + userCreated.username + ") has been created !"));
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
   * Routed by PUT '/user'
   */
  public static void updateUserById(RoutingContext routingContext) {
    try {
      UsersDetails usersDetails = Json.decodeValue(routingContext.getBodyAsString(), UsersDetails.class);
      UsersRight usersRight = usersDetails.right;
      if (checkUsername(usersDetails.username)) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This username already exists. Please try another username.")));
        return;
      }
      int id = Integer.parseInt(routingContext.pathParam("id"));
      UsersDetails userUpdated = Users.updateUserById(id, usersRight, usersDetails);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(userUpdated.username + "'s information have been correctly updated !"));
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
}

