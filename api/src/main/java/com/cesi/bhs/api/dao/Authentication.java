package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
  public static boolean isUsernameAvailable(String username) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement checkUsernameQuery = connect.getConnection().prepareStatement("SELECT COUNT(*) FROM users WHERE (username = ?)");
    checkUsernameQuery.setString(1, username);

    ResultSet usernameResult = checkUsernameQuery.executeQuery();
    usernameResult.next();
    if (usernameResult.getInt("count") > 0) {
      System.out.println(usernameResult.getInt("count"));
      // The username is already in the database
      return false;
    }

    // The username is available
    return true;
  }

  @Contract("_ -> param1")
  public static @NotNull Client registerClient(@NotNull Client client) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement insertUser = connect.getConnection().prepareStatement("INSERT INTO users (username, lastname, firstname, password) VALUES (?, ?, ?, ?) RETURNING id");
    insertUser.setString(1, client.getUsername());
    insertUser.setString(2, client.getFirsname());
    insertUser.setString(3, client.getLastname());
    insertUser.setString(4, client.getPassword());

    ResultSet insertedUserID = insertUser.executeQuery();
    insertedUserID.next();
    client.setId(insertedUserID.getInt("id"));

    PreparedStatement insertClient = connect.getConnection().prepareStatement("INSERT INTO client (users, mail) VALUES (?, ?)");

    insertClient.setInt(1, client.getId());
    insertClient.setString(2, client.getMail());

    insertClient.executeUpdate();

    return client;
  }

  public static @NotNull Users getUser(String username) throws SQLException {
    // Get password from database
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = connect.getConnection().prepareStatement("SELECT * FROM users WHERE (username = ?)");
    preparedStatement.setString(1, username);

    ResultSet resultSet = preparedStatement.executeQuery();
    Users user = new UsersImpl();
    if (resultSet.next()) {
      user.setId(resultSet.getInt("id"));
      user.setUsername(resultSet.getString("username"));
      user.setFirsname(resultSet.getString("firstname"));
      user.setLastname(resultSet.getString("lastname"));
      user.setPasswordHash(resultSet.getString("password"));
    }
    return user;
  }
}
