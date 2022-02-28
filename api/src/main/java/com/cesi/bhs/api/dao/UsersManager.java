package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Users;

import java.util.List;

public interface UsersManager {

  /**
   * @return  a List of all registered users
   */
   List<Users> getAllUsers();
}

