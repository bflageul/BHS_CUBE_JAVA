package com.cesi.bhs.api.DAO;

import com.cesi.bhs.api.data.Test;

import java.sql.PreparedStatement;

public class TestDAOImpl implements TestDAO {

    /**
     * instance du service
     */
    private static TestDAO instance;

    /**
     * Récupère l'instance du service
     *
     * @return l'instance du service
     */
    public static TestDAO getInstance() {
        if (TestDAOImpl.instance == null) {
            instance = new TestDAOImpl();
        }
        return instance;
    }

    private TestDAOImpl() {
        //do nothing
    }

    private Connect connectInstance = ConnectImpl.getInstance();

    public void save(Test test) {
        try {
            PreparedStatement pstmt = connectInstance.getConnection().prepareStatement("Insert into test (id,commentaire, date) VALUES (?,?,?)");
            pstmt.setInt(1, test.getId());
            pstmt.setString(2, test.getCommentaire());
            pstmt.setObject(3, test.getDate());

            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                connectInstance.getConnection().close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
