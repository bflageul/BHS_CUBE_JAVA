package com.cesi.bhs.api;

import com.cesi.bhs.api.db.Product;
import com.cesi.bhs.api.db.ProductImpl;

import java.sql.Date;
import java.util.List;
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

  public void add(Product product) {
    try {
      PreparedStatement pstmt = getConnection().prepareStatement("Insert into Product (name, stock, description, type, origin, medal,birthdate, productorname) VALUES (?,?,?,?,?,?,?)");
      pstmt.setString(1, product.getName());
      pstmt.setInt(2, product.getStock());
      pstmt.setString(3, product.getDescription());
      pstmt.setString(4, product.getType());
      pstmt.setString(5, product.getOrigin());
      pstmt.setString(6, product.getMedal());
      pstmt.setDate(7, (Date) product.getBirthdate());
      pstmt.setString(8, product.getProductorname());
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);


    }
  }

 /** public static Product add(ProductImpl product) {
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
    return products.findById(id);
  }

  public static Product update(final Product product){
    product.put(product.getId(), product);
    return product;
  }

  public void remove(final Integer id){
    products.remove(id);
  }

  public static
  List<Product> getAll(){
        return products(id);
  }**/
}
