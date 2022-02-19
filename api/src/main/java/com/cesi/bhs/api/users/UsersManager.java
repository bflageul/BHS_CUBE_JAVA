package com.cesi.bhs.api.users;

import java.util.List;

public interface UsersManager {

  /**
   * @return  a List of all registered users
   */
   List<GetAllUsers> getAllUsers();
}

