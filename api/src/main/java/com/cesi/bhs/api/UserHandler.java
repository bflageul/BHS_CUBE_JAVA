package com.cesi.bhs.api;

import com.cesi.bhs.api.dao.Users;
import com.cesi.bhs.api.data.SimpleHttpResult;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.UsersDetails;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.javatuples.Triplet;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import static com.cesi.bhs.api.dao.Authentication.isUsernameAvailable;
import static com.cesi.bhs.api.dao.Users.isEmailAvailable;
import static com.cesi.bhs.api.dao.Users.isUsernameUsedByOther;


public class UserHandler {

  /**
   * Routed by GET '/users'
   */
  public static void getAllUsers(RoutingContext routingContext) {
    try {

      List<com.cesi.bhs.api.data.Users> usersList = Users.getAllUsers();

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

      Triplet userById;
//      com.cesi.bhs.api.data.Users userById;
      int id = Integer.parseInt(routingContext.pathParam("id"));

      if (!Users.doesIdExist(id)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "This user does not exist")));
      }

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
   * Routed by GET '/user'
   */
  public static void getCurrentUser(RoutingContext routingContext) {

    try {
      final String authorizationHeader = routingContext.request().getHeader("Authorization");
      final String token = authorizationHeader.split(" ")[1];

      byte[] decoded = Base64.getDecoder().decode(token);
      String decodedToken = new String(decoded);
      String[] splitDecodedMsg = decodedToken.split(":");
      int idFromToken = Integer.parseInt(splitDecodedMsg[0]);

      Triplet currentUser = Users.getUserById(idFromToken);

      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(currentUser));
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

      // Check if username is already used in database
      if (!isUsernameAvailable(usersDetails.username)) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This username already exists. Please try another username.")));
        return;
        // Else if conditions to verify NON NULL fields
      } else if (usersDetails.firstname == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Firstname is missing. Please inform a firstname.")));
        return;
      } else if (usersDetails.lastname == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Lastname is missing. Please inform a lastname.")));
        return;
      } else if (usersDetails.username == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Username is missing. Please inform a username.")));
        return;
      } else if (usersDetails.password == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Password is missing. Please inform a password.")));
        return;
      } else if ((usersRight == UsersRight.NORMAL_EMPLOYEE
        || usersRight == UsersRight.HIGH_EMPLOYEE
        || usersRight == UsersRight.DIRECTOR)
        && usersDetails.job == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Job is missing. Please inform a job.")));
        return;
      } else if (usersRight == UsersRight.CLIENT && usersDetails.email == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Email is missing. Please inform a valid email address.")));
        return;
      } else if (usersRight == UsersRight.CLIENT
        && (usersDetails.postcode == null
        || usersDetails.street == null
        || usersDetails.country == null
        || usersDetails.city == null)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid address information. Please ensure all address fields are informed.")));
        return;
        // Check if email is already used in database
      } else if (usersRight == UsersRight.CLIENT && !isEmailAvailable(usersDetails.email)) {
        routingContext.response()
          .setStatusCode(401)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(401, "This email already exists. Change email account information.")));
        return;
      }
      // then execute update process
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
   * Routed by PUT '/user/id'
   */
  public static void updateUserById(RoutingContext routingContext) {
    int id = Integer.parseInt(routingContext.pathParam("id"));
    try {

      UsersDetails usersDetails = Json.decodeValue(routingContext.getBodyAsString(), UsersDetails.class);
      UsersRight usersRight = usersDetails.right;
      com.cesi.bhs.api.dao.Users user = new Users();

      // verified if username is not used by another user
      if (isUsernameUsedByOther(id, usersDetails.username)) {
//      if (!isUsernameAvailable(usersDetails.username) && (!usersDetails.username.equals(Users.getUserById(id).getUsername())) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "This username is already used by another user.")));
        return;
        // verified if id passed in URL match with UserRight passed in Json
      } else if (usersRight == UsersRight.CLIENT && !user.isClient(id)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "This user is not referred as a client.")));
        return;
      } else if ((usersRight == UsersRight.NORMAL_EMPLOYEE
        || usersRight == UsersRight.HIGH_EMPLOYEE
        || usersRight == UsersRight.DIRECTOR)
        && !user.isEmployee(id)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "This user is not referred as a employee.")));
        return;
        // Conditions to verify NON NULL fields
      } else if (usersDetails.firstname == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Firstname is missing. Please inform a firstname.")));
        return;
      } else if (usersDetails.lastname == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Lastname is missing. Please inform a lastname.")));
        return;
      } else if (usersDetails.username == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Username is missing. Please inform a username.")));
        return;
      } else if ((usersRight == UsersRight.NORMAL_EMPLOYEE
        || usersRight == UsersRight.HIGH_EMPLOYEE
        || usersRight == UsersRight.DIRECTOR)
        && usersDetails.job == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Job is missing. Please inform a job.")));
        return;
      } else if (usersRight == UsersRight.CLIENT && usersDetails.email == null) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Email is missing. Please inform a valid email address.")));
        return;
      } else if (usersRight == UsersRight.CLIENT
        && (usersDetails.postcode == null
        || usersDetails.street == null
        || usersDetails.country == null
        || usersDetails.city == null)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid address information. Please ensure all address fields are informed.")));
        return;
      }
      // then execute update process
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

  public static void removeUserById(RoutingContext routingContext) {
    try {
      int id = Integer.parseInt(routingContext.pathParam("id"));

      if (!Users.doesIdExist(id)) {
        routingContext.response()
          .setStatusCode(400)
          .putHeader("content-type", "application/json; charset=utf-8")
          .end(Json.encodePrettily(new SimpleHttpResult(400, "This user does not exist")));
      }
      Users.removeUserById(id);
      routingContext.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily("User removed !"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
