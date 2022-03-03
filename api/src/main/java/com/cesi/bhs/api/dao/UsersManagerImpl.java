package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Users;
import com.cesi.bhs.api.data.UsersImpl;
import com.cesi.bhs.api.data.UsersRight;
import com.cesi.bhs.api.users.UsersDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//¿¿¿ How to ??? -> implement UserManager interface
public class UsersManagerImpl {

  /**
   * Function rendering a list of all employees and clients registered in database
   *
   * @param
   * @return List<GetAllUsers>
   * @throws SQLException
   */
  public static List<Users> getAllUsers() throws SQLException {

    String query = "SELECT users.*, client.address, client.mail, employee.job FROM users " +
      "LEFT JOIN client ON client.users = id " +
      "LEFT JOIN employee ON employee.users = id;";

    Connect conn = ConnectImpl.getInstance();
    Statement statement = conn.getConnection().createStatement();
    ResultSet result = statement.executeQuery(query);

    List<Users> usersList = new ArrayList<Users>();

    while (result.next()) {
      if (result.getString(8) != null) {
        Users user = new UsersImpl();
        user.setId(result.getInt("id"));
        user.setUsername(result.getString("username"));
        user.setFirsname(result.getString("firstname"));
        user.setLastname(result.getString("lastname"));
        user.setRight(UsersRight.CLIENT);
        usersList.add(user);
      } else {
        Users user = new UsersImpl();
        user.setId(result.getInt("id"));
        user.setUsername(result.getString("username"));
        user.setFirsname(result.getString("firstname"));
        user.setLastname(result.getString("lastname"));
        user.setRight(UsersRight.NORMAL_EMPLOYEE);
        usersList.add(user);
      }
    }
    return usersList;
  }

  /**
   * Function specific user, specified by its userId
   *
   * @param userId
   * @return User
   * @throws SQLException
   */
  public static Users getUserById(int userId) throws SQLException {

    String query = "SELECT users.*, client.address, client.mail, employee.job FROM users " +
        "LEFT JOIN client ON client.users = id " +
        "LEFT JOIN employee ON employee.users = id " +
        "WHERE (id = ?);";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, userId);
    ResultSet result = preparedStatement.executeQuery();

    Users user = new UsersImpl();
    while (result.next()) {
      if (result.getString(8) != null) {
        user.setId(result.getInt("id"));
        user.setUsername(result.getString("username"));
        user.setFirsname(result.getString("firstname"));
        user.setLastname(result.getString("lastname"));
        user.setRight(UsersRight.NORMAL_EMPLOYEE);
      } else {
        user.setId(result.getInt("id"));
        user.setUsername(result.getString("username"));
        user.setFirsname(result.getString("firstname"));
        user.setLastname(result.getString("lastname"));
        user.setRight(UsersRight.CLIENT);
      }
    }
    return user;
  }

  /**
   * Function creating a new user.
   * You must indicate the user right as function parameter in order to make difference between Client and Employee
   *
   * @param usersDetails
   * @param usersRight
   * @return usersDetails
   * @throws SQLException
   */
  public static UsersDetails createUser(UsersRight usersRight, UsersDetails usersDetails) throws SQLException {

    Users user = new UsersImpl();
    user.setClearPassword(usersDetails.password);

    Connect conn = ConnectImpl.getInstance();

    String query = "INSERT INTO users(username, firstname, lastname, password) values (?, ?, ?, ?)RETURNING id;";
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setString(1, usersDetails.username);
    preparedStatement.setString(2, usersDetails.firstname);
    preparedStatement.setString(3, usersDetails.lastname);
    preparedStatement.setString(4, user.getPassword());
    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    int userId = rs.getInt("id");

    if (usersRight == UsersRight.CLIENT) {
      query = "INSERT INTO address(postcode, street, country, city) values (?, ?, ?, ?) RETURNING id;";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setString(1, usersDetails.postcode);
      preparedStatement.setString(2, usersDetails.street);
      preparedStatement.setString(3, usersDetails.country);
      preparedStatement.setString(4, usersDetails.city);
      rs = preparedStatement.executeQuery();
      rs.next();
      int addressId = rs.getInt("id");

      query = "INSERT INTO client(users, address, mail) values (?, ?, ?);";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, addressId);
      preparedStatement.setString(3, usersDetails.email);
//      preparedStatement.executeQuery();
    } else if (usersRight == UsersRight.NORMAL_EMPLOYEE ||
        usersRight == UsersRight.HIGH_EMPLOYEE ||
        usersRight == UsersRight.DIRECTOR ) {
      query = "INSERT INTO employee(users, job) values (?, ?);";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setInt(1, userId);
      preparedStatement.setString(3, usersDetails.job);
    }
    return usersDetails;
  }

  public static UsersDetails updateUserById(int id, UsersRight usersRight, UsersDetails usersDetails) throws SQLException {
    Connect conn = ConnectImpl.getInstance();

//    String query = "UPDATE ? SET ? = \' ? \' WHERE users.id = ?;";
    String query = "UPDATE users SET username = \' ggg \' WHERE users.id = ?;";
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);
    ResultSet rs = preparedStatement.executeQuery();
    rs.next();

//    if (usersRight == UsersRight.CLIENT) {
//      preparedStatement.setString(1, usersDetails.postcode);
//      preparedStatement.setString(2, usersDetails.street);
//      preparedStatement.setString(3, usersDetails.country);
//      preparedStatement.setString(4, usersDetails.city);
//      ResultSet rs = preparedStatement.executeQuery();
//      rs.next();
//      int addressId = rs.getInt("id");
//
//      query = "INSERT INTO client(users, address, mail) values (?, ?, ?);";
//      preparedStatement = conn.getConnection().prepareStatement(query);
//      preparedStatement.setInt(1, userId);
//      preparedStatement.setInt(2, addressId);
//      preparedStatement.setString(3, usersDetails.email);
////      preparedStatement.executeQuery();
//    } else if (usersRight == UsersRight.NORMAL_EMPLOYEE ||
//      usersRight == UsersRight.HIGH_EMPLOYEE ||
//      usersRight == UsersRight.DIRECTOR ) {
//      query = "INSERT INTO employee(users, job) values (?, ?);";
//      preparedStatement = conn.getConnection().prepareStatement(query);
//      preparedStatement.setInt(1, userId);
//      preparedStatement.setString(3, usersDetails.job);
//    }

    return usersDetails;
  }
};
