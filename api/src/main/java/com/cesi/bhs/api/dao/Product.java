package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.ProductImpl;
import com.cesi.bhs.api.data.SupplierImpl;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

  public static @NotNull com.cesi.bhs.api.data.Product selectProduct(@NotNull int id) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement selectProduct = connect.getConnection().prepareStatement("SELECT product.*," +
      " supplier.id as supplierid," +
      " supplier.name as suppliername FROM product_join_supplier\n" +
      "  LEFT JOIN product ON product_join_supplier.product = product.id\n" +
      "  LEFT JOIN supplier ON product_join_supplier.supplier = supplier.id\n" +
      "  WHERE product.id = ?;");

    selectProduct.setInt(1, id);
    ResultSet resultSet =  selectProduct.executeQuery();

   resultSet.next();
    List<SupplierImpl> suppliers = new ArrayList();
    suppliers.add(new SupplierImpl(resultSet.getInt("supplierid"),resultSet.getString("suppliername")));



    ProductImpl product = new ProductImpl();
    product.setId((resultSet.getInt("id")));
    product.setName(resultSet.getString("name"));
    product.setStock(resultSet.getInt("stock"));
    product.setType(resultSet.getString("producttype"));
    product.setOrigin(resultSet.getString("origin"));
    product.setMedal(resultSet.getString("medal"));
    product.setBirthdate(resultSet.getDate("birthdate"));
    product.setProductorname(resultSet.getString("productorname"));

    while (resultSet.next()){
      suppliers.add(new SupplierImpl(resultSet.getInt("supplierid"),resultSet.getString("suppliername")));

    }
    product.setSupplier(suppliers.toArray(new SupplierImpl[suppliers.size()]));

    return product;
  }
}
/**
 * read -> on prend un id pour renvoyer un produit , ou bien une liste
 * delete on prend un id et on supprime un id
 * update on prend un produit et on update
 */

