package com.cesi.bhs.api.data;


import java.sql.Date;

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
  private Supplier[] Supplier;

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
    return Supplier;
  }

  @Override
  public void setSupplier(Supplier[] supplier) {
    Supplier = supplier;
  }
}
