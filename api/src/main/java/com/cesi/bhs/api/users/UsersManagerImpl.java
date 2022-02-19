package com.cesi.bhs.api.users;

import com.cesi.bhs.api.dao.Connect;
import com.cesi.bhs.api.dao.ConnectImpl;
import com.cesi.bhs.api.data.UsersRight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//¿¿¿ How to ??? -> implement UserManager interface
public class UsersManagerImpl {
  public static List<GetAllUsers> getAllUsers() throws SQLException {

    //if (currentUser.checkToken())
    String query = "SELECT users.*, client.address, client.mail, employee.job FROM users LEFT JOIN client ON client.users = id LEFT JOIN employee ON employee.users = id;";

    Connect conn = ConnectImpl.getInstance();
    Statement statement = conn.getConnection().createStatement();
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
    return usersList;
  }
};
