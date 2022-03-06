package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.*;
import com.cesi.bhs.api.users.UsersDetails;
import org.javatuples.Triplet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Users {

  /**
   * Check if id exists
   *
   * @param id
   */
  public static boolean isIdExist(int id) throws SQLException {

    String query =  "SELECT COUNT(*) FROM users WHERE id = ?;";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);

    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    System.out.println(rs.getInt("count"));
    if (rs.getInt("count") > 0) {
      // This id refers to a client
      return true;
    }
    return false;
  }

  /**
   * Check if username is used by another user
   * This function compare username of id @param to username @param
   * @param id
   * @param username
   */
  public static boolean isUsernameUsedByOther(int id, String username) throws SQLException {

    String query =  "SELECT COUNT(*) FROM users WHERE id != ? AND username = ?;";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);
    preparedStatement.setString(2, username);

    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    System.out.println(rs.getInt("count"));
    if (rs.getInt("count") > 0) {
      // This id refers to a client
      return true;
    }
    return false;
  }

  /**
   * Check if an id refers to a CLIENT
   *
   * @param id
   */
  public static boolean isClient(int id) throws SQLException {

    String query =  "SELECT COUNT(*) FROM client WHERE users = ?;";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);

    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    System.out.println(rs.getInt("count"));
    if (rs.getInt("count") > 0) {
      // This id refers to a client
      return true;
    }
    return false;
  }

  /**
   * Check if an id refers to an EMPLOYEE
   *
   * @param id
   */
  public static boolean isEmployee(int id) throws SQLException {

    String query = "SELECT COUNT(*) FROM employee WHERE users = ?;";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);

    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    if (rs.getInt("count") > 0) {
      // This id refers to a client
      return true;
    }
    return false;
  }

  /**
   * Check if email information is already used in database
   *
   * @param email
   */
  public static boolean isEmailAvailable(String email) throws SQLException {

    String query = "SELECT COUNT(*) FROM client WHERE (mail = ?)";

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setString(1, email);

    ResultSet rs = preparedStatement.executeQuery();
    rs.next();
    if (rs.getInt("count") > 0) {
      // This email already exists in  database
      return false;
    }
    // This email is available
    return true;
  }

  /**
   * Function rendering a list of all employees and clients registered in database
   *
   * @return List<GetAllUsers>
   */
  public static List<com.cesi.bhs.api.data.Users> getAllUsers() throws SQLException {

    String query = "SELECT users.*, client.mail, employee.job FROM users " +
      "LEFT JOIN client ON client.users = id " +
      "LEFT JOIN employee ON employee.users = id;";

    Connect conn = ConnectImpl.getInstance();
    Statement statement = conn.getConnection().createStatement();
    ResultSet result = statement.executeQuery(query);

    List<com.cesi.bhs.api.data.Users> usersList = new ArrayList<>();

    while (result.next()) {
      if (result.getString("mail") != null) {
        com.cesi.bhs.api.data.Users user = new UsersImpl();
        user.setId(result.getInt("id"));
        user.setUsername(result.getString("username"));
        user.setFirsname(result.getString("firstname"));
        user.setLastname(result.getString("lastname"));
        user.setRight(UsersRight.CLIENT);
        usersList.add(user);
      } else {
        com.cesi.bhs.api.data.Users user = new UsersImpl();
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
   * @return User
   */
  public static Triplet getUserById(int userId) throws SQLException {

    String query = "SELECT users.id, users.username, users.firstname, users.lastname, employee.job, " +
      "mail, postcode, street, country, city, orderdate, deliverydate, price FROM users " +
      "LEFT JOIN employee ON employee.users = users.id " +
      "LEFT JOIN " +
      "(SELECT address.postcode AS postcode, address.street AS street, address.country AS country, address.city AS city, mail, orderdate, deliverydate, price, clientId FROM " +
      "    (SELECT client.mail AS mail, client.users AS clientId, orderdate, deliverydate, price FROM " +
      "        (SELECT orders.orderdate AS orderdate, orders.deliverydate AS deliverydate, orders.price AS price, orders_join_client.users AS orderClientId FROM orders_join_client " +
      "        JOIN users ON orders_join_client.users = users.id " +
      "        JOIN orders ON orders_join_client.orders = orders.id) AS orderjoinselection " +
      "    RIGHT JOIN client ON client.users = orderjoinselection.orderClientId) AS clientselection " +
      "LEFT JOIN address ON clientselection.clientId = address.id) AS finalselection ON users.id = finalselection.clientId " +
      "WHERE users.id = ?;";

    Triplet<Client, Order, Employee> triplet = null;
    com.cesi.bhs.api.data.Users users = new UsersImpl();
    Address address = new AddressImpl();
    Order order = new OrderImpl();
    Client client = new ClientImpl();
    Employee employee = new EmployeeImpl();

    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, userId);
    ResultSet result = preparedStatement.executeQuery();

    while (result.next()) {
      if (result.getString("mail") != null) {
        address.setPostcode(result.getString("postcode"));
        address.setStreet(result.getString("street"));
        address.setCity(result.getString("city"));
        address.setCountry(result.getString("country"));
        order.setOrderdate(result.getDate("orderdate"));
        order.setDeliverydate(result.getDate("deliverydate"));
        order.setPrice(result.getInt("price"));
        client.setId(result.getInt("id"));
        client.setUsername(result.getString("username"));
        client.setFirsname(result.getString("firstname"));
        client.setLastname(result.getString("lastname"));
        client.setMail(result.getString("mail"));
        client.setRight(UsersRight.CLIENT);
        triplet = new Triplet<>(client, order, employee);
        client.setAddress(address);
      } else {
        employee.setId(result.getInt("id"));
        employee.setUsername(result.getString("username"));
        employee.setFirsname(result.getString("firstname"));
        employee.setLastname(result.getString("lastname"));
        employee.setJob(result.getString("job"));
        employee.setRight(UsersRight.NORMAL_EMPLOYEE);
        triplet = new Triplet<>(client, order, employee);
      }
    }
    return triplet;
  }

  /**
   * Function creating a new user.
   * You must indicate the user right as function parameter in order to make difference between Client and Employee
   *
   * @return usersDetails
   */
  public static UsersDetails createUser(UsersRight usersRight, UsersDetails usersDetails) throws SQLException {

    com.cesi.bhs.api.data.Users user = new UsersImpl();
    user.setClearPassword(usersDetails.password);

    Connect conn = ConnectImpl.getInstance();

    String query = "INSERT INTO users(username, firstname, lastname, password) values (?, ?, ?, ?) RETURNING id;";
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
      preparedStatement.executeUpdate();
    } else if (usersRight == UsersRight.NORMAL_EMPLOYEE ||
      usersRight == UsersRight.HIGH_EMPLOYEE ||
      usersRight == UsersRight.DIRECTOR) {

      query = "INSERT INTO employee(users, job) values (?, ?);";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setInt(1, userId);
      preparedStatement.setString(2, usersDetails.job);
      preparedStatement.executeUpdate();
    }
    return usersDetails;
  }

  /**
   *
   * @param id
   * @param usersRight
   * @param usersDetails
   * @return
   * @throws SQLException
   */
  public static UsersDetails updateUserById(int id, UsersRight usersRight, UsersDetails usersDetails) throws SQLException {

    com.cesi.bhs.api.data.Users user = new UsersImpl();
    user.setClearPassword(usersDetails.password);

    Connect conn = ConnectImpl.getInstance();

    String query = "UPDATE users SET (username, lastname, firstname, password) = (?,?,?,?) WHERE id = ?;";
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);

    preparedStatement.setString(1, usersDetails.username);
    preparedStatement.setString(2, usersDetails.firstname);
    preparedStatement.setString(3, usersDetails.lastname);
    preparedStatement.setString(4, user.getPassword());
    preparedStatement.setInt(5, id);
    preparedStatement.executeUpdate();

    if (usersRight == UsersRight.CLIENT) {
      query = "UPDATE client SET mail = ? WHERE users = ? RETURNING address;";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setString(1, usersDetails.email);
      preparedStatement.setInt(2, id);
      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      int clientAddressId = rs.getInt("address");

      query = "UPDATE address SET (postcode, street, country, city) = (?,?,?,?) WHERE id = ?;";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setString(1, usersDetails.postcode);
      preparedStatement.setString(2, usersDetails.street);
      preparedStatement.setString(3, usersDetails.country);
      preparedStatement.setString(4, usersDetails.city);
      preparedStatement.setInt(5, clientAddressId);
      preparedStatement.executeUpdate();

    } else if (usersRight == UsersRight.NORMAL_EMPLOYEE ||
      usersRight == UsersRight.HIGH_EMPLOYEE ||
      usersRight == UsersRight.DIRECTOR ) {
      query = "UPDATE employee SET job = ? WHERE users = ?;";
      preparedStatement = conn.getConnection().prepareStatement(query);
      preparedStatement.setString(1, usersDetails.job);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
    }
    return usersDetails;
  }

  public static void removeUserById(int id) throws SQLException {
    String query = "DELETE FROM users WHERE id = ?;";
    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setInt(1, id);
    preparedStatement.executeUpdate();
  }
}
