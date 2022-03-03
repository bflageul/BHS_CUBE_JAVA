package com.cesi.bhs.api.data;

/**
 * Address is composed by a postcode, a street(number+name of the street),
 * a country and a city.
 */
public interface Address {
  static void setAddress(Address[] toArray) {
  }

  public int getId();

  public void setId(int idAddress);

  public String getPostcode();

  public void setPostcode(String postcodeAddress);

  public String getStreet();

  public void setStreet(String streetAddress);

  public String getCountry();

  public void setCountry(String countryAddress);

  public String getCity();

  public void setCity(String cityAddress);
}
