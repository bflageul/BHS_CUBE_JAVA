package com.cesi.bhs.api.data;

/**
 * Address is composed by a postcode, a street(number+name of the street),
 * a country and a city.
 */
public interface Address {

  int getId();
  void setId();

  String getPostcode();
  void setPostcode();

  String getStreet();
  void setStreet();

  String getCountry();
  void setCountry();

  String getCity();
  void setCity();
}
