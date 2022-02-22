package com.cesi.bhs.api.authentication;

import com.cesi.bhs.api.data.Client;
import com.cesi.bhs.api.data.Users;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.cesi.bhs.api.dao.Authentication.isUsernameAvailable;
import static com.cesi.bhs.api.dao.Authentication.registerClient;

public class AuthenticationManagerImpl implements AuthenticationManager {
  // A map with in A. the token, and in B. a pair of User and the creation date of the token
  static private final HashMap<String, Pair<Users, Date>> tokenMap = new HashMap<>();
  // Service instance
  private static AuthenticationManager instance;

  private AuthenticationManagerImpl() {
  }

  public static AuthenticationManager getInstance() {
    if (AuthenticationManagerImpl.instance == null) {
      instance = new AuthenticationManagerImpl();
    }
    return instance;
  }

  @Override
  public boolean checkToken(String token, AuthenticationContext context) {
    Pair<Users, Date> dataPair = tokenMap.get(token);

    // If the token does not exist
    if (dataPair == null) {
      return false;
    }

    // Is the token still valid
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(Date.from(Instant.now()));
    // The token is valid for three month
    calendar.add(Calendar.MONTH, -3);
    if (dataPair.getValue1().compareTo(calendar.getTime()) < 0) {
      return false;
    }

    // The token is valid but does the user have enough rights to do that ?
    switch (context) {
      case LOGIN:
      case REGISTER:
        return true;
      default:
        return false;
    }
  }

  @Override
  public String login(@NotNull Users user, String password) {
    if (user.validatePassword(password)) {
      return createToken(user);
    }
    return "Token creation failed. Invalid password";
  }

  @Override
  public String register(@NotNull Client client) {
    try {
      // Check if the username is available
      if (!isUsernameAvailable(client.getUsername())) {
        return "This Username is already registered";
      }

      registerClient(client);
    } catch (SQLException e) {
      e.printStackTrace();
      return "Registration failed, internal server error";
    }

    return "Registration done, please login";
  }

  private String createToken(@NotNull Users user) {
    // Get current date
    Date now = Date.from(Instant.now());

    // Prepare the token string
    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append(user.getId()).append(":").append(user.getUsername()).append(":").append(now.getTime());

    // Encode the token
    String token = Base64.getEncoder().encodeToString(tokenBuilder.toString().getBytes());

    tokenMap.put(token, new Pair<>(user, now));

    return token;
  }
}
