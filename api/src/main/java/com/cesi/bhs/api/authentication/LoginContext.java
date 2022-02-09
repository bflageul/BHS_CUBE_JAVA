package com.cesi.bhs.api.authentication;

/**
 * Contexts for the login, we will use that to define the user rights
 */
public enum LoginContext {
  LOGIN,
  REGISTER,
  CREATE_USER,
  DELETE_USER,
  UPDATE_USER,
  VIEW_USER
}
