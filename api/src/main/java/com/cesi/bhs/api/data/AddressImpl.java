package com.cesi.bhs.api.data;

public class AddressImpl implements Address {
  private  int id;
  private String postcode;
  private String street;
  private String country;
  private String city;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int idAddress){
    id = idAddress;
  }

  @Override
  public String getPostcode() {
    return postcode;
  }

  @Override
  public void setPostcode(String postcodeAddress){
    postcode = postcodeAddress;
  }

  @Override
  public String getStreet() {
    return street;
  }

  @Override
  public void setStreet(String streetAddress){
    street = streetAddress;
  }

  @Override
  public String getCountry(){
    return country;
  }

  @Override
  public void setCountry(String countryAddress){
    country = countryAddress;
  }

  @Override
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(String cityAddress){
    city = cityAddress;
  }
}
