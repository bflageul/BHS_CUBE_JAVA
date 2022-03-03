package com.cesi.bhs.api.dao;

import com.cesi.bhs.api.data.Address;
import com.cesi.bhs.api.data.AddressImpl;
import com.cesi.bhs.api.data.ProductImpl;
import com.cesi.bhs.api.data.SupplierImpl;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Supplier {




  /**
     * create supplier
     * @param supplier
     * @return
     * @throws SQLException
     */
    public static @NotNull com.cesi.bhs.api.data.Supplier registerSupplier(@NotNull com.cesi.bhs.api.data.Supplier supplier) throws SQLException {
      Connect connect = ConnectImpl.getInstance();
      PreparedStatement insertSupplier = connect.getConnection().prepareStatement("Insert into Supplier (address, name, phone, mail) VALUES (?,?,?,?) RETURNING id");
      insertSupplier.setString(1, supplier.getAddress());
      insertSupplier.setString(2, supplier.getName());
      insertSupplier.setString(3, supplier.getPhone());
      insertSupplier.setString(4, supplier.getMail());
      ResultSet resultSet =  insertSupplier.executeQuery();
      resultSet.next();
      supplier.setId(resultSet.getInt("id"));


      return supplier;
    }

    public static @NotNull com.cesi.bhs.api.data.Supplier selectSupplier(@NotNull int id) throws SQLException {
      Connect connect = ConnectImpl.getInstance();
      PreparedStatement selectSupplier = connect.getConnection().prepareStatement("SELECT supplier.*," +
        " supplier.id as supplierid," +
        "address.id as addressid" +
        " supplier.name as suppliername FROM product_join_supplier\n" +
        "  LEFT JOIN product ON product_join_supplier.product = product.id\n" +
        "  LEFT JOIN address ON product_join_supplier.address = address.id\n" +
        "  WHERE address.id = ?;");

      selectSupplier.setInt(1, id);
      ResultSet resultSet =  selectSupplier.executeQuery();

      resultSet.next();
      List<Product> products = new ArrayList();
      products.add(new ProductImpl(resultSet.getInt("productid"),resultSet.getString("productname")));
      List<Address> addresses = new ArrayList();
      addresses.add(new AddressImpl(resultSet.getInt("addressid")));


      SupplierImpl supplier = new SupplierImpl();
      supplier.setId((resultSet.getInt("id")));
      supplier.setAddress(resultSet.getString("address"));
      supplier.setName(resultSet.getString("name"));
      supplier.setPhone(resultSet.getString("phone"));
      supplier.setMail(resultSet.getString("mail"));



      while (resultSet.next()){
        suppliers.add(new Supplier(resultSet.getInt("supplierid"),resultSet.getString("suppliername")));

      }
      supplier.setSupplier(suppliers.toArray(new Supplier[suppliers.size()]));
      Address.setAddress(addresses.toArray(new Address[addresses.size()]));

      return supplier;
    }

    public static @NotNull com.cesi.bhs.api.data.Supplier updateSupplier(@NotNull com.cesi.bhs.api.data.Supplier supplier) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement UpdateSupplier = connect.getConnection().prepareStatement("UPDATE Supplier (address, name, phone, mail) VALUES (?,?,?,?) RETURNING id");
    UpdateSupplier.setString(1, supplier.getAddress());
    UpdateSupplier.setString(2, supplier.getName());
    UpdateSupplier.setString(3, supplier.getPhone());
    UpdateSupplier.setString(4, supplier.getMail());
    ResultSet resultSet =  UpdateSupplier.executeQuery();
    resultSet.next();
    supplier.setId(resultSet.getInt("id"));


    return supplier; }


  public static @NotNull com.cesi.bhs.api.data.Supplier deleteSupplier(@NotNull com.cesi.bhs.api.data.Supplier supplier) throws SQLException {
    Connect connect = ConnectImpl.getInstance();
    PreparedStatement DeleteSupplier = connect.getConnection().prepareStatement("DELETE FROM Supplier WHERE id = ?");
    DeleteSupplier.setInt(1, supplier.getId());
    ResultSet resultSet =  DeleteSupplier.executeQuery();
    resultSet.next();



    return supplier; }
  }








