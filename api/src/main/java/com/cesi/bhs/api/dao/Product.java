package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Client;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class Product {

  /**
   * create product
   * @param product
   * @return
   * @throws SQLException
   */
  public static @NotNull com.cesi.bhs.api.data.Product registerProduct(@NotNull com.cesi.bhs.api.data.Product product) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement insertProduct = connect.getConnection().prepareStatement("Insert into Product (name, stock, description, type, origin, medal,birthdate, productorname) VALUES (?,?,?,?,?,?,?) RETURNING id");
    insertProduct.setString(1, product.getName());
    insertProduct.setInt(2, product.getStock());
    insertProduct.setString(3, product.getDescription());
    insertProduct.setString(4, product.getType());
    insertProduct.setString(5, product.getOrigin());
    insertProduct.setString(6, product.getMedal());
    insertProduct.setDate(7, (Date) product.getBirthdate());
    insertProduct.setString(8, product.getProductorname());
    ResultSet resultSet = insertProduct.executeQuery();
    resultSet.next();
    product.setId(resultSet.getInt("id"));


    return product;
  }


}
