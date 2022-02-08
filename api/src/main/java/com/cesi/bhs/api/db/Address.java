package com.cesi.bhs.api.db;

/**
 * Address is composed by a postcode, a street(number+name of the street),
 * a country and a city.
 */
public interface Address {

  public int getId();
  public void setId();

  public String getPostcode();
  public void setPostcode();

  public String getStreet();
  public void setStreet();

  public String getCountry();
  public void setCountry();

  public String getCity();
  public void setCity();
}
