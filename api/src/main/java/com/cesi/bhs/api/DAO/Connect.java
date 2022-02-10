package com.cesi.bhs.api.DAO;

import java.sql.Connection;

public interface Connect {

    /**
     * Récupère la connection a la BDD
     * @return la connection
     */
    Connection getConnection();

}
