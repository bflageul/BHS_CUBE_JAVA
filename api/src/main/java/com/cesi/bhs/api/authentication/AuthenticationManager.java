package com.cesi.bhs.api.authentication;

import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.Users;

public interface AuthenticationManager {
  /**
   * Verify the provided token and check if this user is allowed to work with the context
   */
  boolean checkToken(String token, AuthenticationContext context);

  /**
   * Log the user in, generate a token
   */
  String login(Users user, String password);

  /**
   * Register a new user of type client
   */
  String register(Client client);
}
