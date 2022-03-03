package com.cesi.bhs.api.data;


import com.cesi.bhs.api.product.RegistrationProduct;

import java.util.Date;

public class ProductImpl implements Product {

  private int id;
  private String name;
  private int stock;
  private String description;
  private String type;
  private String origin;
  private String medal;
  private Date birthdate;
  private String productorname;
  private Supplier[] supplier;

  public ProductImpl() {

  }

  public ProductImpl(int productid, String productname) {
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getStock() {
    return stock;
  }

  @Override
  public void setStock(int stock) {
    this.stock = stock;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String getOrigin() {
    return origin;
  }

  @Override
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  @Override
  public String getMedal() {
    return medal;
  }

  @Override
  public void setMedal(String medal) {
    this.medal = medal;
  }

  @Override
  public Date getBirthdate() {
    return birthdate;
  }

  @Override
  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  @Override
  public String getProductorname() {
    return productorname;
  }

  @Override
  public void setProductorname(String productorname) {
    this.productorname = productorname;
  }

  @Override
  public Supplier[] getSupplier() {
    return supplier;
  }

  @Override
  public void setSupplier(Supplier[] supplier) {
    supplier = supplier;
  }

 public ProductImpl(RegistrationProduct registrationProduct){
    this.name = registrationProduct.name;
    this.stock = registrationProduct.stock;
    this.description = registrationProduct.description;
    this.type = registrationProduct.type;
    this.origin = registrationProduct.origin;
    this.medal = registrationProduct.medal;
    this.birthdate = registrationProduct.birthdate;
    this.productorname = registrationProduct.productorname;
 }

  public ProductImpl(int id,String name, int stock, String description,String type, String origin,  String medal, Date birthdate, String productorname, Supplier[] supplier ){
    this.id = id;
    this.name = name;
    this.stock = stock;
    this.description = description;
    this.type = type;
    this.origin = origin;
    this.medal = medal;
    this.birthdate =birthdate;
    this.productorname = productorname;
    this.supplier=supplier;
  }


  public void put() {
  }
}
