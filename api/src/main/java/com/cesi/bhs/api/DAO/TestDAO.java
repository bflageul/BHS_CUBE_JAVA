package com.cesi.bhs.api.DAO;

import com.cesi.bhs.api.data.Test;

public interface TestDAO {

    /**
     * Sauvegarde mon test en base
     *
     * @param test le test a sauvegarder.
     */
    void save(Test test);
}
