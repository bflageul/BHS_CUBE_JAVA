package com.cesi.bhs.api.authentication;

import com.cesi.bhs.api.data.Users;

public interface LoginManager {
  /**
   * Verify the provided token and check if this user is allowed to work with the context
   * @param token
   * @param context
   * @return
   */
  boolean checkToken(String token, LoginContext context);

  /**
   * Log the user in, generate a token
   * @param user
   * @param password
   * @return
   */
  String login(Users user, String password);
}
