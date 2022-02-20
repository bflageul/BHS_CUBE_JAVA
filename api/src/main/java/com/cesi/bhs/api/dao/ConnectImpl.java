package com.cesi.bhs.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectImpl implements Connect {

  /**
   * instance du service
   */
  private static Connect instance;

  private ConnectImpl() {
    //do nothing
  }

  /**
   * Récupère l'instance du service
   *
   * @return l'instance du service
   */
  public static Connect getInstance() {
    if (ConnectImpl.instance == null) {
      instance = new ConnectImpl();
    }
    return instance;
  }

  @Override
  /**
   * @see Connect#getConnection()
   */
  public Connection getConnection() {
    Connection connexion = null;

    try {
      Class.forName("org.postgresql.Driver");
      connexion = DriverManager.getConnection("jdbc:postgresql://localhost/stivedb", "cesi", "cesi");
    } catch (Exception e) {
      System.out.println(e);
    }

    return connexion;
  }
}
