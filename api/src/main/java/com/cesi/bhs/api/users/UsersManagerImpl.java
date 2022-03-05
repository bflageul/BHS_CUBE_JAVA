package com.cesi.bhs.api.users;

import com.cesi.bhs.api.dao.Connect;
import com.cesi.bhs.api.dao.ConnectImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersManagerImpl {
  /**
   * Check if username already (in order to catch exception when creating or updating user)
   * @param username
   * @return
   * @throws SQLException
   */

  public static boolean checkUsername(String username) throws SQLException {
    String query = "SELECT username FROM users WHERE (username = ?);";
    Connect conn = ConnectImpl.getInstance();
    PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
    preparedStatement.setString(1, username);
    ResultSet rs = preparedStatement.executeQuery();
    boolean result;
    if (rs.next()) {
      result = true;
    } else {
      result = false;
    }
    return result;
  }
}
