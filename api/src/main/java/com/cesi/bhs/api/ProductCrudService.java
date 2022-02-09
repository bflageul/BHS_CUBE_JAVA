package com.cesi.bhs.api;

import com.cesi.bhs.api.db.Product;
import com.cesi.bhs.api.db.ProductImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProductCrudService {


  public Connection getConnection() {
    Connection connexion = null;
    try {
      Class.forName("org.postgresql.Driver");
      connexion = DriverManager.getConnection("odbc:postgresql://localhost/stivedb", "cesi", "cesi");
    } catch (Exception e) {
      System.out.println(e);
    }
    return connexion;
  }
  public void save(Product product) {
    try {
      PreparedStatement pstmt = getConnection().prepareStatement("Insert into Product (name, stock, type, origin, medal, productorname) VALUES (?,?,?,?,?,?)");
      pstmt.setString(1, product.getName());
      pstmt.setInt(2, product.getStock());
      pstmt.setString(3, product.getType());
      pstmt.setString(4, product.getOrigin());
      pstmt.setString(5, product.getMedal());
      pstmt.setString(6, product.getProductorname());
    } catch (Exception e) {
      System.out.println(e);


    }
  }
  public Product add( ProductImpl product) {
    final String id = "fr" + System.currentTimeMillis() + "d";
    final Product newproduct = new ProductImpl(id,
      product.getName(),
      product.getStock(),
      product.getType(),
      product.getOrigin(),
      product.getMedal(),
      product.getProductorname());
    product.put(id, newproduct);
    return newproduct;
  }
}
