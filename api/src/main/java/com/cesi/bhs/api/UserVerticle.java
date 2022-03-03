package com.cesi.bhs.api;

import com.cesi.bhs.api.dao.UsersManagerImpl;
import com.cesi.bhs.api.data.SimpleHttpResult;
import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.UsersDetails;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;
import java.util.List;

import static com.cesi.bhs.api.users.UsersManagerImpl.checkUsername;


public class UserVerticle {

  /**
   * Routed by GET '/users'
   *
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
   * Routed by GET '/user/id'
   *
   * @param routingContext
   */
  public static void getUserById(RoutingContext routingContext) {
    try {
      Users userById = new UsersImpl();
      int id = Integer.parseInt(routingContext.pathParam("id"));
      userById = UsersManagerImpl.getUserById(id);
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
   * CLIENT’s data : {"username": "","firstname":"","lastname":"","password": "", "email":"","postcode":"","street":"a","country":"","city":"","right":""}'
   * EMPLOYEE’s data : {"username": "","firstname":"","lastname":"","password": "", "job":"","right":""}'
   *
   * @param routingContext
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
      UsersDetails userCreated = UsersManagerImpl.createUser(usersRight, usersDetails);
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
   * CLIENT’s data : {"username": "","firstname":"","lastname":"","password": "", "email":"","postcode":"","street":"a","country":"","city":"","right":""}'
   * EMPLOYEE’s data : {"username": "","firstname":"","lastname":"","password": "", "job":"","right":""}'
   *
   * @param routingContext
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
      UsersDetails userUpdated = UsersManagerImpl.updateUserById(id, usersRight, usersDetails);
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
};

