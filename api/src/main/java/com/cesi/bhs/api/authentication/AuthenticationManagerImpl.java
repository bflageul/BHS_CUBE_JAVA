package com.cesi.bhs.api.authentication;

import com.cesi.bhs.api.data.Users;
import org.javatuples.Pair;

import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AuthenticationManagerImpl implements AuthenticationManager {
  // A map with in A. the token, and in B. a pair of User and the creation date of the token
  static private final HashMap<String, Pair<Users, Date>> tokenMap = new HashMap<>();

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
    if (dataPair.getValue1().compareTo(calendar.getTime()) < 0 ){
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

  public String login(Users user, String password) {
    if (user.validatePassword(password)) {
      return createToken(user);
    }
    return "Token creation failed";
  }

  private String createToken(Users user) {
    // Get current date
    Date now = Date.from(Instant.now());

    // Prepare the token string
    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append(user.getId()).append(":").append(user.getUsername()).append(":").append(now);

    // Encode the token
    String token = Base64.getEncoder().encode(tokenBuilder.toString().getBytes()).toString();

    tokenMap.put(token, new Pair<>(user, now));

    return token;
  }
}
