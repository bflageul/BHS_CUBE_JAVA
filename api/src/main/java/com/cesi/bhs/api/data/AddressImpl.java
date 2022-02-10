package com.cesi.bhs.api.data;

public class AddressImpl {
  private  int id;
  private String postcode;
  private String street;
  private String country;
  private String city;

  public int getId() {
    return id;
  }

  public void setId(int idAddress){
    id = idAddress;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcodeAddress){
    postcode = postcodeAddress;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String streetAddress){
    street = streetAddress;
  }

  public String getCountry(){
    return country;
  }

  public void setCountry(String countryAddress){
    country = countryAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String cityAddress){
    city = cityAddress;
  }
}
