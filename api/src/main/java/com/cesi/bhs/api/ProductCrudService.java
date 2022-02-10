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
      PreparedStatement pstmt = getConnection().prepareStatement("Insert into Product (name, stock, description, type, origin, medal, productorname) VALUES (?,?,?,?,?,?)");
      pstmt.setString(1, product.getName());
      pstmt.setInt(2, product.getStock());
      pstmt.setString(3, product.getDescription())
      pstmt.setString(4, product.getType());
      pstmt.setString(5, product.getOrigin());
      pstmt.setString(6, product.getMedal());
      pstmt.setString(7, product.getProductorname());
    } catch (Exception e) {
      System.out.println(e);


    }
  }

  public static Product add(ProductImpl product) {
    final Integer id = "fr" + System.currentTimeMillis() + "d";
    final Product newproduct = new ProductImpl(id,
      product.getName(),
      product.getStock(),
      product.getDescription(),
      product.getType(),
      product.getOrigin(),
      product.getMedal(),
      product.getProductorname());
    return newproduct;
  }

  public static Product findById(Integer id) {
    return product.findById(id);
  }

  public static Product update(final Product product){
    product.put(product.getId(), product);
    return product;
  }
}
