package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.users.GetAllUsers;

import java.util.List;

public interface UsersManager {

  /**
   * @return  a List of all registered users
   */
   List<GetAllUsers> getAllUsers();
}

