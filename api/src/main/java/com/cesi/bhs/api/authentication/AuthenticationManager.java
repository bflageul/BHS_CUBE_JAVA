package com.cesi.bhs.api.authentication;

import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.Users;

public interface AuthenticationManager {
  /**
   * Verify the provided token and check if this user is allowed to work with the context
   *
   * @param token
   * @param context
   * @return
   */
  boolean checkToken(String token, AuthenticationContext context);

  /**
   * Log the user in, generate a token
   *
   * @param user
   * @param password
   * @return
   */
  String login(Users user, String password);

  /**
   * Register a new user of type client
   *
   * @param client
   * @return
   */
  String register(Client client);
}
