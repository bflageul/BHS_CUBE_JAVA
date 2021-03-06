package com.cesi.bhs.api.db;



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


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getMedal() {
    return medal;
  }

  public void setMedal(String medal) {
    this.medal = medal;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getProductorname() {
    return productorname;
  }

  public void setProductorname(String productorname) {
    this.productorname = productorname;
  }


  public Supplier[] getSupplier() {
    return Supplier;
  }

  public void setSupplier(Supplier[] supplier) {
    Supplier = supplier;
  }
}
