package com.cesi.bhs.api.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectImpl implements Connect {

    /**
     * instance du service
     */
    private static Connect instance;

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

    private ConnectImpl(){
        //do nothing
    }

    @Override
    /**
     * @see Connect#getConnection()
     */
    public Connection getConnection() {
        Connection connexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connexion = DriverManager.getConnection("jdbc:mysql://localhost/stivedb", "cesi", "cesi");
        } catch (Exception e) {
            System.out.println(e);
        }

        return connexion;

    }
}
